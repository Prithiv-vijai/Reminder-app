package com.example.dailyassist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {

    private Context mContext;
    private List<Reminder> mRemindersList;
    private OnReminderEditListener mEditListener;
    private OnReminderDeleteListener mDeleteListener;

    public ReminderAdapter(Context context, List<Reminder> remindersList,
                           OnReminderEditListener editListener,
                           OnReminderDeleteListener deleteListener) {
        mContext = context;
        mRemindersList = remindersList;
        mEditListener = editListener;
        mDeleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_reminder, parent, false);
        return new ReminderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderViewHolder holder, int position) {
        Reminder reminder = mRemindersList.get(position);
        holder.bind(reminder); // Bind the reminder data to the ViewHolder
    }

    @Override
    public int getItemCount() {
        return mRemindersList.size();
    }

    public void setReminders(List<Reminder> remindersList) {
        mRemindersList = remindersList;
        notifyDataSetChanged();
    }

    public class ReminderViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView dateTextView;
        private TextView timeTextView;

        public ReminderViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);

            // Set long click listener to delete the reminder
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Reminder reminder = mRemindersList.get(position);
                        if (mDeleteListener != null) {
                            mDeleteListener.onReminderDelete(reminder); // Pass the reminder to the listener
                        }
                        return true;
                    }
                    return false;
                }
            });
        }

        public void bind(Reminder reminder) {
            // Bind reminder data to the ViewHolder views
            titleTextView.setText(reminder.getTitle());
            dateTextView.setText(reminder.getDate());
            timeTextView.setText(reminder.getTime());
        }
    }

    public interface OnReminderEditListener {
        void onReminderEdit(Reminder reminder);
    }

    public interface OnReminderDeleteListener {
        void onReminderDelete(Reminder reminder);
    }
}
