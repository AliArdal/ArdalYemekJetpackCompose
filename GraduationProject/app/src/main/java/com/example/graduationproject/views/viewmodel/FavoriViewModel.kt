package com.example.graduationproject.views.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.graduationproject.data.entity.Yemekler

class FavoriViewModel : ViewModel() {
    private val _favoriYemekler = mutableStateListOf<Yemekler>()
    val favoriYemekler: List<Yemekler> get() = _favoriYemekler


    fun favoriEkle(yemek: Yemekler) {
        if (!_favoriYemekler.contains(yemek)) {
            _favoriYemekler.add(yemek)
        }
    }


    fun favoriCikar(yemek: Yemekler) {
        _favoriYemekler.remove(yemek)
    }


    fun isFavori(yemek: Yemekler): Boolean {
        return _favoriYemekler.contains(yemek)
    }
}
