package com.example.hostellaundrycode

import android.os.Build
import androidx.annotation.RequiresApi
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

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp


@Composable
fun AddLaundryRequestScreen(
    onConfirmClick: () -> Unit
) {
    val itemTypes = listOf(
        "Shirts", "T-shirts", "Half pants (Shorts/Skirts)",
        "Full Pants (Jeans/Lowers)", "Jackets", "Sweater", "Hoodies", "Extras"
    )

    val requestTypes = listOf("Normal", "Delicate", "Dry Clean")
    var selectedRequest by remember { mutableStateOf(requestTypes[0]) }

    val slots = remember { generateSimpleTimeSlots() }

    var selectedSlot by remember { mutableStateOf("") }

    val itemCounts = remember { mutableStateMapOf<String, Int>() }
    itemTypes.forEach { itemCounts.putIfAbsent(it, 0) }

    val totalClothes by remember {
        derivedStateOf { itemCounts.values.sum() }
    }


    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Request Confirmed") },
            text = { Text("Your laundry request has been submitted.") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    onConfirmClick()
                }) {
                    Text("OK")
                }
            }
        )
    }

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

                            Button(
                                onClick = {
                                    if (itemCounts.values.sum() < 10) {
                                        itemCounts[type] = itemCounts[type]!! + 1
                                    }
                                },
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier.size(32.dp)
                            ) {
                                Text("+")
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Max 10 items. Requests exceeding this will be rejected.",
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

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                showDialog = true
            },
            enabled = totalClothes in 1..10 && selectedSlot.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF003366))
        ) {
            Text("Confirm")
        }
    }
}

// Reusable dropdown menu
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuBox(
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            label = { Text("Select") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            modifier = Modifier
                .menuAnchor() // ✅ Needed for anchoring
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
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




fun generateSimpleTimeSlots(): List<String> {
    val slots = mutableListOf<String>()

    val morningStart = 10 * 60
    val morningEnd = 14 * 60
    val eveningStart = 16 * 60
    val eveningEnd = 18 * 60

    for (time in morningStart until morningEnd step 15) {
        val hour = time / 60
        val minute = time % 60
        val ampm = if (hour < 12) "AM" else "PM"
        val displayHour = if (hour == 0 || hour == 12) 12 else hour % 12
        slots.add(String.format("%d:%02d %s", displayHour, minute, ampm))
    }

    for (time in eveningStart until eveningEnd step 15) {
        val hour = time / 60
        val minute = time % 60
        val ampm = if (hour < 12) "AM" else "PM"
        val displayHour = if (hour == 0 || hour == 12) 12 else hour % 12
        slots.add(String.format("%d:%02d %s", displayHour, minute, ampm))
    }

    return slots
}
