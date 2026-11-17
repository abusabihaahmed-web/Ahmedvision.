
package com.ahmedvision.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val navy = Color(0xFF071336)
private val purple = Color(0xFF8424CB)
private val gold = Color(0xFFFFC76B)

private val sections = listOf(
    "📺" to "القنوات المباشرة",
    "🎬" to "الأفلام",
    "🎞" to "المسلسلات",
    "⚽" to "الرياضة",
    "🧒" to "قنوات الأطفال",
    "⭐" to "المفضلة"
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AhmedVisionApp()
        }
    }
}

@Composable
fun AhmedVisionApp() {
    var selected by remember {
        mutableStateOf("الرئيسية")
    }

    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = gold,
            background = navy
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(navy, purple, navy)
                    )
                )
                .padding(16.dp)
        ) {
            Text(
                text = "AHMED VISION",
                color = gold,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(20.dp))

            if (selected == "الرئيسية") {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color(0xFF202052),
                            RoundedCornerShape(20.dp)
                        )
                        .padding(24.dp),
                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {
                    Text("🕌", fontSize = 65.sp)

                    Text(
                        "أهلاً بك في أحمد فيجن",
                        color = Color.White,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        "عالم الترفيه بين يديك",
                        color = gold,
                        fontSize = 16.sp
                    )
                }

                Spacer(Modifier.height(24.dp))

                Text(
                    "الأقسام",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(12.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement =
                        Arrangement.spacedBy(12.dp),
                    verticalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {
                    items(sections) { section ->
                        Column(
                            modifier = Modifier
                                .height(135.dp)
                                .background(
                                    Color(0xFF34246C),
                                    RoundedCornerShape(18.dp)
                                )
                                .clickable {
                                    selected = section.second
                                }
                                .padding(12.dp),
                            horizontalAlignment =
                                Alignment.CenterHorizontally,
                            verticalArrangement =
                                Arrangement.Center
                        ) {
                            Text(
                                section.first,
                                fontSize = 38.sp
                            )

                            Spacer(Modifier.height(8.dp))

                            Text(
                                section.second,
                                color = Color.White,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            } else {
                Text(
                    selected,
                    color = gold,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(20.dp))

                Text(
                    "سيتم إضافة المحتوى هنا",
                    color = Color.White
                )

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = {
                        selected = "الرئيسية"
                    }
                ) {
                    Text("العودة للرئيسية")
                }
            }
        }
    }
}
