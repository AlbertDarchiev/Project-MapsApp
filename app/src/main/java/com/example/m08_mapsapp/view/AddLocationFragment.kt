package com.example.m08_mapsapp.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.m08_mapsapp.R
import com.example.m08_mapsapp.databinding.FragmentAddLocationBinding
import com.example.m08_mapsapp.view.LoginFragment.Companion.emailLogged
import com.example.m08_mapsapp.viewmodel.MapViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.firebase.firestore.FirebaseFirestore

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


        mapViewModel = ViewModelProvider(this).get(MapViewModel::class.java)


        binding.latEditText.setText(MapFragment.lat.toString())
        binding.longEditText.setText(MapFragment.long.toString())

        mapViewModel.locationMap.observe(viewLifecycleOwner){ Location ->
            Toast.makeText(activity, "AAAAAA ${Location}", Toast.LENGTH_SHORT).show()

//            Log.d("AddLocationFragment", "Location: $location")
        }
        binding.button.setOnClickListener {
            Toast.makeText(activity, "${mapViewModel.locationMap.value?.latitude}", Toast.LENGTH_SHORT).show()

            updateMarks()
                    db.collection("users").document(emailLogged).collection("locations").add(
                        hashMapOf
                            ("name" to binding.titleEditText.text.toString(),
                            "latitude" to binding.latEditText.text.toString(),
                            "longitude" to binding.longEditText.text.toString())
                    )
                    findNavController().navigate(R.id.action_addLocationFragment_to_fragment_map)
                }
        }
    fun updateMarks(): MutableList<String>{
        val listLocations = mutableListOf<String>()
        db.collection("users").document(emailLogged).collection("locations")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    listLocations.add(document.get("name") as String)
                    println("TESTDOC: ${document.get("latitude")}")
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
        println("LISTLOCAT: "+ listLocations)
        return listLocations

        }
    }
