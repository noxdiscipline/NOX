package com.nox.discipline.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

/**
 * NOX DATA MODELS - THE DISCIPLINE ARCHITECTURE
 * 
 * These are not just data classes.
 * These are the digital DNA of discipline itself.
 * Every field serves the mission: TRACK, PUNISH, IMPROVE.
 * 
 * @author NOX_ARCHITECT
 * @version ELITE-SAVAGE
 */

// ============================================================================
// PUNISHMENT LOG - THE HALL OF SHAME AND VICTORY
// ============================================================================

@Entity(tableName = "punishment_logs")
@TypeConverters(Converters::class)
data class PunishmentLog(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    // BASIC VIOLATION DATA
    val appPackageName: String,
    val appDisplayName: String,
    val violationType: ViolationType,
    val timestamp: Date,
    val duration: Long, // Duration in milliseconds
    
    // PUNISHMENT DETAILS
    val punishmentType: PunishmentType,
    val punishmentIntensity: Int, // 1-10 scale
    val wasEscaped: Boolean,
    val escapeMethod: EscapeMethod?,
    val escapeTime: Long?, // Time taken to escape in milliseconds
    
    // PSYCHOLOGICAL DATA
    val userEmotionalState: EmotionalState?,
    val stressLevel: Int?, // 1-10 scale if available
    val timeOfDay: TimeOfDay,
    val dayOfWeek: DayOfWeek,
    
    // CONTEXT DATA
    val batteryLevel: Int,
    val isCharging: Boolean,
    val wifiConnected: Boolean,
    val locationContext: String?, // Home, Work, etc.
    
    // BROTHERHOOD DATA
    val syncedToBrotherhood: Boolean = false,
    val brotherhoodImpact: Boolean = false,
    
    // NOTES AND REFLECTION
    val userNotes: String? = null,
    val systemNotes: String? = null
)

// ============================================================================
// DISCIPLINE STATS - THE MEASUREMENT OF STRENGTH
// ============================================================================

@Entity(tableName = "discipline_stats")
@TypeConverters(Converters::class)
data class DisciplineStats(
    @PrimaryKey
    val date: Date,
    
    // DAILY PERFORMANCE METRICS
    val totalViolations: Int,
    val totalEscapes: Int,
    val totalFails: Int,
    val totalScreenTime: Long, // in milliseconds
    val disciplineScore: Double, // 0-100 scale
    
    // STREAK DATA
    val currentStreak: Int,
    val longestStreak: Int,
    val streakBroken: Boolean,
    
    // APP-SPECIFIC DATA
    val worstApp: String?,
    val worstAppTime: Long,
    val mostUsedApps: List<String>,
    
    // PUNISHMENT EFFECTIVENESS
    val punishmentSuccess: Double, // % of successful punishments
    val averageEscapeTime: Long,
    val mostEffectivePunishment: PunishmentType?,
    
    // PSYCHOLOGICAL PATTERNS
    val peakWeaknessHour: Int, // 0-23 hour format
    val strongestHour: Int,
    val emotionalPattern: Map<EmotionalState, Int>,
    
    // BROTHERHOOD IMPACT
    val brotherhoodActive: Boolean,
    val brotherPerformance: Double?,
    val mutualFailures: Int,
    
    // GOALS AND TARGETS
    val targetDisciplineScore: Double,
    val targetStreakGoal: Int,
    val dailyGoalMet: Boolean
)

// ============================================================================
// NOX SETTINGS - THE CONFIGURATION OF DISCIPLINE
// ============================================================================

