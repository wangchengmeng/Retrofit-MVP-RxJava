package com.example.sunddenfix.retrofit.utils.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class OkHttpGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {

    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
//        val httpClient = OkHttpClient().newBuilder()
//            .sslSocketFactory(HtcHostnameVerifier.getSSLSocketFactory())
//            .hostnameVerifier(HtcHostnameVerifier.getHostnameVerifier())
//            .build()
//        registry.replace(
//            GlideUrl::class.java,
//            InputStream::class.java,
//            OkHttpUrlLoader.Factory(httpClient)
//        )
    }
}
