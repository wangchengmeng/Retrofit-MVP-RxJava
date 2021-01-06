package com.example.sunddenfix.retrofit.ui.adapter;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.sunddenfix.retrofit.R;
import com.example.sunddenfix.retrofit.base.BaseAdapter;
import com.example.sunddenfix.retrofit.base.BaseViewHolder;
import com.example.sunddenfix.retrofit.bean.CountryModel;

import java.util.List;

/**
 * @author : wangchengm
 * @date : 2021/1/55:32 PM
 * @desc :
 */
public class CountryAdapter extends BaseAdapter<BaseViewHolder, CountryModel> {

    private Context mContext;

    public CountryAdapter(Context context, @NonNull List<CountryModel> datas) {
        super(context, R.layout.item_country, datas);
        mContext = context;
    }

    @Override
    protected BaseViewHolder getViewHolder(View itemView) {
        return new BaseViewHolder(mContext, itemView);
    }

    @Override
    protected void onChildBindViewHolder(BaseViewHolder holder, CountryModel data, int position) {
        holder.setText(R.id.tv_result, data.getArea());
    }
}
