package com.hubu.myFirstSSM.pojo;

public class User {
    private Integer id;

    private String name;

    private String password;

    //用户名匿名显示
    private String anonymousName;
    public String getAnonymousName(){
        if(name == null)
            return null;
        if(name.length() == 1)
            return "*";
        if(name.length() == 2)
            return name.substring(0,1)+"*";

        //首字母和尾字母不隐藏
        char[] a = name.toCharArray();
        for(int i = 1;i < a.length - 1;i++)
            a[i] = '*';

        return new String(a);
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

}