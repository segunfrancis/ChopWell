package com.project.segunfrancis.chopwell.presentation.ui;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.project.segunfrancis.chopwell.R;
import com.project.segunfrancis.chopwell.databaseUtil.MyDatabaseUtil;
import com.project.segunfrancis.chopwell.entity.MealEntity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StartActivity extends AppCompatActivity {

    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private static final int RC_SIGN_IN = 9001;
    private ProgressDialog pd;

  /*
  @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Enabling disk persistence for offline functionality
        MyDatabaseUtil.getDatabase();

        Button googleSignIn = findViewById(R.id.google_signIn);
        Button skipSignIn = findViewById(R.id.skip_signIn);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        mAuth = FirebaseAuth.getInstance();

        // Initiating progress dialog
        pd = new ProgressDialog(this);
        pd.setTitle("Signing In");
        pd.setMessage("Please wait...");
        pd.setCancelable(false);

        googleSignIn.setOnClickListener(v -> {
            pd.show();
            signIn();
        });

        mAuthListener = firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() != null) {
                if (MyDatabaseUtil.isAdmin()) {
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(StartActivity.this, CategoryActivity.class));
                }
                finish();
            }
        };

        // Configure Google SignIn
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, connectionResult -> Toast.makeText(StartActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show())
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                .build();

        skipSignIn.setOnClickListener(v ->
                new MaterialAlertDialogBuilder(StartActivity.this, R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
                        .setTitle("Chop Well")
                        .setIcon(R.drawable.ic_app_icon)
                        .setMessage("Skipping Sign In will limit the features of this app")
                        .setNegativeButton("SKIP", (dialog1, which) -> navigateToCategoryActivity())
                        .setPositiveButton("CANCEL", (dialog12, which) -> dialog12.dismiss()).show());
    }

    public void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the intent from GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(getApplicationContext(), "Google Sign in Failed", Toast.LENGTH_SHORT).show();
                Log.d("StartActivity", e.getLocalizedMessage());
                pd.dismiss();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                        FirebaseUser user = mAuth.getCurrentUser();
                        String userId = user.getUid();
                        String userEmail = user.getEmail();
                        MealEntity model = new MealEntity(userId, userEmail);
                        myRef.child("users").child(user.getUid()).setValue(model);
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(getApplicationContext(), "Authentication Failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                });
    }

    private void navigateToCategoryActivity() {
        startActivity(new Intent(StartActivity.this, CategoryActivity.class));
    }
    */
}
