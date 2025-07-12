package com.nox.discipline.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*

/**
 * NOX LOGGER - Elite logging system for behavioral tracking
 * Tracks every violation, escape, and punishment with surgical precision
 */
object NOXLogger {
    private const val TAG = "NOX_DISCIPLINE"
    private const val LOG_FILE_PREFIX = "nox_log_"
    
    fun i(message: String) {
        Log.i(TAG, "✅ $message")
    }
    
    fun w(message: String) {
        Log.w(TAG, "⚠️ $message")
    }
    
    fun e(message: String) {
        Log.e(TAG, "❌ $message")
    }
    
    fun discipline(message: String) {
        Log.w(TAG, "🔱 DISCIPLINE: $message")
    }
    
    fun punishment(message: String) {
        Log.e(TAG, "⚡ PUNISHMENT: $message")
    }
    
    fun victory(message: String) {
        Log.i(TAG, "🏆 VICTORY: $message")
    }
}

/**
 * NOX BEHAVIORAL LOGGER - Tracks user discipline patterns
 * Every action is logged for analysis and accountability
 */
class NOXBehavioralLogger(private val context: Context) {
    
    companion object {
        private const val PREFS_NAME = "nox_behavioral_logs"
        private const val KEY_DAILY_LOGS = "daily_logs"
        private const val KEY_STREAK_DATA = "streak_data"
        private const val KEY_PERFORMANCE_SCORE = "performance_score"
    }
    
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()
    
    data class LogEntry(
        val timestamp: Long,
        val appPackage: String,
        val action: LogAction,
        val duration: Long,
        val date: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    )
    
    enum class LogAction {
        DETECTED, ESCAPE, FAIL, PUNISHMENT, VOICE_CONFESSION, CAMERA_GUILT
    }
    
    data class StreakData(
        val currentStreak: Int,
        val bestStreak: Int,
        val lastFailDate: String?,
        val totalDisciplineDays: Int
    )
    
    data class DailyStats(
        val date: String,
        val escapes: Int,
        val fails: Int,
        val punishments: Int,
        val totalUsageTime: Long,
        val worstApp: String?
    )
    
    fun logDetection(appPackage: String) {
        val entry = LogEntry(
            timestamp = System.currentTimeMillis(),
            appPackage = appPackage,
            action = LogAction.DETECTED,
            duration = 0
        )
        saveLogEntry(entry)
        NOXLogger.w("Detection logged: $appPackage")
    }
    
    fun logEscape(appPackage: String, duration: Long) {
        val entry = LogEntry(
            timestamp = System.currentTimeMillis(),
            appPackage = appPackage,
            action = LogAction.ESCAPE,
            duration = duration
        )
        saveLogEntry(entry)
        updateStreak(false) // Escape doesn't break streak
        NOXLogger.victory("Escape logged: $appPackage (${duration}ms)")
    }
    
    fun logFail(appPackage: String, duration: Long) {
        val entry = LogEntry(
            timestamp = System.currentTimeMillis(),
            appPackage = appPackage,
            action = LogAction.FAIL,
            duration = duration
        )
        saveLogEntry(entry)
        updateStreak(true) // Fail breaks streak
        NOXLogger.e("Fail logged: $appPackage (${duration}ms)")
    }
    
    fun logPunishment(appPackage: String, duration: Long) {
        val entry = LogEntry(
            timestamp = System.currentTimeMillis(),
            appPackage = appPackage,
            action = LogAction.PUNISHMENT,
            duration = duration
        )
        saveLogEntry(entry)
        NOXLogger.punishment("Punishment logged: $appPackage")
    }
    
    fun logVoiceConfession(appPackage: String) {
        val entry = LogEntry(
            timestamp = System.currentTimeMillis(),
            appPackage = appPackage,
            action = LogAction.VOICE_CONFESSION,
            duration = 0
        )
        saveLogEntry(entry)
        NOXLogger.discipline("Voice confession logged: $appPackage")
    }
    
    fun logCameraGuilt(appPackage: String) {
        val entry = LogEntry(
            timestamp = System.currentTimeMillis(),
            appPackage = appPackage,
            action = LogAction.CAMERA_GUILT,
            duration = 0
        )
        saveLogEntry(entry)
        NOXLogger.discipline("Camera guilt logged: $appPackage")
    }
    
