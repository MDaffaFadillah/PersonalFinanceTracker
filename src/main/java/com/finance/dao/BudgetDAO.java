package com.finance.dao;

import com.finance.koneksi.Koneksi;
import com.finance.model.Budget;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BudgetDAO {

    public boolean tambahBudget(Budget b) {
        String sql = "INSERT INTO budget (id_user, id_kategori, bulan, tahun, limit_budget) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = new Koneksi().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, b.getIdUser());
            ps.setInt(2, b.getIdKategori());
            ps.setInt(3, b.getBulan());
            ps.setInt(4, b.getTahun());
            ps.setDouble(5, b.getLimitBudget());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editBudget(Budget b) {
        String sql = "UPDATE budget SET limit_budget = ? WHERE id_budget = ?";
        try (Connection conn = new Koneksi().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, b.getLimitBudget());
            ps.setInt(2, b.getIdBudget());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<Budget> getBudgetByBulan(int idUser, int bulan, int tahun) {
        List<Budget> list = new ArrayList<>();
        String sql = "SELECT b.*, k.nama_kategori, " +
                     "(SELECT COALESCE(SUM(t.nominal), 0) FROM transaksi t " +
                     " WHERE t.id_kategori = b.id_kategori AND t.id_user = b.id_user " +
                     " AND MONTH(t.tanggal) = b.bulan AND YEAR(t.tanggal) = b.tahun) AS total_terpakai " +
                     "FROM budget b JOIN kategori k ON b.id_kategori = k.id_kategori " +
                     "WHERE b.id_user = ? AND b.bulan = ? AND b.tahun = ?";
        try (Connection conn = new Koneksi().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUser);
            ps.setInt(2, bulan);
            ps.setInt(3, tahun);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Budget b = new Budget(
                    rs.getInt("id_budget"),
                    rs.getInt("id_user"),
                    rs.getInt("id_kategori"),
                    rs.getInt("bulan"),
                    rs.getInt("tahun"),
                    rs.getDouble("limit_budget")
                );
                b.setTotalTerpakai(rs.getDouble("total_terpakai"));
                b.setNamaKategori(rs.getString("nama_kategori"));
                list.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public Budget cekBudget(int idUser, int idKategori, int bulan, int tahun) {
        List<Budget> list = getBudgetByBulan(idUser, bulan, tahun);
        for (Budget b : list) {
            if (b.getIdKategori() == idKategori) {
                return b;
            }
        }
        return null;
    }

    public List<Budget> getAllBudget() {
    List<Budget> list = new ArrayList<>();
    // Sesuaikan query dengan nama tabel dan kolom yang benar di database Anda
    String sql = "SELECT b.*, k.nama_kategori FROM budget b JOIN kategori k ON b.id_kategori = k.id_kategori";
    
    try (Connection conn = new com.finance.koneksi.Koneksi().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Budget b = new Budget();
            b.setIdBudget(rs.getInt("id_budget"));
            b.setIdKategori(rs.getInt("id_kategori"));
            b.setNamaKategori(rs.getString("nama_kategori")); // Pastikan field ini ada
            b.setLimitBudget(rs.getDouble("limit_budget"));
            b.setBulan(rs.getInt("bulan"));
            b.setTahun(rs.getInt("tahun"));
            list.add(b);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}
    public List<Budget> getAllBudgetByUser(int idUser) {
    List<Budget> list = new ArrayList<>();
    // Join dengan kategori agar nama_kategori muncul
    String sql = "SELECT b.*, k.nama_kategori FROM budget b " +
                 "JOIN kategori k ON b.id_kategori = k.id_kategori " +
                 "WHERE b.id_user = ?";
    
    try (Connection conn = new Koneksi().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, idUser);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Budget b = new Budget();
            b.setNamaKategori(rs.getString("nama_kategori"));
            b.setLimitBudget(rs.getDouble("limit_budget"));
            b.setBulan(rs.getInt("bulan"));
            b.setTahun(rs.getInt("tahun"));
            list.add(b);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}
}