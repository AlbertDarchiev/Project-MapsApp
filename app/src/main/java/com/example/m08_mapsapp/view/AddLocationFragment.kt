package com.example.m08_mapsapp.view

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.ContentValues.TAG
import android.content.Intent
import android.media.MediaRouter.UserRouteInfo
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
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
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.coroutineScope
import org.checkerframework.checker.initialization.qual.Initialized
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class AddLocationFragment : Fragment() {
    private val db = FirebaseFirestore.getInstance()
    lateinit var binding: FragmentAddLocationBinding
    private lateinit var mapViewModel: MapViewModel
    lateinit var imageUri: Uri

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            if (data != null) {
                imageUri = data.data!!
                binding.imageVIew.setImageURI(imageUri)
            }
        }
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddLocationBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapViewModel = ViewModelProvider(requireActivity()).get(MapViewModel::class.java)
        Toast.makeText(requireContext(), mapViewModel.imageFilename, Toast.LENGTH_SHORT).show()

        if (mapViewModel.imageFileIsNotNull){
            imageUri = mapViewModel.imageFile
            binding.imageVIew.setImageURI(imageUri)
            mapViewModel.imageFileIsNotNull = false
        }

        binding.latEditText.setText(mapViewModel.locationMap.latitude.toString())
        binding.longEditText.setText(mapViewModel.locationMap.longitude.toString())

        binding.button.setOnClickListener {
            if (binding.imageVIew.drawable == null) Toast.makeText(requireContext(), "PUJA UNA IMATGE!", Toast.LENGTH_SHORT).show()
            else if (binding.titleEditText.text.toString() != "" && binding.latEditText.text.toString() != "" && binding.latEditText.text.toString() != "") {

                mapViewModel.listOfLocations = mutableListOf()

                //GUARDAR IMAGEN EN FIREBASE STORAGE
                val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
                val now = Date()
                val fileName = formatter.format(now)
                val storage = FirebaseStorage.getInstance().getReference("images/$fileName")
                mapViewModel.imageFilename = fileName
                storage.putFile(imageUri)
                    .addOnSuccessListener {
                        binding.imageVIew.setImageURI(null)
                        Toast.makeText(requireContext(), "Image uploaded!", Toast.LENGTH_SHORT).show()

                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Image not uploaded!", Toast.LENGTH_SHORT)
                            .show()
                    }

                //GUARDAR EN FIREBASE DATABASE
                db.collection("users").document(emailLogged).collection("locations").add(
                    hashMapOf
                        (
                        "name" to binding.titleEditText.text.toString(),
                        "latitude" to binding.latEditText.text.toString(),
                        "longitude" to binding.longEditText.text.toString(),
                        "imageFilename" to mapViewModel.imageFilename
                    )
                )



                findNavController().navigate(R.id.action_addLocationFragment_to_fragment_map)
            }
            else Toast.makeText(requireContext(), "COMPLETA ELS CAMPS!", Toast.LENGTH_SHORT).show()

        }
        binding.takePhotoButton.setOnClickListener {
            findNavController().navigate(R.id.action_addLocationFragment_to_photoFragment2)
        }

        binding.selectImageButton.setOnClickListener {
            selectImage()
        }


        }
    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        resultLauncher.launch(intent)
    }

}