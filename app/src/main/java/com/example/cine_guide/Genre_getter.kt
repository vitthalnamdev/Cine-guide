package com.example.cine_guide

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import com.example.cine_guide.Repository.genreRepository
import com.example.cine_guide.Retrofit.Retrofit_object
import com.example.cine_guide.modelgenre.Genre
import kotlinx.coroutines.runBlocking


object Genre_getter{


    @Composable
    fun Genre_getter() {
        val genre_map = genreRepository(Retrofit_object.genre_api).getGenreList()
        var iserror: Boolean = false
        val genrelist: MutableList<Genre> = mutableListOf<Genre>()

        LaunchedEffect(Unit) {
            genre_map.collect {
                when (it) {
                    is movieresult.Error -> {
                       println("Error getting the genre")
                    }
                    is movieresult.Success -> {
                        val curr = it.data
                        if (curr != null) {
                            for (i in curr) {
                                genrelist.add(i)
                            }
                        }
                    }
                }
            }
        }
        var sz = genrelist.size

        val int_to_String: HashMap<Int, String> = HashMap()
        val String_to_int: HashMap<String, Int> = HashMap()
        var genre = int_to_String[28]

        for (i in genrelist) {
            int_to_String[i.id] = i.name
            String_to_int[i.name] = i.id
        }
    }
}