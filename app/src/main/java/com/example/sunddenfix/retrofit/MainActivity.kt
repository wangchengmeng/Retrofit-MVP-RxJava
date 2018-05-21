package com.example.sunddenfix.retrofit

import android.os.Bundle
import android.os.PersistableBundle
import com.example.sunddenfix.retrofit.R
import com.example.sunddenfix.retrofit.base.BaseActivity
import com.example.sunddenfix.retrofit.model.CountryModel
import com.example.sunddenfix.retrofit.presenter.MainPresenter
import com.example.sunddenfix.retrofit.viewIm.MainView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainPresenter>(), MainView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mIPresenter.getCountry2("21.22.11.33")
    }

    override fun updateText(model: CountryModel) {
        tvResult.text = model.country
        tvFlowableResult.text = model.area
    }

    override fun updateText2(model: String) {
        tvResult.text = model
    }

    override fun getPresenter(): MainPresenter = MainPresenter(this, this)

    override fun getResourceId(): Int = R.layout.activity_main
}