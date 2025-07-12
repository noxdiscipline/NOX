package com.nox.discipline.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nox.discipline.R
import com.nox.discipline.service.NOXDisciplineService
import com.nox.discipline.utils.NOXLogger
import kotlinx.coroutines.*
import kotlin.random.Random

/**
 * NOX PUNISHMENT OVERLAY - THE DISCIPLINE INTERFACE
 * Elite-tier fullscreen punishment experience
 * 
 * This overlay is your digital slap - impossible to ignore, designed to create
 * maximum psychological impact through aggressive visual design and forced interaction
 */
class PunishmentOverlayActivity : AppCompatActivity() {
    
    companion object {
        private const val MIN_OVERLAY_TIME = 10000L // 10 seconds minimum
        private const val VOICE_CONFESSION_PHRASE = "I choose discipline over addiction"
        private const val CAMERA_GUILT_DURATION = 5000L // 5 seconds of self-reflection
        
        private val AGGRESSIVE_COLORS = intArrayOf(
            0xFFFF0000.toInt(), // Blood red
            0xFF8B0000.toInt(), // Dark red
            0xFFDC143C.toInt(), // Crimson
            0xFFB22222.toInt()  // Fire brick
        )
    }
    
    private lateinit var txtPunishmentQuote: TextView
    private lateinit var txtViolatingApp: TextView
    private lateinit var txtTimer: TextView
    private lateinit var btnVoiceConfession: Button
    private lateinit var btnCameraGuilt: Button
    private lateinit var imgCameraPreview: ImageView
    private lateinit var rootLayout: View
    
    private var overlayStartTime: Long = 0
    private var isVoiceMode = false
    private var isCameraMode = false
    private var timerJob: Job? = null
    private var colorJob: Job? = null
    
