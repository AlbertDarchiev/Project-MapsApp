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

class LocationAdapter: RecyclerView.Adapter<LocationAdapter.ViewHolder>() {
    var locations: MutableList<Location> = ArrayList()
    lateinit var context:Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById(R.id.locationName_textView) as TextView
        val latitude = view.findViewById(R.id.locationLatitude_textView) as TextView
        val longitude = view.findViewById(R.id.locationLongitude_textView) as TextView
        fun bind(location:Location, context: Context){
            title.text = location.name
            latitude.text = location.latitude.toString()
            longitude.text = location.longitude.toString()
            itemView.setOnClickListener(View.OnClickListener { Toast.makeText(context, "AAAAA" , Toast.LENGTH_SHORT).show() })
        }
    }

    fun RecyclerAdapter(locations : MutableList<Location>, context: Context){
        this.locations = locations
        this.context = context
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = locations.get(position)
        holder.bind(item, context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            layoutInflater.inflate(
                R.layout.location_info,
                parent,
                false
            )
        )
    }


    override fun getItemCount(): Int {
        return locations.size
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
//    fun setLocations(locations: List<Location>) {
//        this.locations.clear()
//        this.locations.addAll(locations)
//        notifyDataSetChanged()
//    }




}
