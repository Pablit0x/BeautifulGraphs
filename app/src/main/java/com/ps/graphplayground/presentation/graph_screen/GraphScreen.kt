package com.ps.graphplayground.presentation.graph_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.models.BarChartData
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

@Composable
fun GraphScreen() {


    var steps by remember { mutableStateOf(5) }
    var dataPoints by remember {
        mutableStateOf(
            listOf(
                Point(x = 0f, y = 40f),
                Point(x = 1f, y = 20f),
                Point(x = 2f, y = 0f),
                Point(x = 3f, y = 20f),
                Point(x = 4f, y = 40f),
                Point(x = 5f, y = 20f)
            )
        )
    }
    var xAxisStepSize by remember { mutableStateOf(100.dp) }
    var xAxisBackgroundColor by remember { mutableStateOf(Color.Transparent) }

    var yAxisStepSize by remember { mutableStateOf(300.dp) }
    var yAxisBackgroundColor by remember { mutableStateOf(Color.Transparent) }

    var xAxisLabelAndAxisLinePadding by remember { mutableStateOf(15.dp) }
    var yAxisLabelAndAxisLinePadding by remember { mutableStateOf(20.dp) }

    var xAxisLabelFontSize by remember { mutableStateOf(12.sp) }
    var yAxisLabelFontSize by remember { mutableStateOf(12.sp) }

    var lineType by remember { mutableStateOf(LineType.SmoothCurve(isDotted = false)) }
    var gridLines : GridLines? by remember { mutableStateOf(null) }


    val xAxisData =
        AxisData.Builder().axisStepSize(xAxisStepSize).backgroundColor(xAxisBackgroundColor)
            .steps(dataPoints.size - 1).labelData { value -> value.toString() }
            .labelAndAxisLinePadding(xAxisLabelAndAxisLinePadding)
            .axisLineColor(MaterialTheme.colorScheme.primary)
            .axisLabelColor(MaterialTheme.colorScheme.primary).axisLabelFontSize(xAxisLabelFontSize)
            .build()

    val yAxisData =
        AxisData.Builder().axisStepSize(yAxisStepSize).backgroundColor(yAxisBackgroundColor)
            .steps(steps).labelData { value ->
                val yScale = 100 / steps
                (value * yScale).toString()
            }.labelAndAxisLinePadding(yAxisLabelAndAxisLinePadding)
            .axisLineColor(MaterialTheme.colorScheme.primary)
            .axisLabelColor(MaterialTheme.colorScheme.primary).axisLabelFontSize(yAxisLabelFontSize)
            .build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = dataPoints,
                    LineStyle(
                        color = MaterialTheme.colorScheme.primary, lineType = lineType
                    ),
                    intersectionPoint = IntersectionPoint(MaterialTheme.colorScheme.primary),
                    selectionHighlightPoint = SelectionHighlightPoint(MaterialTheme.colorScheme.tertiary),
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
        gridLines = gridLines
    )

    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LineChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp), lineChartData = lineChartData
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(modifier = Modifier.padding(16.dp),
            value = "Steps $steps",
            onValueChange = {},
            enabled = false,
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
            trailingIcon = {
                IconButton(onClick = { steps++ }) {
                    Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = null)
                }
            },
            leadingIcon = {
                IconButton(onClick = { steps-- }) {
                    Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
                }
            })

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = "isDotted")
            Switch(checked = lineType.isDotted,
                onCheckedChange = { lineType = LineType.SmoothCurve(isDotted = it) })
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = "gridLines")
            Switch(checked = gridLines == null,
                onCheckedChange = { gridLines = if(it) null else GridLines(Color.Gray) })
        }

    }

}