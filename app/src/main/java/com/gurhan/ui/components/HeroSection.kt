package com.gurhan.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gurhan.ui.theme.TealGradientEnd
import com.gurhan.ui.theme.TealGradientStart

@Composable
fun HeroSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(TealGradientStart, TealGradientEnd)
                ),
                shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
            )
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Greeting
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // Date badge
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White.copy(alpha = 0.2f),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(
                        text = "📅 Hijri Date",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
                
                // Greeting
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Essalamu Aleýkum",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Hoş geldiňiz",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
            
            // Verse of the Day Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "⭐ GÜNÜN AÝATY",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = TealGradientStart
                        )
                        Text(
                            text = "Fatiha 1",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    }
                    
                    Text(
                        text = "بِسْمِ ٱللَّهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ",
                        fontSize = 24.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.End
                    )
                    
                    Text(
                        text = "Rehimli we Mähirbanly Allahyň ady bilen",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.background
                        )
                    ) {
                        Text("Doly Oka →", color = TealGradientStart)
                    }
                }
            }
        }
    }
}
