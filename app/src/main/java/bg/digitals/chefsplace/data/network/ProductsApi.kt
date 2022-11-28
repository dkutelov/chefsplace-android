package bg.digitals.chefsplace.data.network

import bg.digitals.chefsplace.models.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductsApi {

    @GET("/products/{filter}")
    suspend fun getProducts(@Path("filter") filter: String): Response<List<ProductsResponse>>

}