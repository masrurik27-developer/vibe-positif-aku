# ✨ Vibes Positif+ — Build Otomatis via GitHub Actions

---

## 📁 Isi Project Ini

```
VibesPositif/
│
├── .github/
│   └── workflows/
│       └── build-android.yml   ← 🔴 Workflow utama (jangan diedit)
│
├── app/
│   ├── build.gradle.kts        ← Config build + signing
│   ├── proguard-rules.pro
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml
│           ├── java/com/vibes/positif/
│           │   ├── VibesApp.kt
│           │   └── MainActivity.kt   ← Ada marker ADS & BILLING
│           └── res/
│
├── gradle/
│   ├── libs.versions.toml      ← Semua versi dependency
│   └── wrapper/
│       └── gradle-wrapper.properties
│
├── scripts/
│   ├── generate-keystore.sh    ← Jalankan SEKALI untuk buat kunci
│   └── minify-google-services.sh
│
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradlew                     ← Wajib ada! Script build Linux/Mac
├── gradlew.bat                 ← Script build Windows
└── .gitignore
```

---

## 🔑 Sistem Marker ADS & BILLING

Tambahkan penanda ini di kode Kotlin dan build.gradle kamu. Workflow akan otomatis comment/uncomment saat build.

### Di file Kotlin / Java:
```kotlin
// ── ADS_START ─────────────────────────────────────────────
import com.google.android.gms.ads.MobileAds
// kode ads lainnya...
// ── ADS_END ───────────────────────────────────────────────

// ── BILLING_START ─────────────────────────────────────────
import com.android.billingclient.api.BillingClient
// kode billing lainnya...
// ── BILLING_END ───────────────────────────────────────────
```

### Di file build.gradle.kts:
```kotlin
// ── ADS_START ─────────────────────────────────────────────
implementation(libs.play.services.ads)
// ── ADS_END ───────────────────────────────────────────────

// ── BILLING_START ─────────────────────────────────────────
implementation(libs.billing.ktx)
// ── BILLING_END ───────────────────────────────────────────
```

---

## 🚀 PANDUAN LENGKAP — Upload & Build

---

### LANGKAH 1 — Daftar GitHub (kalau belum punya)

1. Buka **github.com**
2. Klik **Sign up** → isi email, username, password
3. Verifikasi email
4. ✅ Akun GitHub siap

---

### LANGKAH 2 — Buat Repository Baru

1. Login ke GitHub
2. Klik tombol **"+"** di pojok kanan atas
3. Pilih **"New repository"**
4. Isi:
   - Repository name: `vibes-positif`
   - Pilih **Private**
   - **Jangan centang** "Add a README file"
5. Klik **"Create repository"**
6. ✅ Repository kosong siap

---

### LANGKAH 3 — Install Git di Komputer

**Windows:**
1. Buka **git-scm.com/download/win**
2. Download dan install (klik Next terus)
3. Setelah install, cari **"Git Bash"** di Start Menu

**Mac:**
1. Buka Terminal (Cmd+Space → ketik Terminal)
2. Ketik: `git --version`
3. Kalau belum ada → klik Install saat diminta

---

### LANGKAH 4 — Upload Project ke GitHub

Buka **Git Bash** (Windows) atau **Terminal** (Mac/Linux), lalu ketik perintah-perintah ini satu per satu:

```bash
# Masuk ke folder project (sesuaikan path-nya)
cd /path/ke/folder/VibesPositif

# Inisialisasi git
git init

# Tambah semua file
git add .

# Commit pertama
git commit -m "Upload project Vibes Positif pertama kali"

# Ganti main sebagai branch utama
git branch -M main

# Hubungkan ke GitHub (ganti USERNAME dengan username GitHub kamu)
git remote add origin https://github.com/USERNAME/vibes-positif.git

# Upload!
git push -u origin main
```

Saat diminta username & password:
- Username: username GitHub kamu
- Password: **bukan password GitHub biasa**, tapi Personal Access Token

**Cara buat Personal Access Token:**
1. GitHub → klik foto profil → **Settings**
2. Scroll paling bawah → **Developer settings**
3. **Personal access tokens** → **Tokens (classic)**
4. **Generate new token (classic)**
5. Centang **repo** → **Generate token**
6. Copy token → gunakan sebagai password

---

### LANGKAH 5 — Siapkan Firebase (untuk google-services.json)

1. Buka **firebase.google.com** → login pakai Google
2. **Create a project** → nama: `vibes-positif`
3. Klik ikon Android di "Add app"
4. Package name: `com.vibes.positif`
5. Download **google-services.json**
6. **Jangan taruh di folder project!** (sudah ada di .gitignore)

