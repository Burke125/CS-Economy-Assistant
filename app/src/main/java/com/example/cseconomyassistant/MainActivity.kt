package com.example.cseconomyassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.cseconomyassistant.data.auth.AuthGate
import com.example.cseconomyassistant.ui.navigation.AppNavGraph
import com.example.cseconomyassistant.ui.navigation.BottomNavigationBar
import com.example.cseconomyassistant.ui.theme.CSEconomyAssistantTheme
import com.example.cseconomyassistant.ui.viewmodel.RoundViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        val auth = FirebaseAuth.getInstance()

        auth.signInAnonymously()
            .addOnSuccessListener {
                android.util.Log.d("AUTH_DEBUG", "Signed in, uid=${it.user?.uid}")
            }
            .addOnFailureListener { e ->
                android.util.Log.e("AUTH_DEBUG", "Sign in failed", e)
            }

        enableEdgeToEdge()

        setContent {
            CSEconomyAssistantTheme(darkTheme = true) {
                AppEntryPoint()
            }
        }
    }
}

@Composable
fun AppEntryPoint() {

    val navController = rememberNavController()
    val roundViewModel: RoundViewModel = viewModel()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
        ) {
            AuthGate {
                AppNavGraph(
                    navController = navController,
                    roundViewModel = roundViewModel
                )
            }
        }
    }
}
