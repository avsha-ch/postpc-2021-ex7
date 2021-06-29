package huji.postpc.rachels.sandwich

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    var firebaseManager : SandwichOrdersFirebaseManager? = null
    var currentOrder: SandwichOrder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (firebaseManager == null) {
            firebaseManager = SandwichOrderApp.instance.firebaseManager
        }
        currentOrder = firebaseManager!!.getCurrentOrder()
        when {
            (currentOrder == null || currentOrder!!.status == DONE) -> {
                val intent = Intent(this, PlaceOrderActivity::class.java)
                startActivity(intent)
            }
            currentOrder!!.status == WAITING -> {
                val intent = Intent(this, EditOrderActivity::class.java)
                startActivity(intent)
            }
            currentOrder!!.status == IN_PROGRESS -> {
                val intent = Intent(this, MakingOrderActivity::class.java)
                startActivity(intent)
            }
            currentOrder!!.status == READY -> {
                val intent = Intent(this, ReadyOrderActivity::class.java)
                startActivity(intent)
            }
        }

    }

    override fun onBackPressed() { }


}