package com.example.cine_guide.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.cine_guide.Genre_getter
import com.example.cine_guide.presentation.sharedviewmodel
import java.time.format.TextStyle
import kotlin.math.floor

@Composable
fun Movie(sharedviewmodel: sharedviewmodel){
    val horizontalscrollState = rememberScrollState()
    val verticalscrollState = rememberScrollState()
    var poster = sharedviewmodel.poster
    Column(modifier = Modifier.verticalScroll(verticalscrollState)){
        Imageadd(poster!!.backdrop_path)
        TextDisplay(poster.title)
        Spacer(modifier = Modifier.size(12.dp))

        Row(modifier = Modifier.horizontalScroll(horizontalscrollState)){
            for (i in poster.genre_ids) {
                Genre_construct(i)
            }
        }
        Spacer(modifier = Modifier.size(12.dp))
        Row() {
            Text("Rating: ", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 16.dp))
            Text(text = poster.vote_average.toString(), fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(start = 16.dp))
        }
        Text("Overview : ", fontSize = 24.sp , fontWeight = FontWeight.Bold
         , modifier = Modifier.padding(start = 16.dp , top = 16.dp)
        )
        Overview(poster.overview)
    }

}





@Composable
fun Overview(overview: String) {

  Text(text = overview  ,
      style = androidx.compose.ui.text.TextStyle(
          fontSize = 18.sp,
      lineHeight = 24.sp
  ), modifier = Modifier.padding(start = 16.dp , top = 16.dp))
    Spacer(modifier = Modifier.size(80.dp))
}

@Composable
fun Genre_construct(i: Int) {
   Button(onClick = {

   }, modifier = Modifier.padding(start = 25.dp)){
       Genre_getter.int_to_String[i]?.
       let { Text(text = it ) }
   }
}

@Composable
fun TextDisplay(title: String) {
    Text(
        text = title,
        style = androidx.compose.ui.text.TextStyle(
            fontSize = 30.sp,
            lineHeight = 36.sp
        ),
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        modifier = Modifier
            .padding(16.dp)  // Adjust the padding as needed
            .fillMaxWidth()
    )
}


@Composable
fun Imageadd(poster: String){
    var basepath: String = "https://image.tmdb.org/t/p/original/"
    var path = basepath + poster
    val imagepainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).data(path)
            .size(Size.ORIGINAL).build()
    ).state
   Card(modifier = Modifier.height(400.dp).fillMaxWidth()
       .padding(top = 25.dp , start = 16.dp , end = 16.dp )
   ){
       if (imagepainter is AsyncImagePainter.State.Success) {
           Image(
               painter = imagepainter.painter,
               contentDescription = null,
               contentScale = ContentScale.Crop,

               )
       } else {
           Box(
               modifier = Modifier.fillMaxSize(),
               contentAlignment = Alignment.Center
           ) {
               CircularProgressIndicator()
           }
       }
   }
}