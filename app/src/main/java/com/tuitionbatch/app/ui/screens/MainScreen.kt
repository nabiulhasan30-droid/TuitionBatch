package com.tuitionbatch.app.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.tuitionbatch.app.ui.viewmodel.MainViewModel

enum class NavigationItem(val title: String, val icon: ImageVector) {
    DASHBOARD("হোম", Icons.Default.Dashboard),
    BATCHES("ব্যাচ", Icons.Default.Groups),
    ROUTINE("রুটিন", Icons.Default.CalendarMonth),
    STUDENTS("শিক্ষার্থী", Icons.Default.School),
    FEES("ফি / বেতন", Icons.Default.AccountBalanceWallet)
}

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    initialBatchId: Long? = null
) {
    var currentItem by remember { mutableStateOf(NavigationItem.DASHBOARD) }
    var viewingBatchDetailId by remember { mutableStateOf<Long?>(initialBatchId) }

    if (viewingBatchDetailId != null) {
        BatchDetailScreen(
            batchId = viewingBatchDetailId!!,
            viewModel = viewModel,
            onBack = { viewingBatchDetailId = null }
        )
    } else {
        Scaffold(
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp
                ) {
                    NavigationItem.values().forEach { item ->
                        val isSelected = currentItem == item
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { currentItem = item },
                            icon = {
                                Icon(item.icon, contentDescription = item.title)
                            },
                            label = { Text(item.title) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        )
                    }
                }
            }
        ) { paddingValues ->
            androidx.compose.foundation.layout.Box(modifier = Modifier.padding(paddingValues)) {
                when (currentItem) {
                    NavigationItem.DASHBOARD -> {
                        DashboardScreen(
                            viewModel = viewModel,
                            onNavigateToBatches = { currentItem = NavigationItem.BATCHES },
                            onNavigateToStudents = { currentItem = NavigationItem.STUDENTS },
                            onNavigateToFees = { currentItem = NavigationItem.FEES },
                            onOpenBatchDetail = { batchId -> viewingBatchDetailId = batchId }
                        )
                    }
                    NavigationItem.BATCHES -> {
                        BatchesScreen(
                            viewModel = viewModel,
                            onOpenBatchDetail = { batchId -> viewingBatchDetailId = batchId }
                        )
                    }
                    NavigationItem.ROUTINE -> {
                        RoutineScreen(
                            viewModel = viewModel,
                            onOpenBatchDetail = { batchId -> viewingBatchDetailId = batchId }
                        )
                    }
                    NavigationItem.STUDENTS -> {
                        StudentsScreen(
                            viewModel = viewModel,
                            onOpenBatchDetail = { batchId -> viewingBatchDetailId = batchId }
                        )
                    }
                    NavigationItem.FEES -> {
                        FeesScreen(
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}
