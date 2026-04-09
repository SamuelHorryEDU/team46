package IPOS_Detailed_Design.model.users;

import IPOS_Detailed_Design.model.User;
import IPOS_Detailed_Design.model.enums.UserRole;

public class DirectorOfOperations extends User {

    public DirectorOfOperations() {
        setRole(UserRole.DIRECTOR_OF_OPERATIONS);
    }

    public DirectorOfOperations(int userId, String username, String password) {
        super(userId, username, password, UserRole.DIRECTOR_OF_OPERATIONS);
    }
}