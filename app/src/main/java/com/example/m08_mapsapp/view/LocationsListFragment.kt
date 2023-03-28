package com.example.m08_mapsapp.view

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.component1
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

        adapter.setOnItemClickListener(object : LocationAdapter.onItemClickListener{
            var idToDelete =""
            var imageName = ""
            override fun onItemClick(position: Int) {
                imageName = mapViewModel.listOfLocations[position].image.toString()
                Toast.makeText(context, "Location deleted", Toast.LENGTH_SHORT).show()

                db.collection("users").document(LoginFragment.emailLogged).collection("locations")
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            if (imageName == document.get("imageFilename")) {
                                idToDelete = document.id
                                db.collection("users").document(LoginFragment.emailLogged).collection("locations").document(idToDelete)
                                    .delete()
                                    .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
                                    .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
                                break
                            }
                        }
                    }

            }
        })
    }



}