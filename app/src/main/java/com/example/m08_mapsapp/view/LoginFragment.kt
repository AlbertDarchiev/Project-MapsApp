package com.example.m08_mapsapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.m08_mapsapp.R
import com.example.m08_mapsapp.databinding.ActivityMainBinding
import com.example.m08_mapsapp.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    companion object{
        var emailLogged = ""
    }
    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentLoginBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
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
                    emailLogged = it.result?.user?.email.toString()
                    Toast.makeText(activity, "AAAAAAAAAAAAAA", Toast.LENGTH_SHORT).show()

                    findNavController().navigate(R.id.action_loginFragment_to_fragment_map)

//                    goToHome(emailLogged!!)
                }
                else{
                    Toast.makeText(activity, "Error al registrar l'usuari", Toast.LENGTH_SHORT).show()
//                    showError("Error al registrar l'usuari")
                }
            }
        }

    }

}