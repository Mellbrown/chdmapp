package me.myds.g2u.chdmapp.model.manager;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import me.myds.g2u.chdmapp.model.bean.Board;
import me.myds.g2u.chdmapp.model.bean.MemberInfo;

public class MemeberInfoManager {
    public final static String REMOTE_PATH = "MemberInfoes";
    public static Map<String,MemberInfo> data = new HashMap<>();

    public static void init(){
        FirebaseDatabase.getInstance().getReference(REMOTE_PATH).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data = dataSnapshot.getValue(new GenericTypeIndicator<Map<String, MemberInfo>>() {});
                if (data == null) data = new HashMap<>();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                data = new HashMap<>();
            }
        });
    }

    public static void update(String uid, MemberInfo memberInfo){
        if(uid == null){
            uid = FirebaseDatabase.getInstance().getReference(REMOTE_PATH).push().getKey();
        }
        if(memberInfo!=null)memberInfo.uid = uid;
        FirebaseDatabase.getInstance().getReference(REMOTE_PATH).child(uid).setValue(memberInfo);
    }
}
