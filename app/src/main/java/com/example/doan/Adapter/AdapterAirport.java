package com.example.doan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.R;
import com.example.doan.model.Airports;
import com.example.doan.my_interface.IClickItemAirport;

import java.util.ArrayList;
import java.util.List;

public class AdapterAirport extends RecyclerView.Adapter<AdapterAirport.ViewHolder> implements Filterable {
    public List<Airports> lstAirport;
    public List<Airports> mLstAirport;
    private IClickItemAirport iClickItemAirport;
    public AdapterAirport(List<Airports> lstAirports, IClickItemAirport listener){
        lstAirport = lstAirports;
        mLstAirport = lstAirport;
        this.iClickItemAirport = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View airportView= inflater.inflate(R.layout.item_airport,parent,false);
        ViewHolder viewHolder = new ViewHolder(airportView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Airports temp = lstAirport.get(position);
        holder.txtCode.setText(temp.airportCode);
        holder.txtCity.setText(temp.City);
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemAirport.onAirportClick(temp);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstAirport.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout layoutItem;
        TextView txtCity;
        TextView txtCode;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            layoutItem = (RelativeLayout) itemView.findViewById(R.id.layoutItem);
            txtCity = (TextView) itemView.findViewById(R.id.txtcity);
            txtCode =(TextView) itemView.findViewById(R.id.txtAirportCode);
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty()){
                    lstAirport = mLstAirport;
                }
                else{
                    List<Airports> list= new ArrayList<>();
                    for(Airports airports : mLstAirport){
                        if(airports.getCity().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(airports);
                        }
                    }
                    lstAirport = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = lstAirport;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                lstAirport = (List<Airports>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
