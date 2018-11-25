package me.myds.g2u.chdmapp.fragment.read;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import me.myds.g2u.chdmapp.BaseRecyclerAdapter;
import me.myds.g2u.chdmapp.R;
import me.myds.g2u.chdmapp.model.bean.Board;
import me.myds.g2u.chdmapp.model.bean.MemberInfo;
import me.myds.g2u.chdmapp.model.bean.Schedule;
import me.myds.g2u.chdmapp.model.manager.BoardManager;
import me.myds.g2u.chdmapp.model.manager.MemeberInfoManager;
import me.myds.g2u.chdmapp.model.manager.ScheduleManager;
import me.myds.g2u.chdmapp.viewholder.BoarderViewHolder;
import me.myds.g2u.chdmapp.viewholder.MemberInfoViewHolder;

public class ReadMemberInfoFragment extends Fragment {

    RecyclerView lst;
    RecyclerView.LayoutManager layoutManager;
    BaseRecyclerAdapter<MemberInfo, MemberInfoViewHolder> adapter;

    public SearchView searchView;
    public String queryString = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewLayout = inflater.inflate(R.layout.fragment_read_member_info, container,false);

        lst = viewLayout.findViewById(R.id.lst);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new BaseRecyclerAdapter<MemberInfo, MemberInfoViewHolder>(
                R.layout.item_memberinfo, MemberInfoViewHolder.class
        ) {
            @Override
            public void dataConvertViewHolder(MemberInfoViewHolder holder, MemberInfo data) {

                holder.txtName.setText(data.name);
                holder.txtMajor.setText(String.format("(%s) %s", data.year, data.major));
                holder.txtDept.setText(String.format("[%s] %s-%s",data.boarder,data.dept,data.position));
            }
        };

        lst.setLayoutManager(layoutManager);
        lst.setAdapter(adapter);

        searchView = viewLayout.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                queryString = s;
                InputMethodManager imm =  (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                update();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                queryString = s;
                if(queryString.length() > 10 ){
                    Toast.makeText(getContext(), "검색 글자 수는 10을 넘길 수 없습니다.", Toast.LENGTH_SHORT).show();
                    return false;
                }
                update();
                return true;
            }
        });

        return viewLayout;
    }

    private void update(){
        adapter.dataList.clear();
        if(queryString == "")
            adapter.dataList.addAll(MemeberInfoManager.data.values());
        else
            for(MemberInfo memberInfo : MemeberInfoManager.data.values()){
                if(memberInfo.name.matches(String.format(".*%s.*",queryString)))
                    adapter.dataList.add(memberInfo);

            }
        Collections.sort(adapter.dataList, new Comparator<MemberInfo>() {
            @Override
            public int compare(MemberInfo o1, MemberInfo o2) {
                return o1.name.compareTo(o2.name);
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

