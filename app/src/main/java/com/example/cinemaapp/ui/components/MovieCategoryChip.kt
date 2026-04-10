package com.example.cinemaapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinemaapp.data.model.MovieCategory
import com.example.cinemaapp.ui.theme.*

@Composable
fun MovieCategoryChip(
    category: MovieCategory,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val bgColor   = if (category.isSelected) category.chipColor else SurfaceDark
    val textColor = if (category.isSelected) Color.White        else TextSecondary

    Column(
        modifier = modifier
            .width(90.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp, horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = category.emoji,
            fontSize = 26.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = category.title,
            style = MaterialTheme.typography.labelLarge,
            color = textColor,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "${category.movieCount} movies",
            style = MaterialTheme.typography.bodySmall,
            color = if (category.isSelected) Color.White.copy(alpha = 0.75f) else TextSecondary,
            fontSize = 10.sp
        )
    }
}