package com.project.segunfrancis.chopwell.framework.local

import android.content.Context
import android.content.SharedPreferences
import com.project.segunfrancis.chopwell.data.PreferenceHelper
import com.project.segunfrancis.chopwell.presentation.utils.AppConstants.ONBOARDING_PREF_NAME
import com.project.segunfrancis.chopwell.presentation.utils.AppConstants.SHARED_PREF_NAME

class PreferenceHelperImpl(private val context: Context) : PreferenceHelper {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    override fun setSeenOnBoarding(value: Boolean) {
        sharedPreferences.edit().putBoolean(ONBOARDING_PREF_NAME, value).apply()
    }

    override fun getSeenOnBoarding(): Boolean {
        return sharedPreferences.getBoolean(ONBOARDING_PREF_NAME, false)
    }
}