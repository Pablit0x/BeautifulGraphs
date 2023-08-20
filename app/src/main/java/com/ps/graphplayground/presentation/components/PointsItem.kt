package com.ps.graphplayground.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import co.yml.charts.common.model.Point

@Composable
fun PointsItem(
    point: Point, index: Int, xOnChange: (Float) -> Unit, yOnChange: (Float) -> Unit
) {

    var pointX by remember { mutableStateOf(point.x.toString()) }
    var pointY by remember { mutableStateOf(point.y.toString()) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = "Point $index:", modifier = Modifier.weight(1f))

        OutlinedTextField(
            value = pointX, onValueChange = {
                pointX = it
                if (pointX.isNotEmpty()) {
                    if (pointX.toFloat() != 0f) {
                        xOnChange(pointX.toFloat())
                    }
                }
            }, keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ), modifier = Modifier.weight(1f)
        )

        OutlinedTextField(
            value = pointY, onValueChange = {
                pointY = it
                if (pointY.isNotEmpty()) {
                    if (pointY.toFloat() != 0f) {
                        yOnChange(pointY.toFloat())
                    }
                }
            }, keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ), modifier = Modifier.weight(1f)
        )
    }
}