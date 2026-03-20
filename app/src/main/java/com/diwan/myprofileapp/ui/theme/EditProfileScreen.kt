package com.diwan.myprofileapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


// COMPOSABLE: EditTextField
// Komponen TextField yang stateless — state di-hoist ke parent (EditProfileScreen).
// Reusable untuk field nama maupun bio tanpa perlu buat fungsi terpisah.
// Parameter:
//   - label         : String          → label teks di atas field
//   - value         : String          → nilai saat ini (dari parent)
//   - onValueChange : (String) -> Unit → callback saat teks berubah
//   - singleLine    : Boolean         → apakah input satu baris atau tidak


@Composable
fun EditTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true
) {
    Column {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = singleLine,
            shape = RoundedCornerShape(10.dp)
        )
    }
}


// SCREEN: EditProfileScreen
// Halaman form untuk edit nama dan bio.
// State lokal (inputNama, inputBio) di-hoist dari EditTextField ke sini,
// lalu dikirim ke ViewModel saat tombol Save ditekan.
// Parameter:
//   - currentNama : String         → nilai nama saat ini dari ViewModel
//   - currentBio  : String         → nilai bio saat ini dari ViewModel
//   - onSave      : (String, String) -> Unit → callback simpan ke ViewModel
//   - onBack      : () -> Unit     → callback kembali ke halaman profil


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    currentNama: String,
    currentBio: String,
    onSave: (String, String) -> Unit,
    onBack: () -> Unit
) {
    // State lokal untuk input form, di-inisialisasi dari nilai profil saat ini.
    // Perubahan di sini tidak langsung update ViewModel — baru update saat Save ditekan.
    var inputNama by remember { mutableStateOf(currentNama) }
    var inputBio by remember { mutableStateOf(currentBio) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Edit Profil",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    // Tombol back untuk kembali ke halaman profil
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1A237E),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // Field Nama — stateless, state di-hoist ke inputNama di atas
            EditTextField(
                label = "Nama Lengkap",
                value = inputNama,
                onValueChange = { inputNama = it }
            )

            // Field Bio — multiline karena bio bisa lebih dari satu baris
            EditTextField(
                label = "Bio",
                value = inputBio,
                onValueChange = { inputBio = it },
                singleLine = false
            )

            Spacer(modifier = Modifier.weight(1f))

            // Tombol Save — memanggil callback onSave dengan nilai input terkini,
            // lalu kembali ke halaman profil via onBack
            Button(
                onClick = {
                    onSave(inputNama, inputBio)
                    onBack()
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1A237E)
                )
            ) {
                Text(
                    text = "Simpan",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}