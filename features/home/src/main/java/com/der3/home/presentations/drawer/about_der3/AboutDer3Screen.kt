package com.der3.home.presentations.drawer.about_der3

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.der3.home.presentations.drawer.about_der3.components.AboutActionCard
import com.der3.home.presentations.drawer.about_der3.mvi.AboutDer3Intent
import com.der3.home.presentations.drawer.about_der3.mvi.AboutDer3State
import com.der3.model.UiText
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import com.der3.ui.R
import com.der3.ui.components.Der3TopAppBar
import com.der3.ui.components.ErrorDialog
import com.der3.ui.components.LoadingDialog
import com.der3.ui.style.ShiftSystemBarStyle
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.utils.asString
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@Composable
fun AboutDer3Route(
    onNavigate: (Screens) -> Unit = {}
) {
    val viewModel = hiltViewModel<AboutDer3ViewModel>()
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
                else -> {}
            }
        }.launchIn(this)
    }

    LoadingDialog(visible = viewModel.viewState.isLoading)

    ErrorDialog(
        visible = showErrorDialog,
        message = errorMessage,
        onRetry = {
            showErrorDialog = false
            viewModel.onIntent(AboutDer3Intent.Retry)
        },
        onDismiss = {
            showErrorDialog = false
            viewModel.onIntent(AboutDer3Intent.DismissError)
        }
    )

    ShiftSystemBarStyle(
        statusBarColor = AppColors.green800,
        isStatusBarVisible = true,
        useDarkStatusBarIcons = false,
        isEdgeToEdgeEnabled = true,
        isNavigationBarVisible = false,
        navigationBarColor = AppColors.gray50,
        useDarkNavigationBarIcons = false
    )

    AboutDer3Screen(
        state = viewModel.viewState,
        onIntent = viewModel::onIntent
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutDer3Screen(
    state: AboutDer3State,
    onIntent: (AboutDer3Intent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.green25)
    ) {
        Der3TopAppBar(
            title = stringResource(id = R.string.about_title),
            backgroundColor = AppColors.green800,
            titleColor = Color.White,
            navigationIconColor = Color.White,
            onBackClick = {
                onIntent(AboutDer3Intent.Back)
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .background(
                    color = AppColors.green25
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // App Logo Box
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(AppColors.green800),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.splash_logo),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp),
                    tint = AppColors.gold500
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.der3_muslim_title),
                style = MaterialTheme.typography.headlineSmall,
                color = AppColors.green800,
                fontWeight = FontWeight.Bold
            )

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = AppColors.gold500.copy(alpha = 0.1f),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    text = "${stringResource(id = R.string.version_title)} ${state.version}",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppColors.gold600
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.about_description),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = AppColors.gray500,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Action Items
            AboutActionCard (
                title = stringResource(id = R.string.rate_title),
                subtitle = stringResource(id = R.string.rate_subtitle),
                icon = Icons.Default.Star,
                onClick = { onIntent(AboutDer3Intent.RateApp) }
            )

            AboutActionCard(
                title = stringResource(id = R.string.share_title),
                subtitle = stringResource(id = R.string.share_subtitle),
                icon = Icons.Default.Share,
                onClick = { onIntent(AboutDer3Intent.ShareApp) }
            )

            AboutActionCard(
                title = stringResource(id = R.string.contact_title),
                subtitle = stringResource(id = R.string.contact_subtitle),
                icon = Icons.Default.Email,
                onClick = { onIntent(AboutDer3Intent.ContactUs) }
            )

            AboutActionCard(
                title = stringResource(id = R.string.website_title),
                subtitle = stringResource(id = R.string.website_subtitle),
                icon = Icons.Default.Language,
                onClick = { onIntent(AboutDer3Intent.VisitWebsite) }
            )

            AboutActionCard(
                title = stringResource(id = R.string.privacy_title),
                subtitle = stringResource(id = R.string.privacy_subtitle),
                icon = Icons.Default.PrivacyTip,
                onClick = { onIntent(AboutDer3Intent.PrivacyPolicy) }
            )

            Spacer(modifier = Modifier.height(32.dp))

            HorizontalDivider(modifier = Modifier.padding(horizontal = 32.dp), color = AppColors.gray200)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.follow_us),
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.gray400
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SocialIcon(icon = Icons.Default.Language) { onIntent(AboutDer3Intent.OpenSocialMedia) }
                SocialIcon(icon = Icons.Default.CameraAlt) { onIntent(AboutDer3Intent.OpenSocialMedia) }
                SocialIcon(icon = Icons.Default.AlternateEmail) { onIntent(AboutDer3Intent.OpenSocialMedia) }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(id = R.string.made_with_love),
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppColors.green800,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.size(16.dp)
                )
            }

            Text(
                text = stringResource(id = R.string.all_rights_reserved),
                style = MaterialTheme.typography.labelSmall,
                color = AppColors.gray400,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}



@Composable
fun SocialIcon(icon: ImageVector, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = CircleShape,
        color = Color.White,
        modifier = Modifier.size(44.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.gray100)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = AppColors.gold500,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview(showBackground = true, locale = "ar")
@Composable
fun AboutDer3ScreenPreview() {
    Der3MuslimTheme (

    ){
        AboutDer3Screen(
            state = AboutDer3State(),
            onIntent = {}
        )
    }
}
