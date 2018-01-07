package com.bavers.lamping.lampshopping;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * Adapter for scans list in HistoryActivity
 */


public class ScansAdapter extends RecyclerView.Adapter<ScansAdapter.ScanViewHolder> {

    private List<Scan> scanList;

    public class ScanViewHolder extends RecyclerView.ViewHolder {
        public TextView lamp_rate;
        public TableLayout lampRateTableLayout;
        public TextView lamp_title;
        public TextView lamp_description;

        public ScanViewHolder(View view) {
            super(view);
            lamp_rate = (TextView) view.findViewById(R.id.lamp_rate);
            lampRateTableLayout = (TableLayout) view.findViewById(R.id.LampRateTableLayout);
            lamp_title = (TextView) view.findViewById(R.id.lamp_title);
            lamp_description = (TextView) view.findViewById(R.id.lamp_description);

        }
    }


    public ScansAdapter(List<Scan> scanList) {
        this.scanList = scanList;
    }

    @Override
    public ScanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.scans_list_row, parent, false);

        return new ScanViewHolder(itemView);
    }

    private String formatDate(Date date) {
        return DateFormat.getDateInstance(DateFormat.SHORT).format(date);
    }

    @Override
    public void onBindViewHolder(ScanViewHolder holder, int position) {
        Scan scan = scanList.get(position);
        if (scan.foundLamps.isEmpty()) {
            holder.lamp_rate.setText("NA");
            holder.lamp_title.setText("Lamp for " + scan.barcode + " not found");
            holder.lamp_description.setText(formatDate(scan.scanDate));
        } else {
            Lamp lamp = scan.foundLamps.iterator().next();
            holder.lamp_rate.setText(lamp.rating);
            holder.lamp_title.setText(lamp.brand + " " + lamp.model);
            holder.lamp_description.setText(scan.barcode + " " + formatDate(scan.scanDate));
            String color = lamp.getRateColor();
            holder.lampRateTableLayout.setBackgroundColor(Color.parseColor(color));
            //holder.lamp_rate.setBackgroundColor(Color.parseColor(color));
        }
    }

    @Override
    public int getItemCount() {
        return scanList.size();
    }
}
