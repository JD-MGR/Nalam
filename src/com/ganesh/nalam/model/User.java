package com.ganesh.nalam.model;

public class User {
    private long id;
    private String emailId;
    private String password;
    private Role role;
    private String name;

    public enum Role{
        CUSTOMER,PHARMACIST
    }
    public User(){}

    public User(long id, String emailId, String password, Role role, String name) {
        this.id = id;
        this.emailId = emailId;
        this.password = password;
        this.role = role;
        this.name = name;
    }
    public User(long id,String email,String name){
        this.id=id;
        this.emailId=email;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setId(long id){this.id=id;}

    public long getId() {
        return id;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", name='" + name + '\'' +
                '}';
    }
}
