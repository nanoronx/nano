package com.example.data

import com.example.model.AssessmentQuestion
import com.example.model.LearningObjective
import com.example.model.SubjectPreset

object CurriculumBank {

    val ROMBEL_GROUPS = mapOf(
        "VII" to listOf("VII A", "VII B", "VII C", "VII D", "VII E", "VII F"),
        "VIII" to listOf("VIII A", "VIII B", "VIII C", "VIII D", "VIII E", "VIII F"),
        "IX" to listOf("IX A", "IX B", "IX C", "IX D", "IX E", "IX F")
    )

    fun getGradePrefixFromRombel(rombelName: String): String {
        val upper = rombelName.trim().uppercase()
        return when {
            upper.startsWith("IX") || upper.startsWith("9") -> "9"
            upper.startsWith("VIII") || upper.startsWith("8") -> "8"
            else -> "7"
        }
    }

    fun adaptTpCodesToRombel(tpList: List<LearningObjective>, rombelName: String): List<LearningObjective> {
        val prefix = getGradePrefixFromRombel(rombelName)
        return tpList.mapIndexed { idx, tp ->
            val subNum = if (tp.code.contains('.')) tp.code.substringAfter('.') else (idx + 1).toString()
            tp.copy(code = "$prefix.$subNum")
        }
    }

