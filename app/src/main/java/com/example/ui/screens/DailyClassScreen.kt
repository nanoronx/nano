package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ClassProfile
import com.example.model.LearningObjective
import com.example.model.StudentGrade
import com.example.model.TeachingJournal
import com.example.ui.components.DocumentExporter
import com.example.ui.theme.AmberBorder
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamSurfaceVariant
import com.example.ui.theme.CrimsonLight
import com.example.ui.theme.CrimsonPrimary
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.GoldSecondary
import com.example.ui.theme.SlateTextDark
import com.example.ui.theme.SlateTextMedium
import com.example.ui.theme.SuccessGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyClassScreen(
    profile: ClassProfile,
    tps: List<LearningObjective>,
    students: List<StudentGrade>,
    journals: List<TeachingJournal>,
    dailyTab: String, // "presensi" | "jurnal" | "nilai"
    onSelectDailyTab: (String) -> Unit,
    onUpdateAttendance: (Long, String) -> Unit,
    onUpdateScore: (Long, String, Int) -> Unit,
    onAddStudent: (String) -> Unit,
    onAddJournal: (String, String, String, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var newStudentName by remember { mutableStateOf("") }

    // Journal form state
    var journalDate by remember { mutableStateOf("Senin, 12 Oktober 2026") }
    var journalMateri by remember { mutableStateOf(tps.firstOrNull()?.title ?: "Materi Pembelajaran") }
    var journalActivity by remember { mutableStateOf("Diskusi kelompok, latihan soal 1–5") }
    var materiDropdownExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CreamBackground)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Mode Header & Tab Selector (HP Simulator look)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, GoldSecondary)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = CrimsonPrimary,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.PhoneAndroid, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Kelas Harian (HP Mode)",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Black,
                                color = SlateTextDark
                            )
                            Text(
                                text = "Kelas ${profile.activeClass} • ${profile.subject}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = CrimsonPrimary
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = GoldLight.copy(alpha = 0.3f)
                    ) {
                        Text(
                            text = "${students.size} Siswa",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldPrimary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // 3 Mode Switcher Pills
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(
                        "presensi" to "Presensi",
                        "jurnal" to "Jurnal",
                        "nilai" to "Daftar Nilai"
                    ).forEach { (tabId, label) ->
                        val active = dailyTab == tabId
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (active) CrimsonPrimary else CreamSurfaceVariant,
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (active) CrimsonPrimary else AmberBorder),
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { onSelectDailyTab(tabId) }
                        ) {
                            Box(
                                modifier = Modifier.padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = label,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (active) Color.White else SlateTextDark
                                )
                            }
                        }
                    }
                }
            }
        }

        // TAB 1: PRESENSI SISWA
        if (dailyTab == "presensi") {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, AmberBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Presensi Hari Ini (${profile.activeClass})", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = SlateTextDark)
                        val present = students.count { it.attendance == "H" }
                        Text("$present/${students.size} Hadir", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = SuccessGreen)
                    }

                    students.forEach { s ->
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = CreamSurfaceVariant,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(s.name, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SlateTextDark)
                                    Text("NIS: ${s.nis}", fontSize = 10.sp, color = SlateTextMedium)
                                }

                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    listOf("H", "S", "I", "A").forEach { code ->
                                        val active = s.attendance == code
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(
                                                    if (active) {
                                                        if (code == "H") SuccessGreen else CrimsonPrimary
                                                    } else Color.White
                                                )
                                                .border(1.dp, if (active) Color.Transparent else AmberBorder, RoundedCornerShape(6.dp))
                                                .clickable { onUpdateAttendance(s.id, code) }
                                                .padding(horizontal = 10.dp, vertical = 6.dp)
                                        ) {
                                            Text(
                                                text = code,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 11.sp,
                                                color = if (active) Color.White else SlateTextDark
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // TAB 2: JURNAL MENGAJAR
        if (dailyTab == "jurnal") {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, AmberBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Catat Jurnal Mengajar", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = CrimsonPrimary)

                    OutlinedTextField(
                        value = journalDate,
                        onValueChange = { journalDate = it },
                        label = { Text("Tanggal Pertemuan") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    ExposedDropdownMenuBox(
                        expanded = materiDropdownExpanded,
                        onExpandedChange = { materiDropdownExpanded = !materiDropdownExpanded }
                    ) {
                        OutlinedTextField(
                            value = journalMateri,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Materi Pembelajaran (TP)") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = materiDropdownExpanded) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        ExposedDropdownMenu(
                            expanded = materiDropdownExpanded,
                            onDismissRequest = { materiDropdownExpanded = false }
                        ) {
                            tps.forEach { tp ->
                                DropdownMenuItem(
                                    text = { Text("TP ${tp.code} - ${tp.title}") },
                                    onClick = {
                                        journalMateri = tp.title
                                        materiDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = journalActivity,
                        onValueChange = { journalActivity = it },
                        label = { Text("Kegiatan & Catatan Kelas") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        maxLines = 3
                    )

                    Button(
                        onClick = {
                            val presentCount = "${students.count { it.attendance == "H" }}/${students.size} Hadir"
                            onAddJournal(journalDate, profile.activeClass, journalMateri, journalActivity, presentCount)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CrimsonPrimary)
                    ) {
                        Text("Simpan Jurnal Mengajar", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }

            // Journal History
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, AmberBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Riwayat Jurnal Pertemuan", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = SlateTextDark)
                    journals.forEach { j ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = CreamSurfaceVariant,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("${j.date} • Kelas ${j.rombel}", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = CrimsonPrimary)
                                    Text(j.presentCount, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SuccessGreen)
                                }
                                Text("Materi: ${j.materi}", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = SlateTextDark)
                                Text(j.activity, fontSize = 10.sp, color = SlateTextMedium)
                            }
                        }
                    }
                }
            }
        }

        // TAB 3: DAFTAR NILAI
        if (dailyTab == "nilai") {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, AmberBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Daftar Nilai Siswa (${profile.activeClass})", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = SlateTextDark)
                        Button(
                            onClick = {
                                val csv = DocumentExporter.generateERaporCsv(profile, students, tps)
                                DocumentExporter.shareDocument(context, "Daftar_Nilai_${profile.subject}_${profile.activeClass}.csv", csv, "text/csv")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = CrimsonPrimary),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Ekspor CSV", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    students.forEach { s ->
                        val summary = s.calculateSummary(tps)
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = CreamSurfaceVariant,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(s.name, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SlateTextDark)
                                        Text("NIS: ${s.nis}", fontSize = 9.sp, color = SlateTextMedium)
                                    }
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = CrimsonPrimary
                                    ) {
                                        Text(
                                            text = "Rapor: ${summary.finalScore}",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp,
                                            color = Color.White,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                // Grade input boxes for TP1, TP2, TP3, TP4, STS, SAS
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    listOf(
                                        "TP1" to s.tp1 to "tp1",
                                        "TP2" to s.tp2 to "tp2",
                                        "TP3" to s.tp3 to "tp3",
                                        "TP4" to s.tp4 to "tp4",
                                        "STS" to s.sts to "sts",
                                        "SAS" to s.sas to "sas"
                                    ).forEach { (info, field) ->
                                        val label = info.first
                                        val score = info.second
                                        Column(
                                            modifier = Modifier.weight(1f),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(label, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = SlateTextMedium)
                                            OutlinedTextField(
                                                value = score.toString(),
                                                onValueChange = { newVal ->
                                                    val intVal = newVal.toIntOrNull() ?: 0
                                                    onUpdateScore(s.id, field, intVal)
                                                },
                                                modifier = Modifier.fillMaxWidth(),
                                                textStyle = androidx.compose.ui.text.TextStyle(
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                                ),
                                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                                shape = RoundedCornerShape(8.dp),
                                                singleLine = true
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Add Student input
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = newStudentName,
                            onValueChange = { newStudentName = it },
                            placeholder = { Text("Nama siswa baru...", fontSize = 11.sp) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        Button(
                            onClick = {
                                if (newStudentName.isNotBlank()) {
                                    onAddStudent(newStudentName)
                                    newStudentName = ""
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = CrimsonPrimary),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Tambah", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
