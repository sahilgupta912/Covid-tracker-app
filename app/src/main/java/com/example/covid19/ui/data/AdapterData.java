package com.example.covid19.ui.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19.R;

import java.util.List;

public class AdapterData extends RecyclerView.Adapter <AdapterData.holder>{

    private final List<ModelClassDataRecycler> stateList;


    public AdapterData(List<ModelClassDataRecycler> stateList) {
        this.stateList=stateList;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.rv_state_single_row,parent,false);

        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

        String res1=stateList.get(position).getTxtState();
        String res2=stateList.get(position).getTxtStateConfirm();
        String res3=stateList.get(position).getTxtStateRecover();
        String res4=stateList.get(position).getTxtStateDeceased();

        holder.setData(res1,res2,res3,res4);

    }

    @Override
    public int getItemCount() {
        return stateList.size();
    }


    class holder extends RecyclerView.ViewHolder{
        TextView txtState;
        TextView txtStateConfirmed;
        TextView txtStateRecovered;
        TextView txtStateDeceased;

        public holder(@NonNull View itemView) {
            super(itemView);

            txtState=itemView.findViewById(R.id.txtState);
            txtStateConfirmed=itemView.findViewById(R.id.txtStateConfirmed);
            txtStateRecovered=itemView.findViewById(R.id.txtStateRecovered);
            txtStateDeceased=itemView.findViewById(R.id.txtStateDeceased);
        }

        public void setData(String res1, String res2, String res3, String res4) {
            if(res1.contains("Kerala"))
                res1="Kerala";
            txtState.setText(res1);
            txtStateConfirmed.setText(res2);
            txtStateRecovered.setText(res3);
            txtStateDeceased.setText(res4);
        }
    }
}
