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
import java.util.Date
import java.util.Locale

@Composable
fun RequestCard(
    title: String,
    date: Date,
    status: String,
) {
    val dateFormat = SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.US)
    val dateString = dateFormat.parse(date.toString())

    val dateFormatter = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
    val timeFormatter = SimpleDateFormat("HH:mm", Locale.ENGLISH)

    val dateFormatted = dateString?.let { dateFormatter.format(it) }
    val timeFormatted = dateString?.let { timeFormatter.format(it) }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(16.dp).fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Start,
            )
            Row(
                modifier = Modifier.padding(top = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Заявка была создана: ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = "$dateFormatted $timeFormatted",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
            Row(
                modifier = Modifier.padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Статус заявки: ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = status,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }

        }
    }
}

