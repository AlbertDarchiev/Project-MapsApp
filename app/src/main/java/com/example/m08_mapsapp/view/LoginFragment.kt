package com.example.m08_mapsapp.view

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.SupportActionModeWrapper
import androidx.core.content.edit
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.m08_mapsapp.R
import com.example.m08_mapsapp.databinding.FragmentLoginBinding
import com.example.m08_mapsapp.viewmodel.MapViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    lateinit var myPreferences: SharedPreferences
    private lateinit var mapViewModel: MapViewModel

    companion object{
        var emailLogged = ""
    }


    @SuppressLint("ResourceType")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        val imageView = binding.worldImage
        val animation: Animation = AnimationUtils.loadAnimation(context, R.drawable.rotate_animation)
        imageView.startAnimation(animation)
        (activity as AppCompatActivity).supportActionBar?.hide()
        //Disable go back
        activity?.onBackPressed()

        myPreferences = requireActivity().getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        setupForm()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mapViewModel = ViewModelProvider(requireActivity()).get(MapViewModel::class.java)
        mapViewModel.logout = false
        super.onViewCreated(view, savedInstanceState)
        binding.logButton.isEnabled = true
        checkLoggedUser()
        binding.logButton.setOnClickListener {
        val email = binding.mailEText.text.toString()
        val password = binding.passEText.text.toString()
        binding.logButton.isEnabled = false
            if (email != "" && password != "") {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            rememberUser(email, password, binding.rememberSwitch.isChecked)
                            emailLogged = it.result?.user?.email.toString()
                            Toast.makeText(activity, "WELCOME", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_loginFragment_to_fragment_map)
                        }
                    }
                    .addOnFailureListener { e ->
                        binding.logButton.isEnabled = true
                        Toast.makeText(activity, "Error al iniciar sessió", Toast.LENGTH_SHORT)
                            .show()
                        Log.w(TAG, "Error login", e)
                    }
            }
            else {
                binding.logButton.isEnabled = true
                Toast.makeText(activity, "Completa tots els camps", Toast.LENGTH_SHORT).show()
            }

        }
        binding.regButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

}
    private fun checkLoggedUser() {
        val email = myPreferences.getString("email", "")
        val active = myPreferences.getBoolean("active", false)
        if(active && mapViewModel.logout){
            findNavController().navigate(R.id.action_loginFragment_to_fragment_map)
        }
    }

    private fun setupForm() {
        val email = myPreferences.getString("email", "")
        val pass = myPreferences.getString("password", "")
        val remember = myPreferences.getBoolean("remember", false)
        if (email != null) {
            if(email.isNotEmpty()){
                binding.mailEText.setText(email)
                binding.passEText.setText(pass)
                binding.rememberSwitch.isChecked = remember
            }
        }
    }

    private fun rememberUser(email: String, pass: String, remember: Boolean) {
        if(remember){
            myPreferences.edit {
                putString("email", email)
                putString("password", pass)
                putBoolean("remember", remember)
                putBoolean("active", remember)
                apply()
            }
        }
        else{
            myPreferences.edit {
                putString("email", "")
                putString("password", "")
                putBoolean("remember", remember)
                putBoolean("active", remember)
                apply()
            }
        }
    }


}