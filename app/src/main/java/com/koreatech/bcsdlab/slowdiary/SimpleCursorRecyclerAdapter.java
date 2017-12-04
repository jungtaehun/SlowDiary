package com.koreatech.bcsdlab.slowdiary;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SimpleCursorRecyclerAdapter extends RecyclerView.Adapter<SimpleViewHolder> {

    private Cursor mCursor;
    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
            case 2:
            case 3:
            case 5:
            case 7:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        //SimpleViewHolder vh = new SimpleViewHolder(new TextView(parent.getContext()));
        View view = null;

        switch ( getItemViewType(position)) {
            case TYPE_HEADER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_big, parent, false);
                return new SimpleViewHolder(view) {
                };
            }
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_small, parent, false);
                return new SimpleViewHolder(view) {
                };
            }
        }
        return null;
        //return vh;
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder viewHolder, int position) {
        mCursor.moveToPosition(position);
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                viewHolder.title.setText(mCursor.getString(mCursor.getColumnIndex(TestDb.TITLE)));
                viewHolder.content.setText(mCursor.getString(mCursor.getColumnIndex(TestDb.CONTENT)));
                break;
            case TYPE_CELL:
                viewHolder.title.setText(mCursor.getString(mCursor.getColumnIndex(TestDb.TITLE)));
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mCursor != null)
            return mCursor.getCount();
        else return 0;
    }

    /**
     * set DataBase Cursor for Recycler Adapter
     * @param cur
     */
    public void setDataSet(Cursor cur) { mCursor = cur; }
}

class SimpleViewHolder extends RecyclerView.ViewHolder
{
    //public TextView[] views;
    public TextView title, content;

    public SimpleViewHolder (View itemView)
    {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.ltitle);
        content = (TextView) itemView.findViewById(R.id.lcontent);
        /*
        views = new TextView[to.length];
        for(int i = 0 ; i < to.length ; i++) {
            views[i] = (TextView) itemView.findViewById(to[i]);
        }
        */
    }
}
