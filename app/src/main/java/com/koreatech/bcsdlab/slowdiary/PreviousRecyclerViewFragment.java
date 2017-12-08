package com.koreatech.bcsdlab.slowdiary;

import android.database.Cursor;
import android.os.AsyncTask;
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

public class PreviousRecyclerViewFragment extends Fragment {
    View vw;
    private static final boolean GRID_LAYOUT = false;
    private static final int ITEM_COUNT = 10;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    PreviousRecyclerAdapter mAdapter;

    public static PreviousRecyclerViewFragment newInstance() {
        return new PreviousRecyclerViewFragment();
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
        /*

        mAdapter = new FutureRecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);
        new LoadTestDbTask().execute();
        */
        mAdapter = new PreviousRecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);
        new LoadTestDbTask().execute();
    }

    public void updateView(){
        Cursor c = new TestDb(getActivity()).getPrevious();
        mAdapter.swapCursor(c);
        mAdapter.notifyDataSetChanged();
    }

    public class LoadTestDbTask extends AsyncTask<Void, Void, Cursor> {

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */

        @Override
        protected Cursor doInBackground(Void... params) {
            Cursor cursor = new TestDb(getActivity()).getPrevious();
            mAdapter.setDataSet(getContext(),cursor);
            return cursor;
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p/>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param cursor The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            mAdapter.swapCursor(cursor);
            mAdapter.notifyDataSetChanged();
        }
    }

}