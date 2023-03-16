package com.example.m08_mapsapp.viewmodel

import android.media.MicrophoneInfo.Coordinate3F
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.m08_mapsapp.model.Location
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class MapViewModel: ViewModel() {
    var map = MutableLiveData<GoogleMap>()
    val markerList = MutableLiveData<List<Marker>>()
    var locationMap = MutableLiveData<Location>()
    var testVM = MutableLiveData<String>()
}