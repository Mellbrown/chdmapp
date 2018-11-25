package me.myds.g2u.chdmapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.api.services.calendar.Calendar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import me.myds.g2u.chdmapp.R;
import me.myds.g2u.chdmapp.model.manager.BoardManager;
import me.myds.g2u.chdmapp.model.manager.MemeberInfoManager;
import me.myds.g2u.chdmapp.model.manager.ScheduleManager;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        getSupportActionBar().setTitle("창대동문수첩");

        MemeberInfoManager.init();
        BoardManager.init();
        ScheduleManager.init();
        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                startActivity(new Intent(IntroActivity.this, LoginActivity.class));
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(IntroActivity.this, "서비스에 연결할 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
