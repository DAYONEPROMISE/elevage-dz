# ElevageDZ — Research (verified 2026-10-10)

## Problem
Algerian small breeders (sheep/cattle/goat/poultry/camel) track animals, vaccines,
and feed costs on paper. Rural connectivity is unreliable → app must be 100% offline,
no account, French + Arabic (RTL).

## Options considered
| Option | Offline | FR/AR RTL | Cost | Verdict |
| --- | --- | --- | --- | --- |
| Native Kotlin + Compose + Room | Yes (Room local DB, AlarmManager reminders) | Yes (Compose RTL) | Free (MIT libs) | SELECTED |
| Flutter + sqflite | Yes | Yes, extra setup | Free | Rejected: team skill is Kotlin, larger APK |
| PWA / web app | No (needs browser, weak offline) | Yes | Free | Rejected: offline requirement |

## Verified stack (clean-room, MIT/Apache-2.0)
- Kotlin 1.9.25, AGP 8.6.1, compileSdk/targetSdk 34, minSdk 26
- Jetpack Compose BOM 2024.09.02 + Material 3, Room 2.6.1 (KSP), coroutines 1.8.1, JUnit 4.13.2
- Reminders: AlarmManager + NotificationCompat (local only, no server)
- No analytics, no network permission → nothing leaves the device

## Sources (docs consulted during build)
- https://developer.android.com/jetpack/compose
- https://developer.android.com/training/data-storage/room
- https://developer.android.com/develop/ui/compose/layouts/rtl
- https://developer.android.com/reference/android/app/AlarmManager
