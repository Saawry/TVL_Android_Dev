package com.mostafiz.android.tvltask.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.mostafiz.android.tvltask.databinding.SettingsActivityBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: SettingsActivityBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sharedPref.edit()

        val dark = sharedPref.getBoolean("theme", true)

        binding.themeSwitch.isChecked = dark



        binding.btnUpdate.setOnClickListener {
            if (binding.themeSwitch.isChecked && !dark) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean("theme", true).apply()
                recreate()
            } else if (!binding.themeSwitch.isChecked && dark){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean("theme", false).apply()
                recreate()
            }else{
                Toast.makeText(
                    this,
                    "Settings Updated",
                    Toast.LENGTH_SHORT
                ).show()
                toMainActivity()
            }
        }


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun toMainActivity(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

}

