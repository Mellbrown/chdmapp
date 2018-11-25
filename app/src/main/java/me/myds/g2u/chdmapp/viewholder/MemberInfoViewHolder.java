package me.myds.g2u.chdmapp.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import me.myds.g2u.chdmapp.R;

public class MemberInfoViewHolder extends RecyclerView.ViewHolder {

    public TextView txtName;
    public TextView txtMajor;
    public TextView txtDept;

    public MemberInfoViewHolder(@NonNull View itemView) {
        super(itemView);

        txtName  = itemView.findViewById(R.id.txtName);
        txtMajor  = itemView.findViewById(R.id.txtMajor);
        txtDept  = itemView.findViewById(R.id.txtDept);
    }
}
