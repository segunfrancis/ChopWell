package com.project.segunfrancis.sharedpref

import android.content.Context
import com.project.segunfrancis.sharedpref.PrefConstants.ONBOARDING_SCREEN_KEY
import com.project.segunfrancis.sharedpref.PrefConstants.SHARED_PREF_NAME

class PreferenceHelper(private val context: Context) {

    private val sharedPreferences by lazy {
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    var hasUserSeenOnBoardingScreen: Boolean
        get() = sharedPreferences.getBoolean(ONBOARDING_SCREEN_KEY, false)
        set(value) = sharedPreferences.edit().putBoolean(ONBOARDING_SCREEN_KEY, value).apply()
}