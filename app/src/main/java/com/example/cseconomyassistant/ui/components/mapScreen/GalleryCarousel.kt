package com.example.cseconomyassistant.ui.components.mapScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun GalleryCarousel(
    gallery: List<Int>,
    modifier: Modifier = Modifier
) {
    if (gallery.isEmpty()) return

    val pagerState = rememberPagerState { gallery.size }
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Image(
                painter = painterResource(gallery[page]),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        IconButton(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(8.dp),
            enabled = pagerState.currentPage > 0,
            onClick = {
                scope.launch {
                    pagerState.animateScrollToPage(
                        pagerState.currentPage - 1
                    )
                }
            }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Previous image"
            )
        }

        IconButton(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(8.dp),
            enabled = pagerState.currentPage < gallery.lastIndex,
            onClick = {
                scope.launch {
                    pagerState.animateScrollToPage(
                        pagerState.currentPage + 1
                    )
                }
            }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Next image"
            )
        }
    }
}
