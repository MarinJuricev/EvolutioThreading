package netovski.test.evolutiothreading

import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    private lateinit var foodListAdapter: FoodListAdapter
    private lateinit var uiHandler: UiHandler
    private lateinit var orderHandlerThread: OrderHandlerThread
    private lateinit var foodRunnable: FoodRunnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        foodListAdapter = FoodListAdapter(mutableListOf(), applicationContext)
        orderRecyclerView.layoutManager = LinearLayoutManager(this)
        orderRecyclerView.adapter = foodListAdapter
        uiHandler = UiHandler()
        uiHandler.setRecyclerView(orderRecyclerView)
        uiHandler.setAdapter(foodListAdapter)

    }

    override fun onStart() {
        super.onStart()

        orderHandlerThread = OrderHandlerThread(uiHandler)
        foodRunnable = FoodRunnable(orderHandlerThread)

        foodRunnable.setSize(20)

        orderHandlerThread.start()
        foodRunnable.start()
    }

    class UiHandler : Handler() {

        private lateinit var weakRefFoodListAdapter: WeakReference<FoodListAdapter>
        private lateinit var weakRefOrderRecyclerView: WeakReference<RecyclerView>

        fun setAdapter(foodListAdapter: FoodListAdapter) {
            weakRefFoodListAdapter = WeakReference(foodListAdapter)
        }

        fun setRecyclerView(foodRecyclerView: RecyclerView) {
            weakRefOrderRecyclerView = WeakReference(foodRecyclerView)
        }

        private fun addAndNotifyForOrder(foodOrder: FoodOrder, position: Int) {
            weakRefFoodListAdapter.get()?.getOrderList()?.add(foodOrder)
            weakRefOrderRecyclerView.get()?.adapter?.notifyItemInserted(position)
        }

        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            val position = weakRefFoodListAdapter.get()?.getOrderList()?.size
            addAndNotifyForOrder(msg?.obj as FoodOrder, position!!)
            weakRefOrderRecyclerView.get()?.smoothScrollToPosition(weakRefFoodListAdapter
                .get()?.itemCount!!)
        }
    }
}