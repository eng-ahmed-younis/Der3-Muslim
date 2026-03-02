import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.der3.data.params.ZekrDetailsParams
import com.der3.home.di.factory.ZekrDetailsViewModelFactory
import com.der3.home.presentations.zekr_details.ZekrDetailsViewModel
import com.der3.home.presentations.zekr_details.mvi.ZekrDetailsIntent
import com.der3.home.presentations.zekr_details.mvi.ZekrDetailsState
import com.der3.model.UiText
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import com.der3.ui.R
import com.der3.ui.components.ErrorDialog
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.utils.asString
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Locale


@Composable
fun ZekrDetailsRoute(
    params: ZekrDetailsParams,
    onNavigate: (Screens) -> Unit
) {

    val viewModel: ZekrDetailsViewModel =
        hiltViewModel<ZekrDetailsViewModel, ZekrDetailsViewModelFactory> { factory ->
            factory.create(params)
        }


    val scope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var errorType by remember { mutableStateOf<UiText?>(null) }
    var showErrorDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effects.onEach {
            when (it) {
                is MviEffect.Navigate -> onNavigate(it.screen)
                is MviEffect.OnErrorDialog -> {
                    errorMessage = it.error.asString(context)
                    errorType = it.error
                    showErrorDialog = true
                }
            }
        }.launchIn(scope)
    }

    ErrorDialog(
        visible = showErrorDialog,
        message = errorMessage,
        onRetry = {},
        onDismiss = {}
    )

    ZekrDetailsScreen(
        state = viewModel.viewState,
        onIntent = viewModel::onIntent
    )

}

@Composable
fun ZekrDetailsScreen(
    state: ZekrDetailsState,
    onIntent: (ZekrDetailsIntent) -> Unit,
) {


    val total = state.zekrDetails.repeatCount.coerceAtLeast(1)
    val progress = (state.currentCount.toFloat() / total.toFloat()).coerceIn(0f, 1f)

    val scrollState = rememberScrollState()

    LoadingDialog(visible = state.isLoading)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.gray50)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

        Der3TopAppBar(
            title = stringResource(R.string.zekr_details_title),
            backgroundColor = AppColors.gray50,
            onBackClick = {
                onIntent(ZekrDetailsIntent.Back)
            },
            actionIcon = Icons.Default.MoreVert
        )

        Spacer(Modifier.height(20.dp))


        // Category Badge
        CategoryChip(
            text = stringResource(R.string.zekr_details_quran_verse)

        )

        Spacer(Modifier.height(20.dp))

        // Main Zekr
        Text(
            text = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = AppColors.gray900Text
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "من قالها مائة مرة حُطّت خطاياه وإن كانت مثل زبد البحر.",
            textAlign = TextAlign.Center,
            color = AppColors.gray500,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(Modifier.height(40.dp))

        // Circular Counter
        CircularZekrCounter(
            progress = progress,
            count = state.currentCount,
            total = state.zekrDetails.repeatCount,
            onClick = {
                onIntent(ZekrDetailsIntent.IncrementZekrReadingCount)
            }
        )

        Spacer(Modifier.height(100.dp))

        ProgressCard(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(40.dp))

        ControlPanel(
            modifier = Modifier
                .fillMaxWidth(),
            isPlaying = state.audioState.isPlaying,
            onFavorite = {},
            onPlay = {
                onIntent(
                    ZekrDetailsIntent.ToggleAudio(
                        audioPath = state.zekrDetails.audioPath
                    )
                )
            },
            onReset = {},
            onShare = {},
            onVolume = {}
        )

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ZekrDetailsScreenPreview() {
    // Local state to make the preview interactive
    var count by remember { mutableIntStateOf(33) }
    val total = 100

    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        ZekrDetailsScreen(
            state = ZekrDetailsState(),
            onIntent = {}
        )
    }
}

/**
 * UI click
↓
Intent.ToggleAudio
↓
UseCase
↓
AudioPlayer
↓
ObserveAzkarAudioStateUseCase
↓
Reducer updates state
↓
Compose recomposes
↓
Icon switches Play/Pause automatically*/