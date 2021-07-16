package com.project.segunfrancis.chopwell.presentation.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.segunfrancis.chopwell.databinding.ActivityStartBinding
import com.project.segunfrancis.chopwell.presentation.ui.CategoryActivity
import com.project.segunfrancis.firebase.data.AuthManager
import org.koin.android.ext.android.inject
import timber.log.Timber

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding
    val authManager: AuthManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (authManager.isUserSignedIn()) {
            // Navigate to next screen
            navigateToCategoryActivity()
        }

        binding.googleSignIn.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        Timber.d("Sign in clicked")
        authManager.anonymousSignIn { response ->
            Timber.d("User email: ${response?.email}")
            Timber.d("User ID: ${response?.uid}")
            Timber.d("User isAnonymous: ${response?.isAnonymous}")
            Timber.d("User metadata: ${response?.metadata?.creationTimestamp}")
        }
    }

    private fun navigateToCategoryActivity() {
        startActivity(Intent(this@StartActivity, CategoryActivity::class.java))
    }
}
