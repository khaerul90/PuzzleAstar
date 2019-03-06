package com.example.erul.puzzleastar;

import android.app.Activity;

import android.app.Dialog;
import android.content.Context;

import android.graphics.Bitmap;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;

import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Created by epril on 29/11/2017.
 */

public class PapanPuzzleView extends View {

    public static final int NUM_SHUFFLE_STEPS = 10;
    public static final int NUM = 1;
    private Activity activity;
    private PapanPuzzle papanPuzzle;
    private ArrayList<PapanPuzzle> animation;
    private Random random = new Random();
    private int Nomorlangkah;
    private int Nomoralgoritma;

    private Comparator<PapanPuzzle> comparator = new Comparator<PapanPuzzle>() {
        @Override
        public int compare(PapanPuzzle lhs, PapanPuzzle rhs) {
            return lhs.priority() - rhs.priority();
        }
    };

    public PapanPuzzleView(Context context) {
        super(context);
        activity = (Activity) context;
        animation = null;
    }
    public void initialize(Bitmap imageBitmap, RelativeLayout container) {
        int width = getWidth();
        int height = getHeight();
        papanPuzzle = new PapanPuzzle(imageBitmap, width, height);
        //Fungsi_resetlangkah();
        //Fungsi_resetAlgoritma();
        shuffle();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (papanPuzzle != null)
            if (animation != null && animation.size() >= 0) {
                        papanPuzzle = animation.remove(0);
                        papanPuzzle.draw(canvas);
                        MediaPlayer Suara_menang = MediaPlayer.create(activity, R.raw.swishe_banner);
                        Suara_menang.start();
                        animation.clear();
                        Fungsi_Langkah();
                astar();
                if (animation.size() == 0) {
                    animation = null;
                    papanPuzzle.reset();
                    if (papanPuzzle.resolved()) {
                        Fungsi_resetAlgoritma();
                        MediaPlayer Suara_draw = MediaPlayer.create(activity, R.raw.cheer);
                        Suara_draw.start();
                        final Dialog berhasil = new Dialog(getContext());
                        berhasil.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        berhasil.setContentView(R.layout.halaman_menang);
                        berhasil.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        Button ya = (Button) berhasil.findViewById(R.id.ya);
                        Button Tidak = (Button) berhasil.findViewById(R.id.Tidak);
                        berhasil.show();
                        ya.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                shuffle();
                                berhasil.cancel();
                            }
                        });
                        Tidak.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                System.exit(0);
                            }
                        });
                        Nomoralgoritma = 0;
                    }
                }
            else {
                    this.postInvalidateDelayed(500);
                }
                }
                else {
                papanPuzzle.draw(canvas);

                }
         }
    public void shuffle() {
        if (animation==null && papanPuzzle != null) {
            for (int i = 0; i < NUM_SHUFFLE_STEPS; i++) {
                ArrayList<PapanPuzzle> neighbours = papanPuzzle.neighbours();
                int randomInt = random.nextInt(neighbours.size());
                papanPuzzle = neighbours.get(randomInt);
            }
            Fungsi_resetlangkah();
            Fungsi_resetAlgoritma();
            astar();
            invalidate();
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (animation == null && papanPuzzle != null) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (papanPuzzle.click(event.getX(), event.getY()))
                        Fungsi_Langkah();
                    MediaPlayer Suara_menang = MediaPlayer.create(activity, R.raw.swishe_banner);
                    Suara_menang.start();
                {
                    invalidate();
                    if (papanPuzzle.resolved()) {
                        MediaPlayer mp = MediaPlayer.create(activity, R.raw.cheer);
                        mp.start();
                        final Dialog berhasil = new Dialog(getContext());
                        berhasil.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        berhasil.setContentView(R.layout.halaman_menang);
                        berhasil.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        Button ya = (Button)berhasil.findViewById(R.id.ya);
                        Button Tidak = (Button)berhasil.findViewById(R.id.Tidak);
                        berhasil.show();
                        ya.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                shuffle();
                                berhasil.cancel();
                            }
                        });
                        Tidak.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                System.exit(0);
                            }
                        });
                        Nomoralgoritma = 0;
                        Nomorlangkah = 0;
                    }
                    else if (Nomorlangkah >= Nomoralgoritma && !papanPuzzle.resolved()){

                        final Dialog alertDialog = new Dialog(getContext());
                        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        alertDialog.setContentView(R.layout.halaman_gameover);
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        Button ya = (Button)alertDialog.findViewById(R.id.ya);
                        Button Tidak = (Button)alertDialog.findViewById(R.id.Tidak);
                        alertDialog.show();
                        ya.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                //alertDialog.cancel();
                                shuffle();
                                alertDialog.cancel();

                            }
                        });
                        Tidak.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                System.exit(0);
                            }
                        });
                    }
                    return  true;

                }
            }
        }
        return super.onTouchEvent(event);
    }

    public void solve() {
        Nomoralgoritma=0;
        PriorityQueue<PapanPuzzle> priorityQueue = new PriorityQueue<>(1, comparator);
        PapanPuzzle currentBoard = new PapanPuzzle(papanPuzzle, -1);
        currentBoard.setPreviousBoard(null);
        priorityQueue.add(currentBoard);
        while (!priorityQueue.isEmpty()) {
            PapanPuzzle bestState = priorityQueue.poll();
            if (bestState.resolved()) {

                ArrayList<PapanPuzzle> steps = new ArrayList<>();

                while (bestState.getPreviousBoard() != null) {

                    steps.add(bestState);
                    bestState = bestState.getPreviousBoard();
                    Fungsi_algoritma();
                    }
                Collections.reverse(steps);
                animation = steps;
                invalidate();
                break;
                }
            else {
                priorityQueue.addAll(bestState.neighbours());

                    }
        }
    }

    private void Fungsi_Langkah() {
        Nomorlangkah++;
        PuzzleActivity.Langkah.setText("Langkah \n   " + Integer.toString(Nomorlangkah));

    }
    public void Fungsi_algoritma() {
        Nomoralgoritma++;
        PuzzleActivity.Algoritma.setText("Finish \n  " + Integer.toString(Nomoralgoritma));

    }
    private void Fungsi_resetlangkah() {
        Nomorlangkah = 0;
        PuzzleActivity.Langkah.setText("Langkah \n   " + Integer.toString(Nomorlangkah));

    }
    private void Fungsi_resetAlgoritma() {
        Nomoralgoritma = 0;
        PuzzleActivity.Algoritma.setText("Finish \n  " + Integer.toString(Nomoralgoritma));

    }
    public void astar() {
Nomoralgoritma=0;
        PriorityQueue<PapanPuzzle> priorityQueue = new PriorityQueue<>(1, comparator);
        PapanPuzzle currentBoard = new PapanPuzzle(papanPuzzle, -1);
        currentBoard.setPreviousBoard(null);
        priorityQueue.add(currentBoard);
        while (!priorityQueue.isEmpty()) {
            PapanPuzzle bestState = priorityQueue.poll();
                if (bestState.resolved())
                {
                    ArrayList<PapanPuzzle> steps = new ArrayList<>();
                    while (bestState.getPreviousBoard() != null)
                    {
                        steps.add(bestState);
                        bestState = bestState.getPreviousBoard();
                        Fungsi_algoritma();
                    }
                    invalidate();
                    break;
                } else {
                    priorityQueue.addAll(bestState.neighbours());
                }
            }
        }

}

