# 🎓 Tuition Batch - Android Native Application

একটি সম্পূর্ণ নেটিভ অ্যান্ডয়েড অ্যাপ্লিকেশন (Kotlin + Jetpack Compose + Material 3 + Room Database), যা প্রাইভেট টিউটর, শিক্ষক এবং কোচিং সেন্টারের জন্য তৈরি করা হয়েছে। 

এই অ্যাপের অন্যতম মূল আকর্ষণ হলো **প্রতিটি পড়ার ঠিক ১৫ মিনিট পূর্বে স্বয়ংক্রিয় নোটিফিকেশন ও অ্যালার্ম সিস্টেম**।

---

## 🌟 প্রধান প্রধান সুবিধাসমূহ (Key Features)

### 🔔 ১. স্মার্ট ১৫-মিনিট নোটিফিকেশন অ্যালার্ম (15-Minute Prior Reminder)
- রুটিন অনুযায়ী প্রতিটি ব্যাচ শুরু হওয়ার ঠিক **১৫ মিনিট আগে** উচ্চ-অগ্রাধিকারের (High Priority) হেডস-আপ নোটিফিকেশন ও ভাইব্রেশন অ্যালার্ট।
- ডিভাইস রিবুট হলেও `BootReceiver` এর মাধ্যমে সমস্ত অ্যালার্ম পুনরায় সক্রিয় হয়ে যায়।
- নোটিফিকেশনে পড়ার বিষয়, সময়, স্থান ও এক ক্লিকে অ্যাপ খোলার বাটন থাকে।

### 👥 ২. টিউশন ব্যাচ ম্যানেজমেন্ট (Batch Management)
- ব্যাচের নাম, বিষয় (Physics, Chemistry, Math, English ইত্যাদি), শ্রেণি/গ্রেড।
- সাপ্তাহিক পড়ার দিনসমূহ (শনি, রবি, সোম, মঙ্গল, বুধ, বৃহস্পতি, শুক্র)।
- ক্লাস শুরুর সময় ও সময়কাল।
- ব্যাচভিত্তিক কালার ট্যাগ ও মাসিক ফি নির্ধারণ।

### 👨‍🎓 ৩. শিক্ষার্থী প্রোফাইল ও অভিভাবক যোগাযোগ (Student & Guardian Directory)
- শিক্ষার্থীর নাম, রোল নম্বর, প্রতিষ্ঠান, ব্যক্তিগত ফোন নম্বর।
- অভিভাবকের নাম ও ফোন নম্বর।
- ১-ক্লিকে শিক্ষার্থী বা অভিভাবককে সরাসরি **ফোন কল** করার সুবিধা।

### 📋 ৪. দৈনিক হাজিরা খাতা (Attendance Register)
- তারিখ অনুযায়ী প্রতিটি ব্যাচের শিক্ষার্থীর উপস্থিতি রেকর্ড:
  - 🟢 উপস্থিত (Present)
  - 🔴 অনুপস্থিত (Absent)
  - 🟡 দেরি (Late)
- 'সবাইকে উপস্থিত করুন' (Mark all present) কুইক বাটন।

### 💰 ৫. বেতন ও ফি হিসাব (Tuition Fees & Accounts)
- চলতি মাসের প্রত্যাশিত ফি বনাম আদায়কৃত ফি ও বকেয়ার রিয়েল-টাইম সামারি।
- শিক্ষার্থীভিত্তিক পেমেন্ট এন্ট্রি (Cash, bKash, Nagad, Bank)।
- রসিদ নোট ও ট্রানজাকশন আইডি সংরক্ষণ।

### 📅 ৬. সাপ্তাহিক রুটিন টাইমটেবিল (Weekly Routine)
- বারের ভিত্তিতে (শনিবার থেকে শুক্রবার) সম্পূর্ণ পড়ার শিডিউল।
- আজকের ক্লাসের দ্রুত তালিকা ও কাউন্টডাউন।

