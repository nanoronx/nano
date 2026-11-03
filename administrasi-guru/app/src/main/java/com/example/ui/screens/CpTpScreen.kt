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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ClassProfile
import com.example.model.LearningObjective
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
fun CpTpScreen(
    profile: ClassProfile,
    cpText: String,
    tps: List<LearningObjective>,
    onUpdateCpText: (String) -> Unit,
    onUpdateTpTitle: (String, String) -> Unit,
    onAddNewTp: () -> Unit,
    onRemoveTp: (String) -> Unit,
    onNavigateToPreparation: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = CrimsonPrimary,
                        modifier = Modifier.size(44.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.MenuBook, contentDescription = null, tint = Color.White)
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "CP dan TP",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = SlateTextDark
                        )
                        Text(
                            text = "${profile.subject} • ${profile.fase} • Kelas ${profile.activeClass}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = CrimsonPrimary
                        )
                    }
                }

                OutlinedButton(
                    onClick = onAddNewTp,
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CrimsonPrimary)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = CrimsonPrimary, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Tambah TP", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = CrimsonPrimary)
                }
            }
        }

        // Capaian Pembelajaran Box
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CreamSurfaceVariant),
            border = androidx.compose.foundation.BorderStroke(1.dp, AmberBorder)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Capaian Pembelajaran (CP) — ${profile.fase}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = CrimsonPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = cpText,
                    onValueChange = onUpdateCpText,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    maxLines = 6
                )
            }
        }

        // List of TP Cards
        Text(
            text = "Daftar Tujuan Pembelajaran (${tps.size} TP)",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = SlateTextDark
        )

        tps.forEach { tp ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, AmberBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = CrimsonPrimary
                            ) {
                                Text(
                                    text = tp.code,
                                    color = Color.White,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = GoldPrimary.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = "${tp.jp} JP",
                                    color = GoldPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(16.dp))
                            if (tps.size > 1) {
                                IconButton(onClick = { onRemoveTp(tp.id) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Hapus TP", tint = CrimsonLight, modifier = Modifier.size(18.dp))
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = tp.title,
                        onValueChange = { onUpdateTpTitle(tp.id, it) },
                        label = { Text("Tujuan Pembelajaran") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Materi: ${tp.materi}",
                        fontSize = 11.sp,
                        color = SlateTextMedium
                    )
                    Text(
                        text = "Indikator: ${tp.indikator}",
                        fontSize = 11.sp,
                        color = SlateTextMedium
                    )
                }
            }
        }

        // Summary and Navigate Button
        Button(
            onClick = onNavigateToPreparation,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CrimsonPrimary)
        ) {
            Text("Lihat ATP, Prota & Promes", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
        }
    }
}
