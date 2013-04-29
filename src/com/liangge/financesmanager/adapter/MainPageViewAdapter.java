package com.liangge.financesmanager.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * User: liangge
 * Date: 13-4-29
 * Time: 上午10:09
 */
public class MainPageViewAdapter extends PagerAdapter{
    private List<View> pageViewList;
    public MainPageViewAdapter(List<View> pageViewList){
        this.pageViewList = pageViewList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(pageViewList.get(position), 0);
        return pageViewList.get(position);
    }

    @Override
    public int getCount() {
        return pageViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(pageViewList.get(position));
    }
}
