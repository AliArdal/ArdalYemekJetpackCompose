package com.example.graduationproject.views.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.graduationproject.R
import com.example.graduationproject.views.viewmodel.AnasayfaViewModel
import com.example.graduationproject.views.viewmodel.FavoriViewModel
import com.example.graduationproject.views.viewmodel.YemekKayitViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun BottomBarSayfa(navController: NavHostController, anasayfaViewModel: AnasayfaViewModel,yemekKayitViewModel: YemekKayitViewModel,
                   favoriViewModel: FavoriViewModel) {
    var selectedItem by remember { mutableStateOf("anasayfa") }

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = Color(0xFFF50057),
        darkIcons = false
    )

    Scaffold(
        bottomBar = {
            BottomAppBar(modifier = Modifier.height(60.dp)) {
                NavigationBarItem(
                    selected = selectedItem == "anasayfa",
                    onClick = {
                        selectedItem = "anasayfa"
                        navController.navigate("anasayfa") {
                            popUpTo("anasayfa") { inclusive = true }
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.anasayfa_resim),
                            contentDescription = "Anasayfa"
                        )
                    },
                    label = { Text(text = "Anasayfa") }
                )

                NavigationBarItem(
                    selected = selectedItem == "sepetsayfa",
                    onClick = {
                        selectedItem = "sepetsayfa"
                        navController.navigate("sepetsayfa") {
                            popUpTo("sepetsayfa") { inclusive = true }
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.cart),
                            contentDescription = "Sepetim"
                        )
                    },
                    label = { Text(text = "Sepetim") }
                )
                NavigationBarItem(
                    selected = selectedItem == "favorisayfa",
                    onClick = {
                        selectedItem = "favorisayfa"
                        navController.navigate("favorisayfa") {
                            popUpTo("favorisayfa") { inclusive = true }
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.favori_resim),
                            contentDescription = "Favorim"
                        )
                    },
                    label = { Text(text = "Favorilerim") }
                )
                NavigationBarItem(
                    selected = selectedItem == "kampanyalar",
                    onClick = {
                        selectedItem = "kampanyalar"

                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.kampanyalar),
                            contentDescription = "Kampanyalar"
                        )
                    },
                    label = { Text(text = "Kampanyalar") }
                )

            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Anasayfa(navController = navController, anasayfaViewModel = anasayfaViewModel, yemekKayitViewModel = yemekKayitViewModel,favoriViewModel)
        }
    }
}
