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
)
