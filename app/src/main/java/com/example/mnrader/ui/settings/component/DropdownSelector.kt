package com.example.mnrader.ui.settings.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelector(
    label: String,
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit
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
            label = { Text(label, color = Color.Gray) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFE0E0E0),
                unfocusedBorderColor = Color(0xFFE0E0E0),
                disabledBorderColor = Color(0xFFE0E0E0),
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.White)
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        onSelected(selectionOption)
                        expanded = false
                    }
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DropdownSelectorPreview() {
    var selectedOption by remember { mutableStateOf("강아지") }

    val options = listOf("강아지", "고양이", "기타")

    MaterialTheme {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            DropdownSelector(
                label = "종류",
                options = options,
                selected = selectedOption,
                onSelected = { selectedOption = it }
            )
        }
    }
}
