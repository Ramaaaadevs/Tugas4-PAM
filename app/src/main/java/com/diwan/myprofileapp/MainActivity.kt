package com.diwan.myprofileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.diwan.myprofileapp.ui.ProfileScreen
import com.diwan.myprofileapp.viewmodel.ProfileViewModel


// MainActivity tidak banyak berubah dari minggu lalu.
// Penambahan: theme sekarang mengikuti state isDarkMode dari ViewModel.


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            // ViewModel dibuat di sini agar bisa dishare ke MaterialTheme
            // (untuk dark mode) dan ke ProfileScreen sekaligus.
            val viewModel: ProfileViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsState()

            // Pilih color scheme berdasarkan state isDarkMode dari ViewModel
            val colorScheme = if (uiState.isDarkMode) darkColorScheme() else lightColorScheme()

            MaterialTheme(colorScheme = colorScheme) {
                Surface {
                    ProfileScreen(viewModel = viewModel)
                }
            }
        }
    }
}