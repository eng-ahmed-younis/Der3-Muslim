package com.der3.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.ui.R
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale

@Composable
fun ZekrShareCard(
    text: String,
    count: Int,
    total: Int
) {
    val goldColor = Color(0xFFFFD700)
    
    Box(
        modifier = Modifier
            .width(420.dp)
            .heightIn(min = 720.dp)
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.islamic_share_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        // Gradient Overlay for depth and readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.5f),
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.7f)
                        )
                    )
                )
        )

        // Decorative Inner Border
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .border(
                    width = 1.5.dp, 
                    brush = Brush.verticalGradient(listOf(goldColor.copy(alpha = 0.6f), Color.Transparent, goldColor.copy(alpha = 0.6f))), 
                    shape = RoundedCornerShape(12.dp)
                )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 720.dp)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Header: Quranic Verse with decorative symbols
            Text(
                text = "۞﴿ أَلَا بِذِكْرِ اللَّهِ تَطْمَئِنُّ الْقُلُوبُ ﴾۞",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = goldColor,
                style = TextStyle(
                    textDirection = TextDirection.Rtl,
                    fontFamily = FontFamily(Font(R.font.cairo_bold))
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 28.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            
            // Elegant Separator
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(1.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color.Transparent, goldColor.copy(alpha = 0.8f), Color.Transparent)
                        )
                    )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Main Content: Zekr Text (Highlighted with subtle background)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = text,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 22.sp,
                    lineHeight = 42.sp,
                    fontWeight = FontWeight.Medium,
                    style = TextStyle(
                        textDirection = TextDirection.Rtl,
                        fontFamily = FontFamily(Font(R.font.cairo_bold))
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Stats Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(20.dp))
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatItem(label = "العدد الكلي", value = total.toString(), color = Color.White)
                    
                    // Vertical Divider
                    Box(modifier = Modifier.width(1.dp).height(35.dp).background(Color.White.copy(alpha = 0.2f)))
                    
                    StatItem(label = "تمت القراءة", value = count.toString(), color = goldColor)
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "📲 تمت المشاركة من تطبيق درع المسلم",
                    color = goldColor.copy(alpha = 0.9f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.cairo_bold))
                )
            }
            
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun StatItem(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.9f),
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.cairo_bold))
        )
        Text(
            text = value,
            color = color,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily(Font(R.font.cairo_bold))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ZekrShareCardPreview() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        ZekrShareCard(
            text = "✨ *((يَجْمَعُ كَفَّيْهِ ثُمَّ يَنْفُثُ فِيهِمَا فَيَقْرَأُ فِيهِمَا فَيَمْسَحُ بِهِمَا مَا اسْتَطَاعَ مِنْ جَسَدِهِ يَبْدَأُ بِهِمَا عَلَى رَأْسِهِ وَوَجْهِهِ وَمَا أَقْبَلَ مِنْ جَسَدِهِ)). ✨",
            count = 2,
            total = 3
        )
    }
}
