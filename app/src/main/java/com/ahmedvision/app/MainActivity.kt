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
private val entries = listOf("📺" to "القنوات المباشرة", "🎬" to "الأفلام", "🎞" to "المسلسلات", "⚽" to "الرياضة", "🧒" to "قنوات الأطفال", "⭐" to "المفضلة", "🔎" to "بحث", "🔄" to "تحديث المحتوى", "📅" to "دليل البرامج")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); setContent { AhmedVisionApp() } }
}

@Composable
fun AhmedVisionApp() {
    var screen by remember { mutableStateOf("home") }
    var method by remember { mutableStateOf("xtream") }
    var server by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }
    val colors = listOf(Color(0xFF6A22B9), Color(0xFF0764D7), Color(0xFFCB176B), Color(0xFF009DAB), Color(0xFFED8A17), Color(0xFF7534C4))
    MaterialTheme(colorScheme = darkColorScheme(primary = gold, background = navy, surface = Color(0xFF12244B))) {
        Column(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color(0xFF081135), Color(0xFF17265C), Color(0xFF130C36)))).padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("📺 Ahmed Vision", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = gold)
                Spacer(Modifier.weight(1f))
                TextButton(onClick = { screen = if (screen == "login") "home" else "login" }) { Text(if (screen == "login") "الرئيسية" else "تسجيل الدخول") }
            }
            Spacer(Modifier.height(24.dp))
            if (screen == "home") {
                Text("أهلًا بك في Ahmed Vision", color = Color.White, fontSize = 24.sp)
                Text("واجهة تجريبية — اربط اشتراكك المرخّص لتظهر المحتويات", color = Color.LightGray)
                Spacer(Modifier.height(20.dp))
                LazyVerticalGrid(columns = GridCells.Adaptive(155.dp), horizontalArrangement = Arrangement.spacedBy(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(entries) { entry ->
                        val index = entries.indexOf(entry)
                        Column(modifier = Modifier.height(145.dp).background(Brush.verticalGradient(listOf(colors[index % colors.size], navy)), RoundedCornerShape(20.dp)).border(1.dp, gold.copy(alpha = .35f), RoundedCornerShape(20.dp)).clickable { screen = entry.second }.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                            Text(entry.first, fontSize = 35.sp)
                            Spacer(Modifier.height(10.dp))
                            Text(entry.second, color = Color.White, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                        }
                    }
                }
            } else if (screen == "login") {
                Text("تسجيل الدخول", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                Row { FilterChip(selected = method == "xtream", onClick = { method = "xtream" }, label = { Text("Xtream") }); Spacer(Modifier.width(10.dp)); FilterChip(selected = method == "code", onClick = { method = "code" }, label = { Text("كود التفعيل") }) }
                Spacer(Modifier.height(12.dp))
                if (method == "xtream") {
                    OutlinedTextField(server, { server = it }, label = { Text("رابط الخادم HTTPS") }, singleLine = true)
                    OutlinedTextField(username, { username = it }, label = { Text("اسم المستخدم") }, singleLine = true)
                    OutlinedTextField(password, { password = it }, label = { Text("كلمة المرور") }, singleLine = true, visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation())
                    Text("الاتصال بالخادم قيد التطوير؛ لا تُدخل بيانات حقيقية في هذه النسخة التجريبية.", color = Color.LightGray)
                } else {
                    OutlinedTextField(code, { code = it }, label = { Text("كود التفعيل") }, singleLine = true)
                    Text("تفعيل الأكواد يتطلب خادم إدارة اشتراكات؛ غير متصل حاليًا.", color = Color.LightGray)
                }
                Spacer(Modifier.height(16.dp))
                Button(onClick = {}, enabled = false) { Text("اتصال — قريبًا") }
            } else {
                Text(screen, color = gold, fontSize = 26.sp)
                Spacer(Modifier.height(12.dp))
                Text("سيظهر المحتوى هنا بعد ربط الخادم والتحقق من الاشتراك.", color = Color.White)
                Spacer(Modifier.height(12.dp))
                Button(onClick = { screen = "home" }) { Text("العودة للرئيسية") }
            }
        }
    }
}
