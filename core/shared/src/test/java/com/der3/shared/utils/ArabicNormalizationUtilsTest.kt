package com.der3.shared.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class ArabicNormalizationUtilsTest {

    @Test
    fun `normalizeArabic removes diacritics correctly`() {
        val input = "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ"
        val expected = "الحمد لله رب العالمين"
        assertEquals(expected, input.normalizeArabic())
    }

    @Test
    fun `normalizeArabic normalizes Alif variants and Ta Marbuta correctly`() {
        val input = "إبراهيم أحمد أستاذ آمنة"
        val expected = "ابراهيم احمد استاذ امنه"
        assertEquals(expected, input.normalizeArabic())
    }

    @Test
    fun `normalizeArabic normalizes Ta Marbuta and Alif Maksura correctly`() {
        val input = "فاطمة صلاة على مصطفى"
        val expected = "فاطمه صلاه علي مصطفي"
        assertEquals(expected, input.normalizeArabic())
    }
}
