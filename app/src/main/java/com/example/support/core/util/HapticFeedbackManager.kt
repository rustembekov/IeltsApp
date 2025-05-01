package com.example.support.core.util

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HapticFeedbackManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val _vibrateEvent = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val vibrateEvent = _vibrateEvent.asSharedFlow()

    fun vibrate(durationMs: Long = 50L, amplitude: Int = VibrationEffect.DEFAULT_AMPLITUDE) {
        _vibrateEvent.tryEmit(Unit)

        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val manager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            manager.defaultVibrator
        } else {
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        val effect = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            VibrationEffect.createOneShot(durationMs, amplitude)
        } else {
            return
        }

        vibrator.vibrate(effect)
    }
}
