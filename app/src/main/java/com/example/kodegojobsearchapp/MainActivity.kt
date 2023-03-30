package com.example.kodegojobsearchapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.kodegojobsearchapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Job Search"
        supportActionBar?.displayOptions


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sign_out -> {
                val bottomSheetDialogFragment = MyBottomSheetDialogFragment()
                bottomSheetDialogFragment.show(supportFragmentManager, "MyBottomSheetDialogFragment")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}