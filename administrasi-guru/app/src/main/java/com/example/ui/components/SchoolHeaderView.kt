package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ClassProfile
import com.example.ui.theme.CrimsonPrimary
import com.example.ui.theme.SlateTextDark
import com.example.ui.theme.SlateTextMedium

@Composable
fun SchoolHeaderView(
    profile: ClassProfile,
    modifier: Modifier = Modifier
) {
    if (!profile.useSchoolHeader) return

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = profile.pemda.uppercase(),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = SlateTextDark,
            textAlign = TextAlign.Center
        )
        Text(
            text = profile.dinas.uppercase(),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = SlateTextDark,
            textAlign = TextAlign.Center
        )
        Text(
            text = profile.schoolName.uppercase(),
            fontSize = 15.sp,
            fontWeight = FontWeight.Black,
            color = CrimsonPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 2.dp)
        )
        Text(
            text = profile.address,
            fontSize = 10.sp,
            color = SlateTextMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Double Line Divider (Traditional Indonesian Official Kop Surat style)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.5.dp)
                .background(Color.Black)
        )
        Spacer(modifier = Modifier.height(1.5.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.8.dp)
                .background(Color.Black)
        )
    }
}
