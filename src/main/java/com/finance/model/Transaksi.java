package com.finance.model;

import java.sql.Date;

public class Transaksi {
    private int idTransaksi;
    private int idUser;
    private int idKategori;
    private double nominal;
    private String keterangan;
    private Date tanggal;

    // Field tambahan (bukan kolom asli tabel) — untuk hasil JOIN saat ditampilkan di JTable
    private String namaKategori;
    private String jenisKategori;

    public Transaksi() {}

    public Transaksi(int idTransaksi, int idUser, int idKategori, double nominal, String keterangan, Date tanggal) {
        this.idTransaksi = idTransaksi;
        this.idUser = idUser;
        this.idKategori = idKategori;
        this.nominal = nominal;
        this.keterangan = keterangan;
        this.tanggal = tanggal;
    }

    public int getIdTransaksi() { return idTransaksi; }
    public void setIdTransaksi(int idTransaksi) { this.idTransaksi = idTransaksi; }

    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }

    public int getIdKategori() { return idKategori; }
    public void setIdKategori(int idKategori) { this.idKategori = idKategori; }

    public double getNominal() { return nominal; }
    public void setNominal(double nominal) { this.nominal = nominal; }

    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }

    public Date getTanggal() { return tanggal; }
    public void setTanggal(Date tanggal) { this.tanggal = tanggal; }

    public String getNamaKategori() { return namaKategori; }
    public void setNamaKategori(String namaKategori) { this.namaKategori = namaKategori; }

    public String getJenisKategori() { return jenisKategori; }
    public void setJenisKategori(String jenisKategori) { this.jenisKategori = jenisKategori; }
}