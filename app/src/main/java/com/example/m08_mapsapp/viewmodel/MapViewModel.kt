package com.example.m08_mapsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class MapViewModel: ViewModel() {
    var map = MutableLiveData<GoogleMap>()
    val markerList = MutableLiveData<List<Marker>>()
}