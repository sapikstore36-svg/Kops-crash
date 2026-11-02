# ALPIN67 WA

Starter Android messenger menggunakan Kotlin, Jetpack Compose, Firebase Authentication, dan Cloud Firestore.

## Persiapan Firebase
1. Buat project di https://console.firebase.google.com/
2. Tambahkan Android app dengan package `com.alpin67.wa`.
3. Unduh `google-services.json`, lalu letakkan di folder `app/` (sejajar dengan `build.gradle.kts`).
4. Di Firebase Authentication, aktifkan provider **Email/Password**.
5. Buat database Cloud Firestore.
6. Publikasikan isi `firestore.rules` ke tab Rules Firestore.

## Menjalankan
- Buka folder proyek ini dengan Android Studio versi yang mendukung AGP 8.7.
- Pastikan Android SDK 35 terpasang.
- Sinkronkan Gradle, lalu jalankan pada emulator/perangkat Android.
- Untuk APK: **Build > Build Bundle(s) / APK(s) > Build APK(s)**.

## Mengirim pesan antarperangkat
1. Daftarkan dua akun berbeda pada dua perangkat.
2. Salin UID akun penerima dari layar aplikasi.
3. Masukkan UID itu di kolom UID penerima, lalu tekan **Buka chat**.
4. Kirim pesan. Kedua perangkat akan menerima pembaruan secara realtime.

## Catatan
- Ini starter project, belum memuat enkripsi end-to-end, panggilan suara/video, notifikasi push, unggah media, pemulihan akun, atau pencarian kontak.
- UID adalah pengenal publik aplikasi; jangan pernah membagikan password atau kode OTP.
- Untuk produksi, tambahkan App Check, pembatasan spam/rate, validasi lebih ketat, kebijakan privasi, dan pengujian Firestore Rules.
- File `google-services.json` tidak disertakan karena harus dibuat khusus untuk project Firebase milikmu.
