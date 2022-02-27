package com.mahfuznow.instagram.ui.base

import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.view.View

abstract class OnSingleDoubleClickListener : View.OnClickListener {
    private var isSingleEvent = false
    private val doubleClickQualificationSpanInMillis: Long = DEFAULT_QUALIFICATION_SPAN
    private var timestampLastClick: Long
    private val handler: Handler
    private val runnable: Runnable
    private var view: View? = null

    override fun onClick(view: View) {
        this.view = view
        if (SystemClock.elapsedRealtime() - timestampLastClick < doubleClickQualificationSpanInMillis) {
            isSingleEvent = false
            handler.removeCallbacks(runnable)
            onDoubleClick(view)
            return
        }
        isSingleEvent = true
        handler.postDelayed(runnable, DEFAULT_QUALIFICATION_SPAN)
        timestampLastClick = SystemClock.elapsedRealtime()
    }

    abstract fun onDoubleClick(view: View)
    abstract fun onSingleClick(view: View)

    companion object {
        private const val DEFAULT_QUALIFICATION_SPAN: Long = 200
    }

    init {
        timestampLastClick = 0
        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            if (isSingleEvent) {
                view?.let { onSingleClick(it) }
            }
        }
    }
}