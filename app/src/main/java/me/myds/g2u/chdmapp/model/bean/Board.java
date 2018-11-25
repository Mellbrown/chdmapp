package me.myds.g2u.chdmapp.model.bean;

public class Board {

    public String uid;
    public String title;
    public String content;
    public Long createTime;

    public Board(){}
    public Board(
            String title,
            String content,
            Long createTime){
        this.title = title;
        this.content = content;
        this.createTime = createTime;
    }
}
