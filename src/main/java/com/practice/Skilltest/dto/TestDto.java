package com.practice.Skilltest.dto;

import org.apache.ibatis.type.Alias;

@Alias("TestDto")
public class TestDto {

    private String name;
    private String cellphone;
    private int id_num;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public int getId_num() {
        return id_num;
    }

    public void setId_num(int id_num) {
        this.id_num = id_num;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "TestDto{" +
                "name='" + name + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", id_num=" + id_num +
                ", age=" + age +
                '}';
    }
}
