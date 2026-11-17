


package com.ahmedvision.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
    var showAddUser by remember {
        mutableStateOf(false)
    }
    var userName by remember {
        mutableStateOf("")
    }
    var savedUser by remember {
        mutableStateOf("")
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "AHMED VISION",
                color = gold,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(16.dp))

            if (selected == "الرئيسية") {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color(0xFF202052),
                            RoundedCornerShape(20.dp)
                        )
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(
                            id = R.drawable.aqsa_image
                        ),
                        contentDescription = "المسجد الأقصى",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(190.dp),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = "أهلاً بك في أحمد فيجن",
                        color = Color.White,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "عالم الترفيه بين يديك",
                        color = gold,
                        fontSize = 16.sp
                    )
                }

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = {
                        showAddUser = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "➕ إضافة مستخدم",
                        fontSize = 18.sp
                    )
                }

                if (savedUser.isNotBlank()) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "المستخدم: $savedUser",
                        color = Color.White
                    )
                }

                Spacer(Modifier.height(20.dp))

                Text(
                    "الأقسام",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(12.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.height(455.dp),
                    userScrollEnabled = false,
                    horizontalArrangement =
                        Arrangement.spacedBy(12.dp),
                    verticalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {
                    items(sections) { section ->
                        Column(
                            modifier = Modifier
                                .height(140.dp)
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
                Spacer(Modifier.height(20.dp))

                Text(
                    selected,
                    color = gold,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(20.dp))

                Text(
                    "لم تتم إضافة مصادر محتوى بعد",
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

        if (showAddUser) {
            AlertDialog(
                onDismissRequest = {
                    showAddUser = false
                },
                title = {
                    Text("إضافة مستخدم")
                },
                text = {
                    Column {
                        OutlinedTextField(
                            value = userName,
                            onValueChange = {
                                userName = it
                            },
                            label = {
                                Text("اسم المستخدم")
                            },
                            singleLine = true
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "سيتم عرض اسم المستخدم في الواجهة",
                            fontSize = 12.sp
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if (userName.isNotBlank()) {
                                savedUser = userName.trim()
                                showAddUser = false
                            }
                        }
                    ) {
                        Text("إضافة")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showAddUser = false
                        }
                    ) {
                        Text("إلغاء")
                    }
                }
            )
        }
    }
}
