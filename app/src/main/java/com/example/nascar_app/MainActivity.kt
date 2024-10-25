package com.example.nascar_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nascar_app.screens.NewsScreen
import com.example.nascar_app.ui.theme.Nascar_appTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Nascar_appTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun loginScreen(modifier: Modifier, navController: NavController) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
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
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun mainAppScreen(modifier: Modifier) {
        var selectedTabIndex by remember { mutableStateOf(0) }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Nascar App")
                    },
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = Color.Black,
                        titleContentColor = Color.White
                    )
                )
            },
            content = { paddingValues ->
                Column(modifier = Modifier.padding(paddingValues)) {
                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        modifier = Modifier.fillMaxWidth(),
                    ){
                        val titles = listOf("News", "Events", "Profile")
                        titles.forEachIndexed { index, title ->
                            Tab(
                                text = { Text(title, color = Color.Black) },
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index }
                            )
                        }
                    }

                    when(selectedTabIndex){
                        0 -> NewsScreen(modifier = modifier)
                    }
                }
            }
        )
    }

    @Composable
    fun Navigation() {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "login_form") {
            composable("login_form") { loginScreen(modifier = Modifier, navController = navController) }
            composable("main_app_screen") { mainAppScreen(modifier = Modifier) }
        }
    }




}