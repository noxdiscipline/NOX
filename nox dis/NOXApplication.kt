package com.nox.discipline

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.room.Room
import com.nox.discipline.data.database.NOXDatabase
import com.nox.discipline.data.repository.NOXRepository
import com.nox.discipline.utils.NOXLogger
import com.nox.discipline.utils.NOXPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber

/**
 * NOX APPLICATION - THE DIGITAL DISCIPLINE FORGE
 * 
 * This is not just an application class.
 * This is the foundation of a psychological warfare system.
 * Every line of code here serves the mission: ELIMINATE DIGITAL WEAKNESS.
 * 
 * @author NOX_ARCHITECT
 * @version ELITE-SAVAGE
 */
class NOXApplication : Application() {
    
    // ELITE ARCHITECTURE - SINGLE SOURCE OF TRUTH
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    
    // DATABASE - THE FORTRESS OF DISCIPLINE DATA
    val database by lazy { 
        Room.databaseBuilder(
            applicationContext,
            NOXDatabase::class.java,
            "nox_discipline_database"
        ).apply {
            // ELITE CONFIGURATION
            enableMultiInstanceInvalidation()
            setJournalMode(androidx.room.RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING)
            fallbackToDestructiveMigration() // BRUTAL BUT NECESSARY
        }.build()
    }
    
    // REPOSITORY - THE COMMAND CENTER
    val repository by lazy { 
        NOXRepository(
            database.disciplineDao(),
            database.punishmentLogDao(),
            database.settingsDao()
        )
    }
    
    // PREFERENCES - THE CONFIGURATION VAULT
    val preferences by lazy { NOXPreferences(this) }
    
    companion object {
        // NOTIFICATION CHANNELS - SYSTEM DOMINATION
        const val CHANNEL_GUARDIAN = "NOX_GUARDIAN"
        const val CHANNEL_PUNISHMENT = "NOX_PUNISHMENT"
        const val CHANNEL_BROTHERHOOD = "NOX_BROTHERHOOD"
        const val CHANNEL_EMERGENCY = "NOX_EMERGENCY"
        
        // ELITE CONSTANTS
        const val NOX_VERSION = "ELITE-SAVAGE-1.0"
        const val NOX_SIGNATURE = "DISCIPLINE_FORGE"
        
        // SINGLETON ACCESS - GLOBAL COMMAND
        lateinit var instance: NOXApplication
            private set
    }
    
    override fun onCreate() {
        super.onCreate()
        
        // ESTABLISH GLOBAL COMMAND
        instance = this
        
        // INITIALIZE LOGGING SYSTEM
        initializeLogging()
        
        // ESTABLISH NOTIFICATION CHANNELS
        createNotificationChannels()
        
        // INITIALIZE CORE SYSTEMS
        initializeCoreServices()
        
        // ELITE STARTUP LOG
        NOXLogger.logSystemStart("NOX APPLICATION INITIALIZED - DISCIPLINE FORGE ACTIVE")
        
        // BRUTAL TRUTH LOG
        Timber.i("🔱 NOX DISCIPLINE FORGE ONLINE")
        Timber.i("🎯 MISSION: ELIMINATE DIGITAL WEAKNESS")
        Timber.i("⚔️ STATUS: READY FOR PSYCHOLOGICAL WARFARE")
    }
    
