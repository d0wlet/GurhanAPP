package com.gurhan.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gurhan.data.model.Surah
import com.gurhan.ui.theme.TealPrimary

@Composable
fun SurahCard(
    surah: Surah,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Number container
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(
                            color = TealPrimary.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = surah.id.toString(),
                        fontWeight = FontWeight.Bold,
                        color = TealPrimary,
                        fontSize = 16.sp
                    )
                }
                
                // Text group
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = surah.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = surah.revelationType,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Box(
                            modifier = Modifier
                                .size(3.dp)
                                .background(Color.LightGray, shape = androidx.compose.foundation.shape.CircleShape)
                        )
                        Text(
                            text = "${surah.versesCount} Aýat",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
            
            // Arabic name
            Text(
                text = surah.arabicName,
                fontSize = 20.sp,
                color = TealPrimary
            )
        }
    }
}
