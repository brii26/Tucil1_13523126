# 🎯 IQ Puzzler Pro
## Tugas Kecil 1 IF2211 Strategi Algoritma

---

## 📚 General Information
**IQ Puzzler Pro** Proyek ini dikembangkan sebagai bagian dari Proyek Kecil 1 untuk mata kuliah **Strategi Algoritma**. Aplikasi ini menggunakan algoritma *Brute Force Backtracking* untuk menyelesaikan teka-teki penyusunan blok pada papan permainan IQ Puzzler.

---

## 👨‍💻 Author
- **Name:** Brian Ricardo Tamin
- **Student ID:** 13523126
- **Class:** K03

---

## 🚀 Run Program
Ikuti langkah-langkah berikut untuk menjalankan aplikasi:

### 1. **Unduh dan Instal Maven**  
Maven digunakan untuk mengelola dependensi proyek ini. Unduh Maven melalui tautan berikut:  
- [Unduh Maven](https://maven.apache.org/download.cgi)  

Tambahkan *path* Maven ke *Environment Variables* agar dapat digunakan di terminal.

### 2. **Unduh JavaFX versi 17 LTS**  
Proyek ini menggunakan JavaFX untuk *Graphical User Interface* (GUI). Unduh JavaFX 17 LTS di sini:  
- [Unduh JavaFX 17](https://gluonhq.com/products/javafx/)  

Ekstrak file yang telah diunduh dan pastikan Anda mengetahui lokasi folder JavaFX tersebut.

### 3. **Menjalankan Program**  
Masuk ke direktori proyek melalui terminal Anda, lalu jalankan perintah berikut secara berurutan:  
```bash
mvn clean compile
mvn javafx:run

```
- `mvn clean compile` membersihkan dan mengkompilasi ulang kode.  
- `mvn javafx:run` menjalankan aplikasi.  

---


## 🛠️ Fitur  
Aplikasi ini menawarkan beberapa fitur berguna untuk membantu pengguna menyelesaikan teka-teki IQ Puzzler:

1. **Unggah File** 📁  
   - Memuat konfigurasi papan dan blok dari file eksternal.  
2. **Selesaikan!** 🧩  
   - Menggunakan algoritma *Brute Force Backtracking* untuk menemukan solusi.  
3. **Simpan sebagai Teks** 📝  
   - Menyimpan solusi yang ditemukan dalam format teks.  
4. **Simpan sebagai Gambar** 🖼️  
   - Mengonversi solusi menjadi gambar dan menyimpannya.  
5. **Atur Ulang** 🔄  
   - Mengatur ulang papan ke kondisi awal untuk mencoba solusi baru. 
---
