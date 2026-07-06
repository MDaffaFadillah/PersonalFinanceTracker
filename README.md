# 💰 Personal Finance Manager

Aplikasi manajemen keuangan pribadi yang dirancang untuk membantu pengguna dalam melacak transaksi, menetapkan anggaran bulanan, dan mengelola kategori pengeluaran secara efisien dengan antarmuka yang intuitif.

---

## 🚀 Fitur Utama

* **Sistem Autentikasi**: Login aman dengan manajemen *session* agar data setiap pengguna tetap privat.
* **Manajemen Kategori**: CRUD lengkap untuk mengategorikan pemasukan dan pengeluaran.
* **Budgeting Pintar**: Atur batas anggaran per kategori dan dapatkan peringatan otomatis jika transaksi melebihi limit.
* **Pencatatan Transaksi**: Rekapitulasi pengeluaran dan pemasukan dengan validasi data *real-time*.
* **Data Personal**: Setiap user hanya dapat melihat dan mengelola data milik mereka sendiri.

---

## 🛠️ Persyaratan Sistem

* **Java Development Kit (JDK)**: Versi 8 atau lebih baru.
* **IDE**: NetBeans IDE.
* **Database**: MySQL (pastikan *connector* JDBC sudah terpasang).

---

## 📥 Cara Penggunaan

### 1. Clone Proyek
Clone atau unduh proyek ini ke komputer Anda terlebih dahulu.

### 2. Setup Database
1. Buat database baru di MySQL.
2. Impor file `.sql` (jika tersedia) atau buat tabel yang diperlukan (`User`, `Kategori`, `Budget`, `Transaksi`).

### 3. Konfigurasi Koneksi
Sesuaikan file `Koneksi.java` dengan kredensial database Anda (nama database, *username*, dan *password*).

### 4. Menjalankan Aplikasi
1. Buka proyek di NetBeans IDE.
2. Klik kanan pada file `Main.java`.
3. Pilih **Run File** (atau tekan tombol `Shift + F6`).

### 5. Mulai Menggunakan
Lakukan **Login** terlebih dahulu. Setelah berhasil masuk, Anda akan diarahkan ke halaman **Dashboard** untuk mulai mengelola keuangan.

---

## 💻 Tech Stack

* **Language**: Java
* **UI**: Java Swing (NetBeans GUI Builder)
* **Database**: MySQL
* **Architecture**: DAO (Data Access Object) Pattern
