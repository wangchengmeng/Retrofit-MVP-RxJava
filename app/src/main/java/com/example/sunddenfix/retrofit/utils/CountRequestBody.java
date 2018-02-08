package com.example.sunddenfix.retrofit.utils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by wangchengm
 * on 2018/2/8.
 * <p>
 * 实现上传带进度的RequestBody
 */

public class CountRequestBody extends RequestBody {

    private RequestBody mDelegate;//代理  内部主要还是依靠这个代理对象处理
    private Listener mListener;

    public CountRequestBody(RequestBody delegate, Listener listener) {
        mDelegate = delegate;
        mListener = listener;
    }

    @Override
    public MediaType contentType() {
        return mDelegate.contentType();
    }

    @Override
    public long contentLength() {
        try {
            return mDelegate.contentLength();
        } catch (IOException e) {
            return -1;
        }
    }

    @Override
    public void writeTo(BufferedSink sink) {
        try {
            CountSink countSink = new CountSink(sink);
            BufferedSink bufferedSink = Okio.buffer(countSink);
            mDelegate.writeTo(bufferedSink);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected interface Listener {
        void onProgress(long byteCount, long totalCount);
    }

    private class CountSink extends ForwardingSink {

        private long bytesWrite;//记录已经上传的字节数

        public CountSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            bytesWrite += byteCount;
            mListener.onProgress(bytesWrite, contentLength());
        }
    }
}
