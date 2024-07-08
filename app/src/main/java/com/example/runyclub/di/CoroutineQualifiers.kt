package com.example.runyclub.di

import javax.inject.Qualifier

/**
 * Contains custom annotations to qualify different types of coroutine dispatchers and scopes.
 * This helps in distinguishing between them when injecting dependencies.
 */

/**
 * Qualifier for the default coroutine dispatcher.
 */
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class DefaultDispatcher

/**
 * Qualifier for the IO coroutine dispatcher.
 */
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class IoDispatcher

/**
 * Qualifier for the Main coroutine dispatcher.
 */
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class MainDispatcher

/**
 * Qualifier for the Main Immediate coroutine dispatcher.
 */
@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainImmediateDispatcher

/**
 * Qualifier for the application-wide CoroutineScope.
 */
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope
