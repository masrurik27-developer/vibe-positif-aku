#!/bin/bash
# Ubah google-services.json jadi 1 baris untuk GitHub Secret
FILE=${1:-"google-services.json"}
if [ ! -f "$FILE" ]; then
  echo "Usage: ./minify-google-services.sh path/to/google-services.json"
  exit 1
fi
echo "=== Copy baris ini ke Secret GOOGLE_SERVICES_JSON ==="
python3 -c "import sys,json; print(json.dumps(json.load(open('$FILE'))))"
echo "==="
