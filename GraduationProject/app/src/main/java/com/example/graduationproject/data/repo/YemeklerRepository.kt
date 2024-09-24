package com.example.graduationproject.data.repo

import com.example.graduationproject.data.datasource.YemeklerDataSource
import com.example.graduationproject.data.entity.CRUDCevap
import com.example.graduationproject.data.entity.SepetYemekler
import com.example.graduationproject.data.entity.SepettekiYemeklerCevap
import com.example.graduationproject.data.entity.Yemekler


class YemeklerRepository(var yds: YemeklerDataSource) {

    suspend fun yemekleriYukle(): List<Yemekler> = yds.yemekleriYukle()

    suspend fun kaydet(yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:Int,yemek_siparis_adet:Int,kullanici_adi:String) =
        yds.kaydet(yemek_adi, yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)

    suspend fun sepettekiYemekleriGetir(kullanici_adi: String): SepettekiYemeklerCevap? = yds.sepettekiYemekleriGetir(kullanici_adi)

    suspend fun sil(sepet_yemek_id: Int, kullanici_adi: String): CRUDCevap {
        return yds.sil(sepet_yemek_id, kullanici_adi)
    }


}
