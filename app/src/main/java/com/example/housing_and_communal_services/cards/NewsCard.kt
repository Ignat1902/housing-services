import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun NewsCard(
    title: String,
    description: String,
    date: String
) {
    val dateFormat = SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.US)
    val dateString = dateFormat.parse(date)

    val dateFormatter = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
    val timeFormatter = SimpleDateFormat("HH:mm", Locale.ENGLISH)

    val dateFormatted = dateString?.let { dateFormatter.format(it) }
    val timeFormatted = dateString?.let { timeFormatter.format(it) }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {

        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)
            )
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Start,
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline,

                )
            Row(
                Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$dateFormatted $timeFormatted",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}

