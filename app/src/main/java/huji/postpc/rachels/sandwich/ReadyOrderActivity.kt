package huji.postpc.rachels.sandwich

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class ReadyOrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ready_order)
        val firebaseManager = SandwichOrderApp.instance.firebaseManager
        val gotItImageButton : ImageButton = findViewById(R.id.gotItImageButton)

        gotItImageButton.setOnClickListener {
            firebaseManager.changeCurrentStatus(DONE)
            val intent = Intent(this, PlaceOrderActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() { }

}