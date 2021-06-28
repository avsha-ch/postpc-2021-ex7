package huji.postpc.rachels.sandwich

public const val WAITING = "waiting"
public const val IN_PROGRESS = "in-progress"
public const val READY = "ready"
public const val DONE = "done"


data class SandwichOrder(
    var id: String? = null,
    var customerName: String? = null,
    var pickles: Int = 0,
    var hummus: Boolean = false,
    var tahini: Boolean = false,
    var comment: String? = null,
    var status: String = WAITING,
)