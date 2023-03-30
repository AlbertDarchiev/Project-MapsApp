package com.example.m08_mapsapp.viewmodel
import android.net.Uri
import com.example.m08_mapsapp.model.Location
import androidx.lifecycle.ViewModel

class MapViewModel: ViewModel() {
    var locationMap = Location("", "", "", "")
    var listOfLocations = mutableListOf<Location>()
    lateinit var imageFile : Uri
    var imageFileIsNotNull = false
    var imageFilename = ""
    var logout = false
    var locationName = ""
    var idToEdit = ""
    var editLocation = false

}