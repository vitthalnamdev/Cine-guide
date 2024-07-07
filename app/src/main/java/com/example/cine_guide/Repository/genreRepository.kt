package com.example.cine_guide.Repository

import com.example.cine_guide.GenreApi
import com.example.cine_guide.modelgenre.Genre
import com.example.cine_guide.movieresult
import com.example.cine_guide.movieresult.Error
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class genreRepository(
    private val api:GenreApi
) {
    fun getGenreList(): Flow<movieresult<List<Genre>>> {

        return flow {
            val productsFromApi = try {
                api.getGenreList(api_key)
            }catch (e:Exception) {
                emit(movieresult.Error(message = "Cannot load movies"))
                return@flow
            }
            emit(movieresult.Success(productsFromApi.genres))
        }

    }
}