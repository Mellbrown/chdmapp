package me.myds.g2u.chdmapp.model.bean;

public class MemberInfo {

    public String uid;
    public String id;
    public String pw;

    public String name;
    public String year;
    public String major;

    public String phone;
    public String email;
    public String address;

    public String dept;
    public String position;

    public String boarder;

    public MemberInfo(){}
    public MemberInfo(
            String id,
            String pw,
            String name,
            String year,
            String major,
            String phone,
            String email,
            String address,
            String dept,
            String position,
            String boarder){
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.year = year;
        this.major = major;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.dept = dept;
        this.position = position;
        this.boarder = boarder;
    }
}
