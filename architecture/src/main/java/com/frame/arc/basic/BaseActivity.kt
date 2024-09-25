package com.frame.arc.basic

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.frame.arc.basic.scope.ViewModelScopeProvider

abstract class BaseActivity<VB : ViewBinding>(private val bindingInflater: (LayoutInflater) -> VB) :
    AppCompatActivity() {

    private lateinit var _binding: VB
    protected val mViewBinding: VB get() = _binding
    protected val TAG: String by lazy { this.javaClass.simpleName }
    private val viewModelScopeProvider: ViewModelScopeProvider by lazy { ViewModelScopeProvider() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater(layoutInflater)
        setContentView(_binding.root)
        toInitial(savedInstanceState)
    }

    private fun toInitial(savedInstanceState: Bundle?) {
        initViews()
        initData(savedInstanceState)

    }

    protected open fun initViews() {}
    protected open fun initData(savedInstanceState: Bundle?) {}

    fun <T : ViewModel> getViewModel(modelClass: Class<T>): T {
        return viewModelScopeProvider.getActivityScopeViewModel(this, modelClass)
    }

    protected fun <T : ViewModel> getApplicationViewModel(modelClass: Class<T>): T {
        return viewModelScopeProvider.getApplicationScopeViewModel(modelClass)
    }

}