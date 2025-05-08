package dataObjects;

import common.Constant.Constant;

public class User {
    String email, password, pid;
    public User(String email, String password){
        this.email=email;
        this.password=password;
    }
    public User(String email, String password, String pid){
        this.email=email;
        this.password=password;
        this.pid=pid;
    }
    public User() {
        this(Constant.USERNAME, Constant.PASSWORD);
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
