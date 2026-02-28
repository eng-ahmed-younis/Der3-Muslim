package com.der3.model

enum class DaysSelector(val shortName: String) {
    SATURDAY("س"),
    SUNDAY("أ"),
    MONDAY("ن"),
    TUESDAY("ث"),
    WEDNESDAY("ر"),
    THURSDAY("خ"),
    FRIDAY("ج");

    override fun toString(): String = shortName
}
