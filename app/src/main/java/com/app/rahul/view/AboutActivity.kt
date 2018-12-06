package com.app.rahul.view

import android.os.Bundle
import androidx.lifecycle.Observer
import app.rahul.com.kotlinandroidarchitecturecomponent.R
import app.rahul.com.kotlinandroidarchitecturecomponent.databinding.ActivityAboutBinding
import com.app.rahul.baseclass.BaseActivity
import com.app.rahul.utility.Utils
import com.app.rahul.viewmodel.AboutUsVM


class AboutActivity : BaseActivity() {

    lateinit var aboutUsVM: AboutUsVM
    lateinit var mBinding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView(R.layout.activity_about)
    }

    override fun initVariable() {
        aboutUsVM = getViewModel<AboutUsVM>() as AboutUsVM
        mBinding = getBinding() as ActivityAboutBinding
    }

    override fun loadData() {
        aboutUsVM.callAboutUs()
        aboutUsVM.getAboutUsModel().observe(this, Observer { aboutUsModel ->
            mBinding.txtContent.text = Utils.fromHtml(aboutUsModel?.content.toString())
        })
    }

    override fun apiError(error: String) {
        mBinding.txtContent.text = error
    }


}
