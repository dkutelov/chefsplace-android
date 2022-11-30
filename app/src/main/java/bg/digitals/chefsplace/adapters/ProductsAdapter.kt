package bg.digitals.chefsplace.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import bg.digitals.chefsplace.databinding.ProductsRowBinding
import bg.digitals.chefsplace.models.ProductsResponse
import bg.digitals.chefsplace.utils.ProductsDiffUtil

class ProductsAdapter: RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    private var products = emptyList<ProductsResponse>()

    class ProductsViewHolder(private val binding: ProductsRowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(productResponse: ProductsResponse) {
            binding.product = productResponse
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ProductsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ProductsRowBinding.inflate(layoutInflater, parent, false)
                return ProductsViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val currentProduct = products[position]
        holder.bind(currentProduct)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun setData(newData: List<ProductsResponse>) {
        val productsDiffUtil = ProductsDiffUtil(products, newData)
        val diffUtilResult = DiffUtil.calculateDiff(productsDiffUtil)

        products = newData

        diffUtilResult.dispatchUpdatesTo(this)
    }
}