package com.dburgnerjr.movietvshowdatabase

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.dburgnerjr.movietvshowdatabase.features.movies.MovieFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            changeFragment(MovieFragment())
        }
    }

    fun changeFragment(f: Fragment, bCleanStack: Boolean = false) {
        val ft = supportFragmentManager.beginTransaction();
        if (bCleanStack) {
            clearBackStack();
        }
        ft.setCustomAnimations(
                R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.abc_popup_enter, R.anim.abc_popup_exit);
        ft.replace(R.id.activity_base_content, f);
        ft.addToBackStack(null);
        ft.commit();
    }

    fun clearBackStack() {
        val mgr = supportFragmentManager;
        if (mgr.backStackEntryCount > 0) {
            val firstEntry = mgr.getBackStackEntryAt(0);
            mgr.popBackStack(firstEntry.id, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    /**
     * Finish activity when reaching the last fragment.
     */
    override fun onBackPressed() {
        val fragmentMgr = supportFragmentManager;
        if (fragmentMgr.backStackEntryCount > 1) {
            fragmentMgr.popBackStack();
        } else {
            finish();
        }
    }
}
