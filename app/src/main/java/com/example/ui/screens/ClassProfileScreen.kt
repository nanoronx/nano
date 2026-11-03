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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CurriculumBank
import com.example.model.ClassProfile
import com.example.ui.theme.AmberBorder
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamSurfaceVariant
import com.example.ui.theme.CrimsonLight
import com.example.ui.theme.CrimsonPrimary
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.SlateTextDark
import com.example.ui.theme.SlateTextMedium

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassProfileScreen(
    profile: ClassProfile,
    onUpdateProfile: ((ClassProfile) -> ClassProfile) -> Unit,
    onSwitchSubject: (String) -> Unit,
    onSwitchActiveRombel: (String) -> Unit,
    onOpenRombelModal: () -> Unit,
    onNavigateToCpTp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    var subjectExpanded by remember { mutableStateOf(false) }
    var rombelExpanded by remember { mutableStateOf(false) }
    var semesterExpanded by remember { mutableStateOf(false) }

    val allSubjects = CurriculumBank.SUBJECTS.keys.toList()
    val allRombels = (CurriculumBank.ROMBEL_GROUPS.values.flatten() + profile.parallelClasses).distinct()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CreamBackground)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Title Card
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
                        Icon(Icons.Default.School, contentDescription = null, tint = Color.White)
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Data Kelas Saya",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Black,
                        color = SlateTextDark
                    )
                    Text(
                        text = "Isi sekali saja, seluruh dokumen semester otomatis sinkron.",
                        fontSize = 11.sp,
                        color = SlateTextMedium
                    )
                }
            }
        }

        // Form Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, AmberBorder)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // School Name
                OutlinedTextField(
                    value = profile.schoolName,
                    onValueChange = { onUpdateProfile { p -> p.copy(schoolName = it) } },
                    label = { Text("Satuan Pendidikan (Nama Sekolah)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    trailingIcon = { Icon(Icons.Default.Check, contentDescription = null, tint = GoldPrimary) }
                )

                // Rombel & Subject Selectors
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Active Rombel Dropdown
                    ExposedDropdownMenuBox(
                        expanded = rombelExpanded,
                        onExpandedChange = { rombelExpanded = !rombelExpanded },
                        modifier = Modifier.weight(1f)
                    ) {
                        OutlinedTextField(
                            value = profile.activeClass,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Rombel Aktif") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = rombelExpanded) },
                            modifier = Modifier.menuAnchor(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        ExposedDropdownMenu(
                            expanded = rombelExpanded,
                            onDismissRequest = { rombelExpanded = false }
                        ) {
                            allRombels.forEach { r ->
                                DropdownMenuItem(
                                    text = { Text(r, fontWeight = if (r == profile.activeClass) FontWeight.Bold else FontWeight.Normal) },
                                    onClick = {
                                        onSwitchActiveRombel(r)
                                        rombelExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Subject Dropdown
                    ExposedDropdownMenuBox(
                        expanded = subjectExpanded,
                        onExpandedChange = { subjectExpanded = !subjectExpanded },
                        modifier = Modifier.weight(1.3f)
                    ) {
                        OutlinedTextField(
                            value = profile.subject,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Mata Pelajaran") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = subjectExpanded) },
                            modifier = Modifier.menuAnchor(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        ExposedDropdownMenu(
                            expanded = subjectExpanded,
                            onDismissRequest = { subjectExpanded = false }
                        ) {
                            allSubjects.forEach { s ->
                                DropdownMenuItem(
                                    text = { Text(s, fontWeight = if (s == profile.subject) FontWeight.Bold else FontWeight.Normal) },
                                    onClick = {
                                        onSwitchSubject(s)
                                        subjectExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                // Academic Year & Semester
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = profile.academicYear,
                        onValueChange = { onUpdateProfile { p -> p.copy(academicYear = it) } },
                        label = { Text("Tahun Ajaran") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    )

                    ExposedDropdownMenuBox(
                        expanded = semesterExpanded,
                        onExpandedChange = { semesterExpanded = !semesterExpanded },
                        modifier = Modifier.weight(1f)
                    ) {
                        OutlinedTextField(
                            value = profile.semester,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Semester") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = semesterExpanded) },
                            modifier = Modifier.menuAnchor(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        ExposedDropdownMenu(
                            expanded = semesterExpanded,
                            onDismissRequest = { semesterExpanded = false }
                        ) {
                            listOf("Ganjil", "Genap").forEach { sem ->
                                DropdownMenuItem(
                                    text = { Text(sem) },
                                    onClick = {
                                        onUpdateProfile { p -> p.copy(semester = sem) }
                                        semesterExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text("Identitas Guru & Kepala Sekolah", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = CrimsonPrimary)

                // Teacher Details
                OutlinedTextField(
                    value = profile.teacherName,
                    onValueChange = { onUpdateProfile { p -> p.copy(teacherName = it) } },
                    label = { Text("Nama Guru Mata Pelajaran") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = profile.teacherNip,
                    onValueChange = { onUpdateProfile { p -> p.copy(teacherNip = it) } },
                    label = { Text("NIP Guru") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                // Principal Details
                OutlinedTextField(
                    value = profile.principalName,
                    onValueChange = { onUpdateProfile { p -> p.copy(principalName = it) } },
                    label = { Text("Nama Kepala Sekolah") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = profile.principalNip,
                    onValueChange = { onUpdateProfile { p -> p.copy(principalNip = it) } },
                    label = { Text("NIP Kepala Sekolah") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }

        // Parallel Classes Quick Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CreamSurfaceVariant),
            border = androidx.compose.foundation.BorderStroke(1.dp, AmberBorder)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Rombel Paralel Diampu",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = SlateTextDark
                        )
                        Text(
                            text = "${profile.parallelClasses.size} kelas paralel aktif",
                            fontSize = 11.sp,
                            color = SlateTextMedium
                        )
                    }
                    OutlinedButton(
                        onClick = onOpenRombelModal,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Kelola Kelas", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = CrimsonPrimary)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    profile.parallelClasses.take(5).forEach { r ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (r == profile.activeClass) CrimsonPrimary else Color.White,
                            border = androidx.compose.foundation.BorderStroke(1.dp, AmberBorder)
                        ) {
                            Text(
                                text = r,
                                color = if (r == profile.activeClass) Color.White else SlateTextDark,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        // Continue Button
        Button(
            onClick = onNavigateToCpTp,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CrimsonPrimary)
        ) {
            Text("Lanjut ke CP dan TP", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
        }
    }
}
