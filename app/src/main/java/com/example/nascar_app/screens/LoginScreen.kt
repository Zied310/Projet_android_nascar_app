

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nascar_app.R
import com.example.nascar_app.data.sharedPreference.LoginPreferenceManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(modifier: Modifier, navController: NavController, context: Context) {
    val loginPreferenceManager = LoginPreferenceManager(context)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var isValidEmail by remember { mutableStateOf(true) }
    var isValidPassword by remember { mutableStateOf(true) }
    var emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
    var isFormSubmitted by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top

    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "Nascar Logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(modifier = Modifier.height(164.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it},
            label = { Text("Email") },
            isError = !isValidEmail, // Show error outline if the email is invalid
            trailingIcon = {
                IconButton(
                    onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Email, contentDescription = "Email")
                }
            }

        )
        if (!isValidEmail && isFormSubmitted) {
            Text(
                text = "Invalid email address",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(
                    onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Lock, contentDescription = "Email")
                }
            }
        )
        if (isFormSubmitted && !isValidPassword) {
            Text(
                text = "Password must be at least 6 characters",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                isFormSubmitted = true
                isValidEmail = emailRegex.matches(email)
                isValidPassword = password.length >= 6
                if(isFormSubmitted && isValidPassword && isValidEmail){
                    if(rememberMe) {
                        loginPreferenceManager.saveLoginDetails(email, password)
                    }
                    navController.navigate("main_app_screen") // Navigate to main app screen
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            )

        ) {
            Text("Login")
        }


        Spacer(modifier = modifier.height(6.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = rememberMe,
                onCheckedChange = { rememberMe = it },
                colors = CheckboxDefaults.colors(checkedColor = Color.Black, uncheckedColor = Color.Black)

            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Remember Me")
        }
    }
}