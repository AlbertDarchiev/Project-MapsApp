package com.example.m08_mapsapp.view

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings.Global.putString
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.m08_mapsapp.R
import com.example.m08_mapsapp.databinding.FragmentLoginBinding
import com.example.m08_mapsapp.viewmodel.MapViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    lateinit var myPreferences: SharedPreferences
    private lateinit var mapViewModel: MapViewModel

    companion object{
        var emailLogged = ""
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        val drawerLayout = view?.findViewById<DrawerLayout>(R.id.drawer_layout)
        drawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
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

            FirebaseAuth.getInstance().
            signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                binding.logButton.isEnabled = false
                if(it.isSuccessful){
                    rememberUser(email, password, binding.rememberSwitch.isChecked)
                    emailLogged = it.result?.user?.email.toString()
                    Toast.makeText(activity, "WELCOME", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_loginFragment_to_fragment_map)
                }
            }
            .addOnFailureListener { e ->
                binding.logButton.isEnabled = true
                Toast.makeText(activity, "Error al iniciar sessió", Toast.LENGTH_SHORT).show()
                Log.w(TAG, "Error login", e)
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
        val pass = myPreferences.getString("pass", "")
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