package com.example.erul.puzzleastar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;


public class Halaman_utama extends AppCompatActivity {
    final Context context = this;
    private CharSequence number_of_tiles[]={"3X3","4X4","5X5"};
    int selectedTile;
    int NUM_OF_TILES;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.halaman_utama);
        final MediaPlayer Audio = MediaPlayer.create(this, R.raw.button);

        Button pindah_halaman = (Button) findViewById(R.id.pindah_halaman);
        Button tkeluar = (Button) findViewById(R.id.tkeluar);
        Button level = (Button) findViewById(R.id.Level);
        Button aturan = (Button) findViewById(R.id.Aturan);

       //tombolbermain
        pindah_halaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Audio.start();
                Intent pindah= new Intent(Halaman_utama.this,PuzzleActivity.class);
                startActivity(pindah);
            }
        }
        );
        aturan.setOnClickListener(new View.OnClickListener() {
                                              @Override
         public void onClick(View view) {
         Audio.start();
         Intent halamanaturan= new Intent(Halaman_utama.this,Halaman_aturan.class);
         startActivity(halamanaturan);
                                              }
                                          }
        );
        //tombolbermain>
//tombolkeluar
        tkeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("KONFIRMASI");
                alertDialogBuilder
                        .setMessage("Apa Kalian Ingin Keluar?")
                        .setCancelable(false)
                        .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Audio.start();
                                moveTaskToBack(true);

                            }
                        })
                        .setNegativeButton("TIDAK", null)

                        .show();
            }
        }
        );
//tombolkeluar>
        level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Audio.start();
                selectedTile=0;
                NUM_OF_TILES=3;

                Audio.start();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Pilih Level ")
                        .setSingleChoiceItems(number_of_tiles, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Audio.start();
                                selectedTile = which;
                            }
                        })
                        .setPositiveButton("Ya",new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog,int which){
                                Audio.start();
                                NUM_OF_TILES=NUM_OF_TILES+selectedTile;
                                PapanPuzzle.NUM_TILES=NUM_OF_TILES;
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Audio.start();

                            }
                        });
                builder.create();
                builder.show();

            }
        });

//main
    }
//diaologkeluar

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
//dialogkeluar>
}

