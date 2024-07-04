package com.example.cine_guide.Repository

import com.example.cine_guide.Api
import com.example.cine_guide.models.Product
import com.example.cine_guide.movieresult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepositoryImpl(
    private val api: Api
): ProductRepository {
    override suspend fun getProductList(): Flow<movieresult<List<Product>>> {
      return flow {
         val productsFromApi = try {
             var api_key = "api_key"
             api.getProductsList(api_key)
         }catch (e:Exception) {
             emit(movieresult.Error(message = "Cannot load movies"))
             return@flow
         }
          emit(movieresult.Success(productsFromApi.results))

    }

}
}
