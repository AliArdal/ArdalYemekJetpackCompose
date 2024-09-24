package com.example.graduationproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.example.graduationproject.ui.theme.GraduationProjectTheme
import com.example.graduationproject.views.pages.NavigationGraph
import com.example.graduationproject.views.pages.SplashScreen
import com.example.graduationproject.views.viewmodel.AnasayfaViewModel
import com.example.graduationproject.views.viewmodel.FavoriViewModel
import com.example.graduationproject.views.viewmodel.SepetViewModel
import com.example.graduationproject.views.viewmodel.YemekKayitViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GraduationProjectTheme {

                val navController = rememberNavController()
                val anasayfaViewModel: AnasayfaViewModel = hiltViewModel()
                val yemekKayitViewModel: YemekKayitViewModel = hiltViewModel()
                val sepetViewModel: SepetViewModel = hiltViewModel()
                val favoriViewModel: FavoriViewModel = hiltViewModel()

                NavigationGraph(
                    navController = navController,
                    anasayfaViewModel = anasayfaViewModel,
                    yemekKayitViewModel = yemekKayitViewModel,
                    sepetViewModel = sepetViewModel,
                    favoriViewModel = favoriViewModel
                )
        }
    }
}}
