package com.example.firebaseopt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()


        val nameEditField=findViewById<EditText>(R.id.name_editfield)
        val emailEditField=findViewById<EditText>(R.id.email_Edittxtfield)
        val passwordEditField=findViewById<EditText>(R.id.password_edtxtfield)
        val loginText=findViewById<TextView>(R.id.login_txt)
        loginText.setOnClickListener(){
            val intent=Intent(this@MainActivity,LoginActivity::class.java)
            startActivity(intent)
        }
        val progressBar=findViewById<ProgressBar>(R.id.progressBarSignUp)
        val signupButton=findViewById<Button>(R.id.signup_button)

        signupButton.setOnClickListener(){
            val name=nameEditField.text.toString()
            val email=emailEditField.text.toString()
            val password=passwordEditField.text.toString()

            if(name.isEmpty()){
                nameEditField.error="FIll this field first"
            }
            if(email.isEmpty()){
                emailEditField.error="enter the email first"
            }
            if(password.isEmpty()){
                passwordEditField.error="enter the password first"
            }

            mAuth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful){
                        Toast.makeText(this, "successf."+it.exception, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Authentication failed."+it.exception, Toast.LENGTH_SHORT).show()
                    }
                }




        }
    }
}