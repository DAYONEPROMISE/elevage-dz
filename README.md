# ElevageDZ — offline livestock tracker for Algerian breeders (MIT)

Offline-first Android app (Kotlin, Jetpack Compose Material 3, Room). French/Arabic RTL. No account, no network.

## Features
- Animal/batch register (sheep, cattle, goat, poultry, camel)
- Vaccination & treatment log with local-alarm reminders (AlarmManager + notification, works offline)
- Feed cost tracker in DZD with totals
- FR/AR in-app switch, RTL layout, `supportsRtl=true`

## Research (verified)
- Feed = 60–70% of production cost (Zembri 2026, Kabylia dairy-goat study; Godber et al. 2020).
- Bran crisis Jan 2026: official 1,800–2,100 DA/quintal vs market 4,500–6,000 DA in Mascara (Algeria Invest 11 Jan 2026); ministerial circular 16 Dec 2025 quotas via ONAB.
- State support: 12 DA/L breeder + 5 DA/L collector + 4 DA/L processor milk subsidies; 60,000 DA per dairy-cow birth; free FMD vaccination (USDA Dairy Annual 2022).
- Feed grain: barley mainly for sheep/cattle/camels; demand spikes when pastures fail (USDA Grain & Feed Annual 2024/2025).

## Build
```
./gradlew :app:testDebugUnitTest :app:lintDebug :app:assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

## Limitations
- Reminders need exact-alarm/notification permission; no cloud sync by design.
- No vet diagnosis; manual entry only.
- Room v1, no auto-migration yet.

## Cost
- App: free, offline. Cloud build: GitHub Actions ubuntu-latest free tier (~4–6 min).

## First 10 customers
1–3: sheep breeders (Mascara/Sétif souks), 4–5: Kabylia goat farms, 6–7: poultry batches (Alger/Blida), 8–10: cattle milk collectors. Channel: souk vets + ONAB distributors; demo in FR/AR, install APK via USB.
