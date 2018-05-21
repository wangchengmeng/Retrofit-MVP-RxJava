package com.example.sunddenfix.retrofit.viewIm

import com.example.sunddenfix.retrofit.base.IView
import com.example.sunddenfix.retrofit.model.CountryModel

interface MainView : IView {

    fun updateText(model: CountryModel)

    fun updateText2(model: String)
}