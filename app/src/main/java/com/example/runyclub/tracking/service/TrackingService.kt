package com.example.runyclub.tracking.service

import android.content.Intent
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.model.CameraPosition
import com.example.runyclub.tracking.TrackingManager
import com.example.runyclub.tracking.notification.NotificationHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject
import androidx.lifecycle.Observer

@AndroidEntryPoint
class TrackingService : LifecycleService() {

    companion object {
        const val ACTION_PAUSE_TRACKING = "action_pause_tracking"
        const val ACTION_RESUME_TRACKING = "action_resume_tracking"
        const val ACTION_START_SERVICE = "action_start_service"
    }

    @Inject
    lateinit var trackingManager: TrackingManager

    @Inject
    lateinit var notificationHelper: NotificationHelper

    @Inject
    lateinit var trackingServiceManager: TrackingServiceManager

    private val cameraPositionObserver = Observer<CameraPosition> { cameraPosition ->
        // Handle camera position update here, if needed.
    }

    var job: Job? = null

    override fun onCreate() {
        super.onCreate()
        trackingServiceManager.cameraPositionUpdate.observe(this, cameraPositionObserver)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        when (intent?.action) {
            ACTION_PAUSE_TRACKING -> trackingManager.pauseTracking()
            ACTION_RESUME_TRACKING -> trackingManager.startResumeTracking()
            ACTION_START_SERVICE -> {
                startForeground(
                    NotificationHelper.TRACKING_NOTIFICATION_ID,
                    notificationHelper.baseNotificationBuilder.build()
                )

                if (job == null)
                    job = combine(
                        trackingManager.trackingDurationInMs,
                        trackingManager.currentRunState
                    ) { duration, currentRunState ->
                        notificationHelper.updateTrackingNotification(
                            durationInMillis = duration,
                            isTracking = currentRunState.isTracking
                        )
                    }.launchIn(lifecycleScope)
            }
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        trackingServiceManager.cameraPositionUpdate.removeObserver(cameraPositionObserver)
        notificationHelper.removeTrackingNotification()
        job?.cancel()
        job = null
    }
}