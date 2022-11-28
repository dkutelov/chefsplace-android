package bg.digitals.chefsplace.models

data class ProductsResponse(
    val id: String,
    val name: String,
    val mainImage: String,
    val category: String,
    val price: Int,
    val weight: Float
)
