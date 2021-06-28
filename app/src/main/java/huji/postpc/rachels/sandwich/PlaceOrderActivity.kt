package huji.postpc.rachels.sandwich

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageButton

class PlaceOrderActivity : OrderActivity() {

    private lateinit var placeOrderImageButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_place_order)
        super.onCreate(savedInstanceState)

        placeOrderImageButton = findViewById(R.id.placeOrderImageButton)


        if (sandwichOrder.customerName == null) {
            placeOrderImageButton.imageAlpha = 125
            placeOrderImageButton.isEnabled = false
        }

        nameEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (nameEditText.text.isNotEmpty()) {
                    placeOrderImageButton.imageAlpha = 255
                    placeOrderImageButton.isEnabled = true
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        placeOrderImageButton.setOnClickListener {
            if (nameEditText.text.isNotEmpty()) {
                sandwichOrder.customerName = nameEditText.text.toString()
            }
            if (commentEditText.text.isNotEmpty()) {
                sandwichOrder.comment = commentEditText.text.toString()
            }
            firebaseManager.setCurrentOrder(sandwichOrder)
            val intent = Intent(this, EditOrderActivity::class.java)
            startActivity(intent)
        }
    }
}