package com.example.mnrader.navigation

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.mnrader.ui.theme.Green1

@Composable
fun NavigationBar(
    modifier: Modifier = Modifier,
    currentTab: MainTab,
    visible: Boolean = true,
    tabs: List<MainTab> = MainTab.entries,
    onTabSelected: (MainTab) -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideIn { IntOffset(0, it.height) },
        exit = fadeOut() + slideOut { IntOffset(0, it.height) }
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(58.dp)
                .padding(horizontal = 28.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Bottom,
        ) {
            tabs.forEach { tab ->
                tab.text?.let {
                    MainBottomBarItem(
                        iconRes = tab.iconResId,
                        selected = tab == currentTab,
                        text = tab.text,
                        contentDescription = tab.contentDescription,
                        onClick = { onTabSelected(tab) },
                    )
                } ?: run {
                    MainBottomBarAddItem(
                        iconRes = tab.iconResId,
                        selected = tab == currentTab,
                        contentDescription = tab.contentDescription,
                        onClick = { onTabSelected(tab) },
                    )
                }
            }
        }
    }
}

@Composable
private fun RowScope.MainBottomBarAddItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    contentDescription: String,
    @DrawableRes iconRes: Int,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .weight(1f)
            .fillMaxHeight()
            .selectable(
                selected = selected,
                indication = null,
                role = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = contentDescription,
            tint = if (selected) {
                Green1
            } else {
                Color(0xFF8E8E93)
            },
            modifier = Modifier.size(50.dp),
        )
    }
}

@Composable
private fun RowScope.MainBottomBarItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    contentDescription: String,
    @DrawableRes iconRes: Int,
    text: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .weight(1f)
            .fillMaxHeight()
            .selectable(
                selected = selected,
                indication = null,
                role = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = contentDescription,
            tint = if (selected) {
                Green1
            } else {
                Color(0xFF8E8E93)
            },
            modifier = Modifier.size(24.dp),
        )
        Text(
            text = text,
            color = if (selected) {
                Green1
            } else {
                Color(0xFF8E8E93)
            },
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun NavigationBarPreview() {
    NavigationBar(
        currentTab = MainTab.HOME,
        tabs = MainTab.entries,
        onTabSelected = {}
    )
}