package com.example.imageslider;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //    private ActivityMainBinding binding;
    private ViewPager2 viewPager2;
    private List<Image> imageList;
    private ImageAdapter adapter;
    private Handler sliderHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager2 = findViewById(R.id.viewPager2);
        imageList = new ArrayList<>();


        imageList.add(new Image(R.drawable.pexels4));
        imageList.add(new Image(R.drawable.pexels1));
        imageList.add(new Image(R.drawable.pexels1));
        imageList.add(new Image(R.drawable.pexels1));
        imageList.add(new Image(R.drawable.pexels3));
        imageList.add(new Image(R.drawable.pexels3));
        imageList.add(new Image(R.drawable.pexels3));
        imageList.add(new Image(R.drawable.pexels4));

        adapter = new ImageAdapter(imageList, viewPager2);
        viewPager2.setAdapter(adapter);

        viewPager2.setOffscreenPageLimit(3);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);

        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.14f);
            }
        });

        viewPager2.setPageTransformer(transformer);


        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            public void onPageSelected(int position) {
                sliderHandler.removeCallbacks(slideRunnable);
                sliderHandler.postDelayed(slideRunnable, 2000);
            }
        });
    }

    private Runnable slideRunnable = new Runnable() {

        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }

    };

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(slideRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(slideRunnable, 2000);
    }
}