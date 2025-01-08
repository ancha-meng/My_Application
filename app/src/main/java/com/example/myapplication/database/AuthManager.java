package com.example.myapplication.database;

public class AuthManager {
    private static AuthManager instance;
    private boolean isLoggedIn;
    private String username;

    private AuthManager() {
        // 私有构造函数
    }

    public static synchronized AuthManager getInstance() {
        if (instance == null) {
            instance = new AuthManager();
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public String getUsername() {
        return username;
    }

    public void setLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