    private fun saveLogEntry(entry: LogEntry) {
        val existingLogs = getDailyLogs()
        val updatedLogs = existingLogs.toMutableList()
        updatedLogs.add(entry)
        
        val json = gson.toJson(updatedLogs)
        prefs.edit().putString(KEY_DAILY_LOGS, json).apply()
        
        updateDailyStats(entry)
        updatePerformanceScore()
    }
    
    fun getDailyLogs(): List<LogEntry> {
        val json = prefs.getString(KEY_DAILY_LOGS, "[]")
        val type = object : TypeToken<List<LogEntry>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }
    
    fun getTodayLogs(): List<LogEntry> {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        return getDailyLogs().filter { it.date == today }
    }
    
    fun getStreakData(): StreakData {
        val json = prefs.getString(KEY_STREAK_DATA, null)
        return if (json != null) {
            gson.fromJson(json, StreakData::class.java)
        } else {
            StreakData(0, 0, null, 0)
        }
    }
    
    private fun updateStreak(failOccurred: Boolean) {
        val streakData = getStreakData()
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        
        val updatedStreak = if (failOccurred) {
            StreakData(
                currentStreak = 0,
                bestStreak = maxOf(streakData.bestStreak, streakData.currentStreak),
                lastFailDate = today,
                totalDisciplineDays = streakData.totalDisciplineDays
            )
        } else {
            // Check if this is a new day without fails
            val todayLogs = getTodayLogs()
            val hasFails = todayLogs.any { it.action == LogAction.FAIL }
            
            if (!hasFails && streakData.lastFailDate != today) {
                StreakData(
                    currentStreak = streakData.currentStreak + 1,
                    bestStreak = maxOf(streakData.bestStreak, streakData.currentStreak + 1),
                    lastFailDate = streakData.lastFailDate,
                    totalDisciplineDays = streakData.totalDisciplineDays + 1
                )
            } else {
                streakData
            }
        }
        
        val json = gson.toJson(updatedStreak)
        prefs.edit().putString(KEY_STREAK_DATA, json).apply()
    }
    
    fun getDailyStats(): DailyStats {
        val todayLogs = getTodayLogs()
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        
        val escapes = todayLogs.count { it.action == LogAction.ESCAPE }
        val fails = todayLogs.count { it.action == LogAction.FAIL }
        val punishments = todayLogs.count { it.action == LogAction.PUNISHMENT }
        val totalUsageTime = todayLogs.sumOf { it.duration }
        
        val worstApp = todayLogs
            .groupBy { it.appPackage }
            .maxByOrNull { it.value.sumOf { entry -> entry.duration } }
            ?.key
        
        return DailyStats(
            date = today,
            escapes = escapes,
            fails = fails,
            punishments = punishments,
            totalUsageTime = totalUsageTime,
            worstApp = worstApp
        )
    }
    
    private fun updateDailyStats(entry: LogEntry) {
        // This could be expanded to store historical daily stats
        // For now, we calculate stats on-demand
    }
    
    private fun updatePerformanceScore() {
        val todayLogs = getTodayLogs()
        val streakData = getStreakData()
        
        // Calculate performance score (0-100)
        val escapes = todayLogs.count { it.action == LogAction.ESCAPE }
        val fails = todayLogs.count { it.action == LogAction.FAIL }
        val totalInteractions = escapes + fails
        
        val score = if (totalInteractions == 0) {
            100 // Perfect day
        } else {
            val escapeRate = escapes.toFloat() / totalInteractions
            val baseScore = (escapeRate * 70).toInt() // 70% max from escape rate
            val streakBonus = minOf(streakData.currentStreak * 2, 30) // 30% max from streak
            minOf(baseScore + streakBonus, 100)
        }
        
        prefs.edit().putInt(KEY_PERFORMANCE_SCORE, score).apply()
    }
    
    fun getPerformanceScore(): Int {
        return prefs.getInt(KEY_PERFORMANCE_SCORE, 100)
    }
    
    fun exportLogs(): String {
        val logs = getDailyLogs()
        val streakData = getStreakData()
        val dailyStats = getDailyStats()
        
        val exportData = mapOf(
            "logs" to logs,
            "streak" to streakData,
            "daily_stats" to dailyStats,
            "performance_score" to getPerformanceScore(),
            "export_timestamp" to System.currentTimeMillis()
        )
        
        return gson.toJson(exportData)
    }
    