    private val activityScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_punishment_overlay)
        
        setupFullscreenMode()
        initializeViews()
        setupContent()
        startPunishmentSequence()
        
        NOXLogger.w("Punishment overlay launched - Discipline enforcement active")
    }
    
    private fun setupFullscreenMode() {
        // Make this overlay impossible to escape
        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD)
            addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
            addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
            
            // Prevent back button and recent apps
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
            }
        }
        
        // Hide system UI
        window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN
        )
    }
    
    private fun initializeViews() {
        txtPunishmentQuote = findViewById(R.id.txtPunishmentQuote)
        txtViolatingApp = findViewById(R.id.txtViolatingApp)
        txtTimer = findViewById(R.id.txtTimer)
        btnVoiceConfession = findViewById(R.id.btnVoiceConfession)
        btnCameraGuilt = findViewById(R.id.btnCameraGuilt)
        imgCameraPreview = findViewById(R.id.imgCameraPreview)
        rootLayout = findViewById(R.id.rootLayout)
    }
    
    private fun setupContent() {
        val violatingApp = intent.getStringExtra("violating_app") ?: "Unknown App"
        val punishmentQuote = intent.getStringExtra("punishment_quote") ?: "DISCIPLINE YOURSELF"
        
        txtPunishmentQuote.text = punishmentQuote
        txtViolatingApp.text = "CAUGHT USING: ${getAppDisplayName(violatingApp)}"
        
        // Setup button listeners
        btnVoiceConfession.setOnClickListener {
            if (!isVoiceMode) {
                initiateVoiceConfession()
            }
        }
        
        btnCameraGuilt.setOnClickListener {
            if (!isCameraMode) {
                initiateCameraGuilt()
            }
        }
    }
    
    private fun startPunishmentSequence() {
        overlayStartTime = System.currentTimeMillis()
        
        // Start aggressive visual effects
        startAggressiveColorFlashing()
        startCountdownTimer()
        
        // Start pulsing animation on quote
        val pulseAnimation = AnimationUtils.loadAnimation(this, R.anim.pulse_animation)
        txtPunishmentQuote.startAnimation(pulseAnimation)
        
        // Disable buttons initially
        btnVoiceConfession.isEnabled = false
        btnCameraGuilt.isEnabled = false
        
        // Enable escape methods after minimum time
        Handler(Looper.getMainLooper()).postDelayed({
            enableEscapeMethods()
        }, MIN_OVERLAY_TIME)
    }
    
    private fun startAggressiveColorFlashing() {
        colorJob = activityScope.launch {
            while (isActive) {
                val color = AGGRESSIVE_COLORS[Random.nextInt(AGGRESSIVE_COLORS.size)]
                rootLayout.setBackgroundColor(color)
                delay(500)
                rootLayout.setBackgroundColor(Color.BLACK)
                delay(300)
            }
        }
    }
    
    private fun startCountdownTimer() {
        timerJob = activityScope.launch {
            while (isActive) {
                val elapsedTime = System.currentTimeMillis() - overlayStartTime
                val remainingTime = maxOf(0, MIN_OVERLAY_TIME - elapsedTime)
                
                if (remainingTime > 0) {
                    txtTimer.text = "PUNISHMENT ENDS IN: ${remainingTime / 1000}s"
                    txtTimer.visibility = View.VISIBLE
                } else {
                    txtTimer.text = "ESCAPE METHODS UNLOCKED"
                    txtTimer.setTextColor(0xFF00FF00.toInt()) // Green
                }
                
                delay(100)
            }
        }
    }
    
    private fun enableEscapeMethods() {
        btnVoiceConfession.isEnabled = true
        btnCameraGuilt.isEnabled = true
        
        // Change button colors to indicate availability
        btnVoiceConfession.setBackgroundColor(0xFF8B0000.toInt())
        btnCameraGuilt.setBackgroundColor(0xFF8B0000.toInt())
        
        // Start button pulsing
        val pulseAnimation = AnimationUtils.loadAnimation(this, R.anim.pulse_animation)
        btnVoiceConfession.startAnimation(pulseAnimation)
        btnCameraGuilt.startAnimation(pulseAnimation)
    }
    
    private fun initiateVoiceConfession() {
        isVoiceMode = true
        btnVoiceConfession.text = "SPEAKING..."
        btnVoiceConfession.setBackgroundColor(0xFFFF4500.toInt())
        
        txtPunishmentQuote.text = "SPEAK: '$VOICE_CONFESSION_PHRASE'"
        
        // Initialize voice recognition through service
        // This would connect to the service's voice recognition method
        NOXLogger.i("Voice confession initiated")
        
        // Simulate voice recognition for demo
        Handler(Looper.getMainLooper()).postDelayed({
            onVoiceConfessionSuccess()
        }, 3000)
    }
    
    private fun onVoiceConfessionSuccess() {
        NOXLogger.i("Voice confession successful - Overlay dismissed")
        dismissOverlay()
    }
    
    private fun initiateCameraGuilt() {
        isCameraMode = true
        btnCameraGuilt.text = "LOOK AT YOURSELF"
        btnCameraGuilt.setBackgroundColor(0xFFFF4500.toInt())
        
        // Show camera preview (implementation would use CameraX)
        imgCameraPreview.visibility = View.VISIBLE
        txtPunishmentQuote.text = "THIS IS THE FACE OF ADDICTION\nFIX IT."
        
        // Stop color flashing during camera mode
        colorJob?.cancel()
        rootLayout.setBackgroundColor(Color.BLACK)
        
        Handler(Looper.getMainLooper()).postDelayed({
            onCameraGuiltComplete()
        }, CAMERA_GUILT_DURATION)
    }
    
    private fun onCameraGuiltComplete() {
        NOXLogger.i("Camera guilt session complete - Overlay dismissed")
        dismissOverlay()
    }
    
    private fun dismissOverlay() {
        // Log successful escape
        NOXLogger.i("Punishment overlay dismissed - Discipline enforced")
        
        // Cancel all jobs
        timerJob?.cancel()
        colorJob?.cancel()
        activityScope.cancel()
        
        // Finish activity
        finish()
    }
    
    private fun getAppDisplayName(packageName: String): String {
        return when (packageName) {
            "com.instagram.android" -> "INSTAGRAM"
            "com.twitter.android" -> "TWITTER"
            "com.tiktok.android" -> "TIKTOK"
            "com.reddit.frontpage" -> "REDDIT"
            "com.snapchat.android" -> "SNAPCHAT"
            "com.netflix.android" -> "NETFLIX"
            "com.facebook.android" -> "FACEBOOK"
            "com.youtube.android" -> "YOUTUBE"
            "com.whatsapp" -> "WHATSAPP"
            "com.discord" -> "DISCORD"
            else -> packageName.substringAfterLast(".")
        }
    }
    
    override fun onBackPressed() {
        // Prevent back button escape
        NOXLogger.w("Back button pressed - Escape denied")
        
        // Make punishment more intense
        txtPunishmentQuote.text = "BACK BUTTON WON'T SAVE YOU\nFACE YOUR WEAKNESS"
        txtPunishmentQuote.setTextColor(0xFFFF0000.toInt())
        
        // Restart color flashing more aggressively
        colorJob?.cancel()
        startAggressiveColorFlashing()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        timerJob?.cancel()
        colorJob?.cancel()
        activityScope.cancel()
    }
}