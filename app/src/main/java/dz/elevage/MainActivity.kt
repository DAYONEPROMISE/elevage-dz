package dz.elevage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.room.Room
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(applicationContext, AppDb::class.java, "elevagedz.db").build()
        val appCtx = applicationContext
        setContent { App(db, appCtx) }
    }
}

@Composable
fun App(db: AppDb, appCtx: android.content.Context) {
    var lang by remember { mutableStateOf("fr") }
    val s = if (lang == "ar") Strings.ar else Strings.fr
    CompositionLocalProvider(LocalLayoutDirection provides if (lang == "ar") LayoutDirection.Rtl else LayoutDirection.Ltr) {
        MaterialTheme {
            var tab by remember { mutableStateOf(0) }
            val scope = rememberCoroutineScope()
            var animals by remember { mutableStateOf(listOf<Animal>()) }
            var events by remember { mutableStateOf(listOf<HealthEvent>()) }
            var feeds by remember { mutableStateOf(listOf<FeedCost>()) }
            fun refresh() = scope.launch {
                animals = db.animals().all()
                events = db.health().all()
                feeds = db.feed().all()
            }
            LaunchedEffect(Unit) { refresh() }
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        listOf(s.animals, s.health, s.feed, s.settings).forEachIndexed { i, t ->
                            NavigationBarItem(selected = tab == i, onClick = { tab = i }, label = { Text(t) }, icon = {})
                        }
                    }
                }
            ) { pad ->
                Box(Modifier.padding(pad).padding(12.dp)) {
                    when (tab) {
                        0 -> AnimalsTab(s, animals, onAdd = { tag, sp -> scope.launch { db.animals().insert(Animal(tag = tag, species = sp)); refresh() } })
                        1 -> HealthTab(s, events, onAdd = { tag, prod, days ->
                            scope.launch {
                                val today = LocalDate.now().toEpochDay()
                                val remind = today + days
                                db.health().insert(HealthEvent(animalTag = tag, product = prod, eventEpochDay = today, remindEpochDay = remind))
                                val trigger = LocalDate.ofEpochDay(remind).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
                                Reminder.schedule(appCtx, "$tag • $prod", trigger)
                                refresh()
                            }
                        })
                        2 -> FeedTab(s, feeds, onAdd = { label, qty, price ->
                            scope.launch {
                                val day = LocalDate.now().toEpochDay()
                                db.feed().insert(FeedCost(label = label, qtyKg = qty, priceDzdPerKg = price, dayEpoch = day))
                                refresh()
                            }
                        })
                        else -> SettingsTab(s, lang, onLang = { lang = it })
                    }
                }
            }
        }
    }
}

@Composable
fun AnimalsTab(s: Strings.S, animals: List<Animal>, onAdd: (String, String) -> Unit) {
    var tag by remember { mutableStateOf("") }
    var sp by remember { mutableStateOf("sheep") }
    Column {
        Text(s.addAnimal, style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(tag, { tag = it }, label = { Text(s.tag) }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(sp, { sp = it }, label = { Text(s.species) }, modifier = Modifier.fillMaxWidth())
        Button(onClick = { if (Validation.animalTagValid(tag)) { onAdd(tag.trim(), sp.trim()); tag = "" } }, modifier = Modifier.padding(top = 8.dp)) { Text(s.add) }
        LazyColumn { items(animals) { a -> Text("${a.tag} • ${a.species}", modifier = Modifier.padding(vertical = 4.dp)) } }
    }
}

@Composable
fun HealthTab(s: Strings.S, events: List<HealthEvent>, onAdd: (String, String, Long) -> Unit) {
    var tag by remember { mutableStateOf("") }
    var prod by remember { mutableStateOf("") }
    var days by remember { mutableStateOf("30") }
    val today = LocalDate.now().toEpochDay()
    Column {
        Text(s.addVaccine, style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(tag, { tag = it }, label = { Text(s.tag) }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(prod, { prod = it }, label = { Text(s.vaccine) }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(days, { days = it }, label = { Text(s.dueInDays) }, modifier = Modifier.fillMaxWidth())
        Button(onClick = { onAdd(tag.trim(), prod.trim(), days.toLongOrNull() ?: 30); tag = ""; prod = "" }, modifier = Modifier.padding(top = 8.dp)) { Text(s.add) }
        LazyColumn {
            items(events) { e ->
                val d = if (e.remindEpochDay != null) ReminderMath.daysUntil(today, e.remindEpochDay) else null
                Text("${e.animalTag} • ${e.product}" + if (d != null) " • J$d" else "", modifier = Modifier.padding(vertical = 4.dp))
            }
        }
    }
}

@Composable
fun FeedTab(s: Strings.S, feeds: List<FeedCost>, onAdd: (String, Double, Double) -> Unit) {
    var label by remember { mutableStateOf("") }
    var qty by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    val total = FeedMath.totalCost(feeds.map { it.qtyKg to it.priceDzdPerKg })
    Column {
        Text(s.addFeed, style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(label, { label = it }, label = { Text(s.feed) }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(qty, { qty = it }, label = { Text(s.qtyKg) }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(price, { price = it }, label = { Text(s.priceDzd) }, modifier = Modifier.fillMaxWidth())
        Button(onClick = {
            val q = qty.toDoubleOrNull() ?: 0.0; val p = price.toDoubleOrNull() ?: 0.0
            if (Validation.quantityValid(q) && Validation.priceValid(p)) { onAdd(label.trim(), q, p); label = ""; qty = ""; price = "" }
        }, modifier = Modifier.padding(top = 8.dp)) { Text(s.add) }
        Text("${s.total}: ${FeedMath.formatDzd(total)}", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 8.dp))
        LazyColumn { items(feeds) { f -> Text("${f.label} • ${f.qtyKg}kg × ${f.priceDzdPerKg} = ${FeedMath.formatDzd(f.qtyKg * f.priceDzdPerKg)}", modifier = Modifier.padding(vertical = 4.dp)) } }
    }
}

@Composable
fun SettingsTab(s: Strings.S, lang: String, onLang: (String) -> Unit) {
    Column {
        Text(s.language, style = MaterialTheme.typography.titleMedium)
        Row {
            Button(onClick = { onLang("fr") }, enabled = lang != "fr") { Text("Français") }
            Spacer(Modifier.width(8.dp))
            Button(onClick = { onLang("ar") }, enabled = lang != "ar") { Text("العربية") }
        }
        Text(s.offlineNote, modifier = Modifier.padding(top = 12.dp))
    }
}
