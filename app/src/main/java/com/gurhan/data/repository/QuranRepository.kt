package com.gurhan.data.repository

import com.gurhan.data.model.Surah

class QuranRepository {
    
    fun getAllSurahs(): List<Surah> {
        return listOf(
            Surah(1, "Fatiha", "الفاتحة", "Mekge", 7),
            Surah(2, "Bakara", "البقرة", "Medine", 286),
            Surah(3, "Ali Imran", "آل عمران", "Medine", 200),
            Surah(4, "Nisa", "النساء", "Medine", 176),
            Surah(5, "Maide", "المائدة", "Medine", 120),
            Surah(6, "En'am", "الأنعام", "Mekge", 165),
            Surah(7, "A'raf", "الأعراف", "Mekge", 206),
            Surah(8, "Enfal", "الأنفال", "Medine", 75),
            Surah(9, "Töwbe", "التوبة", "Medine", 129),
            Surah(10, "Ýunus", "يونس", "Mekge", 109),
            Surah(11, "Hud", "هود", "Mekge", 123),
            Surah(12, "Ýusup", "يوسف", "Mekge", 111),
            Surah(13, "Ra'd", "الرعد", "Medine", 43),
            Surah(14, "Ibrahim", "ابراهيم", "Mekge", 52),
            Surah(15, "Hijr", "الحجر", "Mekge", 99),
            Surah(16, "Nahl", "النحل", "Mekge", 128),
            Surah(17, "Isra", "الإسراء", "Mekge", 111),
            Surah(18, "Kehf", "الكهف", "Mekge", 110),
            Surah(19, "Merýem", "مريم", "Mekge", 98),
            Surah(20, "Taha", "طه", "Mekge", 135),
            Surah(21, "Enbiýa", "الأنبياء", "Mekge", 112),
            Surah(22, "Haj", "الحج", "Medine", 78),
            Surah(23, "Mu'minun", "المؤمنون", "Mekge", 118),
            Surah(24, "Nur", "النور", "Medine", 64),
            Surah(25, "Furkan", "الفرقان", "Mekge", 77),
            Surah(26, "Şuara", "الشعراء", "Mekge", 227),
            Surah(27, "Neml", "النمل", "Mekge", 93),
            Surah(28, "Kasas", "القصص", "Mekge", 88),
            Surah(29, "Ankebut", "العنكبوت", "Mekge", 69),
            Surah(30, "Rum", "الروم", "Mekge", 60),
            Surah(31, "Lokman", "لقمان", "Mekge", 34),
            Surah(32, "Sejde", "السجدة", "Mekge", 30),
            Surah(33, "Ahzab", "الأحزاب", "Medine", 73),
            Surah(34, "Sebe", "سبأ", "Mekge", 54),
            Surah(35, "Fatyr", "فاطر", "Mekge", 45),
            Surah(36, "Ýasin", "يس", "Mekge", 83),
            Surah(37, "Saffat", "الصافات", "Mekge", 182),
            Surah(38, "Sad", "ص", "Mekge", 88),
            Surah(39, "Zumer", "الزمر", "Mekge", 75),
            Surah(40, "Mü'min", "غافر", "Mekge", 85),
            Surah(41, "Fussilet", "فصلت", "Mekge", 54),
            Surah(42, "Şura", "الشورى", "Mekge", 53),
            Surah(43, "Zuhruf", "الزخرف", "Mekge", 89),
            Surah(44, "Duhan", "الدخان", "Mekge", 59),
            Surah(45, "Jasiýe", "الجاثية", "Mekge", 37),
            Surah(46, "Ahkaf", "الأحقاف", "Mekge", 35),
            Surah(47, "Muhammed", "محمد", "Medine", 38),
            Surah(48, "Feth", "الفتح", "Medine", 29),
            Surah(49, "Hujurat", "الحجرات", "Medine", 18),
            Surah(50, "Kaf", "ق", "Mekge", 45),
            Surah(51, "Zariýat", "الذاريات", "Mekge", 60),
            Surah(52, "Tur", "الطور", "Mekge", 49),
            Surah(53, "Nejm", "النجم", "Mekge", 62),
            Surah(54, "Kamer", "القمر", "Mekge", 55),
            Surah(55, "Rahman", "الرحمن", "Medine", 78),
            Surah(56, "Waka", "الواقعة", "Mekge", 96),
            Surah(57, "Hadid", "الحديد", "Medine", 29),
            Surah(58, "Mujadele", "المجادلة", "Medine", 22),
            Surah(59, "Haşr", "الحشر", "Medine", 24),
            Surah(60, "Mumtehine", "الممتحنة", "Medine", 13),
            Surah(61, "Saff", "الصف", "Medine", 14),
            Surah(62, "Juma", "الجمعة", "Medine", 11),
            Surah(63, "Munafikun", "المنافقون", "Medine", 11),
            Surah(64, "Tegabun", "التغابن", "Medine", 18),
            Surah(65, "Talak", "الطلاق", "Medine", 12),
            Surah(66, "Tahrim", "التحريم", "Medine", 12),
            Surah(67, "Mülk", "الملك", "Mekge", 30),
            Surah(68, "Kalem", "القلم", "Mekge", 52),
            Surah(69, "Hakka", "الحاقة", "Mekge", 52),
            Surah(70, "Mearij", "المعارج", "Mekge", 44),
            Surah(71, "Nuh", "نوح", "Mekge", 28),
            Surah(72, "Jin", "الجن", "Mekge", 28),
            Surah(73, "Muzzemmil", "المزمل", "Mekge", 20),
            Surah(74, "Mussessir", "المدثر", "Mekge", 56),
            Surah(75, "Kyýame", "القيامة", "Mekge", 40),
            Surah(76, "Insan", "الإنسان", "Medine", 31),
            Surah(77, "Murselat", "المرسلات", "Mekge", 50),
            Surah(78, "Nebe", "النبأ", "Mekge", 40),
            Surah(79, "Naziat", "النازعات", "Mekge", 46),
            Surah(80, "Abese", "عبس", "Mekge", 42),
            Surah(81, "Tekwir", "التكوير", "Mekge", 29),
            Surah(82, "Infitar", "الإنفطار", "Mekge", 19),
            Surah(83, "Mutaffifin", "المطففين", "Mekge", 36),
            Surah(84, "Inşikak", "الإنشقاق", "Mekge", 25),
            Surah(85, "Buruj", "البروج", "Mekge", 22),
            Surah(86, "Taryk", "الطارق", "Mekge", 17),
            Surah(87, "A'la", "الأعلى", "Mekge", 19),
            Surah(88, "Gaşiýe", "الغاشية", "Mekge", 26),
            Surah(89, "Fejr", "الفجر", "Mekge", 30),
            Surah(90, "Beled", "البلد", "Mekge", 20),
            Surah(91, "Şems", "الشمس", "Mekge", 15),
            Surah(92, "Leýl", "الليل", "Mekge", 21),
            Surah(93, "Duha", "الضحى", "Mekge", 11),
            Surah(94, "Inşirah", "الشرح", "Mekge", 8),
            Surah(95, "Tin", "التين", "Mekge", 8),
            Surah(96, "Alak", "العلق", "Mekge", 19),
            Surah(97, "Kadyr", "القدر", "Mekge", 5),
            Surah(98, "Beýýine", "البينة", "Medine", 8),
            Surah(99, "Zilzal", "الزلزلة", "Medine", 8),
            Surah(100, "Adiyat", "العاديات", "Mekge", 11),
            Surah(101, "Karia", "القارعة", "Mekge", 11),
            Surah(102, "Tekasur", "التكاثر", "Mekge", 8),
            Surah(103, "Asr", "العصر", "Mekge", 3),
            Surah(104, "Hümeze", "الهمزة", "Mekge", 9),
            Surah(105, "Fil", "الفيل", "Mekge", 5),
            Surah(106, "Kureýş", "قريش", "Mekge", 4),
            Surah(107, "Ma'un", "الماعون", "Mekge", 7),
            Surah(108, "Kewser", "الكوثر", "Mekge", 3),
            Surah(109, "Kafirun", "الكافرون", "Mekge", 6),
            Surah(110, "Nasr", "النصر", "Medine", 3),
            Surah(111, "Tebbet", "المسد", "Mekge", 5),
            Surah(112, "Yhlas", "الإخلاص", "Mekge", 4),
            Surah(113, "Felek", "الفلق", "Mekge", 5),
            Surah(114, "Nas", "الناس", "Mekge", 6)
        )
    }
    
    fun getSurahById(id: Int): Surah? {
        return getAllSurahs().find { it.id == id }
    }
    
    fun getVersesBySurahId(surahId: Int): List<com.gurhan.data.model.Verse> {
        // Sample verse - would be loaded from actual data
        return listOf(
            com.gurhan.data.model.Verse(
                surahId = surahId,
                verseNumber = 1,
                arabicText = "بِسْمِ ٱللَّهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ",
                turkmenTranslation = "Rahman we Rahym bolan Allahyň ady bilen."
            )
        )
    }
    
    fun searchSurahs(query: String): List<Surah> {
        return getAllSurahs().filter { 
            it.name.contains(query, ignoreCase = true) ||
            it.arabicName.contains(query) ||
            it.id.toString().contains(query)
        }
    }
}
