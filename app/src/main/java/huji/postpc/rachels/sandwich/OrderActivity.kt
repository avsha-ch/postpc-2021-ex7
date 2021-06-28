package huji.postpc.rachels.sandwich

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

private const val YES = "Yes, please"
private const val NO = "No, thanks"

open class OrderActivity : AppCompatActivity() {
    protected lateinit var firebaseManager: SandwichOrdersFirebaseManager

    protected lateinit var nameEditText: EditText

    protected lateinit var sandwichOrder : SandwichOrder

    private lateinit var minusPickleImageButton: ImageButton
    private lateinit var pickleCounterTextView: TextView
    private lateinit var plusPickleImageButton: ImageButton

    private lateinit var hummusIndicatorTextView: TextView

    private lateinit var tahiniIndicatorTextView: TextView

    protected lateinit var commentEditText: EditText

    private lateinit var placeOrderImageButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseManager = SandwichOrderApp.instance.firebaseManager
        sandwichOrder = firebaseManager.getCurrentOrder() ?: SandwichOrder()

        nameEditText = findViewById(R.id.nameEditText)

        minusPickleImageButton = findViewById(R.id.minusPickleImageButton)
        pickleCounterTextView = findViewById(R.id.pickleCounterTextView)
        plusPickleImageButton = findViewById(R.id.plusPickleImageButton)

        hummusIndicatorTextView = findViewById(R.id.hummusIndicatorTextView)

        tahiniIndicatorTextView = findViewById(R.id.tahiniIndicatorTextView)

        commentEditText = findViewById(R.id.commentEditText)

        // val current : SandwichOrder? = firebaseManager.getCurrentOrder()

        hummusIndicatorTextView.text = NO
        tahiniIndicatorTextView.text = NO

        if (sandwichOrder.pickles == 0) {
            minusPickleImageButton.imageAlpha = 125
            minusPickleImageButton.isEnabled = false
        }


        minusPickleImageButton.setOnClickListener {
            if (sandwichOrder.pickles > 0) {
                if (!plusPickleImageButton.isEnabled) {
                    plusPickleImageButton.imageAlpha = 255
                    plusPickleImageButton.isEnabled = true
                }
                sandwichOrder.pickles--
                pickleCounterTextView.text = sandwichOrder.pickles.toString()
            }
            if (sandwichOrder.pickles == 0) {
                minusPickleImageButton.imageAlpha = 125
                minusPickleImageButton.isEnabled = false
            }
        }

        plusPickleImageButton.setOnClickListener {
            if (sandwichOrder.pickles < 10) {
                if (!minusPickleImageButton.isEnabled) {
                    minusPickleImageButton.imageAlpha = 255
                    minusPickleImageButton.isEnabled = true
                }
                sandwichOrder.pickles++
                pickleCounterTextView.text = sandwichOrder.pickles.toString()
            }
            if (sandwichOrder.pickles == 10) {
                plusPickleImageButton.imageAlpha = 125
                plusPickleImageButton.isEnabled = false
            }
        }

        hummusIndicatorTextView.setOnClickListener {
            if (hummusIndicatorTextView.text == NO) {
                sandwichOrder.hummus = true
                hummusIndicatorTextView.text = YES
            } else if (hummusIndicatorTextView.text == YES) {
                sandwichOrder.hummus = false
                hummusIndicatorTextView.text = NO
            }
        }

        tahiniIndicatorTextView.setOnClickListener {
            if (tahiniIndicatorTextView.text == NO) {
                sandwichOrder.tahini = true
                tahiniIndicatorTextView.text = YES
            } else if (tahiniIndicatorTextView.text == YES) {
                sandwichOrder.tahini = false
                tahiniIndicatorTextView.text = NO
            }
        }
    }
}