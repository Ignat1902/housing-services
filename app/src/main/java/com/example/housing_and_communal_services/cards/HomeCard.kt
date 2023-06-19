import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.housing_and_communal_services.R
import com.example.housing_and_communal_services.data.models.User

@Composable
fun HomeCard(
    user: User,
) {

    fun getKvParts(user: User): Pair<String?, String?> {
        val kvIndex = user.address?.indexOf(", кв.") ?: -1
        if (kvIndex == -1) return Pair(null, null)
        val kvValue = user.address?.substring(kvIndex + 5)?.trim()
        val addressValue = user.address?.substring(0, kvIndex)?.trim()
        return Pair(addressValue, kvValue)
    }

    val (address, kvValue) = getKvParts(user)

    Card(
        modifier = Modifier.padding(16.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                Modifier
                    .padding(
                        vertical = 16.dp
                    )
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                        .padding(8.dp)

                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        painter = painterResource(id = R.drawable.apartment_48px),
                        contentDescription = "Icon"
                    )

                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Квартира $kvValue",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground
                    )


                    if (address != null) {
                        Text(
                            text = address,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }


                }
            }

            /*Text(
                text = "Баланс",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )
            Text(
                text = balance,
                Modifier.padding(top = 10.dp),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.tertiary,
                textAlign = TextAlign.Center,
            )

            Row(
                Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                OutlinedButton(
                    *//*colors = ButtonDefaults.filledTonalButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        containerColor = MaterialTheme.colorScheme.inverseOnSurface
                    ),*//*
                    colors = ButtonDefaults.filledTonalButtonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    border = null,
                    onClick = { *//*TODO*//* }
                ) {
                    Text(text = "История платежей")
                }
                *//*OutlinedButton(
                    colors = ButtonDefaults.filledTonalButtonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    border = null,
                    onClick = { *//**//*TODO*//**//* }
                ) {
                    Text(text = "Оплатить")
                }*//*
            }*/

        }
    }
}
