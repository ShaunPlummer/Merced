package com.washuTechnologies.merced.di

import javax.inject.Qualifier

/**
 * A custom annotation allowing components to specify that the default coroutines dispatcher should
 * be injected via the constructor.
 */
@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher

/**
 * A custom annotation allowing components to specify that the IO coroutines dispatcher should
 * be injected via the constructor.
 */
@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

/**
 * A custom annotation allowing components to specify that the main coroutines dispatcher should
 * be injected via the constructor.
 */
@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher
