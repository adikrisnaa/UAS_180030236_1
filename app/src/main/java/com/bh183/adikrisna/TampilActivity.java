package com.bh183.adikrisna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;

public class TampilActivity extends AppCompatActivity {

    private ImageView imgBuku;
    private TextView tvJudul, tvTanggal, tvGenre, tvPengarang, tvPenerbit, tvSinopsis;
    private String linkBuku;
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil);

        imgBuku = findViewById(R.id.iv_buku);
        tvJudul = findViewById(R.id.tv_judul);
        tvTanggal = findViewById(R.id.tv_tanggal);
        tvGenre = findViewById(R.id.tv_genre);
        tvPengarang = findViewById(R.id.tv_pengarang);
        tvPenerbit = findViewById(R.id.tv_penerbit);
        tvSinopsis = findViewById(R.id.tv_sinopsis);

        Intent terimaData = getIntent();
        tvJudul.setText(terimaData.getStringExtra("JUDUL"));
        tvTanggal.setText(terimaData.getStringExtra("TANGGAL"));
        tvGenre.setText(terimaData.getStringExtra("GENRE"));
        tvPengarang.setText(terimaData.getStringExtra("PENGARANG"));
        tvPenerbit.setText(terimaData.getStringExtra("PENERBIT"));
        tvSinopsis.setText(terimaData.getStringExtra("SINOPSIS"));
        String imgLocation = terimaData.getStringExtra("GAMBAR");

        try {
            File file = new File(imgLocation);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            imgBuku.setImageBitmap(bitmap);
            imgBuku.setContentDescription(imgLocation);
        } catch (FileNotFoundException er) {
            er.printStackTrace();
            Toast.makeText(this, "Gagal mengambil gambar dari media penyimpanan", Toast.LENGTH_SHORT).show();
        }
        linkBuku = terimaData.getStringExtra("LINK");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tampil_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.item_menu_bagikan)
        {
            Intent bagikan = new Intent(Intent.ACTION_SEND);
            bagikan.putExtra(Intent.EXTRA_SUBJECT, tvJudul.getText().toString());
            bagikan.putExtra(Intent.EXTRA_TEXT,linkBuku);
            bagikan.setType("text/plain");
            startActivity(Intent.createChooser(bagikan, "Bagikan Link Penjualan Buku"));
        }

        return super.onOptionsItemSelected(item);
    }
}
