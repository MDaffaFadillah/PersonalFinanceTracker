package com.finance.model;

public class Session {
    private static Users userLogin;

    public static void setUserLogin(Users user) {
        userLogin = user;
    }

    public static Users getUserLogin() {
        return userLogin;
    }

    public static int getIdUser() {
        return (userLogin != null) ? userLogin.getIdUser() : 0;
    }

    public static void logout() {
        userLogin = null;
    }
}
