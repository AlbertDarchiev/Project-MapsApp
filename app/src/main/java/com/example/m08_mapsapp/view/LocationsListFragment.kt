package com.example.m08_mapsapp.view

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.m08_mapsapp.R
import com.example.m08_mapsapp.databinding.FragmentLocationsListBinding
import com.example.m08_mapsapp.model.Location
import com.example.m08_mapsapp.model.LocationAdapter
import com.example.m08_mapsapp.viewmodel.MapViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore

class LocationsListFragment : Fragment() {
    private lateinit var adapter: LocationAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var locationsList: MutableList<Location>
    private lateinit var binding: FragmentLocationsListBinding
    private val db = FirebaseFirestore.getInstance()
    private lateinit var mapViewModel: MapViewModel



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentLocationsListBinding.inflate(layoutInflater)
        return inflater.inflate(R.layout.fragment_locations_list, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapViewModel = ViewModelProvider(requireActivity()).get(MapViewModel::class.java)

        locationsList = mapViewModel.listOfLocations
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = LocationAdapter(locationsList)
        recyclerView.adapter = adapter
    }

//    private fun getLocations(){
//        locationsList = mutableListOf<Location>()
//
//        db.collection("users").document(LoginFragment.emailLogged).collection("locations")
//            .get()
//            .addOnSuccessListener { documents ->
//                var loc : Location
//                for (document in documents) {
//                    loc = Location(document.get("name").toString(), document.get("latitude").toString().toDouble(), document.get("longitude").toString().toDouble(), document.get("imageFilename").toString())
//                    println("PRINTVALUES: ${loc}")
//                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
//                    locationsList.add(Location(loc.name, loc.latitude, loc.longitude, loc.image))
//
//                }
//            }
//    }


}