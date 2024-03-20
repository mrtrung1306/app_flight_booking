package com.example.doan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.Adapter.AdapterNew;
import com.example.doan.Adapter.AdapterPost;
import com.example.doan.model.News;
import com.example.doan.model.Post;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    int temp =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ViewPager2 mViewPager2 = findViewById(R.id.viewPager);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId==R.id.home){

                }
                else{
                    Intent intent = new Intent(MainActivity.this,BookingActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });

        AppBarLayout appBarLayout = findViewById(R.id.app_bar_layout);
        ConstraintLayout constraintLayout = findViewById(R.id.header_title);
        int maxScroll = 138;

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                // verticalOffset is negative when scrolling up and 0 when fully expanded
                Log.d("ScrollTest", "Vertical Offset: " + verticalOffset);

                int radiusOffset = Math.abs(verticalOffset); // Adjust this multiplier as needed

                // Ensure the radiusOffset doesn't exceed the maximum scroll range
                radiusOffset = Math.min(radiusOffset, maxScroll);
                Log.d("ScrollTest", "Radius Offset: " + radiusOffset);
                Log.d("ScrollTest", "maxScroll: " + maxScroll);
                // Calculate the new radius based on the offset
                float newRadius = (maxScroll + verticalOffset) / (float) maxScroll * 90;
                Log.d("ScrollTest", "newRadius: " + newRadius);
                // Apply the new radius to the ConstraintLayout background
                ViewCompat.setBackground(constraintLayout, createRoundedBackground(newRadius));
            }
        });


        RecyclerView rcvBestPrice = findViewById(R.id.rcvBestPrice);
        rcvBestPrice.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<Post> listPost = new ArrayList<>();
        listPost.add(new Post("phuquoc","1,500,000 VND","Phú Quốc"));
        listPost.add(new Post("hanoi","1,000,000 VND","Hà Nội"));
        listPost.add(new Post("dalat","500,000 VND","Đà Lạt"));

        AdapterPost adapter = new AdapterPost(listPost);
        rcvBestPrice.setAdapter(adapter);

        RecyclerView rcvNew = findViewById(R.id.rcvNew);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        rcvNew.setLayoutManager(gridLayoutManager);
        List<News> listNew = new ArrayList<>();
        listNew.add(new News("Chuyến bay tiết kiệm","uudai1"));
        listNew.add(new News("Ưu đãi vé","uudai2"));
        listNew.add(new News("Giảm giá ","uudai3"));
        listNew.add(new News("Ưu đãi hành lý","uudai4"));
        listNew.add(new News("Giảm giá","uudai5"));
        listNew.add(new News("Ưu đãi vé","uudai6"));

        AdapterNew adapterNew = new AdapterNew(listNew);
        rcvNew.setAdapter(adapterNew);


    }
    private Drawable createRoundedBackground(float radius) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(ContextCompat.getColor(MainActivity.this, R.color.white));  // Use ContextCompat for compatibility
        shape.setCornerRadii(new float[]{radius, radius, radius, radius, 0, 0, 0, 0});
        return shape;
    }
    @Override
    public void onClick(View view) {

    }
}
//        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                switch(position){
//                    case 0:
//                        bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
//                        break;
//                    case 1:
//                        bottomNavigationView.getMenu().findItem(R.id.ticket).setChecked(true);
//                        break;
//                }
//            }
//        });