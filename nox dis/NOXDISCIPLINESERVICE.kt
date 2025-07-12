package com.noxdiscipline.service

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NOXDisciplineService : LifecycleService() {
    private var windowManager: WindowManager? = null
    private var overlayView: FrameLayout? = null
    private val serviceState = MutableLiveData<ServiceState>()
    
    companion object {
        const val ACTION_START_SERVICE = "action_start_service"
        const val ACTION_STOP_SERVICE = "action_stop_service"
        const val USER_LOGIN = "noxdiscipline"  // Add user login here
        const val SERVICE_START_TIME = "2025-07-12 03:10:29"  // Add current UTC time here
        private const val TAG = "NOXDisciplineService"
    }

    enum class ServiceState {
        RUNNING,
        STOPPED
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "Service created by $USER_LOGIN at $SERVICE_START_TIME")
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        setupOverlay()
        serviceState.value = ServiceState.RUNNING
    }

    private fun setupOverlay() {
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or 
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP
        }

        overlayView = FrameLayout(this).apply {
            LayoutInflater.from(this@NOXDisciplineService)
                .inflate(R.layout.overlay_layout, this, true)
        }

        // Update overlay with user and time information
        overlayView?.let {
            val statusText = it.findViewById<TextView>(R.id.statusText)
            statusText.text = "Service Active - Started by $USER_LOGIN\nLast Update: $SERVICE_START_TIME"
        }

        windowManager?.addView(overlayView, params)
    }

    private fun startOverlayService() {
        Log.i(TAG, "Service starting by $USER_LOGIN at $SERVICE_START_TIME")
        if (overlayView == null) {
            setupOverlay()
        }
        serviceState.value = ServiceState.RUNNING
    }

    private fun stopOverlayService() {
        Log.i(TAG, "Service stopping by $USER_LOGIN at $SERVICE_START_TIME")
        overlayView?.let {
            windowManager?.removeView(it)
            overlayView = null
        }
        serviceState.value = ServiceState.STOPPED
        stopSelf()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Log.i(TAG, "Service command received by $USER_LOGIN at $SERVICE_START_TIME")
        when (intent?.action) {
            ACTION_START_SERVICE -> startOverlayService()
            ACTION_STOP_SERVICE -> stopOverlayService()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        Log.i(TAG, "Service destroyed by $USER_LOGIN at $SERVICE_START_TIME")
        stopOverlayService()
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.i(TAG, "Service bound by $USER_LOGIN at $SERVICE_START_TIME")
        super.onBind(intent)
        return null
    }
}