@Entity(tableName = "nox_settings")
@TypeConverters(Converters::class)
data class NOXSettings(
    @PrimaryKey
    val settingId: String,
    
    // DETECTION SETTINGS
    val detectionThreshold: Long = 10000, // 10 seconds default
    val detectionInterval: Long = 1000, // 1 second checks
    val blacklistedApps: Set<String> = emptySet(),
    val appSpecificThresholds: Map<String, Long> = emptyMap(),
    
    // PUNISHMENT SETTINGS
    val punishmentIntensity: Int = 5, // 1-10 scale
    val enableSoundPunishment: Boolean = true,
    val enableVibrationPunishment: Boolean = true,
    val enableFlashlightPunishment: Boolean = true,
    val enableCameraGuilt: Boolean = true,
    val enableVoiceConfession: Boolean = true,
    
    // OVERLAY SETTINGS
    val overlayDuration: Long = 15000, // 15 seconds
    val overlayColor: Int = android.graphics.Color.BLACK,
    val overlayTextColor: Int = android.graphics.Color.RED,
    val overlayMessages: List<String> = emptyList(),
    
    // VOICE CONFESSION SETTINGS
    val confessionPhrases: List<String> = emptyList(),
    val voiceRecognitionTimeout: Long = 30000, // 30 seconds
    val speechConfidenceThreshold: Float = 0.7f,
    
    // TIME RESTRICTIONS
    val blackoutZones: List<BlackoutZone> = emptyList(),
    val emergencyLockdownDuration: Long = 3600000, // 1 hour
    
    // BROTHERHOOD SETTINGS
    val brotherhoodEnabled: Boolean = false,
    val brotherhoodPartnerId: String? = null,
    val mutualPunishmentEnabled: Boolean = false,
    
    // NOTIFICATION SETTINGS
    val notificationsEnabled: Boolean = true,
    val dailyReportTime: String = "20:00",
    val weeklyReportDay: DayOfWeek = DayOfWeek.SUNDAY,
    
    // ADVANCED SETTINGS
    val adaptivePunishmentEnabled: Boolean = false,
    val learningModeEnabled: Boolean = false,
    val biometricIntegration: Boolean = false,
    val exportDataEnabled: Boolean = true,
    
    // UI PREFERENCES
    val darkModeEnabled: Boolean = true,
    val animationsEnabled: Boolean = true,
    val hapticFeedbackEnabled: Boolean = true,
    
    // SYSTEM SETTINGS
    val batteryOptimizationIgnored: Boolean = false,
    val autoStartEnabled: Boolean = true,
    val debugLoggingEnabled: Boolean = false,
    
    // LAST UPDATED
    val lastUpdated: Date = Date()
)

// ============================================================================
// BLACKOUT ZONE - TIME-BASED RESTRICTIONS
// ============================================================================

data class BlackoutZone(
    val id: String,
    val name: String,
    val startHour: Int, // 0-23
    val startMinute: Int, // 0-59
    val endHour: Int,
    val endMinute: Int,
    val daysOfWeek: Set<DayOfWeek>,
    val allowedApps: Set<String>, // Apps allowed during blackout
    val strictMode: Boolean = false, // If true, NO apps allowed
    val isActive: Boolean = true
)

// ============================================================================
// BROTHERHOOD SYNC DATA - MUTUAL ACCOUNTABILITY
// ============================================================================

@Entity(tableName = "brotherhood_sync")
@TypeConverters(Converters::class)
data class BrotherhoodSync(
    @PrimaryKey
    val syncId: String,
    
    // PARTNER INFO
    val partnerId: String,
    val partnerName: String,
    val partnerDeviceId: String,
    
    // SYNC STATUS
    val lastSyncTimestamp: Date,
    val syncStatus: SyncStatus,
    val connectionStrength: Int, // 1-5 signal strength
    
    // SHARED STATS
    val myDisciplineScore: Double,
    val partnerDisciplineScore: Double,
    val combinedStreak: Int,
    val mutualFailures: Int,
    
    // PUNISHMENT SHARING
    val mutualPunishmentActive: Boolean,
    val lastMutualPunishment: Date?,
    val punishmentReason: String?,
    
    // MESSAGES
    val lastMessage: String?,
    val lastMessageTimestamp: Date?,
    val unreadMessages: Int
)

// ============================================================================
// VOICE CONFESSION DATA - SPEECH RECOGNITION RESULTS
// ============================================================================

@Entity(tableName = "voice_confessions")
@TypeConverters(Converters::class)
data class VoiceConfession(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    // BASIC DATA
    val timestamp: Date,
    val violationLogId: Long, // Reference to punishment log
    
    // SPEECH RECOGNITION
    val recognizedText: String,
    val confidenceScore: Float,
    val expectedPhrase: String,
    val isMatch: Boolean,
    
    // PERFORMANCE DATA
    val attemptNumber: Int,
    val timeToComplete: Long, // milliseconds
    val audioQuality: Float,
    
    // PSYCHOLOGICAL DATA
    val emotionalTone: EmotionalTone?,
    val speechClarity: SpeechClarity,
    val hesitationCount: Int,
    
    // RESULT
    val confessionAccepted: Boolean,
    val punishmentReleased: Boolean
)

