package com.frame.arc.basic.scope

import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.multidex.MultiDexApplication

open class BaseApplication(override val viewModelStore: ViewModelStore = ViewModelStore()) :
    MultiDexApplication(), ViewModelStoreOwner {

    /*private var mAppViewModelStore: ViewModelStore? = null*/

    companion object {
        private val sInstance: BaseApplication by lazy { BaseApplication() }
        fun getInstance(): BaseApplication = sInstance
    }

    /*override fun getViewModelStore(): ViewModelStore {
        if (mAppViewModelStore == null) {
            mAppViewModelStore = ViewModelStore()
        }
        return mAppViewModelStore!!
    }*/

}