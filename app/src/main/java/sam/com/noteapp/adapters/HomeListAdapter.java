package sam.com.noteapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sam.com.noteapp.R;
import sam.com.noteapp.listeners.OnListItemClickListener;

/**
 * Created by sam on 2/8/16.
 */
public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ListViewHolder> {
    private OnListItemClickListener onListItemClickListener;
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, final int position) {
        holder.header.setText("Header");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onListItemClickListener.onClick(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public void setOnListItemClickListener(OnListItemClickListener onListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView header;

        public ListViewHolder(View itemView) {
            super(itemView);
            header = (TextView) itemView.findViewById(R.id.header_text);
        }
    }
}
