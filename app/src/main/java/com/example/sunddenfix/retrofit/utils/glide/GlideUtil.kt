package com.example.sunddenfix.retrofit.utils.glide

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.sunddenfix.retrofit.R
import com.example.sunddenfix.retrofit.config.Constant

/**
 * @Author wangchengm
 * @desc Glide加载网络图片
 */
class GlideUtil {

    companion object {

        fun showGif(context: Context, url: String, imageView: ImageView) {
            GlideApp.with(context)
                .asGif()
                .load(url)
                .placeholder(R.mipmap.bg_default_img)
                .error(R.mipmap.bg_default_img)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .fitCenter()
                .skipMemoryCache(true)
                .into(imageView)
        }

        fun showGif(context: Context, url: Int, imageView: ImageView) {
            GlideApp.with(context)
                .asGif()
                .load(url)
                .placeholder(R.mipmap.bg_default_img)
                .error(R.mipmap.bg_default_img)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .fitCenter()
                .skipMemoryCache(true)
                .into(imageView)
        }

        /**
         * 显示网络图片
         *
         * @param context    可以是 Activity FragmentActivity
         * @param url        网络图片链接
         * @param placeResId 占位图片资源id
         * @param errorId    加再错误的图片资源id
         */
        @JvmOverloads
        fun showNetImage(
            context: Context,
            url: Uri,
            imageView: ImageView,
            isSquare: Boolean = false
        ) {
            val placeResId = if (isSquare) R.mipmap.bg_default_square else R.mipmap.bg_default_img
            GlideApp.with(context)
                .load(url)
                .apply(getRequestOps(placeResId, placeResId))
                .into(imageView)
        }

        /**
         * 显示网络图片 支持gif
         *
         * @param context    可以是 Activity FragmentActivity
         * @param url        网络图片链接
         * @param placeResId 占位图片资源id
         * @param errorId    加再错误的图片资源id
         * @param isPlaceholder    是否需要加载占位图 图片列表需要
         */
        @JvmOverloads
        fun showNetImage(
            context: Context,
            url: String,
            imageView: ImageView,
            isSquare: Boolean = false,
            isPlaceholder: Boolean = false
        ) {
            val placeResId = if (isSquare) R.mipmap.bg_default_square else R.mipmap.bg_default_img
            val uri = Uri.parse(url)
            if (url.endsWith(Constant.GIF_END_WITH, true)) {
                showGif(context, url, imageView)
            } else {
                GlideApp.with(context)
                    .load(uri)
                    .apply(getRequestOps(placeResId, placeResId, isPlaceholder))
                    .into(imageView)
            }
        }

        /**
         * 显示圆形网络图片
         *
         * @param context    可以是 Activity FragmentActivity
         * @param url        网络图片链接
         * @param placeResId 占位图片资源id
         * @param errorId    加再错误的图片资源id
         */
        @JvmOverloads
        fun showCircleNetImage(
            context: Context,
            url: String,
            imageView: ImageView
        ) {
            val uri = Uri.parse(url)
            GlideApp.with(context)
                .load(uri)
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(imageView)
        }

        /**
         * 显示网络图片 固定宽高
         *
         * @param context 可以是 Activity FragmentActivity
         * @param url     网络图片链接
         * @param width   图片宽
         * @param height  图片高
         */
        fun showFixNetImage(
            context: Context,
            url: String,
            imageView: ImageView,
            width: Int,
            height: Int
        ) {
            GlideApp.with(context)
                .load(url)
                .apply(getFixRequestOps(width, height))
                .into(imageView)
        }

        /**
         * 显示网络图片 固定宽高
         *
         * @param context 可以是 Activity FragmentActivity
         * @param url     网络图片链接
         * @param width   图片宽
         * @param height  图片高
         */
        fun showFixNetImage(
            context: Context,
            url: Uri,
            imageView: ImageView,
            width: Int,
            height: Int
        ) {
            GlideApp.with(context)
                .load(url)
                .apply(getFixRequestOps(width, height))
                .into(imageView)
        }

        /**
         * 显示本地图片
         *
         * @param context 可以是 Activity FragmentActivity
         * @param resId   图片资源id
         */
        fun showNetImage(context: Context, resId: Int, imageView: ImageView) {
            GlideApp.with(context)
                .load(resId)
                .into(imageView)
        }

        @SuppressLint("CheckResult")
        private fun getRequestOps(
            placeResId: Int, errorId: Int,
            isPlaceholder: Boolean = false
        ): RequestOptions {
            val requestOptions = RequestOptions()
            if (isPlaceholder) {
                requestOptions
                    .placeholder(placeResId)
            }
            return requestOptions
                .error(errorId)
                .dontAnimate()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存各个尺寸的图片 加载更快 只是需要更大的磁盘空间
                .skipMemoryCache(true)//跳过内存缓存
        }

        @SuppressLint("CheckResult")
        private fun getFixRequestOps(width: Int, height: Int): RequestOptions {
            val requestOptions = RequestOptions()
            return requestOptions
                //                .placeholder(placeResId)
                //                .error(errorId)
                .dontAnimate()
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存各个尺寸的图片 加载更快 只是需要更大的磁盘空间
                .skipMemoryCache(true)//跳过内存缓存
        }
    }
}