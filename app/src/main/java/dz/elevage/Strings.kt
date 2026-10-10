package dz.elevage

// Minimal FR/AR string tables with RTL support via Compose LayoutDirection.
object Strings {
    data class S(
        val animals: String, val health: String, val feed: String, val settings: String,
        val addAnimal: String, val tag: String, val species: String, val add: String,
        val addVaccine: String, val vaccine: String, val dueInDays: String,
        val addFeed: String, val qtyKg: String, val priceDzd: String, val total: String,
        val language: String, val offlineNote: String
    )
    val fr = S(
        "Animaux", "Santé", "Alimentation", "Réglages",
        "Ajouter animal", "Identifiant", "Espèce (mouton/vache/chèvre/volaille/dromadaire)", "Ajouter",
        "Ajouter vaccin/soin", "Vaccin / soin", "Rappel (jours)",
        "Ajouter aliment", "Quantité (kg)", "Prix (DZD/kg)", "Total",
        "Langue", "100% hors-ligne • aucun compte • données sur l'appareil"
    )
    val ar = S(
        "الحيوانات", "الصحة", "الأعلاف", "الإعدادات",
        "إضافة حيوان", "الرقم التعريفي", "النوع (غنم/بقر/ماعز/دواجن/إبل)", "إضافة",
        "إضافة لقاح/علاج", "اللقاح / العلاج", "تذكير (أيام)",
        "إضافة علف", "الكمية (كغ)", "السعر (دج/كغ)", "المجموع",
        "اللغة", "يعمل دون إنترنت • بدون حساب • البيانات على الجهاز"
    )
}
