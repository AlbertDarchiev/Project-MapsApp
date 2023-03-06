package com.example.m08_mapsapp.viewmodel

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.GoogleMap

class MapViewModel: ViewModel() {
    var map: GoogleMap? = null
}