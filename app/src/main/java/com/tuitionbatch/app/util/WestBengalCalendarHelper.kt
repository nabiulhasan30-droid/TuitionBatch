package com.tuitionbatch.app.util

import java.time.LocalDate
import java.time.YearMonth

enum class HolidayCategory(val label: String) {
    GOVT_HOLIDAY("পশ্চিমবঙ্গে সরকারি ছুটি"),
    FESTIVAL("ধর্মীয় ও সাংস্কৃতিক উৎসব"),
    OBSERVANCE("জাতীয় ও স্মরণীয় দিবস")
}

data class HolidayInfo(
    val name: String,
    val englishName: String,
    val isPublicHoliday: Boolean,
    val category: HolidayCategory,
    val description: String,
    val traditions: String = "",
    val tag: String = if (isPublicHoliday) "সরকারি ছুটি" else "উৎসব / দিবস"
)

data class CalendarDayData(
    val date: LocalDate,
    val englishDay: Int,
    val bengaliDate: BengaliDate,
    val hijriDate: HijriDate,
    val holiday: HolidayInfo?,
    val isSunday: Boolean,
    val isFriday: Boolean,
    val isToday: Boolean
)

object WestBengalCalendarHelper {

    // Lunisolar / Tithi and movable holiday mapping for 2024 to 2030 (YYYY-MM-DD -> HolidayInfo)
    private val movableHolidaysMap = mutableMapOf<LocalDate, HolidayInfo>()

    init {
        registerMovableHolidays()
    }

