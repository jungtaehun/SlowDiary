package com.koreatech.bcsdlab.slowdiary;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class FutureRecyclerAdapter extends RecyclerView.Adapter<FutureRecyclerAdapter.SimpleViewHolder> {

    private Cursor mCursor;
    CustomCursorAdapter mCursorAdapter;
    Context mContext;
    static final int TYPE_HEADER = 0;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    boolean mDataValid;
    protected int mRowIDColumn;

    class SimpleViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title, content;

        public SimpleViewHolder (View itemView)
        {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.ltitle);
            content = (TextView) itemView.findViewById(R.id.lcontent);
        }
    }

    private class CustomCursorAdapter extends CursorAdapter {
        public CustomCursorAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }

        @Override
        public int getItemViewType(int position) {
            return TYPE_HEADER;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View v = null;

            int type = getItemViewType(cursor.getPosition());
            RecyclerView.ViewHolder viewHolder = null;

            switch (type) {
                case TYPE_HEADER:
                    v = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.list_item_card_big, parent, false);
                    viewHolder = new SimpleViewHolder(v);
                    break;
            }
            assert v != null;
            v.setTag(viewHolder);
            return v;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            final int viewType = getItemViewType(cursor.getPosition());

            switch (viewType) {
                case TYPE_HEADER:
                    SimpleViewHolder holder = (SimpleViewHolder) view.getTag();
                    holder.title.setText(cursor.getString(cursor.getColumnIndex(TestDb.TITLE)));
                    holder.content.setText(cursor.getString(cursor.getColumnIndex(TestDb.CONTENT)));
                    break;
            }

        }


    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mCursorAdapter.newView(mContext, mCursor, parent);
        SimpleViewHolder viewHolder = null;
        switch (viewType){
            case TYPE_HEADER:
                viewHolder = new SimpleViewHolder(v);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        //Log.e(TAG, String.valueOf(mCursorAdapter.getCursor()));
        mCursorAdapter.bindView(holder.itemView, mContext, mCursor);
    }

    @Override
    public int getItemCount() {
        if (mCursor != null)
            return mCursor.getCount();
        else return 0;
    }


    public void setDataSet(Context context, Cursor cur) {
        mCursor = cur;
        mContext = context;
        mCursorAdapter = new CustomCursorAdapter(mContext, mCursor, 0);
    }

    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }
        Cursor oldCursor = mCursor;
        mCursor = newCursor;
        if (newCursor != null) {
            mRowIDColumn = newCursor.getColumnIndexOrThrow("_id");
            mDataValid = true;
            // notify the observers about the new cursor
            notifyDataSetChanged();
        } else {
            mRowIDColumn = -1;
            mDataValid = false;
            // notify the observers about the lack of a data set
            notifyItemRangeRemoved(0, getItemCount());
        }
        return oldCursor;
    }
}