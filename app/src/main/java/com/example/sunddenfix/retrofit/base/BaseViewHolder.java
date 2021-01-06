package com.example.sunddenfix.retrofit.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunddenfix.retrofit.utils.UncheckedUtil;


/**
 * @Author wangchengm
 * @desc Adapter 的BaseHolder
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mCacheView;//缓存pool
    private Context mContext;//上下文对象
    private View mContentView;//列表中的itemView

    public BaseViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        this.mContentView = itemView;
        this.mCacheView = new SparseArray<>();
    }

    public View getContentView() {
        return mContentView;
    }

    /**
     * 获取id对应的View
     *
     * @param viewId View的id
     * @param <T>    泛型
     * @return 返回该id对应的View
     */
    public <T extends View> T retrieveView(@IdRes int viewId) {
        View view = mCacheView.get(viewId);
        if (null == view) {
            //pool中还没有该id对应的View对象，添加进去
            view = mContentView.findViewById(viewId);
            mCacheView.put(viewId, view);
        }
        //如果pool中存在该id对应的View了 就不用去find了
        return UncheckedUtil.cast(view);
    }

    /**
     * 设置文本数据
     *
     * @param viewID viewID
     * @param value  设置的文本值
     */
    public void setText(@IdRes int viewID, String value) {
        TextView textView = retrieveView(viewID);
        if (null != textView) {
            textView.setText(value);
        }
    }

    /**
     * html 5设置TextView的值
     *
     * @param viewId
     * @param source
     * @return
     */
    public BaseViewHolder setHtmlText(int viewId, String source) {
        TextView tv = retrieveView(viewId);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tv.setText(Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY));
        } else {
            tv.setText(Html.fromHtml(source));
        }
        return this;
    }

    /**
     * 设置文本数据
     *
     * @param viewID View的id
     * @param resID  字符串资源的id
     */
    public void setText(@IdRes int viewID, @StringRes int resID) {
        TextView textView = retrieveView(viewID);
        textView.setText(resID);
    }

    /**
     * 设置View的显示和隐藏
     *
     * @param viewID  viewID
     * @param visible 是否隐藏 true 显示-VISIBLE false 隐藏-GONE
     */
    public void setVisible(@IdRes int viewID, boolean visible) {
        View view = retrieveView(viewID);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置图片
     *
     * @param viewID View的id
     * @param resID  图片资源的id
     */
    public void setImageRes(@IdRes int viewID, @DrawableRes int resID) {
        ImageView imageView = retrieveView(viewID);
        imageView.setImageResource(resID);
    }

    /**
     * 设置图片
     *
     * @param viewID View的id
     * @param bitmap 设置的bitmap
     */
    public void setImageBitmap(@IdRes int viewID, Bitmap bitmap) {
        ImageView imageView = retrieveView(viewID);
        imageView.setImageBitmap(bitmap);
    }

    /**
     * 设置背景颜色
     *
     * @param viewID ViewID
     * @param color  颜色资源id
     */
    public void setBackgroundColor(@IdRes int viewID, @ColorRes int color) {
        View view = retrieveView(viewID);
        view.setBackgroundColor(mContext.getResources().getColor(color));
    }

    /**
     * 设置背景
     *
     * @param viewID View的id
     * @param resID  资源的id
     */
    public void setBackgroundRes(@IdRes int viewID, @DrawableRes int resID) {
        View view = retrieveView(viewID);
        view.setBackgroundResource(resID);
    }

    /**
     * 设置字体的颜色
     *
     * @param viewID View的id
     * @param color  颜色资源id
     */
    public void setTextColor(@IdRes int viewID, @ColorRes int color) {
        TextView textView = retrieveView(viewID);
        textView.setTextColor(ContextCompat.getColor(mContext, color));
    }

    /**
     * 设置点击事件
     *
     * @param viewID   View的id
     * @param listener 点击事件
     */
    public void setOnClickListener(@IdRes int viewID, View.OnClickListener listener) {
        View view = retrieveView(viewID);
        view.setOnClickListener(listener);
    }
}
