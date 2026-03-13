package com.diwan.myprofileapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


// DATA CLASS: ProfileInfo
// Menyimpan seluruh data profil pengguna dalam satu struktur.
// Penggunaan data class memungkinkan penyimpanan data yang
// bersifat immutable (tidak berubah setelah dibuat), sehingga
// lebih aman digunakan dalam UI yang bersifat deklaratif.


data class ProfileInfo(
    val nama: String,       // Nama lengkap pengguna
    val bio: String,        // Deskripsi singkat / bio
    val email: String,      // Alamat email
    val phone: String,      // Nomor telepon
    val location: String    // Lokasi / institusi
)


// COMPOSABLE 1: ProfileHeader
//
// Fungsi ini untuk menampilkan bagian atas (header) dari halaman profil, mencakup foto profil berbentuk lingkaran, nama pengguna, dan bio singkat.
// Parameter:
//   - nama : String → nama yang akan ditampilkan
//   - bio  : String → deskripsi singkat pengguna


@Composable
fun ProfileHeader(nama: String, bio: String) {

    // Box digunakan sebagai container utama header.
    // fillMaxWidth() untuk header biar bisa menuhin layar
    // background gradient memberikan efek warna dari biru tua ke biru sedang,
    // padding vertical memberi jarak atas-bawah konten di dalam header.
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A237E), // Warna biru tua (atas)
                        Color(0xFF283593)  // Warna biru sedang (bawah)
                    )
                )
            )
            .padding(vertical = 32.dp),
        contentAlignment = Alignment.Center // Seluruh konten di-center
    ) {
        // Column untuk profil, nama, bio, secara vertikal
        // dengan jarak antar elemen sebesar 12.dp
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Foto profil menggunakan box berbentuk lingkaran Karena tidak menggunakan gambar asli, digantikan dengan Icon
            Box(
                modifier = Modifier
                    .size(100.dp)           // Ukuran kotak 100x100dp
                    .clip(CircleShape)      // Dipotong menjadi bentuk lingkaran
                    .background(Color(0xFF5C6BC0)), // Warna latar avatar
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person, // Icon orang sebagai avatar
                    contentDescription = "Foto Profil",
                    tint = Color.White,
                    modifier = Modifier.size(60.dp)
                )
            }

            // Menampilkan nama pengguna dengan ukuran font 24sp dan bold
            Text(
                text = nama,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            // Menampilkan bio dengan warna lebih redup agar terlihat
            // sebagai informasi sekunder di bawah nama
            Text(
                text = bio,
                fontSize = 14.sp,
                color = Color(0xFFB0BEC5),      // Abu-abu kebiruan
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }
    }
}


// COMPOSABLE 2: InfoItem
//
// Fungsi reusable untuk menampilkan satu baris informasi kontak, terdiri dari icon, label (judul), dan value (isi).
// dipake biar bisa digunakan berulang kali untuk email,Phone, maupun Location tanpa perlu membuat fungsi terpisah.
//
// Parameter:
//   - icon  : ImageVector → icon Material yang ditampilkan di kiri
//   - label : String      → judul informasi (contoh: "Email")
//   - value : String      → isi informasi (contoh: "diwan.123140116@student.itera.ac.id)


@Composable
fun InfoItem(icon: ImageVector, label: String, value: String) {

    // Row menyusun icon dan teks secara horizontal dengan alignment vertikal di tengah biar rapih.
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Wadah icon berbentuk lingkaran dengan latar warna biru muda.
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFFE8EAF6)), // Biru muda sebagai latar icon
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color(0xFF3949AB), // Warna icon biru gelap
                modifier = Modifier.size(20.dp)
            )
        }

        // Spacer  jarak horizontal antara icon dan teks
        Spacer(modifier = Modifier.width(16.dp))

        // Column  label dan value secara vertikal di sebelah kanan icon
        Column {
            // Label ditampilkan kecil dan abu-abu sebagai judul informasi
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )
            // Value ditampilkan lebih besar dan tebal sebagai isi informasi
            Text(
                text = value,
                fontSize = 15.sp,
                color = Color(0xFF212121),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}


