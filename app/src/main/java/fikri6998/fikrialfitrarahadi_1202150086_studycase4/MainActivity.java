package fikri6998.fikrialfitrarahadi_1202150086_studycase4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mlistnama, mcarigambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inisialisasi Komponen
        mlistnama =(Button)findViewById(R.id.btnListNama);
        mcarigambar =(Button)findViewById(R.id.btnCariGambar);

        //method saat menklik tombol mlistnama akan menuju aktivitas ListNamaActivity
        mlistnama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ListNamaActivity.class));
            }
        });
        //method saat menklik tombol mcarigambar akan menuju aktivitas CariGambarActivity
        mcarigambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CariGambarActivity.class));
            }
        });
    }
}
