package com.koreatech.bcsdlab.slowdiary.temp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.koreatech.bcsdlab.slowdiary.R;

import java.util.List;

public class TestRecyclerViewAdapter extends RecyclerView.Adapter<TestRecyclerViewAdapter.MyViewHolder> {

    List<DiaryItem> contents;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    public TestRecyclerViewAdapter(List<DiaryItem> contents) {
        this.contents = contents;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, content;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.ltitle);
            content = (TextView) view.findViewById(R.id.lcontent);
        }
    }

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
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        switch (viewType) {
            case TYPE_HEADER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_big, parent, false);
                return new MyViewHolder(view) {
                };
            }
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_small, parent, false);
                return new MyViewHolder(view) {
                };
            }
        }
        return null;
    }

    public void onBindViewHolder(final MyViewHolder holder, int position) {

        DiaryItem diary = contents.get(position);

        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                holder.title.setText(diary.getTitle());
                holder.content.setText(diary.getContent());
                break;
            case TYPE_CELL:
                holder.title.setText(diary.getTitle());
                break;
        }
    }
}