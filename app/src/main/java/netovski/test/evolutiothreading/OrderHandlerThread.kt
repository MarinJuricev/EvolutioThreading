package netovski.test.evolutiothreading

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import java.util.*

class OrderHandlerThread(private var uiHandler: MainActivity.UiHandler) :
    HandlerThread("OrderHandlerThread") {

    var handler:Handler? = null

    override fun onLooperPrepared() {
        super.onLooperPrepared()

        handler = getHandler(looper)
    }

    private fun getHandler(looper: Looper): Handler {
        return object : Handler(looper) {

            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                val foodOrder = msg?.obj as FoodOrder
                foodOrder.foodPrice = convertCurrency(foodOrder.foodPrice)
                foodOrder.sideOrder = attachSideOrder()
                val processedMessage = Message()
                processedMessage.obj = foodOrder
                uiHandler.sendMessage(processedMessage)
            }
        }
    }

    private fun convertCurrency(foodPriceInDollars: Float): Float {
        return foodPriceInDollars * 6.1f
    }

    /**
     * This function attaches random side order to the incoming food Orders.
     */
    private fun attachSideOrder(): String {
        val random = Random()
        val randomOrder = random.nextInt(3)
        return when (randomOrder) {
            0 -> "Pomfrit"
            1 -> "Salata"
            else -> "Blitva"
        }
    }

    fun sendOrder(foodOrder: FoodOrder): Unit{
        val message = Message()

        message.obj = foodOrder
        handler?.sendMessage(message)
    }
}
   // THE NONO ZONE