package com.example.sunddenfix.retrofit.utils

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.*
import java.io.IOException

class CountRequestBody() : RequestBody() {

    lateinit var mDelegate: RequestBody
    lateinit var mListener: OnProgressListener

    constructor(delegate: RequestBody, listener: OnProgressListener) : this() {
        this.mDelegate = delegate
        this.mListener = listener
    }


    override fun contentType(): MediaType? {
        return mDelegate.contentType()
    }

    override fun contentLength(): Long {
        return try {
            mDelegate.contentLength()
        } catch (e: Exception) {
            -1
        }
    }

    override fun writeTo(sink: BufferedSink) {
        try {
            val countSink = CountSink(sink)
            val bufferedSink = Okio.buffer(countSink)
            mDelegate.writeTo(bufferedSink)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    interface OnProgressListener {
        fun onProgress(byteCount: Long, totalCount: Long)
    }

    private inner class CountSink(sink: Sink) : ForwardingSink(sink) {
        private var byteWrite: Long = 0//记录已经上传的字节数

        @Throws(IOException::class)
        override fun write(source: Buffer?, byteCount: Long) {
            super.write(source!!, byteCount)
            byteWrite += byteCount
            mListener.onProgress(byteWrite, contentLength())
        }
    }
}