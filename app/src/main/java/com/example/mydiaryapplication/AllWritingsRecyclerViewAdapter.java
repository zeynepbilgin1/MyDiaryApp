package com.example.mydiaryapplication;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
public class AllWritingsRecyclerViewAdapter extends RecyclerView.Adapter<AllWritingsRecyclerViewAdapter.ListViewHolder>{
        Context context;
        List<Writing> writings;
        AllWritingsActivity activity;
        OnDeleteClickListener listener;

    public interface OnDeleteClickListener {
        void onDelete(Writing writing);
    }

    public AllWritingsRecyclerViewAdapter(Context context, List<Writing> writings, AllWritingsActivity activity, OnDeleteClickListener listener) {
            this.context = context;
            this.writings = writings;
            this.activity = activity;
            this.listener = listener;
    }

    @NonNull
    @Override
    public AllWritingsRecyclerViewAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row,parent,false);
        return new AllWritingsRecyclerViewAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllWritingsRecyclerViewAdapter.ListViewHolder holder, int position) {
        Writing current = writings.get(position);
        holder.date.setText(current.date.toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DisplayActivity.class);
                intent.putExtra("writing", current);
                view.getContext().startActivity(intent);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onDelete(current);
                }
            }
        });
    }

    @Override
    public int getItemCount(){
            return writings.size();
        }
    public static class ListViewHolder extends RecyclerView.ViewHolder{
        TextView date;
        ImageButton btnDelete;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            btnDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
