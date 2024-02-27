package com.example.m08_mapsapp.view

import android.annotation.SuppressLint
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
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.m08_mapsapp.R
import com.example.m08_mapsapp.databinding.FragmentLoginBinding
import com.example.m08_mapsapp.viewmodel.MapViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var myPreferences: SharedPreferences
    private lateinit var mapViewModel: MapViewModel

    companion object {
        var emailLogged = ""
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        activity?.onBackPressed()
        myPreferences = requireActivity().getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        setupForm()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapViewModel = ViewModelProvider(requireActivity()).get(MapViewModel::class.java)
        mapViewModel.logout = false
        binding.logButton.isEnabled = true
        checkLoggedUser()

//        binding.mailEText.setOnFocusChangeListener { view, hasFocus ->
//            view.setBackgroundResource(if (hasFocus) R.drawable.shape_edittext_focus else R.drawable.shape_edittext)
//        }

        binding.logButton.setOnClickListener {
            val email = binding.mailEText.text.toString()
            val password = binding.passEText.text.toString()
            binding.logButton.isEnabled = false
            if (email.isNotEmpty() && password.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val remember = binding.rememberSwitch.isChecked
                            rememberUser(email, password, remember)
                            emailLogged = task.result?.user?.email.toString()
                            Toast.makeText(activity, "WELCOME", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_loginFragment_to_fragment_map)
                        }
                    }
                    .addOnFailureListener { e ->
                        binding.logButton.isEnabled = true
                        Toast.makeText(activity, "Error ${e.message}", Toast.LENGTH_SHORT).show()
                        print("LOGIN Error: ${e.message}")
                    }
            } else {
                binding.logButton.isEnabled = true
                Toast.makeText(activity, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.regButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun checkLoggedUser() {
        val email = myPreferences.getString("email", "")
        val active = myPreferences.getBoolean("active", false)
        if (active && mapViewModel.logout) {
            findNavController().navigate(R.id.action_loginFragment_to_fragment_map)
        }
    }

    private fun setupForm() {
        val email = myPreferences.getString("email", "")
        val pass = myPreferences.getString("password", "")
        val remember = myPreferences.getBoolean("remember", false)
        if (!email.isNullOrEmpty()) {
            binding.mailEText.setText(email)
            binding.passEText.setText(pass)
            binding.rememberSwitch.isChecked = remember
        }
    }

    private fun rememberUser(email: String, pass: String, remember: Boolean) {
        myPreferences.edit {
            putString("email", if (remember) email else "")
            putString("password", if (remember) pass else "")
            putBoolean("remember", remember)
            putBoolean("active", remember)
            apply()
        }
    }
}
