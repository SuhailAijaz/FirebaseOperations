package com.example.firebaseopt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var mauth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mauth = FirebaseAuth.getInstance()

        val loginButton = findViewById<Button>(R.id.login_button)
        val emailEditfield = findViewById<EditText>(R.id.username_Edittxtfield)
        val passwordedField = findViewById<EditText>(R.id.password_edtxtfieldlogin)

        val forgetPaswordtxt = findViewById<TextView>(R.id.forgetpswd_Text)
        val progressBar = findViewById<ProgressBar>(R.id.progressBarLogin)

        loginButton.setOnClickListener(){
            val email=emailEditfield.text.toString()
            val password=passwordedField.text.toString()

            mauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){
                if(it.isSuccessful){
                    Toast.makeText(applicationContext, "successs", Toast.LENGTH_SHORT).show()
                    val intent= Intent(this@LoginActivity,WelcomeActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(applicationContext, "failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
}
}