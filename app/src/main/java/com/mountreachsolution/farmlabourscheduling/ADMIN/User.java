package com.mountreachsolution.farmlabourscheduling.ADMIN;

public class User {
    private String id;
    private String name;
    private String mobile;
    private String address;
    private String age;
    private String skill;
    private String adharno;

    public User(String id, String name, String mobile, String address, String age, String skill, String adharno) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.age = age;
        this.skill = skill;
        this.adharno = adharno;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getAdharno() {
        return adharno;
    }

    public void setAdharno(String adharno) {
        this.adharno = adharno;
    }
}

