package com.example.nascar_app.screens

import EventItem
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nascar_app.R
import com.example.nascar_app.data.EventsDatabase
import com.example.nascar_app.data.models.EventModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ProfileScreen(modifier : Modifier, context: Context){

    val db = EventsDatabase.getDatabase(context = context)
    val favoriteEventsState = remember { mutableStateOf(emptyList<EventModel>()) }
    val coroutineScope = rememberCoroutineScope() // Create a coroutine scope

    LaunchedEffect(Unit) {
        // Call the suspend function to get favorite events
        favoriteEventsState.value = withContext(Dispatchers.IO) {
            db.eventDao().getFavoriteEvents() // Assuming this is a suspend function
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top

    ) {


        Image(
            painter = painterResource(id = R.drawable.ic_user),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
        )


        Text(
           "Your Name here",
            fontSize = 20.sp
        )
        Text(
            "Your Email here",
            fontSize = 16.sp
        )




    }

    Divider(
        color = Color.Gray, // Set the color of the divider
        modifier = Modifier
            .fillMaxWidth() // Make the divider take the full width
            .height(1.dp) // Set the height (thickness) of the divider
    )

    LazyColumn(content = {
        items(favoriteEventsState.value) { event -> // Correct usage of items function
            EventItem(
                image = event.image,
                title = event.title,
                date = event.date,
                button = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            db.eventDao().removeEventFromFavorites(event.id)
                            favoriteEventsState.value = db.eventDao().getFavoriteEvents()
                        }
                    }) {
                        Icon(Icons.Filled.Favorite, contentDescription = "Add to favorites")
                    }

                }

            )
        }
    })



}