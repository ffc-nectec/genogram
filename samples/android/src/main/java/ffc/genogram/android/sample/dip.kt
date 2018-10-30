package ffc.genogram.android.sample

import android.content.Context
import android.util.TypedValue

internal fun Context.dip(dipValue: Int): Int =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue.toFloat(), resources.displayMetrics).toInt()
