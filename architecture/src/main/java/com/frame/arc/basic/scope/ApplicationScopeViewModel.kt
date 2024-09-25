package com.frame.arc.basic.scope

import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

open class ApplicationScopeViewModel private constructor() : ViewModelStoreOwner {

    private var mAppViewModelStore: ViewModelStore? = null

    companion object {
        private val sInstance: ApplicationScopeViewModel by lazy { ApplicationScopeViewModel() }
        fun getInstance(): ApplicationScopeViewModel = sInstance
    }

    override fun getViewModelStore(): ViewModelStore {
        if (mAppViewModelStore == null) {
            mAppViewModelStore = ViewModelStore()
        }
        return mAppViewModelStore!!
    }

}