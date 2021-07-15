package com.project.segunfrancis.firebase.data

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.project.segunfrancis.chopwell.entity.MealEntity
import com.project.segunfrancis.firebase.R
import com.project.segunfrancis.firebase.utils.SignInResult

class AuthManager(private val context: Context) {
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest
    private val firebaseAuth = Database.initAuth()

    fun beginSignInRequest() {
        oneTapClient = Identity.getSignInClient(context)
        signInRequest = BeginSignInRequest.builder()
            .setPasswordRequestOptions(
                BeginSignInRequest.PasswordRequestOptions.builder()
                    .setSupported(true)
                    .build()
            )
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(context.getString(R.string.default_web_client_id))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
            // Automatically sign in when exactly one credential is retrieved.
            .setAutoSelectEnabled(true)
            .build()
    }

    fun displaySignInUI(): IntentSender? {
        return oneTapClient.beginSignIn(signInRequest)
            .result?.pendingIntent?.intentSender
    }

    fun oneTouchSignIn(intent: Intent?): SignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val idToken = credential.googleIdToken
        val username = credential.id
        val password = credential.password
        return try {
            when {
                idToken != null -> SignInResult.IdToken(token = idToken)
                password != null -> SignInResult.Password(username = username, password = password)
                else -> SignInResult.Error("Something went wrong")
            }
        } catch (t: Throwable) {
            SignInResult.Error(t.localizedMessage)
        }
    }

    fun googleSignIn(): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(context, gso)
        return googleSignInClient.signInIntent
    }

    fun isUserSignedIn(): Boolean {
        // if GoogleSignIn.getLastSignedInAccount is not null, user is already signed in
        return GoogleSignIn.getLastSignedInAccount(context) != null
    }

    fun googleSignInResult(intent: Intent?) {
        val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(intent)
        try {
            val account = task.getResult(ApiException::class.java)
            account?.let { firebaseAuthWithGoogle(it) }
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user: FirebaseUser? = firebaseAuth.currentUser
                    val userId = user?.uid
                    val userEmail = user?.email
                    val model =
                        MealEntity(userId, userEmail)
                    user?.let { Database.init().reference.child("users").child(it.uid).setValue(model) }
                } else {
                    // If sign in fails, display a message to the user.

                }
            }
    }
}

