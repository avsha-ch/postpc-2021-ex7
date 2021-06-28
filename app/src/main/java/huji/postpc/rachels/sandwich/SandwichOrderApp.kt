package huji.postpc.rachels.sandwich

import android.app.Application

class SandwichOrderApp : Application() {

    companion object {
        lateinit var instance: SandwichOrderApp
            private set
    }
    lateinit var firebaseManager : SandwichOrdersFirebaseManager
        private set


    override fun onCreate() {
        super.onCreate()
        instance = this
        firebaseManager = SandwichOrdersFirebaseManager(this)
    }
}