# FinanceTable

Product Requirement Document (PRD): Aplikasi Keuangan "FinanceTable"

**1. Pendahuluan**

Aplikasi ini dirancang untuk pengguna yang menginginkan kontrol penuh dan efisiensi dalam mencatat transaksi keuangan secara manual. Berbeda dengan aplikasi keuangan otomatis, aplikasi ini menonjolkan antarmuka berbasis tabel (spreadsheet-style) untuk mempercepat input data serta memberikan konteks waktu dan lokasi yang akurat untuk setiap transaksi.

**2. Deskripsi Produk**

"FinanceTable" adalah aplikasi Android native yang memungkinkan pengguna untuk memasukkan data pemasukan dan pengeluaran secara cepat melalui baris-baris tabel, menghitung saldo secara otomatis, serta mencatat detail temporal (tanggal) dan spasial (lokasi) untuk analisis finansial yang lebih mendalam.

**3. Fitur Utama (Functional Requirements)**

Fitur	Deskripsi
Input Tabel Dinamis	Antarmuka utama berupa tabel yang dapat ditambah barisnya secara manual. Setiap baris berisi kolom: Tanggal, Deskripsi, Nominal, dan Lokasi. 
Kalkulasi Otomatis	Sistem secara real-time menghitung total pemasukan, total pengeluaran, dan saldo akhir berdasarkan data yang diinput di tabel. 
Pencatatan Konteks Spasial	Input lokasi berbasis teks manual yang dilengkapi dengan fitur autocomplete untuk mempermudah pemilihan tempat transaksi (toko, restoran, dsb). 
Manajemen Waktu	Integrasi dengan DatePicker modal untuk memastikan input tanggal yang valid dan tersinkronisasi dengan kalender sistem. 
Mode Offline-First	Data disimpan sepenuhnya di memori lokal perangkat menggunakan database relasional sehingga aplikasi tetap dapat digunakan tanpa internet. 
Formatting Mata Uang	Input angka pada kolom nominal secara otomatis terformat dengan pemisah ribuan (thousand separator) sesuai lokal pengguna. 

**4. Persyaratan Antarmuka (UI/UX)**

•	Paradigma UI: Menggunakan Jetpack Compose (Declarative UI) untuk memastikan antarmuka yang reaktif terhadap perubahan data. 

•	Desain Tabel: Implementasi menggunakan LazyColumn untuk baris dan Row untuk kolom dengan lebar yang proporsional menggunakan Modifier.weight. 

•	Header Statis: Baris judul tabel (Header) tetap berada di posisi atas saat pengguna melakukan scrolling pada daftar transaksi yang panjang. 

•	Visualisasi Status: Warna teks berbeda untuk pemasukan (hijau) dan pengeluaran (merah) guna memudahkan pemindaian data secara visual. 

**5. Arsitektur Data (Database Schema)**

Menggunakan Room Database dengan entitas tunggal Transaction yang memiliki atribut: 

•	id: Primary Key (Auto-generate).

•	description: String (Keterangan transaksi).

•	amount: Double/Long (Nominal uang).

•	type: Boolean (Income = 1, Expense = 0).

•	timestamp: Long (Waktu transaksi dalam milidetik).

•	location_name: String (Nama tempat transaksi).

•	latitude/longitude: Double (Koordinat lokasi untuk kebutuhan mapping masa depan).
________________________________________
**6. Tech Stack Lengkap**

Berdasarkan analisis performa dan efisiensi memori (penggunaan memori native Android 15-25% lebih rendah dibanding Flutter), berikut adalah tumpukan teknologi yang digunakan: 

A. Core Development

•	Language: Kotlin (Bahasa utama yang didukung Google untuk pengembangan native modern). 

•	Min SDK: Android 7.0 (API 24) untuk memastikan kompatibilitas luas dengan performa optimal. 


B. Antarmuka Pengguna (UI Layer)

•	Toolkit: Jetpack Compose (Untuk membangun UI deklaratif yang ringan dan mudah dipelihara). 

•	Design System: Material Design 3 (M3) (Implementasi standar desain terbaru dari Google termasuk Dark Mode otomatis). 

•	Navigation: Navigation Compose (Manajemen perpindahan layar yang type-safe). 

C. Arsitektur & Logika Bisnis

•	Architecture Pattern: MVVM (Model-View-ViewModel) (Pemisahan antara UI, logika, dan data untuk kemudahan testing). 

•	Dependency Injection: Hilt (Untuk manajemen dependensi library secara otomatis). 

•	Asynchronous Processing: Kotlin Coroutines & Flow (Menangani operasi database di latar belakang tanpa membuat UI freeze). 


D. Penyimpanan Data (Persistence)

•	Database: Room Persistence Library (Abstraksi SQLite yang memberikan pengecekan kueri pada saat kompilasi). 

•	Time Handling: Kotlinx Datetime (Library modern untuk pengelolaan zona waktu dan format tanggal). 

E. API & Layanan Pihak Ketiga

•	Location Services: Google Play Services (Fused Location Provider) untuk mendapatkan lokasi perangkat secara akurat. 

•	Autocomplete Search: Stadia Maps SDK atau Google Places API untuk fitur saran tempat otomatis pada input lokasi. 
________________________________________

**7. Metrik Keberhasilan**

1.	Akurasi Data: Penghitungan saldo harus tepat 100% tanpa kesalahan pembulatan angka desimal (menggunakan BigDecimal dalam logika internal). 

2.	Responsivitas UI: Recomposition tabel tidak boleh menyebabkan jank (frame rate minimal 60 FPS pada pengguliran daftar transaksi). 

3.	Integritas Data: Data tidak boleh hilang saat aplikasi ditutup paksa atau perangkat dimatikan (dijamin oleh ACID property dari Room Database).
