package huji.postpc.rachels.sandwich

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.gson.Gson
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot

private const val ORDERS_COLLECTION = "orders"
private const val FIRESTORE_LOG_TAG = "SandwichOrdersFirebase"
private const val MANAGER_LOG_TAG = "SandwichOrdersManager"
private const val SNAPSHOT_ERROR = "exception in snapshot: "
private const val FIREBASE_ACTION_ERROR = "exception in firebase action: "
private const val EMPTY_VALUE_ERROR = "value is null"
private const val SP_CURRENT_ORDER = "sp_current_order"
private const val SP_ORDER_TRACKING = "sp_order_tracking"


class SandwichOrdersFirebaseManager(context : Context) {

    private val sp: SharedPreferences = context.getSharedPreferences(SP_ORDER_TRACKING, Context.MODE_PRIVATE)
    // TODO: maybe use this instead of allSandwichOrders
    private var currentSandwichOrder : SandwichOrder? = null
    private val gson = Gson()



    init {
        // createLiveQuery()
        if (currentSandwichOrder == null){
            val temp = sp.getString(SP_CURRENT_ORDER, null)
            if (temp != null){
                currentSandwichOrder = gson.fromJson(temp, SandwichOrder::class.java)
            }
        }
    }

    fun getCurrentOrder() : SandwichOrder? {
        if (currentSandwichOrder != null){
            return currentSandwichOrder
        }
        val temp = sp.getString(SP_CURRENT_ORDER, null)
        return gson.fromJson(temp, SandwichOrder::class.java)
    }

    fun setCurrentOrder(newSandwichOrder: SandwichOrder?) {
        sp.edit().putString(SP_CURRENT_ORDER, gson.toJson(newSandwichOrder)).apply()
        if (newSandwichOrder == null && currentSandwichOrder != null){
            deleteSandwichOrder(currentSandwichOrder!!)
            currentSandwichOrder = null
            return
        }
        else if (newSandwichOrder == null){
            return
        }
        else {
            editSandwichOrder(newSandwichOrder)
            currentSandwichOrder = newSandwichOrder
        }

    }

    fun addSandwichOrder(sandwichOrder: SandwichOrder) {
        val docId = sandwichOrder.id
        if (docId == null) {
            Log.e(MANAGER_LOG_TAG, "An order without an ID!")
            return
        }
        if (getCurrentOrder() == null) {
            setCurrentOrder(sandwichOrder)
            val firestore = FirebaseFirestore.getInstance()

            val doc = firestore.collection(ORDERS_COLLECTION).document()
            sandwichOrder.id = doc.id
            doc.set(sandwichOrder)
                .addOnSuccessListener {
                    // TODO: maybe? sp.edit().putString(sandwichOrder.id, gson.toJson(sandwichOrder)).apply()
                    Log.d(FIRESTORE_LOG_TAG, "Order " + docId + "was successfully added to firestore")
                }
                .addOnFailureListener { exception ->
                    Log.e(FIRESTORE_LOG_TAG, "while trying to add order " + docId + "got error: " + exception)
                }
        }
        else {
            Log.w(MANAGER_LOG_TAG, "order is already in-place")
            return
        }


    }

    fun editSandwichOrder(alteredSandwichOrder: SandwichOrder) {
        val previousSandwichOrder = getCurrentOrder()
        if (previousSandwichOrder == null){
            addSandwichOrder(alteredSandwichOrder)
            return
        }
        alteredSandwichOrder.id = previousSandwichOrder.id
        setCurrentOrder(alteredSandwichOrder)
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection(ORDERS_COLLECTION).document(previousSandwichOrder.id!!).set(alteredSandwichOrder)
            .addOnSuccessListener {
                Log.d(FIRESTORE_LOG_TAG, "Order " + previousSandwichOrder.id + "was successfully deleted from firestore")
            }
            .addOnFailureListener { exception : Exception ->
                Log.e(FIRESTORE_LOG_TAG, "while trying to edit order " + previousSandwichOrder.id + "got error: " + exception)
            }
    }

    fun deleteSandwichOrder(sandwichOrder: SandwichOrder){
        setCurrentOrder(null)
        val docId = sandwichOrder.id
        if (docId == null) {
            Log.e(MANAGER_LOG_TAG, "An order without an ID!")
            return
        }
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection(ORDERS_COLLECTION).document(docId).delete()
            .addOnSuccessListener {
                Log.d(FIRESTORE_LOG_TAG, "Order " + docId + "was successfully deleted from firestore")
            }
            .addOnFailureListener { exception ->
                Log.e(FIRESTORE_LOG_TAG, "while trying to delete order " + docId + "got error: " + exception)
            }
    }

    public fun changeCurrentStatus(newStatus : Status){
        currentSandwichOrder = getCurrentOrder()
        if (currentSandwichOrder == null) {
            Log.e(MANAGER_LOG_TAG, "No order to change")
            return
        }
        else {
            currentSandwichOrder!!.status = newStatus
            editSandwichOrder(currentSandwichOrder!!)
        }

    }

//    private fun createLiveQuery() {
//        val firestore = FirebaseFirestore.getInstance()
//        val ordersReference = firestore.collection(ORDERS_COLLECTION) //.whereEqualTo(STATUS_FIELD, Status.WAITING.getStr)
//
//        val liveQuery = ordersReference.addSnapshotListener { value, exception ->
//            if (exception != null){
//                Log.d(FIRESTORE_LOG_TAG, SNAPSHOT_ERROR + exception.message)
//                return@addSnapshotListener
//            }
//            if (value == null) {
//                Log.d(FIRESTORE_LOG_TAG, EMPTY_VALUE_ERROR)
//                return@addSnapshotListener
//            }
//            // success!
//            this.allSandwichOrders.clear()
//            for (doc : QueryDocumentSnapshot in value) {
//                val sandwichOrder = doc.toObject(SandwichOrder::class.java)
//                this.allSandwichOrders.add(sandwichOrder)
//            }
//            // TODO: will continue to listen until you will call "liveQuery.remove()"
//        }
//
//    }

}