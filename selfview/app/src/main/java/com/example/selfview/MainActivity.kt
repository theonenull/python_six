package com.example.selfview

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.view.animation.AnticipateOvershootInterpolator




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var text:myview=findViewById(R.id.text)
        var button:Button=findViewById(R.id.button)
        button.setOnClickListener {
            val animator: ObjectAnimator = ObjectAnimator.ofFloat(
                text, "mCurrentStep", 0f,6000f
            )
//        animator.interpolator = AnticipateOvershootInterpolator()
            animator.setDuration(2000)
            animator.start()
        }



    }
}