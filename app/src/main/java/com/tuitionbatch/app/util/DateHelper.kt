package com.tuitionbatch.app.util

import java.time.LocalDate
import java.time.chrono.HijrahDate
import java.time.temporal.ChronoField
import java.util.Calendar

object DateHelper {

    private val bengaliDigits = mapOf(
        '0' to '০', '1' to '১', '2' to '২', '3' to '৩', '4' to '৪',
        '5' to '৫', '6' to '৬', '7' to '৭', '8' to '৮', '9' to '৯'
    )

    fun toBengaliDigits(number: Any): String {
        return number.toString().map { bengaliDigits[it] ?: it }.joinToString("")
    }

    private val bengaliDayNames = mapOf(
        Calendar.SATURDAY to "শনিবার",
        Calendar.SUNDAY to "রবিবার",
        Calendar.MONDAY to "সোমবার",
        Calendar.TUESDAY to "মঙ্গলবার",
        Calendar.WEDNESDAY to "বুধবার",
        Calendar.THURSDAY to "বৃহস্পতিবার",
        Calendar.FRIDAY to "শুক্রবার"
    )

    private val englishMonthNamesInBengali = arrayOf(
        "জানুয়ারি", "ফেব্রুয়ারি", "মার্চ", "এপ্রিল", "মে", "জুন",
        "জুলাই", "আগস্ট", "সেপ্টেম্বর", "অক্টোবর", "নভেম্বর", "ডিসেম্বর"
    )

    private val hijriMonthNames = arrayOf(
        "মহররম", "সফর", "রবিউল আউয়াল", "রবিউস সানি",
        "জুমাদাল উলা", "জুমাদাস সানি", "রজব", "শাবান",
        "রমজান", "শাওয়াল", "জিলকদ", "জিলহজ্জ"
    )

    /**
     * সবার উপরে প্রদর্শনের জন্য ইংরেজি তারিখ (বাংলায় লিখিত)
     * যেমন: সোমবার, ৯ নভেম্বর ২০২৬
     */
    fun getEnglishDate(cal: Calendar = Calendar.getInstance()): String {
        val dayOfWeek = bengaliDayNames[cal.get(Calendar.DAY_OF_WEEK)] ?: ""
        val dayOfMonth = toBengaliDigits(cal.get(Calendar.DAY_OF_MONTH))
        val monthName = englishMonthNamesInBengali[cal.get(Calendar.MONTH)]
        val year = toBengaliDigits(cal.get(Calendar.YEAR))
        return "$dayOfWeek, $dayOfMonth $monthName $year"
    }

    /**
     * পশ্চিমবঙ্গ (West Bengal) পঞ্জিকা ও সৌর বর্ষ গণনা নিয়ম অনুযায়ী বাংলা তারিখ
     * যেমন: ২৩ কার্তিক ১৪৩৩ বঙ্গাব্দ
     */
    fun getWestBengalBengaliDate(cal: Calendar = Calendar.getInstance()): String {
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH) + 1 // 1-12
        val day = cal.get(Calendar.DAY_OF_MONTH)

        // পশ্চিমবঙ্গিয় পহেলা বৈশাখ প্রায় ১৫ই এপ্রিল শুরু হয়
        val bengaliYear = if (month < 4 || (month == 4 && day < 15)) {
            year - 594
        } else {
            year - 593
        }

        val isLeap = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)

        val bMonth: String
        val bDay: Int

        when (month) {
            1 -> {
                if (day < 15) {
                    bMonth = "পৌষ"
                    bDay = 15 + day
                } else {
                    bMonth = "মাঘ"
                    bDay = day - 14
                }
            }
            2 -> {
                if (day < 14) {
                    bMonth = "মাঘ"
                    bDay = 17 + day
                } else {
                    bMonth = "ফাল্গুন"
                    bDay = day - 13
                }
            }
            3 -> {
                val febDays = if (isLeap) 29 else 28
                val falgunInFeb = febDays - 13
                if (day < 15) {
                    bMonth = "ফাল্গুন"
                    bDay = falgunInFeb + day
                } else {
                    bMonth = "চৈত্র"
                    bDay = day - 14
                }
            }
            4 -> {
                if (day < 15) {
                    bMonth = "চৈত্র"
                    bDay = 17 + day
                } else {
                    bMonth = "বৈশাখ"
                    bDay = day - 14
                }
            }
            5 -> {
                if (day < 16) {
                    bMonth = "বৈশাখ"
                    bDay = 16 + day
                } else {
                    bMonth = "জ্যৈষ্ঠ"
                    bDay = day - 15
                }
            }
            6 -> {
                if (day < 16) {
                    bMonth = "জ্যৈষ্ঠ"
                    bDay = 16 + day
                } else {
                    bMonth = "আষাঢ়"
                    bDay = day - 15
                }
            }
            7 -> {
                if (day < 17) {
                    bMonth = "আষাঢ়"
                    bDay = 15 + day
                } else {
                    bMonth = "শ্রাবণ"
                    bDay = day - 16
                }
            }
            8 -> {
                if (day < 17) {
                    bMonth = "শ্রাবণ"
                    bDay = 15 + day
                } else {
                    bMonth = "ভাদ্র"
                    bDay = day - 16
                }
            }
            9 -> {
                if (day < 17) {
                    bMonth = "ভাদ্র"
                    bDay = 15 + day
                } else {
                    bMonth = "আশ্বিন"
                    bDay = day - 16
                }
            }
            10 -> {
                if (day < 18) {
                    bMonth = "আশ্বিন"
                    bDay = 14 + day
                } else {
                    bMonth = "কার্তিক"
                    bDay = day - 17
                }
            }
            11 -> {
                if (day < 17) {
                    bMonth = "কার্তিক"
                    bDay = 14 + day
                } else {
                    bMonth = "অগ্রহায়ণ"
                    bDay = day - 16
                }
            }
            12 -> {
                if (day < 17) {
                    bMonth = "অগ্রহায়ণ"
                    bDay = 14 + day
                } else {
                    bMonth = "পৌষ"
                    bDay = day - 16
                }
            }
            else -> {
                bMonth = "বৈশাখ"
                bDay = 1
            }
        }

        return "${toBengaliDigits(bDay)} $bMonth ${toBengaliDigits(bengaliYear)} বঙ্গাব্দ"
    }

    /**
     * ইসলামিক উম্মুল কুরা / হিজরি ক্যালেন্ডার অনুযায়ী আরবি তারিখ ও মাস
     * যেমন: ২৯ জুমাদাল উলা ১৪৪৮ হিজরি
     */
    fun getArabicHijriDate(cal: Calendar = Calendar.getInstance()): String {
        return try {
            val localDate = LocalDate.of(
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH) + 1,
                cal.get(Calendar.DAY_OF_MONTH)
            )
            val hijrahDate = HijrahDate.from(localDate)
            val hDay = hijrahDate.get(ChronoField.DAY_OF_MONTH)
            val hMonth = hijrahDate.get(ChronoField.MONTH_OF_YEAR) // 1-12
            val hYear = hijrahDate.get(ChronoField.YEAR_OF_ERA)

            val monthName = hijriMonthNames.getOrElse(hMonth - 1) { "আরবি" }
            "${toBengaliDigits(hDay)} $monthName ${toBengaliDigits(hYear)} হিজরি"
        } catch (e: Exception) {
            "আরবি তারিখ"
        }
    }
}
