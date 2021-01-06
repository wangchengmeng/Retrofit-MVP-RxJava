package com.example.sunddenfix.retrofit.viewIm;

import com.example.sunddenfix.retrofit.base.IView;
import com.example.sunddenfix.retrofit.bean.CountryModel;

import java.util.List;

/**
 * @author wangchengmeng
 */
public interface MainView extends IView {
    void getCountrySuccess(List<CountryModel> models, int status);

    void refreshOrDisLayout(int status);

    void loadNorMoreData();
}
