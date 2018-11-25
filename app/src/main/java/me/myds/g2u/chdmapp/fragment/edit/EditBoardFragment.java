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
import me.myds.g2u.chdmapp.activity.update.UpdateBoardActivity;
import me.myds.g2u.chdmapp.model.bean.Board;
import me.myds.g2u.chdmapp.model.bean.Schedule;
import me.myds.g2u.chdmapp.model.manager.BoardManager;
import me.myds.g2u.chdmapp.model.manager.ScheduleManager;
import me.myds.g2u.chdmapp.viewholder.BoarderViewHolder;
import me.myds.g2u.chdmapp.viewholder.MemberInfoViewHolder;

public class EditBoardFragment extends Fragment {

    RecyclerView lst;
    RecyclerView.LayoutManager layoutManager;
    BaseRecyclerAdapter<Board, BoarderViewHolder> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewLayout = inflater.inflate(R.layout.fragment_edit_board, container,false);

        lst = viewLayout.findViewById(R.id.lst);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new BaseRecyclerAdapter<Board, BoarderViewHolder>(
                R.layout.item_board, BoarderViewHolder.class
        ) {
            @Override
            public void dataConvertViewHolder(BoarderViewHolder holder, Board data) {
                holder.txtTitle.setText(data.title);
                holder.txtDesc.setText(
                        new SimpleDateFormat("yyyy-MM-dd")
                                .format(new Date(data.createTime)) +
                                " " + String.format("%30s",data.content)
                );
            }
            @Override
            public void onCreateAfterViewHolder(final BoarderViewHolder holder) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(),UpdateBoardActivity.class);
                        Board board = dataList.get(holder.getLayoutPosition());
                        intent.putExtra("uid", board.uid);
                        startActivity(intent);
                    }
                });
            }
        };

        FloatingActionButton btnAdd = viewLayout.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UpdateBoardActivity.class);
                startActivity(intent);
            }
        });

        lst.setLayoutManager(layoutManager);
        lst.setAdapter(adapter);

        return viewLayout;
    }

    private void update(){
        adapter.dataList.clear();
        adapter.dataList.addAll(BoardManager.data.values());
        Collections.sort(adapter.dataList, new Comparator<Board>() {
            @Override
            public int compare(Board o1, Board o2) {
                return (int)(o1.createTime - o2.createTime) * -1;
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

