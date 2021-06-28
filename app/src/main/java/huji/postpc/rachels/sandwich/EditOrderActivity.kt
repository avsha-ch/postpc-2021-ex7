package huji.postpc.rachels.sandwich

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EditOrderActivity : OrderActivity() {

    private lateinit var saveOrderImageButton : ImageButton
    private lateinit var cancelOrderImageButton : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_edit_order)
        super.onCreate(savedInstanceState)

        saveOrderImageButton = findViewById(R.id.saveOrderImageButton)
        cancelOrderImageButton = findViewById(R.id.cancelOrderImageButton)

        pickleCounterTextView.text = sandwichOrder.pickles.toString()
        hummusIndicatorTextView.text = if (sandwichOrder.hummus) YES else NO
        tahiniIndicatorTextView.text = if (sandwichOrder.tahini) YES else NO
        nameEditText.setText(sandwichOrder.customerName)
        commentEditText.setText(sandwichOrder.comment)


        nameEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (nameEditText.text.isEmpty()) {
                    saveOrderImageButton.imageAlpha = 125
                    saveOrderImageButton.isEnabled = false
                }
                else if (nameEditText.text.isNotEmpty()) {
                    saveOrderImageButton.imageAlpha = 255
                    saveOrderImageButton.isEnabled = true
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        saveOrderImageButton.setOnClickListener {
            sandwichOrder.customerName = nameEditText.text.toString()
            sandwichOrder.comment = commentEditText.text.toString()
            firebaseManager.setCurrentOrder(sandwichOrder)
        }

        cancelOrderImageButton.setOnClickListener {
            firebaseManager.setCurrentOrder(null)
            val intent = Intent(this, PlaceOrderActivity::class.java)
            startActivity(intent)
        }
    }
}