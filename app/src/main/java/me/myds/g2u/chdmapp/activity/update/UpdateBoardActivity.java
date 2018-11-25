package me.myds.g2u.chdmapp.activity.update;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.Date;

import me.myds.g2u.chdmapp.R;
import me.myds.g2u.chdmapp.model.bean.Board;
import me.myds.g2u.chdmapp.model.bean.MemberInfo;
import me.myds.g2u.chdmapp.model.manager.BoardManager;
import me.myds.g2u.chdmapp.model.manager.MemeberInfoManager;

public class UpdateBoardActivity extends AppCompatActivity {

    public EditText iptTitle;
    public EditText iptContent;
    public Button btnUpdate;
    public Button btnDelete;

    public String uid;
    public Long createtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_board);

        iptTitle = findViewById(R.id.iptTitle);
        iptContent = findViewById(R.id.iptContent);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        getSupportActionBar().setTitle("공지사항 작성");

        uid = getIntent().getStringExtra("uid");
        if(uid == null){
            btnDelete.setVisibility(View.GONE);
        }else{
            Board board = BoardManager.data.get(uid);
            iptTitle.setText(board.title);
            iptContent.setText(board.content);
            createtime = board.createTime;
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Board board = new Board(
                        iptTitle.getText().toString(),
                        iptContent.getText().toString(),
                        createtime==null?new Date().getTime():createtime
                );

                if(
                        board.title == null ||
                        board.content == null){
                    Toast.makeText(UpdateBoardActivity.this, "모든 필드에 값을 입력 해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                BoardManager.update(uid,board);

                Handler handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        String s = (String) msg.obj;
                    }
                };
                if(uid == null){
                    for(MemberInfo memberInfo : MemeberInfoManager.data.values()){
                        BoardManager.SendMail(
                                memberInfo.email,
                                iptTitle.getText().toString(),
                                iptContent.getText().toString(),
                                handler
                        );
                    }
                }
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoardManager.update(uid,null);
                finish();
            }
        });
    }
}
