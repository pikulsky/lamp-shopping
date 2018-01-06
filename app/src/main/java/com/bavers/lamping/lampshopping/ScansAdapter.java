package com.bavers.lamping.lampshopping;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Adapter for scans list in HistoryActivity
 */


public class ScansAdapter extends RecyclerView.Adapter<ScansAdapter.MyViewHolder> {

    private List<Scan> scanList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
        }
    }


    public ScansAdapter(List<Scan> scanList) {
        this.scanList = scanList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.scans_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Scan scan = scanList.get(position);
        holder.title.setText(scan.barcode);
        holder.genre.setText(scan.barcode);
        holder.year.setText(scan.barcode);
    }

    @Override
    public int getItemCount() {
        return scanList.size();
    }
}
