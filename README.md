# 🎯 IQ Puzzler Pro
## Tugas Kecil 1 IF2211 Strategi Algoritma

---

## 📚 General Information
**IQ Puzzler Pro** Tugas ini dikembangkan sebagai bagian dari Tugas Kecil 1 untuk mata kuliah **Strategi Algoritma**. Aplikasi ini menggunakan algoritma *Brute Force Backtracking* untuk menyelesaikan teka-teki penyusunan blok pada papan permainan IQ Puzzler.

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
Aplikasi ini memiliki beberapa fitur pada GUI, yaitu: 

1. **Upload File** 📁  
   - Memuat konfigurasi papan dan blok dari file eksternal (.txt).  
2. **Solve** 🧩  
   - Menggunakan algoritma *Brute Force Backtracking* untuk menemukan solusi.  
3. **Save as text** 📝  
   - Menyimpan solusi yang ditemukan dalam format teks.  
4. **Simpan as image** 🖼️  
   - Mengonversi solusi menjadi gambar dan menyimpannya.  
5. **Reset** 🔄  
   - Mengatur ulang papan ke kondisi awal untuk mencoba solusi baru. (Reset hanya dapat digunakan setelah solusi ditemukan atau belum ditemukan, selama program masih mencari solusi, reset tidak berfungsi) 
---
