package netovski.test.evolutiothreading

import java.util.*

class FoodRunnable(val orderHandlerThread: OrderHandlerThread) : Runnable {

    private var thread: Thread = Thread(this)
    private var alive: Boolean = false
    private var count: Int = 0
    private var size: Int = 0

    override fun run() {
        alive = true

        while (alive && count < size){
            count++
            val foodOrder = FoodOrder(getRandomOrderName(), getRandomOrderPrice(), "")
            orderHandlerThread.sendOrder(foodOrder)

            Thread.sleep(1000)
        }
    }

    fun start() {
        if (!thread.isAlive)
            thread.start()
    }

    fun stop() {
        alive = false
    }

    fun setSize(size: Int){
        this.size = size
    }


    /**
     * @return Random Order Name for the restaurant.
     */
    private fun getRandomOrderName(): String {
        val random = Random()
        val randomOrder = random.nextInt(10)
        return when (randomOrder) {
            0 -> "Burger"
            1 -> "Pizza"
            2 -> "Piletina u pizza tijestu"
            3 -> "Sladoled"
            4 -> "Sesula sendvic"
            5 -> "Ozujsko pivo"
            6 -> "Kola"
            7 -> "Riza"
            8 -> "Torta"
            else -> "Lignje kolutici"
        }
    }

    /**
     * @return get the random price for orders in restaurant.
     */
    private fun getRandomOrderPrice(): Float {
        val random = Random()
        val randomOrder = random.nextInt(10)
        return when (randomOrder) {
            0 -> 5f
            1 -> 10f
            2 -> 15f
            3 -> 20f
            4 -> 25f
            5 -> 30f
            6 -> 35f
            7 -> 40f
            8 -> 45f
            else -> 50f
        }
    }
}