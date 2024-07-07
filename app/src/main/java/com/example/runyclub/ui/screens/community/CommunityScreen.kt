import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import okhttp3.Challenge

@Composable
fun CommunityScreen() {
    val challenges = listOf(
        Challenge("5K Beginner's Run", "A perfect start for beginners to hit a 5K."),
        Challenge("10K Challenge", "Step up your game and conquer the 10K distance."),
        Challenge("Half Marathon", "Ready for more? Aim for a half marathon."),
        Challenge("Marathon", "The ultimate challenge: Complete a full marathon.")
    )

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(challenges) { challenge ->
            ChallengeItem(challenge)
        }
    }
}

@Composable
fun ChallengeItem(challenge: Challenge) {
    TODO("Not yet implemented")
}


