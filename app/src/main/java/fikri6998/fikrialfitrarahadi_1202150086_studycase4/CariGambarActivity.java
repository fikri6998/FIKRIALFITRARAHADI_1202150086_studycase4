package fikri6998.fikrialfitrarahadi_1202150086_studycase4;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class CariGambarActivity extends AppCompatActivity {

    //Deklarasi Komponen yang akan digunakan
    //EditText
    private EditText ImageURL;
    //Button
    private Button btnImage;
    //ImageView (gambar)
    private ImageView Imageview;
    //ProgressDialog(Loading)
    private ProgressDialog loading;
    //Penunjuk jika gambar sudah di load.
    private int ImageIsLoaded=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_gambar);

            setTitle("Cari Gambar");

            //Inisialisasi Komponen View
            ImageURL=(EditText)findViewById(R.id.ImgURL);
            btnImage=(Button)findViewById(R.id.btnImg);
            Imageview=(ImageView)findViewById(R.id.Imgview);

            //Aksi Klik pada Tombol
            btnImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Memanggil Method yang dapat mengupdate
                    loadImage();
                }
            });

            //Cek apakah bundle berisikan sesuatu
            if(savedInstanceState!=null){
                //Cek apakah didalam bundle nilai kunci sebagai penunjuk data sudah di load sebelumnya
                if(savedInstanceState.getInt("IMAGE_IS_LOADED")!=0 && !savedInstanceState.getString("EXTRA_TEXT_URL").isEmpty()){
                    ImageURL.setText(""+savedInstanceState.getString("EXTRA_TEXT_URL"));
                    loadImage();
                }
            }
        }


        //Method yang berguna untuk melakukan penyimpanan sesuatu pada package
        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);

            //Simpan untuk URL TEXT agar input tidak dilakukan kembali
            outState.putString("EXTRA_TEXT_URL",ImageURL.getText().toString());
            //Simpan untuk respon pada savedinstancestate
            outState.putInt("IMAGE_IS_LOADED",ImageIsLoaded);
        }


        //method yang digunakan untuk mengupdate tampilan mengubah gambar dengan menggunakan asynctask url

    public void loadImage(){
        //Mengambil nilai input (String)
        String UrlImage = ImageURL.getText().toString();
        //Aksi Asynctask untuk melakukan pencarian/load gambar dari internet
        new CariImageTask().execute(UrlImage);
    }

    /*
    * Class Asynctask dengan paramater deklarasi:
    *   Input: String -> Alamat Gambar
    *   Progress: Integer -> Persentase Proses
    *   Result: Bitmap -> Gambar
    */
    public class CariImageTask extends AsyncTask<String, Integer, Bitmap> {

        //Method yang dilakukan sebelum aksi dilakukan
        // Pada aplikasi, tahap ini digunakan untuk memunculkan progress bar beserta propertynya


        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            loading = new ProgressDialog(CariGambarActivity.this);
            loading.setMessage("Wait a minute ...");
            loading.setMax(100);
            loading.incrementProgressBy(1);
            loading.show();


        }

        //Method ini digunakan untuk mencari gambar dari internet berdasarkan
        // alamat URL gambar valid dengan mengubahnya menjadi bitmap

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(params[0]);
                bitmap = BitmapFactory.decodeStream((InputStream)url.getContent());
            } catch (IOException e) {
                Log.e("doInBackground() - ", e.getMessage());
            }
            return bitmap;
        }

        //Method ini digunakan saat proses pengambilan data berlangsung dan menampilkan progressbar

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            try {
                Thread.sleep(1000);
                loading.setMessage("Loading...");
                loading.incrementProgressBy(values[0]);
                loading.setProgress(values[0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        //method ini digunakan untuk menampilkan gambar yang sudah didapat ke dalam imageView
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            //Set ImageView
            Imageview.setImageBitmap(bitmap);
            //Parameter Sudah di load
            ImageIsLoaded=1;
            //Menghilangkan Loading(ProgressBar)
            loading.dismiss();

        }
    }
}

