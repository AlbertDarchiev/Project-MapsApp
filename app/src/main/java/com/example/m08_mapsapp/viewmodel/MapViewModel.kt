package com.example.m08_mapsapp.viewmodel
import com.example.m08_mapsapp.model.Location
import androidx.lifecycle.ViewModel

class MapViewModel: ViewModel() {
    var locationMap = Location("", 0.0, 0.0)
    var listOfLocations = mutableListOf<Location>()
}