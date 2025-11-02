# TP6DPBO2425C2

## Janji
Saya Repa Pitriani dengan NIM 2402499 mengerjakan Tugas Praktikum 5 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya, maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

## Desain

Program ini merupakan implementasi game **Flappy Bird** menggunakan **Java Swing** dengan konsep **pemrograman berorientasi objek (OOP)**.  
Program mencakup **5 class utama**, yaitu `App`, `Logic`, `View`, `Player`, dan `Pipe`.
Berikut penjelasan singkat dari masing-masing class:

### 1. `App`
Kelas utama (entry point) yang menjalankan program.  
Bertugas membuat jendela (`JFrame`), mengatur ukuran layar, menghubungkan `Logic` dengan `View`, lalu menampilkan game ke pengguna.

### 2. `Logic`
Kelas yang mengatur seluruh **logika permainan**, meliputi:
- pergerakan burung dan pipa,  
- sistem skor,  
- deteksi tabrakan,  
- serta transisi antar-state: **menu → bermain → game over**.
  
Kelas ini juga menangani input dari **keyboard** (`spasi`, `R`, `ESC`) dan **mouse** (klik tombol **Play** atau **Exit**).

### 3. `View`
Menangani bagian **tampilan visual** game menggunakan `Graphics`.  
Kelas ini menggambar:
- background,  
- burung (player),  
- pipa,  
- skor,  
- serta tampilan menu dan game over.

Selain itu, posisi teks dan tombol diatur agar selalu berada di tengah layar.

### 4. `Player`
Mewakili **karakter utama (burung)**.  
Menyimpan posisi, ukuran, gambar, dan kecepatan jatuh (gravitasi).  
Player dapat melompat saat tombol spasi ditekan, dan posisinya diperbarui setiap frame.

### 5. `Pipe`
Mewakili **pipa** yang menjadi rintangan di permainan.  
Menyimpan posisi, ukuran, gambar, dan arah pergerakan pipa.  
Kelas ini juga menandai apakah pipa sudah dilewati player agar skor dapat bertambah.

## Penjelasan

Saat program dijalankan:
1. Muncul **menu utama** dengan dua tombol:
   - **Play** untuk memulai permainan.  
   - **Exit** untuk keluar dari program.
2. Ketika **Play** diklik, permainan dimulai:
   - Burung mulai jatuh karena gravitasi.  
   - Tekan **spasi** untuk membuat burung melompat.  
   - Pipa muncul dari sisi kanan layar dan bergerak ke kiri.
3. Setiap kali player berhasil melewati sepasang pipa, skor bertambah satu.
4. Jika burung menabrak pipa atau jatuh ke tanah, muncul tampilan **Game Over**.
5. Saat game over:
   - Tekan **R** untuk mengulang permainan.  
   - Tekan **ESC** untuk kembali ke menu utama.

## Dokumentasi

