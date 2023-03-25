package com.example.m08_mapsapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.m08_mapsapp.R
import com.example.m08_mapsapp.databinding.FragmentLocationsListBinding
import com.example.m08_mapsapp.model.Location
import com.example.m08_mapsapp.model.LocationAdapter

class LocationsListFragment : Fragment() {
    private lateinit var adapter: LocationAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var locationsList: MutableList<Location>
    private lateinit var binding: FragmentLocationsListBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentLocationsListBinding.inflate(layoutInflater)
        return inflater.inflate(R.layout.fragment_locations_list, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLocations()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = LocationAdapter(locationsList)
        recyclerView.adapter = adapter

    }

    private fun getLocations(){
        locationsList = mutableListOf<Location>()
        locationsList.add(Location("TESTNAME", 52.21, 32.25, "2023_03_20_18_18_14"))
    }


}