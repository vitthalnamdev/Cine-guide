package com.example.cine_guide.Repository

import androidx.compose.runtime.MutableState
import com.example.cine_guide.Api
import com.example.cine_guide.SearchApi
import com.example.cine_guide.models.Product
import com.example.cine_guide.movieresult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class searchrepository(
    private val api: SearchApi
): ProductRepository {
    override suspend fun getProductList(text:String?): Flow<movieresult<List<Product>>> {
        return flow {
            val productsFromApi = try {
                var api_key = "39b12d0dcd3edc25a3a0c5e2fa9619fa"
                api.getProductsList(text, api_key)
            }catch (e:Exception) {
                emit(movieresult.Error(message = "Cannot load movies"))
                return@flow
            }
            emit(movieresult.Success(productsFromApi.results))
        }
    }
}
