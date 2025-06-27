import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hostellaundrycode.AdminLoginScreen
import com.example.hostellaundrycode.LoginSelectorScreen
import com.example.hostellaundrycode.StudentLoginScreen
import com.example.hostellaundrycode.StudentSignupScreen


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
                        onLoginClick = { },
                        onSignUpClick = {
                            navController.navigate("studentSignup")
                        }
                    )
                }

                composable("adminLogin") {
                    AdminLoginScreen(
                        onLoginClick = {},
                        onRequestAccessClick = {}
                    )
                }

                composable("studentSignup") {
                    StudentSignupScreen(
                        onSignupClick = {},
                        onLoginClick = {}
                    )
                }
            }
        }
    }
}
