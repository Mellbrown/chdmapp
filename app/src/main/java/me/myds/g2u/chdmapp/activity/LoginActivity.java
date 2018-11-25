package me.myds.g2u.chdmapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import me.myds.g2u.chdmapp.R;
import me.myds.g2u.chdmapp.model.manager.LoginManager;

public class LoginActivity extends AppCompatActivity {

    public EditText iptID;
    public EditText iptPW;
    public Button btnLogin;
    public ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Load UI
        iptID = findViewById(R.id.iptID);
        iptPW = findViewById(R.id.iptPW);
        btnLogin = findViewById(R.id.btnLogin);
        progress = findViewById(R.id.progress);
        progress.setVisibility(View.INVISIBLE);

        getSupportActionBar().setTitle("로그인");

        // 로그인 버튼 누를 시
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);

                String strID = iptID.getText().toString();
                String strPW = iptPW.getText().toString();

                boolean result = LoginManager.login(strID, strPW);
                progress.setVisibility(View.INVISIBLE);
                if(result){
                    Toast.makeText(LoginActivity.this, "로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "로그인 실패하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
