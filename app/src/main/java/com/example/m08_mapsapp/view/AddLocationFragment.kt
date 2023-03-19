package com.example.m08_mapsapp.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.m08_mapsapp.R
import com.example.m08_mapsapp.databinding.FragmentAddLocationBinding
import com.example.m08_mapsapp.model.Location
import com.example.m08_mapsapp.view.LoginFragment.Companion.emailLogged
import com.example.m08_mapsapp.viewmodel.MapViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.coroutineScope

class AddLocationFragment : Fragment() {
    private val db = FirebaseFirestore.getInstance()
    lateinit var binding: FragmentAddLocationBinding
    private lateinit var mapViewModel: MapViewModel

    var currentUserDB = db.collection("users").document(emailLogged!!)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddLocationBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        mapViewModel = ViewModelProvider(requireActivity()).get(MapViewModel::class.java)

        mapViewModel = ViewModelProvider(requireActivity()).get(MapViewModel::class.java)
        Toast.makeText(activity, "${mapViewModel.locationMap.latitude}", Toast.LENGTH_SHORT).show()

        binding.latEditText.setText(mapViewModel.locationMap.latitude.toString())
        binding.longEditText.setText(mapViewModel.locationMap.longitude.toString())


        binding.button.setOnClickListener {
            mapViewModel.listOfLocations = mutableListOf()
            Toast.makeText(activity, "${mapViewModel.locationMap.latitude}", Toast.LENGTH_SHORT).show()

                    db.collection("users").document(emailLogged).collection("locations").add(
                        hashMapOf
                            ("name" to binding.titleEditText.text.toString(),
                            "latitude" to binding.latEditText.text.toString(),
                            "longitude" to binding.longEditText.text.toString())
                    )
            findNavController().navigate(R.id.action_addLocationFragment_to_fragment_map)
                }
        }
    }