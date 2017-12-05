package com.koreatech.bcsdlab.slowdiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.koreatech.bcsdlab.slowdiary.temp.DrawerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends DrawerActivity {

    @BindView(R.id.materialViewPager)
    MaterialViewPager mViewPager;
    View vw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");
        ButterKnife.bind(this);

        final Toolbar toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        vw = findViewById(R.id.drawer_layout);
        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position % 2) {
                    /* TODO
                    *  현재 세 개의 Fragment에서 똑같은 Adapter를 받기에 모든 Fragment에 똑같은 내용이 출력된다.
                    *  하지만 이제는 position이 0일 경우 즐겨찾기한 Diary를, 1일때는 열린 Diary를, 2일때는 열리지 않은 Diary를 보여줘야한다.
                    *  (현재 시간상으로 Fragment를 축소하여 0일 때는 열린 Diary를, 2일때는 열리지 않은 Diary를 보여주게 한다.)
                    *  또한 열린 Diary의 경우는 큰 CardView(TYPE_HEADER)로, 열리지 않은 Diary는 작은 CardView(TYPE_CELL)을 사용한다.
                    *  (현재 이 부분은 RecyclerViewFragment, SimpleCursorRecyclerAdapter가 담당하고 있음.)
                    *  (또한 이 부분을 구현하기 위해 DB 테이블의 수정이 필요할 수도 있으며, 추가적인 Fragment가 필요할수도 있음.)
                    * */
                    //case 0:
                    //    return RecyclerViewFragment.newInstance();
                    //case 1:
                    //    return RecyclerViewFragment.newInstance();
                    //case 2:
                    //    return WebViewFragment.newInstance();
                    default:
                        return RecyclerViewFragment.newInstance();
                }
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 2) {
                    case 0:
                        return "오픈된 일기";
                    case 1:
                        return "보관중인 일기";
                }
                return "";
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            /* TODO
            *  현재 헤더 디자인을 테스트 이미지로 설정하였으나, 이제는 그에 맞게 헤더 이미지를 찾아보는 것이 좋을 것 같다.
            *  이미지는 Drawable과 link를 모두 지원한다.
            * */
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.green,
                                getResources().getDrawable(R.drawable.choco));
                    case 1:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.blue,
                                getResources().getDrawable(R.drawable.choco));
                }

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddDiaryActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        final View logo = findViewById(R.id.logo_white);
        if (logo != null) {
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
                    Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
                    Snackbar.make(v, "Yes, the title is clickable", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                }
            });
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1:
                String title = data.getStringExtra("Title");
                String content = data.getStringExtra("Content");
                Snackbar.make(vw , "제목 : " + title + ", 내용 : " + content, Snackbar.LENGTH_LONG).setAction("Action", null).show();

                /* TODO
                *   AddDiaryActivity에서 완료 result를 받을 때, 새로고침해주기.
                *   (현재 새로고침 부분은 RecyclerViewFragment의 LoadTestDbTask 클래스가 담당하고 있음.)
                * */

                break;
            default:
                Snackbar.make(vw , "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                break;
        }
    }





}
