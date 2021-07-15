package com.project.segunfrancis.chopwell.presentation.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
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

        authManager.initGoogleSignIn()
        if (authManager.isUserSignedIn()) {
            // Navigate to next screen
            navigateToCategoryActivity()
        }

        binding.googleSignIn.setOnClickListener { signIn() }
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            Timber.d("ActivityResult data: ${result.data?.data}")
            authManager.googleSignInResult(result.data) { success ->
                if (success) {
                    // Navigate to next screen
                    navigateToCategoryActivity()
                } else {
                    // handle error
                    Timber.d("Unable to sign in")
                }
            }

        Timber.d("ResultCode: ${result.data}")
    }

    private fun signIn() {
        Timber.d("Sign in clicked")
        val signInIntent = authManager.googleSignIn()
        Timber.d("SignIn intent uri: $signInIntent")
        resultLauncher.launch(signInIntent)
    }

    private fun navigateToCategoryActivity() {
        startActivity(Intent(this@StartActivity, CategoryActivity::class.java))
    }
}
