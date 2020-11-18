package com.example.assignment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;


import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Objects;

import Methods.House;
import Methods.ReadData;

public class ContentDetail extends Activity {
    HashMap<String, House> house_info = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_detail);
        housesInfo();
        scrollingMethod();
    }

    public void housesInfo() {
        InputStream inputStream = getResources().openRawResource(R.raw.house_info);
        house_info = ReadData.loadFromXMLFile(inputStream);
    }

    public void scrollingMethod() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("contentOfHouseID");
        String loc = intent.getStringExtra("location");
        assert id != null;
        House house = house_info.get(id);
        TextView location = findViewById(R.id.Location);
        TextView priceNumber = findViewById(R.id.PriceNumber);
        TextView typeContent = findViewById(R.id.TypeContent);
        TextView bedroom = findViewById(R.id.bedroom);
        TextView bathroom = findViewById(R.id.bathroom);
        TextView garage = findViewById(R.id.garage);

        assert house != null;
        typeContent.setText(house.getType());
        location.setText(loc);
        priceNumber.setText(house.getPrice());
        String bedroomText = String.valueOf(house.getRoom().getBedroom());
        String bathroomText = String.valueOf(house.getRoom().getBathroom());
        String garageText = String.valueOf(house.getRoom().getparkingspace());

        if (house.getRoom().getBedroom() == 1||house.getRoom().getBedroom() == 0) {
            bedroomText = bedroomText + " bedroom";
        } else {
            bedroomText = bedroomText + " bedrooms";
        }

        if (house.getRoom().getBathroom() == 1||house.getRoom().getBathroom() == 0) {
            bathroomText = bathroomText + " bathroom";
        } else {
            bathroomText = bathroomText + " bathrooms";
        }

        if (house.getRoom().getparkingspace() == 1||house.getRoom().getparkingspace() == 0) {
            garageText = garageText + " garage";
        } else {
            garageText = garageText + " garages";
        }

        bedroom.setText(bedroomText);
        bathroom.setText(bathroomText);
        garage.setText(garageText);

        final int[] ImageHeight = new int[1];
        ScrollView scrollView = findViewById(R.id.detailScroll);
        final ImageView imageView = findViewById(R.id.houseImage);
        AssetManager assetManager = getAssets();
        int num;
        try {
            num = Objects.requireNonNull(assetManager.list("imageResource")).length;
            InputStream inputStream = assetManager.open("imageResource/"+ Integer.parseInt(id)%num + ".png");
            imageView.setImageBitmap(BitmapFactory.decodeStream(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ViewTreeObserver viewTreeObserver = imageView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ImageHeight[0] = imageView.getMeasuredHeight();
                imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });


        final ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        scrollView.setSmoothScrollingEnabled(true);
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                int num = i1;
                if (num == 0) {
                    num = 1;
                }
                int height = ImageHeight[0] - num;
                if (height <= 0) {
                    height = 0;
                }
                layoutParams.height = height;
                imageView.setLayoutParams(layoutParams);
            }
        });
    }
}
