package com.preeth.bookish.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.preeth.bookish.*
import com.preeth.bookish.fragment.Dashboard
import com.preeth.bookish.fragment.Favourites
import com.preeth.bookish.fragment.Info
import com.preeth.bookish.fragment.Profile

class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView
    lateinit var dashboard: MenuItem
    var previousMenu:MenuItem?= null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

                drawerLayout=findViewById(R.id.drawerLayout)
                coordinatorLayout=findViewById(R.id.coordinatorLayout)
                toolbar=findViewById(R.id.theToolbar)
                frameLayout=findViewById(R.id.frame)
                navigationView=findViewById(R.id.navigationView)

                setUpToolbar()
        setDashboardFragment()

                val actionBarDrawerToggle = ActionBarDrawerToggle(
                    this@MainActivity,
                    drawerLayout,
                    R.string.open_drawer,
                    R.string.close_drawer
                )
                drawerLayout.addDrawerListener(actionBarDrawerToggle)
                actionBarDrawerToggle.syncState()


                navigationView.setNavigationItemSelectedListener{
                    if (previousMenu !=null) {
                        previousMenu?.isChecked = false
                    }
                        it.isCheckable=true
                        it.isChecked=true
                        previousMenu=it



                    when (it.itemId)
                    {
                        R.id.dashboard ->{Toast.makeText(
                            this@MainActivity,
                            "Dashboard",
                            Toast.LENGTH_SHORT).show()
                            setDashboardFragment()


                        }

                        R.id.profile ->{Toast.makeText(
                            this@MainActivity,
                            "Profile",
                            Toast.LENGTH_SHORT).show()
                            supportFragmentManager.beginTransaction()
                                .replace(
                                    R.id.frame,
                                    Profile()
                                )
                                .commit()
                            supportActionBar?.title="Profile"
                            drawerLayout.closeDrawers()


                        }


                        R.id.favourites ->{Toast.makeText(
                            this@MainActivity,
                            "Favourites",
                            Toast.LENGTH_SHORT).show()
                            supportFragmentManager.beginTransaction()
                                .replace(
                                    R.id.frame,
                                    Favourites()
                                )
                                .commit()
                            supportActionBar?.title="Favourites"
                            drawerLayout.closeDrawers()

                        }

                        R.id.info ->{Toast.makeText(
                            this@MainActivity,
                            "Info",
                            Toast.LENGTH_SHORT).show()
                            supportFragmentManager.beginTransaction()
                                .replace(
                                    R.id.frame,
                                    Info()
                                )
                                .commit()
                            supportActionBar?.title="Info"
                            drawerLayout.closeDrawers()

                        }

                    }

                    return@setNavigationItemSelectedListener true


            }

            }
    fun setUpToolbar()
    {
        setSupportActionBar(toolbar)
        supportActionBar?.title="Bookish"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        val id=item.itemId

        if (id==android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)

        }

        return super.onOptionsItemSelected(item)
    }

fun setDashboardFragment(){
    var transaction=supportFragmentManager.beginTransaction()
    var fragmentDashboard= Dashboard()

      transaction.replace(R.id.frame,fragmentDashboard)
        transaction.commit()
    navigationView.setCheckedItem(R.id.dashboard)
    supportActionBar?.title="Dashboard"
    drawerLayout.closeDrawers()


}

    override fun onBackPressed() {
        var frag=supportFragmentManager.findFragmentById(R.id.frame)
        when(frag){
            !is Dashboard ->setDashboardFragment()

        else-> super.onBackPressed()
    }
    }
}
