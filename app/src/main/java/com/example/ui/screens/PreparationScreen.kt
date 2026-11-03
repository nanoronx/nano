package com.example.ui.screens

import android.content.Context
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
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
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
import com.example.ui.theme.GoldSecondary
import com.example.ui.theme.SlateTextDark
import com.example.ui.theme.SlateTextMedium
import com.example.ui.theme.SuccessGreen

@Composable
fun PreparationScreen(
    profile: ClassProfile,
    cpText: String,
    tps: List<LearningObjective>,
    onTogglePromesWeek: (String, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val promesHorizontalScroll = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CreamBackground)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Stepper Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, AmberBorder)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = CrimsonPrimary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Persiapan Semester (ATP, Prota, Promes, Silabus)",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black,
                        color = SlateTextDark
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))

                val docs = listOf(
                    "ATP" to "Alur Tujuan Pembelajaran",
                    "Prota" to "Program Tahunan",
                    "Promes" to "Program Semester (16 Minggu)",
                    "Silabus" to "Silabus Pembelajaran"
                )

                docs.forEach { (code, title) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = CrimsonPrimary
                            ) {
                                Text(
                                    text = code,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(title, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = SlateTextDark)
                        }

                        OutlinedButton(
                            onClick = {
                                val text = if (code == "Promes") {
                                    DocumentExporter.generatePromesText(profile, tps)
                                } else {
                                    DocumentExporter.generateAtpText(profile, cpText, tps)
                                }
                                DocumentExporter.shareDocument(context, "$title ${profile.subject} ${profile.activeClass}", text)
                            },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(12.dp), tint = CrimsonPrimary)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Bagikan", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = CrimsonPrimary)
                        }
                    }
                }
            }
        }

        // Program Semester (Promes) 16 Minggu Efektif
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
                    Column {
                        Text(
                            text = "Program Semester (Promes)",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = SlateTextDark
                        )
                        Text(
                            text = "Semester ${profile.semester} • 16 Minggu Efektif (Ketuk kotak untuk atur jadwal)",
                            fontSize = 11.sp,
                            color = SlateTextMedium
                        )
                    }
                    Button(
                        onClick = {
                            val text = DocumentExporter.generatePromesText(profile, tps)
                            DocumentExporter.shareDocument(context, "Promes ${profile.subject} ${profile.activeClass}", text)
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

                // Scrollable Matrix of 16 weeks
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(promesHorizontalScroll)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        // Weeks Header Row
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.width(140.dp)) {
                                Text("Tujuan Pembelajaran", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = CrimsonPrimary)
                            }
                            (1..16).forEach { week ->
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .padding(horizontal = 2.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "$week",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp,
                                        color = SlateTextMedium
                                    )
                                }
                            }
                        }

                        // TP Rows
                        tps.forEach { tp ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Column(modifier = Modifier.width(140.dp)) {
                                    Text("TP ${tp.code}", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = CrimsonPrimary)
                                    Text(tp.title, fontSize = 10.sp, color = SlateTextDark, maxLines = 1)
                                    Text("${tp.jp} JP", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GoldPrimary)
                                }

                                (1..16).forEach { week ->
                                    val active = tp.weeks.contains(week)
                                    Box(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .padding(2.dp)
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(if (active) CrimsonPrimary else CreamSurfaceVariant)
                                            .border(
                                                width = 1.dp,
                                                color = if (active) GoldSecondary else AmberBorder,
                                                shape = RoundedCornerShape(6.dp)
                                            )
                                            .clickable { onTogglePromesWeek(tp.id, week) },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (active) {
                                            Icon(
                                                Icons.Default.Check,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(14.dp)
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

        // Preview Table
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, AmberBorder)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Pratinjau Tabel Alur Tujuan Pembelajaran (ATP)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = SlateTextDark
                )
                Spacer(modifier = Modifier.height(10.dp))

                tps.forEach { tp ->
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = CreamSurfaceVariant,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("TP ${tp.code}: ${tp.title}", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SlateTextDark)
                                Text("${tp.jp} JP", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = CrimsonPrimary)
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text("Materi: ${tp.materi}", fontSize = 10.sp, color = SlateTextMedium)
                            Text("Indikator: ${tp.indikator}", fontSize = 10.sp, color = SlateTextMedium)
                        }
                    }
                }
            }
        }
    }
}
