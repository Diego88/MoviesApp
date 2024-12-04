package com.dmoyahur.moviesapp.common.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.dmoyahur.moviesapp.common.R
import com.dmoyahur.moviesapp.common.util.TestConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopBar(
    title: String,
    onBack: (() -> Unit)? = null,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                modifier = Modifier.testTag(TestConstants.Common.TOP_BAR_TITLE_TAG)
            )
        },
        navigationIcon = {
            onBack?.let {
                IconButton(
                    onClick = it,
                    modifier = Modifier.testTag(TestConstants.Common.BACK_BUTTON_TAG)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}