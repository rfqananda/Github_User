package com.rifqiananda.githubuserv2.ui.themes

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.fragment.app.Fragment
import com.rifqiananda.githubuserv2.R
import com.rifqiananda.githubuserv2.databinding.FragmentThemeBinding
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.activityViewModels
import com.google.android.material.switchmaterial.SwitchMaterial
import com.rifqiananda.githubuserv2.adapter.UserAdapter
import com.rifqiananda.githubuserv2.databinding.LayoutAdapterBinding
import com.rifqiananda.githubuserv2.ui.view.ThemeViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class ThemeFragment : Fragment(R.layout.fragment_theme) {

    private val pref by lazy { PrefHelper(requireContext()) }

    private var _binding: FragmentThemeBinding? = null
    private val binding get() = _binding!!


    private lateinit var dataAdapter: UserAdapter

    private val viewModel by activityViewModels<ThemeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentThemeBinding.bind(view)

        dataAdapter = UserAdapter()

        binding.switchTheme.isChecked = viewModel.theme.value == AppCompatDelegate.MODE_NIGHT_YES


        binding.switchTheme.setOnCheckedChangeListener { _, b ->
            Log.d("Settings", "$b")
            val mode = if (b) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else AppCompatDelegate.MODE_NIGHT_NO
            viewModel.saveTheme(mode)
        }


//        binding.apply {
//
//            switchTheme.isChecked = pref.getBoolean("pref_is_dark_mode")
//
//            switchTheme.setOnCheckedChangeListener { buttonView, isChecked ->
//                when (isChecked) {
//                    true -> {
//                        pref.put("pref_is_dark_mode", true)
//                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                    }
//                    false -> {
//                        pref.put("pref_is_dark_mode", false)
//                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                    }
//                }
//            }
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

class ThemePreferences(private val datastore: DataStore<androidx.datastore.preferences.core.Preferences>) {

    private val key = intPreferencesKey("key")

    suspend fun saveTheme(theme: Int) {
        datastore.edit { settings ->
            val value = settings[key]
            if (value != theme) settings[key] = theme
        }
    }

    fun getTheme(): Flow<Int?> {
        return datastore.data.map { settings ->
            settings[key]
        }
    }
}

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "Store")