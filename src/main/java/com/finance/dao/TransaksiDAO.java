package com.finance.dao;

import com.finance.koneksi.Koneksi;
import com.finance.model.Transaksi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransaksiDAO {

    // ===== CRUD DASAR =====
    public boolean tambahTransaksi(Transaksi t) {
        String sql = "INSERT INTO transaksi (id_user, id_kategori, nominal, keterangan, tanggal) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = new Koneksi().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, t.getIdUser());
            ps.setInt(2, t.getIdKategori());
            ps.setDouble(3, t.getNominal());
            ps.setString(4, t.getKeterangan());
            ps.setDate(5, t.getTanggal());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editTransaksi(Transaksi t) {
        String sql = "UPDATE transaksi SET id_kategori=?, nominal=?, keterangan=?, tanggal=? WHERE id_transaksi=?";
        try (Connection conn = new Koneksi().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, t.getIdKategori());
            ps.setDouble(2, t.getNominal());
            ps.setString(3, t.getKeterangan());
            ps.setDate(4, t.getTanggal());
            ps.setInt(5, t.getIdTransaksi());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hapusTransaksi(int idTransaksi) {
        String sql = "DELETE FROM transaksi WHERE id_transaksi = ?";
        try (Connection conn = new Koneksi().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTransaksi);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Transaksi> getTransaksiByUser(int idUser) {
    List<Transaksi> list = new ArrayList<>();
    // Menggunakan JOIN agar kita mendapatkan nama_kategori secara otomatis
    String sql = "SELECT t.*, k.nama_kategori FROM transaksi t " +
                 "JOIN kategori k ON t.id_kategori = k.id_kategori " +
                 "WHERE t.id_user = ? ORDER BY t.tanggal DESC";
    
    try (Connection conn = new Koneksi().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, idUser);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            Transaksi t = new Transaksi();
            t.setIdTransaksi(rs.getInt("id_transaksi"));
            t.setIdUser(rs.getInt("id_user"));
            t.setIdKategori(rs.getInt("id_kategori"));
            t.setNamaKategori(rs.getString("nama_kategori")); // Penting: Ambil nama kategori dari JOIN
            t.setTanggal(rs.getDate("tanggal")); // Pastikan ini sesuai tipe di Transaksi.java
            t.setNominal(rs.getDouble("nominal"));
            t.setKeterangan(rs.getString("keterangan"));
            list.add(t);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}

    // ===== SALDO TOTAL (akumulatif, TIDAK difilter bulan) =====

    public double getSaldoTotal(int idUser) {
        String sql = "SELECT " +
                     "SUM(CASE WHEN k.jenis = 'Pemasukan' THEN t.nominal ELSE 0 END) - " +
                     "SUM(CASE WHEN k.jenis = 'Pengeluaran' THEN t.nominal ELSE 0 END) AS saldo " +
                     "FROM transaksi t JOIN kategori k ON t.id_kategori = k.id_kategori " +
                     "WHERE t.id_user = ?";
        try (Connection conn = new Koneksi().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUser);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("saldo"); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ===== FINANCIAL HEALTH (per bulan yang dipilih) =====
    public double[] getRingkasanBulanan(int idUser, int bulan, int tahun) {
        String sql = "SELECT " +
                     "SUM(CASE WHEN k.jenis = 'Pemasukan' THEN t.nominal ELSE 0 END) AS pemasukan, " +
                     "SUM(CASE WHEN k.jenis = 'Pengeluaran' THEN t.nominal ELSE 0 END) AS pengeluaran " +
                     "FROM transaksi t JOIN kategori k ON t.id_kategori = k.id_kategori " +
                     "WHERE t.id_user = ? AND MONTH(t.tanggal) = ? AND YEAR(t.tanggal) = ?";
        try (Connection conn = new Koneksi().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUser);
            ps.setInt(2, bulan);
            ps.setInt(3, tahun);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new double[]{ rs.getDouble("pemasukan"), rs.getDouble("pengeluaran") };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new double[]{0, 0};
    }

    // ===== INSIGHT: kategori pengeluaran terbesar bulan yang dipilih =====
    public String[] getKategoriTerbesar(int idUser, int bulan, int tahun) {
        String sql = "SELECT k.nama_kategori, SUM(t.nominal) AS total " +
                     "FROM transaksi t JOIN kategori k ON t.id_kategori = k.id_kategori " +
                     "WHERE t.id_user = ? AND k.jenis = 'Pengeluaran' " +
                     "AND MONTH(t.tanggal) = ? AND YEAR(t.tanggal) = ? " +
                     "GROUP BY k.id_kategori, k.nama_kategori " +
                     "ORDER BY total DESC LIMIT 1";
        try (Connection conn = new Koneksi().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUser);
            ps.setInt(2, bulan);
            ps.setInt(3, tahun);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new String[]{ rs.getString("nama_kategori"), String.valueOf(rs.getDouble("total")) };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }
}