// COMPOSABLE 3: ProfileCard
// Fungsi ini menampilkan kartu untuk informasi user. Di dalamnya memanggil InfoItem sebanyak 3 kali menggunakan composable yang reusebale
// Parameter:
//   - info : ProfileInfo → objek data berisi semua informasi profil


@Composable
fun ProfileCard(info: ProfileInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),                          // Sudut melengkung 16dp
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), // Bayangan tipis
        colors = CardDefaults.cardColors(containerColor = Color.White)   // Latar putih
    ) {
        Column(modifier = Modifier.padding(20.dp)) {

            // Judul kartu
            Text(
                text = "Informasi Kontak",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A237E)
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Garis pemisah antara judul dan daftar informasi
            HorizontalDivider(color = Color(0xFFE0E0E0))

            Spacer(modifier = Modifier.height(8.dp))

            // Memanggil InfoItem 3 kali dengan data yang berbeda pake satu fungsi dipakai berulang tanpa duplicate kode.
            InfoItem(icon = Icons.Default.Email,      label = "Email",    value = info.email)
            InfoItem(icon = Icons.Default.Phone,      label = "Phone",    value = info.phone)
            InfoItem(icon = Icons.Default.LocationOn, label = "Location", value = info.location)
        }
    }
}

// MAIN SCREEN: ProfileScreen
// composable utama tampilan. semua composable di atas dijadiin satu halaman profil yang lengkap. Dipanggil langsung dari MainActivity.

@Composable
fun ProfileScreen() {

    // Mendefinisikan data profil pengguna menggunakan data class ProfileInfo.
    // Dengan memusatkan data di sini, perubahan data cukup dilakukan
    // di satu tempat saja.
    val profile = ProfileInfo(
        nama     = "Diwan Ramadhani Dwi Putra",
        bio      = "Mahasiswa Semester 6 Teknik Informatika\nInstitut Teknologi Sumatera",
        email    = "diwan.123140116@student.itera.ac.id",
        phone    = "081278437207",
        location = "Institut Teknologi Sumatera, Lampung"
    )

    // remember + mutableStateOf digunakan untuk menyimpan state tombol Follow.
    // Ketika nilai isFollowing berubah, Compose akan otomatis me-recompose
    // (menggambar ulang) bagian UI yang bergantung pada state ini.
    var isFollowing by remember { mutableStateOf(false) }

    // Column utama sebagai wadah seluruh konten halaman.
    // verticalScroll memungkinkan halaman di-scroll jika konten melebihi layar.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Latar abu-abu sangat muda
            .verticalScroll(rememberScrollState())
    ) {

        // Memanggil ProfileHeader untuk menampilkan bagian atas halaman
        ProfileHeader(nama = profile.nama, bio = profile.bio)

        Spacer(modifier = Modifier.height(20.dp))

        // Memanggil ProfileCard untuk menampilkan informasi kontak
        ProfileCard(info = profile)

        Spacer(modifier = Modifier.height(16.dp))

        // Row berisi dua tombol yang tersusun horizontal
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Tombol Follow bersifat interaktif — warnanya berubah
            // berdasarkan state isFollowing (contoh penggunaan state di Compose)
            Button(
                onClick = { isFollowing = !isFollowing }, // Toggle state
                modifier = Modifier.weight(1f),           // Mengisi setengah lebar
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    // Warna berbeda tergantung kondisi isFollowing
                    containerColor = if (isFollowing) Color(0xFF5C6BC0) else Color(0xFF1A237E)
                )
            ) {
                Text(
                    text = if (isFollowing) "Following ✓" else "Follow",
                    fontWeight = FontWeight.Bold
                )
            }

            // Tombol Pesan menggunakan OutlinedButton (tanpa latar, hanya garis tepi)
            OutlinedButton(
                onClick = { },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Pesan", fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}