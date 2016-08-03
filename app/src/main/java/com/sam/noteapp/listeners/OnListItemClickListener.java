package com.sam.noteapp.listeners;

import android.view.View;

/**
 * Created by sam on 2/8/16.
 */
public interface OnListItemClickListener {
    public void onClick(View view, int noteId);
    public void onLongPressClick(View view, int noteId);
}