**Ubah google-services.json jadi 1 baris:**

Mac/Linux:
```bash
python3 -c "import json; print(json.dumps(json.load(open('google-services.json'))))"
```

Windows (PowerShell):
```powershell
Get-Content google-services.json | ConvertFrom-Json | ConvertTo-Json -Compress
```

Atau pakai script yang sudah ada:
```bash
./scripts/minify-google-services.sh path/ke/google-services.json
```

Simpan hasilnya (1 baris panjang) — ini akan dipakai di Langkah 7.

---

### LANGKAH 6 — Buat Keystore (Kunci Tanda Tangan)

Jalankan script yang sudah disediakan:

**Mac/Linux:**
```bash
chmod +x scripts/generate-keystore.sh
./scripts/generate-keystore.sh
```

**Windows (Git Bash):**
```bash
bash scripts/generate-keystore.sh
```

Script akan:
1. Menanyakan Store Password dan Key Password
2. Membuat file `vibes-release.jks`
3. Menampilkan string Base64 panjang

**⚠️ WAJIB:** Simpan file `vibes-release.jks` di Google Drive / USB drive.
Kalau hilang, kamu tidak bisa update aplikasi di Play Store selamanya!

---

### LANGKAH 7 — Tambah Secrets di GitHub

1. Buka repo di GitHub: `github.com/USERNAME/vibes-positif`
2. Klik tab **Settings** (paling kanan)
3. Menu kiri: **Secrets and variables** → **Actions**
4. Klik **"New repository secret"**

Tambahkan **5 secrets** ini:

| Nama Secret | Isi | Dari mana |
|---|---|---|
| `GOOGLE_SERVICES_JSON` | Hasil minify Langkah 5 | Script minify-google-services |
| `KEYSTORE_BASE64` | String Base64 panjang | Output script generate-keystore |
| `SIGNING_KEY_ALIAS` | `vibes-key` | Sudah ditentukan |
| `SIGNING_KEY_PASSWORD` | Password key kamu | Yang kamu input di script |
| `SIGNING_STORE_PASSWORD` | Password store kamu | Yang kamu input di script |

---

### LANGKAH 8 — Jalankan Build!

1. Buka repo di GitHub
2. Klik tab **"Actions"**
3. Di menu kiri, klik **"🚀 Build Android APK & AAB"**
4. Klik tombol **"Run workflow"** (pojok kanan atas)
5. Muncul form — isi:
   - **enable_ads**: `true` atau `false`
   - **enable_billing**: `true` atau `false`
   - **version_name**: `1.0.0`
6. Klik tombol hijau **"Run workflow"**
7. Tunggu 5–15 menit
8. ✅ Build selesai!

---

### LANGKAH 9 — Download Hasil Build

1. Klik tab **Actions**
2. Klik nama workflow yang sudah selesai (ada centang hijau ✅)
3. Scroll ke bawah → lihat bagian **Artifacts**
4. Download sesuai kebutuhan:

| Artifact | File | Untuk apa |
|---|---|---|
| `RELEASE-APK-v1.0.0-...` | `.apk` signed | Install langsung ke HP Android |
| `RELEASE-AAB-v1.0.0-...` | `.aab` signed | Upload ke Google Play Store |
| `DEBUG-APK-v1.0.0` | `.apk` debug | Testing internal tim |

---

## 🔄 Update Aplikasi

Setiap kali edit kode dan mau build ulang:

```bash
git add .
git commit -m "Update fitur X"
git push
```

Lalu jalankan workflow lagi dari tab Actions.

---

## ❓ Troubleshooting

| Error | Penyebab | Solusi |
|---|---|---|
| `google-services.json not found` | Secret salah format | Pastikan 1 baris JSON valid |
| `Keystore tampered` | Password salah | Cek secret SIGNING_STORE_PASSWORD |
| `gradlew: Permission denied` | Izin file hilang | Sudah ditangani otomatis di workflow |
| `Could not find :app` | settings.gradle salah | Pastikan ada `include(":app")` |
| Build merah semua | Dependency error | Cek app/build.gradle.kts |

---

## 📞 Penting Diingat

- File `google-services.json` → **JANGAN di-commit** ke GitHub
- File `*.jks` → **JANGAN di-commit** ke GitHub
- Kedua file sudah diamankan di `.gitignore`
- Gunakan **GitHub Secrets** untuk semua data sensitif
