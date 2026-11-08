package com.tuitionbatch.app.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.tuitionbatch.app.ui.components.AppDrawerContent
import com.tuitionbatch.app.ui.components.DrawerDestination
import com.tuitionbatch.app.ui.theme.ThemeManager
import com.tuitionbatch.app.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    initialBatchId: Long? = null
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    var currentThemeId by remember { mutableIntStateOf(ThemeManager.getSavedThemeId(context)) }
    val currentThemeConfig = remember(currentThemeId) { ThemeManager.getThemeConfig(currentThemeId) }

    var currentDestination by remember { mutableStateOf(DrawerDestination.ROUTINE) }
    var viewingBatchDetailId by remember { mutableStateOf<Long?>(initialBatchId) }

    var showThemeDialog by remember { mutableStateOf(false) }
    var showAddBatchDialog by remember { mutableStateOf(false) }
    var showAddStudentDialog by remember { mutableStateOf(false) }
    var showAlarmInfoDialog by remember { mutableStateOf(false) }

    val activeBatches by viewModel.activeBatches.collectAsState()
    val totalStudents by viewModel.totalActiveStudents.collectAsState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawerContent(
                themeConfig = currentThemeConfig,
                activeBatchCount = activeBatches.size,
                totalStudentCount = totalStudents,
                currentDestination = currentDestination,
                onNavigate = { destination ->
                    coroutineScope.launch { drawerState.close() }
                    when (destination) {
                        DrawerDestination.ROUTINE -> currentDestination = DrawerDestination.ROUTINE
                        DrawerDestination.BATCHES -> currentDestination = DrawerDestination.BATCHES
                        DrawerDestination.STUDENTS -> currentDestination = DrawerDestination.STUDENTS
                        DrawerDestination.FEES -> currentDestination = DrawerDestination.FEES
                        DrawerDestination.ATTENDANCE -> currentDestination = DrawerDestination.ATTENDANCE
                        DrawerDestination.THEME_PICKER -> showThemeDialog = true
                        DrawerDestination.ADD_BATCH -> showAddBatchDialog = true
                        DrawerDestination.ADD_STUDENT -> showAddStudentDialog = true
                        DrawerDestination.ALARM_INFO -> showAlarmInfoDialog = true
                    }
                }
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (viewingBatchDetailId != null) {
                BatchDetailScreen(
                    batchId = viewingBatchDetailId!!,
                    viewModel = viewModel,
                    themeConfig = currentThemeConfig,
                    onBack = { viewingBatchDetailId = null }
                )
            } else {
                when (currentDestination) {
                    DrawerDestination.ROUTINE -> {
                        FullScreenRoutineScreen(
                            viewModel = viewModel,
                            themeConfig = currentThemeConfig,
                            onOpenDrawer = { coroutineScope.launch { drawerState.open() } },
                            onOpenThemePicker = { showThemeDialog = true },
                            onOpenBatchDetail = { batchId -> viewingBatchDetailId = batchId }
                        )
                    }
                    DrawerDestination.BATCHES -> {
                        BatchesScreen(
                            viewModel = viewModel,
                            themeConfig = currentThemeConfig,
                            onBack = { currentDestination = DrawerDestination.ROUTINE },
                            onOpenBatchDetail = { batchId -> viewingBatchDetailId = batchId }
                        )
                    }
                    DrawerDestination.STUDENTS -> {
                        StudentsScreen(
                            viewModel = viewModel,
                            themeConfig = currentThemeConfig,
                            onBack = { currentDestination = DrawerDestination.ROUTINE },
                            onOpenBatchDetail = { batchId -> viewingBatchDetailId = batchId }
                        )
                    }
                    DrawerDestination.FEES -> {
                        FeesScreen(
                            viewModel = viewModel,
                            themeConfig = currentThemeConfig,
                            onBack = { currentDestination = DrawerDestination.ROUTINE }
                        )
                    }
                    DrawerDestination.ATTENDANCE -> {
                        AttendanceScreen(
                            viewModel = viewModel,
                            themeConfig = currentThemeConfig,
                            onBack = { currentDestination = DrawerDestination.ROUTINE }
                        )
                    }
                    else -> {
                        FullScreenRoutineScreen(
                            viewModel = viewModel,
                            themeConfig = currentThemeConfig,
                            onOpenDrawer = { coroutineScope.launch { drawerState.open() } },
                            onOpenThemePicker = { showThemeDialog = true },
                            onOpenBatchDetail = { batchId -> viewingBatchDetailId = batchId }
                        )
                    }
                }
            }
        }
    }

    if (showThemeDialog) {
        ThemeSelectorDialog(
            currentThemeId = currentThemeId,
            onThemeSelected = { newThemeId ->
                currentThemeId = newThemeId
                ThemeManager.saveThemeId(context, newThemeId)
                showThemeDialog = false
            },
            onDismiss = { showThemeDialog = false }
        )
    }

    if (showAddBatchDialog) {
        AddEditBatchDialog(
            onDismiss = { showAddBatchDialog = false },
            onSave = { newBatch ->
                viewModel.insertBatch(newBatch)
                showAddBatchDialog = false
            }
        )
    }

    if (showAddStudentDialog) {
        AddEditStudentDialog(
            batches = activeBatches,
            onDismiss = { showAddStudentDialog = false },
            onSave = { newStudent ->
                viewModel.insertStudent(newStudent)
                showAddStudentDialog = false
            }
        )
    }

    if (showAlarmInfoDialog) {
        AlarmInfoDialog(
            onDismiss = { showAlarmInfoDialog = false }
        )
    }
}
