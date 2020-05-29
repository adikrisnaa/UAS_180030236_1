package com.bh183.adikrisna;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 1;
    private final static String DATABASE_NAME = "db_buku";
    private final static  String TABLE_BUKU = "t_buku";
    private final static String KEY_ID_BUKU = "ID_Buku";
    private final static String KEY_JUDUL = "Judul";
    private final static String KEY_TGL = "Tanggal";
    private final static String KEY_GAMBAR = "Gambar";
    private final static String KEY_GENRE = "Genre";
    private final static String KEY_PENGARANG = "Pengarang";
    private final static String KEY_PENERBIT = "Penerbit";
    private final static String KEY_SINOPSIS = "Sinopsis";
    private final static String KEY_LINK = "Link";
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private Context context;

    public DatabaseHandler (Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_BUKU = "CREATE TABLE " + TABLE_BUKU
                + "(" + KEY_ID_BUKU + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUL + " TEXT, " + KEY_TGL + " DATE, "
                + KEY_GAMBAR + " TEXT, " + KEY_GENRE + " TEXT, "
                + KEY_PENGARANG + " TEXT, " + KEY_PENERBIT + " TEXT, "
                + KEY_SINOPSIS + " TEXT, " + KEY_LINK + " TEXT);";
        db.execSQL(CREATE_TABLE_BUKU);
        inisialisasiBukuAwal(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_BUKU;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void tambahBuku(Buku dataBuku) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataBuku.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataBuku.getTanggal()));
        cv.put(KEY_GAMBAR, dataBuku.getGambar());
        cv.put(KEY_GENRE, dataBuku.getGenre());
        cv.put(KEY_PENGARANG, dataBuku.getPengarang());
        cv.put(KEY_PENERBIT, dataBuku.getPenerbit());
        cv.put(KEY_SINOPSIS, dataBuku.getSinopsis());
        cv.put(KEY_LINK, dataBuku.getLink());

        db.insert(TABLE_BUKU, null, cv);
        db.close();
    }

    public void tambahBuku(Buku dataBuku, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataBuku.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataBuku.getTanggal()));
        cv.put(KEY_GAMBAR, dataBuku.getGambar());
        cv.put(KEY_GENRE, dataBuku.getGenre());
        cv.put(KEY_PENGARANG, dataBuku.getPengarang());
        cv.put(KEY_PENERBIT, dataBuku.getPenerbit());
        cv.put(KEY_SINOPSIS, dataBuku.getSinopsis());
        cv.put(KEY_LINK, dataBuku.getLink());

        db.insert(TABLE_BUKU, null, cv);
    }

    public void editBuku(Buku dataBuku) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataBuku.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataBuku.getTanggal()));
        cv.put(KEY_GAMBAR, dataBuku.getGambar());
        cv.put(KEY_GENRE, dataBuku.getGenre());
        cv.put(KEY_PENGARANG, dataBuku.getPengarang());
        cv.put(KEY_PENERBIT, dataBuku.getPenerbit());
        cv.put(KEY_SINOPSIS, dataBuku.getSinopsis());
        cv.put(KEY_LINK, dataBuku.getLink());

        db.update(TABLE_BUKU, cv, KEY_ID_BUKU + "=?", new String[]{String.valueOf(dataBuku.getIdBuku())});
        db.close();
    }

    public void hapusBuku(int idBuku) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_BUKU, KEY_ID_BUKU + "=?", new String[]{String.valueOf(idBuku)});
        db.close();
    }

    public ArrayList<Buku> getAllBuku() {
        ArrayList<Buku> dataBuku = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_BUKU;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if (csr.moveToFirst()) {
            do {
                Date tempDate = new Date();
                try {
                    tempDate = sdFormat.parse(csr.getString(2));
                } catch (ParseException er) {
                    er.printStackTrace();
                }

                Buku tempBuku = new Buku(
                  csr.getInt(0),
                  csr.getString(1),
                  tempDate,
                  csr.getString(3),
                  csr.getString(4),
                  csr.getString(5),
                  csr.getString(6),
                  csr.getString(7),
                  csr.getString(8)
                );

                dataBuku.add(tempBuku);
            } while (csr.moveToNext());
        }

        return dataBuku;
    }

    private String storeImageFile(int id) {
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageInternalStorage(image, context);
        return location;
    }

    private void inisialisasiBukuAwal(SQLiteDatabase db) {
        int idBuku = 0;
        Date tempDate = new Date();

        // Menambah data buku ke-1
        try {
            tempDate = sdFormat.parse("01/04/2014");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Buku buku1 = new Buku(
                idBuku,
                "Dilan: Dia Adalah Dilanku Tahun 1990",
                tempDate,
                storeImageFile(R.drawable.buku1),
                "Genre Romance",
                "Pidi Baiq",
                "Penerbit : PT Mizan Pustaka",
                "Novel dilan: dia adalah dilanku tahun 1990 menceritakan tentang kisah cinta Milea. Milea adalah seorang murid baru pindahan dari Jakarta. Dan di saat ia berjalan menuju sekolah, ia bertemu dengan seorang teman satu sekolahnya, seorang peramal. Peramal itu mengatakan bahwa nanti mereka akan bertemu di kantin. Awalnya Milea tidak menghiraukan laki-laki peramal itu, tapi setiap hari laki-laki peramal tersebut selalu mengganggunya. Mau tidak mau, Milea mulai mencari tahu, laki-laki peramal itu bernama Dilan.\n" +
                        "Suatu hari, saat Dilan mengikuti Milea pulang dengan angkot ia berkata, “Milea, kamu cantik, tapi aku belum mencintaimu. Enggak tahu kalau sore. Tunggu aja”. Perkataan Dilan itu membuat hati Milea berdebar-debar, mungkin ia kaget atas ucapan Dilan. Milea diam mendengar ucapan itu, ia juga memikirkan Beni, pacarnya yang ada di Jakarta.\n" +
                        "Dilan mendekati Milea dengan cara yang tidak biasa, mungkin itu yang membuat Milea selalu memikirkannya. Dilan memberikan coklat kepada Milea melalui tukang pos, Dilan membawa Bi Asih untuk memijiti Milea saat sedang sakit, Dilan memberikan hadiah Teka Teki Silang pada Milea sebagai hadiah ulang tahun dengan sebuah tulisan “Selamat ulang tahun, Milea. Ini hadiah untukmu, Cuma TTS. Tapi sudah kuisi semua. Aku sayang kamu. Aku tidak mau kamu pusing kaena harus mengisinya. Dilan”\n" +
                        "Lambat laun, seiring berjalannya waktu Milea dan Dilan menjadi akrab. Milea mengetahui beberapa hal tentang dilan dari Wati, sepupu Dilan yang sekelas dengannya. Sekolah Milea di Bandung terpilih menjadi peserta Cerdas Cermat TVRI, beberapa siswa yang bukan peserta dianjurkan untuk ikut memberikan semangat buat teman-temannya yang sedang berlomba. Milea salah satunya, dan di Jakarta ia sudah berencana untuk bertemu dengn Beni, pacarnya. Milea sudah lama menunggu Beni yang berjanji untuk datang ke TVRI, namun Beni tak kunjung datang. Akhirnya, Milea pergi makan bersama Nandan dan Wati. Saat itulah Beni datang dan marah-marah melihat Milea makan bersama laki-laki lain. Hubungan mereka pun berakhir.\n" +
                        "\n",
                "https://www.tokopedia.com/search?st=product&q=novel%20dilan"
        );

        tambahBuku(buku1, db);
        idBuku++;

        // Menambahkan data buku ke-2
        try {
            tempDate = sdFormat.parse("01/12/2008");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Buku buku2 = new Buku(
                idBuku,
                "Percy Jackson & Olympians ” The Lightning Thief ”",
                tempDate,
                storeImageFile(R.drawable.buku2),
                "Genre Fantasi",
                "Rick Riordan",
                "Penerbit : Mizan Fantasi",
                "The Lightning Thief merupakan buku pertama dari serial Percy Jackson & Olympians karya Rick Riordan . Buku ini menceritakan tentang seorang anak laki-laki bernama Percy Jackson yang berumur 12 tahun dan mengidap penyakit diseleksia dan GPPH ( Gangguan Pemusatan Perhatian dan Hiperaktif ). Ia mengidap penyakit diseleksia dimana saat membaca huruf-huruf seperti melayang-layang tak beraturan karena otaknya terprogram untuk membaca huruf Yunani Kuno. Ia juga seorang pribadi yang hiperaktif dan impulsif. Akibat penyakit yang di deritanya, ia sering dikeluarkan dari sekolah karena sering membuat masalah. Tetapi, itu hanya sedikit saja dari sekian masalah yang menantinya. Monster-monster tiba-tiba menyerangnya. Sampai pada akhirnya ia menemukan tempat yang sesungguhnya diperuntukan baginya yaitu perkemahan blasteran .\n" +
                        "Perkemahan Blasteran merupakan suatu tempat khusus untuk anak-anak keturunan dewa , disana mereka terlindungi dari para monster yang mencarinya dan berusaha untuk membunuh mereka. Di Perkemahan Blasteran anak-anak keturunan dewa menempati kabin-kabin sebagai tempat tinggal mereka. Kabin-kabin tersebut dikelompokkan berdasarkan siapa dewa atau dewi yang menjadi ayah atau ibu mereka. Di perkemahan tersebut percy menemui hal-hal yang tak pernah ia bayangkan seperti bertemu dengan makhluk makhluk yang tidak biasa seperti satir, centaurus, cyclops, dan monster-monster lainnya.\n" +
                        "Masalah besar telah menanti Percy  setelah ia datang ke Perkemahan Blasteran dan diklaim sebagai putra Poseidon , ia membuat seorang dewa marah besar.Sebelum kedatangan Percy  ke Perkemahan Blasteran , telah terjadi perselisihan antara Dewa Zeus dan Dewa Poseidon. Petir asali milik Dewa Zeus yang merupakan lambang kekuasaanya dan terbuat dari perunggu-bintang berkelas tinggi, yang ujungnya ditutupi bahan peledak tingkat-dewa telah hilang dan dicuri. Setelah Zeus menyadari petir asali miliknya hilang, ia langsung menyalahkan Poseidon . Menurut hukum dewa yang paling kuno seorang dewa tidak bisa merebut lambang kekuasaan dewa lain secara langsung. Oleh karena itu Zeus yakin Poseidon membujuk seorang pahlawan manusia untuk mengambilnya dan akan  digunakan untuk menggulingkan Zeus dari singgasananya. Percy dituduh menajdi tersangka pencuri Petir asali milik Dewa Zeus dan menimbulkan perpecahan para dewa\n" +
                        "\n",
                "https://www.tokopedia.com/search?st=product&q=Percy%20Jackson%20%26%20Olympians%20%E2%80%9D%20The%20Lightning%20Thief%20%E2%80%9D"
        );

        tambahBuku(buku2, db);
        idBuku++;

        //Menambahkan data buku ke-3
        try {
            tempDate = sdFormat.parse("01/06/2010");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Buku buku3 = new Buku(
                idBuku,
                "Marmut Merah Jambu",
                tempDate,
                storeImageFile(R.drawable.buku3),
                "Genre Komedi",
                "Raditya Dika",
                "Penerbit : Bukune",
                "Marmut Merah Jambu adalah buku ke lima dari Raditya Dika yang telah resmi diterbitkan pada juni 2010. Sebagian besar dari 13 judul yang ada pada buku ini adalah kisah percintaan yang bertepuk sebelah tangan. Hampir semua cerita yang ada di dalam nya adalah pengalaman Radit atau pun dari orang terdekat Radit. Kisah cinta yang bermula dari SD sampai kuliah.\n" +
                        "Dalam buku ini Radit menceritakan kisah cintanya mulai dari cinta diam-diam, indahnya PDKT, sampai ditolak mentah-mentah. Buku ini keseluruhan sangat menarik dibaca karena sesuai dengan kehidupan sehari-hari seperti cinta diam-diam karena takut mengungkapkan isi hatinya. Keahlian Radit dalam mengolah kata dan sebagian curahan dari teman-temannya menjadi salah satu isi dalam buku ini bahkan mulai dari perhatian orang tuanya, adiknya(Edgar), bahkan sampai kucing peliharaan keluarganya yang dibuat tokoh utama disalah satu bab dimana sang kucing dimanusiakan.\n" +
                        "Gaya bahasa yang digunakan juga sangat baik membuat pembacanya seperti benar benar menyaksikan langsung kejadian-kejadian yang ada pada buku ini. Apalagi pada waktu Radit mau berangkat untuk shoting film Kambing Jantan . Pada waktu itu ada luar bandara dan tiba-tiba ada nomor tidak dikenal menelfon dan ternyata orang suruhan ayahnya\n" +
                        "‘Bang, aku ada titipan dari Bapak; katanya di telepon.\n" +
                        "‘Udah mau boarding belom, Bang?’\n" +
                        "‘Belom, ini lagi diluar, belom masuk. Kenapa? Tanya gue.\n" +
                        "‘Ada titipan dari Bapak! Jangan masuk kedalem dulu, Bang! Tunggu!\n" +
                        "Sepuluh menit menunggu akhirnya seseorang datang dan memberikan plastik hitam.\n" +
                        "‘Apa ini?\n" +
                        "‘Celana dalem baru, Bang,’ katanya orang itu, kalem. Gubrak. \n" +
                        "Celana dalem baru. Bokap gue mitip pesan ke gue agar selalu mengganti celan dalam supaya sehat selalu di Australia.\n" +
                        "Cerita ini mengambarkan betapa cintanya Ayahnya kepada Radit agar sehat selalu dengan kemasan yang lucu dan mengharukan.\n" +
                        "\n",
                "https://www.tokopedia.com/search?st=product&q=marmut%20merah%20jambu%20novel"
        );

        tambahBuku(buku3, db);
        idBuku++;


        //Menambahkan data buku ke-4
        try {
            tempDate = sdFormat.parse("01/04/2007");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Buku buku4 = new Buku(
                idBuku,
                "Diary of a Wimpy Kid",
                tempDate,
                storeImageFile(R.drawable.buku4),
                "Genre Komedi",
                "Jeff Kinney",
                "Penerbit : Amulet Books ",
                "Greg Heffley seorang anak yang Tengil,jail,dan nakal. Greg adalah anak ke 2 dari 3 bersaudara ia memiliki kaka bernama Rodrick dan adik bernama Manny. Greg terjebak di Sekolah Menengah Pertama bersama segerombolan orang dungu. Greg ingin sekali menjadi orang terpopuler disekolah nya. Dia sangat berusaha mencari teman yang populer agar ia terbawa populer tetapi semua harapannya berbanding terbalik ia malah mendapat kesialan di sekolah ataupun dirumahnya. Setiap ia mencoba bertingkah disekolah dengan gayanya yang tengil orang-orang di sekitarnya malah terlihat cuek maka dari itu ia selalu GAGAL!.\n" +
                        "\n" +
                        "Walaupun Greg adalah anak tengil,tetapi ia memiliki sahabat setia bernama Rowley. Semenjak harapannya selalu Gagal Greg menuliskannya dalam sebuah Jurnal tetapi bertuliskan Diary, Jurnal itu dibelikan oleh Mom Greg.\n" +
                        "\n" +
                        "Di Sekolah Menengah Pertamanya ada sebuah kutukan sentuhan KEJU. Kutukan itu ada karena sebuah Keju yang sudah lama terjatuh di Lapangan, kutukannya adalah apabila ada seorang anak yang berani memegang keju itu maka tidak ada lagi orang yang mau berteman dengannya,kecuali ia menyentuh orang lain maka orang yang ia sentuh akan menjadi temannya. Apakah Greg akan menjadi populer? \n" +
                        "Daripada penasaran lebih baik baca bukunya bisa membuat pipi pegal, perut terkocok, bahkan mata berair.\n",
                "https://www.tokopedia.com/search?st=product&q=diary%20of%20a%20wimpy%20kid"
        );

        tambahBuku(buku4, db);
        idBuku++;

        //Menambahkan data buku ke-5
        try {
            tempDate = sdFormat.parse("17/01/2015");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Buku buku5 = new Buku(
                idBuku,
                "Koala Kumal",
                tempDate,
                storeImageFile(R.drawable.buku5),
                "Genre Komedi",
                "Raditya Dika",
                "Penerbit : Gagas Media ",
                "Selain main perang-perangan, gue, Dodo, dan Bahri juga suka berjemur di atas mobil tua warna merah yang sering diparkir di pinggir sungai samping kompleks. Formasinya selalu sama: Bahri dan gue tiduran di atap mobil, sedangkan Dodo, seperti biasa, agak terbuang, di atas bagasi. Kadang kami tiduran selama setengah jam. Kadang, kalau cuaca lagi sangat terik, bisa sampai dua jam. Kalau cuacanya lagi sejuk dan tidak terlalu terik, kami biasanya sama-sama menatap ke arah matahari, memandangi langit sambil tiduran. Kalau sudah begini, Bahri menaruh kedua tangannya di belakang kepala, sambil tiduran dia berkata,\n" +
                        "‘Rasanya kayak di Miami, ya?’\n" +
                        "‘Iya,’ jawab gue.\n" +
                        "‘Iya,’ jawab Dodo.\n" +
                        "Kami bertiga gak ada yang pernah ke Miami.\n" +
                        "\n" +
                        "Koala Kumal adalah buku komedi yang menceritakan pengalaman Raditya Dika dari mulai jurit malam SMP yang berakhir dengan kekacauan sampai bertemu perempuan yang mahir bermain tombak.",
                "https://www.tokopedia.com/search?st=product&q=koala%20kumal"
        );

        tambahBuku(buku5, db);
        idBuku++;
    }
}
