package com.example.hostellaundrycode

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginSelectorScreen(
    onStudentClick: () -> Unit,
    onAdminClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Logo (Replace with actual logo resource later)
//        Image(
//            painter = painterResource(id = R.drawable.laundry_logo), // Put your logo in drawable
//            contentDescription = "Laundrify Logo",
//            modifier = Modifier
//                .height(100.dp)
//                .padding(8.dp)
//        )

        Text(
            text = "Laundrify",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1565C0)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Student Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFD6E4F0))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Default.Add, contentDescription = null, tint = Color.Black, modifier = Modifier.size(32.dp))
                Text("Student", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(
                    text = "Manage laundry, book slots and get real-time updates about your clothes",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Button(onClick = onStudentClick) {
                    Text("Student Login")
                }
            }
        }

        // Admin Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFD6E4F0))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(32.dp))

                Text("Admin", fontWeight = FontWeight.Bold, fontSize = 20.sp)

                Text(
                    text = "View laundry requests and manage clothes efficiently",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Button(onClick = onAdminClick) {
                    Text("Admin Access")
                }
            }
        }
    }
}
