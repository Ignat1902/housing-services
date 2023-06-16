import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.housing_and_communal_services.R

@Composable
fun CounterCard(
    icon: Int,
    number: String,
    value: String,
    data: String
) {
    Card(
        modifier = Modifier.padding(10.dp),
        border = ButtonDefaults.outlinedButtonBorder,
        colors = CardDefaults.outlinedCardColors(),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                Modifier
                    .padding(
                        vertical = 16.dp
                    )
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Icon(
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.primary,
                    painter = painterResource(id = icon),
                    contentDescription = "Icon"
                )


                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Холодная вода",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Номер ИПУ: $number",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }

            OutlinedTextField(

                value = "",
                onValueChange = {
                    it
                },
                label = { Text(text = "Показания") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                placeholder = { Text(text = "null") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(),
            )

            Text(
                text = "Прошлые показания: null",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Дата поверки:  $data",
                style = MaterialTheme.typography.bodySmall
            )

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.error)
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onError,
                    painter = painterResource(id = R.drawable.baseline_assignment_late_24),
                    contentDescription = "Icon"
                )

                Text(
                    text = "Внесите показания до 26 числа текущего месяца",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onError
                )

            }

            Row(
                Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                OutlinedButton(
                    colors = ButtonDefaults.filledTonalButtonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    border = null,
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "История показаний")
                }
                OutlinedButton(
                    colors = ButtonDefaults.filledTonalButtonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    border = null,
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "Внести")
                }
            }

        }
    }
}
