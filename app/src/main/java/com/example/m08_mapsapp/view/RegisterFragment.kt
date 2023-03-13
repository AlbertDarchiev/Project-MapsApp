package com.example.m08_mapsapp.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.m08_mapsapp.R
import com.example.m08_mapsapp.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class RegisterFragment : Fragment() {
    lateinit var binding: FragmentRegisterBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.regButton.setOnClickListener {
            val email = binding.mailEText.text.toString()
            val password = binding.passEText.text.toString()

            FirebaseAuth.getInstance().
            createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        LoginFragment.emailLogged = it.result?.user?.email.toString()
                        Toast.makeText(activity, "Usuari creat correctament", Toast.LENGTH_SHORT).show()

//                        val data = hashMapOf("name" to "test")



//                        db.collection("users")
//                            .document(email)
//                            .set(data)
//                            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
//                            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    }
                    else{
                        Toast.makeText(activity, "Error al registrar l'usuari", Toast.LENGTH_SHORT).show()
                    }
                }


        }

    }

}