    val SUBJECTS: Map<String, SubjectPreset> = mapOf(
        "Matematika" to SubjectPreset(
            fase = "Fase D",
            cp = "Pada akhir Fase D, peserta didik dapat membaca, menulis, dan membandingkan bilangan bulat, bilangan rasional dan irasional, bilangan desimal. Mereka dapat menerapkan operasi aritmetika pada bilangan riil, memberikan estimasi/perkiraan dalam menyelesaikan masalah berkaitan dengan literasi finansial, menggunakan rasio dan proporsi, serta mengenali, memprediksi dan menggeneralisasi pola dalam bentuk susunan benda dan bentuk aljabar.",
            tps = listOf(
                LearningObjective(
                    id = "tp1",
                    code = "7.1",
                    title = "Operasi bilangan bulat",
                    jp = 12,
                    materi = "Bilangan Bulat Positif dan Negatif, Garis Bilangan, Operasi Hitung Campuran, KPK dan FPB",
                    indikator = "Peserta didik mampu membandingkan dan melakukan operasi hitung campuran bilangan bulat serta menyelesaikan masalah kontekstual.",
                    weeks = listOf(1, 2, 3, 4),
                    aktivitas = "Eksplorasi konsep suhu dan ketinggian menggunakan garis bilangan serta diskusi kelompok pemecahan masalah operasi bilangan bulat.",
                    questions = listOf(
                        AssessmentQuestion("Hasil dari operasi hitung (-15) + 28 - (-12) adalah...", listOf("1", "25", "-25", "55"), "B", "C2"),
                        AssessmentQuestion("Suhu di dalam kulkas semula -4°C. Saat listrik padam, suhu naik 3°C setiap 10 menit. Berapa suhu kulkas setelah padam selama 30 menit?", listOf("5°C", "8°C", "9°C", "13°C"), "A", "C3"),
                        AssessmentQuestion("Hasil dari (-8) × 6 : (-4) adalah...", listOf("-12", "12", "-16", "16"), "B", "C2")
                    ),
                    essay = "Dalam sebuah kompetisi matematika, jawaban benar diberi skor 4, salah -2, dan tidak dijawab -1. Jika dari 40 soal Budi menjawab benar 28 soal dan salah 7 soal, tentukan total skor Budi!"
                ),
                LearningObjective(
                    id = "tp2",
                    code = "7.2",
                    title = "Pecahan dan desimal",
                    jp = 10,
                    materi = "Bentuk Pecahan, Desimal, Persen, dan Operasi Hitung Bilangan Rasional",
                    indikator = "Peserta didik mampu mengubah bentuk pecahan ke desimal/persen dan menyelesaikan operasi hitung pecahan dalam kehidupan sehari-hari.",
                    weeks = listOf(5, 6, 7, 8),
                    aktivitas = "Diskusi kelompok memodelkan pembagian kue/kertas lipat dan latihan soal konversi pecahan ke bentuk desimal.",
                    questions = listOf(
                        AssessmentQuestion("Bentuk desimal dari pecahan 3/8 adalah...", listOf("0,325", "0,375", "0,625", "0,875"), "B", "C2"),
                        AssessmentQuestion("Hasil dari 2 1/4 + 1,5 - 3/4 adalah...", listOf("2,5", "3,0", "3,25", "3,5"), "B", "C3"),
                        AssessmentQuestion("Ibu memiliki 12,5 kg tepung terigu yang akan dimasukkan ke dalam kantong plastik berukuran 1/4 kg. Banyak kantong plastik yang dibutuhkan adalah...", listOf("25 kantong", "40 kantong", "50 kantong", "60 kantong"), "C", "C3")
                    ),
                    essay = "Sebuah lahan seluas 600 m² akan ditanami jagung 1/3 bagian, kacang tanah 2/5 bagian, dan sisanya dibuat kolam ikan. Hitunglah luas kolam ikan tersebut!"
                ),
                LearningObjective(
                    id = "tp3",
                    code = "7.3",
                    title = "Rasio dan proporsi",
                    jp = 8,
                    materi = "Konsep Rasio, Skala Peta, Perbandingan Senilai, dan Perbandingan Berbalik Nilai",
                    indikator = "Peserta didik mampu membedakan dan menyelesaikan permasalahan perbandingan senilai dan berbalik nilai serta skala.",
                    weeks = listOf(9, 10, 11),
                    aktivitas = "Mengukur denah ruang kelas untuk menghitung skala dan menganalisis tabel perbandingan senilai serta berbalik nilai.",
                    questions = listOf(
                        AssessmentQuestion("Rasio paling sederhana dari 45 menit terhadap 1,5 jam adalah...", listOf("1 : 2", "1 : 3", "2 : 3", "3 : 4"), "A", "C2"),
                        AssessmentQuestion("Jarak dua kota pada peta adalah 6 cm. Jika skala peta 1 : 1.500.000, maka jarak sebenarnya kedua kota tersebut adalah...", listOf("9 km", "45 km", "90 km", "900 km"), "C", "C3"),
                        AssessmentQuestion("Suatu pekerjaan dapat diselesaikan oleh 12 pekerja dalam waktu 20 hari. Jika ingin diselesaikan dalam 15 hari, banyak pekerja tambahan yang diperlukan adalah...", listOf("3 orang", "4 orang", "6 orang", "16 orang"), "B", "C4")
                    ),
                    essay = "Sebuah mobil membutuhkan 5 liter bensin untuk menempuh jarak 60 km. Berapa liter bensin yang dibutuhkan untuk menempuh jarak 156 km?"
                ),
                LearningObjective(
                    id = "tp4",
                    code = "7.4",
                    title = "Bentuk aljabar",
                    jp = 14,
                    materi = "Unsur Bentuk Aljabar (Variabel, Koefisien, Konstanta, Suku), Operasi Penjumlahan, Pengurangan, dan Perkalian Aljabar",
                    indikator = "Peserta didik mampu menyatakan kuantitas yang berubah-ubah ke dalam bentuk aljabar dan melakukan operasi aljabar.",
                    weeks = listOf(12, 13, 14, 15, 16),
                    aktivitas = "Memodelkan banyak barang dalam kotak menggunakan variabel dan menyederhanakan suku-suku sejenis.",
                    questions = listOf(
                        AssessmentQuestion("Bentuk sederhana dari 5x - 3y + 7 - 2x + 6y - 4 adalah...", listOf("3x + 3y + 3", "3x - 9y + 3", "7x + 3y + 3", "3x + 3y - 11"), "A", "C2"),
                        AssessmentQuestion("Koefisien dari variabel y pada bentuk aljabar 4x² - 7y + 9 adalah...", listOf("4", "7", "-7", "9"), "C", "C1"),
                        AssessmentQuestion("Hasil perkalian dari (2x + 3)(x - 4) adalah...", listOf("2x² - 5x - 12", "2x² + 5x - 12", "2x² - 11x - 12", "2x² - 5x + 12"), "A", "C3")
                    ),
                    essay = "Sebuah persegi panjang memiliki ukuran panjang (3x + 2) cm dan lebar (2x - 1) cm. Nyatakan keliling persegi panjang tersebut dalam bentuk aljabar paling sederhana!"
                )
            )
        ),
        "Informatika" to SubjectPreset(
            fase = "Fase D",
            cp = "Pada akhir Fase D, peserta didik mampu menerapkan berpikir komputasional untuk menghasilkan beberapa solusi dari persoalan dengan data diskrit bervolume kecil serta mendisposisikan berpikir komputasional dalam bidang lain, mampu memanfaatkan aplikasi surel dan peramban untuk berkomunikasi dan mencari informasi secara efektif dengan menerapkan literasi digital, memahami konsep jaringan komputer dan internet (LAN, MAN, WAN, PAN), serta mampu mengakses, mengolah, dan menganalisis data secara efisien.",
            tps = listOf(
                LearningObjective(
                    id = "tp1",
                    code = "7.1",
                    title = "Berpikir Komputasional (Algoritma & Struktur Data)",
                    jp = 12,
                    materi = "Dekomposisi, Pengenalan Pola, Abstraksi, dan Algoritma",
                    indikator = "Peserta didik mampu menyelesaikan persoalan logika dan pemodelan data diskrit menggunakan pendekatan dekomposisi dan pengenalan pola.",
                    weeks = listOf(1, 2, 3, 4),
                    aktivitas = "Diskusi kelompok memecahkan teka-teki logika komputasi dan menyusun diagram alir langkah penyelesaian masalah sehari-hari.",
                    questions = listOf(
                        AssessmentQuestion("Proses memecah masalah kompleks menjadi bagian-bagian kecil yang lebih mudah dikelola disebut...", listOf("Abstraksi", "Dekomposisi", "Pengenalan Pola", "Algoritma"), "B", "C2"),
                        AssessmentQuestion("Urutan langkah-langkah logis dan sistematis untuk menyelesaikan suatu masalah disebut...", listOf("Program", "Struktur Data", "Algoritma", "Deklarasi"), "C", "C2"),
                        AssessmentQuestion("Menyaring informasi yang tidak relevan dan fokus pada informasi penting dalam penyelesaian masalah merupakan prinsip...", listOf("Abstraksi", "Dekomposisi", "Iterasi", "Kompilasi"), "A", "C3")
                    ),
                    essay = "Jelaskan bagaimana kamu menerapkan prinsip dekomposisi saat merencanakan acara kegiatan kelas!"
                ),
                LearningObjective(
                    id = "tp2",
                    code = "7.2",
                    title = "Literasi Digital & Pemanfaatan Perkakas Teknologi",
                    jp = 10,
                    materi = "Mesin Pencari, Evaluasi Informasi Digital, Etika Siber, & Kolaborasi Daring",
                    indikator = "Peserta didik mampu mencari informasi secara akurat menggunakan kata kunci efektif, memverifikasi kredibilitas berita, dan menjaga keamanan data pribadi.",
                    weeks = listOf(5, 6, 7, 8),
                    aktivitas = "Praktik pencarian informasi menggunakan sintaks khusus mesin pencari serta analisis studi kasus hoaks dan keamanan kata sandi.",
                    questions = listOf(
                        AssessmentQuestion("Untuk mencari dokumen berformat PDF secara spesifik di mesin pencari Google, sintaks yang tepat adalah...", listOf("type:pdf", "filetype:pdf", "doc:pdf", "search:pdf"), "B", "C3"),
                        AssessmentQuestion("Tindakan memverifikasi kebenaran berita dengan membandingkan beberapa sumber terpercaya disebut...", listOf("Lateral Reading", "Skimming", "Phishing", "Broadcasting"), "A", "C2"),
                        AssessmentQuestion("Manakah di bawah ini contoh kata sandi (password) yang paling kuat dan aman?", listOf("tanggalLahir123", "admin12345", "K0mput3r#Smp3!", "namasaya2026"), "C", "C3")
                    ),
                    essay = "Sebutkan 3 ciri informasi palsu (hoaks) di media sosial dan langkah pencegahannya!"
                ),
                LearningObjective(
                    id = "tp3",
                    code = "7.3",
                    title = "Jaringan Komputer dan Internet (LAN, MAN, WAN, PAN)",
                    jp = 8,
                    materi = "Klasifikasi Jaringan Berdasarkan Jangkauan Geografis (PAN, LAN, MAN, WAN) & Koneksi Internet",
                    indikator = "Peserta didik mampu mengidentifikasi dan membedakan karakteristik jaringan PAN, LAN, MAN, dan WAN beserta penerapannya di lingkungan sekolah dan masyarakat.",
                    weeks = listOf(9, 10, 11),
                    aktivitas = "Observasi topologi jaringan laboratorium komputer sekolah, simulasi tethering/Bluetooth (PAN), dan pemetaan jangkauan jaringan.",
                    questions = listOf(
                        AssessmentQuestion("Jaringan komputer yang hanya mencakup area sangat dekat seperti menghubungkan smartphone dengan headset Bluetooth disebut...", listOf("LAN (Local Area Network)", "PAN (Personal Area Network)", "MAN (Metropolitan Area Network)", "WAN (Wide Area Network)"), "B", "C2"),
                        AssessmentQuestion("Jaringan komputer yang menghubungkan komputer-komputer di dalam satu ruang laboratorium sekolah atau satu gedung disebut...", listOf("PAN", "LAN", "MAN", "WAN"), "B", "C2"),
                        AssessmentQuestion("Urutan jenis jaringan komputer dari jangkauan geografis terkecil hingga terbesar yang benar adalah...", listOf("LAN - PAN - MAN - WAN", "PAN - LAN - MAN - WAN", "PAN - MAN - LAN - WAN", "WAN - MAN - LAN - PAN"), "B", "C3")
                    ),
                    essay = "Jelaskan perbedaan mendasar antara jaringan LAN dan WAN beserta masing-masing satu contoh penerapannya!"
                ),
                LearningObjective(
                    id = "tp4",
                    code = "7.4",
                    title = "Analisis Data dan Pengolahan Lembar Kerja",
                    jp = 14,
                    materi = "Pengumpulan Data, Tabel, Fungsi Statistik Dasar (SUM, AVERAGE, MAX, MIN), dan Visualisasi Grafik",
                    indikator = "Peserta didik mampu mengolah data numerik menggunakan aplikasi lembar kerja (spreadsheet) dan menyajikannya dalam bentuk grafik yang informatif.",
                    weeks = listOf(12, 13, 14, 15, 16),
                    aktivitas = "Praktik mengolah data nilai dan tinggi badan teman sekelas menggunakan fungsi rumus lembar kerja serta membuat diagram batang.",
                    questions = listOf(
                        AssessmentQuestion("Fungsi pada aplikasi lembar kerja (spreadsheet) yang digunakan untuk menghitung nilai rata-rata dari sekumpulan data adalah...", listOf("=SUM()", "=COUNT()", "=AVERAGE()", "=MAX()"), "C", "C2"),
                        AssessmentQuestion("Pertemuan antara baris (row) dan kolom (column) pada lembar kerja disebut...", listOf("Range", "Cell (Sel)", "Sheet", "Formula"), "B", "C1"),
                        AssessmentQuestion("Jika sel A1 berisi angka 10 dan B1 berisi angka 20, maka hasil dari rumus =SUM(A1:B1) adalah...", listOf("15", "20", "30", "200"), "C", "C3")
                    ),
                    essay = "Mengapa penyajian data dalam bentuk grafik/diagram seringkali lebih mudah dipahami dibandingkan tabel angka biasa?"
                )
            )
        ),
        "Bahasa Indonesia" to SubjectPreset(
            fase = "Fase D",
            cp = "Peserta didik memiliki kemampuan berbahasa untuk berkomunikasi dan bernalar sesuai dengan tujuan, konteks sosial, dan akademis dalam memahami, mengolah, dan menginterpretasi informasi paparan tentang topik yang beragam dan karya sastra.",
            tps = listOf(
                LearningObjective("tp1", "7.1", "Teks Deskripsi", 12, "Struktur dan Kebahasaan Teks Deskripsi Objek/Tempat", "Menganalisis informasi dan menulis teks deskripsi secara runtut dan hidup.", listOf(1, 2, 3, 4), "Mengamati lingkungan sekolah dan menulis paragraf deskripsi.", listOf(AssessmentQuestion("Tujuan utama teks deskripsi adalah...", listOf("Menjelaskan langkah membuat sesuatu", "Menggambarkan objek secara rinci sehingga pembaca seolah melihat langsung", "Meyakinkan pembaca", "Menceritakan kejadian masa lalu"), "B", "C2")), "Buatlah satu paragraf deskripsi tentang ruang kelasmu!"),
                LearningObjective("tp2", "7.2", "Puisi Rakyat dan Cerita Fantasi", 10, "Pantun, Gurindam, Syair, dan Unsur Intrinsik Cerita Imajinasi", "Mengidentifikasi ciri puisi rakyat dan mengkreasikan cerita fantasi.", listOf(5, 6, 7, 8), "Berbalas pantun dan menyusun kerangka cerita fantasi.", listOf(AssessmentQuestion("Sajak akhir pada pantun yang benar adalah...", listOf("a-a-a-a", "a-b-a-b", "a-a-b-b", "a-b-b-a"), "B", "C1")), "Tuliskan satu bait pantun nasihat tentang pentingnya rajin belajar!"),
                LearningObjective("tp3", "7.3", "Teks Prosedur", 8, "Ciri Kebahasaan Kalimat Perintah dan Struktur Teks Prosedur", "Menyajikan langkah-langkah melakukan suatu kegiatan secara sistematis.", listOf(9, 10, 11), "Praktik mendemonstrasikan teks prosedur di depan kelas.", listOf(AssessmentQuestion("Kalimat 'Masukkan dua sendok gula ke dalam gelas!' merupakan jenis kalimat...", listOf("Deklaratif", "Interogatif", "Imperatif", "Persuasif"), "C", "C2")), "Jelaskan struktur teks prosedur secara berurutan!"),
                LearningObjective("tp4", "7.4", "Teks Laporan Hasil Observasi", 14, "Fakta dan Opini, Struktur Definisi Umum dan Deskripsi Bagian", "Menyusun laporan hasil observasi secara objektif dan faktual.", listOf(12, 13, 14, 15, 16), "Observasi tanaman di taman sekolah dan menyusun laporan.", listOf(AssessmentQuestion("Teks Laporan Hasil Observasi harus bersifat objektif, artinya...", listOf("Sesuai pendapat pribadi penulis", "Sesuai fakta dan data sebenarnya di lapangan", "Menggunakan bahasa kiasan", "Berisi cerita rekaan"), "B", "C2")), "Apa perbedaan antara kalimat fakta dan kalimat opini?")
            )
        ),
        "IPA" to SubjectPreset(
            fase = "Fase D",
            cp = "Peserta didik mampu melakukan pengukuran terhadap aspek fisis yang mereka temui dan memanfaatkan ragam gerak dan gaya, memahami karakteristik zat serta perubahan fisika dan kimia, serta mengidentifikasi interaksi antar makhluk hidup dan lingkungannya.",
            tps = listOf(
                LearningObjective("tp1", "7.1", "Hakikat Ilmu Sains dan Metode Ilmiah", 12, "Besaran Pokok, Besaran Turunan, Satuan SI, dan Alat Ukur", "Melakukan pengukuran besaran fisis menggunakan alat ukur baku dengan tepat.", listOf(1, 2, 3, 4), "Praktikum mengukur panjang, massa, dan waktu di laboratorium.", listOf(AssessmentQuestion("Manakah di bawah ini yang termasuk kelompok besaran pokok?", listOf("Panjang, Luas, Volume", "Panjang, Massa, Waktu", "Kecepatan, Gaya, Usaha", "Massa, Berat, Suhu"), "B", "C2")), "Sebutkan tahapan-tahapan dalam metode ilmiah secara berurutan!"),
                LearningObjective("tp2", "7.2", "Zat dan Perubahannya", 10, "Wujud Zat, Massa Jenis, Perubahan Fisika dan Perubahan Kimia", "Membedakan karakteristik wujud zat serta mengidentifikasi perubahan fisika dan kimia.", listOf(5, 6, 7, 8), "Percobaan mengamati es mencair dan kertas terbakar.", listOf(AssessmentQuestion("Peristiwa berikut yang merupakan perubahan kimia adalah...", listOf("Air membeku menjadi es", "Besi berkarat", "Lilin meleleh", "Gula larut dalam air"), "B", "C2")), "Jelaskan perbedaan perubahan fisika dan perubahan kimia beserta contohnya!"),
                LearningObjective("tp3", "7.3", "Suhu, Kalor, dan Pemuaian", 8, "Konversi Skala Termometer, Kalor, Konduksi, Konveksi, dan Radiasi", "Menganalisis pengaruh kalor terhadap perubahan suhu dan wujud benda.", listOf(9, 10, 11), "Mengukur suhu air hangat dan mengamati perpindahan kalor.", listOf(AssessmentQuestion("Perpindahan kalor melalui zat perantara tanpa disertai perpindahan partikel zat tersebut dinamakan...", listOf("Konduksi", "Konveksi", "Radiasi", "Evaporasi"), "A", "C2")), "Mengapa pemasangan kabel listrik di pinggir jalan dibuat agak kendur?"),
                LearningObjective("tp4", "7.4", "Klasifikasi Makhluk Hidup dan Ekosistem", 14, "Ciri Makhluk Hidup, Kunci Determinasi, dan Jaring-jaring Makanan", "Mengklasifikasikan makhluk hidup dan menganalisis interaksi komponen ekosistem.", listOf(12, 13, 14, 15, 16), "Observasi komponen biotik dan abiotik di halaman sekolah.", listOf(AssessmentQuestion("Komponen biotik yang berperan sebagai produsen dalam ekosistem darat adalah...", listOf("Jamur", "Tumbuhan hijau", "Belalang", "Bakteri pengurai"), "B", "C1")), "Apa yang terjadi pada ekosistem sawah jika populasi ular menurun drastis?")
            )
        ),
        "IPS" to SubjectPreset(
            fase = "Fase D",
            cp = "Peserta didik memahami keberagaman kondisi geografis Indonesia, konektivitas antarruang, interaksi sosial, lembaga sosial, serta aktivitas ekonomi masyarakat dalam memenuhi kebutuhan hidup secara berkelanjutan.",
            tps = listOf(
                LearningObjective("tp1", "7.1", "Keluarga Awal Kehidupan & Lokasi Geografis", 12, "Sejarah Keluarga, Peta, Letak Astronomis dan Geografis Indonesia", "Membaca peta dan menganalisis pengaruh letak geografis terhadap kehidupan sosial.", listOf(1, 2, 3, 4), "Mengamati peta Indonesia dan diskusi kelompok konektivitas antarruang.", listOf(AssessmentQuestion("Garis khayal pada peta yang membagi bumi menjadi belahan utara dan selatan disebut...", listOf("Garis Bujur", "Garis Khatulistiwa (Lintang 0°)", "Garis Meridian", "Garis Balik"), "B", "C2")), "Jelaskan keuntungan letak geografis Indonesia di jalur perdagangan dunia!"),
                LearningObjective("tp2", "7.2", "Interaksi Sosial dan Lembaga Sosial", 10, "Syarat Interaksi Sosial, Bentuk Asosiatif & Disosiatif, Lembaga Sosial", "Mengidentifikasi bentuk interaksi sosial dan peran lembaga sosial di masyarakat.", listOf(5, 6, 7, 8), "Studi kasus bentuk kerja sama dan akomodasi di lingkungan sekolah.", listOf(AssessmentQuestion("Syarat terjadinya interaksi sosial adalah adanya...", listOf("Kontak sosial dan komunikasi", "Kekayaan dan jabatan", "Persaingan dan pertentangan", "Kelompok dan individu"), "A", "C2")), "Sebutkan 3 contoh interaksi sosial asosiatif di lingkungan sekolah!"),
                LearningObjective("tp3", "7.3", "Kebutuhan Manusia dan Kelangkaan Sumber Daya", 8, "Jenis Kebutuhan, Alat Pemuas Kebutuhan, dan Prinsip Ekonomi", "Menganalisis skala prioritas kebutuhan dan solusi mengatasi kelangkaan.", listOf(9, 10, 11), "Menyusun daftar skala prioritas keuangan pelajar.", listOf(AssessmentQuestion("Kebutuhan akan makanan, pakaian, dan tempat tinggal berdasarkan intensitasnya termasuk kebutuhan...", listOf("Primer", "Sekunder", "Tersier", "Rohani"), "A", "C1")), "Bagaimana cara menyusun skala prioritas kebutuhan bagi seorang pelajar?"),
                LearningObjective("tp4", "7.4", "Pemberdayaan Masyarakat & Literasi Finansial", 14, "Uang, Pendapatan, Tabungan, dan Keberagaman Sosial Budaya", "Memahami pengelolaan keuangan sederhana dan pelestarian budaya lokal.", listOf(12, 13, 14, 15, 16), "Simulasi perencanaan tabungan dan pameran budaya daerah.", listOf(AssessmentQuestion("Fungsi asli uang dalam kegiatan ekonomi adalah sebagai...", listOf("Alat tukar dan satuan hitung", "Alat penimbun kekayaan", "Komoditas perdagangan", "Penunjuk status sosial"), "A", "C2")), "Mengapa literasi finansial penting dimiliki sejak usia SMP?")
            )
        ),
        "Bahasa Inggris" to SubjectPreset(
            fase = "Fase D",
            cp = "Peserta didik menggunakan teks lisan, tulisan, dan visual dalam bahasa Inggris untuk berinteraksi dan berkomunikasi dalam konteks yang beragam baik formal maupun informal.",
            tps = listOf(
                LearningObjective("tp1", "7.1", "Greetings, Self-Introduction & Pronouns", 12, "Expressions of Greeting, Introducing Oneself and Others", "Memperkenalkan diri dan orang lain dengan pelafalan dan struktur kalimat yang tepat.", listOf(1, 2, 3, 4), "Role-play perkenalan diri di depan kelas secara berpasangan.", listOf(AssessmentQuestion("Rina: 'Hello, my name is Rina. Nice to meet you.' Budi: '...'", listOf("See you later", "Nice to meet you too", "I am sorry", "Good night"), "B", "C2")), "Write a short paragraph (4-5 sentences) introducing yourself and your hobby!"),
                LearningObjective("tp2", "7.2", "Culinary and Me (Describing Favorite Food)", 10, "Adjectives, Taste & Texture of Food, Simple Present Tense", "Mendeskripsikan makanan dan minuman favorit serta menanyakan kesukaan teman.", listOf(5, 6, 7, 8), "Wawancara teman sekelas tentang makanan tradisional favorit.", listOf(AssessmentQuestion("Rujak has a spicy and sour taste. The word 'spicy' in Indonesian means...", listOf("Manis", "Asin", "Pedas", "Pahit"), "C", "C1")), "Describe your favorite Indonesian food in 3 sentences!"),
                LearningObjective("tp3", "7.3", "Home Sweet Home (Rooms & Things in the House)", 8, "Prepositions of Place, There is / There are, Household Chores", "Mendeskripsikan ruangan di rumah dan aktivitas kebersihan sehari-hari.", listOf(9, 10, 11), "Menggambar denah kamar tidur dan mendeskripsikannya dalam bahasa Inggris.", listOf(AssessmentQuestion("My mother cooks delicious meals in the...", listOf("Bedroom", "Kitchen", "Bathroom", "Garage"), "B", "C1")), "Mention 3 daily chores you do to keep your house clean!"),
                LearningObjective("tp4", "7.4", "My School Activities & Class Schedule", 14, "Telling Time, Days of the Week, School Subjects & Extracurriculars", "Menyusun dan menjelaskan jadwal pelajaran serta kegiatan ekstrakurikuler.", listOf(12, 13, 14, 15, 16), "Membuat poster jadwal pelajaran kelas dalam bahasa Inggris.", listOf(AssessmentQuestion("We have Flag Ceremony every ... morning at school.", listOf("Sunday", "Monday", "Friday", "Saturday"), "B", "C1")), "What is your favorite subject at school and why do you like it?")
            )
        ),
        "PPKn" to SubjectPreset(
            fase = "Fase D",
            cp = "Peserta didik mampu menganalisis kronologis lahirnya Pancasila, menerapkan nilai-nilai Pancasila dalam kehidupan sehari-hari, memahami norma dan UUD NRI Tahun 1945, serta menjaga keutuhan NKRI dan Bhinneka Tunggal Ika.",
            tps = listOf(
                LearningObjective("tp1", "7.1", "Sejarah Kelahiran Pancasila", 12, "Sidang BPUPKI, Rumusan Dasar Negara, Panitia Sembilan, dan Penetapan Pancasila", "Menjelaskan proses perumusan dan penetapan Pancasila sebagai dasar negara.", listOf(1, 2, 3, 4), "Diskusi kelompok meneladani semangat tokoh perumus dasar negara.", listOf(AssessmentQuestion("Badan yang dibentuk untuk mempersiapkan kemerdekaan Indonesia dan merumuskan dasar negara adalah...", listOf("PPKI", "BPUPKI", "KNIP", "PETA"), "B", "C1")), "Sebutkan 3 tokoh yang mengusulkan rumusan dasar negara pada sidang pertama BPUPKI!"),
                LearningObjective("tp2", "7.2", "Norma dan UUD NRI Tahun 1945", 10, "Norma Agama, Kesusilaan, Kesopanan, dan Norma Hukum", "Menerapkan berbagai norma yang berlaku di lingkungan sekolah dan masyarakat.", listOf(5, 6, 7, 8), "Observasi kepatuhan tata tertib sekolah dan analisis studi kasus norma.", listOf(AssessmentQuestion("Norma yang bersumber dari hati nurani manusia dan menghasilkan rasa penyesalan jika dilanggar adalah...", listOf("Norma Hukum", "Norma Kesusilaan", "Norma Kesopanan", "Norma Adat"), "B", "C2")), "Mengapa aturan atau tata tertib sangat diperlukan di lingkungan sekolah?"),
                LearningObjective("tp3", "7.3", "Kesatuan Indonesia dan Karakteristik Daerah", 8, "Wilayah NKRI, Otonomi Daerah, dan Persatuan dalam Keberagaman", "Menunjukkan sikap menjaga keutuhan wilayah NKRI dari lingkungan terdekat.", listOf(9, 10, 11), "Pemetaan ciri khas daerah di Indonesia dan komitmen persatuan.", listOf(AssessmentQuestion("Bentuk negara Indonesia sesuai Pasal 1 Ayat 1 UUD NRI Tahun 1945 adalah...", listOf("Serikat", "Kesatuan yang berbentuk Republik", "Monarki", "Federasi"), "B", "C1")), "Bagaimana wujud nyata peran pelajar dalam menjaga keutuhan NKRI?"),
                LearningObjective("tp4", "7.4", "Kebinekaan Indonesia & Gotong Royong", 14, "Keberagaman Suku, Agama, Ras, Antargolongan, dan Budaya Gotong Royong", "Mempraktikkan toleransi dan kerja sama gotong royong di sekolah.", listOf(12, 13, 14, 15, 16), "Proyek aksi nyata kebersihan dan gotong royong lintas kelompok di kelas.", listOf(AssessmentQuestion("Semboyan Bhinneka Tunggal Ika memiliki arti...", listOf("Bersatu kita teguh bercerai kita runtuh", "Berbeda-beda tetapi tetap satu jua", "Maju terus pantang mundur", "Satu nusa satu bangsa"), "B", "C1")), "Berikan 3 contoh kegiatan gotong royong yang dapat dilakukan siswa di kelas!")
            )
        ),
        "Seni Budaya" to SubjectPreset(
            fase = "Fase D",
            cp = "Pada akhir Fase D, peserta didik mampu mengamati, mengenal, merekam, dan menuangkan pengalaman serta pengamatannya terhadap lingkungan, perasaan, atau empatinya secara visual dan musikal dengan menggunakan proporsi, gestur, ruang, unsur seni rupa maupun seni pertunjukan tradisi Nusantara.",
            tps = listOf(
                LearningObjective("tp1", "7.1", "Menggambar Flora, Fauna, dan Alam Benda", 12, "Unsur Seni Rupa (Titik, Garis, Bidang, Warna, Tekstur), Prinsip Komposisi, dan Teknik Arsir", "Peserta didik mampu mengidentifikasi unsur seni rupa dan menggambar bentuk objek flora, fauna, serta alam benda dengan proporsi yang tepat.", listOf(1, 2, 3, 4), "Observasi objek tumbuhan dan benda di sekitar sekolah serta praktik menggambar sketsa dengan teknik arsir gradasi.", listOf(AssessmentQuestion("Unsur seni rupa paling dasar yang menjadi awal terbentuknya garis dan bidang adalah...", listOf("Tekstur", "Titik", "Gelap Terang", "Ruang"), "B", "C1")), "Jelaskan langkah-langkah menggambar flora atau alam benda agar menghasilkan proporsi dan pencahayaan yang baik!"),
                LearningObjective("tp2", "7.2", "Ragam Hias Nusantara pada Bahan Tekstil dan Kayu", 10, "Motif Ragam Hias Geometris, Flora, Fauna, Figuratif, serta Motif Batik Khas Daerah", "Peserta didik mampu mendesain pola ragam hias daerah Nusantara dan menerapkannya pada media kertas atau tekstil.", listOf(5, 6, 7, 8), "Menggambar pola ragam hias geometris dan motif batik pesisir (seperti motif Paoman Indramayu) dengan teknik stilasi.", listOf(AssessmentQuestion("Penggayaan atau pengubahan bentuk objek alam menjadi bentuk ornamen hiasan tanpa meninggalkan ciri aslinya disebut...", listOf("Stilasi", "Realisme", "Fotografi", "Imitasi"), "A", "C2")), "Apa fungsi penerapan ragam hias pada benda kerajinan tekstil atau kayu dalam budaya masyarakat Indonesia?"),
                LearningObjective("tp3", "7.3", "Teknik Vokal dan Bernyanyi Secara Unisono", 8, "Artikulasi, Phrasering, Intonasi, Pernapasan Diafragma, dan Lagu Daerah Nusantara", "Peserta didik mampu menyanyikan lagu daerah dan lagu nasional secara unisono (satu suara) dengan intonasi dan artikulasi yang benar.", listOf(9, 10, 11), "Latihan olah vokal pernapasan diafragma dan praktik menyanyikan lagu daerah secara berkelompok.", listOf(AssessmentQuestion("Bernyanyi secara berkelompok dengan menggunakan satu suara atau satu melodi pokok disebut...", listOf("Kanon", "Akappela", "Unisono", "Paduan Suara Campuran"), "C", "C1")), "Jelaskan perbedaan antara artikulasi, intonasi, dan phrasering dalam teknik vokal!"),
                LearningObjective("tp4", "7.4", "Gerak Dasar Tari Tradisional Daerah", 14, "Elemen Dasar Tari (Ruang, Waktu, Tenaga), Level Gerak, Pola Lantai, dan Apresiasi Tari Daerah", "Peserta didik mampu memperagakan gerak dasar tari tradisional sesuai iringan musik serta menerapkan pola lantai.", listOf(12, 13, 14, 15, 16), "Praktik eksplorasi elemen gerak tari (ruang, waktu, tenaga) dan peragaan gerak dasar tari tradisional daerah (seperti Tari Topeng Indramayu).", listOf(AssessmentQuestion("Unsur utama dalam seni tari adalah...", listOf("Suara", "Gerak tubuh yang ritmis dan estetis", "Cahaya panggung", "Dekorasi"), "B", "C1")), "Jelaskan 3 elemen dasar gerak tari (Ruang, Waktu, dan Tenaga) beserta contohnya!")
            )
        ),
        "B. Indramayu" to SubjectPreset(
            fase = "Fase D",
            cp = "Pada akhir Fase D, peserta didik mampu memahami, mengapresiasi, dan menggunakan Bahasa Indramayu (Basa Dermayon) sesuai unggah-ungguh basa (Ngoko/Bagongan dan Krama/Bebasan) dalam komunikasi sehari-hari, membaca dan menulis aksara Carakan Jawa/Pegon dasar, serta melestarikan sastra dan budaya lokal Indramayu.",
            tps = listOf(
                LearningObjective("tp1", "7.1", "Unggah-Ungguh Basa Indramayu (Basa Bagongan lan Bebasan)", 12, "Tingkatan Bahasa Indramayu (Ngoko/Bagongan lan Krama/Bebasan) dalam Percakapan Sehari-hari", "Peserta didik mampu menerapkan unggah-ungguh basa (Bebasan/Krama) saat berbicara dengan guru, orang tua, dan masyarakat dengan santun.", listOf(1, 2, 3, 4), "Praktik dialog berpasangan (pacelathon) menggunakan Basa Bebasan kepada guru dan orang tua.", listOf(AssessmentQuestion("Tingkatan bahasa dalam Bahasa Indramayu yang digunakan untuk berbicara kepada orang yang lebih tua atau dihormati disebut...", listOf("Basa Bagongan", "Basa Bebasan (Krama)", "Basa Pergaulan", "Basa Kasar"), "B", "C1")), "Ubahlah kalimat berikut ke dalam Basa Bebasan Indramayu yang santun: 'Pak Guru wis teka ning sekolahan esuk-esuk'!"),
                LearningObjective("tp2", "7.2", "Teks Narasi Babad Dermayu lan Cerita Rakyat Indramayu", 10, "Sejarah Raden Wiralodra, Nyi Endang Darma Ayu, dan Nilai Luhur Cerita Rakyat Indramayu", "Peserta didik mampu menceritakan kembali isi teks Babad Dermayu menggunakan bahasa sendiri dan meneladani pesan moralnya.", listOf(5, 6, 7, 8), "Membaca teks Babad Dermayu, diskusi kelompok nilai kepahlawanan Raden Wiralodra, dan presentasi lisan.", listOf(AssessmentQuestion("Tokoh utama pembuka hutan di tepi Sungai Cimanuk yang menjadi pendiri Kabupaten Indramayu adalah...", listOf("Raden Kian Santang", "Raden Aria Wiralodra", "Pangeran Diponegoro", "Sunan Kalijaga"), "B", "C1")), "Sebutkan 3 nilai keteladanan dari kisah perjuangan Raden Wiralodra dalam membangun pedukuhan Cimanuk (Indramayu)!"),
                LearningObjective("tp3", "7.3", "Sastra Lokal: Wangsalan, Parikan, lan Pribasa Dermayon", 8, "Parikan (Pantun Dermayon), Wangsalan, dan Peribahasa Daerah Indramayu", "Peserta didik mampu mengidentifikasi makna parikan/wangsalan dan membuat parikan nasihat dalam Bahasa Indramayu.", listOf(9, 10, 11), "Menyusun dan membacakan parikan bertema semangat belajar serta sopan santun di depan kelas.", listOf(AssessmentQuestion("Puisi lama dalam sastra Jawa/Indramayu yang terdiri dari sampiran dan isi (mirip pantun) disebut...", listOf("Parikan", "Geguritan", "Babad", "Dongeng"), "A", "C1")), "Buatlah satu bait Parikan Basa Indramayu (4 baris) dengan tema pentingnya menjaga kebersihan sekolah!"),
                LearningObjective("tp4", "7.4", "Aksara Carakan Jawa / Dermayon (Aksara Nglegena lan Sandhangan)", 14, "20 Aksara Carakan (Ha-Na-Ca-Ra-Ka) dan Sandhangan Swara (Wulu, Suku, Taling, Pepet, Taling Tarung)", "Peserta didik mampu membaca dan menyalin kata serta kalimat sederhana menggunakan Aksara Carakan dengan tepat.", listOf(12, 13, 14, 15, 16), "Latihan menulis 20 aksara dasar Carakan dan menyalin kata-kata dalam Bahasa Indramayu menggunakan sandhangan vokal.", listOf(AssessmentQuestion("Urutan 5 aksara pertama dalam Carakan (Ha-Na-Ca-Ra-Ka) memiliki filosofi makna yaitu...", listOf("Ada utusan", "Saling bertarung", "Sama-sama kuat", "Menjadi jenazah"), "A", "C2")), "Sebutkan 20 aksara dasar Carakan (Ha-Na-Ca-Ra-Ka) secara berurutan dalam 4 baris!")
            )
        ),
        "PJOK" to SubjectPreset(
            fase = "Fase D",
            cp = "Pada akhir Fase D, peserta didik dapat menunjukkan kemampuan dalam mempraktikkan hasil analisis keterampilan gerak spesifik berupa permainan dan olahraga, aktivitas senam, aktivitas gerak berirama, serta menerapkan konsep kebugaran jasmani dan pola perilaku hidup sehat.",
            tps = listOf(
                LearningObjective("tp1", "7.1", "Permainan Invasi & Net (Sepak Bola, Bola Voli, Bola Basket)", 12, "Gerak Spesifik Passing, Mengontrol Bola, Dribbling, Shooting, serta Servis Bawah dan Passing Bola Voli", "Peserta didik mampu mempraktikkan variasi gerak spesifik permainan bola besar serta menjunjung tinggi sportivitas dan kerja sama tim.", listOf(1, 2, 3, 4), "Pemanasan dinamis, latihan berpasangan passing kaki bagian dalam (sepak bola) serta passing bawah/atas bola voli di lapangan sekolah.", listOf(AssessmentQuestion("Teknik menendang bola yang paling efektif digunakan untuk memberikan umpan jarak pendek secara akurat dalam sepak bola adalah menggunakan...", listOf("Ujung jari kaki", "Kaki bagian dalam", "Tumit kaki", "Punggung kaki penuh"), "B", "C2")), "Jelaskan posisi kedua lengan dan perkenaan bola yang benar saat melakukan gerakan passing bawah pada permainan bola voli!"),
                LearningObjective("tp2", "7.2", "Aktivitas Atletik (Jalan Cepat, Lari Jarak Pendek & Lompat Jauh)", 10, "Teknik Start Jongkok (Crouch Start), Gerakan Lari Sprint, Memasuki Garis Finish, dan Tahapan Lompat Jauh", "Peserta didik mampu mempraktikkan teknik dasar start jongkok, lari jarak pendek (sprint), dan lompat jauh dengan koordinasi gerak yang baik.", listOf(5, 6, 7, 8), "Praktik aba-aba start jongkok ('Bersedia, Siap, Ya!'), lari cepat 60 meter, dan tahapan tolakan lompat jauh.", listOf(AssessmentQuestion("Jenis start yang wajib digunakan oleh pelari pada nomor lari jarak pendek (sprint 100 m) adalah...", listOf("Start berdiri", "Start melayang", "Start jongkok (crouch start)", "Start bebas"), "C", "C1")), "Apa perbedaan mendasar antara gerakan jalan cepat dengan gerakan lari dilihat dari kontak kaki dengan tanah?"),
                LearningObjective("tp3", "7.3", "Aktivitas Kebugaran Jasmani & Senam Lantai", 8, "Latihan Kekuatan (Push-up, Sit-up, Back-up), Daya Tahan Jantung-Paru, Kelenturan, serta Guling Depan (Forward Roll)", "Peserta didik mampu melakukan latihan komponen kebugaran jasmani dan rangkaian gerak spesifik senam lantai dengan aman.", listOf(9, 10, 11), "Sirkuit training kebugaran jasmani (sit-up, push-up, shuttle run) dan praktik guling depan di atas matras.", listOf(AssessmentQuestion("Kemampuan tubuh untuk melakukan aktivitas fisik sehari-hari tanpa mengalami kelelahan yang berlebihan disebut...", listOf("Kesehatan rohani", "Kebugaran jasmani", "Kekuatan otot", "Kecepatan gerak"), "B", "C1")), "Sebutkan 4 manfaat melakukan pemanasan sebelum berolahraga dan pendinginan setelah berolahraga!"),
                LearningObjective("tp4", "7.4", "Pola Makan Sehat, Bergizi, dan Seimbang serta Budaya Hidup Sehat", 14, "Zat Gizi Makanan (Karbohidrat, Protein, Lemak, Vitamin, Mineral), Gizi Seimbang Remaja, dan Pencegahan Pergaulan Tidak Sehat", "Peserta didik mampu menganalisis kebutuhan gizi harian remaja dan menerapkan perilaku hidup bersih dan sehat di sekolah maupun rumah.", listOf(12, 13, 14, 15, 16), "Menyusun menu makanan sehat 'Isi Piringku' untuk remaja SMP dan kampanye poster pola hidup sehat.", listOf(AssessmentQuestion("Zat gizi dalam makanan yang berfungsi utama sebagai pembangun sel-sel tubuh dan memperbaiki jaringan tubuh yang rusak adalah...", listOf("Karbohidrat", "Protein", "Vitamin", "Air"), "B", "C2")), "Jelaskan komponen apa saja yang harus ada dalam seporsi makan sehat sesuai panduan 'Isi Piringku'!")
            )
        )
    )

    val INITIAL_STUDENTS = listOf(
        com.example.model.StudentGrade(1, "Andi Pratama", "262707001", "H", 88, 92, 85, 87, 86, 90),
        com.example.model.StudentGrade(2, "Bunga Lestari", "262707002", "H", 78, 80, 68, 72, 74, 76),
        com.example.model.StudentGrade(3, "Candra Wibowo", "262707003", "H", 92, 90, 94, 96, 92, 94),
        com.example.model.StudentGrade(4, "Dewi Sartika", "262707004", "H", 81, 85, 82, 84, 83, 85),
        com.example.model.StudentGrade(5, "Eko Saputra", "262707005", "H", 86, 88, 84, 75, 82, 84)
    )

    val INITIAL_JOURNALS = listOf(
        com.example.model.TeachingJournal(1, "Senin, 5 Oktober 2026", "VII A", "Operasi bilangan bulat", "Eksplorasi garis bilangan dan kuis singkat", "5/5 Hadir"),
        com.example.model.TeachingJournal(2, "Senin, 12 Oktober 2026", "VII A", "Pecahan dan desimal", "Diskusi kelompok, latihan soal 1–5", "5/5 Hadir")
    )
}
