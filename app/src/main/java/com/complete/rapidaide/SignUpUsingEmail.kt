package com.complete.rapidaide

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast

import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up_using_email.*

class SignUpUsingEmail : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth
//    private lateinit var dbReference : DatabaseReference
    private lateinit var mProgressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_using_email)
        mAuth = FirebaseAuth.getInstance()
        signup.setOnClickListener {
            viaEmail()
           name.isEnabled = false
            password.isEnabled = false
          email.isEnabled = false
        }
    }
    private fun viaEmail()
    {

        val email = email.text.toString()
        val password = password.text.toString()
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            showProgressDialog()
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this)
                { task ->
                    if (task.isSuccessful)
                    {
                        checkEmail(email)
                    }
                    else
                    {
                        Log.d("taget",task.exception.toString())
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        hideProgressDialog()
                    }
                }.addOnFailureListener {
                   name.isEnabled = true

                    hideProgressDialog()
                }
        }
        else
        {
            Toast.makeText(this , "Please Enter Something", Toast.LENGTH_SHORT).show()
        }
    }
//    private fun addUserToDbViaEmail(name : String, email :String, uid : String)
//    {
//        dbReference = FirebaseDatabase.getInstance("https://convo-26f9d-default-rtdb.asia-southeast1.firebasedatabase.app/")
//            .reference
//        dbReference.child("user").child(uid).setValue(User(name,email,uid))
//    }
    private fun checkEmail(email:String)
    {
        val firebaseUser = mAuth.currentUser
        val name = name.text.toString().trim()
        firebaseUser?.sendEmailVerification()?.addOnCompleteListener { task ->
            if(task.isSuccessful)
            {
                Toast.makeText(this,"Verification mail sent", Toast.LENGTH_SHORT).show()
                finish()
//                addUserToDbViaEmail(name,email,mAuth.currentUser!!.uid)
                mAuth.signOut()
                val intent = Intent(this,LoginUsingEmail::class.java)
                startActivity(intent)
                hideProgressDialog()

            }
            else
            {
                Toast.makeText(this,"error occurred", Toast.LENGTH_SHORT).show()
                hideProgressDialog()
            }
        }
    }
    private fun showProgressDialog() {
        mProgressDialog = Dialog(this)

        mProgressDialog.show()
    }

    private fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }
}
