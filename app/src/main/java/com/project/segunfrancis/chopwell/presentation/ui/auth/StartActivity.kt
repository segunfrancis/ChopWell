package com.project.segunfrancis.chopwell.presentation.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.project.segunfrancis.chopwell.R
import com.project.segunfrancis.chopwell.databinding.ActivityStartBinding
import com.project.segunfrancis.chopwell.presentation.ui.categories.CategoryActivity
import com.project.segunfrancis.chopwell.presentation.ui.util.ProgressDialogMax
import com.project.segunfrancis.firebase.data.AuthManager
import com.project.segunfrancis.firebase.utils.SignUpResult
import org.koin.android.ext.android.inject
import timber.log.Timber

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding
    private val authManager: AuthManager by inject()
    private val pd: ProgressDialogMax by lazy { ProgressDialogMax(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (authManager.isUserSignedIn()) {
            navigateToCategoryActivity()
        }

        binding.googleSignIn.setOnClickListener { signIn() }
        binding.skipSignIn.setOnClickListener { displayDialog() }

        pd.setTitle("Signing In")
        pd.setMessage("Please wait...")
        pd.setCancelable(false)
    }

    private fun signIn() {
        Timber.d("Sign in clicked")
        authManager.anonymousSignIn { response ->
            when (response) {
                is SignUpResult.Loading -> {
                }
                is SignUpResult.Success -> {
                    navigateToCategoryActivity()
                }
                is SignUpResult.Error -> {
                }
            }
        }
    }

    private fun navigateToCategoryActivity() {
        startActivity(Intent(this@StartActivity, CategoryActivity::class.java))
        finish()
    }

    private fun displayDialog() {
        MaterialAlertDialogBuilder(this, R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
            .setTitle("Chop Well")
            .setIcon(R.drawable.ic_app_icon)
            .setMessage("Skipping Sign In will limit the features of this app")
            .setNegativeButton("SKIP") { dialog, _ ->
                dialog.dismiss()
                navigateToCategoryActivity()
            }
            .setPositiveButton("CANCEL") { dialog, _ ->
                dialog.dismiss()
            }
    }
}
