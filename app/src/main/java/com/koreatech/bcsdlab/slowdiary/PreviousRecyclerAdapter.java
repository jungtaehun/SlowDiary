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

public class PreviousRecyclerAdapter extends RecyclerView.Adapter<PreviousRecyclerAdapter.SimpleViewHolder> {

    private Cursor mCursor;
    CustomCursorAdapter mCursorAdapter;
    Context mContext;
    static final int TYPE_CELL = 0;
    boolean mDataValid;
    protected int mRowIDColumn;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    class SimpleViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title;

        public SimpleViewHolder (View itemView)
        {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.ltitle);
        }
    }

    private class CustomCursorAdapter extends CursorAdapter {
        public CustomCursorAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }

        @Override
        public int getItemViewType(int position) {
            return TYPE_CELL;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View v = null;

            int type = getItemViewType(cursor.getPosition());
            RecyclerView.ViewHolder viewHolder = null;

            switch (type) {
                case TYPE_CELL:
                    v = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.list_item_card_small, parent, false);
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
                case TYPE_CELL:

                    SimpleViewHolder holder0 = (SimpleViewHolder) view.getTag();
                    String open_date = cursor.getString(cursor.getColumnIndex(TestDb.ODATE));
                    Date date = new Date();
                    try {
                        date = df.parse(open_date);
                    } catch (ParseException e) {
                        Log.e(TAG, "Parsing ISO8601 datetime failed", e);
                    }
                    Date now = new Date();
                    int diffInDays = (int)( (now.getTime() - date.getTime())
                            / (1000 * 60 * 60 * 24) );
                    holder0.title.setText("D - " + String.valueOf(diffInDays));
                    break;
            }

        }


    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mCursorAdapter.newView(mContext, mCursor, parent);
        SimpleViewHolder viewHolder = null;
        switch (viewType){
            case TYPE_CELL:
                viewHolder = new SimpleViewHolder(v);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        mCursorAdapter.bindView(holder.itemView, mContext, mCursor);
    }

    @Override
    public int getItemCount() {
        if (mCursor != null)
            return mCursor.getCount();
        else return 0;
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

    public void setDataSet(Context context, Cursor cur) {
        mCursor = cur;
        mContext = context;
        mCursorAdapter = new CustomCursorAdapter(mContext, mCursor, 0);
    }
}

