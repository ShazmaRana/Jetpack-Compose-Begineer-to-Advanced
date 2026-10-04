package com.example.navigationapp


import AppNavigation
import android.R
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.navigationapp.ui.theme.NavigationAppTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationAppTheme {
                AppNavigation()
                }
            }
        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    onNavigateNext: (String, String, String, String, String, String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var rollnum by remember { mutableStateOf("") }
    var university by remember { mutableStateOf("") }
    var skills by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? -> profileImageUri = uri }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Student Professional Form", fontWeight = FontWeight.SemiBold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Tell us about yourself",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            // Avatar + edit badge
            Box(modifier = Modifier.size(120.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .clickable { launcher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (profileImageUri != null) {
                        AsyncImage(
                            model = profileImageUri,
                            contentDescription = "Profile Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            Icons.Default.PersonSearch, "Default Image",
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.BottomEnd)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Edit, "Edit Profile Picture",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            FormField("Full Name", name, "e.g. John Doe", Icons.Default.Person) { name = it }
            FormField("Email", email, "name@gmail.com", Icons.Default.Email,
                KeyboardType.Email) { email = it }
            FormField("Roll Number", rollnum, "e.g. 21SW01", Icons.Default.Badge) { rollnum = it }
            FormField("University", university, "e.g. MUET", Icons.Default.School) { university = it }
            FormField("Skills", skills, "Kotlin, Compose", Icons.Default.Code) { skills = it }
            FormField("Experience", experience, "e.g. 1 year", Icons.Default.Work) { experience = it }

            Spacer(Modifier.height(4.dp))

            Button(
                onClick = {
                    onNavigateNext(
                        name, email, rollnum, university, skills, experience,
                        profileImageUri?.toString() ?: ""
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Submit", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Composable
private fun FormField(
    label: String,
    value: String,
    placeholder: String,
    icon: ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        leadingIcon = { Icon(icon, contentDescription = null) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.fillMaxWidth()
    )
}

// 1. ADD THIS PREVIEW FOR YOUR FORM
@Preview(showBackground = true)
@Composable
fun AddScreenPreview() {
    NavigationAppTheme {
        AddScreen(
            onNavigateNext = {_, _, _, _, _, _, _ ->}
        )
    }
}



@Composable
fun ProfileScreen(
    name: String, email: String, rollnum: String,
    university: String, skills: String, experience: String,
    imageUri: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        )
                    )
                )
                .statusBarsPadding()
                .padding(top = 24.dp, bottom = 64.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "My Profile",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        // Avatar overlapping the header
        Box(
            modifier = Modifier
                .offset(y = (-60).dp)
                .size(120.dp)
                .shadow(8.dp, CircleShape)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            if (imageUri.isNotEmpty()) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Profile Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    Icons.Default.Person, "Default Image",
                    modifier = Modifier.size(56.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Name sits just under the avatar
        Column(
            modifier = Modifier.offset(y = (-48).dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                university,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Details card
        Card(
            modifier = Modifier
                .offset(y = (-32).dp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(Modifier.padding(vertical = 8.dp)) {
                ProfileRow(Icons.Default.Email, "Email", email)
                HorizontalDivider(Modifier.padding(horizontal = 16.dp))
                ProfileRow(Icons.Default.Badge, "Roll No", rollnum)
                HorizontalDivider(Modifier.padding(horizontal = 16.dp))
                ProfileRow(Icons.Default.School, "University", university)
                HorizontalDivider(Modifier.padding(horizontal = 16.dp))
                ProfileRow(Icons.Default.Code, "Skills", skills)
                HorizontalDivider(Modifier.padding(horizontal = 16.dp))
                ProfileRow(Icons.Default.Work, "Experience", experience)
            }
        }
    }
}

@Composable
private fun ProfileRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        }
        Spacer(Modifier.width(16.dp))
        Column {
            Text(
                label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                value.ifBlank { "-" },
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
@Preview(showBackground = true , showSystemUi = true)
@Composable
private fun ProfileScreenPrev() {

    NavigationAppTheme{
        //Adding fake Sample data for preview
//        These values are only for the preview, not for your running app. At runtime, the real values flow from AddScreen → navController.navigate(ProfileRoute(...)) → toRoute<ProfileRoute>() → ProfileScreen(...), and the preview code never runs.

ProfileScreen(
    name = "John Doe",
    email = "john2@gmail.com",
    rollnum = "sw11",
    university = "LUMS",
    skills = "AI Engineer",
    experience = "3 Years",
    imageUri = ""
)


//
//        The reason the preview needs them is that Android Studio's preview renders ProfileScreen in isolation. It doesn't run your NavHost or AddScreen, so nothing is there to supply the arguments. Without sample values, the preview has nothing to display (and Kotlin won't compile a call missing required parameters).
//
//        So there are two separate call sites:
//
//        Where	Who provides the values
//        Real app (AppNavigation)	AddScreen's form, via the route
//        @Preview function	Fake sample data you type, just for design-time viewing
//
//        Because @Preview functions are never part of your app's actual flow, the sample data doesn't affect anything. You can change it freely to test how the layout looks, for example with a very long name or empty skills.
//
//        If you don't want to type sample values in the preview at all, you have two options:
//
//        Give the parameters defaults in the composable:
//        kotlin
//        @Composable
//        fun ProfileScreen(
//            name: String = "",
//            email: String = "",
//            rollnum: String = "",
//            university: String = "",
//            skills: String = "",
//            experience: String = "",
//            imageUri: String = ""
//        )
//
//        Then ProfileScreen() in the preview compiles as-is, but it will show blank text. Defaults are also hiding mistakes, since forgetting to pass a value in the NavGraph would silently show an empty field instead of giving a compile error.
//
//        Delete the preview entirely. Previews are optional and only a development convenience.
//
//        I'd keep the preview with sample data (the first approach). It shows you the real layout, and the required parameters keep you honest in the NavGraph.
//        ProfileScreen()

    }
}