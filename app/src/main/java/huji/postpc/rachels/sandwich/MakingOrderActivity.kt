package huji.postpc.rachels.sandwich

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide


class MakingOrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_making_order)
        val sandwichImageView : ImageView = findViewById(R.id.sandwichImageView)
        Glide.with(SandwichOrderApp.instance)
            .load(R.drawable.ic_sandwich)
            .into(sandwichImageView)
    }
}