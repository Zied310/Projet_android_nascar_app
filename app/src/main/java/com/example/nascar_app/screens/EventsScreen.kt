
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nascar_app.R
import com.example.nascar_app.data.EventsDatabase
import com.example.nascar_app.data.models.EventModel
import kotlinx.coroutines.launch


@Composable
fun EventsScreen(modifier: Modifier, navController: NavController, context: Context) {

    val db = EventsDatabase.getDatabase(context = context)
    val coroutineScope = rememberCoroutineScope()


    val eventItems = listOf(
        EventModel(
            image = R.drawable.ic_event1,
            id = 1,
            title = "TALLADEGA SUPERSPEEDWAY",
            date = "SUN. OCT 10, 2023 | 2:00 PM ET"
        ),
        EventModel(
            image = R.drawable.ic_event2,
            id = 2,
            title = "CHARLOTTE MOTOR SPEEDWAY ROAD COURSE",
            date = "SUN. OCT 18, 2023 | 2:00 PM ET"
        ),
        EventModel(
            image = R.drawable.ic_event3,
            id = 3,
            title = "LAS VEGAS MOTOR SPEEDWAY",
            date = "SUN. OCT 25, 2023 | 2:30 PM ET"
        ),
        EventModel(
            image = R.drawable.ic_event4,
            id = 4,
            title = "HOMESTEAD-MIAMI SPEEDWAY",
            date = "SUN. OCT 28, 2023 | 2:30 PM ET"
        ),
    )

    Box(modifier = modifier.fillMaxSize()){
        LazyColumn(content = {
            items(eventItems) { eventItem -> // Correct usage of items function
                EventItem(
                    image = eventItem.image,
                    title = eventItem.title,
                    date = eventItem.date,
                    button = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                db.eventDao().addEventToFavorites(eventItem)
                            }
                        }) {
                            Icon(Icons.Filled.Favorite, contentDescription = "Add to favorites")
                        }

                    }

                )
            }
        })

        IconButton(
            onClick = {
                navController.navigate("add_event_screen")
            },
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.BottomEnd)
                .padding(16.dp) // Padding from the edges
                .background(Color.Black, shape = RoundedCornerShape(28.dp))
        ){
            Icon(
                imageVector = Icons.Filled.Add, // Use your plus icon here
                contentDescription = "Add",
                tint = Color.White
            )
        }

    }


}