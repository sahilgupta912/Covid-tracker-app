package com.example.covid19.ui.notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.holder> {

    private final List<ModelClassNotificationRecycler> newsList;

    public AdapterNotification(ArrayList<ModelClassNotificationRecycler> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.rv_news_single_row,parent,false);

        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

        String res1=newsList.get(position).getTxtNews();
        String res2=newsList.get(position).getTxtDate();

        holder.setData(res1,res2);


    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class holder extends RecyclerView.ViewHolder
    {
        TextView txtNews;
        TextView txtDate;
        public holder(@NonNull View itemView) {
            super(itemView);
            txtNews=itemView.findViewById(R.id.txtNews);
            txtDate=itemView.findViewById(R.id.txtNewsPublishedDate);

        }

        public void setData(String res1, String res2){
            txtNews.setText(res1);
            txtDate.setText(res2);
        }
    }
}
