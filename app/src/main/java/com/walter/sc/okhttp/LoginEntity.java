package com.walter.sc.okhttp;

/**
 * Created by huangxl on 2016/3/31.
 */
public class LoginEntity {
    private String userName;
    private String userPwd;
    private String[] areaNames;
    private String[] department;
    private String date;
    private String test;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String[] getAreaNames() {
        return areaNames;
    }

    public void setAreaNames(String[] areaNames) {
        this.areaNames = areaNames;
    }

    public String[] getDepartment() {
        return department;
    }

    public void setDepartment(String[] department) {
        this.department = department;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