    private fun registerMovableHolidays() {
        // --- 2024 ---
        addH(2024, 2, 14, "সরস্বতী পূজা / বসন্ত পঞ্চমী", "Saraswati Puja", true, HolidayCategory.GOVT_HOLIDAY, "বিদ্যা ও সুরের দেবী মা সরস্বতীর বন্দনা। ছাত্রছাত্রীদের পুষ্পাঞ্জলি ও পুস্তক পূজার দিন। পশ্চিমবঙ্গে ২ দিন ব্যাপী ছুটি থাকে।")
        addH(2024, 2, 15, "সরস্বতী পূজা (অতিরিক্ত ছুটি)", "Saraswati Puja (Addl)", true, HolidayCategory.GOVT_HOLIDAY, "সরস্বতী পূজার পরদিন বিসর্জন ও পূজার আনুষ্ঠানিকতা উপলক্ষে পশ্চিমবঙ্গ সরকারের নির্ধারিত ছুটি।")
        addH(2024, 3, 8, "মহাশিবরাত্রি", "Maha Shivratri", true, HolidayCategory.GOVT_HOLIDAY, "দেবাদিদেব মহাদেবের উপবাস ও আরাধনার পবিত্র তিথি।")
        addH(2024, 3, 25, "দোলযাত্রা", "Doljatra", true, HolidayCategory.GOVT_HOLIDAY, "শ্রীকৃষ্ণ ও শ্রীরাধিকার আবির ও রঙের উৎসব। পশ্চিমবঙ্গে আনন্দ-উচ্ছ্বাসে পালিত হয়।")
        addH(2024, 3, 26, "হোলি", "Holi", true, HolidayCategory.GOVT_HOLIDAY, "দোলযাত্রার পরদিন রঙের আনন্দ উৎসব। পশ্চিমবঙ্গে রাজ্য সরকারের ছুটি।")
        addH(2024, 3, 29, "গুড ফ্রাইডে", "Good Friday", true, HolidayCategory.GOVT_HOLIDAY, "যিশুখ্রিস্টের মানবজাতির কল্যাণে আত্মত্যাগ ও ক্রুশবিদ্ধ হওয়ার পবিত্র স্মরণ দিবস।")
        addH(2024, 4, 17, "শ্রীরাম নবমী", "Ram Navami", true, HolidayCategory.GOVT_HOLIDAY, "শ্রীরামচন্দ্রের জন্মতিথি উপলক্ষে পশ্চিমবঙ্গে সরকারি ছুটি।")
        addH(2024, 4, 21, "মহাবীর জয়ন্তী", "Mahavir Jayanti", true, HolidayCategory.GOVT_HOLIDAY, "জৈন ধর্মের ২৪তম তীর্থঙ্কর ভগবান মহাবীরের পবিত্র জন্মজয়ন্তী।")
        addH(2024, 5, 23, "বুদ্ধ পূর্ণিমা", "Buddha Purnima", true, HolidayCategory.GOVT_HOLIDAY, "ভগবান গৌতম বুদ্ধের জন্ম, বোধিলাভ ও মহাপরিনির্বাণ স্মরণোৎসব।")
        addH(2024, 7, 7, "রথযাত্রা", "Rath Yatra", true, HolidayCategory.GOVT_HOLIDAY, "জগন্নাথ দেবের রথযাত্রা উৎসব। মাহেশ ও মায়াপুরে মহাসমারোহে উদযাপিত।")
        addH(2024, 8, 19, "রাখীবন্ধন", "Raksha Bandhan", false, HolidayCategory.FESTIVAL, "ভাই ও বোনের স্নেহ-ভালোবাসার বন্ধন ও রবীন্দ্রনাথের মৈত্রী রাখী উৎসব।")
        addH(2024, 8, 26, "শ্রীকৃষ্ণ জন্মাষ্টমী", "Janmashtami", true, HolidayCategory.GOVT_HOLIDAY, "ভগবান শ্রীকৃষ্ণের পবিত্র শুভ আবির্ভাব তিথি।")
        addH(2024, 10, 2, "মহালয়া", "Mahalaya", true, HolidayCategory.GOVT_HOLIDAY, "দেবীপক্ষের শুভ সূচনা ও পিতৃপুরুষের তর্পণ তিথি।")
        addH(2024, 10, 9, "দুর্গাপূজা (মহা ষষ্ঠী)", "Durga Puja (Maha Sasthi)", true, HolidayCategory.GOVT_HOLIDAY, "দেবী দুর্গার বোধন ও দুর্গাপূজার মহাধুমধাম শুরু।")
        addH(2024, 10, 10, "দুর্গাপূজা (মহাসপ্তমী)", "Durga Puja (Maha Saptami)", true, HolidayCategory.GOVT_HOLIDAY, "নবপত্রিকা প্রবেশ ও সপ্তমী পূজা।")
        addH(2024, 10, 11, "দুর্গাপূজা (মহাষ্টমী)", "Durga Puja (Maha Ashtami)", true, HolidayCategory.GOVT_HOLIDAY, "মহাষ্টমী পূজা, অঞ্জলি ও সন্ধিপূজা। বাঙালির সবচেয়ে আনন্দের মুহূর্ত।")
        addH(2024, 10, 12, "দুর্গাপূজা (মহানবমী)", "Durga Puja (Maha Nabami)", true, HolidayCategory.GOVT_HOLIDAY, "মহানবমী বিহিত পূজা ও হোমযজ্ঞ।")
        addH(2024, 10, 13, "বিজয়া দশমী", "Vijaya Dashami", true, HolidayCategory.GOVT_HOLIDAY, "দেবী প্রতিমা বিসর্জন, সিঁদুর খেলা ও শুভ বিজয়ার শুভেচ্ছা বিনিময়।")
        addH(2024, 10, 16, "কোজাগরী লক্ষ্মীপূজা", "Lakshmi Puja", true, HolidayCategory.GOVT_HOLIDAY, "ঘরে ঘরে ধনধান্যের দেবী মা লক্ষ্মীর আরাধনা।")
        addH(2024, 10, 31, "কালীপূজা ও দীপাবলি", "Kali Puja & Diwali", true, HolidayCategory.GOVT_HOLIDAY, "মা কালীর পূজা ও আলোর উৎসব দীপাবলি।")
        addH(2024, 11, 3, "ভ্রাতৃদ্বিতীয়া / ভাইফোঁটা", "Bhai Phota", true, HolidayCategory.GOVT_HOLIDAY, "বোনেরা ভাইদের দীর্ঘায়ু কামনা করে কপালে চন্দন ও কাজলের ফোঁটা দেয়।")
        addH(2024, 11, 7, "ছট পূজা", "Chhath Puja", true, HolidayCategory.GOVT_HOLIDAY, "সূর্যদেব ও ছটি মাইয়ার পবিত্র ব্রত ও জলাশয়ে অর্ঘ্য দান।")
        addH(2024, 11, 15, "গুরু নানক জয়ন্তী", "Guru Nanak Jayanti", true, HolidayCategory.GOVT_HOLIDAY, "শিখ ধর্মের প্রতিষ্ঠাতা শ্রী গুরু নানক দেব জীর পবিত্র প্রকাশ পর্ব।")

        // --- 2025 ---
        addH(2025, 2, 2, "সরস্বতী পূজা / বসন্ত পঞ্চমী", "Saraswati Puja", true, HolidayCategory.GOVT_HOLIDAY, "মা সরস্বতীর বন্দনা ও অঞ্জলি। পশ্চিমবঙ্গে সরকারি ছুটি।")
        addH(2025, 2, 3, "সরস্বতী পূজা (অতিরিক্ত ছুটি)", "Saraswati Puja (Addl)", true, HolidayCategory.GOVT_HOLIDAY, "সরস্বতী পূজা পরবর্তী সরকারি ছুটি।")
        addH(2025, 2, 26, "মহাশিবরাত্রি", "Maha Shivratri", true, HolidayCategory.GOVT_HOLIDAY, "মহাদেবের ব্রত ও শিবরাত্রির জাগরণ।")
        addH(2025, 3, 14, "দোলযাত্রা", "Doljatra", true, HolidayCategory.GOVT_HOLIDAY, "বাঙালির বসন্তোৎসব ও আবিরের আনন্দ।")
        addH(2025, 3, 15, "হোলি", "Holi", true, HolidayCategory.GOVT_HOLIDAY, "রঙিন হোলি উৎসব ও রাজ্য সরকারি ছুটি।")
        addH(2025, 4, 6, "শ্রীরাম নবমী", "Ram Navami", true, HolidayCategory.GOVT_HOLIDAY, "শ্রীরাম নবমী উপলক্ষে পশ্চিমবঙ্গে সরকারি ছুটি।")
        addH(2025, 4, 11, "মহাবীর জয়ন্তী", "Mahavir Jayanti", true, HolidayCategory.GOVT_HOLIDAY, "ভগবান মহাবীরের জন্মজয়ন্তী।")
        addH(2025, 4, 18, "গুড ফ্রাইডে", "Good Friday", true, HolidayCategory.GOVT_HOLIDAY, "যিশুখ্রিস্টের আত্মত্যাগ স্মরণে পবিত্র গুড ফ্রাইডে।")
        addH(2025, 5, 12, "বুদ্ধ পূর্ণিমা", "Buddha Purnima", true, HolidayCategory.GOVT_HOLIDAY, "গৌতম বুদ্ধের জন্ম ও বুদ্ধত্ব প্রাপ্তির মহাতীর্থ তিথি।")
        addH(2025, 6, 27, "রথযাত্রা", "Rath Yatra", true, HolidayCategory.GOVT_HOLIDAY, "শ্রী শ্রী জগন্নাথ দেবের রথযাত্রা।")
        addH(2025, 8, 9, "রাখীবন্ধন", "Raksha Bandhan", false, HolidayCategory.FESTIVAL, "ভ্রাতৃত্ব ও সম্প্রীতির রাখীবন্ধন উৎসব।")
        addH(2025, 8, 16, "শ্রীকৃষ্ণ জন্মাষ্টমী", "Janmashtami", true, HolidayCategory.GOVT_HOLIDAY, "শ্রীকৃষ্ণের জন্মতিথি মহোৎসব।")
        addH(2025, 9, 21, "মহালয়া", "Mahalaya", true, HolidayCategory.GOVT_HOLIDAY, "দেবীপক্ষের আগমন ও মহালয়ার অমাবস্যা তর্পণ।")
        addH(2025, 9, 28, "দুর্গাপূজা (মহা ষষ্ঠী)", "Durga Puja (Maha Sasthi)", true, HolidayCategory.GOVT_HOLIDAY, "শারদীয়া দুর্গাপূজার উদ্বোধন ও কল্পারম্ভ।")
        addH(2025, 9, 29, "দুর্গাপূজা (মহাসপ্তমী)", "Durga Puja (Maha Saptami)", true, HolidayCategory.GOVT_HOLIDAY, "নবপত্রিকা স্নান ও মহাসপ্তমী পূজা।")
        addH(2025, 9, 30, "দুর্গাপূজা (মহাষ্টমী)", "Durga Puja (Maha Ashtami)", true, HolidayCategory.GOVT_HOLIDAY, "অঞ্জলি ও কুমারী পূজা সহ মহাষ্টমী।")
        addH(2025, 10, 1, "দুর্গাপূজা (মহানবমী)", "Durga Puja (Maha Nabami)", true, HolidayCategory.GOVT_HOLIDAY, "মহানবমীর হোম ও বিহিত পূজা।")
        addH(2025, 10, 2, "বিজয়া দশমী", "Vijaya Dashami", true, HolidayCategory.GOVT_HOLIDAY, "প্রতিমা নিরঞ্জন ও শুভ বিজয়ার শুভেচ্ছা।")
        addH(2025, 10, 6, "কোজাগরী লক্ষ্মীপূজা", "Lakshmi Puja", true, HolidayCategory.GOVT_HOLIDAY, "কোজাগরী পূর্ণিমায় মা লক্ষ্মীর বন্দনা।")
        addH(2025, 10, 20, "কালীপূজা ও দীপাবলি", "Kali Puja & Diwali", true, HolidayCategory.GOVT_HOLIDAY, "শ্যামাপূজা ও দীপাবলির আলোর রোশনাই।")
        addH(2025, 10, 23, "ভ্রাতৃদ্বিতীয়া / ভাইফোঁটা", "Bhai Phota", true, HolidayCategory.GOVT_HOLIDAY, "যমদুয়ারে কাঁটা দিয়ে ভাইয়ের মঙ্গল প্রার্থনা।")
        addH(2025, 10, 28, "ছট পূজা", "Chhath Puja", true, HolidayCategory.GOVT_HOLIDAY, "পবিত্র ছট ব্রতের সান্ধ্যকালীন অর্ঘ্য।")
        addH(2025, 11, 5, "গুরু নানক জয়ন্তী", "Guru Nanak Jayanti", true, HolidayCategory.GOVT_HOLIDAY, "শ্রী গুরু নানক দেব জীর জন্মতিথি উৎসব।")

        // --- 2026 ---
        addH(2026, 1, 23, "সরস্বতী পূজা / বসন্ত পঞ্চমী", "Saraswati Puja", true, HolidayCategory.GOVT_HOLIDAY, "জ্ঞানদায়িনী মা সরস্বতীর পূজা ও নেতাজী জন্মজয়ন্তী।")
        addH(2026, 1, 24, "সরস্বতী পূজা (অতিরিক্ত ছুটি)", "Saraswati Puja (Addl)", true, HolidayCategory.GOVT_HOLIDAY, "সরস্বতী পূজার পরবর্তী সরকারি ছুটি।")
        addH(2026, 2, 15, "মহাশিবরাত্রি", "Maha Shivratri", true, HolidayCategory.GOVT_HOLIDAY, "মহাদেব শিবের আরাধনার পবিত্র রাত।")
        addH(2026, 3, 3, "দোলযাত্রা", "Doljatra", true, HolidayCategory.GOVT_HOLIDAY, "বসন্তের রঙের উৎসব দোলযাত্রা।")
        addH(2026, 3, 4, "হোলি", "Holi", true, HolidayCategory.GOVT_HOLIDAY, "হোলির রঙের আনন্দ ও সরকারি ছুটি।")
        addH(2026, 3, 27, "শ্রীরাম নবমী", "Ram Navami", true, HolidayCategory.GOVT_HOLIDAY, "শ্রীরামচন্দ্রের জন্মতিথি।")
        addH(2026, 3, 31, "মহাবীর জয়ন্তী", "Mahavir Jayanti", true, HolidayCategory.GOVT_HOLIDAY, "জৈন তীর্থঙ্কর ভগবান মহাবীর জয়ন্তী।")
        addH(2026, 4, 3, "গুড ফ্রাইডে", "Good Friday", true, HolidayCategory.GOVT_HOLIDAY, "পবিত্র গুড ফ্রাইডে। যিশুর আত্মত্যাগ স্মরণ।")
        addH(2026, 5, 31, "বুদ্ধ পূর্ণিমা", "Buddha Purnima", true, HolidayCategory.GOVT_HOLIDAY, "তথাগত গৌতম বুদ্ধের পবিত্র পূর্ণিমা।")
        addH(2026, 7, 16, "রথযাত্রা", "Rath Yatra", true, HolidayCategory.GOVT_HOLIDAY, "শ্রী জগন্নাথ দেবের রথযাত্রা উৎসব।")
        addH(2026, 8, 28, "রাখীবন্ধন", "Raksha Bandhan", false, HolidayCategory.FESTIVAL, "রাখীবন্ধন ও সৌহার্দ্য দিবস।")
        addH(2026, 9, 4, "শ্রীকৃষ্ণ জন্মাষ্টমী", "Janmashtami", true, HolidayCategory.GOVT_HOLIDAY, "শ্রীকৃষ্ণ জন্মাষ্টমী মহোৎসব।")
        addH(2026, 10, 10, "মহালয়া", "Mahalaya", true, HolidayCategory.GOVT_HOLIDAY, "দেবীপক্ষের শুভ সূচনা ও তর্পণ তিথি।")
        addH(2026, 10, 16, "দুর্গাপূজা (মহা ষষ্ঠী)", "Durga Puja (Maha Sasthi)", true, HolidayCategory.GOVT_HOLIDAY, "শারদোৎসবের বোধন ও আনন্দের সূচনা।")
        addH(2026, 10, 17, "দুর্গাপূজা (মহাসপ্তমী)", "Durga Puja (Maha Saptami)", true, HolidayCategory.GOVT_HOLIDAY, "নবপত্রিকা প্রবেশ ও সপ্তমী পূজা।")
        addH(2026, 10, 18, "দুর্গাপূজা (মহাষ্টমী)", "Durga Puja (Maha Ashtami)", true, HolidayCategory.GOVT_HOLIDAY, "মহাষ্টমী পুষ্পাঞ্জলি ও সন্ধিপূজা।")
        addH(2026, 10, 19, "দুর্গাপূজা (মহানবমী)", "Durga Puja (Maha Nabami)", true, HolidayCategory.GOVT_HOLIDAY, "মহানবমীর মহাযজ্ঞ ও আরাধনা।")
        addH(2026, 10, 20, "বিজয়া দশমী", "Vijaya Dashami", true, HolidayCategory.GOVT_HOLIDAY, "বিজয়া দশমী ও মা দুর্গার বিসর্জন পর্ব।")
        addH(2026, 10, 24, "কোজাগরী লক্ষ্মীপূজা", "Lakshmi Puja", true, HolidayCategory.GOVT_HOLIDAY, "কোজাগরী পূর্ণিমায় মা লক্ষ্মীর পূজা।")
        addH(2026, 11, 8, "কালীপূজা ও দীপাবলি", "Kali Puja & Diwali", true, HolidayCategory.GOVT_HOLIDAY, "মা কালীর পূজা ও আলোর উৎসব দীপাবলি।")
        addH(2026, 11, 11, "ভ্রাতৃদ্বিতীয়া / ভাইফোঁটা", "Bhai Phota", true, HolidayCategory.GOVT_HOLIDAY, "ভাইবোনের মিষ্টি ও ফোঁটার আনন্দ অনুষ্ঠান।")
        addH(2026, 11, 15, "ছট পূজা", "Chhath Puja", true, HolidayCategory.GOVT_HOLIDAY, "পবিত্র ছট উৎসবের উপবাস ও অঞ্জলি।")
        addH(2026, 11, 24, "গুরু নানক জয়ন্তী", "Guru Nanak Jayanti", true, HolidayCategory.GOVT_HOLIDAY, "শ্রী গুরু নানক দেব জীর জন্মতিথি উৎসব।")

        // --- 2027 ---
        addH(2027, 2, 11, "সরস্বতী পূজা / বসন্ত পঞ্চমী", "Saraswati Puja", true, HolidayCategory.GOVT_HOLIDAY, "বাগদেবী মা সরস্বতীর পূজা ও অঞ্জলি।")
        addH(2027, 3, 6, "মহাশিবরাত্রি", "Maha Shivratri", true, HolidayCategory.GOVT_HOLIDAY, "মহাদেবের পবিত্র মহাশিবরাত্রি।")
        addH(2027, 3, 22, "দোলযাত্রা", "Doljatra", true, HolidayCategory.GOVT_HOLIDAY, "বসন্তের দোল উৎসব ও আবিরের খেলা।")
        addH(2027, 3, 23, "হোলি", "Holi", true, HolidayCategory.GOVT_HOLIDAY, "হোলি উৎসব ও সরকারি ছুটি।")
        addH(2027, 3, 26, "গুড ফ্রাইডে", "Good Friday", true, HolidayCategory.GOVT_HOLIDAY, "পবিত্র গুড ফ্রাইডে।")
        addH(2027, 4, 15, "শ্রীরাম নবমী", "Ram Navami", true, HolidayCategory.GOVT_HOLIDAY, "শ্রীরাম নবমী।")
        addH(2027, 4, 19, "মহাবীর জয়ন্তী", "Mahavir Jayanti", true, HolidayCategory.GOVT_HOLIDAY, "ভগবান মহাবীর জয়ন্তী।")
        addH(2027, 5, 20, "বুদ্ধ পূর্ণিমা", "Buddha Purnima", true, HolidayCategory.GOVT_HOLIDAY, "বুদ্ধ পূর্ণিমা উৎসব।")
        addH(2027, 7, 6, "রথযাত্রা", "Rath Yatra", true, HolidayCategory.GOVT_HOLIDAY, "শ্রী জগন্নাথ দেবের রথযাত্রা।")
        addH(2027, 8, 25, "শ্রীকৃষ্ণ জন্মাষ্টমী", "Janmashtami", true, HolidayCategory.GOVT_HOLIDAY, "ভগবান শ্রীকৃষ্ণের আবির্ভাব তিথি।")
        addH(2027, 9, 29, "মহালয়া", "Mahalaya", true, HolidayCategory.GOVT_HOLIDAY, "মহালয়া ও পিতৃ তর্পণ।")
        addH(2027, 10, 6, "দুর্গাপূজা (মহা ষষ্ঠী)", "Durga Puja (Maha Sasthi)", true, HolidayCategory.GOVT_HOLIDAY, "দুর্গাপূজা ষষ্ঠী।")
        addH(2027, 10, 7, "দুর্গাপূজা (মহাসপ্তমী)", "Durga Puja (Maha Saptami)", true, HolidayCategory.GOVT_HOLIDAY, "মহাসপ্তমী পূজা।")
        addH(2027, 10, 8, "দুর্গাপূজা (মহাষ্টমী)", "Durga Puja (Maha Ashtami)", true, HolidayCategory.GOVT_HOLIDAY, "মহাষ্টমী অঞ্জলি ও সন্ধিপূজা।")
        addH(2027, 10, 9, "দুর্গাপূজা (মহানবমী)", "Durga Puja (Maha Nabami)", true, HolidayCategory.GOVT_HOLIDAY, "মহানবমী।")
        addH(2027, 10, 10, "বিজয়া দশমী", "Vijaya Dashami", true, HolidayCategory.GOVT_HOLIDAY, "বিজয়া দশমী।")
        addH(2027, 10, 14, "কোজাগরী লক্ষ্মীপূজা", "Lakshmi Puja", true, HolidayCategory.GOVT_HOLIDAY, "কোজাগরী লক্ষ্মীপূজা।")
        addH(2027, 10, 29, "কালীপূজা ও দীপাবলি", "Kali Puja & Diwali", true, HolidayCategory.GOVT_HOLIDAY, "দীপাবলি ও কালীপূজা।")
        addH(2027, 11, 1, "ভ্রাতৃদ্বিতীয়া / ভাইফোঁটা", "Bhai Phota", true, HolidayCategory.GOVT_HOLIDAY, "ভাইফোঁটা অনুষ্ঠান।")
        addH(2027, 11, 5, "ছট পূজা", "Chhath Puja", true, HolidayCategory.GOVT_HOLIDAY, "ছট ব্রত ও অঞ্জলি।")
        addH(2027, 11, 14, "গুরু নানক জয়ন্তী", "Guru Nanak Jayanti", true, HolidayCategory.GOVT_HOLIDAY, "শ্রী গুরু নানক জয়ন্তী।")

        // --- 2028 ---
        addH(2028, 1, 31, "সরস্বতী পূজা / বসন্ত পঞ্চমী", "Saraswati Puja", true, HolidayCategory.GOVT_HOLIDAY, "মা সরস্বতীর পূজা ও অঞ্জলি।")
        addH(2028, 2, 23, "মহাশিবরাত্রি", "Maha Shivratri", true, HolidayCategory.GOVT_HOLIDAY, "পবিত্র মহাশিবরাত্রি।")
        addH(2028, 3, 11, "দোলযাত্রা", "Doljatra", true, HolidayCategory.GOVT_HOLIDAY, "বসন্তের দোল উৎসব।")
        addH(2028, 3, 12, "হোলি", "Holi", true, HolidayCategory.GOVT_HOLIDAY, "হোলি উৎসব।")
        addH(2028, 4, 14, "গুড ফ্রাইডে", "Good Friday", true, HolidayCategory.GOVT_HOLIDAY, "পবিত্র গুড ফ্রাইডে।")
        addH(2028, 5, 8, "বুদ্ধ পূর্ণিমা", "Buddha Purnima", true, HolidayCategory.GOVT_HOLIDAY, "বুদ্ধ পূর্ণিমা উৎসব।")
        addH(2028, 6, 24, "রথযাত্রা", "Rath Yatra", true, HolidayCategory.GOVT_HOLIDAY, "জগন্নাথ দেবের রথযাত্রা।")
        addH(2028, 8, 13, "শ্রীকৃষ্ণ জন্মাষ্টমী", "Janmashtami", true, HolidayCategory.GOVT_HOLIDAY, "শ্রীকৃষ্ণ জন্মাষ্টমী।")
        addH(2028, 9, 18, "মহালয়া", "Mahalaya", true, HolidayCategory.GOVT_HOLIDAY, "মহালয়া ও পিতৃ তর্পণ।")
        addH(2028, 9, 25, "দুর্গাপূজা (মহা ষষ্ঠী)", "Durga Puja (Maha Sasthi)", true, HolidayCategory.GOVT_HOLIDAY, "দুর্গাপূজা ষষ্ঠী।")
        addH(2028, 9, 26, "দুর্গাপূজা (মহাসপ্তমী)", "Durga Puja (Maha Saptami)", true, HolidayCategory.GOVT_HOLIDAY, "মহাসপ্তমী।")
        addH(2028, 9, 27, "দুর্গাপূজা (মহাষ্টমী)", "Durga Puja (Maha Ashtami)", true, HolidayCategory.GOVT_HOLIDAY, "মহাষ্টমী।")
        addH(2028, 9, 28, "দুর্গাপূজা (মহানবমী)", "Durga Puja (Maha Nabami)", true, HolidayCategory.GOVT_HOLIDAY, "মহানবমী।")
        addH(2028, 9, 29, "বিজয়া দশমী", "Vijaya Dashami", true, HolidayCategory.GOVT_HOLIDAY, "বিজয়া দশমী।")
        addH(2028, 10, 3, "কোজাগরী লক্ষ্মীপূজা", "Lakshmi Puja", true, HolidayCategory.GOVT_HOLIDAY, "কোজাগরী লক্ষ্মীপূজা।")
        addH(2028, 10, 17, "কালীপূজা ও দীপাবলি", "Kali Puja & Diwali", true, HolidayCategory.GOVT_HOLIDAY, "দীপাবলি ও কালীপূজা।")
        addH(2028, 10, 20, "ভ্রাতৃদ্বিতীয়া / ভাইফোঁটা", "Bhai Phota", true, HolidayCategory.GOVT_HOLIDAY, "ভাইফোঁটা অনুষ্ঠান।")
        addH(2028, 10, 24, "ছট পূজা", "Chhath Puja", true, HolidayCategory.GOVT_HOLIDAY, "ছট পূজা।")
        addH(2028, 11, 2, "গুরু নানক জয়ন্তী", "Guru Nanak Jayanti", true, HolidayCategory.GOVT_HOLIDAY, "গুরু নানক জয়ন্তী।")

        // --- 2029 & 2030 Highlights ---
        addH(2029, 2, 18, "সরস্বতী পূজা / বসন্ত পঞ্চমী", "Saraswati Puja", true, HolidayCategory.GOVT_HOLIDAY, "মা সরস্বতীর পূজা ও অঞ্জলি।")
        addH(2029, 3, 1, "দোলযাত্রা", "Doljatra", true, HolidayCategory.GOVT_HOLIDAY, "দোলযাত্রা।")
        addH(2029, 10, 16, "দুর্গাপূজা (মহাষ্টমী)", "Durga Puja (Maha Ashtami)", true, HolidayCategory.GOVT_HOLIDAY, "দুর্গাপূজা মহাষ্টমী।")
        addH(2029, 11, 5, "কালীপূজা ও দীপাবলি", "Kali Puja & Diwali", true, HolidayCategory.GOVT_HOLIDAY, "কালীপূজা ও দীপাবলি।")

        addH(2030, 2, 7, "সরস্বতী পূজা / বসন্ত পঞ্চমী", "Saraswati Puja", true, HolidayCategory.GOVT_HOLIDAY, "মা সরস্বতীর পূজা ও অঞ্জলি।")
        addH(2030, 3, 19, "দোলযাত্রা", "Doljatra", true, HolidayCategory.GOVT_HOLIDAY, "দোলযাত্রা।")
        addH(2030, 10, 5, "দুর্গাপূজা (মহাষ্টমী)", "Durga Puja (Maha Ashtami)", true, HolidayCategory.GOVT_HOLIDAY, "দুর্গাপূজা মহাষ্টমী।")
        addH(2030, 10, 26, "কালীপূজা ও দীপাবলি", "Kali Puja & Diwali", true, HolidayCategory.GOVT_HOLIDAY, "কালীপূজা ও দীপাবলি।")
    }

