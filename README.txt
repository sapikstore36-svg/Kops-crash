ARENA STRIKE ONLINE — starter project

ISI:
- public/index.html : game client 3D untuk browser Android/Acode
- server.js         : server room realtime Socket.IO (maksimal 8 pemain)
- package.json      : dependensi Node.js

MENJALANKAN SERVER:
1. Install Node.js pada komputer/VPS/hosting Node.js.
2. Ekstrak folder ini.
3. Jalankan: npm install
4. Jalankan: npm start
5. Buka http://IP-SERVER:3000/ pada browser.
6. Pada game, masukkan URL server yang sama di semua perangkat.

PENTING:
- Acode adalah editor, bukan server publik. Untuk mabar lewat internet, deploy folder ini ke hosting Node.js yang mendukung WebSocket/Socket.IO.
- Untuk uji coba satu Wi-Fi, jalankan server pada komputer yang satu jaringan; masukkan http://IP-KOMPUTER:3000.
- Fitur multiplayer adalah prototipe: room 8 pemain, posisi realtime, loot, damage antarpemain, dan event tembakan.
- Belum termasuk matchmaking publik, akun permanen, anti-cheat, sinkronisasi animasi kompleks, suara, atau sistem produksi.
- Three.js dan Socket.IO client dimuat dari CDN sehingga browser perlu internet.
- Jika browser menolak orientasi terkunci, putar perangkat secara manual.
