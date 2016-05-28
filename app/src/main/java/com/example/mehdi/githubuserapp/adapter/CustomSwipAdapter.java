package com.example.mehdi.githubuserapp.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.mehdi.githubuserapp.R;

/**
 * Created by mehdi on 3/15/16.
 */
public class CustomSwipAdapter extends PagerAdapter {

    private int[] imageSources = {R.drawable.sample_1, R.drawable.sample_2, R.drawable.sample_3, R.drawable.sample_4, R.drawable.sample_5, R.drawable.sample_6 };
    private Context context;
    private LayoutInflater inflater;

    public CustomSwipAdapter(Context context) {
        this.context = context;
    }
    @Override
    public int getCount() {
        return imageSources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = inflater.inflate(R.layout.swip_layout, container, false);
        ImageView imgView = (ImageView) item_view.findViewById(R.id.image_view);
        imgView.setImageResource(imageSources[position]);
        container.addView(item_view);

        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
