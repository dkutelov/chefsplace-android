package bg.digitals.chefsplace

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import bg.digitals.chefsplace.data.Repository
import bg.digitals.chefsplace.models.ProductsResponse
import bg.digitals.chefsplace.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import retrofit2.Response

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    val productsResponse: MutableLiveData<NetworkResult<List<ProductsResponse>>> = MutableLiveData()

    fun getProducts(filter: String?) = viewModelScope.launch {
        getProductsSafeCall(filter)
    }

    private suspend fun getProductsSafeCall(filter: String?) {
        productsResponse.value = NetworkResult.Loading()

        if (hasInternetConnection()) {

            try {
                val response = repository.remote.getProducts(filter ?: "")

                productsResponse.value = handleProductsResponse(response)
            } catch (e: Exception) {
                productsResponse.value = NetworkResult.Error("Products not found!")
            }
        } else {
            productsResponse.value = NetworkResult.Error("No Internet Connection!")
        }


    }

    private fun handleProductsResponse(response: Response<List<ProductsResponse>>): NetworkResult<List<ProductsResponse>>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }

            response.code() == 402 -> {
                return NetworkResult.Error("Api key limited!")
            }

            response.body()!!.isEmpty() -> {
                return NetworkResult.Error("Products Not Found!")
            }

            response.isSuccessful -> {                                                                                                                                                                                                          kl
                val products = response.body()
                Log.i("products", products.toString())
                return NetworkResult.Success(products!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}