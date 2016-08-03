package com.sam.noteapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.sam.noteapp.R;
import com.sam.noteapp.listeners.OnListItemClickListener;
import com.sam.noteapp.pojo.Notes;

/**
 * Created by sam on 2/8/16.
 */
public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ListViewHolder> {
    private static final String TAG = "HomeListAdapter";
    private OnListItemClickListener onListItemClickListener;
    private List<Notes> notesList;

    public void setNotesList(List<Notes> notesList) {
        this.notesList = notesList;
    }

    @Override

    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, final int position) {
        holder.header.setText(notesList.get(position).getHeader());
        holder.note.setText(notesList.get(position).getNote());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onListItemClickListener.onClick(view, notesList.get(position).getId());
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.i(TAG, "onLongClick: ");
                onListItemClickListener.onLongPressClick(view, notesList.get(position).getId());
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public void setOnListItemClickListener(OnListItemClickListener onListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView header;
        private TextView note;

        public ListViewHolder(View itemView) {
            super(itemView);
            header = (TextView) itemView.findViewById(R.id.header_text);
            note = (TextView) itemView.findViewById(R.id.content_text);
        }
    }
}
