package com.`common-android`.android.android

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.commons.android.R


//Crea un dialogo simulando loading de carga
fun Fragment.createLoading(): AlertDialog? {
    return this.activity?.let {
        val builder = AlertDialog.Builder(it)
        builder.setView(R.layout.loading_lay)
        builder.setCancelable(false)
        builder.create().apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
}

//Metodo para navegar entre fragmentos con animacion
fun Fragment.navigateTo(@IdRes navActionResId: Int, bundle: Bundle, popUpTo: Int? = null, inclusive: Boolean = false) {
    try {
        if (popUpTo != null) {
            val navOptions = NavOptions.Builder()
                    .setEnterAnim(R.anim.add_view_slide_in)
                    .setExitAnim(R.anim.add_view_fade_out)
                    .setPopEnterAnim(R.anim.pop_view_slide_in)
                    .setPopExitAnim(R.anim.pop_view_fade_out)
                    .setPopUpTo(popUpTo, inclusive)
                    .build()

            findNavController().navigate(navActionResId, bundle, navOptions)
        } else {
            findNavController().navigate(navActionResId, bundle)
        }
    } catch (ignored: Exception) {
        // Navigation library has a bug which crash if same navActionResId is passed too quickly so we implemented this try / catch
    }
}

//Escondo teclado desde vista
fun View.hideKeyboard(context: Context) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(applicationWindowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
}

//Escondo teclado sin contexto
fun FragmentActivity.hideKeyboard() {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}

fun View.removeKeyboardOnTouch(fragment: FragmentActivity?, callback: (() -> Unit)? = null) {
    setOnTouchListener { _, _ ->
        fragment?.hideKeyboard()
        callback?.invoke()
        false
    }
}