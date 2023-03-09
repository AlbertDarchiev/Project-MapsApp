package com.example.m08_mapsapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.m08_mapsapp.R
import com.example.m08_mapsapp.databinding.FragmentAddLocationBinding
import com.example.m08_mapsapp.model.Location
import com.example.m08_mapsapp.view.LoginFragment.Companion.emailLogged
import com.example.m08_mapsapp.viewmodel.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore

class AddLocationFragment : Fragment() {
    private val db = FirebaseFirestore.getInstance()
    lateinit var binding: FragmentAddLocationBinding
    lateinit var map: GoogleMap
    private lateinit var mapViewModel: MapViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddLocationBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        mapViewModel = ViewModelProvider(requireActivity()).get(MapViewModel::class.java)


        binding.latEditText.setText(MapFragment.lat.toString())
        binding.longEditText.setText(MapFragment.long.toString())

        binding.button.setOnClickListener {
            db.collection("location").document(emailLogged!!).set(
                hashMapOf
                ("name" to binding.titleEditText.text.toString(),
                    "latitude" to binding.latEditText.text.toString(),
                    "longitude" to binding.longEditText.text.toString())
            )
            findNavController().navigate(R.id.action_addLocationFragment_to_fragment_map)



//            var latitude = binding.latEditText.text.toString().toDouble()
//            var longitude = binding.longEditText.text.toString().toDouble()
//            var title = binding.titleEditText.text.toString()
//            val action = MapFragmentDirections.actionFragmentMapToAddLocationFragment()
//            findNavController().navigate(action)


        }

    }
    fun createMarker(latV: Double, longV: Double, titleText: String){
        val coordinates = LatLng(latV, longV)
        val myMarker = MarkerOptions().position(coordinates).title(titleText)
        map.addMarker(myMarker)
    }
}