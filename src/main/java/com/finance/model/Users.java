package com.finance.model;

public class Users {
    private int idUser;
    private String username;
    private String password;
    private String nama;
    private String role;

    public Users() {}

    public Users(int idUser, String username, String password, String nama, String role) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.nama = nama;
        this.role = role;
    }

    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}