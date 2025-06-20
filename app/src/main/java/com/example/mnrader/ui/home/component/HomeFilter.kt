package com.example.mnrader.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mnrader.model.CatBreed
import com.example.mnrader.model.City
import com.example.mnrader.model.DogBreed
import com.example.mnrader.ui.common.FilterChip

@Composable
fun HomeFilter(
    modifier: Modifier = Modifier,
    city: City? = null,
    breed: String? = null,
    onLocationUpdate: (City) -> Unit = {},
    onBreedUpdate: (String?) -> Unit = {},
    isWitnessShown: Boolean,
    onWitnessClick: (Boolean) -> Unit = {},
    isLostShown: Boolean,
    onLostClick: (Boolean) -> Unit = {},
    isProtectShown: Boolean,
    onProtectClick: (Boolean) -> Unit = {},
) {
    val scrollState = rememberScrollState()
    Row(
        modifier = modifier
            .horizontalScroll(scrollState)
            .shadow(1.dp)
            .padding(vertical = 7.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        HomeAddressDropdown(
            selectedCity = city,
        ) {
            onLocationUpdate(it)
        }
        HomeBreedDropdown(
            selectedBreed = breed,
        ) {
            onBreedUpdate(it)
        }
        FilterChip(
            text = "목격한 동물",
            isShown = isWitnessShown,
            onClick = onWitnessClick
        )
        FilterChip(
            text = "실종 동물",
            isShown = isLostShown,
            onClick = onLostClick
        )
        FilterChip(
            text = "보호중",
            isShown = isProtectShown,
            onClick = onProtectClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAddressDropdown(
    modifier: Modifier = Modifier,
    selectedCity: City?,
    onCitySelected: (City) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .wrapContentWidth()
            .widthIn(min = 120.dp),
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedCity?.displayName ?: "지역",
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .wrapContentWidth()
                .widthIn(min = 120.dp)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBreedDropdown(
    modifier: Modifier = Modifier,
    selectedBreed: String?,
    onBreedSelected: (String?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .wrapContentWidth()
            .widthIn(min = 120.dp), // 기본은 120dp, 내용이 크면 자동 확장,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedBreed ?: "품종",
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .wrapContentWidth()
                .widthIn(min = 120.dp)
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
            DropdownMenuItem(
                modifier = Modifier.background(Color.White),
                text = { Text("전체") },
                onClick = {
                    onBreedSelected(null)
                    expanded = false
                }
            )
            DogBreed.entries.forEach { dog ->
                DropdownMenuItem(
                    modifier = Modifier.background(Color.White),
                    text = { Text(dog.breedName) },
                    onClick = {
                        onBreedSelected(dog.breedName)
                        expanded = false
                    }
                )
            }
            CatBreed.entries.forEach { cat ->
                DropdownMenuItem(
                    modifier = Modifier.background(Color.White),
                    text = { Text(cat.breedName) },
                    onClick = {
                        onBreedSelected(cat.breedName)
                        expanded = false
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun HomeFilterPreview() {
    HomeFilter(
        isWitnessShown = true,
        isLostShown = false,
        isProtectShown = true,
        onBreedUpdate = {},
        onWitnessClick = {},
        onLostClick = {},
        onProtectClick = {},
        city = null,
    )
}