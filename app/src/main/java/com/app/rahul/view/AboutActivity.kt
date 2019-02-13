package com.app.rahul.view

import android.os.Bundle
import app.rahul.com.kotlinandroidarchitecturecomponent.R
import app.rahul.com.kotlinandroidarchitecturecomponent.databinding.ActivityAboutBinding
import com.app.rahul.baseclass.BaseActivity
import com.app.rahul.viewmodel.AboutUsVM


class AboutActivity : BaseActivity() {

    private lateinit var aboutUsVM: AboutUsVM
    lateinit var mBinding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView(R.layout.activity_about)
    }

    override fun initVariable() {
        aboutUsVM = getViewModel<AboutUsVM>() as AboutUsVM
        mBinding = getBinding() as ActivityAboutBinding
        mBinding.lifecycleOwner = this
        mBinding.aboutUsVM = aboutUsVM
    }

    override fun loadData() {
        aboutUsVM.callAboutUs()
    }


}
