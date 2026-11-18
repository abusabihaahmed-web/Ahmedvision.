

package com.ahmedvision.app

import android.os.Bundle
import android.net.Uri
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDirection
import java.io.IOException
import java.net.URI
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
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
    var selected by remember { mutableStateOf("الرئيسية") }
    var showAddUser by remember { mutableStateOf(false) }
    var signedIn by remember { mutableStateOf<XtreamAccount?>(null) }

    MaterialTheme(
        colorScheme = darkColorScheme(primary = gold, background = navy)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(navy, purple, navy)))
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
                        .background(Color(0xFF202052), RoundedCornerShape(20.dp))
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.aqsa_image),
                        contentDescription = "المسجد الأقصى",
                        modifier = Modifier.fillMaxWidth().height(190.dp),
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
                    onClick = { showAddUser = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("➕ إضافة مستخدم", fontSize = 18.sp)
                }

                if (signedIn != null) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "تم تسجيل الدخول: ${signedIn?.username}",
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
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(sections) { section ->
                        Column(
                            modifier = Modifier
                                .height(140.dp)
                                .background(Color(0xFF34246C), RoundedCornerShape(18.dp))
                                .clickable { selected = section.second }
                                .padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(section.first, fontSize = 38.sp)
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

                Text("لم تتم إضافة مصادر محتوى بعد", color = Color.White)

                Spacer(Modifier.height(20.dp))

                Button(onClick = { selected = "الرئيسية" }) {
                    Text("العودة للرئيسية")
                }
            }
        }

        if (showAddUser) {
            XtreamLoginDialog(
                account = signedIn,
                onDismiss = { showAddUser = false },
                onSignedIn = { account ->
                    signedIn = account
                    showAddUser = false
                }
            )
        }
    }
}

private class XtreamAccount(
    val server: URI,
    val username: String,
    val activationCode: String
)

@Composable
private fun XtreamLoginDialog(
    account: XtreamAccount?,
    onDismiss: () -> Unit,
    onSignedIn: (XtreamAccount) -> Unit
) {
    var username by remember { mutableStateOf(account?.username.orEmpty()) }
    var password by remember { mutableStateOf("") }
    var serverUrl by remember { mutableStateOf(account?.server?.toString().orEmpty()) }
    var activationCode by remember { mutableStateOf(account?.activationCode.orEmpty()) }
    var error by remember { mutableStateOf<String?>(null) }
    var signingIn by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val inputStyle = LocalTextStyle.current.copy(textDirection = TextDirection.Ltr)

    AlertDialog(
        onDismissRequest = { if (!signingIn) onDismiss() },
        title = { Text("إضافة مستخدم · Xtream Codes") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("اسم المستخدم") },
                    enabled = !signingIn,
                    singleLine = true,
                    textStyle = inputStyle,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("كلمة المرور") },
                    enabled = !signingIn,
                    singleLine = true,
                    textStyle = inputStyle,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = serverUrl,
                    onValueChange = { serverUrl = it },
                    label = { Text("رابط سيرفر Xtream") },
                    placeholder = { Text("https://example.com:8443") },
                    enabled = !signingIn,
                    singleLine = true,
                    textStyle = inputStyle,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = activationCode,
                    onValueChange = { activationCode = it },
                    label = { Text("كود التفعيل (اختياري)") },
                    enabled = !signingIn,
                    singleLine = true,
                    textStyle = inputStyle,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    "التحقق من كود التفعيل يتطلب الربط بخدمة المزوّد.",
                    style = MaterialTheme.typography.bodySmall
                )
                error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            }
        },
        confirmButton = {
            TextButton(
                enabled = !signingIn,
                onClick = submit@{
                    val server = parseXtreamServer(serverUrl)
                    val loginName = username.trim()
                    if (loginName.isEmpty() || password.isBlank()) {
                        error = "أدخل اسم المستخدم وكلمة المرور."
                        return@submit
                    }
                    if (server == null) {
                        error = "أدخل رابط سيرفر صالحًا يبدأ بـ https://."
                        return@submit
                    }

                    val loginPassword = password
                    val newAccount = XtreamAccount(server, loginName, activationCode.trim())
                    error = null
                    signingIn = true

                    scope.launch {
                        try {
                            val rejection = checkXtreamLogin(newAccount, loginPassword)
                            if (rejection == null) {
                                password = ""
                                onSignedIn(newAccount)
                            } else {
                                error = rejection
                            }
                        } finally {
                            signingIn = false
                        }
                    }
                }
            ) {
                Text(if (signingIn) "جارٍ التحقق…" else "تسجيل الدخول")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !signingIn) {
                Text("إلغاء")
            }
        }
    )
}

private fun parseXtreamServer(input: String): URI? {
    val server = try {
        URI(input.trim()).parseServerAuthority()
    } catch (_: java.net.URISyntaxException) {
        return null
    }

    return server.takeIf {
        it.scheme.equals("https", ignoreCase = true) && it.host != null &&
            it.rawUserInfo == null && it.rawQuery == null && it.rawFragment == null &&
            (it.port == -1 || it.port in 1..65535)
    }
}

private suspend fun checkXtreamLogin(account: XtreamAccount, password: String): String? =
    withContext(Dispatchers.IO) {
        try {
            val base = account.server.toASCIIString().trimEnd('/')
            val endpoint = if (base.endsWith("/player_api.php")) base else "$base/player_api.php"
            val loginUrl = Uri.parse(endpoint).buildUpon()
                .appendQueryParameter("username", account.username)
                .appendQueryParameter("password", password)
                .build()

            val connection = URL(loginUrl.toString()).openConnection() as HttpsURLConnection
            try {
                connection.connectTimeout = 10_000
                connection.readTimeout = 10_000
                connection.instanceFollowRedirects = false
                connection.useCaches = false
                connection.setRequestProperty("Accept", "application/json")

                val responseCode = connection.responseCode
                when (responseCode) {
                    401, 403 -> "رفض السيرفر تسجيل الدخول."
                    in 200..299 -> {
                        val response = connection.inputStream.bufferedReader().use { it.readText() }
                        val userInfo = JSONObject(response).getJSONObject("user_info")
                        when {
                            userInfo.optInt("auth") != 1 ->
                                "رفض السيرفر تسجيل الدخول. تحقّق من بيانات الاشتراك."
                            !userInfo.optString("status").equals("Active", ignoreCase = true) ->
                                "الاشتراك غير نشط. راجع مزوّد الاشتراك."
                            else -> null
                        }
                    }
                    else -> "تعذّر تسجيل الدخول. استجاب السيرفر بالرمز $responseCode."
                }
            } finally {
                connection.disconnect()
            }
        } catch (_: JSONException) {
            "استجابة السيرفر لا تطابق واجهة Xtream Codes."
        } catch (_: IOException) {
            "تعذّر الاتصال بالسيرفر. تحقّق من الرابط والاتصال بالإنترنت."
        }
    }