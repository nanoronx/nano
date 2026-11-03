package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FileDownload
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ClassProfile
import com.example.model.LearningObjective
import com.example.model.StudentGrade
import com.example.ui.components.DocumentExporter
import com.example.ui.theme.AmberBorder
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamSurfaceVariant
import com.example.ui.theme.CrimsonLight
import com.example.ui.theme.CrimsonPrimary
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.GoldSecondary
import com.example.ui.theme.SlateTextDark
import com.example.ui.theme.SlateTextMedium
import com.example.ui.theme.SuccessGreen

@Composable
fun ReportCardScreen(
    profile: ClassProfile,
    students: List<StudentGrade>,
    tps: List<LearningObjective>,
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
        // Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, AmberBorder)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = CrimsonPrimary,
                    modifier = Modifier.size(44.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = Color.White)
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Deskripsi & Rekap Nilai Rapor",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Black,
                        color = SlateTextDark
                    )
                    Text(
                        text = "Deskripsi capaian kompetensi otomatis dianalisis per siswa.",
                        fontSize = 11.sp,
                        color = SlateTextMedium
                    )
                }
            }
        }

        // Student Report Cards with dynamic narrative
        students.forEach { s ->
            val summary = s.calculateSummary(tps)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, AmberBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(s.name, fontWeight = FontWeight.Black, fontSize = 14.sp, color = SlateTextDark)
                            Text("NIS: ${s.nis} • Kelas ${profile.activeClass}", fontSize = 10.sp, color = SlateTextMedium)
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = CrimsonPrimary
                        ) {
                            Text(
                                text = "Nilai Akhir: ${summary.finalScore}",
                                fontWeight = FontWeight.Black,
                                fontSize = 12.sp,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = CreamSurfaceVariant,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "Deskripsi Capaian Kompetensi Kurikulum Merdeka:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                color = CrimsonPrimary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = summary.reportDescription,
                                fontSize = 11.sp,
                                color = SlateTextDark,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }

        // Export Buttons
        Button(
            onClick = {
                val csv = DocumentExporter.generateERaporCsv(profile, students, tps)
                DocumentExporter.shareDocument(context, "Rekap_Nilai_eRapor_${profile.subject}_${profile.activeClass}.csv", csv, "text/csv")
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen)
        ) {
            Icon(Icons.Default.FileDownload, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Ekspor CSV untuk e-Rapor", fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }

        Button(
            onClick = {
                val csv = DocumentExporter.generateERaporCsv(profile, students, tps)
                DocumentExporter.shareDocument(context, "Rekap_Nilai_Rapor_${profile.subject}_${profile.activeClass}.doc", csv)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CrimsonPrimary)
        ) {
            Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Ekspor Rekap Nilai Rapor (.doc)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
    }
}