### 📖 ৭. সিলেবাস, পড়া ও বাড়ির কাজ (Class Notes & Homework)
- ক্লাসে যা পড়ানো হলো তা লিখে রাখা।
- বাড়ির কাজ (HW) এবং আসন্ন ক্লাস টেস্টের তারিখ সংরক্ষণ।

---

## 🏗 প্রযুক্তি ও আর্কিটেকচার (Tech Stack)

* **Language:** Kotlin 2.0+
* **UI Toolkit:** Jetpack Compose + Material 3 (Dynamic / Dark & Light Themes)
* **Architecture:** MVVM (Model - View - ViewModel) + Clean Repository Pattern
* **Local Database:** Room Database (SQLite) + TypeConverters + StateFlow
* **Asynchronous Operations:** Kotlin Coroutines + Flow
* **Alarms & Background:** Android `AlarmManager` (Exact Alarms) + `BroadcastReceiver` + `NotificationCompat`

---

## 📁 ফাইল স্ট্রাকচার (Directory Structure)

```
TuitionBatch/
├── app/
│   ├── src/main/
│   │   ├── AndroidManifest.xml
│   │   ├── java/com/tuitionbatch/app/
│   │   │   ├── TuitionBatchApp.kt
│   │   │   ├── MainActivity.kt
│   │   │   ├── data/
│   │   │   │   ├── local/
│   │   │   │   │   ├── AppDatabase.kt
│   │   │   │   │   ├── Converters.kt
│   │   │   │   │   ├── dao/
│   │   │   │   │   │   ├── BatchDao.kt
│   │   │   │   │   │   ├── StudentDao.kt
│   │   │   │   │   │   ├── AttendanceDao.kt
│   │   │   │   │   │   ├── FeeDao.kt
│   │   │   │   │   │   └── NoteDao.kt
│   │   │   │   │   └── entity/
│   │   │   │   │       ├── BatchEntity.kt
│   │   │   │   │       ├── StudentEntity.kt
│   │   │   │   │       ├── AttendanceEntity.kt
│   │   │   │   │       ├── FeeRecordEntity.kt
│   │   │   │   │       └── NoteEntity.kt
│   │   │   │   └── repository/
│   │   │   │       └── TuitionRepository.kt
│   │   │   ├── notification/
│   │   │   │   ├── NotificationHelper.kt
│   │   │   │   └── AlarmScheduler.kt
│   │   │   ├── receiver/
│   │   │   │   ├── TuitionReminderReceiver.kt
│   │   │   │   └── BootReceiver.kt
│   │   │   └── ui/
│   │   │       ├── theme/
│   │   │       │   ├── Color.kt
│   │   │       │   ├── Theme.kt
│   │   │       │   └── Type.kt
│   │   │       ├── components/
│   │   │       │   └── CommonCards.kt
│   │   │       ├── screens/
│   │   │       │   ├── MainScreen.kt
│   │   │       │   ├── DashboardScreen.kt
│   │   │       │   ├── BatchesScreen.kt
│   │   │       │   ├── BatchDetailScreen.kt
│   │   │       │   ├── AddEditBatchDialog.kt
│   │   │       │   ├── StudentsScreen.kt
│   │   │       │   ├── AddEditStudentDialog.kt
│   │   │       │   ├── FeesScreen.kt
│   │   │       │   ├── AddFeeDialog.kt
│   │   │       │   └── RoutineScreen.kt
│   │   │       └── viewmodel/
│   │   │           ├── MainViewModel.kt
│   │   │           └── ViewModelFactory.kt
│   │   └── res/
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
└── gradle/
    └── libs.versions.toml
```

---

## 🚀 কীভাবে রান করবেন (How to Run)

1. অ্যান্ড্রয়েড স্টুডিওতে (Android Studio) এই ফোল্ডারটি ওপেন করুন: `projects/TuitionBatch`
2. প্রোজেক্ট সিঙ্ক (Gradle Sync) স্বয়ংক্রিয়ভাবে হবে।
3. আপনার অ্যান্ড্রয়েড ফোন বা এমুলেটরে রান বাটনে ক্লিক করে ইনস্টল করুন।
