package com.complete.rapidaide


import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.complete.rapidaide.databinding.ActivityLoginUsingEmailBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login_using_email.*

class LoginUsingEmail : AppCompatActivity() {
    private var _binding: ActivityLoginUsingEmailBinding? = null
    private val binding get() = _binding!!

    private lateinit var mAuth: FirebaseAuth
    private var vemail = false




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginUsingEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        binding.signup.setOnClickListener {
            val intent = Intent(this, SignUpUsingEmail::class.java)
            startActivity(intent)
        }

        signin.setOnClickListener {

            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            verifyEmail(email, password)
        }

    }

    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {

            } else {
                Toast.makeText(this, " Authentication Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onStart() {
        super.onStart()
        val mAuth = FirebaseAuth.getInstance().currentUser
        if (mAuth != null) {
            startActivity(Intent(this, MainActivity::class.java))
            Log.d("taget", mAuth.uid)
        } else {
            super.onStart()
        }


    }

    private fun verifyEmail(email: String, password: String) {
        if (!TextUtils.isEmpty(binding.email.text.toString()) && !TextUtils.isEmpty(binding.password.text.toString())) {
            mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                vemail = mAuth.currentUser!!.isEmailVerified
                mAuth.signOut()
                Log.d("taget2", vemail.toString())
                if (vemail) {
                    login(email, password)
                } else {

                    Toast.makeText(this, "Please Verify", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {

                Toast.makeText(this, " Authentication Failed", Toast.LENGTH_SHORT).show()
            }


        } else {

            Toast.makeText(this, "Please Enter Something", Toast.LENGTH_SHORT).show()
        }
    }
}