// ============================================================================
// ENUMS - THE CLASSIFICATION SYSTEM
// ============================================================================

enum class ViolationType {
    THRESHOLD_EXCEEDED,
    BLACKOUT_VIOLATION,
    REPEATED_USAGE,
    EMERGENCY_LOCKDOWN_BROKEN,
    BROTHERHOOD_TRIGGERED
}

enum class PunishmentType {
    OVERLAY_ONLY,
    OVERLAY_SOUND,
    OVERLAY_VIBRATION,
    OVERLAY_FLASH,
    FULL_SENSORY,
    CAMERA_GUILT,
    VOICE_CONFESSION,
    EMERGENCY_LOCKDOWN
}

enum class EscapeMethod {
    QUICK_CLOSE,
    VOICE_CONFESSION,
    TIMEOUT,
    EMERGENCY_OVERRIDE,
    SYSTEM_BYPASS
}

enum class EmotionalState {
    CALM,
    ANXIOUS,
    FRUSTRATED,
    ANGRY,
    GUILTY,
    DETERMINED,
    DEFEATED,
    MOTIVATED
}

enum class TimeOfDay {
    EARLY_MORNING,  // 5-8 AM
    MORNING,        // 8-12 PM
    AFTERNOON,      // 12-5 PM
    EVENING,        // 5-9 PM
    NIGHT,          // 9-12 AM
    LATE_NIGHT      // 12-5 AM
}

enum class DayOfWeek {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
}

enum class SyncStatus {
    CONNECTED,
    DISCONNECTED,
    SYNCING,
    ERROR,
    WAITING_FOR_PARTNER
}

enum class EmotionalTone {
    CONFIDENT,
    HESITANT,
    ASHAMED,
    DEFIANT,
    SINCERE,
    ROBOTIC
}

enum class SpeechClarity {
    CLEAR,
    MUMBLED,
    WHISPERED,
    SHOUTED,
    DISTORTED
}

// ============================================================================
// TYPE CONVERTERS - DATABASE SERIALIZATION
// ============================================================================

class Converters {
    
    private val gson = Gson()
    
    // DATE CONVERTERS
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }
    
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
    
    // STRING SET CONVERTERS
    @TypeConverter
    fun fromStringSet(value: Set<String>?): String {
        return gson.toJson(value)
    }
    
    @TypeConverter
    fun toStringSet(value: String): Set<String> {
        val listType = object : TypeToken<Set<String>>() {}.type
        return gson.fromJson(value, listType) ?: emptySet()
    }
    
    // STRING LIST CONVERTERS
    @TypeConverter
    fun fromStringList(value: List<String>?): String {
        return gson.toJson(value)
    }
    
    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType) ?: emptyList()
    }
    
    // MAP CONVERTERS
    @TypeConverter
    fun fromStringLongMap(value: Map<String, Long>?): String {
        return gson.toJson(value)
    }
    
    @TypeConverter
    fun toStringLongMap(value: String): Map<String, Long> {
        val mapType = object : TypeToken<Map<String, Long>>() {}.type
        return gson.fromJson(value, mapType) ?: emptyMap()
    }
    
    @TypeConverter
    fun fromEmotionalStateMap(value: Map<EmotionalState, Int>?): String {
        return gson.toJson(value)
    }
    
    @TypeConverter
    fun toEmotionalStateMap(value: String): Map<EmotionalState, Int> {
        val mapType = object : TypeToken<Map<EmotionalState, Int>>() {}.type
        return gson.fromJson(value, mapType) ?: emptyMap()
    }
    
    // BLACKOUT ZONE CONVERTERS
    @TypeConverter
    fun fromBlackoutZoneList(value: List<BlackoutZone>?): String {
        return gson.toJson(value)
    }
    
    @TypeConverter
    fun toBlackoutZoneList(value: String): List<BlackoutZone> {
        val listType = object : TypeToken<List<BlackoutZone>>() {}.type
        return gson.fromJson(value, listType) ?: emptyList()
    }
    
    // DAY OF WEEK SET CONVERTERS
    @TypeConverter
    fun fromDayOfWeekSet(value: Set<DayOfWeek>?): String {
        return gson.toJson(value)
    }
    
    @TypeConverter
    fun toDayOfWeekSet(value: String): Set<DayOfWeek> {
        val setType = object : TypeToken<Set<DayOfWeek>>() {}.type
        return gson.fromJson(value, setType) ?: emptySet()
    }
}

