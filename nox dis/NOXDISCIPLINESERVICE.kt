<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/rootLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@android:color/black"
    android:clickable="true"
    android:focusable="true">

    <!-- Main punishment message -->
    <TextView
        android:id="@+id/txtPunishmentQuote"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:layout_marginHorizontal="24dp"
        android:fontFamily="@font/roboto_black"
        android:gravity="center"
        android:letterSpacing="0.1"
        android:lineSpacing="8dp"
        android:text="YOU ARE BEING CONTROLLED"
        android:textColor="@android:color/white"
        android:textSize="32sp"
        android:textStyle="bold" />

    <!-- Violating app display -->
    <TextView
        android:id="@+id/txtViolatingApp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_above="@id/txtPunishmentQuote"
        android:layout_marginHorizontal="24dp"
        android:layout_marginBottom="32dp"
        android:fontFamily="@font/roboto_medium"
        android:gravity="center"
        android:letterSpacing="0.15"
        android:text="CAUGHT USING: APP NAME"
        android:textColor="#FF4444"
        android:textSize="18sp"
        android:textStyle="bold" />

    <!-- Timer display -->
    <TextView
        android:id="@+id/txtTimer"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_below="@id/txtPunishmentQuote"
        android:layout_marginHorizontal="24dp"
        android:layout_marginTop="24dp"
        android:fontFamily="@font/roboto_mono"
        android:gravity="center"
        android:letterSpacing="0.1"
        android:text="PUNISHMENT ENDS IN: 10s"
        android:textColor="#FF6666"
        android:textSize="16sp"
        android:textStyle="bold" />

    <!-- Camera preview for guilt mode -->
    <ImageView
        android:id="@+id/imgCameraPreview"
        android:layout_width="300dp"
        android:layout_height="300dp"
        android:layout_centerHorizontal="true"
        android:layout_below="@id/txtTimer"
        android:layout_marginTop="24dp"
        android:background="@drawable/camera_preview_border"
        android:scaleType="centerCrop"
        android:visibility="gone" />

    <!-- Bottom action buttons -->
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_marginHorizontal="24dp"
        android:layout_marginBottom="48dp"
        android:orientation="vertical"
        android:gravity="center">

        <!-- Voice confession button -->
        <Button
            android:id="@+id/btnVoiceConfession"
            android:layout_width="match_parent"
            android:layout_height="64dp"
            android:layout_marginBottom="16dp"
            android:background="@drawable/nox_button_disabled"
            android:enabled="false"
            android:fontFamily="@font/roboto_bold"
            android:letterSpacing="0.1"
            android:text="🎤 VOICE CONFESSION"
            android:text