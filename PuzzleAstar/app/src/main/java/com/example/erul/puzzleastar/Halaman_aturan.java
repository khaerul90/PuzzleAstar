package com.example.erul.puzzleastar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Halaman_aturan extends AppCompatActivity {

    private Button Aturan_kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.halaman_aturan);

        Aturan_kembali = (Button) findViewById(R.id.Aturan_kembali);
        Aturan_kembali.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        Intent Aturan_pindah= new Intent(Halaman_aturan.this,Halaman_utama.class);
        startActivity(Aturan_pindah);
         }
        }
        );
    }

    }
