package com.sixbits.androvisionocv.ui.database_activity.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sixbits.androvisionocv.R;
import com.sixbits.androvisionocv.entity.AttendanceLog;
import com.sixbits.androvisionocv.ui.database_activity.DatabaseExploreInterface;

import java.util.List;

public class LogListAdapter extends RecyclerView.Adapter<LogListAdapter.LogViewHolder> {
    private static final String TAG = "ML_LogListAdapter";
    class LogViewHolder extends RecyclerView.ViewHolder {
        private final TextView logItemView;

        private LogViewHolder(View itemView) {
            super(itemView);
            logItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<AttendanceLog> mLogs; // Cached copy of words
    private DatabaseExploreInterface exploreInterface;

    public LogListAdapter(DatabaseExploreInterface databaseInterface, Context context) {
        mInflater = LayoutInflater.from(context);
        exploreInterface = databaseInterface;
    }

    @Override
    public LogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_log_item, parent, false);
        return new LogViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LogViewHolder holder, int position) {
        if (mLogs != null) {
            AttendanceLog current = mLogs.get(position);
            holder.logItemView.setText(current.getDate());
            holder.logItemView.setOnClickListener((v) -> exploreInterface.goToDetails(current.getId()));
        } else {
            // Covers the case of data not being ready yet.
            holder.logItemView.setText("Record Error");
        }
    }

    public void setLogs(List<AttendanceLog> logs) {
        mLogs = logs;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mLogs has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mLogs != null)
            return mLogs.size();
        else return 0;
    }
}
