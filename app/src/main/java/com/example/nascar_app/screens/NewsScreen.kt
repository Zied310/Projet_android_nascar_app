package com.example.nascar_app.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.nascar_app.R
import com.example.nascar_app.items.NewsItem
import com.example.nascar_app.models.NewsModel

@Composable
fun NewsScreen(modifier: Modifier) {
    val newsItems = listOf(
        NewsModel(
            image = R.drawable.ic_news1,
            title = "NASCAR BETTING: 2023 TALLADEGA SUPERSPEEDWAY-2 ODDS",
            description = "The NASCAR Cup Series Playoffs Round of 12 continues at Talladega Superspeedway on Sunday for the YellaWood 500 (2 p.m. ET, NBC, MRN, SiriusXM NASCAR Radio, NBC Sports App)."
        ),
        NewsModel(
            image = R.drawable.ic_news2,
            title = "NTruex, togetherness and Talladega: Sunday’s playoff race may hinge on team effort",
            description = "Martin Truex Jr. is still looking for the first Talladega Superspeedway victory of his career, and snagging one Sunday at a pivotal point in the NASCAR Cup Series Playoffs would be well-timed. The Joe Gibbs Racing veteran is also looking to right the ship in this star-crossed postseason — his four races so far have yet to produce a top-10 finish or a lap led."
        ),
        NewsModel(
            image = R.drawable.ic_news3,
            title = "Texas rattles Cup Series playoff picture once again",
            description = "Whether it’s Brad Keselowski leading almost every lap only to lose to Jimmie Johnson and not make the Championship 4 or a good old-fashioned donnybrook on pit road with a handful of playoff drivers, Texas Motor Speedway has been at the forefront of postseason theatrics since the inception of the current format in 2014."
        ),
    )

    LazyColumn(modifier = modifier) {
        items(newsItems) { newsItem -> // Correct usage of items function
            NewsItem(
                image = newsItem.image,
                title = newsItem.title,
                description = newsItem.description
            )
        }
    }
}