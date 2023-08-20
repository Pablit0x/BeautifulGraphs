package com.ps.graphplayground.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.model.Point

@Composable
fun PointsItem(
    point: Point, index: Int, xOnChange: (Float) -> Unit, yOnChange: (Float) -> Unit
) {

    var pointX by remember { mutableStateOf(point.x.toString()) }
    var pointY by remember { mutableStateOf(point.y.toString()) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Point $index:", fontSize = 14.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.width(8.dp))

        OutlinedTextField(
            value = pointX, onValueChange = {
                pointX = it
                if (pointX.isNotEmpty()) {
                    xOnChange(pointX.toFloat())
                }
            }, keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ), label = { Text(text = "x") }, modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        OutlinedTextField(
            value = pointY, onValueChange = {
                pointY = it
                if (pointY.isNotEmpty()) {
                    yOnChange(pointY.toFloat())
                }
            }, keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ), label = { Text(text = "y") }, modifier = Modifier.weight(1f)
        )
    }
}