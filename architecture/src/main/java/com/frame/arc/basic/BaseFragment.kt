package com.frame.arc.basic

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.frame.arc.basic.scope.ViewModelScopeProvider

abstract class BaseFragment<VB : ViewBinding>(private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB) :
    Fragment() {
    private var _binding: VB? = null
    protected val mViewBinding: VB get() = _binding!!
    protected val TAG: String by lazy { this.javaClass.simpleName }
    private val viewModelScopeProvider: ViewModelScopeProvider by lazy { ViewModelScopeProvider() }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = bindingInflater(inflater, container, false)
        val root: View = mViewBinding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initData(arguments)
        addOnBackPress()
    }

    protected open fun initViews() {}
    protected open fun initData(arguments: Bundle?) {}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun <T : ViewModel> getViewModel(modelClass: Class<T>): T {
        return viewModelScopeProvider.getFragmentScopeViewModel(this, modelClass)
    }

    protected fun <T : ViewModel> getActivityViewModel(modelClass: Class<T>): T {
        return viewModelScopeProvider.getActivityScopeViewModel(requireActivity(), modelClass)
    }

    protected fun <T : ViewModel> getApplicationViewModel(modelClass: Class<T>): T {
        return viewModelScopeProvider.getApplicationScopeViewModel(modelClass)
    }

    fun getFragmentNavController(): NavController {
        return NavHostFragment.findNavController(this)
    }

    open fun isAddBackPressedCallback(): Boolean {
        return false
    }

    open fun onBackPress() {}

    private fun addOnBackPress() {
        if (isAddBackPressedCallback()) {
            requireActivity().onBackPressedDispatcher.addCallback(
                viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        onBackPress()
                    }
                })
        }
    }


}