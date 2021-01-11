package com.github.shwaka.kococo.example

import com.github.shwaka.kococo.debugOnly

fun main() {
    debugOnly {
        println("This should be shown only in debug mode!")
    }
}
