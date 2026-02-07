# Use case diagram - outline

## Package: Account Management (IPOS-SA-ACC)

- Create merchant account
  - includes things like setting contact details, credit limits and discount plans
- Manage account status
  - used to change an account from 'in default' to 'normal' or 'suspended'
- Suspend overdue account
  - trigger: automatically happens when payment is >15 days late.

## Package: Catalogue Management (IPOS-SA-CAT)

- Maintain catalogue
  - add, delete or update items
- Add stock
  - trigger: when new goods arrive at the InfoPharma warehouse
- Search item
  - for the merchant; can search by ID or key term
- Check low stock
  - triggered automatically by the system; alerts the admin/manager when the number
  of items in stock is < the minimum level

## Package: Order Processing (IPOS-SA-ORD)

- Place order
  - for the merchant; select items, check availability, submit order
  - likely \<\<includes>> the "generate invoice" use case in the reports package
- Update order status
  - warehouse staff; they should be able to enter the dispatch date, courier name
  and ref number
- Record payment
  - updates the merchant's balance

## Package: Reports (IPOS-SA-RPT)

- Generate report
  - generalisation: generate turnover report
  - generalisation: generate invoice list
  - generalisation: generate stock turnover report
