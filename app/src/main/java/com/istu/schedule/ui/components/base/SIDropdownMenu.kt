package com.istu.schedule.ui.components.base

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.istu.schedule.ui.theme.ScheduleISTUTheme

@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Composable
fun SIDropdownMenu(
    modifier: Modifier = Modifier,
    textValue: String = "",
    placeholder: String,
    listItems: List<Pair<Int, String>>,
    selectedItems: List<Pair<Int, String>>,
    onTextValueChange: (String) -> Unit = {},
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .border(1.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(4.dp))
            .padding(vertical = 10.dp, horizontal = 10.dp),
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            FlowRow(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
            ) {
                selectedItems.forEach {
                    SIInputChip(modifier = Modifier.padding(end = 3.dp), text = it.second)
                }
                BasicTextField(
                    modifier = Modifier.weight(1f).padding(vertical = 3.dp),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    value = textValue,
                    onValueChange = {
                        onTextValueChange(it)
                    },
                    singleLine = true,
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    decorationBox = { innerTextField ->
                        if (textValue.isEmpty()) {
                            Text(
                                modifier = Modifier.alpha(0.7f),
                                text = placeholder,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                        innerTextField()
                    },
                )
            }
            TrailingIcon(expanded = false)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSIDropdownMenu() {
    ScheduleISTUTheme {
        SIDropdownMenu(
            modifier = Modifier.padding(10.dp),
            placeholder = "Select speciality",
            listItems = listOf(Pair(1, "Android"), Pair(2, "JavaScript"), Pair(3, "Kotlin"), Pair(4, "C#")),
            selectedItems = listOf(Pair(1, "Android"), Pair(2, "JavaScript")),
        )
    }
}
