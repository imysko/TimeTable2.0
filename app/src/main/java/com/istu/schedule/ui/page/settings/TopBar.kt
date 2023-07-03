package com.istu.schedule.ui.page.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.istu.schedule.R
import com.istu.schedule.ui.icons.Back
import com.istu.schedule.ui.theme.AppTheme
import com.istu.schedule.ui.theme.Shape5

@Composable
fun TopBar(
    title: String,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(Shape5)
                    .clickable { onBackClick() }
            ) {
                Icon(
                    modifier = Modifier.size(22.dp),
                    imageVector = Icons.Back,
                    contentDescription = stringResource(id = R.string.back),
                    tint = AppTheme.colorScheme.textPrimary
                )
            }

            Text(
                text = title,
                style = AppTheme.typography.subtitle,
                color = AppTheme.colorScheme.textPrimary
            )
        }
    }
}
