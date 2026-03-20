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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.diwan.myprofileapp.viewmodel.ProfileViewModel


// COMPOSABLE 1: ProfileHeader
// Menampilkan bagian atas halaman profil: avatar, nama, dan bio.
// Header tetap pakai warna biru — tidak terpengaruh dark mode
// karena sudah gelap dari sananya.


@Composable
fun ProfileHeader(nama: String, bio: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A237E),
                        Color(0xFF283593)
                    )
                )
            )
            .padding(vertical = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF5C6BC0)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Foto Profil",
                    tint = Color.White,
                    modifier = Modifier.size(60.dp)
                )
            }

            Text(
                text = nama,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = bio,
                fontSize = 14.sp,
                color = Color(0xFFB0BEC5),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }
    }
}


// COMPOSABLE 2: InfoItem
// Komponen reusable untuk satu baris informasi kontak (icon + label + value).
// Ditambah parameter textColor agar bisa menyesuaikan dark mode dari parent.


@Composable
fun InfoItem(icon: ImageVector, label: String, value: String, textColor: Color = Color(0xFF212121)) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFFE8EAF6)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color(0xFF3949AB),
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )
            // Warna teks value ngikutin textColor dari parent
            Text(
                text = value,
                fontSize = 15.sp,
                color = textColor,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}


// COMPOSABLE 3: ProfileCard
// Ditambah parameter cardColor dan textColor agar card bisa
// menyesuaikan tampilan saat dark mode aktif.


@Composable
fun ProfileCard(
    email: String,
    phone: String,
    location: String,
    cardColor: Color = Color.White,
    textColor: Color = Color(0xFF212121)
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {

            Text(
                text = "Informasi Kontak",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A237E)
            )

            Spacer(modifier = Modifier.height(4.dp))
            HorizontalDivider(color = Color(0xFFE0E0E0))
            Spacer(modifier = Modifier.height(8.dp))

            // textColor di-pass ke InfoItem agar value teks ikut berubah
            InfoItem(icon = Icons.Default.Email,      label = "Email",    value = email,    textColor = textColor)
            InfoItem(icon = Icons.Default.Phone,      label = "Phone",    value = phone,    textColor = textColor)
            InfoItem(icon = Icons.Default.LocationOn, label = "Location", value = location, textColor = textColor)
        }
    }
}


// MAIN SCREEN: ProfileScreen
// Penambahan dari versi sebelumnya:
//   - bgColor, cardColor, textColor ditentukan berdasarkan isDarkMode
//   - Semua komponen di bawah header menerima warna yang sesuai


@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showEditScreen by remember { mutableStateOf(false) }

    // Variabel warna berdasarkan state isDarkMode dari ViewModel.
    // Semua warna ditentukan di sini agar konsisten di seluruh halaman.
    val bgColor   = if (uiState.isDarkMode) Color(0xFF121212) else Color(0xFFF5F5F5)
    val cardColor = if (uiState.isDarkMode) Color(0xFF1E1E1E) else Color.White
    val textColor = if (uiState.isDarkMode) Color.White else Color(0xFF212121)

    if (showEditScreen) {
        EditProfileScreen(
            currentNama = uiState.nama,
            currentBio = uiState.bio,
            onSave = { nama, bio -> viewModel.saveProfile(nama, bio) },
            onBack = { showEditScreen = false }
        )
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .verticalScroll(rememberScrollState())
    ) {

        ProfileHeader(nama = uiState.nama, bio = uiState.bio)

        Spacer(modifier = Modifier.height(20.dp))

        ProfileCard(
            email     = uiState.email,
            phone     = uiState.phone,
            location  = uiState.location,
            cardColor = cardColor,
            textColor = textColor
        )

        Spacer(modifier = Modifier.height(16.dp))


        // DARK MODE TOGGLE
        // Card dan teks label ikut berubah warna sesuai mode aktif.

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = cardColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (uiState.isDarkMode) "Dark Mode" else "Light Mode",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = textColor
                )
                Switch(
                    checked = uiState.isDarkMode,
                    onCheckedChange = { viewModel.toggleDarkMode() },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(0xFF1A237E)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        // TOMBOL FOLLOW, PESAN, dan EDIT PROFILE
        // Tidak ada perubahan dari versi sebelumnya.

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { viewModel.toggleFollow() },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (uiState.isFollowing) Color(0xFF5C6BC0) else Color(0xFF1A237E)
                )
            ) {
                Text(
                    text = if (uiState.isFollowing) "Following ✓" else "Follow",
                    fontWeight = FontWeight.Bold
                )
            }

            OutlinedButton(
                onClick = { },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Pesan", fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = { showEditScreen = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Edit Profile", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}