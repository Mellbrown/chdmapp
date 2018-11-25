package me.myds.g2u.chdmapp.model.bean;

public class Schedule {

    public static final String TYPE_DATE = "TYPE_DATE";
    public static final String TYPE_END_DATE = "TYPE_END_DATE";
    public static final String TYPE_DATETIME = "TYPE_DATETIME";
    public static final String TYPE_END_DATATIME= "TYPE_END_DATATIME";

    public String uid;
    public String title;

    public String datetype;

    public Long start;
    public Long end;

    public Schedule(){}
    public Schedule(
                String title,
                String datetype,
                Long start,
                Long end ){
        this.title = title;
        this.datetype = datetype;
        this.start = start;
        this.end = end;
    }
}
