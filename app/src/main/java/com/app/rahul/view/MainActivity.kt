package com.app.rahul.view

import android.os.Bundle
import app.rahul.com.kotlinandroidarchitecturecomponent.R
import app.rahul.com.kotlinandroidarchitecturecomponent.databinding.ActivityMainBinding
import com.app.rahul.adapter.UserAdapter
import com.app.rahul.baseclass.BaseActivity
import com.app.rahul.model.ResponseData
import com.app.rahul.model.UserModel
import com.app.rahul.utility.ApiObserver
import com.app.rahul.utility.Utils
import com.app.rahul.viewmodel.HomeVM

/**
 * Created by Rahul Sadhu on 13/2/19.
 */

class MainActivity : BaseActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private val userAdapter by lazy {
        UserAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView(R.layout.activity_main)
    }

    override fun initVariable() {
        mBinding = getBinding() as ActivityMainBinding
        mBinding.lifecycleOwner = this
        mBinding.homeVM = getViewModel<HomeVM>() as HomeVM
        mBinding.rvUser.adapter = userAdapter
    }


    override fun loadData() {
        mBinding.homeVM?.callUserList()?.observe(this, object : ApiObserver<ArrayList<UserModel>>() {
            override fun onSuccess(data: ArrayList<UserModel>) {
                userAdapter.updateList(data)
            }

            override fun onError(data: ResponseData<ArrayList<UserModel>>) {
                Utils.showToast(this@MainActivity, data.message.toString())
            }

        })
    }
}
