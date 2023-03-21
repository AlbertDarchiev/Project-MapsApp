package com.example.m08_mapsapp.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.m08_mapsapp.R
import com.example.m08_mapsapp.databinding.LocationInfoBinding
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class LocationAdapter(private val locations: List<Location>): RecyclerView.Adapter<LocationAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = LocationInfoBinding.bind(view)
    }

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.location_info, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val location = locations[position]
        with(holder){
            binding.locationNameTextView.text = location.name
            binding.locationLatitudeTextView.text = location.latitude.toString()
            binding.locationLongitudeTextView.text = location.longitude.toString()

            val storage = FirebaseStorage.getInstance().reference.child("images/${location.image}")
            val localFile = File.createTempFile("temp", "jpeg")
            storage.getFile(localFile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                binding.locationImageImageView.setImageBitmap(bitmap)

            }.addOnFailureListener{
                println("ERROR IMAGE")
//                Toast.makeText(requireContext(), "Error downloading image!", Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun getImage(location: Location){
        val storage = FirebaseStorage.getInstance().reference.child("images/${location.image}")
        val localFile = File.createTempFile("temp", "jpeg")
        storage.getFile(localFile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
//            binding.imageView.setImageBitmap(bitmap)

        }.addOnFailureListener{
//            Toast.makeText(requireContext(), "Error downloading image!", Toast.LENGTH_SHORT) .show()
        }


    }

}
