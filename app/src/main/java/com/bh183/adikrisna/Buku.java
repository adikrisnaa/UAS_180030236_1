package com.bh183.adikrisna;

import java.util.Date;

public class Buku {

    private int idBuku;
    private String judul;
    private Date tanggal;
    private String gambar;
    private String genre;
    private String pengarang;
    private String penerbit;
    private String sinopsis;
    private String link;

    public Buku(int idBuku, String judul, Date tanggal, String gambar, String genre, String pengarang, String penerbit, String sinopsis, String link) {
        this.idBuku = idBuku;
        this.judul = judul;
        this.tanggal = tanggal;
        this.gambar = gambar;
        this.genre = genre;
        this.pengarang = pengarang;
        this.penerbit = penerbit;
        this.sinopsis = sinopsis;
        this.link = link;
    }

    public int getIdBuku() {
        return idBuku;
    }

    public void setIdBuku(int idBuku) {
        this.idBuku = idBuku;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPengarang() {
        return pengarang;
    }

    public void setPengarang(String pengarang) {
        this.pengarang = pengarang;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getSinopsis() { return sinopsis; }

    public void setSinopsis(String sinopsis) { this.sinopsis = sinopsis; }

    public String getLink() { return link; }

    public void setLink(String link) { this.sinopsis = link; }
}
