package com.example.sunddenfix.retrofit.viewIm;

import com.example.sunddenfix.retrofit.base.IView;
import com.example.sunddenfix.retrofit.model.CountryModel;

/**
 * @author wangchengmeng
 */
public interface MainView extends IView {
    void updateText(CountryModel model);
}
