package me.myds.g2u.chdmapp.activity.update;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import me.myds.g2u.chdmapp.R;
import me.myds.g2u.chdmapp.model.bean.Schedule;
import me.myds.g2u.chdmapp.model.manager.ScheduleManager;
import me.myds.g2u.chdmapp.ymd_hms;

public class UpdateScheduleActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    public EditText iptTitle;
    public CheckBox chbIncludeEnd;
    public CheckBox chbIncludeTime;
    public EditText iptStartDate;
    public EditText iptStartTime;
    public EditText iptEndDate;
    public EditText iptEndTime;
    public Button btnUpdate;
    public Button btnDelete;

    String uid;

    static SimpleDateFormat ptnDate = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat ptnTime = new SimpleDateFormat("HH:mm");
    static SimpleDateFormat ptnDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_schedule);

        getSupportActionBar().setTitle("일정정보 작성");

        iptTitle = findViewById(R.id.iptTitle);
        chbIncludeEnd = findViewById(R.id.chbIncludeEnd);
        chbIncludeTime = findViewById(R.id.chbIncludeTime);
        iptStartDate = findViewById(R.id.iptStartDate);
        iptStartTime = findViewById(R.id.iptStartTime);
        iptEndDate = findViewById(R.id.iptEndDate);
        iptEndTime = findViewById(R.id.iptEndTime);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        iptStartDate.setOnFocusChangeListener(this);
        iptStartTime.setOnFocusChangeListener(this);
        iptEndDate.setOnFocusChangeListener(this);
        iptEndTime.setOnFocusChangeListener(this);

        iptStartDate.setOnClickListener(this);
        iptStartTime.setOnClickListener(this);
        iptEndDate.setOnClickListener(this);
        iptEndTime.setOnClickListener(this);

        uid = getIntent().getStringExtra("uid");
        if(uid == null){
            btnDelete.setVisibility(View.GONE);
        }else {
            Schedule schedule = ScheduleManager.data.get(uid);
            iptTitle.setText(schedule.title);
            iptStartDate.setText(ptnDate.format(new Date(schedule.start)));
            iptStartTime.setText(ptnTime.format(new Date(schedule.start)));
            iptEndDate.setText(ptnDate.format(new Date(schedule.end)));
            iptEndTime.setText(ptnTime.format(new Date(schedule.end)));

            if(Schedule.TYPE_DATE.equals(schedule.datetype)){
                chbIncludeEnd.setChecked(false);
                chbIncludeTime.setChecked(false);
            } else if(Schedule.TYPE_DATETIME.equals(schedule.datetype)){
                chbIncludeEnd.setChecked(false);
                chbIncludeTime.setChecked(true);
            } else if(Schedule.TYPE_END_DATE.equals(schedule.datetype)){
                chbIncludeEnd.setChecked(true);
                chbIncludeTime.setChecked(false);
            } if(Schedule.TYPE_END_DATATIME.equals(schedule.datetype)){
                chbIncludeEnd.setChecked(true);
                chbIncludeTime.setChecked(true);
            }
        }

        ChagneView();

        chbIncludeEnd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ChagneView();
            }
        });

        chbIncludeTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ChagneView();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            long start = ymd_hms.str2ymd_hms(
                    iptStartDate.getText().toString(),
                    iptStartTime.getText().toString()
            ).toDate().getTime();

            long  end = ymd_hms.str2ymd_hms(
                    iptEndDate.getText().toString(),
                    iptEndTime.getText().toString()
            ).toDate().getTime();

            String datetype = "";
            switch ((chbIncludeEnd.isChecked()?2:0) | (chbIncludeTime.isChecked()?1:0)){
                case 0: datetype=Schedule.TYPE_DATE; end = start; break;
                case 1: datetype=Schedule.TYPE_DATETIME; end = start; break;
                case 2: datetype=Schedule.TYPE_END_DATE; break;
                case 3: datetype=Schedule.TYPE_END_DATATIME; break;
            }

            Schedule schedule = new Schedule(iptTitle.getText().toString(), datetype, start,end);

                String erroMsg = "";
                if(schedule.title == null)
                    erroMsg += "제목을 입력 해주세요";
                if(schedule.start > schedule.end)
                    erroMsg += "끝나는 날짜는 시작하는 날짜보다 클 수 없습니다.";
                else
                    for(Schedule o : ScheduleManager.data.values()){
                        if(
                                (start <= o.start && o.start <= end)||
                                (start <= o.end && o.end <= end)
                            ){
                            if(o.uid.equals(uid)) continue;
                            erroMsg += "겹치는 일정이 존재합니다.";
                            break;
                        }
                    }
                if(!erroMsg.equals("")){
                    Toast.makeText(UpdateScheduleActivity.this, erroMsg, Toast.LENGTH_SHORT).show();
                    return;
                }

                ScheduleManager.update(uid,schedule);
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScheduleManager.update(uid,null);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        pop(v);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus) return;
        pop(v);
    }

    private void ChagneView(){
        switch ((chbIncludeEnd.isChecked()?2:0) | (chbIncludeTime.isChecked()?1:0)){
            case 0: //Schedule.TYPE_DATE;
                iptStartTime.setVisibility(View.GONE);
                iptEndDate.setVisibility(View.GONE);
                iptEndTime.setVisibility(View.GONE);
                break;
            case 1: //Schedule.TYPE_DATETIME;
                iptStartTime.setVisibility(View.VISIBLE);
                iptEndDate.setVisibility(View.GONE);
                iptEndTime.setVisibility(View.GONE);
                break;
            case 2: //Schedule.TYPE_END_DATE;
                iptStartTime.setVisibility(View.GONE);
                iptEndDate.setVisibility(View.VISIBLE);
                iptEndTime.setVisibility(View.GONE);
                break;
            case 3: //Schedule.TYPE_END_DATATIME;
                iptStartTime.setVisibility(View.VISIBLE);
                iptEndDate.setVisibility(View.VISIBLE);
                iptEndTime.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void pop(View v){
        switch (v.getId()){
            case R.id.iptStartDate:{
                String strDate = iptStartDate.getText().toString();
                ymd_hms _ymd_hms = ymd_hms.str2ymd(strDate);
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        iptStartDate.setText(ptnDate.format(new ymd_hms(year,month,dayOfMonth).toDate()));
                    }
                },_ymd_hms.year, _ymd_hms.month, _ymd_hms.day).show();
            }break;

            case R.id.iptStartTime:{
                String strTime = iptStartTime.getText().toString();
                ymd_hms _ymd_hms = ymd_hms.str2hms(strTime);
                new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        iptStartTime.setText(ptnTime.format(new ymd_hms(hourOfDay,minute).toDate()));
                    }
                },_ymd_hms.hour, _ymd_hms.min,true).show();
            }break;

            case R.id.iptEndDate:{
                String strDate = iptEndDate.getText().toString();
                ymd_hms _ymd_hms = ymd_hms.str2ymd(strDate);
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        iptEndDate.setText(ptnDate.format(new ymd_hms(year,month,dayOfMonth).toDate()));
                    }
                },_ymd_hms.year, _ymd_hms.month, _ymd_hms.day).show();
            }break;

            case R.id.iptEndTime:{
                String strTime = iptEndTime.getText().toString();
                ymd_hms _ymd_hms = ymd_hms.str2hms(strTime);
                new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        iptEndTime.setText(ptnTime.format(new ymd_hms(hourOfDay,minute).toDate()));
                    }
                },_ymd_hms.hour, _ymd_hms.min,true).show();
            }break;
        }
    }
}
