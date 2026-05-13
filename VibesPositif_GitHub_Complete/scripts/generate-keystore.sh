#!/bin/bash
# Jalankan SEKALI di komputer lokal untuk membuat keystore signing

echo "=== Generate Keystore ==="
read -p "Store Password (min 6 karakter): " STORE_PASS
read -p "Key Password   (min 6 karakter): " KEY_PASS
read -p "Nama kamu / perusahaan          : " ORG_NAME

keytool -genkey -v \
  -keystore vibes-release.jks \
  -alias vibes-key \
  -keyalg RSA -keysize 2048 -validity 10000 \
  -storepass "$STORE_PASS" -keypass "$KEY_PASS" \
  -dname "CN=${ORG_NAME},OU=Mobile,O=${ORG_NAME},L=Jakarta,S=DKI,C=ID"

echo ""
echo "=== Keystore dibuat: vibes-release.jks ==="
echo "Konversi ke Base64 untuk GitHub Secret:"
echo "---"
base64 -i vibes-release.jks | tr -d '\n'
echo ""
echo "---"
echo "Simpan di GitHub Secrets:"
echo "  KEYSTORE_BASE64        = (baris panjang di atas)"
echo "  SIGNING_KEY_ALIAS      = vibes-key"
echo "  SIGNING_KEY_PASSWORD   = $KEY_PASS"
echo "  SIGNING_STORE_PASSWORD = $STORE_PASS"
echo "BACKUP file vibes-release.jks ke Google Drive / USB!"
