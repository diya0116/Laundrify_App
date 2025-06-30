package com.example.hostellaundrycode

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AddLaundryRequestScreen(
    onConfirmClick: () -> Unit
) {
    val itemTypes = listOf(
        "Shirts", "T-shirts", "Half pants (Shorts/Skirts)",
        "Full Pants (Jeans/Lowers)", "Jackets", "Sweater", "Hoodies", "Extras"
    )

    val requestTypes = listOf("Normal", "Urgent")
    var selectedRequest by remember { mutableStateOf(requestTypes[0]) }

    val slots = listOf("9:00 - 10:00 AM", "10:00 - 11:00 AM", "4:00 - 5:00 PM")
    var selectedSlot by remember { mutableStateOf("") }

    val itemCounts = remember { mutableStateMapOf<String, Int>() }
    itemTypes.forEach { itemCounts.putIfAbsent(it, 0) }

//    these were giving error so commented

//    val deliveryDate = LocalDate.of(2025, 7, 5)
//    val formattedDate = deliveryDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)) {

        Text("Add Laundry Request Details", fontWeight = FontWeight.Bold, fontSize = 20.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Request Type", fontWeight = FontWeight.SemiBold)
        DropdownMenuBox(
            options = requestTypes,
            selected = selectedRequest,
            onSelect = { selectedRequest = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Enter Clothes", fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(12.dp)) {
                itemTypes.forEach { type ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(type, modifier = Modifier.weight(1f))

                        Row {
                            Button(onClick = {
                                if ((itemCounts[type] ?: 0) > 0) itemCounts[type] = itemCounts[type]!! - 1
                            }, contentPadding = PaddingValues(0.dp), modifier = Modifier.size(32.dp)) {
                                Text("-")
                            }

                            Text("${itemCounts[type] ?: 0}", modifier = Modifier.padding(horizontal = 8.dp))

                            Button(onClick = {
                                itemCounts[type] = itemCounts[type]!! + 1
                            }, contentPadding = PaddingValues(0.dp), modifier = Modifier.size(32.dp)) {
                                Text("+")
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "***Enter clothes carefully. Your laundry can be rejected and will be received in next slot unwashed.",
            fontSize = 12.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Select Dropping Slot", fontWeight = FontWeight.SemiBold)
        DropdownMenuBox(
            options = slots,
            selected = selectedSlot,
            onSelect = { selectedSlot = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

//        Text("Estimated Delivery Date: $formattedDate", fontSize = 14.sp)
//        this has func formatted date jo mene comment out kiya tha  to yeh bhi error fenk rha tha

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onConfirmClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF003366))
        ) {
            Text("Confirm")
        }
    }
}

@Composable
fun DropdownMenuBox(
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true },
            label = { Text("Select") }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelect(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

