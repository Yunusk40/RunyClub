package com.example.runyclub.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.example.runyclub.database.AppDatabase
import com.example.runyclub.database.AppDatabase.Companion.RUN_TRACK_DB_NAME
import com.example.runyclub.tracking.location.DefaultLocationTrackingManager
import com.example.runyclub.tracking.location.LocationTrackingManager
import com.example.runyclub.tracking.location.LocationUtils
import com.example.runyclub.tracking.notification.DefaultNotificationHelper
import com.example.runyclub.tracking.notification.NotificationHelper
import com.example.runyclub.tracking.service.DefaultTrackingServiceManager
import com.example.runyclub.tracking.service.TrackingServiceManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.plus
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    companion object {

        private const val USER_PREFERENCES_FILE_NAME = "user_preferences"

        /**
         * Provides a FusedLocationProviderClient for accessing the device's location services.
         */
        @Singleton
        @Provides
        fun provideFusedLocationProviderClient(
            @ApplicationContext context: Context
        ) = LocationServices
            .getFusedLocationProviderClient(context)

        /**
         * Provides a Room database instance for local data storage.
         */
        @Provides
        @Singleton
        fun provideRunningDB(
            @ApplicationContext context: Context
        ): AppDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            RUN_TRACK_DB_NAME
        ).build()

        /**
         * Provides the DAO for accessing the Room database.
         */
        @Singleton
        @Provides
        fun provideRunDao(db: AppDatabase) = db.getRunDao()

        /**
         * Provides a DataStore for preference storage, utilizing the IO dispatcher for background operations.
         */
        @Provides
        @Singleton
        fun providesPreferenceDataStore(
            @ApplicationContext context: Context,
            @ApplicationScope scope: CoroutineScope,
            @IoDispatcher ioDispatcher: CoroutineDispatcher
        ): DataStore<Preferences> =
            PreferenceDataStoreFactory.create(
                corruptionHandler = ReplaceFileCorruptionHandler(
                    produceNewData = { emptyPreferences() }
                ),
                produceFile = { context.preferencesDataStoreFile(USER_PREFERENCES_FILE_NAME) },
                scope = scope.plus(ioDispatcher + SupervisorJob())
            )

        /**
         * Provides a LocationTrackingManager for managing location tracking functionalities.
         */
        @Singleton
        @Provides
        fun provideLocationTrackingManager(
            @ApplicationContext context: Context,
            fusedLocationProviderClient: FusedLocationProviderClient,
        ): LocationTrackingManager {
            return DefaultLocationTrackingManager(
                fusedLocationProviderClient = fusedLocationProviderClient,
                context = context,
                locationRequest = LocationUtils.locationRequestBuilder.build()
            )
        }

    }

    /**
     * Binds the DefaultTrackingServiceManager implementation to the TrackingServiceManager interface.
     */
    @Binds
    @Singleton
    abstract fun provideTrackingServiceManager(
        trackingServiceManager: DefaultTrackingServiceManager
    ): TrackingServiceManager

    /**
     * Binds the DefaultNotificationHelper implementation to the NotificationHelper interface.
     */
    @Binds
    @Singleton
    abstract fun provideNotificationHelper(
        notificationHelper: DefaultNotificationHelper
    ): NotificationHelper


}