// ============================================================================
// ELITE CONSTANTS - THE PARAMETERS OF DISCIPLINE
// ============================================================================

object NOXConstants {
    
    // DETECTION THRESHOLDS
    const val MIN_DETECTION_THRESHOLD = 5000L      // 5 seconds
    const val MAX_DETECTION_THRESHOLD = 300000L    // 5 minutes
    const val DEFAULT_DETECTION_THRESHOLD = 10000L // 10 seconds
    
    // PUNISHMENT DURATIONS
    const val MIN_OVERLAY_DURATION = 5000L         // 5 seconds
    const val MAX_OVERLAY_DURATION = 120000L       // 2 minutes
    const val DEFAULT_OVERLAY_DURATION = 15000L    // 15 seconds
    
    // DISCIPLINE SCORING
    const val PERFECT_DISCIPLINE_SCORE = 100.0
    const val FAILING_DISCIPLINE_SCORE = 40.0
    const val STREAK_MULTIPLIER = 1.5
    const val ESCAPE_BONUS = 2.0
    const val FAIL_PENALTY = -5.0
    
    // VOICE CONFESSION
    const val MIN_SPEECH_CONFIDENCE = 0.6f
    const val MAX_SPEECH_CONFIDENCE = 1.0f
    const val DEFAULT_SPEECH_CONFIDENCE = 0.7f
    const val MAX_CONFESSION_ATTEMPTS = 3
    
    // BROTHERHOOD SYNC
    const val SYNC_INTERVAL = 30000L               // 30 seconds
    const val MAX_SYNC_FAILURES = 5
    const val BROTHERHOOD_TIMEOUT = 300000L       // 5 minutes
    
    // EMERGENCY LOCKDOWN
    const val MIN_LOCKDOWN_DURATION = 300000L     // 5 minutes
    const val MAX_LOCKDOWN_DURATION = 86400000L   // 24 hours
    const val DEFAULT_LOCKDOWN_DURATION = 3600000L // 1 hour
    
    // SYSTEM PERFORMANCE
    const val DETECTION_INTERVAL = 1000L          // 1 second
    const val DATABASE_CLEANUP_INTERVAL = 604800000L // 1 week
    const val MAX_LOG_ENTRIES = 10000
    
    // DEFAULT CONFESSION PHRASES
    val DEFAULT_CONFESSION_PHRASES = listOf(
        "I am addicted. I choose discipline. NOX commands me.",
        "I reject weakness. I embrace strength. I am in control.",
        "My mind is my weapon. I will not be enslaved by apps.",
        "I am stronger than my urges. Discipline is my power.",
        "I acknowledge my weakness. I choose to overcome it."
    )
    
    // BRUTAL OVERLAY MESSAGES
    val BRUTAL_OVERLAY_MESSAGES = listOf(
        "YOU ARE BEING CONTROLLED",
        "THIS IS NOT ENTERTAINMENT - THIS IS ADDICTION",
        "WEAK MINDS SCROLL - STRONG MINDS BUILD",
        "EVERY SCROLL IS A CHOICE TO REMAIN WEAK",
        "YOUR ATTENTION IS BEING STOLEN",
        "DISCIPLINE IS THE BRIDGE BETWEEN GOALS AND ACCOMPLISHMENT",
        "YOU'RE BETTER THAN THIS DOPAMINE HIT",
        "STOP CONSUMING - START CREATING",
        "MEDIOCRITY LOVES COMPANY - EXCELLENCE STANDS ALONE",
        "CHAMPIONS ARE MADE WHEN NOBODY IS WATCHING"
    )
}