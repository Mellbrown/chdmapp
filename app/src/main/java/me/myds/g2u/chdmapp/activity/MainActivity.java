package me.myds.g2u.chdmapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.logging.LogManager;

import me.myds.g2u.chdmapp.R;
import me.myds.g2u.chdmapp.fragment.edit.EditBoardFragment;
import me.myds.g2u.chdmapp.fragment.edit.EditMemberInfoFragment;
import me.myds.g2u.chdmapp.fragment.edit.EditScheduleFragment;
import me.myds.g2u.chdmapp.fragment.read.ReadBoardFragment;
import me.myds.g2u.chdmapp.fragment.read.ReadMemberInfoFragment;
import me.myds.g2u.chdmapp.fragment.read.ReadScheduleFragment;
import me.myds.g2u.chdmapp.model.manager.LoginManager;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private ActionBar actionBar;
    private BottomNavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nav = findViewById(R.id.nav);
        fragmentManager = getSupportFragmentManager();
        actionBar = getSupportActionBar();

        nav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        nav.setSelectedItemId(R.id.nav_memberinfo);
    }

    private void setFragment(Fragment fragment, String fragmentTag){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment,fragmentTag);
        fragmentTransaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.nav_memberinfo:{
                    actionBar.setTitle("회원 명단");
                    if(LoginManager.isAdmin())
                        setFragment(new EditMemberInfoFragment(),"EditMemberInfoFragment");
                    else
                        setFragment(new ReadMemberInfoFragment(),"ReadMemberInfoFragment");
                }return true;
                case R.id.nav_board:{
                    actionBar.setTitle("공지사항");
                    if(LoginManager.isAdmin())
                        setFragment(new EditBoardFragment(),"EditBoardFragment");
                    else
                        setFragment(new ReadBoardFragment(),"ReadBoardFragment");
                }return true;
                case R.id.nav_schedule:{
                    actionBar.setTitle("일정목록");
                    if(LoginManager.isAdmin())
                        setFragment(new EditScheduleFragment(),"EditScheduleFragment");
                    else
                        setFragment(new ReadScheduleFragment(),"ReadScheduleFragment");
                }return true;
                case R.id.btn_logout:{
                    LoginManager.logout();
                    Toast.makeText(MainActivity.this, "로그아웃되었습니다.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    finish();
                }return false;
            }
            return false;
        }
    };
}
