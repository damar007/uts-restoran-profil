package com.example.restoranprofill

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestoranApp(this)
        }
    }
}

data class MenuItem(
    val nama: String,
    val harga: String,
    val deskripsi: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestoranApp(context: Context) {

    val prefs = context.getSharedPreferences("resto", Context.MODE_PRIVATE)

    var selectedIndex by remember { mutableIntStateOf(0) }

    var namaRestoran by remember {
        mutableStateOf(
            prefs.getString("nama", "Rasa Nusantara") ?: "Rasa Nusantara"
        )
    }

    var alamat by remember {
        mutableStateOf(
            prefs.getString("alamat", "Surabaya") ?: "Surabaya"
        )
    }

    var deskripsi by remember {
        mutableStateOf(
            prefs.getString("deskripsi", "Masakan Indonesia") ?: "Masakan Indonesia"
        )
    }

    var jamBuka by remember {
        mutableStateOf(
            prefs.getString("jam", "08:00 - 22:00") ?: "08:00 - 22:00"
        )
    }

    val menuList = listOf(
        MenuItem("Nasi Goreng", "Rp22.000", "Nasi goreng spesial"),
        MenuItem("Mie Ayam", "Rp18.000", "Mie ayam gurih"),
        MenuItem("Ayam Bakar", "Rp25.000", "Ayam bakar manis"),
        MenuItem("Es Teh", "Rp5.000", "Minuman segar"),
        MenuItem("Jus Alpukat", "Rp12.000", "Jus alpukat creamy")
    )

    var selectedMenu by remember {
        mutableStateOf<MenuItem?>(null)
    }

    var editMode by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Restoran Profil")
                }
            )
        },

        bottomBar = {
            NavigationBar {

                NavigationBarItem(
                    selected = selectedIndex == 0,
                    onClick = { selectedIndex = 0 },
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("Home") }
                )

                NavigationBarItem(
                    selected = selectedIndex == 1,
                    onClick = { selectedIndex = 1 },
                    icon = { Icon(Icons.Default.Restaurant, null) },
                    label = { Text("Menu") }
                )

                NavigationBarItem(
                    selected = selectedIndex == 2,
                    onClick = { selectedIndex = 2 },
                    icon = { Icon(Icons.Default.Person, null) },
                    label = { Text("Profile") }
                )
            }
        }

    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            when {

                selectedMenu != null -> {
                    DetailMenuScreen(
                        menu = selectedMenu!!,
                        onBack = {
                            selectedMenu = null
                        }
                    )
                }

                editMode -> {
                    EditProfileScreen(
                        namaRestoran,
                        alamat,
                        deskripsi,
                        jamBuka,

                        onSave = { n, a, d, j ->

                            namaRestoran = n
                            alamat = a
                            deskripsi = d
                            jamBuka = j

                            prefs.edit()
                                .putString("nama", n)
                                .putString("alamat", a)
                                .putString("deskripsi", d)
                                .putString("jam", j)
                                .apply()

                            editMode = false
                        },

                        onCancel = {
                            editMode = false
                        }
                    )
                }

                selectedIndex == 0 -> {
                    HomeScreen(
                        namaRestoran,
                        onMenu = {
                            selectedIndex = 1
                        },
                        onProfile = {
                            selectedIndex = 2
                        }
                    )
                }

                selectedIndex == 1 -> {
                    MenuScreen(
                        menuList
                    ) {
                        selectedMenu = it
                    }
                }

                else -> {
                    ProfileScreen(
                        namaRestoran,
                        alamat,
                        deskripsi,
                        jamBuka
                    ) {
                        editMode = true
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    nama: String,
    onMenu: () -> Unit,
    onProfile: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            nama,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onMenu) {
            Text("Lihat Menu")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onProfile) {
            Text("Profil Restoran")
        }
    }
}

@Composable
fun MenuScreen(
    menuList: List<MenuItem>,
    onClick: (MenuItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        items(menuList) { item ->

            Text(
                "🍽 ${item.nama} - ${item.harga}",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onClick(item)
                    }
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun DetailMenuScreen(
    menu: MenuItem,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(menu.nama)

        Text(menu.harga)

        Text(menu.deskripsi)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onBack) {
            Text("Kembali ke Menu")
        }
    }
}

@Composable
fun ProfileScreen(
    nama: String,
    alamat: String,
    deskripsi: String,
    jam: String,
    onEdit: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text("Nama: $nama")
        Text("Alamat: $alamat")
        Text("Deskripsi: $deskripsi")
        Text("Jam buka: $jam")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onEdit) {
            Text("Edit Profil")
        }
    }
}

@Composable
fun EditProfileScreen(
    namaAwal: String,
    alamatAwal: String,
    deskripsiAwal: String,
    jamAwal: String,
    onSave: (String, String, String, String) -> Unit,
    onCancel: () -> Unit
) {

    var nama by remember { mutableStateOf(namaAwal) }
    var alamat by remember { mutableStateOf(alamatAwal) }
    var deskripsi by remember { mutableStateOf(deskripsiAwal) }
    var jam by remember { mutableStateOf(jamAwal) }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        OutlinedTextField(
            value = nama,
            onValueChange = {
                nama = it
            },
            label = {
                Text("Nama Restoran")
            }
        )

        OutlinedTextField(
            value = alamat,
            onValueChange = {
                alamat = it
            },
            label = {
                Text("Alamat")
            }
        )

        OutlinedTextField(
            value = deskripsi,
            onValueChange = {
                deskripsi = it
            },
            label = {
                Text("Deskripsi")
            }
        )

        OutlinedTextField(
            value = jam,
            onValueChange = {
                jam = it
            },
            label = {
                Text("Jam Buka")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onSave(
                    nama,
                    alamat,
                    deskripsi,
                    jam
                )
            }
        ) {
            Text("Simpan")
        }

        Button(
            onClick = onCancel
        ) {
            Text("Batal")
        }
    }
}