<!-- res/anim/pulse_animation.xml -->
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:repeatCount="infinite"
    android:repeatMode="reverse">

    <scale
        android:duration="800"
        android:fromXScale="1.0"
        android:fromYScale="1.0"
        android:interpolator="@android:anim/accelerate_decelerate_interpolator"
        android:pivotX="50%"
        android:pivotY="50%"
        android:toXScale="1.1"
        android:toYScale="1.1" />

    <alpha
        android:duration="800"
        android:fromAlpha="1.0"
        android:interpolator="@android:anim/accelerate_decelerate_interpolator"
        android:toAlpha="0.7" />

</set>

<!-- res/anim/shake_animation.xml -->
<?xml version="1.0" encoding="utf-8"?>
<translate xmlns:android="http://schemas.android.com/apk/res/android"
    android:duration="100"
    android:fromXDelta="0"
    android:interpolator="@android:anim/cycle_interpolator"
    android:repeatCount="10"
    android:toXDelta="10" />

<!-- res/anim/flash_animation.xml -->
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:repeatCount="infinite"
    android:repeatMode="reverse">

    <alpha
        android:duration="300"
        android:fromAlpha="0.0"
        android:interpolator="@android:anim/linear_interpolator"
        android:toAlpha="1.0" />

</set>

<!-- res/anim/slide_in_from_top.xml -->
<?xml version="1.0" encoding="utf-8"?>
<translate xmlns:android="http://schemas.android.com/apk/res/android"
    android:duration="500"
    android:fromYDelta="-100%"
    android:interpolator="@android:anim/overshoot_interpolator"
    android:toYDelta="0%" />

<!-- res/anim/zoom_in_aggressive.xml -->
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android">

    <scale
        android:duration="300"
        android:fromXScale="0.0"
        android:fromYScale="0.0"
        android:interpolator="@android:anim/bounce_interpolator"
        android:pivotX="50%"
        android:pivotY="50%"
        android:toXScale="1.0"
        android:toYScale="1.0" />

    <alpha
        android:duration="300"
        android:fromAlpha="0.0"
        android:interpolator="@android:anim/accelerate_interpolator"
        android:toAlpha="1.0" />

</set>