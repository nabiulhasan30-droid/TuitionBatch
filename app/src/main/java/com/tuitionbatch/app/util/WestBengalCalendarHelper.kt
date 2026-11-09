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
    val icon: String = "",
    val originAndHistory: String = "",
    val significance: String = "",
    val celebrationStyle: String = "",
    val academicStatus: String = "",
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

    fun getHolidayIconByName(name: String, engName: String = ""): String {
        val n = (name + " " + engName).lowercase()
        return when {
            n.contains("সরস্বতী") || n.contains("saraswati") -> "🪕"
            n.contains("দুর্গা") || n.contains("durga") || n.contains("মহালয়া") || n.contains("mahalaya") || n.contains("দশমী") || n.contains("বিজয়া") -> "🔱"
            n.contains("কালী") || n.contains("দীপাবলি") || n.contains("diwali") || n.contains("kali") -> "🪔"
            n.contains("ঈদ") || n.contains("eid") || n.contains("রমজান") || n.contains("ramadan") || n.contains("আশুরা") || n.contains("মুহররম") || n.contains("muharram") || n.contains("মিলাদ") || n.contains("শবে বরাত") || n.contains("আরাফাত") -> "🕌"
            n.contains("ক্রিসমাস") || n.contains("christmas") || n.contains("বড়দিন") -> "🎄"
            n.contains("গুড ফ্রাইডে") || n.contains("good friday") || n.contains("easter") -> "✝️"
            n.contains("স্বাধীনতা") || n.contains("independence") || n.contains("প্রজাতন্ত্র") || n.contains("republic") -> "🇮🇳"
            n.contains("নেতাজী") || n.contains("সুভাষ") || n.contains("গান্ধী") || n.contains("বিবেকানন্দ") || n.contains("বিরসা") -> "🎖️"
            n.contains("রবীন্দ্র") || n.contains("নজরুল") || n.contains("ঠাকুর") -> "✍️"
            n.contains("দোল") || n.contains("হোলি") || n.contains("holi") -> "🎨"
            n.contains("বৈশাখ") || n.contains("baisakh") || n.contains("নববর্ষ") || n.contains("সংক্রান্তি") -> "🌾"
            n.contains("বুদ্ধ") || n.contains("buddha") || n.contains("মহাবীর") || n.contains("mahavir") -> "☸️"
            n.contains("রথ") || n.contains("rath") || n.contains("জগন্নাথ") -> "🚩"
            n.contains("শিব") || n.contains("shiv") -> "🔱"
            n.contains("লক্ষ্মী") || n.contains("lakshmi") -> "🪙"
            n.contains("ছট") || n.contains("chhath") -> "☀️"
            n.contains("রাখী") || n.contains("rakhi") || n.contains("ভাইফোঁটা") || n.contains("bhai phota") -> "🏵️"
            n.contains("শিক্ষক") || n.contains("শিশু") || n.contains("বই") || n.contains("শিক্ষা") -> "📚"
            n.contains("শ্রমিক") || n.contains("মে দিবস") || n.contains("may day") -> "⚒️"
            n.contains("গুরু নানক") || n.contains("guru nanak") -> "🕯️"
            n.contains("বিশ্বকর্মা") || n.contains("vishwakarma") -> "⚙️"
            n.contains("নতুন বছর") || n.contains("new year") -> "🎉"
            else -> "🎉"
        }
    }

    fun getHolidayNoticeText(holiday: HolidayInfo, date: LocalDate): String {
        val bDate = DateHelper.getWestBengalBengaliDate(date)
        val engDateStr = DateHelper.getEnglishFormattedDate(date)
        val dayOfWeekBengali = when (date.dayOfWeek) {
            java.time.DayOfWeek.SATURDAY -> "শনিবার"
            java.time.DayOfWeek.SUNDAY -> "রবিবার"
            java.time.DayOfWeek.MONDAY -> "সোমবার"
            java.time.DayOfWeek.TUESDAY -> "মঙ্গলবার"
            java.time.DayOfWeek.WEDNESDAY -> "বুধবার"
            java.time.DayOfWeek.THURSDAY -> "বৃহস্পতিবার"
            java.time.DayOfWeek.FRIDAY -> "শুক্রবার"
        }
        return """
📢 *টিউশন ব্যাচ নোটিশ - ছুটির ঘোষণা* 🎓
----------------------------------------
প্রিয় শিক্ষার্থী ও সম্মানিত অভিভাবকবৃন্দ,
আনন্দের সাথে অবগত করা যাচ্ছে যে আগামী *${engDateStr} (${dayOfWeekBengali})* [${bDate.formatShort()}], পবিত্র ও উৎসবের দিন *"${holiday.name}"* উপলক্ষে আমাদের সকল টিউশন ব্যাচের ক্লাস স্থগিত থাকবে।

📌 *ছুটির তাৎপর্য ও বিবরণ:*
${holiday.description}

সকলকে উৎসবের আন্তরিক শুভেচ্ছা ও অভিনন্দন! 💐
- *Tuition Batch*
        """.trimIndent()
    }

    private fun addH(
        year: Int, month: Int, day: Int,
        name: String, engName: String, isPublic: Boolean,
        cat: HolidayCategory, desc: String,
        icon: String = "",
        origin: String = "",
        significance: String = "",
        traditions: String = "",
        academic: String = ""
    ) {
        val date = LocalDate.of(year, month, day)
        val assignedIcon = icon.ifBlank { getHolidayIconByName(name, engName) }
        val academicText = academic.ifBlank {
            if (isPublic) "পশ্চিমবঙ্গ সরকারের এন.আই. অ্যাক্ট অনুসারে স্কুল, কলেজ, বিশ্ববিদ্যালয় ও সকল সরকারি প্রতিষ্ঠান পূর্ণ দিবস ছুটি থাকবে। টিউশন ব্যাচ সাধারণত স্থগিত থাকে।"
            else "বিদ্যালয় বা অফিস নিয়মিত চালু থাকবে, তবে বিশেষ সমাবেশ বা শ্রদ্ধাঞ্জলি অনুষ্ঠিত হতে পারে।"
        }
        movableHolidaysMap[date] = HolidayInfo(
            name = name,
            englishName = engName,
            isPublicHoliday = isPublic,
            category = cat,
            description = desc,
            icon = assignedIcon,
            originAndHistory = origin.ifBlank { desc },
            significance = significance.ifBlank { desc },
            celebrationStyle = traditions,
            academicStatus = academicText,
            traditions = traditions
        )
    }

    private fun makeHoliday(
        name: String,
        englishName: String,
        isPublicHoliday: Boolean,
        category: HolidayCategory,
        description: String,
        icon: String = "",
        origin: String = "",
        significance: String = "",
        celebration: String = "",
        academic: String = ""
    ): HolidayInfo {
        val assignedIcon = icon.ifBlank { getHolidayIconByName(name, englishName) }
        val academicText = academic.ifBlank {
            if (isPublicHoliday) "পশ্চিমবঙ্গ সরকারের এন.আই. অ্যাক্ট অনুসারে স্কুল, কলেজ, বিশ্ববিদ্যালয় ও সকল সরকারি প্রতিষ্ঠান পূর্ণ দিবস ছুটি থাকবে। টিউশন ব্যাচ সাধারণত বন্ধ থাকে।"
            else "বিদ্যালয় বা টিউশন ব্যাচ নিয়মিত চালু থাকবে, তবে বিশেষ সম্মান প্রদর্শন বা আলোচনা অনুষ্ঠিত হতে পারে।"
        }
        return HolidayInfo(
            name = name,
            englishName = englishName,
            isPublicHoliday = isPublicHoliday,
            category = category,
            description = description,
            icon = assignedIcon,
            originAndHistory = origin.ifBlank { description },
            significance = significance.ifBlank { description },
            celebrationStyle = celebration,
            academicStatus = academicText,
            traditions = celebration
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
                if (day == 1) return makeHoliday(
                    name = "ইংরেজি নববর্ষ",
                    englishName = "New Year's Day",
                    isPublicHoliday = true,
                    category = HolidayCategory.GOVT_HOLIDAY,
                    description = "গ্রেগরীয় ক্যালেন্ডারের নতুন বছরের প্রথম দিন। পশ্চিমবঙ্গে সর্বস্তরে উৎসব ও রাজ্য সরকারের নির্ধারিত ছুটি।",
                    icon = "🎉",
                    origin = "রোমান সম্রাট জুলিয়াস সিজার খ্রিস্টপূর্ব ৪৬ অব্দে ১লা জানুয়ারিকে নতুন বছরের সূচনা হিসেবে প্রবর্তন করেছিলেন। পরবর্তীতে ১৫৮২ সালে পোপ ত্রয়োদশ গ্রেগরি গ্রেগরীয় বর্ষপঞ্জিতে এটি বহাল রাখেন।",
                    significance = "অতীতের ভুলভ্রান্তি ও গ্লানি মুছে নতুন আশা, আকাঙ্ক্ষা ও সংকল্প নিয়ে জীবন শুরু করার আন্তর্জাতিক মিলনমেলা।",
                    celebration = "পরিবার-বন্ধুদের সাথে শুভেচ্ছা বিনিময়, বনভোজন (পিকনিক), দর্শনীয় স্থানে ভ্রমণ ও মধ্যরাতে আতশবাজি প্রদর্শন।"
                )
                if (day == 12) return makeHoliday(
                    name = "স্বামী বিবেকানন্দ জন্মজয়ন্তী",
                    englishName = "Swami Vivekananda Jayanti",
                    isPublicHoliday = true,
                    category = HolidayCategory.GOVT_HOLIDAY,
                    description = "যুগনায়ক স্বামী বিবেকানন্দের জন্মজয়ন্তী ও জাতীয় যুব দিবস। যুবসমাজকে দেশসেবা ও আত্মবিশ্বাসের অনুপ্রেরণায় উদ্দীপ্ত করার দিন।",
                    icon = "🎖️",
                    origin = "১৮৬৩ সালের ১২ই জানুয়ারি কলকাতার সিমলায় জন্ম নেন বীর সন্ন্যাসী নরেন্দ্রনাথ দত্ত (স্বামী বিবেকানন্দ)। ১৮৯৩ সালে শিকাগো বিশ্ব ধর্মমহাসভায় তাঁর ঐতিহাসিক ভাষণে সনাতন দর্শনের বিশ্বজয় ঘটে।",
                    significance = "তিনি যুবসমাজকে আহ্বান জানিয়েছিলেন—'ওঠো, জাগো এবং লক্ষ্য না পৌঁছানো পর্যন্ত থেমো না'। তাঁর জন্মবার্ষিকী ১৯৮৫ সাল থেকে জাতীয় যুব দিবস হিসেবে উদযাপিত।",
                    celebration = "পশ্চিমবঙ্গ জুড়ে প্রভাতফেরি, রামকৃষ্ণ মিশন ও শিক্ষাপ্রতিষ্ঠানসমূহে বিশেষ প্রার্থনা, ক্রীড়া প্রতিযোগিতা ও যুব সম্মেলন।"
                )
                if (day == 23) return makeHoliday(
                    name = "নেতাজী সুভাষচন্দ্র বসু জন্মজয়ন্তী",
                    englishName = "Netaji Subhas Chandra Bose Jayanti",
                    isPublicHoliday = true,
                    category = HolidayCategory.GOVT_HOLIDAY,
                    description = "দেশনায়ক নেতাজী সুভাষচন্দ্র বসুর পবিত্র জন্মজয়ন্তী (পরাক্রম দিবস)। ভারতমাতার মুক্তি সংগ্রামের মহানায়কের প্রতি বিনম্র শ্রদ্ধা।",
                    icon = "🇮🇳",
                    origin = "১৮৯৭ সালের ২৩শে জানুয়ারি কটকে জন্মগ্রহণ করেন নেতাজী সুভাষচন্দ্র বসু। তিনি আজাদ হিন্দ ফৌজ গঠন করে ব্রিটিশ সাম্রাজ্যবাদের বিরুদ্ধে প্রত্যক্ষ সশস্ত্র বিপ্লব পরিচালনা করেন।",
                    significance = "'তোমরা আমাকে রক্ত দাও, আমি তোমাদের স্বাধীনতা দেব'—তাঁর এই রণহুঙ্কার ভারতীয় স্বাধীনতা সংগ্রামকে চূড়ান্ত পরিণতির দিকে নিয়ে যায়। ভারত সরকার দিনটিকে 'পরাক্রম দিবস' হিসেবে ঘোষণা করেছে।",
                    celebration = "রাজ্যব্যাপী জাতীয় পতাকা উত্তোলন, সাইরেন বাদন, কুচকাওয়াজ, নেতাজীর প্রতিকৃতিতে মাল্যদান ও রক্তদান শিবির।"
                )
                if (day == 26) return makeHoliday(
                    name = "প্রজাতন্ত্র দিবস",
                    englishName = "Republic Day",
                    isPublicHoliday = true,
                    category = HolidayCategory.GOVT_HOLIDAY,
                    description = "ভারতের প্রজাতন্ত্র দিবস। ১৯৫০ সালের ২৬শে জানুয়ারি ভারতীয় সংবিধান কার্যকর হয়েছিল। জাতীয় ও রাজ্য সরকারি ছুটি।",
                    icon = "🇮🇳",
                    origin = "১৯৫০ সালের ২৬শে জানুয়ারি ভারতীয় গণপরিষদ কর্তৃক গৃহীত সংবিধান আনুষ্ঠানিকভাবে কার্যকর হয় এবং ভারত একটি সার্বভৌম গণতান্ত্রিক প্রজাতন্ত্র হিসেবে আত্মপ্রকাশ করে।",
                    significance = "বিশ্বের সর্ববৃহৎ গণতন্ত্রের ভিত্তি রচিত হয়েছিল এই দিনে। প্রতিটি নাগরিকের মৌলিক অধিকার, সমতা ও সার্বভৌমত্বের প্রতীক হলো ভারতের সংবিধান।",
                    celebration = "দিল্লির কর্তব্য পথে এবং কলকাতার রেড রোডে বর্ণাঢ্য সামরিক কুচকাওয়াজ ও সাংস্কৃতিক পদযাত্রা। সকল স্কুল-কলেজে জাতীয় পতাকা উত্তোলন ও দেশাত্মবোধক গান।"
                )
            }
            2 -> {
                if (day == 21) return makeHoliday(
                    name = "আন্তর্জাতিক মাতৃভাষা দিবস",
                    englishName = "International Mother Language Day",
                    isPublicHoliday = false,
                    category = HolidayCategory.OBSERVANCE,
                    description = "মাতৃভাষার মর্যাদা রক্ষায় আত্মত্যাগের স্মরণে বিশ্বব্যাপী আন্তর্জাতিক মাতৃভাষা ও অমর একুশে পালন।",
                    icon = "🗣️",
                    origin = "১৯৫২ সালের ২১শে ফেব্রুয়ারি মাতৃভাষা বাংলার অধিকার রক্ষায় শহীদ হয়েছিলেন সালাম, বরকত, রফিক, জব্বার প্রমুখ। ১৯৯৯ সালে ইউনেস্কো দিনটিকে আন্তর্জাতিক মাতৃভাষা দিবস হিসেবে স্বীকৃতি দেয়।",
                    significance = "ভাষাগত বৈচিত্র্য সংরক্ষণ এবং মাতৃভাষার প্রতি সম্মান প্রদর্শনের জন্য এটি বিশ্বব্যাপী একটি অনন্য প্রেরণা।",
                    celebration = "প্রভাতফেরি, শহীদ মিনারে পুষ্পস্তবক অর্পণ, সাহিত্য সভা, কবিতা পাঠ ও বাংলা ভাষার উৎকর্ষ বিষয়ক আলোচনা।"
                )
            }
            5 -> {
                if (day == 1) return makeHoliday(
                    name = "মে দিবস / আন্তর্জাতিক শ্রমিক দিবস",
                    englishName = "May Day / International Workers' Day",
                    isPublicHoliday = true,
                    category = HolidayCategory.GOVT_HOLIDAY,
                    description = "শ্রমজীবী মানুষের অধিকার ও আত্মত্যাগের স্মরণে বিশ্ব শ্রমিক দিবস। পশ্চিমবঙ্গে সার্বিক সরকারি ছুটি।",
                    icon = "⚒️",
                    origin = "১৮৮৬ সালের ১লা মে যুক্তরাষ্ট্রের শিকাগোর হে মার্কেটে ৮ ঘণ্টা কাজের দাবির আন্দোলনে পুলিশের গুলিতে শ্রমিকদের আত্মত্যাগের স্মরণে এ দিবস উদযাপিত হয়।",
                    significance = "শ্রমের মর্যাদা, ন্যায্য অধিকার এবং বিশ্বজুড়ে মেহনতি মানুষের ঐক্য ও সংহতির প্রতীক।",
                    celebration = "শ্রমিক সংগঠনসমূহের শোভাযাত্রা, লাল পতাকা মিছিল, সেমিনার ও সাংস্কৃতিক অনুষ্ঠান।"
                )
                if (day == 9) return makeHoliday(
                    name = "রবীন্দ্র জয়ন্তী",
                    englishName = "Rabindra Jayanti",
                    isPublicHoliday = true,
                    category = HolidayCategory.GOVT_HOLIDAY,
                    description = "বিশ্বকবি রবীন্দ্রনাথ ঠাকুরের জন্মজয়ন্তী (২৫শে বৈশাখ)। পশ্চিমবঙ্গ জুড়ে গান, কবিতা ও নাটকের মাধ্যমে শ্রদ্ধা জ্ঞাপন ও সরকারি ছুটি।",
                    icon = "✍️",
                    origin = "১২৬৮ বঙ্গাব্দের ২৫শে বৈশাখ (১৮৬১ সালের ৭ই মে) কলকাতার জোড়াসাঁকোর ঠাকুরবাড়িতে জন্ম নেন এশিয়ার প্রথম নোবেলজয়ী সাহিত্যিক রবীন্দ্রনাথ ঠাকুর।",
                    significance = "বাঙালির মনন, আবেগ, দর্শন ও সাহিত্য-সংস্কৃতির প্রাণপুরুষ হলেন রবীন্দ্রনাথ। তাঁর সৃষ্টিকর্ম বিশ্বসাহিত্যের অমূল্য সম্পদ।",
                    celebration = "জোড়াসাঁকো ঠাকুরবাড়ি ও শান্তিনিকেতনে রবীন্দ্রসঙ্গীত, নৃত্যনাট্য, কবিতা পাঠের মাধ্যমে দিবসটি উদযাপিত হয়।"
                )
            }
            8 -> {
                if (day == 15) return makeHoliday(
                    name = "ভারতের স্বাধীনতা দিবস",
                    englishName = "Independence Day",
                    isPublicHoliday = true,
                    category = HolidayCategory.GOVT_HOLIDAY,
                    description = "১৯৪৭ সালের ১৫ই আগস্ট ভারত ব্রিটিশ শাসন থেকে স্বাধীনতা লাভ করে। জাতীয় ও পশ্চিমবঙ্গে পূর্ণ সরকারি ছুটি।",
                    icon = "🇮🇳",
                    origin = "প্রায় ২০০ বছরের রক্তক্ষয়ী সংগ্রাম ও অগণিত শহীদের বলিদানের পর ১৯৪৭ সালের ১৫ই আগস্ট মধ্যরাতে ভারত ব্রিটিশ পরাধীনতা থেকে মুক্তি লাভ করে।",
                    significance = "পরাধীনতার শৃঙ্খল ভেঙে দেশাত্মবোধ ও জাতীয় ঐক্যের নবপ্রভাতের প্রতীক।",
                    celebration = "প্রধানমন্ত্রী কর্তৃক দিল্লির লালকেল্লায় এবং মুখ্যমন্ত্রী কর্তৃক কলকাতায় জাতীয় পতাকা উত্তোলন, মিষ্টি বিতরণ ও দেশপ্রেমের গান।"
                )
            }
            9 -> {
                if (day == 5) return makeHoliday(
                    name = "শিক্ষক দিবস",
                    englishName = "Teachers' Day",
                    isPublicHoliday = false,
                    category = HolidayCategory.OBSERVANCE,
                    description = "ড. সর্বপল্লী রাধাকৃষ্ণনের জন্মজয়ন্তী উপলক্ষে শিক্ষকদের প্রতি শ্রদ্ধা ও ভালোবাসা নিবেদনের পবিত্র দিবস।",
                    icon = "📚",
                    origin = "ভারতের দ্বিতীয় রাষ্ট্রপতি, বিশিষ্ট দার্শনিক ও শিক্ষাবিদ ড. সর্বপল্লী রাধাকৃষ্ণনের জন্মদিন (৫ই সেপ্টেম্বর, ১৮৮৮) স্মরণে ১৯৬২ সাল থেকে শিক্ষক দিবস পালিত হয়।",
                    significance = "সমাজ বিনির্মাণে এবং ভবিষ্যৎ প্রজন্ম গড়ে তোলায় শিক্ষক ও গুরুজনদের অপরিসীম অবদানকে শ্রদ্ধা জানানো।",
                    celebration = "ছাত্রছাত্রীদের দ্বারা শিক্ষকদের উপহার ও পুষ্পস্তবক প্রদান, শিক্ষামূলক নাটক ও সাংস্কৃতিক অনুষ্ঠান।"
                )
                if (day == 17) return makeHoliday(
                    name = "বিশ্বকর্মা পূজা",
                    englishName = "Vishwakarma Puja",
                    isPublicHoliday = false,
                    category = HolidayCategory.FESTIVAL,
                    description = "দেবশিল্পী ভগবান বিশ্বকর্মার আরাধনা। কলকারখানা, যানবাহন ও নির্মাণকাজে বিশেষ পূজা ও ঘুড়ি ওড়ানোর উৎসব।",
                    icon = "⚙️",
                    origin = "হিন্দু পৌরাণিক বিশ্বাস অনুসারে দেবশিল্পী বিশ্বকর্মা হলেন স্বর্গপুরী, অস্ত্রশস্ত্র এবং সকল কারিগরি বিদ্যার রূপকার।",
                    significance = "যন্ত্রপাতির পবিত্রতা রক্ষা এবং শ্রম ও কারিগরি উৎকর্ষতার প্রতি ভক্তি নিবেদন।",
                    celebration = "কলকারখানায় পূজা অর্চনা, প্রসাদ বিতরণ এবং আকাশজুড়ে রঙিন ঘুড়ির লড়াই।"
                )
            }
            10 -> {
                if (day == 2) return makeHoliday(
                    name = "মহাত্মা গান্ধী জয়ন্তী",
                    englishName = "Mahatma Gandhi Jayanti",
                    isPublicHoliday = true,
                    category = HolidayCategory.GOVT_HOLIDAY,
                    description = "জাতির জনক মহাত্মা গান্ধীর জন্মজয়ন্তী ও আন্তর্জাতিক অহিংসা দিবস। জাতীয় ও সরকারি ছুটি।",
                    icon = "🎖️",
                    origin = "১৮৬৯ সালের ২রা অক্টোবর গুজরাটের পোরবন্দরে জন্ম নেন মোহনদাস করমচাঁদ গান্ধী। তিনি সত্যাগ্রহ ও অহিংস আন্দোলনের মাধ্যমে বিশ্বকে পথ দেখিয়েছিলেন।",
                    significance = "অহিংসা, সত্য এবং আত্মমর্যাদার প্রতীক। জাতিসংঘ দিনটিকে 'আন্তর্জাতিক অহিংস দিবস' হিসেবে ঘোষণা করেছে।",
                    celebration = "দিল্লির রাজঘাটে এবং পশ্চিমবঙ্গের গান্ধী ঘাটে প্রার্থনা সভা, চরকা কাটা ও রামধুন সঙ্গীত পরিবেশন।"
                )
            }
            11 -> {
                if (day == 14) return makeHoliday(
                    name = "শিশু দিবস",
                    englishName = "Children's Day",
                    isPublicHoliday = false,
                    category = HolidayCategory.OBSERVANCE,
                    description = "পণ্ডিত জওহরলাল নেহরুর জন্মজয়ন্তী উপলক্ষে শিশুদের অধিকার ও আনন্দ উদযাপনের দিন।",
                    icon = "🎈",
                    origin = "স্বাধীন ভারতের প্রথম প্রধানমন্ত্রী পণ্ডিত জওহরলাল নেহরুর জন্মদিন (১৪ই নভেম্বর, ১৮৮৯)। শিশুরা তাঁকে পরম শ্রদ্ধায় 'চাচা নেহরু' বলে ডাকত।",
                    significance = "শিশুদের শিক্ষা, পুষ্টি, নিরাপত্তা এবং স্নেহপূর্ণ লালনপালনের প্রতি সমাজের দায়বদ্ধতা স্মরণ করিয়ে দেওয়া।",
                    celebration = "স্কুলে শিশুদের জন্য ক্রীড়া প্রতিযোগিতা, কুইজ, নাটক ও মিষ্টি বিতরণ।"
                )
                if (day == 15) return makeHoliday(
                    name = "বিরসা মুন্ডা জয়ন্তী",
                    englishName = "Birsa Munda Jayanti",
                    isPublicHoliday = true,
                    category = HolidayCategory.GOVT_HOLIDAY,
                    description = "উপজাতি স্বাধীনতা সংগ্রামী ধরতি আবা ভগবান বিরসা মুন্ডার পবিত্র জন্মজয়ন্তী। পশ্চিমবঙ্গে সরকারি ছুটি।",
                    icon = "🏹",
                    origin = "১৮৭৫ সালের ১৫ই নভেম্বর জন্মগ্রহণ করেন আদিবাসী মুক্তি আন্দোলনের মহানায়ক বিরসা মুন্ডা। তিনি ব্রিটিশ শাসন ও জমিদারদের শোষণের বিরুদ্ধে 'উলগুলান' (বিদ্রোহ) পরিচালনা করেছিলেন।",
                    significance = "আদিবাসী সম্প্রদায়ের স্বাধিকার, জল-জঙ্গল-জমিনের অধিকার রক্ষা ও ভারতীয় স্বাধীনতা সংগ্রামের অনুপ্রেরণা।",
                    celebration = "আদিবাসী সংস্কৃতি উৎসব, তীরন্দাজি প্রদর্শনী ও সরকারি শ্রদ্ধা নিবেদন।"
                )
            }
            12 -> {
                if (day == 25) return makeHoliday(
                    name = "বড়দিন / ক্রিসমাস ডে",
                    englishName = "Christmas Day",
                    isPublicHoliday = true,
                    category = HolidayCategory.GOVT_HOLIDAY,
                    description = "প্রভু যিশুখ্রিস্টের শুভ জন্মতিথি। সম্প্রীতি, শান্তি ও ক্ষমার মহাবাণী। পশ্চিমবঙ্গে আলোকসজ্জায় সাজানো হয় এবং সরকারি ছুটি থাকে।",
                    icon = "🎄",
                    origin = "বেথলেহেমের এক গোয়ালঘরে কুমারী মেরির কোলে জন্মগ্রহণ করেছিলেন বিশ্বের মানবজাতির ত্রাণকর্তা প্রভু যিশুখ্রিস্ট।",
                    significance = "প্রেম, ক্ষমা, শান্তি এবং সৌভ্রাতৃত্বের বাণী ছড়িয়ে দেওয়া।",
                    celebration = "কলকাতার পার্ক স্ট্রিটে আলোকসজ্জা, সেন্ট পলস ক্যাথেড্রালে মধ্যরাতের বিশেষ প্রার্থনা, ক্যারল গান, কেক খাওয়া ও সান্তা ক্লজের উপহার বিতরণ।"
                )
                if (day == 31) return makeHoliday(
                    name = "বর্ষবিদায় রজনী",
                    englishName = "New Year's Eve",
                    isPublicHoliday = false,
                    category = HolidayCategory.OBSERVANCE,
                    description = "বিদায়ী বছরের শেষ দিন। আনন্দ ও শুভ কামনায় নতুন বছরকে বরণের প্রস্তুতি।",
                    icon = "✨",
                    origin = "রোমান বর্ষপঞ্জির ঐতিহ্য অনুযায়ী বছরের শেষ সূর্যাস্ত ও সমাপনী রজনী।",
                    significance = "বিদায়ী বছরের স্মৃতি রোমন্থন ও আগত বছরের জন্য শুভেচ্ছা বিনিময়।",
                    celebration = "পারিবারিক মিলনমেলা, সান্ধ্যকালীন আড্ডা ও কাউন্টডাউন পার্টি।"
                )
            }
        }

        // 3. Bengali Solar Calendar Holidays (পশ্চিমবঙ্গ নিয়ম)
        val bDate = DateHelper.getWestBengalBengaliDate(date)
        if (bDate.monthName == "বৈশাখ" && bDate.day == 1) {
            return makeHoliday(
                name = "পয়লা বৈশাখ / শুভ বাংলা নববর্ষ",
                englishName = "Poila Baisakh / Bengali New Year",
                isPublicHoliday = true,
                category = HolidayCategory.GOVT_HOLIDAY,
                description = "বাঙালির প্রধান জাতীয় ও সার্বজনীন উৎসব। নতুন খাতা উন্মোচন, ব্যবসায় হালখাতা ও মিষ্টিমুখের মাধ্যমে পশ্চিমবঙ্গে মহাসমারোহে উদ্‌যাপিত।",
                icon = "🌾",
                origin = "মোঘল সম্রাট আকবর কর আদায়ের সুবিধার জন্য ১৫৮৪ খ্রিস্টাব্দে সৌর ও চান্দ্র বর্ষপঞ্জির সমন্বয়ে ফসলি সন প্রবর্তন করেন, যা পরবর্তীতে বঙ্গাব্দে রূপ নেয়।",
                significance = "বাঙালি জাতিসত্তা, কৃষ্টি ও সমৃদ্ধির মিলনমেলা। ব্যবসায়ীরা নতুন হালখাতা খোলেন এবং পুরনো বকেয়া চুকিয়ে নতুন উদ্যমে যাত্রা শুরু করেন।",
                celebration = "নতুন পোশাক পরিধান, প্রভাতী মঙ্গল শোভাযাত্রা, হালখাতা ও ক্রেতাদের মিষ্টিমুখ করানো, ইলিশ-খিচুড়ি ও সাংস্কৃতিক অনুষ্ঠান।"
            )
        }
        if (bDate.monthName == "চৈত্র" && (bDate.day >= 30 || (date.monthValue == 4 && date.dayOfMonth in 13..14))) {
            return makeHoliday(
                name = "চৈত্র সংক্রান্তি ও চড়ক পূজা",
                englishName = "Chaitra Sankranti",
                isPublicHoliday = true,
                category = HolidayCategory.GOVT_HOLIDAY,
                description = "বাংলা বছরের শেষ দিন। নীলপূজা ও গ্রামীণ চড়ক মেলার লোকঐতিহ্য। পশ্চিমবঙ্গে রাজ্য সরকারের ছুটির দিন।",
                icon = "🎪",
                origin = "সূর্যের মীন রাশি থেকে মেষ রাশিতে প্রবেশের দিনটি সংক্রান্তি হিসেবে পালিত হয়। বাংলার লোকায়ত সংস্কৃতিতে শিবের আরাধনায় গাজন ও চড়ক মেলার প্রাচীন ঐতিহ্য।",
                significance = "বছরের সকল অমঙ্গল দূর করে নতুন জীবনের শুভ কামনায় ব্রত পালন।",
                celebration = "চড়ক গাছের ঘূর্ণন, নীলপূজায় শিবের বিয়ে ও গ্রামীণ মেলা।"
            )
        }
        if (bDate.monthName == "বৈশাখ" && bDate.day == 25 && date.dayOfMonth != 9) {
            return makeHoliday(
                name = "রবীন্দ্র জয়ন্তী (২৫শে বৈশাখ)",
                englishName = "Rabindra Jayanti",
                isPublicHoliday = true,
                category = HolidayCategory.GOVT_HOLIDAY,
                description = "বঙ্গাব্দ ২৫শে বৈশাখ বিশ্বকবি রবীন্দ্রনাথ ঠাকুরের পবিত্র আবির্ভাব তিথি। পশ্চিমবঙ্গে সাংস্কৃতিক শ্রদ্ধাঞ্জলি ও ছুটি।",
                icon = "✍️",
                origin = "১২৬৮ বঙ্গাব্দের ২৫শে বৈশাখ কলকাতার জোড়াসাঁকোর ঠাকুরবাড়িতে বিশ্বকবির জন্ম।",
                significance = "বাঙালির প্রাণের ঠাকুর রবীন্দ্রনাথের সৃষ্টিকর্মের প্রতি সার্বজনীন শ্রদ্ধা।",
                celebration = "প্রভাতফেরি, রবীন্দ্রসঙ্গীতানুষ্ঠান ও সাংস্কৃতিক মহোৎসব।"
            )
        }
        if (bDate.monthName == "জ্যৈষ্ঠ" && bDate.day == 11) {
            return makeHoliday(
                name = "কাজী নজরুল ইসলাম জয়ন্তী",
                englishName = "Kazi Nazrul Islam Jayanti",
                isPublicHoliday = false,
                category = HolidayCategory.OBSERVANCE,
                description = "বিদ্রোহী কবি কাজী নজরুল ইসলামের পবিত্র জন্মজয়ন্তী (১১ই জ্যৈষ্ঠ)। সাম্য, প্রেম ও বিদ্রোহের কবির স্মরণে শ্রদ্ধাঞ্জলি।",
                icon = "🎺",
                origin = "১৩০৬ বঙ্গাব্দের ১১ই জ্যৈষ্ঠ (১৮৯৯ সালের ২৪শে মে) বর্ধমানের চুরুলিয়া গ্রামে জন্ম নেন সাম্য ও প্রেমের কবি নজরুল।",
                significance = "অন্যায়, অবিচার ও সাম্প্রদায়িকতার বিরুদ্ধে তাঁর সৃষ্টিশীল অগ্নিবাণী যুগ যুগ ধরে অনুপ্রেরণা জোগায়।",
                celebration = "নজরুলগীতি সম্মেলন, কবিতা আবৃত্তি ও বিশেষ স্মরণসভা।"
            )
        }

        // 4. Islamic Hijri Calendar Holidays (Perpetually computed via HijrahDate)
        val hDate = DateHelper.getArabicHijriDate(date)
        val hMonth = hDate.monthName
        val hDay = hDate.day

        if (hMonth == "শাওয়াল" && hDay == 1) {
            return makeHoliday(
                name = "ঈদুল ফিতর",
                englishName = "Eid-ul-Fitr",
                isPublicHoliday = true,
                category = HolidayCategory.GOVT_HOLIDAY,
                description = "ইসলামের প্রধান ধর্মীয় উৎসব। এক মাস সিয়াম সাধনা শেষে শাওয়াল মাসের চাঁদের উদয়ে ঈদ উৎসব। পশ্চিমবঙ্গে পূর্ণ সরকারি ছুটি।",
                icon = "🕌",
                origin = "হিজরি দ্বিতীয় বর্ষে মহানবী হযরত মুহাম্মদ (সাঃ) মদিনায় প্রথম ঈদুল ফিতর উদযাপনের রীতি প্রবর্তন করেন। রমজানের পবিত্র সিয়াম সাধনার পরিসমাপ্তিতে আল্লাহ তায়ালার পক্ষ থেকে এটি মুমিনদের জন্য বিশেষ উপহার।",
                significance = "গরিব-অসহায়দের ফিতরা বা দান প্রদানের মাধ্যমে ধনী-দরিদ্রের ভেদাভেদ ভুলে সাম্য ও সৌহার্দ্য প্রতিষ্ঠার দিন।",
                celebration = "ঈদগাহে বিশাল জামাত, পরস্পর কোলাকুলি, আত্মীয়দের বাড়ি যাওয়া, সেমাই-লাচ্ছা ও সুস্বাদু খাবারের আপ্যায়ন।"
            )
        }
        if (hMonth == "শাওয়াল" && hDay == 2) {
            return makeHoliday(
                name = "ঈদুল ফিতর (অতিরিক্ত ছুটি)",
                englishName = "Eid-ul-Fitr (Addl Day)",
                isPublicHoliday = true,
                category = HolidayCategory.GOVT_HOLIDAY,
                description = "ঈদুল ফিতর পরবর্তী উৎসব ও আত্মীয়স্বজনের সাথে আনন্দ ভাগাভাগির জন্য পশ্চিমবঙ্গ রাজ্য সরকারের অতিরিক্ত ছুটি।",
                icon = "🌙",
                origin = "ঈদের আনন্দকে দীর্ঘায়িত করতে এবং পরিবারের সাথে সময় কাটাতে রাজ্য সরকার নির্ধারিত সরকারি ছুটি।",
                significance = "পারিবারিক বন্ধন সুদৃঢ়করণ এবং সম্প্রীতি রক্ষা।",
                celebration = "পারিবারিক আড্ডা ও ভ্রমণ।"
            )
        }
        if (hMonth == "জিলহজ্জ" && hDay == 9) {
            return makeHoliday(
                name = "পবিত্র আরাফাত দিবস / হজ",
                englishName = "Day of Arafah",
                isPublicHoliday = false,
                category = HolidayCategory.FESTIVAL,
                description = "পবিত্র হজের মূল আনুষ্ঠানিকতা ও আরাফাতের ময়দানে সমবেত হওয়ার দিন। তওবা ও ক্ষমার মোবারক দিবস।",
                icon = "🕋",
                origin = "হযরত ইব্রাহিম (আঃ) ও হযরত মুহাম্মদ (সাঃ) এর স্মৃতিবিজড়িত আরাফাতের ময়দানে দাঁড়িয়ে শেষ নবী বিদায় হজের ঐতিহাসিক ভাষণ দিয়েছিলেন।",
                significance = "বিশ্ব মুসলিমের ঐক্যের প্রতীক ও গুনাহ মাফের শ্রেষ্ঠতম দিন। এ দিনে সিয়াম পালন অত্যন্ত পুণ্যময়।",
                celebration = "নফল রোজা রাখা, ইস্তিগফার ও বিশ্বশান্তির জন্য বিশেষ দোয়া।"
            )
        }
        if (hMonth == "জিলহজ্জ" && hDay == 10) {
            return makeHoliday(
                name = "ঈদুল আযহা / বকরি ঈদ",
                englishName = "Eid-ul-Adha / Bakrid",
                isPublicHoliday = true,
                category = HolidayCategory.GOVT_HOLIDAY,
                description = "হযরত ইব্রাহিম (আঃ) এর ত্যাগের স্মরণে পালিত কুরবানির ঈদ। ত্যাগ, আনুগত্য ও মানবতার মহিমান্বিত উৎসব। পশ্চিমবঙ্গে সরকারি ছুটি।",
                icon = "🐑",
                origin = "আল্লাহর সন্তুষ্টির উদ্দেশ্যে হযরত ইব্রাহিম (আঃ) স্বীয় পুত্র হযরত ইসমাইল (আঃ) কে উৎসর্গ করতে গিয়েছিলেন। তাঁর এই পরম আনুগত্য ও ত্যাগের স্মরণে বিশ্বজুড়ে পশু কুরবানি দেওয়া হয়।",
                significance = "নিজের অহংকার ও পশুত্বকে বিসর্জন দিয়ে আল্লাহর প্রতি পূর্ণ সমর্পণ ও গরিবদের মাঝে গোশত বিতরণের মাধ্যমে মানবিকতা প্রদর্শন।",
                celebration = "ঈদের জামাত, পশু কুরবানি, কুরবানির গোশত তিন ভাগে ভাগ করে আত্মীয় ও দুস্থদের মাঝে বণ্টন।"
            )
        }
        if (hMonth == "জিলহজ্জ" && hDay == 11) {
            return makeHoliday(
                name = "ঈদুল আযহা (অতিরিক্ত ছুটি)",
                englishName = "Eid-ul-Adha (Addl Day)",
                isPublicHoliday = true,
                category = HolidayCategory.GOVT_HOLIDAY,
                description = "ঈদুল আযহা উপলক্ষে পশ্চিমবঙ্গ সরকারের অতিরিক্ত ছুটি।",
                icon = "🌙",
                origin = "ঈদুল আযহার আনুষ্ঠানিকতা নির্বিঘ্নে সম্পন্ন করার সুবিধার্থে অতিরিক্ত ছুটি।",
                significance = "কুরবানির গোশত বিতরণ ও আত্মীয়তার হক আদায়।",
                celebration = "আত্মীয়স্বজনের সাথে মিলনমেলা।"
            )
        }
        if (hMonth == "মহররম" && hDay == 10) {
            return makeHoliday(
                name = "মহরম / আশুরা",
                englishName = "Muharram / Ashura",
                isPublicHoliday = true,
                category = HolidayCategory.GOVT_HOLIDAY,
                description = "পবিত্র আশুরা। কারবালার ময়দানে হযরত ইমাম হোসেন (রাঃ) এর ত্যাগ ও শাহাদাতের ঐতিহাসিক স্মৃতিবিজড়িত দিন। পশ্চিমবঙ্গে সরকারি ছুটি।",
                icon = "🏴",
                origin = "৬১ হিজরির ১০ই মহররম সত্য ও ন্যায়ের পক্ষে অন্যায়ের বিরুদ্ধে লড়াই করে কারবালার প্রান্তরে শহীদ হন মহানবীর প্রিয় দৌহিত্র হযরত ইমাম হোসেন (রাঃ)।",
                significance = "অন্যায়, জুলুম ও স্বৈরাচারের বিরুদ্ধে রুখে দাঁড়ানো এবং ত্যাগের মহিমায় উদ্বুদ্ধ হওয়া।",
                celebration = "নফল রোজা রাখা, তাজিয়া মিছিল, মার্সিয়া পাঠ ও আলোচনা সভা।"
            )
        }
        if (hMonth == "রবিউল আউয়াল" && hDay == 12) {
            return makeHoliday(
                name = "ফাতেহা-ই-দোয়াজ দাহম / ঈদে মিলাদুন্নবী",
                englishName = "Eid Milad-un-Nabi",
                isPublicHoliday = true,
                category = HolidayCategory.GOVT_HOLIDAY,
                description = "বিশ্বনবী হযরত মুহাম্মদ (সাঃ) এর পবিত্র জন্ম ও ওফাত দিবস। শান্তি ও ভালোবাসার বাণীতে মুখরিত দিবস। পশ্চিমবঙ্গে সরকারি ছুটি।",
                icon = "🕌",
                origin = "৫৭০ খ্রিস্টাব্দের ১২ই রবিউল আউয়াল মক্কার কুরাইশ বংশে রহমাতুল্লিল আলামিন হযরত মুহাম্মদ (সাঃ) পৃথিবীতে শুভাগমন করেন এবং ৬৩ বছর বয়সে একই দিনে ওফাত লাভ করেন।",
                significance = "বিশ্বমানবতার মুক্তিদূত শেষ নবীর চরিত্র, আদর্শ ও শিক্ষার পুনরুজ্জীবন।",
                celebration = "জশনে জুলুস (শান্তিপূর্ণ শোভাযাত্রা), মিলাদ মাহফিল, দরূদ পাঠ ও তবারক বিতরণ।"
            )
        }
        if (hMonth == "শাবান" && hDay == 15) {
            return makeHoliday(
                name = "পবিত্র শবে বরাত",
                englishName = "Shab-e-Barat",
                isPublicHoliday = true,
                category = HolidayCategory.GOVT_HOLIDAY,
                description = "পবিত্র লাইলাতুল বরাত বা ক্ষমার রজনী। রাতভর নফল ইবাদত, কুরআন তিলাওয়াত ও প্রার্থনার দিন। পশ্চিমবঙ্গে সরকারি ছুটি থাকে।",
                icon = "✨",
                origin = "হাদিস অনুসারে শাবান মাসের মধ্যবর্তী রাতে মহান আল্লাহ বান্দাদের দিকে করুণার দৃষ্টি দেন এবং বহু মানুষকে ক্ষমা প্রদর্শন করেন।",
                significance = "তওবা, আত্মশুদ্ধি এবং ভবিষ্যৎ সৌভাগ্যের জন্য আল্লাহর দরবারে মোনাজাত।",
                celebration = "মসজিদে রাতভর নফল নামাজ, কবর জিয়ারত, হালুয়া-রুটি বিতরণ ও পরদিন নফল রোজা রাখা।"
            )
        }
        if (hMonth == "রমজান" && hDay == 1) {
            return makeHoliday(
                name = "পবিত্র মাহে রমজান শুরু",
                englishName = "First Day of Ramadan",
                isPublicHoliday = false,
                category = HolidayCategory.FESTIVAL,
                description = "সংযম, সিয়াম সাধনা ও রহমতের পবিত্র রমজান মাসের সূচনা।",
                icon = "🌙",
                origin = "ইসলামের তৃতীয় স্তম্ভ রোজা। রমজান মাসেই মানবজাতির হেদায়েতের জন্য পবিত্র আল কুরআন নাজিল হয়।",
                significance = "তাকওয়া বা খোদাভীতি অর্জন, ক্ষুধার্তের কষ্ট উপলব্ধি এবং আত্মসংযম শিক্ষা।",
                celebration = "সেহরি খাওয়া, তারাবিহ নামাজ আদায় ও কুরআন তিলাওয়াত।"
            )
        }
        if (hMonth == "রমজান" && hDay == 27) {
            return makeHoliday(
                name = "পবিত্র শবে কদর / লাইলাতুল কদর",
                englishName = "Shab-e-Qadr",
                isPublicHoliday = false,
                category = HolidayCategory.FESTIVAL,
                description = "হাজার মাসের চেয়েও উত্তম মহিমান্বিত রজনী। এই রাতে পবিত্র কুরআন অবতীর্ণ হয়েছিল।",
                icon = "📖",
                origin = "পবিত্র কুরআনের সূরা আল-কদরে বলা হয়েছে—'লাইলাতুল কদর হাজার মাস অপেক্ষাও উত্তম'। এ রাতে ফেরেশতাগণ শান্তি ও বরকত নিয়ে পৃথিবীতে অবতরণ করেন।",
                significance = "জীবনের সমস্ত পাপ মোচনের এক অনন্য মহিমান্বিত সুযোগ।",
                celebration = "মসজিদে রাতভর তাহাজ্জুদ, কুরআন পাঠ, জিকির ও কান্নাকাটি করে ক্ষমা প্রার্থনা।"
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
