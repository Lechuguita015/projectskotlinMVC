package com.example.myapplication.utils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

abstract class BaseActivity <B>: AppCompatActivity(){
    val binding: B by lazy {
        DataBindingUtil.setContentView(this, getLayout()) as B
    }
    abstract fun getLayout(): Int
    open fun initDependency() {}
    open fun initObservers() {}
    abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDependency()
        initObservers()
        initView()
    }
    protected fun getMovies2(){

    }
}