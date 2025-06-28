
package com.example.hostellaundrycode

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.hostellaundrycode.AdminLoginScreen
import com.example.hostellaundrycode.LoginSelectorScreen
import com.example.hostellaundrycode.StudentLoginScreen
import com.example.hostellaundrycode.StudentSignupScreen
import com.example.hostellaundrycode.StudentDashboardScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "selector") {

                composable("selector") {
                    LoginSelectorScreen(
                        onStudentClick = {
                            navController.navigate("studentLogin")
                        },
                        onAdminClick = {
                            navController.navigate("adminLogin")
                        }
                    )
                }

                composable("studentLogin") {
                    StudentLoginScreen(
                        onLoginClick = {
                            navController.navigate("studentdashboard")
                        },
                        onSignUpClick = {
                            navController.navigate("studentSignup")
                        }
                    )
                }

                composable("adminLogin") {
                    AdminLoginScreen(
                        onLoginClick = { /* handle admin login */ },
                        onRequestAccessClick = { /* handle request access */ }
                    )
                }

                composable("studentSignup") {
                    StudentSignupScreen(
                        onSignupClick = { name, rollNo, year ->
                            navController.navigate("dashboard/$name/$rollNo/$year")
                        },
                        onLoginClick = {
                            navController.navigate("studentLogin")
                        }
                    )
                }

                composable("studentdashboard") {
                    StudentDashboardScreen(
                        name = "Test User",
                        rollNo = "000000",
                        hostelInput = "Year 1",
                        onNewRequestClick = {  navController.navigate("laundryrequest")},
                        onHistoryClick = { /* ... */ },
                        onFeedbackClick = { /* ... */ }
                    )
                }
                composable("laundryrequest") {
                   AddLaundryRequestScreen (
                       onConfirmClick={}
                   )

                   }




                }
            }
        }
    }

