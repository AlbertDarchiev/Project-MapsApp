package com.example.m08_mapsapp.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.m08_mapsapp.R
import com.example.m08_mapsapp.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class RegisterFragment : Fragment() {
    lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        (activity as AppCompatActivity).supportActionBar?.hide()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.regButton.setOnClickListener {
            val email = binding.mailEText.text.toString()
            val password = binding.passEText.text.toString()
            val password2 = binding.pass2EText.text.toString()

            if (email == "" || password == "" || password2 == ""){
                Toast.makeText(activity, "Fill all the fields", Toast.LENGTH_SHORT).show()
            }
            else if (password2 == password) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            LoginFragment.emailLogged = it.result?.user?.email.toString()
                            Toast.makeText(activity, "User created successfully", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(activity, "${e.message}", Toast.LENGTH_LONG).show()
                        Log.w(TAG, "Error login", e)
                    }
            }
            else Toast.makeText(activity, "Passwords don't match", Toast.LENGTH_SHORT).show()

        }

        binding.backButton.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

}