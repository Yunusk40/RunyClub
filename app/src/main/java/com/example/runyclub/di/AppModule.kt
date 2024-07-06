package com.example.runyclub.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.example.runyclub.roomdatabase.AppDatabase
import com.example.runyclub.tracking.location.DefaultLocationTrackingManager
import com.example.runyclub.tracking.location.LocationTrackingManager
import com.example.runyclub.tracking.location.LocationUtils
import com.example.runyclub.tracking.notification.DefaultNotificationHelper
import com.example.runyclub.tracking.notification.NotificationHelper
import com.example.runyclub.tracking.service.DefaultTrackingServiceManager
import com.example.runyclub.core.tracking.service.TrackingServiceManager
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

        @Singleton
        @Provides
        fun provideFusedLocationProviderClient(
            @ApplicationContext context: Context
        ) = LocationServices
            .getFusedLocationProviderClient(context)

        @Provides
        @Singleton
        fun provideRunningDB(
            @ApplicationContext context: Context
        ): AppDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()

        /*@Provides
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
                produceFile = { File(context.filesDir, USER_PREFERENCES_FILE_NAME) }, // Corrected method for file creation
                scope = scope.plus(ioDispatcher + SupervisorJob())
            )*/

        @Singleton
        @Provides
        fun provideLocationTrackingManager(
            @ApplicationContext context: Context,
            fusedLocationProviderClient: FusedLocationProviderClient,
        ): LocationTrackingManager {
            return DefaultLocationTrackingManager(
                fusedLocationProviderClient = fusedLocationProviderClient,
                context = context,
                locationRequest = LocationUtils.locationRequestBuilder
            )
        }

    }

}