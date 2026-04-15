package IPOS_Detailed_Design.service;

import IPOS_Detailed_Design.dao.InvoiceDAO;
import IPOS_Detailed_Design.dao.UserDAO;
import IPOS_Detailed_Design.model.User;
import IPOS_Detailed_Design.model.enums.AccountStatus;
import IPOS_Detailed_Design.model.enums.UserRole;

import java.util.ArrayList;
import java.util.List;

/**
 * AccountStatusService — runs automatically at login.
 * Checks every merchant's overdue invoices and updates their
 * account status according to the IPOS-SA brief rules:
 *
 *   0-15 days overdue  → NORMAL (but reminder shown)
 *   15-30 days overdue → SUSPENDED
 *   30+ days overdue   → IN_DEFAULT
 *
 * IN_DEFAULT → NORMAL can only be done manually by Director of Operations.
 */
public class AccountStatusService {

    private final UserDAO    userDAO    = new UserDAO();
    private final InvoiceDAO invoiceDAO = new InvoiceDAO();

    /**
     * Call this right after login. Returns a list of warning messages
     * to display on the dashboard (e.g. for merchants with reminders due).
     */
    public List<String> runStatusChecks() {
        List<String> warnings = new ArrayList<>();
        List<User> merchants = userDAO.getAllMerchants();

        for (User merchant : merchants) {
            int id = merchant.getUserId();
            long daysOverdue = invoiceDAO.getMaxDaysOverdue(id);
            AccountStatus current = merchant.getAccountStatus();

            if (daysOverdue <= 0) {
                // No overdue invoices — if suspended, restore to normal automatically
                if (current == AccountStatus.SUSPENDED) {
                    userDAO.restoreMerchantToNormal(id);
                }
                // IN_DEFAULT stays IN_DEFAULT — needs manual Director authorisation

            } else if (daysOverdue > 0 && daysOverdue <= 15) {
                // 1-15 days: stay Normal, but generate reminder warning
                if (current == AccountStatus.SUSPENDED) {
                    userDAO.restoreMerchantToNormal(id);
                }
                warnings.add("⚠ REMINDER: " + merchant.getAccountHolderName()
                        + " (Acc: " + merchant.getAccountNo() + ") is "
                        + daysOverdue + " day(s) overdue on payment.");

            } else if (daysOverdue > 15 && daysOverdue <= 30) {
                // 15-30 days: suspend
                if (current == AccountStatus.NORMAL) {
                    userDAO.suspendMerchant(id);
                    warnings.add("🔒 SUSPENDED: " + merchant.getAccountHolderName()
                            + " (Acc: " + merchant.getAccountNo() + ") — "
                            + daysOverdue + " days overdue.");
                } else if (current == AccountStatus.SUSPENDED) {
                    warnings.add("🔒 STILL SUSPENDED: " + merchant.getAccountHolderName()
                            + " — " + daysOverdue + " days overdue.");
                }

            } else {
                // 30+ days: in default
                // Only move to IN_DEFAULT if not already there
                if (current != AccountStatus.IN_DEFAULT) {
                    userDAO.markMerchantInDefault(id);
                    warnings.add("❌ IN DEFAULT: " + merchant.getAccountHolderName()
                            + " (Acc: " + merchant.getAccountNo() + ") — "
                            + daysOverdue + " days overdue. Director authorisation required.");
                } else {
                    warnings.add("❌ IN DEFAULT: " + merchant.getAccountHolderName()
                            + " — " + daysOverdue + " days overdue.");
                }
            }
        }
        return warnings;
    }

    /**
     * Director of Operations manually restores an IN_DEFAULT merchant to Normal.
     * Only allowed if a payment has been made (i.e. balance reduced).
     * Returns true if successful, false if conditions not met.
     */
    public boolean directorRestoreToNormal(int merchantId, UserRole requestingUserRole) {
        // Only Director of Operations can do this
        if (requestingUserRole != UserRole.DIRECTOR_OF_OPERATIONS) {
            return false;
        }

        User merchant = userDAO.getUserById(merchantId);
        if (merchant == null) return false;

        // Must be IN_DEFAULT
        if (merchant.getAccountStatus() != AccountStatus.IN_DEFAULT) {
            return false;
        }

        // Check a payment has actually been made — days overdue must now be <= 30
        // (meaning they've paid enough to come out of default range)
        long daysOverdue = invoiceDAO.getMaxDaysOverdue(merchantId);
        if (daysOverdue > 30) {
            // Payment not sufficient — still in default territory
            return false;
        }

        return userDAO.restoreMerchantToNormal(merchantId);
    }
}