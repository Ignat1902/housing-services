import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.housing_and_communal_services.view_models.ServicesViewModel

@Composable
fun ServiceCard(
    title: String,
    description: String,
    coast: String,
    servicesViewModel: ServicesViewModel
) {
    val showAlertDialog = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Column {
            /*Image(
                painter = painterResource(id = image),
                contentDescription = "Sample Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
            )*/
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
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
                    style = MaterialTheme.typography.bodySmall,
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
                        text = "От $coast рублей",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    OutlinedButton(
                        colors = ButtonDefaults.filledTonalButtonColors(
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        border = null,
                        onClick = {
                            servicesViewModel.checkRequest(title)
                            val service = servicesViewModel.checkRequest.value
                            Log.d("IGNAT", "checkRequest = ${service}")
                            if (service == true) {
                                showAlertDialog.value = true
                            } else {
                                servicesViewModel.createRequest(title, context)
                            }
                        }
                    ) {
                        Text(text = "Оставить заявку")
                    }
                }
            }
        }
    }
    if (showAlertDialog.value) {
        AlertDialog(
            onDismissRequest = { showAlertDialog.value = false },
            title = { Text(text = "Внимание") },
            text = { Text(text = "Вы уже оставляли заявку на данную услугу раннее") },
            confirmButton = {
                OutlinedButton(
                    onClick = { showAlertDialog.value = false }
                ) {
                    Text(text = "ОК")
                }
            }
        )
    }
}
