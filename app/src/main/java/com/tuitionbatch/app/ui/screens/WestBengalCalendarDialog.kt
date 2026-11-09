package com.tuitionbatch.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.tuitionbatch.app.ui.theme.AppThemeConfig
import com.tuitionbatch.app.util.DateHelper
import com.tuitionbatch.app.util.HolidayCategory
import com.tuitionbatch.app.util.HolidayInfo
import com.tuitionbatch.app.util.WestBengalCalendarHelper
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

private enum class CalendarViewTab {
    GRID,
    MONTH_HOLIDAYS,
    YEAR_HOLIDAYS
}

@Composable
fun WestBengalCalendarDialog(
    themeConfig: AppThemeConfig,
    onDismiss: () -> Unit
) {
    var currentYearMonth by remember { mutableStateOf(YearMonth.now()) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var currentTab by remember { mutableStateOf(CalendarViewTab.GRID) }

    val daysData = remember(currentYearMonth) {
        WestBengalCalendarHelper.getCalendarDaysForMonth(currentYearMonth)
    }

    val monthHolidays = remember(currentYearMonth) {
        WestBengalCalendarHelper.getHolidaysForMonth(currentYearMonth)
    }

    val annualHolidays = remember(currentYearMonth.year) {
        WestBengalCalendarHelper.getAnnualGovtHolidays(currentYearMonth.year)
    }

    val selectedHoliday = remember(selectedDate) {
        WestBengalCalendarHelper.getHolidayForDate(selectedDate)
    }

    val selectedBengaliDate = remember(selectedDate) {
        DateHelper.getWestBengalBengaliDate(selectedDate)
    }

    val selectedHijriDate = remember(selectedDate) {
        DateHelper.getArabicHijriDate(selectedDate)
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 16.dp),
            shape = RoundedCornerShape(24.dp),
            color = themeConfig.cardBackground.copy(alpha = 0.98f),
            tonalElevation = 8.dp,
            border = androidx.compose.foundation.BorderStroke(
                width = 1.2.dp,
                brush = Brush.verticalGradient(
                    listOf(
                        themeConfig.primary.copy(alpha = 0.8f),
                        themeConfig.cardBorder,
                        themeConfig.accent.copy(alpha = 0.5f)
                    )
                )
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(14.dp)
            ) {
                // 1. Top Header Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(themeConfig.primary.copy(alpha = 0.15f))
                                .border(1.dp, themeConfig.primary.copy(alpha = 0.4f), RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.CalendarMonth,
                                contentDescription = "Calendar",
                                tint = themeConfig.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Column {
                            Text(
                                text = "পশ্চিমবঙ্গ চিরস্থায়ী পঞ্জিকা",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 17.sp
                                ),
                                color = themeConfig.textPrimary
                            )
                            Text(
                                text = "ইংরেজি • বঙ্গাব্দ • হিজরি ও সরকারি ছুটি",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                ),
                                color = themeConfig.textSecondary
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(themeConfig.textSecondary.copy(alpha = 0.12f))
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Close",
                            tint = themeConfig.textPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // 2. Month Navigator Bar
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = themeConfig.primary.copy(alpha = 0.08f),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        themeConfig.primary.copy(alpha = 0.25f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = { currentYearMonth = currentYearMonth.minusMonths(1) },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                Icons.Default.ChevronLeft,
                                contentDescription = "Previous Month",
                                tint = themeConfig.primary
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            val engMonthBengali = DateHelper.englishMonthNamesInBengali[currentYearMonth.monthValue - 1]
                            val engYearBengali = DateHelper.toBengaliDigits(currentYearMonth.year)

                            val midMonthDate = currentYearMonth.atDay(15)
                            val bengaliMid = DateHelper.getWestBengalBengaliDate(midMonthDate)
                            val hijriMid = DateHelper.getArabicHijriDate(midMonthDate)

                            Text(
                                text = "$engMonthBengali $engYearBengali",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    fontSize = 16.sp
                                ),
                                color = themeConfig.primary
                            )

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = "🌾 ${bengaliMid.monthName} ${DateHelper.toBengaliDigits(bengaliMid.year)} • 🌙 ${hijriMid.monthName} ${DateHelper.toBengaliDigits(hijriMid.year)}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                ),
                                color = themeConfig.accent
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { currentYearMonth = currentYearMonth.plusMonths(1) },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    Icons.Default.ChevronRight,
                                    contentDescription = "Next Month",
                                    tint = themeConfig.primary
                                )
                            }

                            // Quick Jump to Today
                            IconButton(
                                onClick = {
                                    currentYearMonth = YearMonth.now()
                                    selectedDate = LocalDate.now()
                                },
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(themeConfig.primary.copy(alpha = 0.15f))
                            ) {
                                Icon(
                                    Icons.Default.Today,
                                    contentDescription = "Today",
                                    tint = themeConfig.primary,
                                    modifier = Modifier.size(17.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 3. Tab Switcher
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val tabItems = listOf(
                        CalendarViewTab.GRID to "📅 ক্যালেন্ডার",
                        CalendarViewTab.MONTH_HOLIDAYS to "🎉 এই মাসের ছুটি (${DateHelper.toBengaliDigits(monthHolidays.size)})",
                        CalendarViewTab.YEAR_HOLIDAYS to "🏛️ সরকারি ছুটি (${DateHelper.toBengaliDigits(annualHolidays.size)})"
                    )

                    tabItems.forEach { (tab, title) ->
                        val isSelected = currentTab == tab
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    if (isSelected) themeConfig.primary
                                    else themeConfig.textSecondary.copy(alpha = 0.08f)
                                )
                                .clickable { currentTab = tab }
                                .padding(vertical = 7.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontSize = 11.5.sp,
                                    fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium
                                ),
                                color = if (isSelected) Color.White else themeConfig.textSecondary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // 4. Content Area Based on Tab
                when (currentTab) {
                    CalendarViewTab.GRID -> {
                        CalendarGridContent(
                            yearMonth = currentYearMonth,
                            daysData = daysData,
                            selectedDate = selectedDate,
                            themeConfig = themeConfig,
                            onSelectDate = { selectedDate = it },
                            selectedHoliday = selectedHoliday,
                            selectedBengaliDate = selectedBengaliDate,
                            selectedHijriDate = selectedHijriDate
                        )
                    }
                    CalendarViewTab.MONTH_HOLIDAYS -> {
                        HolidaysListContent(
                            holidays = monthHolidays,
                            emptyMessage = "এই মাসে কোনো সরকারি বা বিশেষ ছুটি নেই।",
                            themeConfig = themeConfig,
                            onSelectDate = {
                                selectedDate = it
                                currentTab = CalendarViewTab.GRID
                            }
                        )
                    }
                    CalendarViewTab.YEAR_HOLIDAYS -> {
                        HolidaysListContent(
                            holidays = annualHolidays,
                            emptyMessage = "বাৎসরিক সরকারি ছুটি পাওয়া যায়নি।",
                            themeConfig = themeConfig,
                            onSelectDate = {
                                selectedDate = it
                                currentYearMonth = YearMonth.of(it.year, it.month)
                                currentTab = CalendarViewTab.GRID
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CalendarGridContent(
    yearMonth: YearMonth,
    daysData: List<com.tuitionbatch.app.util.CalendarDayData>,
    selectedDate: LocalDate,
    themeConfig: AppThemeConfig,
    onSelectDate: (LocalDate) -> Unit,
    selectedHoliday: HolidayInfo?,
    selectedBengaliDate: com.tuitionbatch.app.util.BengaliDate,
    selectedHijriDate: com.tuitionbatch.app.util.HijriDate
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Weekday Bar: শনি, রবি, সোম, মঙ্গল, বুধ, বৃহ, শুক্র
        val weekHeaders = listOf(
            DayOfWeek.SATURDAY to "শনি",
            DayOfWeek.SUNDAY to "রবি",
            DayOfWeek.MONDAY to "সোম",
            DayOfWeek.TUESDAY to "মঙ্গল",
            DayOfWeek.WEDNESDAY to "বুধ",
            DayOfWeek.THURSDAY to "বৃহ",
            DayOfWeek.FRIDAY to "শুক্র"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            weekHeaders.forEach { (dow, label) ->
                val isSun = dow == DayOfWeek.SUNDAY
                val isFri = dow == DayOfWeek.FRIDAY
                Text(
                    text = label,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black
                    ),
                    color = when {
                        isSun -> Color(0xFFEF4444) // Red for Sunday holiday
                        isFri -> Color(0xFF10B981) // Green for Friday
                        else -> themeConfig.textSecondary
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Calendar Grid Matrix
        val firstDayOfMonth = yearMonth.atDay(1)
        // Map DayOfWeek so that Saturday = 0, Sunday = 1, Monday = 2 ... Friday = 6
        val firstDow = firstDayOfMonth.dayOfWeek
        val leadingEmptyCells = when (firstDow) {
            DayOfWeek.SATURDAY -> 0
            DayOfWeek.SUNDAY -> 1
            DayOfWeek.MONDAY -> 2
            DayOfWeek.TUESDAY -> 3
            DayOfWeek.WEDNESDAY -> 4
            DayOfWeek.THURSDAY -> 5
            DayOfWeek.FRIDAY -> 6
        }

        val totalCells = leadingEmptyCells + daysData.size
        val totalRows = (totalCells + 6) / 7

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            for (rowIndex in 0 until totalRows) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    for (colIndex in 0 until 7) {
                        val cellIndex = rowIndex * 7 + colIndex
                        val dayIndex = cellIndex - leadingEmptyCells

                        if (dayIndex in daysData.indices) {
                            val dayData = daysData[dayIndex]
                            val isSelected = dayData.date == selectedDate
                            val isPublicHoliday = dayData.holiday?.isPublicHoliday == true
                            val hasObservance = dayData.holiday != null && !isPublicHoliday

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(
                                        when {
                                            isSelected -> themeConfig.primary.copy(alpha = 0.28f)
                                            dayData.isToday -> themeConfig.accent.copy(alpha = 0.15f)
                                            isPublicHoliday -> Color(0xFFEF4444).copy(alpha = 0.12f)
                                            else -> themeConfig.textSecondary.copy(alpha = 0.05f)
                                        }
                                    )
                                    .border(
                                        width = when {
                                            isSelected -> 1.8.dp
                                            dayData.isToday -> 1.4.dp
                                            isPublicHoliday -> 1.dp
                                            else -> 0.5.dp
                                        },
                                        color = when {
                                            isSelected -> themeConfig.primary
                                            dayData.isToday -> themeConfig.accent
                                            isPublicHoliday -> Color(0xFFEF4444).copy(alpha = 0.6f)
                                            else -> themeConfig.cardBorder.copy(alpha = 0.3f)
                                        },
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .clickable { onSelectDate(dayData.date) },
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    // Gregorian Day Number (বাংলা সংখ্যা)
                                    Text(
                                        text = DateHelper.toBengaliDigits(dayData.englishDay),
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontSize = 13.5.sp,
                                            fontWeight = if (isSelected || dayData.isToday || isPublicHoliday) FontWeight.Black else FontWeight.Bold
                                        ),
                                        color = when {
                                            isSelected -> themeConfig.primary
                                            dayData.isSunday || isPublicHoliday -> Color(0xFFEF4444)
                                            dayData.isFriday -> Color(0xFF10B981)
                                            else -> themeConfig.textPrimary
                                        }
                                    )

                                    // Bengali day & Hijri day in small footer
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                                    ) {
                                        Text(
                                            text = DateHelper.toBengaliDigits(dayData.bengaliDate.day),
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold
                                            ),
                                            color = themeConfig.bengaliDateColor
                                        )

                                        if (isPublicHoliday) {
                                            Box(
                                                modifier = Modifier
                                                    .size(4.5.dp)
                                                    .clip(CircleShape)
                                                    .background(Color(0xFFEF4444))
                                            )
                                        } else if (hasObservance) {
                                            Box(
                                                modifier = Modifier
                                                    .size(4.dp)
                                                    .clip(CircleShape)
                                                    .background(themeConfig.accent)
                                            )
                                        }
                                    }
                                }
                            }
                        } else {
                            // Blank cell for alignment
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Selected Date Comprehensive Details Box
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(18.dp),
            color = themeConfig.primary.copy(alpha = 0.07f),
            border = androidx.compose.foundation.BorderStroke(
                1.dp,
                if (selectedHoliday?.isPublicHoliday == true) Color(0xFFEF4444).copy(alpha = 0.45f)
                else themeConfig.primary.copy(alpha = 0.3f)
            )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                item {
                    // Date Triple Trio Banner
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            // ইংরেজি তারিখ
                            Text(
                                text = DateHelper.getEnglishDate(selectedDate),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 15.sp
                                ),
                                color = themeConfig.englishDateColor
                            )

                            Spacer(modifier = Modifier.height(2.dp))

                            // বাংলা ও আরবি তারিখ
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "🌾 ${selectedBengaliDate.format()}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 11.5.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = themeConfig.bengaliDateColor
                                )

                                Text(
                                    text = "🌙 ${selectedHijriDate.format()}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 11.5.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = themeConfig.arabicDateColor
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = themeConfig.cardBorder.copy(alpha = 0.3f), thickness = 0.8.dp)
                    Spacer(modifier = Modifier.height(8.dp))

                    // Holiday / Working Day Details
                    if (selectedHoliday != null) {
                        val isPub = selectedHoliday.isPublicHoliday
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isPub) Color(0xFFEF4444).copy(alpha = 0.15f) else themeConfig.accent.copy(alpha = 0.12f),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isPub) Color(0xFFEF4444).copy(alpha = 0.5f) else themeConfig.accent.copy(alpha = 0.4f)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = selectedHoliday.name,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Black,
                                            fontSize = 15.5.sp
                                        ),
                                        color = if (isPub) Color(0xFFEF4444) else themeConfig.accent
                                    )

                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = if (isPub) Color(0xFFEF4444) else themeConfig.accent,
                                        contentColor = Color.White
                                    ) {
                                        Text(
                                            text = if (isPub) "পশ্চিমবঙ্গে সরকারি ছুটি" else "উৎসব / দিবস",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold
                                            ),
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                if (selectedHoliday.englishName.isNotBlank()) {
                                    Text(
                                        text = selectedHoliday.englishName,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Medium
                                        ),
                                        color = themeConfig.textSecondary
                                    )
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = selectedHoliday.description,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontSize = 12.sp,
                                        lineHeight = 17.sp
                                    ),
                                    color = themeConfig.textPrimary
                                )
                            }
                        }
                    } else {
                        // Regular working day info
                        val isSunday = selectedDate.dayOfWeek == DayOfWeek.SUNDAY
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                if (isSunday) Icons.Default.EventBusy else Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = if (isSunday) Color(0xFFEF4444) else Color(0xFF10B981),
                                modifier = Modifier.size(20.dp)
                            )
                            Column {
                                Text(
                                    text = if (isSunday) "রবিবার (সাপ্তাহিক ছুটি)" else "নিয়মিত কার্যদিবস",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.5.sp
                                    ),
                                    color = themeConfig.textPrimary
                                )
                                Text(
                                    text = if (isSunday) "সাপ্তাহিক ছুটি দিন। ব্যাচের সময়সূচি অনুসারে ক্লাস পরিচালিত হবে।"
                                    else "আজ কোনো সরকারি ছুটি নেই। নিয়মিত টিউশন ব্যাচ ও পড়াশোনা চলবে।",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 11.sp
                                    ),
                                    color = themeConfig.textSecondary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HolidaysListContent(
    holidays: List<Pair<LocalDate, HolidayInfo>>,
    emptyMessage: String,
    themeConfig: AppThemeConfig,
    onSelectDate: (LocalDate) -> Unit
) {
    if (holidays.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = emptyMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = themeConfig.textSecondary
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(holidays) { (date, holiday) ->
                val isPub = holiday.isPublicHoliday
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = if (isPub) Color(0xFFEF4444).copy(alpha = 0.08f) else themeConfig.cardBackground,
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isPub) Color(0xFFEF4444).copy(alpha = 0.35f) else themeConfig.cardBorder
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectDate(date) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Left Date Badge
                        Column(
                            modifier = Modifier
                                .width(54.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    if (isPub) Color(0xFFEF4444).copy(alpha = 0.18f)
                                    else themeConfig.primary.copy(alpha = 0.15f)
                                )
                                .padding(vertical = 6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = DateHelper.toBengaliDigits(date.dayOfMonth),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    fontSize = 17.sp
                                ),
                                color = if (isPub) Color(0xFFEF4444) else themeConfig.primary
                            )
                            val bengaliMonth = DateHelper.englishMonthNamesInBengali[date.monthValue - 1]
                            Text(
                                text = bengaliMonth.take(4),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                color = themeConfig.textSecondary
                            )
                        }

                        // Center Holiday Details
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = holiday.name,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 14.sp
                                    ),
                                    color = if (isPub) Color(0xFFEF4444) else themeConfig.primary,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )

                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = if (isPub) Color(0xFFEF4444) else themeConfig.accent,
                                    contentColor = Color.White
                                ) {
                                    Text(
                                        text = if (isPub) "সরকারি ছুটি" else "উৎসব",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                    )
                                }
                            }

                            val bDate = DateHelper.getWestBengalBengaliDate(date)
                            val hDate = DateHelper.getArabicHijriDate(date)
                            Text(
                                text = "🌾 ${bDate.formatShort()} • 🌙 ${hDate.formatShort()}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 10.5.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                color = themeConfig.bengaliDateColor
                            )

                            Text(
                                text = holiday.description,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 11.sp,
                                    lineHeight = 15.sp
                                ),
                                color = themeConfig.textSecondary,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Icon(
                            Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = themeConfig.textSecondary.copy(alpha = 0.5f),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}
