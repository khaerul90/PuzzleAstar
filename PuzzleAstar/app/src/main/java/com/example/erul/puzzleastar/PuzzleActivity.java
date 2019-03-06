package com.example.erul.puzzleastar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;

public class PuzzleActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static TextView Algoritma;
    private Bitmap imageBitmap = null;
    private PapanPuzzleView boardView;
    private RelativeLayout container;
    public static TextView Langkah;
    final Context context = this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        container = (RelativeLayout) findViewById(R.id.puzzle_container);
        boardView = new PapanPuzzleView(this);
        Langkah = (TextView) findViewById(R.id.Langkah);
        Algoritma = (TextView) findViewById(R.id.Algoritma);

        boardView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        container.addView(boardView);

    }
        public void dispatchTakePictureIntent(View view) {
        final MediaPlayer music = MediaPlayer.create(this, R.raw.button);
        music.start();
        Intent takePictureIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final MediaPlayer music = MediaPlayer.create(this, R.raw.button);
        music.start();

        if (resultCode == RESULT_OK) {
            music.start();
            Bitmap imageBitmap = null;
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                music.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

            boardView.initialize(imageBitmap, container);



        }
    }

    public void shuffleImage(View view) {
        final MediaPlayer music = MediaPlayer.create(this, R.raw.button);
        music.start();
        boardView.shuffle();
    }

    public void solve(View view) {
        final MediaPlayer music = MediaPlayer.create(this, R.raw.button);
        music.start();
        boardView.solve();
    }



    public Context getContext() {
        return context;
    }
}