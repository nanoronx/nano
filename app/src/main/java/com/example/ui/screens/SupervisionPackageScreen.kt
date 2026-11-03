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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FolderShared
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import com.example.ui.theme.CrimsonLight
import com.example.ui.theme.CrimsonPrimary
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.GoldSecondary
import com.example.ui.theme.SlateTextDark
import com.example.ui.theme.SlateTextMedium
import com.example.ui.theme.SuccessGreen

data class SupervisionDocItem(
    val id: String,
    val title: String,
    val filename: String
)

@Composable
fun SupervisionPackageScreen(
    profile: ClassProfile,
    cpText: String,
    tps: List<LearningObjective>,
    students: List<StudentGrade>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val docList = listOf(
        SupervisionDocItem("atp", "Alur Tujuan Pembelajaran (ATP)", "ATP_${profile.subject}_${profile.activeClass}.doc"),
        SupervisionDocItem("prota", "Program Tahunan (Prota)", "Prota_${profile.subject}_${profile.activeClass}.doc"),
        SupervisionDocItem("promes", "Program Semester (Promes)", "Promes_${profile.subject}_${profile.activeClass}.doc"),
        SupervisionDocItem("silabus", "Silabus Pembelajaran", "Silabus_${profile.subject}_${profile.activeClass}.doc"),
        SupervisionDocItem("modul", "Modul Ajar / RPP", "Modul_Ajar_${profile.subject}_${profile.activeClass}.doc"),
        SupervisionDocItem("asesmen", "Asesmen Sumatif & Kisi-Kisi", "Asesmen_${profile.subject}_${profile.activeClass}.doc"),
        SupervisionDocItem("nilai", "Daftar Nilai Siswa", "Daftar_Nilai_${profile.subject}_${profile.activeClass}.doc"),
        SupervisionDocItem("rapor", "Rekap Nilai untuk e-Rapor", "Rekap_eRapor_${profile.subject}_${profile.activeClass}.doc")
    )

    fun getDocContent(id: String): String {
        return when (id) {
            "atp", "prota", "silabus" -> DocumentExporter.generateAtpText(profile, cpText, tps)
            "promes" -> DocumentExporter.generatePromesText(profile, tps)
            "modul" -> DocumentExporter.generateModulAjarText(profile, tps.firstOrNull() ?: tps[0])
            "asesmen" -> DocumentExporter.generateAssessmentText(profile, "Asesmen Sumatif", tps)
            "nilai", "rapor" -> DocumentExporter.generateERaporCsv(profile, students, tps)
            else -> DocumentExporter.generateAtpText(profile, cpText, tps)
        }
    }

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
                        Icon(Icons.Default.FolderShared, contentDescription = null, tint = Color.White)
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Paket Siap Supervisi",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Black,
                        color = SlateTextDark
                    )
                    Text(
                        text = "8 Berkas administrasi lengkap 1 semester siap diperiksa.",
                        fontSize = 11.sp,
                        color = SlateTextMedium
                    )
                }
            }
        }

        // Summary Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, GoldSecondary)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Daftar Berkas Supervisi Guru (${profile.subject} - ${profile.activeClass})",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = CrimsonPrimary
                )
                Spacer(modifier = Modifier.height(10.dp))

                docList.forEach { doc ->
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color.White,
                        border = androidx.compose.foundation.BorderStroke(1.dp, AmberBorder),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                Icon(Icons.Default.Description, contentDescription = null, tint = CrimsonLight, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(doc.title, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = SlateTextDark)
                                    Text(doc.filename, fontSize = 9.sp, color = SlateTextMedium)
                                }
                            }

                            OutlinedButton(
                                onClick = {
                                    val content = getDocContent(doc.id)
                                    DocumentExporter.shareDocument(context, doc.filename, content)
                                },
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(12.dp), tint = CrimsonPrimary)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Unduh", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = CrimsonPrimary)
                            }
                        }
                    }
                }
            }
        }

        // Big Complete Package Export Button
        Button(
            onClick = {
                val completePackage = buildString {
                    append("========================================================\n")
                    append("PAKET LENGKAP ADMINISTRASI SUPERVISI GURU SATU SEMESTER\n")
                    append("========================================================\n\n")
                    append(DocumentExporter.generateAtpText(profile, cpText, tps))
                    append("\n\n")
                    append(DocumentExporter.generatePromesText(profile, tps))
                    append("\n\n")
                    append(DocumentExporter.generateAssessmentText(profile, "Asesmen Sumatif", tps))
                    append("\n\n")
                    append(DocumentExporter.generateERaporCsv(profile, students, tps))
                }
                DocumentExporter.shareDocument(
                    context,
                    "Paket_Supervisi_Lengkap_${profile.subject}_${profile.activeClass}.doc",
                    completePackage
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CrimsonPrimary)
        ) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Unduh Paket Lengkap Supervisi (.doc)", fontWeight = FontWeight.Black, fontSize = 14.sp)
        }
    }
}
