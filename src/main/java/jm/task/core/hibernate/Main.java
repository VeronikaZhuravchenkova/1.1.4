package jm.task.core.hibernate;

import jm.task.core.hibernate.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Veronika", "Zhuravchenkova", (byte) 20);
        userService.saveUser("Raul", "Romashkin", (byte) 27);
        userService.saveUser("Maria", "Kuzina", (byte) 34);
        userService.saveUser("Kate", "Ivanova", (byte) 19);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }

}
