package me.myds.g2u.chdmapp.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import me.myds.g2u.chdmapp.R;

public class ScheduleViewHolder extends RecyclerView.ViewHolder {

    public TextView txtTitle;
    public TextView txtDate;

    public ScheduleViewHolder(@NonNull View itemView) {
        super(itemView);

        txtTitle = itemView.findViewById(R.id.txtTitle);
        txtDate = itemView.findViewById(R.id.txtDate);
    }
}
