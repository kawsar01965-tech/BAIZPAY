package com.example.data.models

enum class SalaryRank(
    val rankName: String,
    val requiredDirectRefs: Int,
    val monthlySalary: Double,
    val badgeColorHex: Long
) {
    BRONZE("Bronze", 10, 10.0, 0xFFCD7F32),
    SILVER("Silver", 25, 30.0, 0xFFC0C0C0),
    GOLD("Gold", 50, 75.0, 0xFFFFD700),
    PLATINUM("Platinum", 100, 200.0, 0xFFE5E4E2),
    DIAMOND("Diamond", 250, 600.0, 0xFFB9F2FF),
    ELITE("Elite", 500, 1500.0, 0xFFA855F7),
    CROWN("Crown", 1000, 3500.0, 0xFFEC4899),
    ROYAL("Royal", 2500, 10000.0, 0xFF3B82F6),
    LEGEND("Legend", 5000, 25000.0, 0xFF10B981),
    GLOBAL_AMBASSADOR("Global Ambassador", 10000, 50000.0, 0xFFF59E0B);

    companion object {
        fun getRankForReferrals(count: Int): SalaryRank {
            return entries.lastOrNull { count >= it.requiredDirectRefs } ?: BRONZE
        }

        fun getNextRank(current: SalaryRank): SalaryRank? {
            val nextOrdinal = current.ordinal + 1
            return if (nextOrdinal < entries.size) entries[nextOrdinal] else null
        }
    }
}
