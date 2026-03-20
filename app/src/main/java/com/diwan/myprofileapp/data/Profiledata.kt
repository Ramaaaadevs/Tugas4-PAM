package com.diwan.myprofileapp.data


// DATA CLASS: ProfileUiState
// Menyimpan seluruh state UI profil dalam satu struktur.
// Penggunaan data class memudahkan update state menggunakan .copy()
// tanpa mengubah state yang lain (immutable pattern).


data class ProfileUiState(
    val nama: String = "Diwan Ramadhani Dwi Putra",
    val bio: String = "Mahasiswa Semester 6 Teknik Informatika\nInstitut Teknologi Sumatera",
    val email: String = "diwan.123140116@student.itera.ac.id",
    val phone: String = "081278437207",
    val location: String = "Institut Teknologi Sumatera, Lampung",
    val isDarkMode: Boolean = false,    // State untuk dark/light mode toggle
    val isFollowing: Boolean = false    // State tombol follow (dipindah ke sini dari UI)
)