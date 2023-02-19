package com.example.firebaseopt

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseopt.dataclass.ProfileData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class WelcomeActivity : AppCompatActivity() {
    private lateinit var mDatabase: FirebaseDatabase
    var databaseReference: DatabaseReference? = null
    private lateinit var profileData:ProfileData
    private lateinit var listView:ListView
    private lateinit var  arrayAdapter: ArrayAdapter<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        mDatabase = FirebaseDatabase.getInstance()
        databaseReference = mDatabase.getReference("Testing Database")

        val user= FirebaseAuth.getInstance().currentUser
        Toast.makeText(applicationContext, "current user :"+user, Toast.LENGTH_SHORT).show()


        val idEdtEmployeeName=findViewById<EditText>(R.id.idEdtEmployeeName)
        val idEdtEmployeePhoneNumber=findViewById<EditText>(R.id.idDeptt)
        val idEdtEmployeeAddress=findViewById<EditText>(R.id.idProfile)
        val idBtnSendData=findViewById<Button>(R.id.idBtnSendData)
        val idViewButton=findViewById<Button>(R.id.idViewButton)


        idBtnSendData.setOnClickListener(){
            val empName=idEdtEmployeeName.text.toString()
            val empDeptt=idEdtEmployeePhoneNumber.text.toString()
            val edProfile=idEdtEmployeeAddress.text.toString()

            addDataToDatabase(empName,empDeptt,edProfile)
        }
        idViewButton.setOnClickListener(){
// Read from the database
            // Read from the database
            databaseReference!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val ViewData= arrayOf(profileData.name,profileData.profile,profileData.department)
                    listView=findViewById(R.id.listView)
                    arrayAdapter=ArrayAdapter(applicationContext,android.R.layout.simple_list_item_1,ViewData)
                    listView.adapter=arrayAdapter

// This method is called once with the initial value and again
                    // whenever data at this location is updated.
//                    val value = databaseReference!!.setValue(ProfileData::class.java)
//                    Toast.makeText(applicationContext, "val"+value, Toast.LENGTH_SHORT).show()
//                    Log.d("TAG", "Value is: $value")
                }

                override fun onCancelled(error: DatabaseError) {
// Failed to read value
                    Toast.makeText(applicationContext, "fail"+error.toException(), Toast.LENGTH_SHORT).show()

                    Log.w("TAG", "Failed to read value.", error.toException())
                }
            })
        }
    }

    private fun addDataToDatabase(empName: String, empDeptt: String, edProfile: String) {
        profileData= ProfileData(empName,empDeptt,edProfile)

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                databaseReference!!.setValue(profileData)
                Toast.makeText(applicationContext, "added", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
                Toast.makeText(applicationContext, "not added", Toast.LENGTH_SHORT).show()
            }
        }
        databaseReference!!.addValueEventListener(postListener)
    }
}