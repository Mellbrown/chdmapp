package me.myds.g2u.chdmapp.model.manager;

import me.myds.g2u.chdmapp.model.bean.MemberInfo;

public class LoginManager {

    public static String uid;

    public static boolean login(String id, String pw){
        if(id.equals("admin") && pw.equals("sweswe")){
            uid = "admin";
            return true;
        }

        for(MemberInfo memberInfo : MemeberInfoManager.data.values()){
            if(memberInfo.id.equals(id) && memberInfo.pw.equals(pw)){
                uid = memberInfo.uid;
                return true;
            }
        }
        return false;
    }

    public static boolean isLogin(){
        return uid != null;
    }

    public static boolean isAdmin(){
        return uid != null && uid.equals("admin");
    }

    public static void logout(){
        uid = null;
    }
}
