package com.example.m08_mapsapp.viewmodel
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.m08_mapsapp.model.Location
import androidx.lifecycle.ViewModel
import com.example.m08_mapsapp.model.allLocations
import com.example.m08_mapsapp.view.LocationsListFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class MapViewModel: ViewModel() {
    var locationMap = Location("", 0.0, 0.0, "")
    var listOfLocations = mutableListOf<Location>()
    lateinit var imageFile : Uri
    var imageFileIsNotNull = false
    var imageFilename = ""
    var data = MutableLiveData<allLocations>()



//    fun fetchData(){
//        CoroutineScope(Dispatchers.IO).launch {
//            val response = LocationsListFragment
//            withContext(Dispatchers.Main) {
//                if(response.isSuccessful){
//                    data.postValue(response.body())
//                }
//                else{
//                    Log.e("Error :", response.message())
//                }
//            }
//        }
//    }
}