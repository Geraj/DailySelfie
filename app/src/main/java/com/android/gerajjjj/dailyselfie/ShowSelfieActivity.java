package com.android.gerajjjj.dailyselfie;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;

public class ShowSelfieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_selfie);
        Intent intent = getIntent();
        String url = intent.getStringExtra(MainActivity.URL);
        String name = intent.getStringExtra(MainActivity.NAME);
        File f = new File(url);
        Bitmap bitmap = BitmapFactory.decodeFile(f.getPath());
        ImageView imageView = (ImageView)findViewById(R.id.selfie_image_view);
        imageView.setImageBitmap(bitmap);
    }
}
