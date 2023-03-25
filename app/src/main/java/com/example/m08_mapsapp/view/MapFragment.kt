package com.example.m08_mapsapp.view
import android.Manifest
import android.content.ContentValues
import androidx.core.view.*
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.m08_mapsapp.R
import com.example.m08_mapsapp.databinding.FragmentMapBinding
import com.example.m08_mapsapp.model.Location
import com.example.m08_mapsapp.viewmodel.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore

const val REQUEST_CODE_LOCATION = 100
class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMapLongClickListener{
    companion object{
        var lat = 0.0
        var long = 0.0
    }
    private val db = FirebaseFirestore.getInstance()
    lateinit var map: GoogleMap
    lateinit var binding: FragmentMapBinding
    private lateinit var mapViewModel: MapViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentMapBinding.inflate(layoutInflater)
        mapViewModel = ViewModelProvider(requireActivity()).get(MapViewModel::class.java)

        println("CREATETEST1: "+mapViewModel.listOfLocations)

        return binding.root

//   return rootViewf
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateMarks()
        createMap()
        binding.addButton.setOnClickListener {
            mapViewModel.locationMap = Location("null", 0.0, 0.0, "")
            findNavController().navigate(R.id.action_fragment_map_to_addLocationFragment)
        }
    }

fun createMap(){
   val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
   mapFragment?.getMapAsync(this)
}

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarker()
        enableLocation()
        map.setOnMapLongClickListener(this) //--
    }

    fun createMarker(){
    val coordinates = LatLng(41.4534227,2.1841046)
   val myMarker = MarkerOptions().position(coordinates).title("ITB")
   map.addMarker(myMarker)
   map.animateCamera(
       CameraUpdateFactory.newLatLngZoom(coordinates, 18f),
       3000, null)
}

    private fun isLocationPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun enableLocation(){
        if(!::map.isInitialized) return
        if(isLocationPermissionGranted()){
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                return
            }
            map.isMyLocationEnabled = true
        }
        else{
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(requireContext(), "Ves a la pantalla de permisos de l’aplicació i habilita el de Geolocalització", Toast.LENGTH_SHORT).show()
        }
        else{
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                map.isMyLocationEnabled = true
            }
            else{
                Toast.makeText(requireContext(), "Accepta els permisos de geolocalització",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        Toast.makeText(requireContext(), mapViewModel.imageFilename, Toast.LENGTH_SHORT).show()
        super.onResume()
        if(!::map.isInitialized) return
        if(!isLocationPermissionGranted()){
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                return
            }
            map.isMyLocationEnabled = false
            Toast.makeText(requireContext(), "Accepta els permisos de geolocalització",
                Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMapLongClick(coord: LatLng) { // --
        mapViewModel.locationMap = Location("", coord.latitude, coord.longitude, "")

            val action = MapFragmentDirections.actionFragmentMapToAddLocationFragment()
            findNavController().navigate(action)

    }
    fun updateMarks(){
        db.collection("users").document(LoginFragment.emailLogged).collection("locations")
            .get()
            .addOnSuccessListener { documents ->
                var loc : Location
                for (document in documents) {
                    loc = Location(document.get("name").toString(), document.get("latitude").toString().toDouble(), document.get("longitude").toString().toDouble(), "")
                    println("PRINTVALUES: ${loc}")
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")

                    val coordinates = LatLng(loc.latitude!!, loc.longitude!!)
                    val myMarker = MarkerOptions().position(coordinates).title(loc.name)
                    map.addMarker(myMarker)
                }
            }

//        for (location in mapViewModel.listOfLocations) {
//            println("RERER: "+location)
//            val coordinates = LatLng(location.latitude, location.longitude)
//            val myMarker = MarkerOptions().position(coordinates).title(location.name)
//            map.addMarker(myMarker)
////            map.animateCamera(
////                CameraUpdateFactory.newLatLngZoom(coordinates, 18f),
////                5000, null
////            )
//        }
    }


}