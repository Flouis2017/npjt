package com.flouis.npjt.test;


import com.flouis.npjt.pojo.Student;

public class MyTest {

    public static void main(String[] args) {
        Student student = new Student();
        student.setAge(24);
        System.out.println("This student's age is: " + student.getAge());
    }

}
