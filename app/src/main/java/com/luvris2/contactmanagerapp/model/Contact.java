package com.luvris2.contactmanagerapp.model;

public class Contact {
    // 주소록에 추가된 데이터를 다루는 클래스
    public int id;
    public String name;
    public String phone;

    public Contact(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}
