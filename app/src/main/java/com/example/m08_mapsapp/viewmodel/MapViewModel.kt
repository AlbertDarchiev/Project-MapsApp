package com.example.m08_mapsapp.viewmodel
import android.net.Uri
import com.example.m08_mapsapp.model.Location
import androidx.lifecycle.ViewModel
import java.io.File

class MapViewModel: ViewModel() {
    var locationMap = Location("", 0.0, 0.0)
    var listOfLocations = mutableListOf<Location>()
    lateinit var imageFile : Uri

}