package com.der3.utils


/**
 * create a circular or looping range for the numbers in your scroll wheel.
 * it ensures that when you scroll past the last number, it jumps back to the first (and vice versa).
 * */
fun wrap(value: Int, min: Int, max: Int): Int {
    // 1. Calculate how many numbers are in your set (e.g., 1 to 12 has 12 numbers)
    val range = max - min + 1

    // 2. Use the modulo operator (.mod) to find the remainder.
    // 3. Add 'min' back to shift the result into your specific range.
    return ((value - min).mod(range)) + min
}


fun Int.pad2(): String = String.format("%02d", this)
