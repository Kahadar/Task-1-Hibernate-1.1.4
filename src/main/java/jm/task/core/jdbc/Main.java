package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        final UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Zaur","Tregulov",(byte) 25);
        userService.saveUser("Bill","Gates",(byte) 26);
        userService.saveUser("Nikto","Nikak",(byte) 27);
        userService.saveUser("Mother","Fucker",(byte) 28);
        List<User> userList = userService.getAllUsers();
        for (User user:userList) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
