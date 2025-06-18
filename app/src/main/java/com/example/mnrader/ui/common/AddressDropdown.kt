package com.example.mnrader.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mnrader.model.City

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressDropdown(
    modifier: Modifier = Modifier,
    selectedCity: City?,
    onCitySelected: (City) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier.padding(horizontal = 16.dp),
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedCity?.displayName ?: "지역을 선택하세요",
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color(0xFFCAC4D0),
                    shape = RoundedCornerShape(8.dp)
                ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
            )
        )

        ExposedDropdownMenu(
            modifier = Modifier.background(Color.White),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            City.entries.forEach { city ->
                DropdownMenuItem(
                    modifier = Modifier.background(Color.White),
                    text = { Text(city.displayName) },
                    onClick = {
                        onCitySelected(city)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 400)
@Composable
private fun AddressDropdownPreview() {
    Column {
        AddressDropdown(
            selectedCity = City.SEOUL,
            onCitySelected = {}
        )
    }
}