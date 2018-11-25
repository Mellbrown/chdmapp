package me.myds.g2u.chdmapp.activity.update;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import me.myds.g2u.chdmapp.R;
import me.myds.g2u.chdmapp.model.bean.MemberInfo;
import me.myds.g2u.chdmapp.model.manager.MemeberInfoManager;

public class UpdateMemberActivity extends AppCompatActivity {

    public EditText iptID;
    public EditText iptPW;
    public EditText iptName;
    public EditText iptYear;
    public EditText iptMajor;
    public EditText iptPhone;
    public EditText iptEmail;
    public EditText iptAddress;
    public EditText iptDept;
    public EditText iptPosition;
    public EditText iptBoarder;
    public Button btnUpdate;
    public Button btnDelete;

    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_member);

        getSupportActionBar().setTitle("회원정보 작성");

        iptID = findViewById(R.id.iptID);
        iptPW = findViewById(R.id.iptPW);
        iptName = findViewById(R.id.iptName);
        iptYear = findViewById(R.id.iptYear);
        iptMajor = findViewById(R.id.iptMajor);
        iptPhone = findViewById(R.id.iptPhone);
        iptEmail = findViewById(R.id.iptEmail);
        iptAddress = findViewById(R.id.iptAddress);
        iptDept = findViewById(R.id.iptDept);
        iptPosition = findViewById(R.id.iptPosition);
        iptBoarder = findViewById(R.id.iptBoarder);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        uid = getIntent().getStringExtra("uid");
        if(uid == null){
            btnDelete.setVisibility(View.GONE);
        }else{
            MemberInfo memberInfo = MemeberInfoManager.data.get(uid);
            iptID.setText(memberInfo.id);
            iptPW.setText(memberInfo.pw);
            iptName.setText(memberInfo.name);
            iptYear.setText(memberInfo.year);
            iptMajor.setText(memberInfo.major);
            iptPhone.setText(memberInfo.phone);
            iptEmail.setText(memberInfo.email);
            iptAddress.setText(memberInfo.address);
            iptDept.setText(memberInfo.dept);
            iptPosition.setText(memberInfo.position);
            iptBoarder.setText(memberInfo.boarder);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MemberInfo memberInfo = new MemberInfo(
                        iptID.getText().toString(),
                        iptPW.getText().toString(),
                        iptName.getText().toString(),
                        iptYear.getText().toString(),
                        iptMajor.getText().toString(),
                        iptPhone.getText().toString(),
                        iptEmail.getText().toString(),
                        iptAddress.getText().toString(),
                        iptDept.getText().toString(),
                        iptPosition.getText().toString(),
                        iptBoarder.getText().toString()
                );
                if(
                    memberInfo.id == null ||
                    memberInfo.pw == null ||
                    memberInfo.name == null ||
                    memberInfo.year == null ||
                    memberInfo.major == null ||
                    memberInfo.phone == null ||
                    memberInfo.email == null ||
                    memberInfo.address == null ||
                    memberInfo.dept == null ||
                    memberInfo.position == null ||
                    memberInfo.boarder == null ){

                    Toast.makeText(UpdateMemberActivity.this, "모든 필드를 빈칸 없이 입력 해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                MemeberInfoManager.update(uid,memberInfo);
                finish();
            }
        });

        btnUpdate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ArrayList<String> list = new ArrayList<String>() {{
                    add("정보통신공핫과");
                    add("물리학과");
                    add("컴퓨터공학과");
                    add("전자공학과");
                    add("기계공학과");
                    add("토목공학과");
                    add("산업디자인학과");
                    add("문화테크노학과");
                    add("신문학과");
                    add("불어국문학과");
                    add("수학과");
                    add("바이오테크닉학과");
                    add("미술학과");
                    add("조선해양학과");
                    add("회계학과");
                    add("철학과");
                    add("체육학과");
                }};
                for(int i = 0; 100 > i; i++){
                    String user = "user" + String.format("%03d", ((int) (Math.random() * 1000)));
                    MemberInfo memberInfo = new MemberInfo(
                            user,
                            "user",
                            user,
                            String.format("%4d", ((int) (1930 + Math.random() * 100))),
                            list.get(((int) (Math.random() * list.size()))),
                            String.format("010-%4d-%4d", ((int) (Math.random() * 1000)), ((int) (Math.random() * 1000))),
                            String.format("%s@gmail.com", user),
                            "경남 창원시",
                            "부서",
                            "직위",
                            "회원"
                    );
                    MemeberInfoManager.update(null,memberInfo);
                }
                Toast.makeText(UpdateMemberActivity.this, "회원 100생성", Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MemeberInfoManager.update(uid,null);
                finish();
            }
        });

        btnDelete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ArrayList<String> del = new ArrayList<>();
                for(MemberInfo o : MemeberInfoManager.data.values()){
                    if(o.id.matches("user.*"))
                        del.add(o.uid);
                }

                for(String s : del)
                    MemeberInfoManager.update(s,null);

                Toast.makeText(UpdateMemberActivity.this, "회원 user 삭제", Toast.LENGTH_SHORT).show();

                return true;
            }
        });
    }

}
