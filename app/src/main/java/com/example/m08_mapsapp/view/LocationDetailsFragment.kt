package com.example.m08_mapsapp.view

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
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
import com.example.m08_mapsapp.databinding.FragmentLocationDetailsBinding
import com.example.m08_mapsapp.model.Location
import com.example.m08_mapsapp.viewmodel.MapViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class LocationDetailsFragment : Fragment() {
    lateinit var binding: FragmentLocationDetailsBinding
    private val db = FirebaseFirestore.getInstance()
    lateinit var mapViewModel: MapViewModel
    lateinit var imageUri: Uri
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            if (data != null) {
                imageUri = data.data!!
                binding.imageVIew.setImageURI(imageUri)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentLocationDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapViewModel = ViewModelProvider(requireActivity()).get(MapViewModel::class.java)

        if (mapViewModel.imageFileIsNotNull){
            imageUri = mapViewModel.imageFile
            binding.imageVIew.setImageURI(imageUri)
            mapViewModel.imageFileIsNotNull = false
        }

        mapViewModel.editLocation = true
        db.collection("users").document(LoginFragment.emailLogged).collection("locations").document(mapViewModel.idToEdit)
            .get()
            .addOnSuccessListener {
                    val loc = Location(it.get("name").toString(), it.get("latitude").toString(), it.get("longitude").toString(), it.get("imageFilename").toString())
                    binding.titleEditText.setText(loc.name)
                    binding.longEditText.setText(loc.longitude)
                    binding.latEditText.setText(loc.latitude)
                mapViewModel.imageFilename = loc.image.toString()

                val storage = FirebaseStorage.getInstance().reference.child("images/${loc.image}")
                val localFile = File.createTempFile("temp", "jpeg")
                storage.getFile(localFile).addOnSuccessListener {
                    val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                    binding.imageVIew.setImageBitmap(bitmap)
                    val path = MediaStore.Images.Media.insertImage(requireContext().contentResolver, bitmap, "Title", null)
                    imageUri = Uri.parse(path)

            }}

//        binding.titleEditText.text =

        binding.deleteButton.setOnClickListener {
            db.collection("users").document(LoginFragment.emailLogged).collection("locations").document(mapViewModel.idToEdit)
                .delete()
                .addOnSuccessListener {
                    Log.d(ContentValues.TAG, "DocumentSnapshot successfully deleted!")
                    Toast.makeText(context, "Eliminat correctament!", Toast.LENGTH_SHORT).show()
                    mapViewModel.idToEdit = ""
                    findNavController().navigate(R.id.action_locationDetailsFragment_to_fragment_map2)

                }
                .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error deleting document", e) }

    }

        binding.updateButton.setOnClickListener {
            if (binding.imageVIew.drawable == null) Toast.makeText(requireContext(), "PUJA UNA IMATGE!", Toast.LENGTH_SHORT).show()
            else if (binding.titleEditText.text.toString() != "" && binding.latEditText.text.toString() != "" && binding.latEditText.text.toString() != "") {
                mapViewModel.locationName = ""
                //GUARDAR IMAGEN EN FIREBASE STORAGE
                val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
                val now = Date()
                val fileName = formatter.format(now)
                val storage = FirebaseStorage.getInstance().getReference("images/$fileName")
                mapViewModel.imageFilename = fileName
                storage.putFile(imageUri)
                    .addOnSuccessListener {
                        binding.imageVIew.setImageURI(null)
                    }

            db.collection("users").document(LoginFragment.emailLogged).collection("locations").document(mapViewModel.idToEdit)
                .update(
                    hashMapOf(
                        "name" to binding.titleEditText.text.toString(),
                        "latitude" to binding.latEditText.text.toString(),
                        "longitude" to binding.longEditText.text.toString(),
                        "imageFilename" to mapViewModel.imageFilename) as Map<String, Any>
                )
                .addOnSuccessListener {
                    Log.d(ContentValues.TAG, "Successfully updated!")
                    Toast.makeText(context, "Editat correctament!", Toast.LENGTH_SHORT).show()
                    mapViewModel.idToEdit = ""
                    findNavController().navigate(R.id.action_locationDetailsFragment_to_fragment_map2)
                }
                .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error updating document", e) }
        }
    }
        binding.takePhotoButton.setOnClickListener {
            mapViewModel.locationName = binding.titleEditText.text.toString()

            findNavController().navigate(R.id.action_locationDetailsFragment_to_photoFragment2)
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