package com.tuitionbatch.app.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.tuitionbatch.app.R
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
    val context = LocalContext.current
    var currentYearMonth by remember { mutableStateOf(YearMonth.now()) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var currentTab by remember { mutableStateOf(CalendarViewTab.GRID) }
    var dialogDetailHoliday by remember { mutableStateOf<Pair<LocalDate, HolidayInfo>?>(null) }

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
                .padding(horizontal = 6.dp, vertical = 12.dp),
            shape = RoundedCornerShape(24.dp),
            color = themeConfig.cardBackground.copy(alpha = 0.95f),
            tonalElevation = 10.dp,
            border = androidx.compose.foundation.BorderStroke(
                width = 1.2.dp,
                brush = Brush.verticalGradient(
                    listOf(
                        themeConfig.primary.copy(alpha = 0.85f),
                        themeConfig.cardBorder,
                        themeConfig.accent.copy(alpha = 0.65f)
                    )
                )
            )
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // 🌌 Breathtaking Artistic Background Picture
                Image(
                    painter = painterResource(id = R.drawable.bg_calendar_artistic),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Frosted Glass Aesthetic Tint Overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            themeConfig.cardBackground.copy(
                                alpha = if (themeConfig.isDark) 0.88f else 0.92f
                            )
                        )
                )

                // Main Calendar Layout
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
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
                                    .size(42.dp)
                                    .clip(RoundedCornerShape(13.dp))
                                    .background(themeConfig.primary.copy(alpha = 0.18f))
                                    .border(1.dp, themeConfig.primary.copy(alpha = 0.5f), RoundedCornerShape(13.dp)),
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
                                    text = "ইংরেজি • বঙ্গাব্দ • হিজরি ও পূর্ণাঙ্গ উৎসব বিবরণ",
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
                                .background(themeConfig.textSecondary.copy(alpha = 0.15f))
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Close",
                                tint = themeConfig.textPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // 2. Month Navigator Bar (English Header with Bengali & Hijri Details)
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = themeConfig.primary.copy(alpha = 0.10f),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            themeConfig.primary.copy(alpha = 0.35f)
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
                                val engHeader = DateHelper.getEnglishMonthHeader(currentYearMonth.monthValue, currentYearMonth.year)
                                val bengaliMonthEng = DateHelper.englishMonthNamesInBengali[currentYearMonth.monthValue - 1]
                                val midMonthDate = currentYearMonth.atDay(15)
                                val bengaliMid = DateHelper.getWestBengalBengaliDate(midMonthDate)
                                val hijriMid = DateHelper.getArabicHijriDate(midMonthDate)

                                Text(
                                    text = "$engHeader • $bengaliMonthEng",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Black,
                                        fontSize = 16.sp,
                                        letterSpacing = 0.5.sp
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

                                // Jump to Today
                                IconButton(
                                    onClick = {
                                        currentYearMonth = YearMonth.now()
                                        selectedDate = LocalDate.now()
                                    },
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(themeConfig.primary.copy(alpha = 0.18f))
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
                            CalendarViewTab.MONTH_HOLIDAYS to "🎉 এই মাসের ছুটি (${monthHolidays.size})",
                            CalendarViewTab.YEAR_HOLIDAYS to "🏛️ সরকারি ছুটি (${annualHolidays.size})"
                        )

                        tabItems.forEach { (tab, title) ->
                            val isSelected = currentTab == tab
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(
                                        if (isSelected) themeConfig.primary
                                        else themeConfig.textSecondary.copy(alpha = 0.10f)
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

                    Spacer(modifier = Modifier.height(8.dp))

                    // 4. Content Area Based on Selected Tab
                    when (currentTab) {
                        CalendarViewTab.GRID -> {
                            CalendarGridContent(
                                yearMonth = currentYearMonth,
                                daysData = daysData,
                                selectedDate = selectedDate,
                                themeConfig = themeConfig,
                                onSelectDate = { date, holiday ->
                                    selectedDate = date
                                    if (holiday != null) {
                                        dialogDetailHoliday = date to holiday
                                    }
                                },
                                onOpenHolidayDetails = { date, holiday ->
                                    dialogDetailHoliday = date to holiday
                                },
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
                                onSelectHoliday = { date, holiday ->
                                    dialogDetailHoliday = date to holiday
                                }
                            )
                        }
                        CalendarViewTab.YEAR_HOLIDAYS -> {
                            HolidaysListContent(
                                holidays = annualHolidays,
                                emptyMessage = "বাৎসরিক সরকারি ছুটি পাওয়া যায়নি।",
                                themeConfig = themeConfig,
                                onSelectHoliday = { date, holiday ->
                                    dialogDetailHoliday = date to holiday
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    // 🌟 Rich Holiday & Festival Details Dialog / Bottom Modal
    dialogDetailHoliday?.let { (date, holiday) ->
        HolidayDetailDialog(
            holiday = holiday,
            date = date,
            themeConfig = themeConfig,
            onDismiss = { dialogDetailHoliday = null }
        )
    }
}

@Composable
private fun CalendarGridContent(
    yearMonth: YearMonth,
    daysData: List<com.tuitionbatch.app.util.CalendarDayData>,
    selectedDate: LocalDate,
    themeConfig: AppThemeConfig,
    onSelectDate: (LocalDate, HolidayInfo?) -> Unit,
    onOpenHolidayDetails: (LocalDate, HolidayInfo) -> Unit,
    selectedHoliday: HolidayInfo?,
    selectedBengaliDate: com.tuitionbatch.app.util.BengaliDate,
    selectedHijriDate: com.tuitionbatch.app.util.HijriDate
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Weekday Header Bar: SAT, SUN, MON, TUE, WED, THU, FRI (English + Bengali)
        val weekHeaders = listOf(
            DayOfWeek.SATURDAY to ("SAT" to "শনি"),
            DayOfWeek.SUNDAY to ("SUN" to "রবি"),
            DayOfWeek.MONDAY to ("MON" to "সোম"),
            DayOfWeek.TUESDAY to ("TUE" to "মঙ্গল"),
            DayOfWeek.WEDNESDAY to ("WED" to "বুধ"),
            DayOfWeek.THURSDAY to ("THU" to "বৃহ"),
            DayOfWeek.FRIDAY to ("FRI" to "শুক্র")
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            weekHeaders.forEach { (dow, labels) ->
                val (eng, bng) = labels
                val isSun = dow == DayOfWeek.SUNDAY
                val isFri = dow == DayOfWeek.FRIDAY
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = eng,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black
                        ),
                        color = when {
                            isSun -> Color(0xFFEF4444) // Red for Sunday holiday
                            isFri -> Color(0xFF10B981) // Green for Friday
                            else -> themeConfig.textPrimary
                        }
                    )
                    Text(
                        text = bng,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = when {
                            isSun -> Color(0xFFEF4444).copy(alpha = 0.8f)
                            isFri -> Color(0xFF10B981).copy(alpha = 0.8f)
                            else -> themeConfig.textSecondary
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(3.dp))

        // Calendar Grid Matrix
        val firstDayOfMonth = yearMonth.atDay(1)
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
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            for (rowIndex in 0 until totalRows) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    for (colIndex in 0 until 7) {
                        val cellIndex = rowIndex * 7 + colIndex
                        val dayIndex = cellIndex - leadingEmptyCells

                        if (dayIndex in daysData.indices) {
                            val dayData = daysData[dayIndex]
                            val isSelected = dayData.date == selectedDate
                            val isPublicHoliday = dayData.holiday?.isPublicHoliday == true
                            val hasHolidayOrEvent = dayData.holiday != null
                            val holidayIcon = if (hasHolidayOrEvent) {
                                dayData.holiday?.icon?.ifBlank { "🎉" } ?: "🎉"
                            } else null

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .clip(RoundedCornerShape(9.dp))
                                    .background(
                                        when {
                                            isSelected -> themeConfig.primary.copy(alpha = 0.32f)
                                            dayData.isToday -> themeConfig.accent.copy(alpha = 0.20f)
                                            isPublicHoliday -> Color(0xFFEF4444).copy(alpha = 0.16f)
                                            hasHolidayOrEvent -> themeConfig.accent.copy(alpha = 0.12f)
                                            dayData.isSunday -> Color(0xFFEF4444).copy(alpha = 0.06f)
                                            else -> themeConfig.textSecondary.copy(alpha = 0.05f)
                                        }
                                    )
                                    .border(
                                        width = when {
                                            isSelected -> 1.8.dp
                                            dayData.isToday -> 1.5.dp
                                            isPublicHoliday -> 1.2.dp
                                            hasHolidayOrEvent -> 1.dp
                                            else -> 0.5.dp
                                        },
                                        color = when {
                                            isSelected -> themeConfig.primary
                                            dayData.isToday -> themeConfig.accent
                                            isPublicHoliday -> Color(0xFFEF4444).copy(alpha = 0.75f)
                                            hasHolidayOrEvent -> themeConfig.accent.copy(alpha = 0.6f)
                                            else -> themeConfig.cardBorder.copy(alpha = 0.35f)
                                        },
                                        shape = RoundedCornerShape(9.dp)
                                    )
                                    .clickable {
                                        onSelectDate(dayData.date, dayData.holiday)
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    if (hasHolidayOrEvent && holidayIcon != null) {
                                        // 🌟 ২. যেখানে ছুটির দিন বা অনুষ্ঠান থাকে সেখানে ওই সংক্রান্ত ছবি/আইকন
                                        Text(
                                            text = holidayIcon,
                                            fontSize = 13.sp,
                                            lineHeight = 14.sp
                                        )

                                        // এবং এর নিচে ইংরেজি তারিখ
                                        Text(
                                            text = dayData.englishDay.toString(),
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                fontSize = 11.5.sp,
                                                fontWeight = FontWeight.Black
                                            ),
                                            color = if (isPublicHoliday) Color(0xFFEF4444) else themeConfig.accent
                                        )
                                    } else {
                                        // সাধারণ দিনে: ইংরেজি তারিখ (English Digits)
                                        Text(
                                            text = dayData.englishDay.toString(),
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontSize = 13.5.sp,
                                                fontWeight = if (isSelected || dayData.isToday) FontWeight.Black else FontWeight.Bold
                                            ),
                                            color = when {
                                                isSelected -> themeConfig.primary
                                                dayData.isSunday -> Color(0xFFEF4444)
                                                dayData.isFriday -> Color(0xFF10B981)
                                                else -> themeConfig.textPrimary
                                            }
                                        )

                                        // নিচে বাংলা তারিখ
                                        Text(
                                            text = DateHelper.toBengaliDigits(dayData.bengaliDate.day),
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold
                                            ),
                                            color = themeConfig.bengaliDateColor.copy(alpha = 0.85f)
                                        )
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

        Spacer(modifier = Modifier.height(8.dp))

        // Selected Date Bottom Summary Card
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(16.dp),
            color = themeConfig.cardBackground.copy(alpha = 0.9f),
            border = androidx.compose.foundation.BorderStroke(
                1.dp,
                if (selectedHoliday?.isPublicHoliday == true) Color(0xFFEF4444).copy(alpha = 0.5f)
                else themeConfig.primary.copy(alpha = 0.35f)
            )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                item {
                    // Date Trio Banner
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            // ইংরেজি তারিখ
                            Text(
                                text = "${DateHelper.getEnglishFormattedDate(selectedDate)} (${selectedDate.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }})",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 14.5.sp
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
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = themeConfig.bengaliDateColor
                                )

                                Text(
                                    text = "🌙 ${selectedHijriDate.format()}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = themeConfig.arabicDateColor
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Divider(color = themeConfig.cardBorder.copy(alpha = 0.3f), thickness = 0.8.dp)
                    Spacer(modifier = Modifier.height(6.dp))

                    if (selectedHoliday != null) {
                        val isPub = selectedHoliday.isPublicHoliday
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isPub) Color(0xFFEF4444).copy(alpha = 0.12f) else themeConfig.accent.copy(alpha = 0.10f),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isPub) Color(0xFFEF4444).copy(alpha = 0.45f) else themeConfig.accent.copy(alpha = 0.35f)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onOpenHolidayDetails(selectedDate, selectedHoliday) }
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = selectedHoliday.icon.ifBlank { "🎉" }, fontSize = 18.sp)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = selectedHoliday.name,
                                            style = MaterialTheme.typography.titleSmall.copy(
                                                fontWeight = FontWeight.Black,
                                                fontSize = 14.5.sp
                                            ),
                                            color = if (isPub) Color(0xFFEF4444) else themeConfig.accent
                                        )
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = if (isPub) Color(0xFFEF4444) else themeConfig.accent,
                                        contentColor = Color.White
                                    ) {
                                        Text(
                                            text = "বিস্তারিত দেখুন ➔",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold
                                            ),
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = selectedHoliday.description,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontSize = 11.5.sp,
                                        lineHeight = 16.sp
                                    ),
                                    color = themeConfig.textPrimary,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    } else {
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
                                        fontSize = 13.sp
                                    ),
                                    color = themeConfig.textPrimary
                                )
                                Text(
                                    text = if (isSunday) "সাপ্তাহিক ছুটির দিন। ব্যাচের সময়সূচি অনুসারে ক্লাস পরিচালিত হবে।"
                                    else "আজ কোনো সরকারি ছুটি নেই। নিয়মিত টিউশন ব্যাচ ও পড়াশোনা চলবে।",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
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
    onSelectHoliday: (LocalDate, HolidayInfo) -> Unit
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
                    color = if (isPub) Color(0xFFEF4444).copy(alpha = 0.08f) else themeConfig.cardBackground.copy(alpha = 0.9f),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isPub) Color(0xFFEF4444).copy(alpha = 0.4f) else themeConfig.cardBorder
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectHoliday(date, holiday) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Left Date Badge in English Digits
                        Column(
                            modifier = Modifier
                                .width(56.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    if (isPub) Color(0xFFEF4444).copy(alpha = 0.18f)
                                    else themeConfig.primary.copy(alpha = 0.15f)
                                )
                                .padding(vertical = 5.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = date.dayOfMonth.toString(),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    fontSize = 17.sp
                                ),
                                color = if (isPub) Color(0xFFEF4444) else themeConfig.primary
                            )
                            val engMonthShort = DateHelper.englishMonthNames.getOrElse(date.monthValue - 1) { "" }.take(3).uppercase()
                            Text(
                                text = engMonthShort,
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
                                Text(text = holiday.icon.ifBlank { "🎉" }, fontSize = 16.sp)
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

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = themeConfig.primary.copy(alpha = 0.15f),
                            modifier = Modifier.padding(2.dp)
                        ) {
                            Icon(
                                Icons.Default.ChevronRight,
                                contentDescription = "View Details",
                                tint = themeConfig.primary,
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * ২. বিস্তারিত উৎসব ও ছুটির ডায়ালগ (Full In-Depth Holiday & Festival Details)
 */
@Composable
private fun HolidayDetailDialog(
    holiday: HolidayInfo,
    date: LocalDate,
    themeConfig: AppThemeConfig,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val bDate = DateHelper.getWestBengalBengaliDate(date)
    val hDate = DateHelper.getArabicHijriDate(date)
    val isPub = holiday.isPublicHoliday

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .fillMaxHeight(0.85f),
            shape = RoundedCornerShape(24.dp),
            color = themeConfig.cardBackground,
            border = androidx.compose.foundation.BorderStroke(
                1.5.dp,
                if (isPub) Color(0xFFEF4444) else themeConfig.primary
            ),
            tonalElevation = 12.dp
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Top Header Banner with Festival Graphic & Close Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    if (isPub) Color(0xFFEF4444).copy(alpha = 0.25f)
                                    else themeConfig.primary.copy(alpha = 0.22f),
                                    Color.Transparent
                                )
                            )
                        )
                        .padding(14.dp)
                ) {
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
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (isPub) Color(0xFFEF4444).copy(alpha = 0.2f)
                                        else themeConfig.primary.copy(alpha = 0.2f)
                                    )
                                    .border(
                                        1.2.dp,
                                        if (isPub) Color(0xFFEF4444) else themeConfig.primary,
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = holiday.icon.ifBlank { "🎉" },
                                    fontSize = 26.sp
                                )
                            }

                            Column {
                                Text(
                                    text = holiday.name,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 17.sp
                                    ),
                                    color = if (isPub) Color(0xFFEF4444) else themeConfig.primary
                                )
                                if (holiday.englishName.isNotBlank()) {
                                    Text(
                                        text = holiday.englishName,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium
                                        ),
                                        color = themeConfig.textSecondary
                                    )
                                }
                            }
                        }

                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(themeConfig.textSecondary.copy(alpha = 0.12f))
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Close",
                                tint = themeConfig.textPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

                // Date Trio Chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = themeConfig.primary.copy(alpha = 0.12f),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "📅 ${DateHelper.getEnglishFormattedDate(date)}",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 10.5.sp),
                            color = themeConfig.primary,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = themeConfig.bengaliDateColor.copy(alpha = 0.12f),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "🌾 ${bDate.formatShort()}",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 10.5.sp),
                            color = themeConfig.bengaliDateColor,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = themeConfig.arabicDateColor.copy(alpha = 0.12f),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "🌙 ${hDate.formatShort()}",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 10.5.sp),
                            color = themeConfig.arabicDateColor,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Scrollable Comprehensive Info Sections
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Category Tag Badge
                    item {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isPub) Color(0xFFEF4444) else themeConfig.accent,
                            contentColor = Color.White
                        ) {
                            Text(
                                text = if (isPub) "🏛️ পশ্চিমবঙ্গ সরকারের এন.আই. অ্যাক্ট সরকারি ছুটি"
                                else "🎉 উৎসব ও বিশেষ শ্রদ্ধার্ঘ্য দিবস",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, fontSize = 11.5.sp),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    // 1. মূল বিবরণ
                    item {
                        DetailSectionCard(
                            title = "📌 সারসংক্ষেপ",
                            icon = Icons.Default.Info,
                            content = holiday.description,
                            themeConfig = themeConfig
                        )
                    }

                    // 2. ইতিহাস ও পৌরাণিক উৎস
                    if (holiday.originAndHistory.isNotBlank()) {
                        item {
                            DetailSectionCard(
                                title = "📜 উৎপত্তি ও ঐতিহাসিক পটভূমি",
                                icon = Icons.Default.HistoryEdu,
                                content = holiday.originAndHistory,
                                themeConfig = themeConfig
                            )
                        }
                    }

                    // 3. কেন এই দিনটি পালিত হয় / মূল তাৎপর্য
                    if (holiday.significance.isNotBlank()) {
                        item {
                            DetailSectionCard(
                                title = "🌟 কেন পালিত হয় ও এর মাহাত্ম্য",
                                icon = Icons.Default.Stars,
                                content = holiday.significance,
                                themeConfig = themeConfig
                            )
                        }
                    }

                    // 4. কীভাবে পালিত হয় ও ঐতিহ্য
                    if (holiday.celebrationStyle.isNotBlank()) {
                        item {
                            DetailSectionCard(
                                title = "🎊 উদযাপনের ঐতিহ্য ও বিশেষ রীতি",
                                icon = Icons.Default.Celebration,
                                content = holiday.celebrationStyle,
                                themeConfig = themeConfig
                            )
                        }
                    }

                    // 5. স্কুল-কলেজ ও টিউশন ব্যাচের দিকনির্দেশনা
                    item {
                        DetailSectionCard(
                            title = "🏫 স্কুল-কলেজ ও টিউশন সংক্রান্ত তথ্য",
                            icon = Icons.Default.School,
                            content = holiday.academicStatus,
                            themeConfig = themeConfig
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }

                // Bottom Action Button: WhatsApp Notice Copy
                Surface(
                    color = themeConfig.cardBackground,
                    tonalElevation = 6.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            val notice = WestBengalCalendarHelper.getHolidayNoticeText(holiday, date)
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            clipboard.setPrimaryClip(ClipData.newPlainText("Tuition Holiday Notice", notice))
                            Toast.makeText(context, "✅ ছুটির নোটিশ কপি করা হয়েছে! ছাত্র/অভিভাবকদের পাঠাতে পারবেন।", Toast.LENGTH_LONG).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = themeConfig.primary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp)
                    ) {
                        Icon(
                            Icons.Default.ContentCopy,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "📢 টিউশন ছুটির নোটিশ তৈরি ও কপি করুন",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailSectionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    content: String,
    themeConfig: AppThemeConfig
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = themeConfig.primary.copy(alpha = 0.06f),
        border = androidx.compose.foundation.BorderStroke(0.8.dp, themeConfig.cardBorder.copy(alpha = 0.4f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = themeConfig.primary,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 12.5.sp
                    ),
                    color = themeConfig.primary
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                ),
                color = themeConfig.textPrimary
            )
        }
    }
}
