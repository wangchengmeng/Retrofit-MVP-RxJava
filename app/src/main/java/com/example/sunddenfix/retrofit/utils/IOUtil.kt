package com.example.sunddenfix.retrofit.utils

import java.io.Closeable

class IOUtil {
    companion object {
        fun closeStream(stream: Closeable) {
            try {
                stream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}