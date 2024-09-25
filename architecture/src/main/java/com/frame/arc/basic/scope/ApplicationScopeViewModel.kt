package com.frame.arc.basic.scope

import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

open class ApplicationScopeViewModel constructor(private val mAppViewModelStore: ViewModelStore = ViewModelStore()) :
    ViewModelStoreOwner {

    companion object {
        private val sInstance: ApplicationScopeViewModel by lazy { ApplicationScopeViewModel() }
        fun getInstance(): ApplicationScopeViewModel = sInstance
    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }

}