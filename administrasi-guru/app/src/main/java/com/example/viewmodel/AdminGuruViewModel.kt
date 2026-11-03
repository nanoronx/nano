package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.CurriculumBank
import com.example.model.AssessmentQuestion
import com.example.model.ClassProfile
import com.example.model.LearningObjective
import com.example.model.StudentGrade
import com.example.model.TeachingJournal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

sealed class AppScreen(val id: String, val title: String) {
    object Dashboard : AppScreen("dashboard", "Beranda")
    object ClassProfileScreen : AppScreen("datakelas", "Data Kelas Saya")
    object CpTp : AppScreen("cptp", "CP dan TP")
    object Preparation : AppScreen("persiapan", "ATP, Prota, Promes & Silabus")
    object LessonKit : AppScreen("perangkat", "Modul Ajar, LKPD & Kuis")
    object DailyClass : AppScreen("harian", "Kelas Harian (HP Mode)")
    object Assessment : AppScreen("asesmen", "Asesmen & STS/SAS")
    object ReportCard : AppScreen("rapor", "Deskripsi & Rekap Rapor")
    object SchoolTemplate : AppScreen("template", "Format & Kop Sekolah")
    object SupervisionPackage : AppScreen("supervisi", "Paket Siap Supervisi")
}

class AdminGuruViewModel : ViewModel() {

    private val _currentScreen = MutableStateFlow<AppScreen>(AppScreen.Dashboard)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val _classProfile = MutableStateFlow(ClassProfile())
    val classProfile: StateFlow<ClassProfile> = _classProfile.asStateFlow()

    private val _cpText = MutableStateFlow(CurriculumBank.SUBJECTS["Matematika"]?.cp ?: "")
    val cpText: StateFlow<String> = _cpText.asStateFlow()

    private val _tps = MutableStateFlow(
        CurriculumBank.adaptTpCodesToRombel(
            CurriculumBank.SUBJECTS["Matematika"]?.tps ?: emptyList(),
            "VII A"
        )
    )
    val tps: StateFlow<List<LearningObjective>> = _tps.asStateFlow()

    private val _selectedTpId = MutableStateFlow("tp2")
    val selectedTpId: StateFlow<String> = _selectedTpId.asStateFlow()

    private val _students = MutableStateFlow(CurriculumBank.INITIAL_STUDENTS)
    val students: StateFlow<List<StudentGrade>> = _students.asStateFlow()

    private val _journals = MutableStateFlow(CurriculumBank.INITIAL_JOURNALS)
    val journals: StateFlow<List<TeachingJournal>> = _journals.asStateFlow()

    // Sub-tab states
    private val _perangkatType = MutableStateFlow("modul") // "modul" | "lkpd" | "kuis"
    val perangkatType: StateFlow<String> = _perangkatType.asStateFlow()

    private val _dailyTab = MutableStateFlow("presensi") // "presensi" | "jurnal" | "nilai"
    val dailyTab: StateFlow<String> = _dailyTab.asStateFlow()

    private val _examType = MutableStateFlow("Asesmen Sumatif") // "Asesmen Sumatif" | "Soal STS dan SAS"
    val examType: StateFlow<String> = _examType.asStateFlow()

    private val _examDocTab = MutableStateFlow("kisi") // "kisi" | "soal_kunci" | "lembar_siswa"
    val examDocTab: StateFlow<String> = _examDocTab.asStateFlow()

    private val _showRombelModal = MutableStateFlow(false)
    val showRombelModal: StateFlow<Boolean> = _showRombelModal.asStateFlow()

