package com.example.ui.components

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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.CurriculumBank
import com.example.model.ClassProfile
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RombelSelectionDialog(
    profile: ClassProfile,
    activeTab: String,
    onTabSelected: (String) -> Unit,
    onToggleRombel: (String) -> Unit,
    onToggleGradeGroup: (String) -> Unit,
    onAddCustomRombel: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var customInput by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(scrollState)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.School,
                                contentDescription = null,
                                tint = CrimsonPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Multi-Rombel Paralel",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = CrimsonPrimary
                            )
                        }
                        Text(
                            text = "Kelola Rombel Kelas",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = SlateTextDark
                        )
                        Text(
                            text = "Pilih kelas yang diampu (VII A–F, VIII A–F, IX A–F)",
                            fontSize = 11.sp,
                            color = SlateTextMedium
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Tutup", tint = SlateTextMedium)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Tabs: ALL, VII, VIII, IX
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf("ALL" to "Semua", "VII" to "Kelas VII", "VIII" to "Kelas VIII", "IX" to "Kelas IX").forEach { (tabId, label) ->
                        val selected = activeTab == tabId
                        FilterChip(
                            selected = selected,
                            onClick = { onTabSelected(tabId) },
                            label = { Text(label, fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = CrimsonPrimary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Grade Groups
                listOf("VII", "VIII", "IX").filter { activeTab == "ALL" || activeTab == it }.forEach { grade ->
                    val rombels = CurriculumBank.ROMBEL_GROUPS[grade] ?: emptyList()
                    val selectedInGroup = rombels.count { profile.parallelClasses.contains(it) }
                    val allSelected = selectedInGroup == rombels.size

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = CreamSurfaceVariant),
                        border = androidx.compose.foundation.BorderStroke(1.dp, AmberBorder)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = CrimsonPrimary
                                    ) {
                                        Text(
                                            text = "KELAS $grade",
                                            color = Color.White,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "$selectedInGroup/6 terpilih",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = GoldPrimary
                                    )
                                }
                                TextButton(onClick = { onToggleGradeGroup(grade) }) {
                                    Text(
                                        text = if (allSelected) "Reset" else "+ Pilih Semua",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = CrimsonLight
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                rombels.forEach { rombel ->
                                    val isSelected = profile.parallelClasses.contains(rombel)
                                    val isActive = profile.activeClass == rombel
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(
                                                if (isSelected) CrimsonPrimary else Color.White
                                            )
                                            .border(
                                                width = if (isActive) 2.dp else 1.dp,
                                                color = if (isActive) GoldSecondary else AmberBorder,
                                                shape = RoundedCornerShape(10.dp)
                                            )
                                            .clickable { onToggleRombel(rombel) }
                                            .padding(horizontal = 12.dp, vertical = 8.dp)
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = rombel,
                                                color = if (isSelected) Color.White else SlateTextDark,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 12.sp
                                            )
                                            if (isActive) {
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(
                                                    text = "★",
                                                    color = GoldLight,
                                                    fontSize = 10.sp
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Add Custom Rombel
                OutlinedTextField(
                    value = customInput,
                    onValueChange = { customInput = it },
                    placeholder = { Text("Ketik nama rombel lain (mis: VII G)...", fontSize = 12.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                if (customInput.isNotBlank()) {
                                    onAddCustomRombel(customInput)
                                    customInput = ""
                                }
                            }
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Tambah Rombel", tint = CrimsonPrimary)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Bottom summary and confirmation
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CrimsonPrimary)
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Simpan (${profile.parallelClasses.size} Rombel Aktif)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}
