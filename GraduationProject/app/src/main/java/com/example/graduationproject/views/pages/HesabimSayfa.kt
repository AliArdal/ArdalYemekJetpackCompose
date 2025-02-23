package com.example.graduationproject.views.pages

import android.app.Activity
import android.view.Surface
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.graduationproject.R
import com.example.graduationproject.data.entity.HesabimKategoriler
import com.example.graduationproject.ui.theme.Appbarcolor
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HesabimSayfa(navController: NavController) {

    val kategoriler = remember { mutableStateListOf<HesabimKategoriler>() }

    LaunchedEffect(key1 = true) {
        val f1 = HesabimKategoriler(1,"adres","Adreslerim")
        val f2 = HesabimKategoriler(2,"siparislerim","Siparişlerim")
        val f3 = HesabimKategoriler(3,"favori_resim","Favorilerim")
        val f4 = HesabimKategoriler(4,"kartlarim","Kayıtlı Kartlarım")
        val f5 = HesabimKategoriler(5,"hesapayarlari","Hesap Ayarları")
        val f6 = HesabimKategoriler(6,"language","Dil & Uygulama Ayarları")
        val f7 = HesabimKategoriler(7,"sorular","Sıkça Sorunlar Sorular")
        val f8 = HesabimKategoriler(8,"logout","Çıkış Yap")

        kategoriler.add(f1)
        kategoriler.add(f2)
        kategoriler.add(f3)
        kategoriler.add(f4)
        kategoriler.add(f5)
        kategoriler.add(f6)
        kategoriler.add(f7)
        kategoriler.add(f8)



    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = Appbarcolor,
        darkIcons = false
    )

    Scaffold(topBar = {
        Surface(
            shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp),
            color = Appbarcolor
        ) {
            CenterAlignedTopAppBar(
                title = { Text(text = "Hesabım") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("bottomBarSayfa") {
                            popUpTo("bottomBarSayfa") { inclusive = true }
                            launchSingleTop = true
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Appbarcolor, titleContentColor = Color.White)
            )
        }
    }){paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Merhaba Ali!", fontWeight = FontWeight.Bold, fontSize = 18.sp)

            LazyVerticalGrid(modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 8.dp,
                    bottom = paddingValues.calculateBottomPadding()
                ) // Adjust top padding
                .padding(horizontal = 16.dp),
                columns = GridCells.Fixed(1)
            ) {
                items(
                    count = kategoriler.count(),
                    itemContent = {
                        val title = kategoriler[it]
                        Card(modifier = Modifier.padding(all = 5.dp)) {

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        Color.White,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .clickable {
                                        if (title.id == 3) {
                                            navController.navigate("favorisayfa")
                                        }
                                        if(title.id==2){
                                            navController.navigate("siparislerimsayfa")
                                        }
                                    }
                            ){
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    val resimId = LocalContext.current.resources.getIdentifier(title.resim, "drawable", LocalContext.current.packageName)


                                    Image(
                                        painter = painterResource(id = resimId),
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp, 24.dp)
                                    )

                                    Spacer(modifier = Modifier.width(16.dp))

                                    Text(
                                        text = title.title,
                                        fontSize = 16.sp,
                                        color = Color.Black,
                                        modifier = Modifier.align(Alignment.CenterVertically)
                                    )
                                    Spacer(modifier = Modifier.weight(1f))

                                  Image(painter = painterResource(id = R.drawable.rightarrow), contentDescription ="", modifier = Modifier.size(24.dp) )
                                }


                            }

                        }
                    }
                )

            }
        }

    }

}