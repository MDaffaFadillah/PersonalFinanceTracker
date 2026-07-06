package com.finance.model;

public class Budget {
    private int idBudget;
    private int idUser;
    private int idKategori;
    private int bulan;
    private int tahun;
    private double limitBudget;

    // Field tambahan untuk hasil kalkulasi (bukan kolom tabel)
    private double totalTerpakai;
    private String namaKategori;

    public Budget() {}

    public Budget(int idBudget, int idUser, int idKategori, int bulan, int tahun, double limitBudget) {
        this.idBudget = idBudget;
        this.idUser = idUser;
        this.idKategori = idKategori;
        this.bulan = bulan;
        this.tahun = tahun;
        this.limitBudget = limitBudget;
    }

    public int getIdBudget() { return idBudget; }
    public void setIdBudget(int idBudget) { this.idBudget = idBudget; }

    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }

    public int getIdKategori() { return idKategori; }
    public void setIdKategori(int idKategori) { this.idKategori = idKategori; }

    public int getBulan() { return bulan; }
    public void setBulan(int bulan) { this.bulan = bulan; }

    public int getTahun() { return tahun; }
    public void setTahun(int tahun) { this.tahun = tahun; }

    public double getLimitBudget() { return limitBudget; }
    public void setLimitBudget(double limitBudget) { this.limitBudget = limitBudget; }

    public double getTotalTerpakai() { return totalTerpakai; }
    public void setTotalTerpakai(double totalTerpakai) { this.totalTerpakai = totalTerpakai; }

    public String getNamaKategori() { return namaKategori; }
    public void setNamaKategori(String namaKategori) { this.namaKategori = namaKategori; }

    // Method bantu — supaya logic warna tidak perlu ditulis ulang di frame
    public boolean isTerlampaui() {
        return totalTerpakai > limitBudget;
    }

    public double getPersentase() {
        if (limitBudget == 0) return 0;
        return (totalTerpakai / limitBudget) * 100;
    }
}