    fun clearLogs() {
        prefs.edit().clear().apply()
        NOXLogger.i("All logs cleared")
    }
}

/**
 * NOX PREFERENCES - Elite settings management
 * Stores user preferences and app configuration
 */
class NOXPreferences(context: Context) {
    
    companion object {
        private const val PREFS_NAME = "nox_preferences"
        private const val KEY_BLACKLISTED_APPS = "blacklisted_apps"
        private const val KEY_VIOLATION_THRESHOLD = "violation_threshold"
        private const val KEY_PUNISHMENT_INTENSITY = "punishment_intensity"
        private const val KEY_VOICE_CONFESSION_ENABLED = "voice_confession_enabled"
        private const val KEY_CAMERA_GUILT_ENABLED = "camera_guilt_enabled"
        private const val KEY_HAPTIC_ENABLED = "haptic_enabled"
        private const val KEY_AUDIO_ENABLED = "audio_enabled"
        private const val KEY_FLASHLIGHT_ENABLED = "flashlight_enabled"
        private const val KEY_FIRST_LAUNCH = "first_launch"
    }
    
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()
    
    fun getBlacklistedApps(): Set<String> {
        val json = prefs.getString(KEY_BLACKLISTED_APPS, null)
        return if (json != null) {
            val type = object : TypeToken<Set<String>>() {}.type
            gson.fromJson(json, type) ?: emptySet()
        } else {
            // Default blacklist
            setOf(
                "com.instagram.android",
                "com.twitter.android",
                "com.tiktok.android",
                "com.reddit.frontpage",
                "com.snapchat.android",
                "com.netflix.android",
                "com.facebook.android",
                "com.youtube.android"
            )
        }
    }
    
    fun setBlacklistedApps(apps: Set<String>) {
        val json = gson.toJson(apps)
        prefs.edit().putString(KEY_BLACKLISTED_APPS, json).apply()
    }
    
    fun getViolationThreshold(): Long {
        return prefs.getLong(KEY_VIOLATION_THRESHOLD, 10000L) // 10 seconds default
    }
    
    fun setViolationThreshold(threshold: Long) {
        prefs.edit().putLong(KEY_VIOLATION_THRESHOLD, threshold).apply()
    }
    
    fun getPunishmentIntensity(): Int {
        return prefs.getInt(KEY_PUNISHMENT_INTENSITY, 5) // 1-10 scale, 5 default
    }
    
    fun setPunishmentIntensity(intensity: Int) {
        prefs.edit().putInt(KEY_PUNISHMENT_INTENSITY, intensity.coerceIn(1, 10)).apply()
    }
    
    fun isVoiceConfessionEnabled(): Boolean {
        return prefs.getBoolean(KEY_VOICE_CONFESSION_ENABLED, true)
    }
    
    fun setVoiceConfessionEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_VOICE_CONFESSION_ENABLED, enabled).apply()
    }
    
    fun isCameraGuiltEnabled(): Boolean {
        return prefs.getBoolean(KEY_CAMERA_GUILT_ENABLED, true)
    }
    
    fun setCameraGuiltEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_CAMERA_GUILT_ENABLED, enabled).apply()
    }
    
    fun isHapticEnabled(): Boolean {
        return prefs.getBoolean(KEY_HAPTIC_ENABLED, true)
    }
    
    fun setHapticEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_HAPTIC_ENABLED, enabled).apply()
    }
    
    fun isAudioEnabled(): Boolean {
        return prefs.getBoolean(KEY_AUDIO_ENABLED, true)
    }
    
    fun setAudioEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_AUDIO_ENABLED, enabled).apply()
    }
    
    fun isFlashlightEnabled(): Boolean {
        return prefs.getBoolean(KEY_FLASHLIGHT_ENABLED, true)
    }
    
    fun setFlashlightEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_FLASHLIGHT_ENABLED, enabled).apply()
    }
    
    fun isFirstLaunch(): Boolean {
        return prefs.getBoolean(KEY_FIRST_LAUNCH, true)
    }
    
    fun setFirstLaunch(firstLaunch: Boolean) {
        prefs.edit().putBoolean(KEY_FIRST_LAUNCH, firstLaunch).apply()
    }
}