package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Chelik1", "first", (byte) 20);
        userService.saveUser("Chelik2", "second", (byte) 22);
        userService.saveUser("Chelik3", "third", (byte) 24);
        userService.saveUser("Chelik4", "forth", (byte) 26);

        new UserServiceImpl().removeUserById(2);

        List<User> users = userService.getAllUsers();
        for(User user: users) {
            System.out.println(user);
        }

        new UserServiceImpl().cleanUsersTable();

        new UserServiceImpl().dropUsersTable();

    }
}
