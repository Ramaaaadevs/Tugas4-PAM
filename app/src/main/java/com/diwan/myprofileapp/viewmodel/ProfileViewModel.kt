package com.diwan.myprofileapp.viewmodel

import androidx.lifecycle.ViewModel
import com.diwan.myprofileapp.data.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


// VIEWMODEL: ProfileViewModel
// Menyimpan dan mengelola UI state profil.
// Dengan ViewModel, state tidak hilang saat rotasi layar (configuration change).
// StateFlow digunakan agar UI bisa otomatis update saat state berubah.


class ProfileViewModel : ViewModel() {

    // _uiState bersifat private, hanya ViewModel yang bisa mengubahnya.
    // Ini mencegah UI memodifikasi state secara langsung (enkapsulasi).
    private val _uiState = MutableStateFlow(ProfileUiState())

    // uiState yang di-expose ke UI bersifat read-only (StateFlow, bukan MutableStateFlow)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()


    // EVENT HANDLER: saveProfile
    // Dipanggil saat user menekan tombol Save di form edit.
    // Menggunakan .copy() agar field lain tidak ikut berubah.

    fun saveProfile(nama: String, bio: String) {
        _uiState.update { currentState ->
            currentState.copy(
                nama = nama,
                bio = bio
            )
        }
    }


    // EVENT HANDLER: toggleFollow
    // Mengubah state isFollowing menjadi kebalikannya (toggle).

    fun toggleFollow() {
        _uiState.update { it.copy(isFollowing = !it.isFollowing) }
    }


    // EVENT HANDLER: toggleDarkMode
    // Mengubah state isDarkMode untuk switch dark/light mode.

    fun toggleDarkMode() {
        _uiState.update { it.copy(isDarkMode = !it.isDarkMode) }
    }
}