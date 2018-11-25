package me.myds.g2u.chdmapp.fragment.edit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import me.myds.g2u.chdmapp.BaseRecyclerAdapter;
import me.myds.g2u.chdmapp.R;
import me.myds.g2u.chdmapp.activity.update.UpdateScheduleActivity;
import me.myds.g2u.chdmapp.gcal.CalendarSampleActivity;
import me.myds.g2u.chdmapp.model.bean.Schedule;
import me.myds.g2u.chdmapp.model.manager.ScheduleManager;
import me.myds.g2u.chdmapp.viewholder.ScheduleViewHolder;

public class EditScheduleFragment extends Fragment {

    RecyclerView lst;
    RecyclerView.LayoutManager layoutManager;
    BaseRecyclerAdapter<Schedule, ScheduleViewHolder> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewLayout = inflater.inflate(R.layout.fragment_edit_schedule, container,false);

        lst = viewLayout.findViewById(R.id.lst);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new BaseRecyclerAdapter<Schedule, ScheduleViewHolder>(
                R.layout.item_schedule, ScheduleViewHolder.class
        ) {
            @Override
            public void dataConvertViewHolder(ScheduleViewHolder holder, Schedule data) {
                holder.txtTitle.setText(data.title);
                if(Schedule.TYPE_DATE.equals(data.datetype)){
                    holder.txtDate.setText(
                            new SimpleDateFormat("yyyy년 MM월 dd일")
                                    .format(new Date(data.start))
                    );
                }else if(Schedule.TYPE_END_DATE.equals(data.datetype)){
                    holder.txtDate.setText(
                            new SimpleDateFormat("yyyy년 MM월 dd일")
                                    .format(new Date(data.start)) + " -> " +
                                    new SimpleDateFormat("yyyy년 MM월 dd일")
                                            .format(new Date(data.end))
                    );
                }else if(Schedule.TYPE_DATETIME.equals(data.datetype)){
                    holder.txtDate.setText(
                            new SimpleDateFormat("yyyy년 MM월 dd일 aaa hh:mm")
                                    .format(new Date(data.start))
                    );

                }else if(Schedule.TYPE_END_DATATIME.equals(data.datetype)){
                    holder.txtDate.setText(
                            new SimpleDateFormat("yyyy년 MM월 dd일 aaa hh:mm")
                                    .format(new Date(data.start)) + " -> " +
                                    new SimpleDateFormat("yyyy년 MM월 dd일 aaa hh:mm")
                                            .format(new Date(data.end))
                    );
                }
            }
            @Override
            public void onCreateAfterViewHolder(final ScheduleViewHolder holder) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), UpdateScheduleActivity.class);
                        Schedule schedule = dataList.get(holder.getLayoutPosition());
                        intent.putExtra("uid",schedule.uid);
                        startActivity(intent);
                    }
                });
            }
        };

        FloatingActionButton btnAdd = viewLayout.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UpdateScheduleActivity.class);
                startActivity(intent);
            }
        });

        lst.setLayoutManager(layoutManager);
        lst.setAdapter(adapter);

        viewLayout.findViewById(R.id.btnGcal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CalendarSampleActivity.class));
            }
        });

        return viewLayout;
    }

    private void update(){
        adapter.dataList.clear();
        adapter.dataList.addAll(ScheduleManager.data.values());
        Collections.sort(adapter.dataList, new Comparator<Schedule>() {
            @Override
            public int compare(Schedule o1, Schedule o2) {
                return (int)(o1.start - o1.start)* -1;
            }
        });
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }
}

