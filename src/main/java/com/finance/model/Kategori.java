package com.finance.model;

public class Kategori {
    private int idKategori;
    private String namaKategori;
    private String jenis; // "Pemasukan" atau "Pengeluaran"

    public Kategori() {}

    public Kategori(int idKategori, String namaKategori, String jenis) {
        this.idKategori = idKategori;
        this.namaKategori = namaKategori;
        this.jenis = jenis;
    }

    public int getIdKategori() { return idKategori; }
    public void setIdKategori(int idKategori) { this.idKategori = idKategori; }

    public String getNamaKategori() { return namaKategori; }
    public void setNamaKategori(String namaKategori) { this.namaKategori = namaKategori; }

    public String getJenis() { return jenis; }
    public void setJenis(String jenis) { this.jenis = jenis; }

    @Override
    public String toString() {
        return this.namaKategori;
    }
}