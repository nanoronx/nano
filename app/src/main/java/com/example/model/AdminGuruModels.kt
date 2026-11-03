package com.example.model

data class AssessmentQuestion(
    val q: String,
    val options: List<String>,
    val ans: String,
    val level: String
)

data class LearningObjective(
    val id: String,
    val code: String,
    val title: String,
    val jp: Int,
    val materi: String,
    val indikator: String,
    val weeks: List<Int>,
    val aktivitas: String,
    val questions: List<AssessmentQuestion>,
    val essay: String
)

data class SubjectPreset(
    val fase: String,
    val cp: String,
    val tps: List<LearningObjective>
)

data class StudentGrade(
    val id: Long,
    val name: String,
    val nis: String,
    val attendance: String = "H", // H, S, I, A
    val tp1: Int = 85,
    val tp2: Int = 88,
    val tp3: Int = 84,
    val tp4: Int = 86,
    val sts: Int = 85,
    val sas: Int = 88
) {
    fun calculateSummary(tps: List<LearningObjective>): StudentSummary {
        val scores = listOf(
            tps.getOrNull(0)?.title.orEmpty().ifEmpty { "Materi 1" } to tp1,
            tps.getOrNull(1)?.title.orEmpty().ifEmpty { "Materi 2" } to tp2,
            tps.getOrNull(2)?.title.orEmpty().ifEmpty { "Materi 3" } to tp3,
            tps.getOrNull(3)?.title.orEmpty().ifEmpty { "Materi 4" } to tp4
        )
        val avgTp = scores.map { it.second }.average()
        val finalScore = ((avgTp * 0.5) + (sts * 0.25) + (sas * 0.25)).toInt()
        val sorted = scores.sortedByDescending { it.second }
        val highest = sorted.firstOrNull() ?: ("Materi Pembelajaran" to 85)
        val lowest = sorted.lastOrNull() ?: ("Materi Pembelajaran" to 75)

        val desc = when {
            finalScore >= 85 ->
                "Ananda $name menunjukkan penguasaan yang sangat baik dalam ${highest.first.lowercase()}, serta konsisten memahami ${lowest.first.lowercase()}."
            finalScore >= 78 ->
                "Ananda $name menunjukkan penguasaan yang baik dalam ${highest.first.lowercase()}, namun perlu penguatan lebih lanjut pada ${lowest.first.lowercase()}."
            else ->
                "Ananda $name cukup mampu memahami ${highest.first.lowercase()}, namun perlu bimbingan intensif dalam ${lowest.first.lowercase()}."
        }

        return StudentSummary(
            avgTp = avgTp,
            finalScore = finalScore,
            highestMateri = highest.first,
            lowestMateri = lowest.first,
            reportDescription = desc
        )
    }
}

data class StudentSummary(
    val avgTp: Double,
    val finalScore: Int,
    val highestMateri: String,
    val lowestMateri: String,
    val reportDescription: String
)

data class TeachingJournal(
    val id: Long,
    val date: String,
    val rombel: String,
    val materi: String,
    val activity: String,
    val presentCount: String
)

data class ClassProfile(
    val schoolName: String = "UPTD SMPN 3 GABUSWETAN",
    val pemda: String = "PEMERINTAH KABUPATEN INDRAMAYU",
    val dinas: String = "DINAS PENDIDIKAN DAN KEBUDAYAAN",
    val address: String = "Jl. Raya Gabuswetan, Kec. Gabuswetan, Kab. Indramayu",
    val teacherName: String = "Guru Mata Pelajaran, S.Pd., Gr.",
    val teacherNip: String = "19900101 202012 1 001",
    val principalName: String = "Kepala Sekolah, M.Pd.",
    val principalNip: String = "19750512 200501 1 004",
    val activeClass: String = "VII A",
    val parallelClasses: List<String> = listOf("VII A", "VII B", "VII C"),
    val subject: String = "Matematika",
    val academicYear: String = "2026/2027",
    val semester: String = "Ganjil",
    val fase: String = "Fase D",
    val useSchoolHeader: Boolean = true
)
