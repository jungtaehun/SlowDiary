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
/* Todo
* 현재 큰 CardView와 작은 CardView 선택 기준은 getItemViewType에서 임의로 설정한 position에 의해 결정된다.
* 하지만 이제는 OpenDate와 비교해 오픈되었는지의 여부로 결정해주어야 한다.
* */
    @Override
    public int getItemViewType(int position) {
        mCursor.moveToPosition(position);
        mCursor.getColumnIndex(TestDb.ODATE);
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
/* Todo
* 현재 CardView의 UI 디자인이 되어있지 않은 상태이기에 이 부분의 디자인 수정이 필요함.
* 수정해야할 CardView는 list_item_card_big, list_item_card_small 둘이다.
* */
    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int position) {
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
                viewHolder.title.setText(mCursor.getString(mCursor.getColumnIndex(TestDb.ODATE)));
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
    public TextView title, content;

    public SimpleViewHolder (View itemView)
    {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.ltitle);
        content = (TextView) itemView.findViewById(R.id.lcontent);
    }
}
