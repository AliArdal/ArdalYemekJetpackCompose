package com.example.graduationproject.views.pages

import android.util.Log
import android.widget.ImageView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.example.graduationproject.R
import com.example.graduationproject.ui.theme.Appbarcolor
import com.example.graduationproject.views.viewmodel.AnasayfaViewModel
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.graduationproject.views.viewmodel.FavoriViewModel
import com.example.graduationproject.views.viewmodel.YemekKayitViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Anasayfa(navController: NavController,
             anasayfaViewModel: AnasayfaViewModel,
             yemekKayitViewModel: YemekKayitViewModel,
             favoriViewModel: FavoriViewModel
) {

    val tf = remember { mutableStateOf("") }
    val yemeklerListesi by anasayfaViewModel.yemeklerListesi.observeAsState(emptyList())
    var showAnimation by remember { mutableStateOf(false) }

    var dropdownExpanded by remember { mutableStateOf(false) }
    var sortOption by remember { mutableStateOf("") }
    val gridState = rememberLazyGridState()


    var filterDropdownExpanded by remember { mutableStateOf(false) }
    var filterOption by remember { mutableStateOf("Tümü") }


    val filtrelenmisYemeklerListesi = remember(tf.value, yemeklerListesi, sortOption, filterOption) {
        var filteredList = yemeklerListesi

        if (tf.value.isNotEmpty()) {
            filteredList = filteredList.filter { it.yemek_adi.contains(tf.value, ignoreCase = true) }
        }

        when (sortOption) {
            "Fiyata Göre Artan" -> filteredList = filteredList.sortedBy { it.yemek_fiyat }
            "Fiyata Göre Azalan" -> filteredList = filteredList.sortedByDescending { it.yemek_fiyat }
        }

        when (filterOption) {
            "Tümü" -> filteredList
            "200 TL ve altı" -> filteredList.filter { it.yemek_fiyat <= 200 }
            "300 TL ve altı" -> filteredList.filter { it.yemek_fiyat <= 300 }
            "400 TL ve altı" -> filteredList.filter { it.yemek_fiyat <= 400 }
            else -> filteredList
        }
    }
    LaunchedEffect(sortOption,filterOption) {
        gridState.scrollToItem(0)
    }

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
                shape = RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp),
                color = Appbarcolor
            ) {
                CustomTopAppBar(navController)
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(4.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.location),
                                contentDescription = "Location Icon",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "Evim",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                )
                                Text(
                                    text = "Ümraniye/İstanbul",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Color.Gray
                                    )
                                )
                            }
                            Image(
                                painter = painterResource(id = R.drawable.arrowdown),
                                contentDescription = "Arrow Down",
                                modifier = Modifier.size(24.dp)
                            )
                        }


                    }
                }
            }

            TextField(
                value = tf.value,
                onValueChange = { tf.value = it },
                placeholder = { Text(text = "Yemek ara") },
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = "Search Icon"
                    )
                },
                trailingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.mic),
                        contentDescription = "Mic Icon"
                    )
                },
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        Color.Gray,
                        MaterialTheme.shapes.medium.copy(all = CornerSize(8.dp))
                    )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    Button(
                        onClick = { dropdownExpanded = !dropdownExpanded },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .border(2.dp, Appbarcolor, RoundedCornerShape(20.dp))
                            .height(35.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.updown),
                            contentDescription = "",
                            modifier = Modifier.size(14.dp),
                            tint = Appbarcolor
                        )
                        Text(text = "Sırala")
                    }

                    DropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = { dropdownExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = "Fiyata Göre Artan") },
                            onClick = {
                                sortOption = "Fiyata Göre Artan"
                                dropdownExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Fiyata Göre Azalan") },
                            onClick = {
                                sortOption = "Fiyata Göre Azalan"
                                dropdownExpanded = false
                            }
                        )
                    }
                }

                Box(modifier = Modifier.weight(1f)) {
                    Button(
                        onClick = { filterDropdownExpanded = !filterDropdownExpanded },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .border(2.dp, Appbarcolor, RoundedCornerShape(20.dp))
                            .height(35.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.filter),
                            contentDescription = "",
                            modifier = Modifier.size(14.dp),
                            tint = Appbarcolor
                        )
                        Text(text = "Filtrele")
                    }

                    DropdownMenu(
                        expanded = filterDropdownExpanded,
                        onDismissRequest = { filterDropdownExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = "Tümü") },
                            onClick = {
                                filterOption = "Tümü"
                                filterDropdownExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "200 TL ve altı") },
                            onClick = {
                                filterOption = "200 TL ve altı"
                                filterDropdownExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "300 TL ve altı") },
                            onClick = {
                                filterOption = "300 TL ve altı"
                                filterDropdownExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "400 TL ve altı") },
                            onClick = {
                                filterOption = "400 TL ve altı"
                                filterDropdownExpanded = false
                            }
                        )
                    }
                }
            }




            LazyVerticalGrid(columns = GridCells.Fixed(2),state = gridState) {
                items(filtrelenmisYemeklerListesi, key = { yemek -> yemek.yemek_id }) { yemek ->
                    val resimUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}"
                    var quantity by remember { mutableStateOf(1) }

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                            .background(Color.White)
                    ) {
                        Card(
                            modifier = Modifier.fillMaxSize(),
                            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                            shape = MaterialTheme.shapes.medium.copy(all = CornerSize(8.dp)),
                            border = BorderStroke(1.dp, Color.White)
                        ) {
                            Column(modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate("detaySayfa/${yemek.yemek_id}/${yemek.yemek_adi}/${yemek.yemek_fiyat}/${yemek.yemek_resim_adi}")
                                }) {
                                AndroidView(
                                    factory = { context ->
                                        ImageView(context).apply {
                                            Glide.with(context)
                                                .load(resimUrl)
                                                .placeholder(R.drawable.placeholder)
                                                .into(this)
                                        }
                                    },
                                    modifier = Modifier
                                        .size(80.dp, 100.dp)
                                        .align(Alignment.CenterHorizontally)
                                )

                                Text(
                                    text = yemek.yemek_adi,
                                    fontSize = 24.sp,
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(top = 8.dp)
                                )

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${yemek.yemek_fiyat} ₺",
                                        fontSize = 20.sp,
                                        modifier = Modifier.weight(1f)
                                    )

                                    IconButton(
                                        onClick = {
                                            yemekKayitViewModel.kaydet(
                                                yemek.yemek_adi,
                                                yemek.yemek_resim_adi,
                                                yemek.yemek_fiyat,
                                                quantity
                                            )
                                            showAnimation = true
                                        },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.plus),
                                            contentDescription = "plus"
                                        )
                                    }
                                }
                            }
                        }
                        var isFavori by remember { mutableStateOf(favoriViewModel.isFavori(yemek)) }
                        IconButton(
                            onClick = {

                                isFavori = !isFavori

                                if (isFavori) {
                                    favoriViewModel.favoriEkle(yemek)
                                } else {
                                    favoriViewModel.favoriCikar(yemek)
                                }
                            },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                        ) {

                            Icon(
                                painter = painterResource(id = if (isFavori) R.drawable.unfavori else R.drawable.favori),
                                contentDescription = "Favorite",
                                tint = Appbarcolor
                            )
                        }

                    }
                }
            }
        }


        if (showAnimation) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x80000000), RoundedCornerShape(0.dp)),
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




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(navController: NavController) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 8.dp)
                    .padding(top = 4.dp, bottom = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "ARDAL",
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "YEMEK",
                        style = TextStyle(
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        actions = {
            IconButton(onClick = {navController.navigate("favorisayfa")}, modifier = Modifier.padding(8.dp)) {
                Image(painter = painterResource(id = R.drawable.heart), contentDescription = "" )
            }
            IconButton(onClick = { /* TODO */ }, modifier = Modifier.padding(8.dp)) {
                Image(painter = painterResource(id = R.drawable.chat), contentDescription = "" )
            }
            IconButton(onClick = {
                navController.navigate("hesabimSayfa"){
                    popUpTo("hesabimSayfa"){inclusive = true}
                    launchSingleTop = true
                }
            }, modifier = Modifier.padding(8.dp)) {
                Image(painter = painterResource(id = R.drawable.user), contentDescription = "" )
            }
        },
        modifier = Modifier.height(70.dp)
    )
}
