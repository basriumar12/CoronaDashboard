package com.basbas.lawanqfid.ui.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

/**
 * rizmaulana 2020-02-24.
 */
abstract class BaseFragment : Fragment() {
    private fun getBaseActivity() = activity as BaseActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeChange()
    }

    abstract fun observeChange()

    fun showProgress() {
        getBaseActivity().showProgress()
    }

    fun hideProgress() {
        getBaseActivity().hideProgress()
    }

    fun showSnackbarMessage(message: String?) {
        getBaseActivity().showSnackbarMessage(message)
    }

    fun showSnackbarError(message: String?) {
        getBaseActivity().showSnackbarError(message)
    }

    fun onUnexpectedError() {
        getBaseActivity().onUnexpectedError()
    }

    fun hideSoftKeyboard() {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        val view = activity?.currentFocus
        if (view != null) {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}