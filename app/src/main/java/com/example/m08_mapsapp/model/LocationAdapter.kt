package com.example.m08_mapsapp.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.m08_mapsapp.R
import com.example.m08_mapsapp.databinding.LocationInfoBinding
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class LocationAdapter(private val locations: MutableList<Location>): RecyclerView.Adapter<LocationAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = LocationInfoBinding.bind(view)
        var title: String = binding.locationNameTextView.text as String
        var latitude: String = binding.locationLatitudeTextView.text as String
        var longitude: String = binding.locationLongitudeTextView.text as String

    }

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.location_info, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentLocation = locations[position]
        with(holder){
            holder.title = currentLocation.name
            holder.latitude = currentLocation.latitude.toString()
            holder.longitude = currentLocation.longitude.toString()
//            binding.locationImageImageView.setImageResource(R.drawable.eth)
//            binding.locationNameTextView.text = currentLocation.name
//            binding.locationLatitudeTextView.text = currentLocation.latitude.toString()
//            binding.locationLongitudeTextView.text = currentLocation.longitude.toString()

//            val storage = FirebaseStorage.getInstance().reference.child("images/${currentLocation.image}")
//            val localFile = File.createTempFile("temp", "jpeg")
//            storage.getFile(localFile).addOnSuccessListener {
//                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
//                binding.locationImageImageView.setImageBitmap(bitmap)
//
//            }.addOnFailureListener{
//                println("ERROR IMAGE")
////                Toast.makeText(requireContext(), "Error downloading image!", Toast.LENGTH_SHORT).show()
//            }

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
