package com.example.runyclub.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

/**
 * Module for providing various coroutine dispatchers to be used across the application.
 * This allows for more controlled and efficient use of coroutines by specifying the appropriate dispatcher for different tasks.
 */
@InstallIn(SingletonComponent::class)
@Module
object CoroutinesDispatchersModule {

    /**
     * Provides the default coroutine dispatcher that is optimized for CPU-intensive work.
     */
    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    /**
     * Provides the IO coroutine dispatcher that is optimized for IO-intensive work like reading and writing to files.
     */
    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    /**
     * Provides the Main coroutine dispatcher that is optimized for updating the UI.
     */
    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    /**
     * Provides the Main Immediate coroutine dispatcher for immediate execution on the main thread.
     */
    @MainImmediateDispatcher
    @Provides
    fun providesMainImmediateDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate

    /**
     * Provides a singleton CoroutineScope for application-wide coroutine operations.
     */
    @Singleton
    @ApplicationScope
    @Provides
    fun providesCoroutineScope(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher)

}