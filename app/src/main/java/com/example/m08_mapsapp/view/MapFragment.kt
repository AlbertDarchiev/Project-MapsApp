package com.example.m08_mapsapp.view
import android.Manifest
import androidx.core.view.*
import android.content.pm.PackageManager
import android.os.Bundle
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

const val REQUEST_CODE_LOCATION = 100
class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMapLongClickListener{
    companion object{
        var lat = 0.0
        var long = 0.0
    }
    lateinit var map: GoogleMap
    lateinit var binding: FragmentMapBinding
    private lateinit var mapViewModel: MapViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentMapBinding.inflate(layoutInflater)
        mapViewModel = ViewModelProvider(this).get(MapViewModel::class.java)

        createMap()

        return binding.root

//   return rootViewf
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
       5000, null)
}
    fun createMarker2(){
        val coordinates = LatLng(41.4534229,2.1841046)
        val myMarker = MarkerOptions().position(coordinates).title("ITB")
        map.addMarker(myMarker)

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
        long = coord.longitude
        lat = coord.latitude
        mapViewModel.locationMap.value = Location("namee", lat, long)
        Toast.makeText(requireContext(), "${mapViewModel.locationMap.value}",Toast.LENGTH_SHORT).show()
//        mapViewModel.saveLocation()

            val action = MapFragmentDirections.actionFragmentMapToAddLocationFragment()
            findNavController().navigate(action)

    }


}