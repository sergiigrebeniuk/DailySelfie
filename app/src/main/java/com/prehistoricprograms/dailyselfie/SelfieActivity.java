package com.prehistoricprograms.dailyselfie;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ImageView;


public class SelfieActivity extends ActionBarActivity {

    final static String EXTRA_FILE_PATH = "com.prehistoricprograms.dailyselfie.EXTRA_FILE_PATH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfie);

        Intent intent = getIntent();
        String path = intent.getStringExtra(EXTRA_FILE_PATH);

        if (!path.equals("")) {
            ImageView imageView = (ImageView)findViewById(R.id.large);
            imageView.setImageBitmap(BitmapFactory.decodeFile(path));
        }

    }
}
