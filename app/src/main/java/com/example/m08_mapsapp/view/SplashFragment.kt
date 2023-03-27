package com.example.m08_mapsapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.m08_mapsapp.R

class SplashFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

}


//<com.airbnb.lottie.LottieAnimationView
//android:id="@+id/animationView"
//android:layout_width="match_parent"
//android:layout_height="wrap_content"
//app:lottie_url="https://assets3.lottiefiles.com/packages/lf20_1ezvulr6.json"
//app:lottie_autoPlay="true"
//app:lottie_loop="true"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintBottom_toBottomOf="parent"
//app:layout_constraintTop_toTopOf="parent"/>