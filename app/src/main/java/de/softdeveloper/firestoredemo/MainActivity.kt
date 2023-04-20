package de.softdeveloper.firestoredemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.softdeveloper.firestoredemo.databinding.ActivityMainBinding
import io.grpc.internal.LogExceptionRunnable

data class User(
    val first:String,
    val last:String,
    val city: String,
    val age: Int
)

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore

        val userCollection = db.collection("users")

//        userCollection.get()
//            .addOnCompleteListener {task ->
//                if (task.isSuccessful){
//                    for(document in task.result){
//                        binding.tvOutput.append("User: ${document.id}, ${document.data} name: ${document.get("first")} \n")
//                    }
//                }
//            }
        val u1 = User("frank","Neumann","Berlin",53)
        val u2 = User("peter","Meier","Hamburg",45)
        val u3 = User("Horst","Schulze","Berlin",60)
        val u4 = User("Günter","Lehmann","München",31)

        

        val map = mapOf(
            "1" to u1,
            "2" to u2,
            "3" to u3,
            "4" to u4,
        )

//        db.collection("users").apply {
//            map.forEach  {key,user ->
//                document(key).set(user)
//
//            }
//        }
        binding.tvOutput.text =""
        userCollection.whereGreaterThan("age",50).get(Source.DEFAULT)
            .addOnSuccessListener {
                it.forEach {
                    binding.tvOutput.append(it.get("last").toString() + "\n")

                }
            }
//        val user3 = userCollection.document("3")
//        user3.update("city","Bremen")
//            .addOnSuccessListener { Toast.makeText(this, "Änderung erfolgreich", Toast.LENGTH_SHORT).show() }
//
//        user3.delete()
//            .addOnSuccessListener { Toast.makeText(this, "User 3 wurde gelöscht", Toast.LENGTH_SHORT).show() }
        Log.e("TAG", "onCreate: ${ db.collection("Zutaten").document("Zucker").id} ", )
        
    }

    fun createUsersDummy(){
        var user = hashMapOf(
            "first" to "Frank"
        )

        db.collection("users")
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(this, "User wurde angelegt", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }

        var user2 = hashMapOf(
            "first" to "Hans",
            "last" to "Müller",
            "city" to "Bonn",
            "age" to 23,
            "isAdult" to true
        )

        db.collection("users")
            .add(user2)
            .addOnFailureListener { Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show() }
    }
}