package huji.postpc.rachels.sandwich

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore



class MainActivity : AppCompatActivity() {

    var firebaseManager : SandwichOrdersFirebaseManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (firebaseManager == null) {
            firebaseManager = SandwichOrderApp.instance.firebaseManager
        }
        val currentOrder = firebaseManager!!.getCurrentOrder()
        if (currentOrder == null){
            val intent = Intent(this, PlaceOrderActivity::class.java)
            startActivity(intent)
        }
        else {
            val intent = Intent(this, EditOrderActivity::class.java)
            startActivity(intent)
        }


    }


}