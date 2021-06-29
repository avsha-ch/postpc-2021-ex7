package huji.postpc.rachels.sandwich

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

const val YES = "Yes, please"
const val NO = "No, thanks"
const val ID_BUNDLE = "sandwich_order_id"
const val CUSTOMER_NAME_BUNDLE = "sandwich_order_customer_name"
const val PICKLES_BUNDLE = "sandwich_order_pickles"
const val HUMMUS_BUNDLE = "sandwich_order_hummus"
const val TAHINI_BUNDLE = "sandwich_order_tahini"
const val COMMENT_BUNDLE = "sandwich_order_comment"
const val STATUS_BUNDLE = "sandwich_order_status"

open class OrderActivity : AppCompatActivity() {
    protected lateinit var firebaseManager: SandwichOrdersFirebaseManager

    protected lateinit var nameEditText: EditText

    protected lateinit var sandwichOrder : SandwichOrder

    private lateinit var minusPickleImageButton: ImageButton
    protected lateinit var pickleCounterTextView: TextView
    private lateinit var plusPickleImageButton: ImageButton

    protected lateinit var hummusIndicatorTextView: TextView

    protected lateinit var tahiniIndicatorTextView: TextView

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

    override fun onBackPressed() { }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(ID_BUNDLE, sandwichOrder.id)
        outState.putString(CUSTOMER_NAME_BUNDLE, sandwichOrder.customerName)
        outState.putInt(PICKLES_BUNDLE, sandwichOrder.pickles)
        outState.putBoolean(HUMMUS_BUNDLE, sandwichOrder.hummus)
        outState.putBoolean(TAHINI_BUNDLE, sandwichOrder.tahini)
        outState.putString(COMMENT_BUNDLE, sandwichOrder.comment)
        outState.putString(STATUS_BUNDLE, sandwichOrder.status)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        sandwichOrder = SandwichOrder(
            id = savedInstanceState.getString(ID_BUNDLE),
        customerName = savedInstanceState.getString(CUSTOMER_NAME_BUNDLE),
        pickles = savedInstanceState.getInt(PICKLES_BUNDLE),
        hummus = savedInstanceState.getBoolean(HUMMUS_BUNDLE),
        tahini = savedInstanceState.getBoolean(TAHINI_BUNDLE),
        comment = savedInstanceState.getString(COMMENT_BUNDLE),
        status = savedInstanceState.getString(STATUS_BUNDLE)!!)
        firebaseManager = SandwichOrderApp.instance.firebaseManager
        firebaseManager.setCurrentOrder(sandwichOrder)
        pickleCounterTextView.text = sandwichOrder.pickles.toString()
        hummusIndicatorTextView.text = if (sandwichOrder.hummus) YES else NO
        tahiniIndicatorTextView.text = if (sandwichOrder.tahini) YES else NO
        nameEditText.setText(sandwichOrder.customerName)
        commentEditText.setText(sandwichOrder.comment)

    }



}