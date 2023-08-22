package com.ps.graphplayground.presentation.model

import co.yml.charts.ui.linechart.model.LineType

data class LineTypeMenuItem(
    val id: Int,
    val text: String,
    val type: LineType,
    val icon: Int
)
