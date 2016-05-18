package com.walter.sc.okhttp;

/**
 * Created by huangxl on 2016/3/31.
 */
public class User {
    public String userName;
    public String userPwd;
    public String type;

    public User() {

    }


    public User(String userName, String userPwd ,String type) {
        this.userName = userName;
        this.userPwd = userPwd;
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + userName + '\'' +
                ", password='" + userPwd + '\'' +
                '}';
    }
}
