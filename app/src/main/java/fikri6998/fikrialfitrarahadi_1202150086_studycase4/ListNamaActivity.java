package fikri6998.fikrialfitrarahadi_1202150086_studycase4;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListNamaActivity extends AppCompatActivity {


    ListView mListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_nama_activity);

        setTitle("List Nama Mahasiswa");

        mListview = (ListView) findViewById(R.id.list_view);
    }

    public void startText(View view) {
        //Aksi Asynctask untuk melakukan list nama mahasiswa
        new SimpleAsyncTask(mListview).execute();


    }
    //method untuk panggil class asynctasknya
    class SimpleAsyncTask extends AsyncTask<Void, Void, String> {

        ArrayAdapter adapter;
        ListView mListview;
        ProgressDialog progressDialog;
        ArrayList<String> listNama;

        public SimpleAsyncTask(ListView mListview) {

            this.mListview = mListview;
            progressDialog = new ProgressDialog(ListNamaActivity.this);
            listNama = new ArrayList<>();
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            //menampilkan proses dialog
            progressDialog.setTitle("Loading Data");
            progressDialog.setIndeterminate(true);
            progressDialog.setProgress(0);
            progressDialog.setMax(100);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(true);
            progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel Process", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    progressDialog.dismiss();
                    SimpleAsyncTask.this.cancel(true);
                }
            });

            progressDialog.show();


        }

        @Override
        protected String doInBackground(Void... voids) {

            adapter = new ArrayAdapter<>(ListNamaActivity.this, android.R.layout.simple_list_item_1, listNama); //membuat adapter

            //menyimpan array pada sebuah variabel
            String[] nama = getResources().getStringArray(R.array.namaMahasiswa);

            //perulangan untuk menyimpan array
            for (int a = 0; a < nama.length; a++) {
                final long persen = 100L * a / nama.length;
                final String mhs = nama[a];
                try {
                    Runnable change = new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.setMessage((int) persen+"% - Adding "+mhs);
                        }
                    };
                    runOnUiThread(change);
                    Thread.sleep(300);
                    listNama.add(nama[a]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        //method sesudah asynctask sudah dijalankan
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mListview.setAdapter(adapter);
            progressDialog.dismiss();
        }



        }
    }

