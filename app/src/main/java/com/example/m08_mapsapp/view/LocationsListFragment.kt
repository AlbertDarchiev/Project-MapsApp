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
    private lateinit var locationAdapter: LocationAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    private lateinit var binding: FragmentLocationsListBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_locations_list, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationAdapter = LocationAdapter(getLocations())
        linearLayoutManager = LinearLayoutManager(context)

        binding.recyclerView.apply {
            setHasFixedSize(true) //Optimitza el rendiment de l’app
            layoutManager = linearLayoutManager
            adapter = locationAdapter
        }
    }

    private fun getLocations(): MutableList<Location>{
        val locations = mutableListOf<Location>()
        locations.add(Location("TESTNAME", 52.21, 32.25, "2023_03_20_18_18_14"))
        return locations
    }


}