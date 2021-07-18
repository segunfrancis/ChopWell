package com.project.segunfrancis.chopwell.presentation.ui.categories

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.segunfrancis.chopwell.R
import com.project.segunfrancis.chopwell.adapter.MealAdapter
import com.project.segunfrancis.chopwell.entity.MealEntity
import com.project.segunfrancis.chopwell.presentation.ui.FavoritesActivity
import com.project.segunfrancis.chopwell.presentation.ui.MealListActivity
import com.project.segunfrancis.chopwell.presentation.ui.StartActivity
import com.project.segunfrancis.chopwell.presentation.utils.Utility
import java.util.*
import kotlin.system.exitProcess

class CategoryActivity : AppCompatActivity() {
    private var modelList: MutableList<MealEntity?>? = null
    private var searchAdapter: MealAdapter? = null
    private var searchResults: RecyclerView? = null
    private var mAuth: FirebaseAuth? = null
    private val navController: NavController by lazy { NavController(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        // Creation of the CircularProgressDrawable
        val circularProgressDrawable = CircularProgressDrawable(this@CategoryActivity)
        circularProgressDrawable.strokeWidth = 6.0f
        circularProgressDrawable.setColorSchemeColors(
            Color.WHITE,
            Color.GREEN,
            Color.rgb(216, 27, 96)
        )
        circularProgressDrawable.centerRadius = 50.0f
        circularProgressDrawable.start()
        mAuth = FirebaseAuth.getInstance()
        //searchResults = findViewById(R.id.search_recycler_view)
        //searchResults!!.setHasFixedSize(true)
        //searchResults!!.layoutManager = LinearLayoutManager(this@CategoryActivity)
        modelList = ArrayList()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val logout = menu.findItem(R.id.action_logout)
        // Implement SearchView
        val item = menu.findItem(R.id.action_search)
        val searchView = MenuItemCompat.getActionView(item) as SearchView
        searchView.queryHint = "Search meals"
        // Implement Search
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                var s = s
                if (s.length == 0) {
                    s = " "
                }
                val query = FirebaseDatabase.getInstance().getReference("meals")
                    .orderByChild("queryMealName")
                    .startAt(s.toLowerCase())
                    .endAt(s.toLowerCase() + "\uf8ff")
                modelList!!.clear()
                query.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (snapshot in dataSnapshot.children) {
                            val model = snapshot.getValue(
                                MealEntity::class.java
                            )
                            modelList!!.add(model)
                        }
                        searchAdapter = MealAdapter(this@CategoryActivity, modelList)
                        searchResults!!.adapter = searchAdapter
                        searchAdapter!!.notifyDataSetChanged()
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        modelList!!.clear()
                    }
                })
                return false
            }
        })
        if (mAuth!!.uid == null) {
            logout.title = getString(R.string.sign_in)
            invalidateOptionsMenu()
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_logout) {
            if (item.title == getString(R.string.logout)) {
                MaterialAlertDialogBuilder(
                    this@CategoryActivity,
                    R.style.ThemeOverlay_MaterialComponents_Dialog_Alert
                )
                    .setTitle("Chop Well")
                    .setIcon(R.drawable.ic_app_icon)
                    .setMessage("Do you want to Logout?")
                    .setNegativeButton(
                        "NO"
                    ) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
                    .setPositiveButton(
                        "YES"
                    ) { dialog: DialogInterface?, which: Int ->
                        mAuth!!.signOut()
                        Snackbar.make(
                            findViewById(R.id.root),
                            "You have signed out successfully",
                            Snackbar.LENGTH_INDEFINITE
                        )
                            .show()
                        recreate()
                    }
                    .create()
                    .show()
            } else if (item.title == getString(R.string.sign_in)) {
                startActivity(Intent(this@CategoryActivity, StartActivity::class.java))
            }
        } else if (item.itemId == R.id.action_favorites) {
            if (mAuth!!.uid != null) {
                startActivity(Intent(this@CategoryActivity, FavoritesActivity::class.java))
            } else {
                Snackbar.make(
                    findViewById(R.id.root),
                    "Sign in to use this feature",
                    Snackbar.LENGTH_LONG
                )
                    .setAction("SIGN IN") { view: View? -> navigateToSignInActivity() }
                    .show()
            }
        } else {
            val id = item.itemId
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.id) {
            R.id.categoryFragment -> {
                MaterialAlertDialogBuilder(
                    this@CategoryActivity,
                    R.style.ThemeOverlay_MaterialComponents_Dialog_Alert
                )
                    .setTitle("Chop Well")
                    .setIcon(R.drawable.ic_app_icon)
                    .setMessage("Do you want to exit?")
                    .setPositiveButton(
                        "YES"
                    ) { dialogInterface: DialogInterface, i: Int ->
                        dialogInterface.dismiss()
                        exitProcess(0)
                    }
                    .setNegativeButton(
                        "NO"
                    ) { dialogInterface: DialogInterface, i: Int -> dialogInterface.dismiss() }
                    .create()
                    .show()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    private fun navigateToSignInActivity() {
        startActivity(Intent(this@CategoryActivity, StartActivity::class.java))
    }
}
