package huji.postpc.rachels.sandwich
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController


@RunWith(RobolectricTestRunner::class)
class UnitTests {

    var editOrderActivityController : ActivityController<EditOrderActivity>? = null
    var placeOrderActivityController : ActivityController<PlaceOrderActivity>? = null

    @Before
    fun initialize() {
        editOrderActivityController = Robolectric.buildActivity(EditOrderActivity::class.java)
        placeOrderActivityController = Robolectric.buildActivity(PlaceOrderActivity::class.java)
    }

    @Test
    fun when_tahiniAndHummusIndicatorsAreClicked_then_shouldChangeValues() {

        val yes = "Yes, please"
        val no = "No, thanks"
        editOrderActivityController!!.create().visible()
        val activityUnderTest = editOrderActivityController!!.get()
        val hummusIndicatorTextView = activityUnderTest.findViewById<TextView>(R.id.hummusIndicatorTextView)
        val tahiniIndicatorTextView = activityUnderTest.findViewById<TextView>(R.id.tahiniIndicatorTextView)

        assertTrue(hummusIndicatorTextView.text == no)
        assertTrue(tahiniIndicatorTextView.text == no)

        hummusIndicatorTextView.performClick()
        tahiniIndicatorTextView.performClick()

        assertTrue(hummusIndicatorTextView.text == yes)
        assertTrue(tahiniIndicatorTextView.text == yes)

        hummusIndicatorTextView.performClick()
        tahiniIndicatorTextView.performClick()

        assertTrue(hummusIndicatorTextView.text == no)
        assertTrue(tahiniIndicatorTextView.text == no)
    }

    @Test
    fun when_customerNameIsEmpty_then_PlaceOrderIsDisabled() {
        placeOrderActivityController!!.create().visible()
        val activityUnderTest = placeOrderActivityController!!.get()
        val nameEditText = activityUnderTest.findViewById<EditText>(R.id.nameEditText)
        val placeOrderImageButton = activityUnderTest.findViewById<ImageButton>(R.id.placeOrderImageButton)

        assertTrue(nameEditText.text.toString() == "")
        assertTrue(!placeOrderImageButton.isEnabled)

        nameEditText.setText("Avsha")

        assertTrue(placeOrderImageButton.isEnabled)
    }

    @Test
    fun when_customerNameIsDeletedFromAnExistingOrder_then_SaveIsDisabled() {
        editOrderActivityController!!.create().visible()
        val activityUnderTest = editOrderActivityController!!.get()
        val nameEditText = activityUnderTest.findViewById<EditText>(R.id.nameEditText)
        val saveOrderImageButton = activityUnderTest.findViewById<ImageButton>(R.id.saveOrderImageButton)

        nameEditText.setText("Avsha")
        assertTrue(saveOrderImageButton.isEnabled)

        nameEditText.setText("")
        assertTrue(!saveOrderImageButton.isEnabled)

        nameEditText.setText("Avsha")
        assertTrue(saveOrderImageButton.isEnabled)
    }

}