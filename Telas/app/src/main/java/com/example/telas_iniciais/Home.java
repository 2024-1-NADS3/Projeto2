package com.example.telas_iniciais;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    private ImageSlider imageSlider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imageSlider = findViewById(R.id.image_slider);

        //lista de imagem

        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.image1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image13, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image4, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image12, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);



    }
}