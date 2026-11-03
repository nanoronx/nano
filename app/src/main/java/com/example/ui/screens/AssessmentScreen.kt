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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
fun AssessmentScreen(
    profile: ClassProfile,
    tps: List<LearningObjective>,
    examType: String, // "Asesmen Sumatif" | "Soal STS dan SAS"
    examDocTab: String, // "kisi" | "soal_kunci" | "lembar_siswa"
    onSelectExamType: (String) -> Unit,
    onSelectExamDocTab: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CreamBackground)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Exam Type Selector Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, AmberBorder)
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
                            modifier = Modifier.size(38.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Assignment, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Asesmen & Ujian Semester",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = SlateTextDark
                            )
                            Text(
                                text = "${profile.subject} • Kelas ${profile.activeClass}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = CrimsonPrimary
                            )
                        }
                    }

                    Button(
                        onClick = {
                            val text = DocumentExporter.generateAssessmentText(profile, examType, tps)
                            DocumentExporter.shareDocument(context, "$examType ${profile.subject} ${profile.activeClass}", text)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = CrimsonPrimary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Ekspor", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Exam Type Choice (Asesmen Sumatif vs Soal STS dan SAS)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("Asesmen Sumatif", "Soal STS dan SAS").forEach { type ->
                        val active = examType == type
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (active) CrimsonPrimary else CreamSurfaceVariant,
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (active) CrimsonPrimary else AmberBorder),
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { onSelectExamType(type) }
                        ) {
                            Row(
                                modifier = Modifier.padding(vertical = 12.dp, horizontal = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = type,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (active) Color.White else SlateTextDark
                                )
                                if (active) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }
                }
            }
        }

        // 3 Doc Tabs: Kisi-kisi | Soal dan Kunci | Lembar Siswa
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, AmberBorder)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf(
                        "kisi" to "Kisi-kisi",
                        "soal_kunci" to "Soal & Kunci",
                        "lembar_siswa" to "Lembar Siswa"
                    ).forEach { (tabId, label) ->
                        val active = examDocTab == tabId
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (active) CrimsonPrimary else CreamSurfaceVariant,
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (active) CrimsonPrimary else AmberBorder),
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { onSelectExamDocTab(tabId) }
                        ) {
                            Box(
                                modifier = Modifier.padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = label,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (active) Color.White else SlateTextDark
                                )
                            }
                        }
                    }
                }
            }
        }

        // Tab Content
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, AmberBorder)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                when (examDocTab) {
                    "kisi" -> {
                        Text(
                            text = "Kisi-Kisi Penulisan Soal ($examType)",
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp,
                            color = CrimsonPrimary
                        )

                        tps.forEachIndexed { i, tp ->
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
                                        Text("${i + 1}. TP ${tp.code}: ${tp.title}", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = SlateTextDark)
                                        Text("Level C2/C3", fontWeight = FontWeight.Bold, fontSize = 10.sp, color = GoldPrimary)
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text("Materi: ${tp.materi}", fontSize = 10.sp, color = SlateTextMedium)
                                    Text("Indikator Soal: ${tp.indikator}", fontSize = 10.sp, color = SlateTextMedium)
                                    Text("Bentuk Soal: Pilihan Ganda & Uraian", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = CrimsonLight)
                                }
                            }
                        }
                    }

                    "soal_kunci" -> {
                        Text(
                            text = "Naskah Soal Lengkap & Kunci Jawaban",
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp,
                            color = CrimsonPrimary
                        )

                        tps.forEach { tp ->
                            tp.questions.forEachIndexed { qIdx, q ->
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = CreamSurfaceVariant,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text("[TP ${tp.code}] ${q.q}", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = SlateTextDark)
                                        Spacer(modifier = Modifier.height(4.dp))
                                        q.options.forEachIndexed { oIdx, opt ->
                                            val letter = ('A' + oIdx)
                                            Text("   $letter. $opt", fontSize = 10.sp, color = SlateTextDark)
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "✓ Kunci Jawaban: ${q.ans} (Level Kognitif: ${q.level})",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 10.sp,
                                            color = SuccessGreen
                                        )
                                    }
                                }
                            }
                        }
                    }

                    "lembar_siswa" -> {
                        Text(
                            text = "Lembar Soal Peserta Didik (Siap Cetak)",
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp,
                            color = CrimsonPrimary
                        )

                        tps.forEach { tp ->
                            tp.questions.forEachIndexed { qIdx, q ->
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = CreamSurfaceVariant,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text("[TP ${tp.code}] ${q.q}", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = SlateTextDark)
                                        Spacer(modifier = Modifier.height(4.dp))
                                        q.options.forEachIndexed { oIdx, opt ->
                                            val letter = ('A' + oIdx)
                                            Text("   $letter. $opt", fontSize = 10.sp, color = SlateTextDark)
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
