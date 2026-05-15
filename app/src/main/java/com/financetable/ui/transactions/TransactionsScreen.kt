package com.financetable.ui.transactions

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.financetable.data.Transaction
import com.financetable.vm.TransactionViewModel
import java.text.NumberFormat
import java.util.*
import java.util.Date

@Composable
fun TransactionsScreen(viewModel: TransactionViewModel = hiltViewModel()) {
    val transactions by viewModel.transactions.collectAsState()
    val context = LocalContext.current

    var desc by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var isIncome by remember { mutableStateOf(true) }
    var dateMillis by remember { mutableStateOf(System.currentTimeMillis()) }
    var location by remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()

    Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
        // Input row
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f)) {
                TextField(value = desc, onValueChange = { desc = it }, label = { Text("Deskripsi") })
                Spacer(modifier = Modifier.height(8.dp))
                TextField(value = amount, onValueChange = { amount = it.filter { ch -> ch.isDigit() } }, label = { Text("Nominal") }, keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions(keyboardType = KeyboardType.Number))
                Spacer(modifier = Modifier.height(8.dp))
                TextField(value = location, onValueChange = { location = it }, label = { Text("Lokasi (autocomplete placeholder)") })
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = {
                    val y = calendar.get(Calendar.YEAR)
                    val m = calendar.get(Calendar.MONTH)
                    val d = calendar.get(Calendar.DAY_OF_MONTH)
                    DatePickerDialog(context, { _: DatePicker, yy, mm, dd ->
                        val cal = Calendar.getInstance(); cal.set(yy, mm, dd); dateMillis = cal.timeInMillis
                    }, y, m, d).show()
                }) { Text("Pilih Tanggal") }

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = {
                    val cents = (amount.toLongOrNull() ?: 0L)
                    viewModel.addTransaction(desc, cents, isIncome, dateMillis, location.ifBlank { null })
                    desc = ""; amount = ""; location = ""
                }) { Text("Tambah") }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Header
        Row(modifier = Modifier.fillMaxWidth().background(Color.LightGray).padding(8.dp)) {
            Text("Tanggal", modifier = Modifier.weight(0.25f))
            Text("Deskripsi", modifier = Modifier.weight(0.4f))
            Text("Nominal", modifier = Modifier.weight(0.2f))
            Text("Lokasi", modifier = Modifier.weight(0.15f))
        }

        Spacer(modifier = Modifier.height(4.dp))

        // List
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(transactions) { tx ->
                TransactionRow(tx)
            }
        }
    }
}

@Composable
private fun TransactionRow(tx: Transaction) {
    val fmt = NumberFormat.getIntegerInstance(Locale.getDefault())
    val amountText = fmt.format(tx.amount)
    val color = if (tx.type) Color(0xFF2E7D32) else Color(0xFFC62828)

    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(Date(tx.timestamp).toString(), modifier = Modifier.weight(0.25f))
        Text(tx.description, modifier = Modifier.weight(0.4f))
        Text(amountText, modifier = Modifier.weight(0.2f), color = color)
        Text(tx.locationName ?: "-", modifier = Modifier.weight(0.15f))
    }
}
