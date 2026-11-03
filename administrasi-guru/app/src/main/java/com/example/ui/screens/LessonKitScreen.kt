package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ClassProfile
import com.example.model.LearningObjective
import com.example.ui.components.DocumentExporter
import com.example.ui.components.SchoolHeaderView
import com.example.ui.theme.AmberBorder
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamSurfaceVariant
import com.example.ui.theme.CrimsonLight
import com.example.ui.theme.CrimsonPrimary
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.SlateTextDark
import com.example.ui.theme.SlateTextMedium
import com.example.ui.theme.SuccessGreen

@Composable
fun LessonKitScreen(
    profile: ClassProfile,
    tps: List<LearningObjective>,
    selectedTpId: String,
    perangkatType: String, // "modul" | "lkpd" | "kuis"
    onSelectPerangkatType: (String) -> Unit,
    onSelectTpId: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val tpHorizontalScroll = rememberScrollState()

    val selectedTp = tps.find { it.id == selectedTpId } ?: tps.firstOrNull()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CreamBackground)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Document Type Switcher (Modul Ajar | LKPD | Kuis)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, AmberBorder)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "Pilih Jenis Dokumen Pembelajaran",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = SlateTextDark
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(
                        "modul" to "Modul Ajar / RPP",
                        "lkpd" to "LKPD",
                        "kuis" to "Soal Kuis"
                    ).forEach { (type, label) ->
                        val active = perangkatType == type
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (active) CrimsonPrimary else CreamSurfaceVariant,
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (active) CrimsonPrimary else AmberBorder),
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { onSelectPerangkatType(type) }
                        ) {
                            Column(
                                modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = label,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (active) Color.White else SlateTextDark
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "✓ Siap",
                                    fontSize = 9.sp,
                                    color = if (active) Color.White.copy(alpha = 0.8f) else SuccessGreen,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        // TP Selector Horizontal Chips
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, AmberBorder)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "Pilih Tujuan Pembelajaran (TP)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = SlateTextDark
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(tpHorizontalScroll),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    tps.forEach { tp ->
                        val selected = tp.id == selectedTpId
                        FilterChip(
                            selected = selected,
                            onClick = { onSelectTpId(tp.id) },
                            label = { Text("TP ${tp.code} - ${tp.title.take(16)}...", fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = CrimsonPrimary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }
        }

        // Live Document Preview Card (Paper layout with official Kop Sekolah)
        if (selectedTp != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, AmberBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    // Export Button Top Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = when (perangkatType) {
                                "modul" -> "MODUL AJAR / RPP"
                                "lkpd" -> "LEMBAR KERJA (LKPD)"
                                else -> "SOAL KUIS HARIAN"
                            },
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black,
                            color = CrimsonPrimary
                        )

                        Button(
                            onClick = {
                                val text = DocumentExporter.generateModulAjarText(profile, selectedTp)
                                DocumentExporter.shareDocument(context, "Modul Ajar TP ${selectedTp.code} ${profile.activeClass}", text)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = CrimsonPrimary),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Ekspor Doc", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Official Kop Sekolah
                    SchoolHeaderView(profile = profile)

                    // Identity Box
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = CreamSurfaceVariant,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text("Satuan Pendidikan : ${profile.schoolName}", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            Text("Mata Pelajaran    : ${profile.subject}", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            Text("Kelas / Semester  : ${profile.activeClass} / ${profile.semester}", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            Text("Alokasi Waktu     : ${selectedTp.jp} JP (Minggu ke-${selectedTp.weeks.joinToString(", ")})", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Document Content Body
                    when (perangkatType) {
                        "modul" -> {
                            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Text("A. TUJUAN PEMBELAJARAN (TP ${selectedTp.code})", fontWeight = FontWeight.Black, fontSize = 12.sp, color = CrimsonPrimary)
                                Text("Peserta didik mampu memahami dan menguasai ${selectedTp.title}.", fontSize = 11.sp)
                                Text("Indikator: ${selectedTp.indikator}", fontSize = 11.sp, color = SlateTextMedium)

                                Text("B. KEGIATAN PEMBELAJARAN", fontWeight = FontWeight.Black, fontSize = 12.sp, color = CrimsonPrimary)
                                Text("1. Pendahuluan (10 Menit): Guru menyapa, presensi kelas ${profile.activeClass}, dan apersepsi materi ${selectedTp.title}.", fontSize = 11.sp)
                                Text("2. Kegiatan Inti (60 Menit): ${selectedTp.aktivitas}", fontSize = 11.sp)
                                Text("3. Penutup (10 Menit): Refleksi bersama peserta didik, evaluasi materi, dan doa penutup.", fontSize = 11.sp)

                                Text("C. ASESMEN PEMBELAJARAN", fontWeight = FontWeight.Black, fontSize = 12.sp, color = CrimsonPrimary)
                                Text("- Formatif: Observasi diskusi & LKPD kelompok\n- Sumatif: Tes tertulis pilihan ganda & uraian", fontSize = 11.sp)
                            }
                        }

                        "lkpd" -> {
                            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, AmberBorder),
                                    color = Color.White,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text("Nama Kelompok / Siswa: .....................................................", fontSize = 11.sp)
                                        Text("Kelas / Rombel        : ${profile.activeClass}", fontSize = 11.sp)
                                        Text("Topik Pembelajaran   : ${selectedTp.title}", fontSize = 11.sp)
                                    }
                                }

                                Text("Petunjuk Aktivitas Kelompok:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = CrimsonPrimary)
                                Text(selectedTp.aktivitas, fontSize = 11.sp)

                                Text("Soal Uraian / Eksplorasi:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = CrimsonPrimary)
                                Text(selectedTp.essay, fontSize = 11.sp, fontWeight = FontWeight.Medium)

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(80.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(CreamSurfaceVariant)
                                        .border(1.dp, AmberBorder, RoundedCornerShape(8.dp))
                                        .padding(8.dp)
                                ) {
                                    Text("Ruang jawaban siswa...", fontSize = 10.sp, color = SlateTextMedium)
                                }
                            }
                        }

                        "kuis" -> {
                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                Text("Soal Formatif Pilihan Ganda (TP ${selectedTp.code})", fontWeight = FontWeight.Black, fontSize = 12.sp, color = CrimsonPrimary)
                                selectedTp.questions.forEachIndexed { idx, q ->
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = CreamSurfaceVariant,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(modifier = Modifier.padding(10.dp)) {
                                            Text("${idx + 1}. ${q.q}", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = SlateTextDark)
                                            Spacer(modifier = Modifier.height(4.dp))
                                            q.options.forEachIndexed { oIdx, opt ->
                                                val letter = ('A' + oIdx)
                                                Text("   $letter. $opt", fontSize = 11.sp, color = SlateTextDark)
                                            }
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = "✓ Kunci: ${q.ans} (Level Kognitif: ${q.level})",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 10.sp,
                                                color = SuccessGreen
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
    }
}
