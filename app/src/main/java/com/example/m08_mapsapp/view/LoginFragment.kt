package com.example.m08_mapsapp.view

import android.content.ContentValues
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
import com.example.m08_mapsapp.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    private val db = FirebaseFirestore.getInstance()
    companion object{
        var emailLogged = ""
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentLoginBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.logButton.setOnClickListener {
        val email = binding.mailEText.text.toString()
        val password = binding.passEText.text.toString()

        FirebaseAuth.getInstance().
            signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    emailLogged = it.result?.user?.email.toString()
                    Toast.makeText(activity, "WELCOME", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_loginFragment_to_fragment_map)
                }
                else{
                    Toast.makeText(activity, "Error al iniciar sessió", Toast.LENGTH_SHORT).show()
//                    showError("Error al registrar l'usuari")
                }
            }



    }
        binding.regButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.add.setOnClickListener {
            val documentReference = db.collection("test").document("asdasd")
            val data = hashMapOf(
                "name" to "testName",
            )
            documentReference.set(data)
                .addOnSuccessListener {
                    Toast.makeText(activity, "DocumentSnapshot successfully written!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(activity, "Error writing document", Toast.LENGTH_SHORT).show()
                    println("TESTPRINT")
                    Log.w(TAG, "Error writing document", e)

                }
        }
}
}