package sam.com.noteapp.listeners;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by sam on 2/8/16.
 */
public interface OnListItemClickListener {
    public void onClick(View view, int position);
    public void onLongPressClick(View view, int position);
}
