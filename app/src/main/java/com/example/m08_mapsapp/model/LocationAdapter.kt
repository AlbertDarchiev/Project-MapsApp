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

import com.example.m08_mapsapp.R
import com.example.m08_mapsapp.databinding.LocationInfoBinding
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class LocationAdapter(private val locations: MutableList<Location>): RecyclerView.Adapter<LocationAdapter.ViewHolder>() {
    inner class ViewHolder(view: View, listener: onItemClickListener): RecyclerView.ViewHolder(view){
        val binding = LocationInfoBinding.bind(view)
        var title: String = binding.locationNameTextView.text as String
        var latitude: String = binding.locationLatitudeTextView.text as String
        var longitude: String = binding.locationLongitudeTextView.text as String

        init {
            view.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    private lateinit var locListener: onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        locListener = listener
    }


    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.location_info, parent, false)
        return ViewHolder(view, locListener)
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentLocation = locations[position]
        with(holder){

            binding.locationNameTextView.text = currentLocation.name
            binding.locationLatitudeTextView.text = currentLocation.latitude.toString()
            binding.locationLongitudeTextView.text = currentLocation.longitude.toString()

            val storage = FirebaseStorage.getInstance().reference.child("images/${currentLocation.image}")
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
