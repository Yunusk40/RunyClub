package com.example.runyclub.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.runyclub.R
import com.example.runyclub.database.model.Run
import com.example.runyclub.database.model.User
import com.example.runyclub.common.compose.component.RunInfoDialog
import com.example.runyclub.common.compose.component.RunItem
import com.example.runyclub.common.compose.component.UserProfilePic
import com.example.runyclub.common.compose.compositonLocal.LocalScaffoldBottomPadding
import com.example.runyclub.navigation.BottomNavDestination
import com.example.runyclub.navigation.Destination
import kotlin.math.roundToInt

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    val doesUserExist by viewModel.doesUserExist.collectAsStateWithLifecycle()
    val state by viewModel.homeScreenState.collectAsStateWithLifecycle()
    val durationInMillis by viewModel.durationInMillis.collectAsStateWithLifecycle()

    if (doesUserExist == true)
        HomeScreenContent(
            state = state,
            durationInMillis = durationInMillis,
            deleteRun = viewModel::deleteRun,
            showRun = viewModel::showRun,
            dismissDialog = viewModel::dismissRunDialog,
            navigateToRunScreen = { Destination.navigateToCurrentRunScreen(navController) },
            navigateToRunningHistoryScreen = {
                BottomNavDestination.Home.RecentRun.navigateToRunningHistoryScreen(navController)
            },
            navigateToRunStats = {
                BottomNavDestination.Home.navigateToRunStats(navController)
            }
        )

    LaunchedEffect(key1 = doesUserExist) {
        if (doesUserExist == false)
            BottomNavDestination.Home
                .navigateToOnBoardingScreen(navController)
    }
}

@Composable
fun HomeScreenContent(
    state: HomeScreenState,
    durationInMillis: Long,
    deleteRun: (Run) -> Unit,
    showRun: (Run) -> Unit,
    dismissDialog: () -> Unit,
    navigateToRunScreen: () -> Unit,
    navigateToRunningHistoryScreen: () -> Unit,
    navigateToRunStats: () -> Unit,
) {
    Column {
        TopBar(
            modifier = Modifier
                .zIndex(1f),
            user = state.user,
            weeklyGoalInKm = state.user.weeklyGoalInKM,
            distanceCoveredInCurrentWeekInKm = state.distanceCoveredInKmInThisWeek,
            onWeeklyGoalClick = navigateToRunStats // This can be removed if not used elsewhere
        )
        // Add Statistics Button Here
        Button(
            onClick = navigateToRunStats,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text("View Statistics")
        }
        // Rest of the content remains the same
        Row(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(vertical = 28.dp, horizontal = 24.dp)
        ) {
            Text(
                text = "Recent Activity",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier
                    .weight(1f)
            )
            Text(
                text = "All Activities",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .clickable(onClick = navigateToRunningHistoryScreen, role = Role.Button)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(bottom = LocalScaffoldBottomPadding.current)
        ) {
            RecentRunList(
                runList = state.runList,
                onItemClick = showRun
            )
        }
    }
    state.currentRunInfo?.let {
        RunInfoDialog(
            run = it,
            onDismiss = dismissDialog,
            onDelete = deleteRun
        )
    }
}

@Composable
private fun RecentRunList(
    modifier: Modifier = Modifier,
    runList: List<Run>,
    onItemClick: (Run) -> Unit,
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .padding(horizontal = 24.dp)
            .padding(bottom = 8.dp)
            .wrapContentHeight()
    ) {
        Column {
            runList.forEachIndexed { i, run ->
                Column(
                    modifier = Modifier
                ) {
                    RunItem(
                        run = run,
                        modifier = Modifier
                            .clickable { onItemClick(run) }
                            .padding(16.dp)
                    )
                    if (i < runList.lastIndex)
                        Box(
                            modifier = Modifier
                                .height(1.dp)
                                .width(200.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                        alpha = 0.2f
                                    )
                                )
                                .align(Alignment.CenterHorizontally)
                        )
                }
            }
        }
    }

}

@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
    user: User = User(),
    weeklyGoalInKm: Float = 0f,
    onWeeklyGoalClick: () -> Unit,
    distanceCoveredInCurrentWeekInKm: Float = 0f
) {
    Box(
        modifier = modifier
            .height(IntrinsicSize.Min)
    ) {
        Column(modifier = modifier.padding(horizontal = 24.dp)) {
            Spacer(modifier = Modifier.size(24.dp))
            TopBarProfile(
                modifier = Modifier.background(color = Color.Transparent),
                user = user
            )
        }
    }

}

@Composable
private fun TopBarProfile(
    modifier: Modifier = Modifier,
    user: User
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = buildAnnotatedString {
                append("Welcome, ")
                withStyle(
                    style = SpanStyle(fontWeight = FontWeight.Light),
                ) {
                    append(user.name)
                }
            },
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            modifier = Modifier
                .weight(1f)
        )
    }
}