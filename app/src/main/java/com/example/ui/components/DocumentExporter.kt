package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.model.ClassProfile
import com.example.model.LearningObjective
import com.example.model.StudentGrade

object DocumentExporter {

    fun shareDocument(
        context: Context,
        title: String,
        content: String,
        mimeType: String = "text/plain"
    ) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TITLE, title)
            putExtra(Intent.EXTRA_SUBJECT, title)
            putExtra(Intent.EXTRA_TEXT, content)
            type = mimeType
        }
        val shareIntent = Intent.createChooser(sendIntent, "Ekspor Dokumen: $title")
        context.startActivity(shareIntent)
        Toast.makeText(context, "Dokumen siap dibagikan / dibuka!", Toast.LENGTH_SHORT).show()
    }

    fun generateAtpText(profile: ClassProfile, cpText: String, tps: List<LearningObjective>): String {
        val sb = StringBuilder()
        sb.append("==================================================\n")
        sb.append("ALUR TUJUAN PEMBELAJARAN (ATP) - KURIKULUM MERDEKA\n")
        sb.append("==================================================\n\n")
        sb.append("Satuan Pendidikan : ${profile.schoolName}\n")
        sb.append("Mata Pelajaran    : ${profile.subject}\n")
        sb.append("Fase / Kelas      : ${profile.fase} / ${profile.activeClass}\n")
        sb.append("Tahun Ajaran      : ${profile.academicYear} (${profile.semester})\n")
        sb.append("Guru Pengampu     : ${profile.teacherName} (NIP. ${profile.teacherNip})\n\n")
        sb.append("CAPAIAN PEMBELAJARAN:\n$cpText\n\n")
        sb.append("RINCIAN ALUR TUJUAN PEMBELAJARAN:\n")
        tps.forEachIndexed { i, tp ->
            sb.append("${i + 1}. [TP ${tp.code}] ${tp.title}\n")
            sb.append("   - Materi Pokok : ${tp.materi}\n")
            sb.append("   - Alokasi      : ${tp.jp} JP (Minggu ke-${tp.weeks.joinToString(", ")})\n")
            sb.append("   - Indikator    : ${tp.indikator}\n")
            sb.append("   - Aktivitas    : ${tp.aktivitas}\n\n")
        }
        sb.append("Total Alokasi Waktu: ${tps.sumOf { it.jp }} JP\n\n")
        sb.append("Mengetahui,\nKepala Sekolah: ${profile.principalName}\nNIP: ${profile.principalNip}\n\n")
        sb.append("Guru Mata Pelajaran: ${profile.teacherName}\nNIP: ${profile.teacherNip}\n")
        return sb.toString()
    }

    fun generatePromesText(profile: ClassProfile, tps: List<LearningObjective>): String {
        val sb = StringBuilder()
        sb.append("PROGRAM SEMESTER (PROMES) - 16 MINGGU EFEKTIF\n")
        sb.append("Satuan Pendidikan : ${profile.schoolName}\n")
        sb.append("Kelas / Mapel     : ${profile.activeClass} / ${profile.subject}\n")
        sb.append("Semester          : ${profile.semester} (${profile.academicYear})\n\n")
        sb.append(String.format("%-8s | %-32s | %-6s | %s\n", "Kode TP", "Tujuan Pembelajaran", "JP", "Minggu Pelaksanaan"))
        sb.append("--------------------------------------------------------------------------------\n")
        tps.forEach { tp ->
            sb.append(String.format("%-8s | %-32s | %-6s | Minggu %s\n", tp.code, tp.title.take(30), "${tp.jp} JP", tp.weeks.joinToString(", ")))
        }
        return sb.toString()
    }

    fun generateModulAjarText(profile: ClassProfile, tp: LearningObjective): String {
        val sb = StringBuilder()
        sb.append("MODUL AJAR / RENCANA PELAKSANAAN PEMBELAJARAN\n")
        sb.append("=============================================\n\n")
        sb.append("A. IDENTITAS UMUM\n")
        sb.append("Satuan Pendidikan : ${profile.schoolName}\n")
        sb.append("Mata Pelajaran    : ${profile.subject}\n")
        sb.append("Kelas / Fase      : ${profile.activeClass} / ${profile.fase}\n")
        sb.append("Alokasi Waktu     : ${tp.jp} JP (Minggu ke-${tp.weeks.joinToString(", ")})\n")
        sb.append("Tahun Ajaran      : ${profile.academicYear} (${profile.semester})\n\n")
        sb.append("B. KOMPONEN INTI\n")
        sb.append("1. Tujuan Pembelajaran (TP ${tp.code}):\n")
        sb.append("   Peserta didik mampu memahami dan menguasai ${tp.title}.\n")
        sb.append("2. Indikator Ketercapaian:\n")
        sb.append("   ${tp.indikator}\n")
        sb.append("3. Materi Pokok:\n")
        sb.append("   ${tp.materi}\n\n")
        sb.append("C. LANGKAH-LANGKAH KEGIATAN PEMBELAJARAN\n")
        sb.append("1. Pendahuluan (10 Menit):\n")
        sb.append("   - Guru menyapa, berdoa bersama, presensi siswa kelas ${profile.activeClass}.\n")
        sb.append("   - Apersepsi dan menyampaikan motivasi terkait ${tp.title}.\n")
        sb.append("2. Kegiatan Inti (60 Menit):\n")
        sb.append("   - Orientasi Masalah: Pengenalan materi kontekstual.\n")
        sb.append("   - Aktivitas: ${tp.aktivitas}\n")
        sb.append("   - Diskusi kelompok dan verifikasi hasil kerja siswa.\n")
        sb.append("3. Penutup (10 Menit):\n")
        sb.append("   - Refleksi bersama peserta didik dan simpulan materi.\n")
        sb.append("   - Tindak lanjut dan doa penutup.\n\n")
        sb.append("D. ASESMEN PEMBELAJARAN\n")
        sb.append("- Formatif : Observasi keaktifan diskusi dan LKPD\n")
        sb.append("- Sumatif  : Tes tertulis pilihan ganda & uraian\n\n")
        sb.append("Mengetahui,\nKepala Sekolah: ${profile.principalName}\nGuru Mapel: ${profile.teacherName}\n")
        return sb.toString()
    }

    fun generateAssessmentText(profile: ClassProfile, examType: String, tps: List<LearningObjective>): String {
        val sb = StringBuilder()
        sb.append("DOKUMEN ${examType.uppercase()} - KURIKULUM MERDEKA\n")
        sb.append("Satuan Pendidikan : ${profile.schoolName}\n")
        sb.append("Kelas / Mapel     : ${profile.activeClass} / ${profile.subject}\n")
        sb.append("Tahun Pelajaran   : ${profile.academicYear}\n\n")
        sb.append("=========================================\n")
        sb.append("1. KISI-KISI ASESMEN\n")
        sb.append("=========================================\n")
        tps.forEachIndexed { i, tp ->
            sb.append("${i + 1}. TP ${tp.code} | Materi: ${tp.materi} | Level: C2/C3 | Bentuk: PG & Uraian\n")
        }
        sb.append("\n=========================================\n")
        sb.append("2. NASKAH SOAL & KUNCI JAWABAN\n")
        sb.append("=========================================\n")
        tps.forEach { tp ->
            tp.questions.forEachIndexed { idx, q ->
                sb.append("[TP ${tp.code}] ${q.q}\n")
                q.options.forEachIndexed { oIdx, opt ->
                    val letter = ('A' + oIdx)
                    sb.append("   $letter. $opt\n")
                }
                sb.append("   >> Kunci: ${q.ans} (Level: ${q.level})\n\n")
            }
            sb.append("[Soal Uraian TP ${tp.code}]:\n${tp.essay}\n\n")
        }
        return sb.toString()
    }

    fun generateERaporCsv(profile: ClassProfile, students: List<StudentGrade>, tps: List<LearningObjective>): String {
        val sb = StringBuilder()
        sb.append("No,NIS,Nama Siswa,Kelas,Mata Pelajaran,TP1,TP2,TP3,TP4,STS,SAS,Nilai Akhir Rapor,Deskripsi Capaian Kompetensi\n")
        students.forEachIndexed { idx, s ->
            val summary = s.calculateSummary(tps)
            sb.append("${idx + 1},${s.nis},\"${s.name}\",${profile.activeClass},${profile.subject},")
            sb.append("${s.tp1},${s.tp2},${s.tp3},${s.tp4},${s.sts},${s.sas},${summary.finalScore},\"${summary.reportDescription}\"\n")
        }
        return sb.toString()
    }
}
