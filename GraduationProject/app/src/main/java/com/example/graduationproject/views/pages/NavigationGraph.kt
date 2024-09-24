package com.example.graduationproject.views.pages

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.graduationproject.views.viewmodel.AnasayfaViewModel
import com.example.graduationproject.views.viewmodel.FavoriViewModel
import com.example.graduationproject.views.viewmodel.SepetViewModel
import com.example.graduationproject.views.viewmodel.YemekKayitViewModel

@Composable
fun NavigationGraph(navController: NavHostController,
                    anasayfaViewModel: AnasayfaViewModel,
                    yemekKayitViewModel: YemekKayitViewModel,
                    sepetViewModel: SepetViewModel,
                    favoriViewModel: FavoriViewModel
) {
    NavHost(navController = navController, startDestination = "bottomBarSayfa") {
        composable("bottomBarSayfa") {
            BottomBarSayfa(navController, anasayfaViewModel,yemekKayitViewModel,favoriViewModel)
        }
        composable("anasayfa") {
            Anasayfa(navController, anasayfaViewModel,yemekKayitViewModel,favoriViewModel)
        }
        composable("sepetsayfa") {
            SepetSayfa(navController,sepetViewModel)
        }
        composable(
            route = "detaySayfa/{yemekId}/{yemekAdi}/{yemekFiyati}/{yemekResimAdi}",
            arguments = listOf(
                navArgument("yemekId") { type = NavType.IntType },
                navArgument("yemekAdi") { type = NavType.StringType },
                navArgument("yemekFiyati") { type = NavType.IntType },
                navArgument("yemekResimAdi") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            DetaySayfa(
                navController = navController,
                anasayfaViewModel = anasayfaViewModel,
                yemekKayitViewModel = yemekKayitViewModel,
                yemekId = backStackEntry.arguments?.getInt("yemekId") ?: 0,
                yemekAdi = backStackEntry.arguments?.getString("yemekAdi") ?: "",
                yemekFiyati = backStackEntry.arguments?.getInt("yemekFiyati") ?: 0,
                yemekResimAdi = backStackEntry.arguments?.getString("yemekResimAdi") ?: ""
            )
        }

        composable("favorisayfa")
        {
            FavorilerSayfa(navController,favoriViewModel,yemekKayitViewModel)
        }
        composable("hesabimSayfa")
        {
            HesabimSayfa(navController)
        }
        composable("siparislerimsayfa")
        {
            SiparislerimSayfa(navController)
        }
    }
}
