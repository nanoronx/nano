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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ClassProfile
import com.example.ui.components.SchoolHeaderView
import com.example.ui.theme.AmberBorder
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CrimsonPrimary
import com.example.ui.theme.SlateTextDark
import com.example.ui.theme.SlateTextMedium

@Composable
fun SchoolTemplateScreen(
    profile: ClassProfile,
    onUpdateProfile: ((ClassProfile) -> ClassProfile) -> Unit,
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
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = CrimsonPrimary,
                    modifier = Modifier.size(44.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Settings, contentDescription = null, tint = Color.White)
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Format & Kop Sekolah",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Black,
                        color = SlateTextDark
                    )
                    Text(
                        text = "Sesuaikan kop surat resmi sekolah untuk semua cetakan dokumen.",
                        fontSize = 11.sp,
                        color = SlateTextMedium
                    )
                }
            }
        }

        // Toggle Kop Surat
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
                Column {
                    Text("Gunakan Kop Surat Resmi", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = SlateTextDark)
                    Text("Tampilkan kop di setiap ekspor Word & dokumen", fontSize = 11.sp, color = SlateTextMedium)
                }
                Switch(
                    checked = profile.useSchoolHeader,
                    onCheckedChange = { onUpdateProfile { p -> p.copy(useSchoolHeader = it) } },
                    colors = SwitchDefaults.colors(checkedThumbColor = CrimsonPrimary)
                )
            }
        }

        // Live Preview Kop Surat
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, CrimsonPrimary)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Pratinjau Kop Surat Resmi Sekolah",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = CrimsonPrimary,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                SchoolHeaderView(profile = profile)

                Text(
                    text = "MODUL AJAR & PERANGKAT ADMINISTRASI • ${profile.activeClass}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = CrimsonPrimary,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }

        // Input Fields
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, AmberBorder)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = profile.pemda,
                    onValueChange = { onUpdateProfile { p -> p.copy(pemda = it) } },
                    label = { Text("Baris 1: Pemerintah Daerah (Pemda)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = profile.dinas,
                    onValueChange = { onUpdateProfile { p -> p.copy(dinas = it) } },
                    label = { Text("Baris 2: Dinas Pendidikan dan Kebudayaan") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = profile.schoolName,
                    onValueChange = { onUpdateProfile { p -> p.copy(schoolName = it) } },
                    label = { Text("Baris 3: Nama Satuan Pendidikan") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = profile.address,
                    onValueChange = { onUpdateProfile { p -> p.copy(address = it) } },
                    label = { Text("Baris 4: Alamat Lengkap & Kontak Sekolah") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }
    }
}