    private fun addH(
        year: Int, month: Int, day: Int,
        name: String, engName: String, isPublic: Boolean,
        cat: HolidayCategory, desc: String
    ) {
        val date = LocalDate.of(year, month, day)
        movableHolidaysMap[date] = HolidayInfo(
            name = name,
            englishName = engName,
            isPublicHoliday = isPublic,
            category = cat,
            description = desc
        )
    }

    /**
     * পশ্চিমবঙ্গের যেকোনো তারিখের জন্য সম্পূর্ণ অফলাইন চিরস্থায়ী ছুটি বা উৎসব নির্ধারণ
     */
    fun getHolidayForDate(date: LocalDate): HolidayInfo? {
        val month = date.monthValue
        val day = date.dayOfMonth

        // 1. First check registered Hindu / Lunisolar / Movable Gazetted Holidays
        movableHolidaysMap[date]?.let { return it }

        // 2. Fixed Gregorian West Bengal & National Holidays
        when (month) {
            1 -> {
                if (day == 1) return HolidayInfo(
                    name = "ইংরেজি নববর্ষ",
                    englishName = "New Year's Day",
                    isPublicHoliday = true,
                    category = HolidayCategory.GOVT_HOLIDAY,
                    description = "গ্রেগরীয় ক্যালেন্ডারের নতুন বছরের প্রথম দিন। পশ্চিমবঙ্গে সর্বস্তরে উৎসব ও রাজ্য সরকারের নির্ধারিত ছুটি।"
                )
                if (day == 12) return HolidayInfo(
                    name = "স্বামী বিবেকানন্দ জন্মজয়ন্তী",
                    englishName = "Swami Vivekananda Jayanti",
                    isPublicHoliday = true,
                    category = HolidayCategory.GOVT_HOLIDAY,
                    description = "যুগনায়ক স্বামী বিবেকানন্দের জন্মজয়ন্তী ও জাতীয় যুব দিবস। যুবসমাজকে দেশসেবা ও আত্মবিশ্বাসের অনুপ্রেরণায় উদ্দীপ্ত করার দিন।"
                )
                if (day == 23) return HolidayInfo(
                    name = "নেতাজী সুভাষচন্দ্র বসু জন্মজয়ন্তী",
                    englishName = "Netaji Subhas Chandra Bose Jayanti",
                    isPublicHoliday = true,
                    category = HolidayCategory.GOVT_HOLIDAY,
                    description = "দেশনায়ক নেতাজী সুভাষচন্দ্র বসুর পবিত্র জন্মজয়ন্তী (পরাক্রম দিবস)। ভারতমাতার মুক্তি সংগ্রামের মহানায়কের প্রতি বিনম্র শ্রদ্ধা।"
                )
                if (day == 26) return HolidayInfo(
                    name = "প্রজাতন্ত্র দিবস",
                    englishName = "Republic Day",
                    isPublicHoliday = true,
                    category = HolidayCategory.GOVT_HOLIDAY,
                    description = "ভারতের প্রজাতন্ত্র দিবস। ১৯৫০ সালের ২৬শে জানুয়ারি ভারতীয় সংবিধান কার্যকর হয়েছিল। জাতীয় ও রাজ্য সরকারি ছুটি।"
                )
            }
            2 -> {
                if (day == 21) return HolidayInfo(
                    name = "আন্তর্জাতিক মাতৃভাষা দিবস",
                    englishName = "International Mother Language Day",
                    isPublicHoliday = false,
                    category = HolidayCategory.OBSERVANCE,
                    description = "মাতৃভাষার মর্যাদা রক্ষায় আত্মত্যাগের স্মরণে বিশ্বব্যাপী আন্তর্জাতিক মাতৃভাষা ও অমর একুশে পালন।"
                )
            }
            5 -> {
                if (day == 1) return HolidayInfo(
                    name = "মে দিবস / আন্তর্জাতিক শ্রমিক দিবস",
                    englishName = "May Day / International Workers' Day",
                    isPublicHoliday = true,
                    category = HolidayCategory.GOVT_HOLIDAY,
                    description = "শ্রমজীবী মানুষের অধিকার ও আত্মত্যাগের স্মরণে বিশ্ব শ্রমিক দিবস। পশ্চিমবঙ্গে সার্বিক সরকারি ছুটি।"
                )
                if (day == 9) return HolidayInfo(
                    name = "রবীন্দ্র জয়ন্তী",
                    englishName = "Rabindra Jayanti",
                    isPublicHoliday = true,
                    category = HolidayCategory.GOVT_HOLIDAY,
                    description = "বিশ্বকবি রবীন্দ্রনাথ ঠাকুরের জন্মজয়ন্তী (২৫শে বৈশাখ)। পশ্চিমবঙ্গ জুড়ে গান, কবিতা ও নাটকের মাধ্যমে শ্রদ্ধা জ্ঞাপন ও সরকারি ছুটি।"
                )
            }
            8 -> {
                if (day == 15) return HolidayInfo(
                    name = "ভারতের স্বাধীনতা দিবস",
                    englishName = "Independence Day",
                    isPublicHoliday = true,
                    category = HolidayCategory.GOVT_HOLIDAY,
                    description = "১৯৪৭ সালের ১৫ই আগস্ট ভারত ব্রিটিশ শাসন থেকে স্বাধীনতা লাভ করে। জাতীয় ও পশ্চিমবঙ্গে পূর্ণ সরকারি ছুটি।"
                )
            }
            9 -> {
                if (day == 5) return HolidayInfo(
                    name = "শিক্ষক দিবস",
                    englishName = "Teachers' Day",
                    isPublicHoliday = false,
                    category = HolidayCategory.OBSERVANCE,
                    description = "ড. সর্বপল্লী রাধাকৃষ্ণনের জন্মজয়ন্তী উপলক্ষে শিক্ষকদের প্রতি শ্রদ্ধা ও ভালোবাসা নিবেদনের পবিত্র দিবস।"
                )
                if (day == 17) return HolidayInfo(
                    name = "বিশ্বকর্মা পূজা",
                    englishName = "Vishwakarma Puja",
                    isPublicHoliday = false,
                    category = HolidayCategory.FESTIVAL,
                    description = "দেবশিল্পী ভগবান বিশ্বকর্মার আরাধনা। কলকারখানা, যানবাহন ও নির্মাণকাজে বিশেষ পূজা ও ঘুড়ি ওড়ানোর উৎসব।"
                )
            }
            10 -> {
                if (day == 2) return HolidayInfo(
                    name = "মহাত্মা গান্ধী জয়ন্তী",
                    englishName = "Mahatma Gandhi Jayanti",
                    isPublicHoliday = true,
                    category = HolidayCategory.GOVT_HOLIDAY,
                    description = "জাতির জনক মহাত্মা গান্ধীর জন্মজয়ন্তী ও আন্তর্জাতিক অহিংসা দিবস। জাতীয় ও সরকারি ছুটি।"
                )
            }
            11 -> {
                if (day == 14) return HolidayInfo(
                    name = "শিশু দিবস",
                    englishName = "Children's Day",
                    isPublicHoliday = false,
                    category = HolidayCategory.OBSERVANCE,
                    description = "পণ্ডিত জওহরলাল নেহরুর জন্মজয়ন্তী উপলক্ষে শিশুদের অধিকার ও আনন্দ উদযাপনের দিন।"
                )
                if (day == 15) return HolidayInfo(
                    name = "বিরসা মুন্ডা জয়ন্তী",
                    englishName = "Birsa Munda Jayanti",
                    isPublicHoliday = true,
                    category = HolidayCategory.GOVT_HOLIDAY,
                    description = "উপজাতি স্বাধীনতা সংগ্রামী ধরতি আবা ভগবান বিরসা মুন্ডার পবিত্র জন্মজয়ন্তী। পশ্চিমবঙ্গে সরকারি ছুটি।"
                )
            }
            12 -> {
                if (day == 25) return HolidayInfo(
                    name = "বড়দিন / ক্রিসমাস ডে",
                    englishName = "Christmas Day",
                    isPublicHoliday = true,
                    category = HolidayCategory.GOVT_HOLIDAY,
                    description = "প্রভু যিশুখ্রিস্টের শুভ জন্মতিথি। সম্প্রীতি, শান্তি ও ক্ষমার মহাবাণী। পশ্চিমবঙ্গে আলোকসজ্জায় সাজানো হয় এবং সরকারি ছুটি থাকে।"
                )
                if (day == 31) return HolidayInfo(
                    name = "বর্ষবিদায় রজনী",
                    englishName = "New Year's Eve",
                    isPublicHoliday = false,
                    category = HolidayCategory.OBSERVANCE,
                    description = "বিদায়ী বছরের শেষ দিন। আনন্দ ও শুভ কামনায় নতুন বছরকে বরণের প্রস্তুতি।"
                )
            }
        }

        // 3. Bengali Solar Calendar Holidays (পশ্চিমবঙ্গ নিয়ম)
        val bDate = DateHelper.getWestBengalBengaliDate(date)
        if (bDate.monthName == "বৈশাখ" && bDate.day == 1) {
            return HolidayInfo(
                name = "পয়লা বৈশাখ / শুভ বাংলা নববর্ষ",
                englishName = "Poila Baisakh / Bengali New Year",
                isPublicHoliday = true,
                category = HolidayCategory.GOVT_HOLIDAY,
                description = "বাঙালির প্রধান জাতীয় ও সার্বজনীন উৎসব। নতুন খাতা উন্মোচন, ব্যবসায় হালখাতা ও মিষ্টিমুখের মাধ্যমে পশ্চিমবঙ্গে মহাসমারোহে উদ্‌যাপিত।"
            )
        }
        if (bDate.monthName == "চৈত্র" && (bDate.day >= 30 || (date.monthValue == 4 && date.dayOfMonth in 13..14))) {
            return HolidayInfo(
                name = "চৈত্র সংক্রান্তি ও চড়ক পূজা",
                englishName = "Chaitra Sankranti",
                isPublicHoliday = true,
                category = HolidayCategory.GOVT_HOLIDAY,
                description = "বাংলা বছরের শেষ দিন। নীলপূজা ও গ্রামীণ চড়ক মেলার লোকঐতিহ্য। পশ্চিমবঙ্গে রাজ্য সরকারের ছুটির দিন।"
            )
        }
        if (bDate.monthName == "বৈশাখ" && bDate.day == 25 && date.dayOfMonth != 9) {
            return HolidayInfo(
                name = "রবীন্দ্র জয়ন্তী (২৫শে বৈশাখ)",
                englishName = "Rabindra Jayanti",
                isPublicHoliday = true,
                category = HolidayCategory.GOVT_HOLIDAY,
                description = "বঙ্গাব্দ ২৫শে বৈশাখ বিশ্বকবি রবীন্দ্রনাথ ঠাকুরের পবিত্র আবির্ভাব তিথি। পশ্চিমবঙ্গে সাংস্কৃতিক শ্রদ্ধাঞ্জলি ও ছুটি।"
            )
        }
        if (bDate.monthName == "জ্যৈষ্ঠ" && bDate.day == 11) {
            return HolidayInfo(
                name = "কাজী নজরুল ইসলাম জয়ন্তী",
                englishName = "Kazi Nazrul Islam Jayanti",
                isPublicHoliday = false,
                category = HolidayCategory.OBSERVANCE,
                description = "বিদ্রোহী কবি কাজী নজরুল ইসলামের পবিত্র জন্মজয়ন্তী (১১ই জ্যৈষ্ঠ)। সাম্য, প্রেম ও বিদ্রোহের কবির স্মরণে শ্রদ্ধাঞ্জলি।"
            )
        }

        // 4. Islamic Hijri Calendar Holidays (Perpetually computed via HijrahDate)
        val hDate = DateHelper.getArabicHijriDate(date)
        val hMonth = hDate.monthName
        val hDay = hDate.day

        if (hMonth == "শাওয়াল" && hDay == 1) {
            return HolidayInfo(
                name = "ঈদুল ফিতর",
                englishName = "Eid-ul-Fitr",
                isPublicHoliday = true,
                category = HolidayCategory.GOVT_HOLIDAY,
                description = "ইসলামের প্রধান ধর্মীয় উৎসব। এক মাস সিয়াম সাধনা শেষে শাওয়াল মাসের চাঁদের উদয়ে ঈদ উৎসব। পশ্চিমবঙ্গে পূর্ণ সরকারি ছুটি।"
            )
        }
        if (hMonth == "শাওয়াল" && hDay == 2) {
            return HolidayInfo(
                name = "ঈদুল ফিতর (অতিরিক্ত ছুটি)",
                englishName = "Eid-ul-Fitr (Addl Day)",
                isPublicHoliday = true,
                category = HolidayCategory.GOVT_HOLIDAY,
                description = "ঈদুল ফিতর পরবর্তী উৎসব ও আত্মীয়স্বজনের সাথে আনন্দ ভাগাভাগির জন্য পশ্চিমবঙ্গ রাজ্য সরকারের অতিরিক্ত ছুটি।"
            )
        }
        if (hMonth == "জিলহজ্জ" && hDay == 9) {
            return HolidayInfo(
                name = "পবিত্র আরাফাত দিবস / হজ",
                englishName = "Day of Arafah",
                isPublicHoliday = false,
                category = HolidayCategory.FESTIVAL,
                description = "পবিত্র হজের মূল আনুষ্ঠানিকতা ও আরাফাতের ময়দানে সমবেত হওয়ার দিন। তওবা ও ক্ষমার মোবারক দিবস।"
            )
        }
        if (hMonth == "জিলহজ্জ" && hDay == 10) {
            return HolidayInfo(
                name = "ঈদুল আযহা / বকরি ঈদ",
                englishName = "Eid-ul-Adha / Bakrid",
                isPublicHoliday = true,
                category = HolidayCategory.GOVT_HOLIDAY,
                description = "হযরত ইব্রাহিম (আঃ) এর ত্যাগের স্মরণে পালিত কুরবানির ঈদ। ত্যাগ, আনুগত্য ও মানবতার মহিমান্বিত উৎসব। পশ্চিমবঙ্গে সরকারি ছুটি।"
            )
        }
        if (hMonth == "জিলহজ্জ" && hDay == 11) {
            return HolidayInfo(
                name = "ঈদুল আযহা (অতিরিক্ত ছুটি)",
                englishName = "Eid-ul-Adha (Addl Day)",
                isPublicHoliday = true,
                category = HolidayCategory.GOVT_HOLIDAY,
                description = "ঈদুল আযহা উপলক্ষে পশ্চিমবঙ্গ সরকারের অতিরিক্ত ছুটি।"
            )
        }
        if (hMonth == "মহররম" && hDay == 10) {
            return HolidayInfo(
                name = "মহরম / আশুরা",
                englishName = "Muharram / Ashura",
                isPublicHoliday = true,
                category = HolidayCategory.GOVT_HOLIDAY,
                description = "পবিত্র আশুরা। কারবালার ময়দানে হযরত ইমাম হোসেন (রাঃ) এর ত্যাগ ও শাহাদাতের ঐতিহাসিক স্মৃতিবিজড়িত দিন। পশ্চিমবঙ্গে সরকারি ছুটি।"
            )
        }
        if (hMonth == "রবিউল আউয়াল" && hDay == 12) {
            return HolidayInfo(
                name = "ফাতেহা-ই-দোয়াজ দাহম / ঈদে মিলাদুন্নবী",
                englishName = "Eid Milad-un-Nabi",
                isPublicHoliday = true,
                category = HolidayCategory.GOVT_HOLIDAY,
                description = "বিশ্বনবী হযরত মুহাম্মদ (সাঃ) এর পবিত্র জন্ম ও ওফাত দিবস। শান্তি ও ভালোবাসার বাণীতে মুখরিত দিবস। পশ্চিমবঙ্গে সরকারি ছুটি।"
            )
        }
        if (hMonth == "শাবান" && hDay == 15) {
            return HolidayInfo(
                name = "পবিত্র শবে বরাত",
                englishName = "Shab-e-Barat",
                isPublicHoliday = true,
                category = HolidayCategory.GOVT_HOLIDAY,
                description = "পবিত্র লাইলাতুল বরাত বা ক্ষমার রজনী। রাতভর নফল ইবাদত, কুরআন তিলাওয়াত ও প্রার্থনার দিন। পশ্চিমবঙ্গে সরকারি ছুটি থাকে।"
            )
        }
        if (hMonth == "রমজান" && hDay == 1) {
            return HolidayInfo(
                name = "পবিত্র মাহে রমজান শুরু",
                englishName = "First Day of Ramadan",
                isPublicHoliday = false,
                category = HolidayCategory.FESTIVAL,
                description = "সংযম, সিয়াম সাধনা ও রহমতের পবিত্র রমজান মাসের সূচনা।"
            )
        }
        if (hMonth == "রমজান" && hDay == 27) {
            return HolidayInfo(
                name = "পবিত্র শবে কদর / লাইলাতুল কদর",
                englishName = "Shab-e-Qadr",
                isPublicHoliday = false,
                category = HolidayCategory.FESTIVAL,
                description = "হাজার মাসের চেয়েও উত্তম মহিমান্বিত রজনী। এই রাতে পবিত্র কুরআন অবতীর্ণ হয়েছিল।"
            )
        }

        return null
    }

