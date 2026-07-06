-- 1. Membuat database
CREATE DATABASE IF NOT EXISTS db_finance;
USE db_finance;

-- 2. Tabel Users (Manajemen Akses)
CREATE TABLE users (
    id_user INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nama VARCHAR(100),
    role ENUM('admin', 'user') DEFAULT 'user',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Tabel Kategori (Pemasukan atau Pengeluaran)
CREATE TABLE kategori (
    id_kategori INT AUTO_INCREMENT PRIMARY KEY,
    nama_kategori VARCHAR(50) NOT NULL,
    jenis ENUM('Pemasukan', 'Pengeluaran') NOT NULL
);

-- 4. Tabel Transaksi (Inti Aplikasi)
CREATE TABLE transaksi (
    id_transaksi INT AUTO_INCREMENT PRIMARY KEY,
    id_user INT NOT NULL,
    id_kategori INT NOT NULL,
    nominal DOUBLE NOT NULL,
    keterangan TEXT,
    tanggal DATE NOT NULL,
    FOREIGN KEY (id_user) REFERENCES users(id_user) ON DELETE CASCADE,
    FOREIGN KEY (id_kategori) REFERENCES kategori(id_kategori) ON DELETE CASCADE
);

-- 5. Tabel Budget (Target Pengeluaran)
CREATE TABLE budget (
    id_budget INT AUTO_INCREMENT PRIMARY KEY,
    id_user INT NOT NULL,
    id_kategori INT NOT NULL,
    bulan INT NOT NULL,
    tahun INT NOT NULL,
    limit_budget DOUBLE NOT NULL,
    FOREIGN KEY (id_user) REFERENCES users(id_user) ON DELETE CASCADE,
    FOREIGN KEY (id_kategori) REFERENCES kategori(id_kategori) ON DELETE CASCADE,
    -- Mencegah budget ganda pada kategori/waktu yang sama
    UNIQUE KEY unique_budget (id_user, id_kategori, bulan, tahun)
);

-- 6. Tabel Activity Log (Audit Trail - Opsional)
CREATE TABLE activity_log (
    id_log INT AUTO_INCREMENT PRIMARY KEY,
    id_user INT NOT NULL,
    aktivitas VARCHAR(255) NOT NULL,
    tanggal TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_user) REFERENCES users(id_user)
);

-- 7. Data Dummy (Seed Data)
-- Admin User
INSERT INTO users (username, password, nama, role) VALUES 
('admin', 'admin123', 'Administrator', 'admin');

-- Kategori Default
INSERT INTO kategori (nama_kategori, jenis) VALUES 
('Gaji', 'Pemasukan'),
('Bonus', 'Pemasukan'),
('Makanan', 'Pengeluaran'),
('Transportasi', 'Pengeluaran'),
('Tagihan', 'Pengeluaran');
