package com.ps.graphplayground.presentation.graph_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.extensions.formatToSinglePrecision
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.ps.graphplayground.R
import com.ps.graphplayground.presentation.components.ColorPicker
import com.ps.graphplayground.presentation.components.PointsItem
import com.ps.graphplayground.presentation.model.LineTypeMenuItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GraphScreen() {
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val context = LocalContext.current
    var showPoints by remember { mutableStateOf(true) }
    var showSettings by remember { mutableStateOf(false) }
    var showColorPicker by remember { mutableStateOf(false) }

    val initialColor = MaterialTheme.colorScheme.primary
    val initialGridLinesColor = MaterialTheme.colorScheme.outline

    var steps by remember { mutableStateOf(5) }
    val dataPoints = remember {
        mutableStateListOf(
            Point(x = 0f, y = 40f),
            Point(x = 1f, y = -20f),
            Point(x = 2f, y = 0f),
            Point(x = 3f, y = 20f),
            Point(x = 4f, y = 40f)
        )
    }

    var chartLineColor by remember { mutableStateOf(initialColor) }
    var xAxisLineColor by remember { mutableStateOf(initialColor) }
    var yAxisLineColor by remember { mutableStateOf(initialColor) }
    var intersectionPointColor by remember { mutableStateOf(initialColor) }

    val colorLinearGradient = Brush.linearGradient(
        listOf(
            chartLineColor,
            intersectionPointColor,
            xAxisLineColor,
            yAxisLineColor
        )
    )

    var showGridLines by remember { mutableStateOf(false) }
    var isDotted by remember { mutableStateOf(false) }


    val lineTypes = remember(isDotted) {
        mutableStateListOf(
            LineTypeMenuItem(
                id = 0,
                text = context.getString(R.string.smooth),
                type = LineType.SmoothCurve(isDotted = isDotted),
                icon = R.drawable.smooth_line
            ), LineTypeMenuItem(
                id = 1,
                text = context.getString(R.string.straight),
                type = LineType.Straight(isDotted = isDotted),
                icon = R.drawable.straight_line
            )
        )
    }

    var selectedIndex by remember { mutableStateOf(0) }
    var selectedLineType by remember(
        lineTypes,
        selectedIndex
    ) { mutableStateOf(lineTypes[selectedIndex]) }

    val gridLines: GridLines by remember { mutableStateOf(GridLines(initialGridLinesColor)) }

    val xAxisData = AxisData.Builder().axisStepSize(100.dp).steps(dataPoints.size - 1)
        .labelData { value -> value.toString() }.labelAndAxisLinePadding(15.dp)
        .axisLineColor(xAxisLineColor).axisLabelColor(MaterialTheme.colorScheme.primary)
        .axisLabelFontSize(12.sp).build()

    val yAxisData = AxisData.Builder().steps(steps).labelAndAxisLinePadding(20.dp).labelData { i ->
        val yMin = dataPoints.minOf { it.y }
        val yMax = dataPoints.maxOf { it.y }
        val yScale = (yMax - yMin) / steps
        ((i * yScale) + yMin).formatToSinglePrecision()
    }.axisLineColor(yAxisLineColor).axisLabelColor(MaterialTheme.colorScheme.primary)
        .axisLabelFontSize(12.sp).build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = dataPoints,
                    LineStyle(
                        color = chartLineColor, lineType = selectedLineType.type
                    ),
                    intersectionPoint = IntersectionPoint(intersectionPointColor),
                    selectionHighlightPoint = SelectionHighlightPoint(MaterialTheme.colorScheme.secondary),
                    shadowUnderLine = ShadowUnderLine(
                        alpha = 0.5f, brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.inversePrimary, Color.Transparent
                            )
                        )
                    ),
                    selectionHighlightPopUp = SelectionHighlightPopUp()
                )
            )
        ),
        backgroundColor = MaterialTheme.colorScheme.surface,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = if (showGridLines) gridLines else null
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LineChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp), lineChartData = lineChartData
        )

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            ElevatedButton(
                shape = RoundedCornerShape(40),
                onClick = { showPoints = !showPoints },
                modifier = Modifier.weight(2.5f)
            ) {
                Text(
                    text = stringResource(id = R.string.points),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = if (showPoints) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.weight(0.5f))

            Column(modifier = Modifier.weight(2f)) {
                AnimatedVisibility(
                    visible = showPoints, enter = fadeIn(), exit = fadeOut()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                dataPoints.add(
                                    Point(
                                        x = dataPoints.last().x + 1f, y = dataPoints.last().y + 1f
                                    )
                                )
                            }, colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = {
                                dataPoints.removeLast()
                            }, colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer,
                                contentColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Icon(imageVector = Icons.Default.Remove, contentDescription = null)
                        }
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(10f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            itemsIndexed(dataPoints) { index, item ->
                AnimatedVisibility(
                    visible = showPoints
                ) {
                    PointsItem(point = item, index = index, xOnChange = {
                        dataPoints[index] = Point(x = it, y = item.y)
                    }, yOnChange = {
                        dataPoints[index] = Point(x = item.x, y = it)
                    })
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), contentAlignment = Alignment.BottomCenter
        ) {
            TextButton(onClick = { showSettings = !showSettings }) {
                Text(stringResource(id = R.string.show_settings))
            }
        }

        if (showSettings) {
            ModalBottomSheet(sheetState = bottomSheetState,
                onDismissRequest = { showSettings = false },
                dragHandle = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            if (showColorPicker) stringResource(id = R.string.pick_color) else stringResource(
                                id = R.string.customise_chart
                            ), style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Divider()
                    }
                }) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                        .height(IntrinsicSize.Max)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (showColorPicker) {
                        ColorPicker(initialColor = chartLineColor, onAccept = {
                            showColorPicker = false
                        }, onChangeColor = {
                            chartLineColor = it
                            intersectionPointColor = it
                            yAxisLineColor = it
                            xAxisLineColor = it

                        }, onChangeLineColor = {
                            chartLineColor = it
                        }, onChangePointColor = {
                            intersectionPointColor = it
                        }, onChangeXAxisColor = {
                            xAxisLineColor = it
                        }, onChangeYAxisColor = {
                            yAxisLineColor = it
                        }, onCancel = {
                            chartLineColor = it
                            intersectionPointColor = it
                            yAxisLineColor = it
                            xAxisLineColor = it
                            showColorPicker = false
                        })

                    } else {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.steps),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier.weight(2f)
                            )

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                IconButton(onClick = { steps++ }) {
                                    Icon(
                                        imageVector = Icons.Default.KeyboardArrowUp,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier
                                            .size(32.dp)
                                            .border(
                                                width = 1.dp,
                                                color = MaterialTheme.colorScheme.outline,
                                                shape = RoundedCornerShape(100)
                                            )
                                    )
                                }
                                Text(text = steps.toString())
                                IconButton(onClick = { steps-- }) {
                                    Icon(
                                        imageVector = Icons.Default.KeyboardArrowDown,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier
                                            .size(32.dp)
                                            .border(
                                                width = 1.dp,
                                                color = MaterialTheme.colorScheme.outline,
                                                shape = RoundedCornerShape(100)
                                            )
                                    )
                                }
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.graph_colors),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier.weight(2f)
                            )

                            Box(modifier = Modifier
                                .size(32.dp)
                                .padding(start = 24.dp, end = 24.dp)
                                .clip(RoundedCornerShape(40))
                                .weight(1f)
                                .background(colorLinearGradient)
                                .border(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    width = 2.dp,
                                    shape = RoundedCornerShape(40)
                                )
                                .clickable {
                                    showColorPicker = true
                                })
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            var isLineMenuExpended by remember { mutableStateOf(false) }

                            Text(
                                text = stringResource(id = R.string.line_type),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier.weight(2f)
                            )


                            Box(
                                modifier = Modifier.weight(1f)
                            ) {
                                ExposedDropdownMenuBox(expanded = isLineMenuExpended,
                                    onExpandedChange = {
                                        isLineMenuExpended = !isLineMenuExpended
                                    }) {
                                    OutlinedTextField(
                                        shape = RoundedCornerShape(20),
                                        value = selectedLineType.text,
                                        onValueChange = {},
                                        readOnly = true,
                                        textStyle = TextStyle.Default.copy(
                                            fontSize = 14.sp, textAlign = TextAlign.Center
                                        ),
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(
                                                expanded = isLineMenuExpended
                                            )
                                        },
                                        modifier = Modifier.menuAnchor()
                                    )

                                    ExposedDropdownMenu(expanded = isLineMenuExpended,
                                        onDismissRequest = { isLineMenuExpended = false }) {
                                        lineTypes.forEach { item ->
                                            DropdownMenuItem(leadingIcon = {
                                                Icon(
                                                    painter = painterResource(id = item.icon),
                                                    contentDescription = null,
                                                    modifier = Modifier.size(24.dp)
                                                )
                                            }, text = {
                                                Text(
                                                    text = item.text, textAlign = TextAlign.Center
                                                )
                                            }, onClick = {
                                                selectedIndex = item.id
                                                isLineMenuExpended = false
                                            })
                                        }
                                    }
                                }
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.dotted_line),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier.weight(2f)
                            )
                            Switch(thumbContent = {
                                Icon(
                                    imageVector = if (isDotted) Icons.Default.Check else Icons.Default.Close,
                                    contentDescription = null
                                )
                            },
                                checked = isDotted,
                                onCheckedChange = { isDotted = it },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.show_grid),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier.weight(2f)
                            )
                            Switch(thumbContent = {
                                Icon(
                                    imageVector = if (showGridLines) Icons.Default.Check else Icons.Default.Close,
                                    contentDescription = null
                                )
                            }, checked = showGridLines, onCheckedChange = {
                                showGridLines = it
                            }, modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}