    private val _activeRombelTab = MutableStateFlow("ALL") // "ALL" | "VII" | "VIII" | "IX"
    val activeRombelTab: StateFlow<String> = _activeRombelTab.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
    }

    fun setPerangkatType(type: String) {
        _perangkatType.value = type
    }

    fun setDailyTab(tab: String) {
        _dailyTab.value = tab
    }

    fun setExamType(type: String) {
        _examType.value = type
    }

    fun setExamDocTab(tab: String) {
        _examDocTab.value = tab
    }

    fun setSelectedTpId(id: String) {
        _selectedTpId.value = id
    }

    fun setShowRombelModal(show: Boolean) {
        _showRombelModal.value = show
    }

    fun setActiveRombelTab(tab: String) {
        _activeRombelTab.value = tab
    }

    fun showToast(message: String) {
        _toastMessage.value = message
    }

    fun clearToast() {
        _toastMessage.value = null
    }

    fun updateClassProfile(updater: (ClassProfile) -> ClassProfile) {
        _classProfile.update(updater)
    }

    fun switchSubject(newSubject: String) {
        _classProfile.update { it.copy(subject = newSubject) }
        val preset = CurriculumBank.SUBJECTS[newSubject]
        if (preset != null) {
            _cpText.value = preset.cp
            val adapted = CurriculumBank.adaptTpCodesToRombel(preset.tps, _classProfile.value.activeClass)
            _tps.value = adapted
            adapted.firstOrNull()?.let { _selectedTpId.value = it.id }
            showToast("CP, TP & Perangkat $newSubject otomatis disinkronkan!")
        }
    }

    fun switchActiveRombel(rombelName: String) {
        _classProfile.update { current ->
            val nextParallel = if (current.parallelClasses.contains(rombelName)) {
                current.parallelClasses
            } else {
                current.parallelClasses + rombelName
            }
            current.copy(activeClass = rombelName, parallelClasses = nextParallel)
        }
        _tps.update { currentTps ->
            CurriculumBank.adaptTpCodesToRombel(currentTps, rombelName)
        }
        val grade = CurriculumBank.getGradePrefixFromRombel(rombelName)
        showToast("Rombel aktif: $rombelName (Kode TP disesuaikan ke Kelas $grade)")
    }

    fun toggleParallelRombel(rombel: String) {
        _classProfile.update { current ->
            val exists = current.parallelClasses.contains(rombel)
            if (exists && current.parallelClasses.size == 1) {
                showToast("Minimal harus ada 1 rombel yang aktif!")
                return@update current
            }
            val nextList = if (exists) {
                current.parallelClasses.filter { it != rombel }
            } else {
                current.parallelClasses + rombel
            }
            val nextActive = if (nextList.contains(current.activeClass)) current.activeClass else nextList.first()
            _tps.update { currentTps -> CurriculumBank.adaptTpCodesToRombel(currentTps, nextActive) }
            current.copy(parallelClasses = nextList, activeClass = nextActive)
        }
    }

    fun toggleGradeGroupRombels(gradeKey: String) {
        val groupList = CurriculumBank.ROMBEL_GROUPS[gradeKey] ?: emptyList()
        _classProfile.update { current ->
            val allAlreadySelected = groupList.all { current.parallelClasses.contains(it) }
            val nextList = if (allAlreadySelected) {
                val remaining = current.parallelClasses.filter { !groupList.contains(it) }
                if (remaining.isNotEmpty()) remaining else listOf(groupList.first())
            } else {
                (current.parallelClasses + groupList).distinct()
            }
            val nextActive = if (nextList.contains(current.activeClass)) current.activeClass else nextList.first()
            _tps.update { currentTps -> CurriculumBank.adaptTpCodesToRombel(currentTps, nextActive) }
            current.copy(parallelClasses = nextList, activeClass = nextActive)
        }
        showToast("Rombel Kelas $gradeKey berhasil diperbarui!")
    }

    fun addCustomRombel(customName: String) {
        val trimmed = customName.trim().uppercase()
        if (trimmed.isEmpty()) return
        _classProfile.update { current ->
            val nextList = if (current.parallelClasses.contains(trimmed)) {
                current.parallelClasses
            } else {
                current.parallelClasses + trimmed
            }
            current.copy(parallelClasses = nextList, activeClass = trimmed)
        }
        _tps.update { currentTps -> CurriculumBank.adaptTpCodesToRombel(currentTps, trimmed) }
        showToast("Rombel $trimmed berhasil ditambahkan!")
    }

    fun updateCpText(newCp: String) {
        _cpText.value = newCp
    }

    fun updateTpTitle(tpId: String, newTitle: String) {
        _tps.update { list ->
            list.map { if (it.id == tpId) it.copy(title = newTitle) else it }
        }
    }

    fun togglePromesWeek(tpId: String, weekNum: Int) {
        _tps.update { list ->
            list.map { tp ->
                if (tp.id != tpId) tp
                else {
                    val exists = tp.weeks.contains(weekNum)
                    val nextWeeks = if (exists) {
                        tp.weeks.filter { it != weekNum }
                    } else {
                        (tp.weeks + weekNum).sorted()
                    }
                    tp.copy(weeks = nextWeeks, jp = maxOf(2, nextWeeks.size * 2))
                }
            }
        }
    }

    fun addNewTp() {
        val currentList = _tps.value
        val nextNum = currentList.size + 1
        val gradePrefix = CurriculumBank.getGradePrefixFromRombel(_classProfile.value.activeClass)
        val newTp = LearningObjective(
            id = "tp_${System.currentTimeMillis()}",
            code = "$gradePrefix.$nextNum",
            title = "Materi Pembelajaran Baru Bab $nextNum",
            jp = 6,
            materi = "Pokok Bahasan Bab $nextNum",
            indikator = "Peserta didik mampu memahami dan menerapkan konsep Bab $nextNum dengan baik.",
            weeks = listOf(minOf(16, nextNum * 3)),
            aktivitas = "Diskusi kelompok, eksplorasi konsep, dan presentasi hasil kerja.",
            questions = listOf(
                AssessmentQuestion(
                    "Pertanyaan evaluasi pemahaman konsep dasar materi $gradePrefix.$nextNum...",
                    listOf("Pilihan A", "Pilihan B", "Pilihan C", "Pilihan D"),
                    "A",
                    "C2"
                )
            ),
            essay = "Jelaskan kesimpulan utama dari materi $gradePrefix.$nextNum dengan bahasamu sendiri!"
        )
        _tps.value = currentList + newTp
        showToast("Tujuan Pembelajaran ${newTp.code} ditambahkan!")
    }

    fun removeTp(tpId: String) {
        if (_tps.value.size <= 1) {
            showToast("Minimal harus ada 1 TP!")
            return
        }
        _tps.update { it.filter { tp -> tp.id != tpId } }
        if (_selectedTpId.value == tpId) {
            _tps.value.firstOrNull()?.let { _selectedTpId.value = it.id }
        }
        showToast("TP berhasil dihapus.")
    }

    fun updateStudentAttendance(studentId: Long, status: String) {
        _students.update { list ->
            list.map { if (it.id == studentId) it.copy(attendance = status) else it }
        }
    }

    fun updateStudentScore(studentId: Long, field: String, value: Int) {
        _students.update { list ->
            list.map { student ->
                if (student.id != studentId) student
                else when (field) {
                    "tp1" -> student.copy(tp1 = value)
                    "tp2" -> student.copy(tp2 = value)
                    "tp3" -> student.copy(tp3 = value)
                    "tp4" -> student.copy(tp4 = value)
                    "sts" -> student.copy(sts = value)
                    "sas" -> student.copy(sas = value)
                    else -> student
                }
            }
        }
    }

    fun addStudent(name: String) {
        val trimmed = name.trim()
        if (trimmed.isEmpty()) return
        val current = _students.value
        val newStudent = StudentGrade(
            id = System.currentTimeMillis(),
            name = trimmed,
            nis = "26270700${current.size + 1}",
            attendance = "H",
            tp1 = 80, tp2 = 82, tp3 = 80, tp4 = 84, sts = 80, sas = 82
        )
        _students.value = current + newStudent
        showToast("Siswa $trimmed berhasil ditambahkan!")
    }

    fun addJournal(date: String, rombel: String, materi: String, activity: String, presentCount: String) {
        val newJournal = TeachingJournal(
            id = System.currentTimeMillis(),
            date = date,
            rombel = rombel,
            materi = materi,
            activity = activity,
            presentCount = presentCount
        )
        _journals.update { listOf(newJournal) + it }
        showToast("Jurnal mengajar berhasil disimpan!")
    }
}
