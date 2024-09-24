package com.example.graduationproject.views.pages

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import com.example.graduationproject.R
import com.example.graduationproject.ui.theme.Appbarcolor
import com.example.graduationproject.views.viewmodel.AnasayfaViewModel
import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.graduationproject.views.viewmodel.YemekKayitViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay



@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DetaySayfa(
    navController: NavController,
    anasayfaViewModel: AnasayfaViewModel,
    yemekId: Int,
    yemekAdi: String,
    yemekFiyati: Int,
    yemekResimAdi: String,
    yemekKayitViewModel: YemekKayitViewModel
) {
    val resimUrl = "http://kasimadalan.pe.hu/yemekler/resimler/$yemekResimAdi"
    var showAnimation by remember { mutableStateOf(false) }

    var quantity by remember { mutableStateOf(1) }
    var totalPrice by remember { mutableStateOf(0) }

    LaunchedEffect(quantity) {
        totalPrice = quantity * yemekFiyati
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = Appbarcolor,
        darkIcons = false
    )

    LaunchedEffect(Unit) {
        try {
            anasayfaViewModel.yemekleriYukle()
        } catch (e: Exception) {
            Log.e("Anasayfa", "Yemekler yüklenirken hata: ${e.message}", e)
        }
    }

    Scaffold(
        topBar = {
            Surface(
                shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp),
                color = Appbarcolor
            ) {
                CenterAlignedTopAppBar(
                    title = { Text(text = yemekAdi) },
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
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = Color.White
                    )
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            RatingBar(rating = 4)

            AndroidView(
                factory = { context ->
                    ImageView(context).apply {
                        Glide.with(context)
                            .load(resimUrl)
                            .into(this)
                    }
                },
                modifier = Modifier
                    .size(150.dp, 200.dp)
            )

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)){

                Column (modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                    verticalArrangement = Arrangement.SpaceBetween){
                    Text(text = "Taze Yemekler ve İçecekler: 500 TL Üzeri Ücretsiz Teslimat!", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Ürünlerimiz, en taze malzemelerle ve hijyenik ortamda hazırlanmaktadır. Her lokmada sağlığınızı ve lezzeti ön planda tutarak, kaliteli ve doyurucu yemek deneyimi sunuyoruz. Hızlı teslimatımızla, mutfaktaki ustalığımızı kapınıza getiriyoruz!"
                        , fontSize = 11.sp, color = Color.Gray,)
                }
            }



            Text(
                text = "$yemekFiyati ₺",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Appbarcolor
            )


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    if (quantity > 0) {
                        quantity--
                        totalPrice = quantity * yemekFiyati
                    }
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.minusdetay),
                        contentDescription = "Decrease"
                    )
                }
                Text(text = quantity.toString(), fontSize = 16.sp)
                IconButton(onClick = {
                    quantity++
                    totalPrice = quantity * yemekFiyati
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.plusdetay),
                        contentDescription = "Increase"
                    )
                }
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${totalPrice} ₺",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Button(
                    onClick = {
                        yemekKayitViewModel.kaydet(
                            yemekAdi,
                            yemekResimAdi,
                            yemekFiyati,
                            quantity
                        )
                        showAnimation = true

                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Appbarcolor,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Sepete Ekle", fontSize = 18.sp)
                }
            }
        }

        if (showAnimation) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x80000000), RoundedCornerShape(0.dp)), // Yarı saydam arka plan
                contentAlignment = Alignment.Center
            ) {
                val composition by rememberLottieComposition(LottieCompositionSpec.Asset("adcartt.json"))
                LottieAnimation(
                    composition = composition,
                    iterations = 1,
                    modifier = Modifier.size(150.dp)
                )


                LaunchedEffect(showAnimation) {
                    delay(2000)
                    showAnimation = false
                }
            }
        }
    }
}



@Composable
fun RatingBar(rating: Int) {
    val fullStar = painterResource(id = R.drawable.ic_full_star)
    val emptyStar = painterResource(id = R.drawable.ic_empty_star)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(5) { index ->
            Icon(
                painter = if (index < rating) fullStar else emptyStar,
                contentDescription = "Star",
                tint = Appbarcolor,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
