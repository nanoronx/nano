package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.School
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ClassProfile
import com.example.ui.theme.AmberBorder
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CrimsonDark
import com.example.ui.theme.CrimsonLight
import com.example.ui.theme.CrimsonPrimary
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.GoldSecondary
import com.example.ui.theme.GoldShimmer
import com.example.ui.theme.SlateTextDark
import com.example.ui.theme.SlateTextMedium
import com.example.ui.theme.SuccessGreen
import com.example.viewmodel.AppScreen

data class ModuleCardItem(
    val title: String,
    val targetScreen: AppScreen,
    val subAction: String? = null,
    val isHighlighted: Boolean = false
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DashboardScreen(
    profile: ClassProfile,
    onNavigate: (AppScreen) -> Unit,
    onOpenRombelModal: () -> Unit,
    onSelectSubTab: (String, String) -> Unit, // category, value
    modifier: Modifier = Modifier
) {
    val modules = listOf(
        ModuleCardItem("Data Kelas Saya", AppScreen.ClassProfileScreen, null, false),
        ModuleCardItem("CP dan TP", AppScreen.CpTp, null, false),
        ModuleCardItem("ATP", AppScreen.Preparation, null, false),
        ModuleCardItem("Prota", AppScreen.Preparation, null, false),
        ModuleCardItem("Promes", AppScreen.Preparation, null, true),
        ModuleCardItem("Silabus", AppScreen.Preparation, null, true),
        ModuleCardItem("Modul Ajar / RPP", AppScreen.LessonKit, "modul", true),
        ModuleCardItem("LKPD", AppScreen.LessonKit, "lkpd", false),
        ModuleCardItem("Soal Harian & Kuis", AppScreen.LessonKit, "kuis", true),
        ModuleCardItem("Asesmen Sumatif", AppScreen.Assessment, "Asesmen Sumatif", true),
        ModuleCardItem("Soal STS dan SAS", AppScreen.Assessment, "Soal STS dan SAS", true),
        ModuleCardItem("Daftar Nilai", AppScreen.DailyClass, "nilai", true),
        ModuleCardItem("Presensi Siswa", AppScreen.DailyClass, "presensi", false),
        ModuleCardItem("Jurnal Mengajar", AppScreen.DailyClass, "jurnal", true),
        ModuleCardItem("Deskripsi Rapor", AppScreen.ReportCard, null, true),
        ModuleCardItem("Rekap Nilai Rapor", AppScreen.ReportCard, null, true),
        ModuleCardItem("Paket Siap Supervisi", AppScreen.SupervisionPackage, null, true),
        ModuleCardItem("Template Kop Sekolah", AppScreen.SchoolTemplate, null, false)
    )

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        modifier = modifier
            .fillMaxSize()
            .background(CreamBackground)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Hero Banner
        item(span = { GridItemSpan(maxLineSpan) }) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(CrimsonDark, CrimsonPrimary, GoldSecondary)
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color.White.copy(alpha = 0.2f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = GoldLight,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Kurikulum Merdeka • ${profile.schoolName}",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Satu aplikasi, administrasi guru satu semester.",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            lineHeight = 26.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Sekali atur data kelas (${profile.activeClass} - ${profile.subject}), seluruh ATP, Promes, Modul Ajar, Nilai & Rapor siap dalam sekejap.",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = { onNavigate(AppScreen.ClassProfileScreen) },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = GoldLight,
                                    contentColor = CrimsonDark
                                )
                            ) {
                                Icon(Icons.Default.School, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Atur Data Kelas", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }

                            OutlinedButton(
                                onClick = onOpenRombelModal,
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
                            ) {
                                Text("Pilih Rombel", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }

        // 4 Quick Steps Card Row
        item(span = { GridItemSpan(maxLineSpan) }) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Langkah Cepat Penggunaan",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = SlateTextDark,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(
                        "1. Kelas & Mapel" to AppScreen.ClassProfileScreen,
                        "2. Promes & ATP" to AppScreen.Preparation,
                        "3. Input Nilai" to AppScreen.DailyClass,
                        "4. Unduh Supervisi" to AppScreen.SupervisionPackage
                    ).forEach { (step, screen) ->
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color.White,
                            border = androidx.compose.foundation.BorderStroke(1.dp, AmberBorder),
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { onNavigate(screen) }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(CrimsonPrimary)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = step,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SlateTextDark
                                )
                            }
                        }
                    }
                }
            }
        }

        // Section Title: 18 Modules Grid
        item(span = { GridItemSpan(maxLineSpan) }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .width(4.dp)
                            .height(18.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(CrimsonPrimary)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "18 Modul Administrasi Guru",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black,
                        color = SlateTextDark
                    )
                }
                Text(
                    text = "${profile.activeClass} • ${profile.subject}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = CrimsonPrimary
                )
            }
        }

        // The 18 Modul Cards
        itemsIndexed(modules) { index, card ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .clickable {
                        if (card.subAction != null) {
                            if (card.targetScreen == AppScreen.LessonKit) {
                                onSelectSubTab("perangkat", card.subAction)
                            } else if (card.targetScreen == AppScreen.Assessment) {
                                onSelectSubTab("asesmen", card.subAction)
                            } else if (card.targetScreen == AppScreen.DailyClass) {
                                onSelectSubTab("daily", card.subAction)
                            }
                        }
                        onNavigate(card.targetScreen)
                    },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(
                    width = if (card.isHighlighted) 1.5.dp else 1.dp,
                    color = if (card.isHighlighted) CrimsonLight else AmberBorder
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (card.isHighlighted) CrimsonPrimary.copy(alpha = 0.1f) else GoldShimmer.copy(alpha = 0.4f),
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Assignment,
                                    contentDescription = null,
                                    tint = if (card.isHighlighted) CrimsonPrimary else GoldPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        Text(
                            text = "#${index + 1}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = SlateTextMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = card.title,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = SlateTextDark,
                        maxLines = 2
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = SuccessGreen,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Siap dipakai",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = SuccessGreen
                        )
                    }
                }
            }
        }

        // Bottom Banner for Supervision Package
        item(span = { GridItemSpan(maxLineSpan) }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, GoldSecondary)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Download,
                            contentDescription = null,
                            tint = CrimsonPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Paket Siap Supervisi 1 Semester",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = CrimsonDark
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Unduh seluruh berkas administrasi (ATP, Prota, Promes, Silabus, Modul Ajar, Nilai, Rapor) lengkap dengan Kop Sekolah dan Tanda Tangan.",
                        fontSize = 11.sp,
                        color = SlateTextMedium,
                        lineHeight = 16.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { onNavigate(AppScreen.SupervisionPackage) },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CrimsonPrimary),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Buka Paket Supervisi", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
