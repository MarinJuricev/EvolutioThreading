package netovski.test.evolutiothreading

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.food_list_item.view.*

class FoodListAdapter(
    private val foodOrderList: MutableList<FoodOrder>,
    private val context: Context) : RecyclerView.Adapter<FoodListAdapter.FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.food_list_item,
            parent, false)
        return FoodViewHolder(view)
    }

    override fun getItemCount(): Int {
        return foodOrderList.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bindItems(foodOrderList[position])
    }

    fun getOrderList(): MutableList<FoodOrder> {
        return foodOrderList
    }

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(food: FoodOrder) {
            itemView.foodNameTextView.text = itemView.context.getString(R.string.food_text, food.foodName)
            itemView.foodPriceTextView.text = itemView.context.getString(R.string.price_text, food.foodPrice.toString())
            itemView.sideOrderTextView.text = itemView.context.getString(R.string.side_order_text, food.sideOrder)
        }
    }
}