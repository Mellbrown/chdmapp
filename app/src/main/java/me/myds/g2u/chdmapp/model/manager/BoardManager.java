package me.myds.g2u.chdmapp.model.manager;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import me.myds.g2u.chdmapp.model.bean.Board;

public class BoardManager {
    public static final String REMOTE_PATH = "Boardes";
    public static Map<String, Board> data = new HashMap<>();

    public static void init(){
        FirebaseDatabase.getInstance().getReference(REMOTE_PATH).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data = dataSnapshot.getValue(new GenericTypeIndicator<Map<String, Board>>() {});
                if (data == null) data = new HashMap<>();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                data = new HashMap<>();
            }
        });
    }

    public static void update(String uid, Board board){
        if(uid == null){
            uid = FirebaseDatabase.getInstance().getReference(REMOTE_PATH).push().getKey();
        }
        if(board != null)board.uid = uid;
        FirebaseDatabase.getInstance().getReference(REMOTE_PATH).child(uid).setValue(board);
    }

    public static void SendMail(final String to, final String subject, final String content, final Handler handler){
        new Thread(){
            @Override
            public void run() {
                try{
                    URL url = new URL("http://g2u.myds.me/g2umailsender.php");
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();

                    http.setDefaultUseCaches(false);
                    http.setDoInput(true);
                    http.setDoOutput(true);
                    http.setRequestMethod("POST");

                    http.setRequestProperty("content-type","application/x-www-form-urlencoded");

                    StringBuffer buffer = new StringBuffer();
                    buffer.append("to").append("=").append(to).append("&");
                    buffer.append("subject").append("=").append(subject).append("&");
                    buffer.append("content").append("=").append(content);

                    OutputStreamWriter outputStream = new OutputStreamWriter(http.getOutputStream(),"UTF-8");
                    PrintWriter writer = new PrintWriter(outputStream);
                    writer.write(buffer.toString());
                    writer.flush();

                    InputStreamReader streamReader = new InputStreamReader(http.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(streamReader);
                    StringBuilder builder = new StringBuilder();
                    String s;
                    while((s=reader.readLine()) != null) builder.append(s+"\n");
                    s = builder.toString();

                    Message message = new Message();
                    message.obj = s;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
