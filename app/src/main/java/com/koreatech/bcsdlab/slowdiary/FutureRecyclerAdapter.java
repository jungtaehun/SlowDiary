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

    public FutureRecyclerAdapter(Context context, Cursor c) {
        mContext = context;
        mCursorAdapter = new CustomCursorAdapter(mContext, c, 0);
    }

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

    /* Todo
* 현재 큰 CardView와 작은 CardView 선택 기준은 getItemViewType에서 임의로 설정한 position에 의해 결정된다.
* 하지만 이제는 OpenDate와 비교해 오픈되었는지의 여부로 결정해주어야 한다.
* */
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
            Log.e(TAG, cursor.getString(cursor.getColumnIndex(TestDb.CONTENT)));
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
        View v = mCursorAdapter.newView(mContext, mCursorAdapter.getCursor(), parent);
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
        Log.e(TAG, String.valueOf(position));
        mCursorAdapter.getCursor().moveToPosition(position);
        //Log.e(TAG, String.valueOf(mCursorAdapter.getCursor()));
        mCursorAdapter.bindView(holder.itemView, mContext, mCursorAdapter.getCursor());
    }

    @Override
    public int getItemCount() {
        if (mCursor != null)
            return mCursor.getCount();
        else return 0;
    }


/*
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mCursorAdapter.getCursor().moveToPosition(position);
        mCursorAdapter.bindView(holder.itemView, mContext, mCursorAdapter.getCursor());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mCursorAdapter.newView(mContext, mCursorAdapter.getCursor(), parent);
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case TYPE_HEADER:
                viewHolder = new HeaderViewHolder(v);
                break;
            case TYPE_CELL:
                viewHolder = new CellViewHolder(v);
                break;
        }

        return viewHolder;
    }*/
/*
    @Override
    public int getItemViewType(int position) {
        Cursor cursor = (Cursor) mCursorRecyclerAdapter.getItem(position);
        cursor.moveToNext();
        Date date = new Date();
        String open_date = mCursor.getString(mCursor.getColumnIndex(TestDb.ODATE));
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = df.parse(open_date);
        } catch (ParseException e) {
            Log.e(TAG, "Parsing ISO8601 datetime failed", e);
        }
        Date now = new Date();
        long diffInDays = now.getTime() - date.getTime();
        if(diffInDays < 0.0) {
            return TYPE_CELL;
        } else {
            return TYPE_HEADER;
        }
    }*/
/* Todo
* 현재 CardView의 UI 디자인이 되어있지 않은 상태이기에 이 부분의 디자인 수정이 필요함.
* 수정해야할 CardView는 list_item_card_big, list_item_card_small 둘이다.
* */
/*
    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = null;
        switch ( getItemViewType(position)) {
            case TYPE_HEADER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_big, parent, false);
                return new SimpleViewHolder(view);
            }
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_small, parent, false);
                return new SimpleViewHolder(view);
            }
        }

        return null;
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
                viewHolder.title.setText("D - " + String.valueOf(2));
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mCursor != null)
            return mCursor.getCount();
        else return 0;
    }
*/
    /**
     * set DataBase Cursor for Recycler Adapter
     * @param cur
     */
    public void setDataSet(Cursor cur) { mCursor = cur;}
}
/*
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
*/

