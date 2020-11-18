package com.example.assignment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import Methods.House;
import Methods.ReadData;
import Methods.search;

import static Methods.search.addToView;
import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

public class contentActivity extends AppCompatActivity {
    HashMap<String, House> housesInfo = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Intent intent = getIntent();
        String content = intent.getStringExtra("contentOfSearch");
        EditText editText = findViewById(R.id.searchContent);
        editText.setText(content);
        assert content != null;
        housesInfo();
        listView(content);
    }

    public void housesInfo() {
        InputStream inputStream = getResources().openRawResource(R.raw.house_info);
        housesInfo = ReadData.loadFromXMLFile(inputStream);
    }


    //点击item的动作，textView是id的名字
    public void OnClick(View view) {
        TextView textView = view.findViewById(R.id.item_Name);
        Intent intent2 = new Intent(getApplicationContext(), ContentDetail.class);
        intent2.putExtra("contentOfHouseID", textView.getText().toString());
        String text = textView.getText().toString();
        String location = Objects.requireNonNull(housesInfo.get(text)).getLocation().getStreet()
                + ", " + Objects.requireNonNull(housesInfo.get(text)).getLocation().getCity();
        intent2.putExtra("location", location);
        Toast.makeText(getApplicationContext(), location, Toast.LENGTH_SHORT).show();
        startActivity(intent2);
    }

    public void backWard(View view) {
        finish();
    }

    public ArrayList<Bitmap> pictures() {
        ArrayList<Bitmap> pics = new ArrayList<>();
        AssetManager assetManager = getAssets();
        try {
            int pictureSize = Objects.requireNonNull(assetManager.list("imageResource")).length;
            for (int i = 1; i <= pictureSize; i++) {
                InputStream inputStream = assetManager.open("imageResource/" + i + ".png");
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                pics.add(bitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pics;
    }

    //设置view列表
    public void listView(String content) {
        InputStream inputStream = getResources().openRawResource(R.raw.keywords);
        InputStreamReader reader = new InputStreamReader(inputStream);
        final int spanCount = 2;
        final RecyclerView recyclerView = findViewById(R.id.houseInfo);
        final ImageView innerSearch = findViewById(R.id.innerSearch);
        final EditText editText = findViewById(R.id.searchContent);
        final SwipeRefreshLayout refreshLayout = findViewById(R.id.refreshView);

        final StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(spanCount, OrientationHelper.VERTICAL);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getDrawable(R.drawable.loading);
        final RecycleAdapter adapter = new RecycleAdapter(search.addToView(content,housesInfo,reader), drawable, pictures());
        myDecoration myDecoration = new myDecoration(20);
        recyclerView.addItemDecoration(myDecoration);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == SCROLL_STATE_DRAGGING) {
                    adapter.changeState(true);
                } else if (newState == SCROLL_STATE_IDLE) {
                    adapter.changeState(false);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        innerSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputStream inputStream2 = getResources().openRawResource(R.raw.keywords);
                InputStreamReader reader2 = new InputStreamReader(inputStream2);
                if (adapter.getItemCount() > 0) {
                    adapter.removeAll();
                    adapter.notifyDataSetChanged();
                }
                ArrayList<House> houses = search.addToView(editText.getText().toString(), housesInfo, reader2);
                adapter.addData(houses);
            }
        });
        //刷新布局
        refreshLayout.setColorSchemeResources(R.color.blue, R.color.red, R.color.black);
        refreshLayout.setOnRefreshListener(() -> {
            adapter.notifyDataSetChanged();
            refreshLayout.setRefreshing(false);
        });
    }

}