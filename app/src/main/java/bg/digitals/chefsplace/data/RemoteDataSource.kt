package bg.digitals.chefsplace.data

import bg.digitals.chefsplace.data.network.ProductsApi
import bg.digitals.chefsplace.models.ProductsResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val productsApi: ProductsApi
) {

    suspend fun getProducts(filter: String): Response<List<ProductsResponse>> {
        return productsApi.getProducts()
    }
}