package com.example.runyclub.ui.screens.runninghistory

import android.graphics.BitmapFactory
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.runyclub.database.model.Run
import com.example.runyclub.common.compose.component.RunInfoDialog
import com.example.runyclub.common.compose.component.RunItem
import com.example.runyclub.common.compose.compositonLocal.LocalScaffoldBottomPadding
import com.example.runyclub.R

@Composable
fun RunningHistoryScreen(
    navController: NavController,
    viewModel: RunningHistoryViewModel = hiltViewModel()
) {
    val runItems = viewModel.runList.collectAsLazyPagingItems()


    RunningHistoryScreenContent(
        runItems = runItems,
        onItemClick = viewModel::setDialogRun,
        onNavIconClick = { navController.navigateUp() }
    )

    viewModel.dialogRun.collectAsStateWithLifecycle().value?.let {
        RunInfoDialog(
            run = it,
            onDismiss = { viewModel.setDialogRun(null) },
            onDelete = { viewModel.deleteRun() }
        )
    }
}

@Composable
private fun RunningHistoryScreenContent(
    runItems: LazyPagingItems<Run>,
    onItemClick: (Run) -> Unit,
    onNavIconClick: () -> Unit
) {
    Scaffold(
        topBar = { ScreenTopAppBar(onNavIconClick) }
    ) {
        Box(modifier = Modifier.padding(it)) {
            RunningList(
                runItems = runItems,
                onItemClick = onItemClick
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
private fun ScreenTopAppBar(
    onNavIconClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(text = "Your Activities")
        },
        navigationIcon = {
            IconButton(onClick = onNavIconClick) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_backward),
                    contentDescription = "Navigate back"
                )
            }
        }
    )
}

@Composable
private fun RunningList(
    runItems: LazyPagingItems<Run>,
    onItemClick: (Run) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = LocalScaffoldBottomPadding.current + 8.dp)
    ) {

        if (runItems.loadState.refresh == LoadState.Loading) item {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
        else items(runItems.itemCount) {
            runItems[it]?.let { run ->
                RunCardItem(run = run, onItemClick = onItemClick)
            }
        }
    }
}

@Composable
private fun RunCardItem(
    run: Run,
    onItemClick: (Run) -> Unit = {}
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .clickable { onItemClick(run) }
            .padding(vertical = 4.dp, horizontal = 16.dp)
    ) {
        RunItem(
            run = run,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun RunCardItemPreview() {
    val runList = List(4) {
        Run(
            img = BitmapFactory.decodeResource(
                LocalContext.current.resources,
                R.drawable.running_boy
            )
        )
    }

    RunCardItem(run = runList[0])
}