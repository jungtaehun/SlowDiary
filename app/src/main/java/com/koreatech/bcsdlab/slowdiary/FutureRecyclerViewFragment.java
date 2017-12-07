package com.koreatech.bcsdlab.slowdiary;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FutureRecyclerViewFragment  extends Fragment {
    View vw;
    private static final boolean GRID_LAYOUT = false;
    private static final int ITEM_COUNT = 10;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    FutureRecyclerAdapter mAdapter;

    public static FutureRecyclerViewFragment newInstance() {
        return new FutureRecyclerViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vw = view.findViewById(R.id.drawer_layout);
        ButterKnife.bind(this, view);

        if (GRID_LAYOUT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        mRecyclerView.setHasFixedSize(true);

        //Use this now
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        Cursor cursor = new TestDb(getActivity()).getFuture();
        mAdapter = new FutureRecyclerAdapter(getContext(), cursor);
        mAdapter.setDataSet(cursor);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}