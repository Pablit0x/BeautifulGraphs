package com.ps.graphplayground.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import co.yml.charts.common.model.Point
import com.ps.graphplayground.R

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
        Text(
            text = "${stringResource(id = R.string.point)} $index:",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.width(12.dp))

        OutlinedTextField(
            textStyle = TextStyle.Default.copy(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            value = pointX, onValueChange = {
                pointX = it
                try {
                    val x = pointX.toFloat()
                    xOnChange(x)
                } catch (ignored: Exception) {
                }
            }, keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ), label = { Text(text = "X") }, modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        OutlinedTextField(
            textStyle = TextStyle.Default.copy(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            value = pointY, onValueChange = {
                pointY = it
                try {
                    val y = pointY.toFloat()
                    yOnChange(y)
                } catch (ignored: Exception) { }
            }, keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ), label = { Text(text = "Y") }, modifier = Modifier.weight(1f)
        )
    }
}