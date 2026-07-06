package com.finance.dao;

import com.finance.koneksi.Koneksi;
import com.finance.model.Kategori;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KategoriDAO {

    public List<Kategori> getAllKategori() {
        List<Kategori> list = new ArrayList<>();
        String sql = "SELECT * FROM kategori ORDER BY nama_kategori";
        try (Connection conn = new Koneksi().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Kategori(
                    rs.getInt("id_kategori"),
                    rs.getString("nama_kategori"),
                    rs.getString("jenis")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Dipakai untuk mengisi JComboBox kategori sesuai jenis yang dipilih user
    public List<Kategori> getKategoriByJenis(String jenis) {
        List<Kategori> list = new ArrayList<>();
        String sql = "SELECT * FROM kategori WHERE jenis = ? ORDER BY nama_kategori";
        try (Connection conn = new Koneksi().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, jenis);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Kategori(
                    rs.getInt("id_kategori"),
                    rs.getString("nama_kategori"),
                    rs.getString("jenis")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean tambahKategori(Kategori k) {
        String sql = "INSERT INTO kategori (nama_kategori, jenis) VALUES (?, ?)";
        try (Connection conn = new Koneksi().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, k.getNamaKategori());
            ps.setString(2, k.getJenis());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editKategori(Kategori k) {
        String sql = "UPDATE kategori SET nama_kategori = ?, jenis = ? WHERE id_kategori = ?";
        try (Connection conn = new Koneksi().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, k.getNamaKategori());
            ps.setString(2, k.getJenis());
            ps.setInt(3, k.getIdKategori());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hapusKategori(int idKategori) {
        String sql = "DELETE FROM kategori WHERE id_kategori = ?";
        try (Connection conn = new Koneksi().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idKategori);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            // Kalau kategori masih dipakai transaksi, FK constraint akan menolak — tangkap di sini
            e.printStackTrace();
            return false;
        }
    }
}