    /**
     * INITIALIZE ELITE LOGGING SYSTEM
     * Every action, every failure, every victory - ALL RECORDED
     */
    private fun initializeLogging() {
        if (BuildConfig.DEBUG) {
            // DEBUG MODE - DETAILED INTELLIGENCE
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return "NOX_${element.fileName}:${element.lineNumber}"
                }
            })
        } else {
            // RELEASE MODE - CRITICAL LOGGING ONLY
            Timber.plant(object : Timber.Tree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    if (priority >= android.util.Log.WARN) {
                        // LOG ONLY WARNINGS AND ERRORS IN PRODUCTION
                        NOXLogger.logError("$tag: $message", t)
                    }
                }
            })
        }
    }
    
    /**
     * CREATE NOTIFICATION CHANNELS - SYSTEM DOMINATION
     * Each channel serves a specific purpose in the discipline system
     */
    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            
            // GUARDIAN SERVICE CHANNEL - PERSISTENT MONITORING
            val guardianChannel = NotificationChannel(
                CHANNEL_GUARDIAN,
                "NOX Guardian Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Persistent monitoring service - The Eye That Watches"
                setShowBadge(false)
                enableLights(false)
                enableVibration(false)
            }
            
            // PUNISHMENT CHANNEL - IMMEDIATE CONSEQUENCES
            val punishmentChannel = NotificationChannel(
                CHANNEL_PUNISHMENT,
                "NOX Punishment System",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Immediate punishment notifications - Weakness Detected"
                setShowBadge(true)
                enableLights(true)
                enableVibration(true)
                lightColor = android.graphics.Color.RED
            }
            
            // BROTHERHOOD CHANNEL - MUTUAL ACCOUNTABILITY
            val brotherhoodChannel = NotificationChannel(
                CHANNEL_BROTHERHOOD,
                "NOX Brotherhood Sync",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Brotherhood accountability system - Shared Discipline"
                setShowBadge(true)
                enableLights(true)
                lightColor = android.graphics.Color.BLUE
            }
            
            // EMERGENCY CHANNEL - CRITICAL ALERTS
            val emergencyChannel = NotificationChannel(
                CHANNEL_EMERGENCY,
                "NOX Emergency System",
                NotificationManager.IMPORTANCE_MAX
            ).apply {
                description = "Critical system alerts - Maximum Priority"
                setShowBadge(true)
                enableLights(true)
                enableVibration(true)
                lightColor = android.graphics.Color.RED
            }
            
            // REGISTER ALL CHANNELS
            notificationManager.createNotificationChannels(listOf(
                guardianChannel,
                punishmentChannel,
                brotherhoodChannel,
                emergencyChannel
            ))
            
            Timber.i("🔔 NOX NOTIFICATION CHANNELS ESTABLISHED")
        }
    }
    
    /**
     * INITIALIZE CORE SERVICES
     * Start the essential systems that maintain discipline
     */
    private fun initializeCoreServices() {
        // INITIALIZE PREFERENCES WITH ELITE DEFAULTS
        preferences.initializeDefaults()
        
        // VERIFY SYSTEM PERMISSIONS
        verifySystemPermissions()
        
        // INITIALIZE BLACKLIST
        initializeBlacklist()
        
        Timber.i("🏗️ NOX CORE SERVICES INITIALIZED")
    }
    
    /**
     * VERIFY CRITICAL SYSTEM PERMISSIONS
     * Ensure we have the power to enforce discipline
     */
    private fun verifySystemPermissions() {
        val criticalPermissions = mutableListOf<String>()
        
        // USAGE STATS - ESSENTIAL FOR APP DETECTION
        if (!preferences.hasUsageStatsPermission()) {
            criticalPermissions.add("USAGE_STATS")
        }
        
        // OVERLAY PERMISSION - ESSENTIAL FOR PUNISHMENT
        if (!preferences.hasOverlayPermission()) {
            criticalPermissions.add("SYSTEM_ALERT_WINDOW")
        }
        
        // ACCESSIBILITY - ESSENTIAL FOR REAL-TIME DETECTION
        if (!preferences.hasAccessibilityPermission()) {
            criticalPermissions.add("ACCESSIBILITY_SERVICE")
        }
        
        if (criticalPermissions.isNotEmpty()) {
            Timber.w("⚠️ MISSING CRITICAL PERMISSIONS: ${criticalPermissions.joinToString()}")
            NOXLogger.logWarning("Critical permissions missing: ${criticalPermissions.joinToString()}")
        } else {
            Timber.i("✅ ALL CRITICAL PERMISSIONS GRANTED")
        }
    }
    
    /**
     * INITIALIZE DEFAULT BLACKLIST
     * The apps that trigger the discipline system
     */
    private fun initializeBlacklist() {
        if (preferences.isFirstLaunch()) {
            val defaultBlacklist = setOf(
                // SOCIAL MEDIA DESTRUCTION
                "com.instagram.android",
                "com.facebook.katana",
                "com.twitter.android",
                "com.snapchat.android",
                "com.tiktok.android",
                "com.reddit.frontpage",
                "com.pinterest",
                "com.linkedin.android",
                "com.tumblr",
                "com.discord",
                
                // VIDEO DOPAMINE DEALERS
                "com.google.android.youtube",
                "com.netflix.mediaclient",
                "com.amazon.avod.thirdpartyclient",
                "com.hulu.plus",
                "com.disney.disneyplus",
                "com.viacom.mtv",
                "com.twitch.android.app",
                
                // GAMING ADDICTION
                "com.king.candycrushsaga",
                "com.supercell.clashofclans",
                "com.supercell.clashroyale",
                "com.pubgm.india",
                "com.garena.game.freefire",
                "com.roblox.client",
                "com.miHoYo.GenshinImpact",
                
                // NEWS DOOMSCROLLING
                "com.google.android.apps.magazines",
                "flipboard.app",
                "com.cnn.mobile.android.phone",
                "com.bbc.news",
                
                // SHOPPING ADDICTION
                "com.amazon.mShop.android.shopping",
                "com.ebay.mobile",
                "com.shopify.mobile",
                "com.alibaba.aliexpresshd"
            )
            
            preferences.updateBlacklistedApps(defaultBlacklist)
            Timber.i("📱 DEFAULT BLACKLIST INITIALIZED: ${defaultBlacklist.size} APPS")
        }
    }
    
    /**
     * ELITE CLEANUP - RELEASE RESOURCES
     */
    override fun onTerminate() {
        super.onTerminate()
        NOXLogger.logSystemEnd("NOX APPLICATION TERMINATED")
        Timber.i("🔱 NOX DISCIPLINE FORGE SHUTDOWN")
    }
}