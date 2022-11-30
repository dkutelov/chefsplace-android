package bg.digitals.chefsplace.utils

import androidx.recyclerview.widget.DiffUtil
import bg.digitals.chefsplace.models.ProductsResponse

class ProductsDiffUtil(
    private val oldList: List<ProductsResponse>,
    private val newList: List<ProductsResponse>
) : DiffUtil.Callback(){
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}