    /**
     * একটি নির্দিষ্ট মাসের সকল দিনের ক্যালেন্ডার ডেটা তৈরি করা
     */
    fun getCalendarDaysForMonth(yearMonth: YearMonth): List<CalendarDayData> {
        val daysInMonth = yearMonth.lengthOfMonth()
        val today = LocalDate.now()
        val result = ArrayList<CalendarDayData>(daysInMonth)

        for (day in 1..daysInMonth) {
            val date = yearMonth.atDay(day)
            val bDate = DateHelper.getWestBengalBengaliDate(date)
            val hDate = DateHelper.getArabicHijriDate(date)
            val holiday = getHolidayForDate(date)

            result.add(
                CalendarDayData(
                    date = date,
                    englishDay = day,
                    bengaliDate = bDate,
                    hijriDate = hDate,
                    holiday = holiday,
                    isSunday = date.dayOfWeek == java.time.DayOfWeek.SUNDAY,
                    isFriday = date.dayOfWeek == java.time.DayOfWeek.FRIDAY,
                    isToday = date == today
                )
            )
        }
        return result
    }

    /**
     * একটি নির্দিষ্ট মাসের সকল ছুটি ও উৎসবের তালিকা
     */
    fun getHolidaysForMonth(yearMonth: YearMonth): List<Pair<LocalDate, HolidayInfo>> {
        val days = getCalendarDaysForMonth(yearMonth)
        return days.filter { it.holiday != null }.map { it.date to it.holiday!! }
    }

    /**
     * পুরো বছরের প্রধান প্রধান পশ্চিমবঙ্গ সরকারি ছুটির তালিকা
     */
    fun getAnnualGovtHolidays(year: Int): List<Pair<LocalDate, HolidayInfo>> {
        val result = mutableListOf<Pair<LocalDate, HolidayInfo>>()
        for (month in 1..12) {
            val ym = YearMonth.of(year, month)
            for (day in 1..ym.lengthOfMonth()) {
                val d = ym.atDay(day)
                val h = getHolidayForDate(d)
                if (h != null && h.isPublicHoliday) {
                    result.add(d to h)
                }
            }
        }
        return result.distinctBy { it.second.name }
    }
}
