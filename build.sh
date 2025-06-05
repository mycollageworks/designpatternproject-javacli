#!/bin/bash

echo "🛠️  Compiling Java CLI NoteManager..."

# Buat direktori out jika belum ada
mkdir -p out

# Tentukan classpath separator tergantung OS
SEP=":"
case "$(uname -s)" in
   MINGW*|MSYS*|CYGWIN*|Windows_NT)
     SEP=";"
     ;;
esac

# Deteksi semua file Java
JAVA_FILES=$(find . -name "*.java")

# Compile semua file Java
javac -cp "lib/gson-2.10.1.jar" -d out $JAVA_FILES

if [ $? -ne 0 ]; then
  echo "❌ Compile failed!"
  exit 1
fi

echo "✅ Compilation success!"

# Salin file .env ke direktori out
if [ -f ".env" ]; then
  cp .env out/
  echo "📦 .env copied to out/"
else
  echo "⚠️ .env file not found! Skipping copy..."
fi

# Jalankan aplikasi
echo "🚀 Running CLI..."
java -cp "out${SEP}lib/gson-2.10.1.jar" Main
if [ $? -ne 0 ]; then
  echo "❌ Application failed to run!"
  exit 1
fi