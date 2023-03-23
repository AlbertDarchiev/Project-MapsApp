package com.example.m08_mapsapp.view

import android.os.Bundle
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

class LocationsListFragment : Fragment() {
    private lateinit var locationAdapter: LocationAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    private lateinit var binding: FragmentLocationsListBinding

    lateinit var mRecyclerView : RecyclerView
    val mAdapter : LocationAdapter = LocationAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLocationsListBinding.inflate(layoutInflater)
        return inflater.inflate(R.layout.fragment_locations_list, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(requireActivity())[MapViewModel::class.java]
        locationAdapter = LocationAdapter()
        linearLayoutManager = LinearLayoutManager(requireContext())

        binding.myRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = locationAdapter
        }

        val locations = getLocations()
//        locationAdapter.setLocations(locations)
    }





    private fun getLocations(): MutableList<Location>{
        var locations: MutableList<Location> = ArrayList()
        locations.add(Location("TITLE1", 33.13322, 12.23325, "image"))
        locations.add(Location("TITLE3", 23.13322, 45.45453, "image2"))
        return locations
    }


}