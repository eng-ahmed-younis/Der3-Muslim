import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.der3.ui.themes.AppColors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.der3.ui.themes.Der3MuslimTheme


@Composable
fun Der3TopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    backgroundColor: Color,
    titleColor: Color = AppColors.gray900Text,
    navigationIconColor: Color = AppColors.gray900Text,
    actionIconColor: Color = AppColors.gray900Text,
    showBackButton: Boolean = true,
    onBackClick: () -> Unit = {},
    actionIcon: ImageVector? = null,
    onActionClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = 10.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // 🔹 Left (Back Button)
        if (showBackButton) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = navigationIconColor
                )
            }
        } else {
            Spacer(
                modifier = Modifier
                    .size(48.dp)
            )
        }

        // 🔹 Center (Title Row)
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = titleColor
            )
        }

        // 🔹 Right (Action Icon)
        if (actionIcon != null) {
            IconButton(onClick = { onActionClick?.invoke() }) {
                Icon(
                    imageVector = actionIcon,
                    contentDescription = "Action",
                    tint = actionIconColor
                )
            }
        } else {
            Spacer(modifier = Modifier.size(48.dp)) // balance layout
        }
    }
}



@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun Der3TopAppBarPreview() {
    Der3MuslimTheme {
        Der3TopAppBar(
            title = "Home",
            backgroundColor = AppColors.gray50,
            onBackClick = {},
            actionIcon = Icons.Default.Favorite,
            onActionClick = {}
        )
    }
}
