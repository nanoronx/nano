package com.example.ui

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.FolderShared
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.CurriculumBank
import com.example.ui.components.RombelSelectionDialog
import com.example.ui.screens.AssessmentScreen
import com.example.ui.screens.ClassProfileScreen
import com.example.ui.screens.CpTpScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.DailyClassScreen
import com.example.ui.screens.LessonKitScreen
import com.example.ui.screens.PreparationScreen
import com.example.ui.screens.ReportCardScreen
import com.example.ui.screens.SchoolTemplateScreen
import com.example.ui.screens.SupervisionPackageScreen
import com.example.ui.theme.AmberBorder
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CrimsonDark
import com.example.ui.theme.CrimsonLight
import com.example.ui.theme.CrimsonPrimary
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.GoldSecondary
import com.example.ui.theme.SlateTextDark
import com.example.ui.theme.SlateTextMedium
import com.example.viewmodel.AdminGuruViewModel
import com.example.viewmodel.AppScreen

data class NavItem(
    val screen: AppScreen,
    val label: String,
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminGuruApp(
    viewModel: AdminGuruViewModel = viewModel()
) {
    val context = LocalContext.current
    val currentScreen by viewModel.currentScreen.collectAsState()
    val profile by viewModel.classProfile.collectAsState()
    val cpText by viewModel.cpText.collectAsState()
    val tps by viewModel.tps.collectAsState()
    val selectedTpId by viewModel.selectedTpId.collectAsState()
    val students by viewModel.students.collectAsState()
    val journals by viewModel.journals.collectAsState()
    val perangkatType by viewModel.perangkatType.collectAsState()
    val dailyTab by viewModel.dailyTab.collectAsState()
    val examType by viewModel.examType.collectAsState()
    val examDocTab by viewModel.examDocTab.collectAsState()
    val showRombelModal by viewModel.showRombelModal.collectAsState()
    val activeRombelTab by viewModel.activeRombelTab.collectAsState()
    val toastMessage by viewModel.toastMessage.collectAsState()

    // Show native Toast when toastMessage changes
    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearToast()
        }
    }

    // Handle back button to return to dashboard
    BackHandler(enabled = currentScreen != AppScreen.Dashboard) {
        viewModel.navigateTo(AppScreen.Dashboard)
    }

    // Quick Selectors Dropdown state in TopBar
    var rombelMenuExpanded by remember { mutableStateOf(false) }
    var subjectMenuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = GoldLight,
                                modifier = Modifier.size(34.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text("AG", fontWeight = FontWeight.Black, fontSize = 14.sp, color = CrimsonDark)
                                }
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Column {
                                Text(
                                    text = "Administrasi Guru",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                )
                                Text(
                                    text = "Kurikulum Merdeka • 1 Semester",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = GoldLight
                                )
                            }
                        }

                        // Quick Switchers in TopBar
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            // Quick Rombel Selector Button
                            Box {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color.White.copy(alpha = 0.2f),
                                    modifier = Modifier.clickable { rombelMenuExpanded = true }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(profile.activeClass, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                        Text(" ▾", fontSize = 10.sp, color = GoldLight)
                                    }
                                }
                                DropdownMenu(
                                    expanded = rombelMenuExpanded,
                                    onDismissRequest = { rombelMenuExpanded = false }
                                ) {
                                    profile.parallelClasses.forEach { r ->
                                        DropdownMenuItem(
                                            text = { Text(r, fontWeight = if (r == profile.activeClass) FontWeight.Bold else FontWeight.Normal) },
                                            onClick = {
                                                viewModel.switchActiveRombel(r)
                                                rombelMenuExpanded = false
                                            }
                                        )
                                    }
                                }
                            }

                            // Quick Subject Selector Button
                            Box {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color.White.copy(alpha = 0.2f),
                                    modifier = Modifier.clickable { subjectMenuExpanded = true }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(profile.subject.take(9), fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                        Text(" ▾", fontSize = 10.sp, color = GoldLight)
                                    }
                                }
                                DropdownMenu(
                                    expanded = subjectMenuExpanded,
                                    onDismissRequest = { subjectMenuExpanded = false }
                                ) {
                                    CurriculumBank.SUBJECTS.keys.forEach { s ->
                                        DropdownMenuItem(
                                            text = { Text(s, fontWeight = if (s == profile.subject) FontWeight.Bold else FontWeight.Normal) },
                                            onClick = {
                                                viewModel.switchSubject(s)
                                                subjectMenuExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                },
                navigationIcon = {
                    if (currentScreen != AppScreen.Dashboard) {
                        IconButton(onClick = { viewModel.navigateTo(AppScreen.Dashboard) }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali ke Beranda", tint = Color.White)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CrimsonPrimary,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            val navItems = listOf(
                NavItem(AppScreen.Dashboard, "Beranda", Icons.Default.Home),
                NavItem(AppScreen.ClassProfileScreen, "Kelas", Icons.Default.School),
                NavItem(AppScreen.CpTp, "CP & TP", Icons.Default.MenuBook),
                NavItem(AppScreen.Preparation, "Promes", Icons.Default.CalendarMonth),
                NavItem(AppScreen.LessonKit, "Modul", Icons.Default.Description),
                NavItem(AppScreen.DailyClass, "Harian", Icons.Default.PhoneAndroid),
                NavItem(AppScreen.Assessment, "Asesmen", Icons.Default.Assignment),
                NavItem(AppScreen.SupervisionPackage, "Supervisi", Icons.Default.FolderShared)
            )

            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                navItems.forEach { item ->
                    val selected = currentScreen == item.screen
                    NavigationBarItem(
                        selected = selected,
                        onClick = { viewModel.navigateTo(item.screen) },
                        icon = {
                            Icon(
                                item.icon,
                                contentDescription = item.label,
                                tint = if (selected) CrimsonPrimary else SlateTextMedium
                            )
                        },
                        label = {
                            Text(
                                text = item.label,
                                fontSize = 10.sp,
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                                color = if (selected) CrimsonPrimary else SlateTextMedium
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = CrimsonPrimary.copy(alpha = 0.12f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                AppScreen.Dashboard -> DashboardScreen(
                    profile = profile,
                    onNavigate = { viewModel.navigateTo(it) },
                    onOpenRombelModal = { viewModel.setShowRombelModal(true) },
                    onSelectSubTab = { category, value ->
                        when (category) {
                            "perangkat" -> viewModel.setPerangkatType(value)
                            "asesmen" -> viewModel.setExamType(value)
                            "daily" -> viewModel.setDailyTab(value)
                        }
                    }
                )

                AppScreen.ClassProfileScreen -> ClassProfileScreen(
                    profile = profile,
                    onUpdateProfile = { viewModel.updateClassProfile(it) },
                    onSwitchSubject = { viewModel.switchSubject(it) },
                    onSwitchActiveRombel = { viewModel.switchActiveRombel(it) },
                    onOpenRombelModal = { viewModel.setShowRombelModal(true) },
                    onNavigateToCpTp = { viewModel.navigateTo(AppScreen.CpTp) }
                )

                AppScreen.CpTp -> CpTpScreen(
                    profile = profile,
                    cpText = cpText,
                    tps = tps,
                    onUpdateCpText = { viewModel.updateCpText(it) },
                    onUpdateTpTitle = { id, title -> viewModel.updateTpTitle(id, title) },
                    onAddNewTp = { viewModel.addNewTp() },
                    onRemoveTp = { viewModel.removeTp(it) },
                    onNavigateToPreparation = { viewModel.navigateTo(AppScreen.Preparation) }
                )

                AppScreen.Preparation -> PreparationScreen(
                    profile = profile,
                    cpText = cpText,
                    tps = tps,
                    onTogglePromesWeek = { id, w -> viewModel.togglePromesWeek(id, w) }
                )

                AppScreen.LessonKit -> LessonKitScreen(
                    profile = profile,
                    tps = tps,
                    selectedTpId = selectedTpId,
                    perangkatType = perangkatType,
                    onSelectPerangkatType = { viewModel.setPerangkatType(it) },
                    onSelectTpId = { viewModel.setSelectedTpId(it) }
                )

                AppScreen.DailyClass -> DailyClassScreen(
                    profile = profile,
                    tps = tps,
                    students = students,
                    journals = journals,
                    dailyTab = dailyTab,
                    onSelectDailyTab = { viewModel.setDailyTab(it) },
                    onUpdateAttendance = { id, status -> viewModel.updateStudentAttendance(id, status) },
                    onUpdateScore = { id, field, v -> viewModel.updateStudentScore(id, field, v) },
                    onAddStudent = { viewModel.addStudent(it) },
                    onAddJournal = { date, rombel, materi, act, pCount ->
                        viewModel.addJournal(date, rombel, materi, act, pCount)
                    }
                )

                AppScreen.Assessment -> AssessmentScreen(
                    profile = profile,
                    tps = tps,
                    examType = examType,
                    examDocTab = examDocTab,
                    onSelectExamType = { viewModel.setExamType(it) },
                    onSelectExamDocTab = { viewModel.setExamDocTab(it) }
                )

                AppScreen.ReportCard -> ReportCardScreen(
                    profile = profile,
                    students = students,
                    tps = tps
                )

                AppScreen.SchoolTemplate -> SchoolTemplateScreen(
                    profile = profile,
                    onUpdateProfile = { viewModel.updateClassProfile(it) }
                )

                AppScreen.SupervisionPackage -> SupervisionPackageScreen(
                    profile = profile,
                    cpText = cpText,
                    tps = tps,
                    students = students
                )
            }

            // Multi-Rombel Modal Dialog
            if (showRombelModal) {
                RombelSelectionDialog(
                    profile = profile,
                    activeTab = activeRombelTab,
                    onTabSelected = { viewModel.setActiveRombelTab(it) },
                    onToggleRombel = { viewModel.toggleParallelRombel(it) },
                    onToggleGradeGroup = { viewModel.toggleGradeGroupRombels(it) },
                    onAddCustomRombel = { viewModel.addCustomRombel(it) },
                    onDismiss = { viewModel.setShowRombelModal(false) }
                )
            }
        }
    }
}
