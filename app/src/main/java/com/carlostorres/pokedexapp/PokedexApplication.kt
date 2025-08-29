package com.carlostorres.pokedexapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


/**
 * Custom Application class required for Hilt.
 * The @HiltAndroidApp annotation triggers Hilt's code generation and
 * creates a dependency container that is attached to the application's lifecycle.
 */
@HiltAndroidApp
class PokedexApplication: Application()