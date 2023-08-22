package com.ps.graphplayground.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun ColorPicker(
    initialColor: Color,
    onChangeColor: (Color) -> Unit,
    onChangeLineColor: (Color) -> Unit,
    onChangePointColor: (Color) -> Unit,
    onChangeXAxisColor: (Color) -> Unit,
    onChangeYAxisColor: (Color) -> Unit,
    onAccept: () -> Unit,
    onCancel: (Color) -> Unit
) {
    val colorController = rememberColorPickerController()

    var color by remember { mutableStateOf(Color.Unspecified) }
    val initColor = remember { initialColor }


    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

        var currentOptionIndex by remember { mutableStateOf(0) }

        val options = listOf("All", "Line", "Point", "X Axis", "Y Axis")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onCancel(initColor) }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }

            IconButton(onClick = { onAccept() }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        HsvColorPicker(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f),
            initialColor = initialColor,
            controller = colorController,
            onColorChanged = { colorEnvelope: ColorEnvelope ->
                color = colorEnvelope.color

                when (currentOptionIndex) {
                    0 -> onChangeColor(color)
                    1 -> onChangeLineColor(color)
                    2 -> onChangePointColor(color)
                    3 -> onChangeXAxisColor(color)
                    4 -> onChangeYAxisColor(color)
                }

            })

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            shape = RoundedCornerShape(20),
            modifier = Modifier.fillMaxWidth(0.6f),
            value = options[currentOptionIndex],
            onValueChange = {},
            readOnly = true,
            textStyle = TextStyle.Default.copy(
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            trailingIcon = {
                IconButton(onClick = {
                    if (currentOptionIndex < options.size - 1) {
                        currentOptionIndex++
                    } else {
                        currentOptionIndex = 0
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null
                    )
                }
            },
            leadingIcon = {
                IconButton(onClick = {
                    if (currentOptionIndex > 0) {
                        currentOptionIndex--
                    } else {
                        currentOptionIndex = options.size - 1
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null
                    )
                }
            })
    }
}