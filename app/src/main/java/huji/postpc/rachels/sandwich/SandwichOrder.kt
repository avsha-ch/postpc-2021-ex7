package huji.postpc.rachels.sandwich

enum class Status(val getStr: String) {
    WAITING("waiting"), IN_PROGRESS("in-progress"), READY("ready"), DONE("done")
}

data class SandwichOrder(
    var id: String? = null,
    var customerName: String? = null,
    var pickles: Int = 0,
    var hummus: Boolean = false,
    var tahini: Boolean = false,
    var comment: String? = null,
    var status: Status = Status.WAITING,
)