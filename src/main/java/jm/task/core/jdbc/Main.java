package jm.task.core.jdbc;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("User1", "User1", (byte) 10);
        userService.saveUser("User2", "User2", (byte) 15);
        userService.saveUser("User3", "User3", (byte) 20);
        userService.saveUser("User4", "User4", (byte) 25);

        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
