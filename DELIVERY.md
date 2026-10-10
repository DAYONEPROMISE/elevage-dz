# ElevageDZ — Delivery notes

## Test table
| # | Area | Check | Status |
| --- | --- | --- | --- |
| 1 | Feed math | `LogicTest.feedTotal`: 10kg×200 + 5kg×500 = 4500 DZD | Unit test (see CI/local run) |
| 2 | Feed math empty | `feedTotalEmpty` = 0 | Unit test |
| 3 | Reminders | `daysUntil`/`isOverdue` arithmetic | Unit test |
| 4 | Validation | tag/species/qty/price bounds | Unit test |
| 5 | FR/AR strings | both tables non-blank | Unit test |
| 6 | Lint | `:app:lintDebug` | CI job |
| 7 | Assemble | `:app:assembleDebug` APK | CI job |
| 8 | Manual | add animal → list; add vaccine → reminder fires; add feed → total; switch FR/AR + RTL | To run on device with APK |

## Limitations
- Debug APK only (no Play signing yet); minSdk 26 (Android 8+) excludes older devices.
- Reminders use inexact fallback if exact-alarm permission denied; reboot clears
  scheduled alarms (no BOOT_COMPLETED receiver yet).
- Single-device Room DB: no export/backup/sync by design (privacy), CSV export planned.
- Reminder time fixed at 09:00 local on due day (day granularity, no custom hour).
- Species list fixed to 5; no photo support yet.

## Costs
- Build/run: 0 DZD (MIT/Apache libs, GitHub Actions free tier, no backend).
- Optional later: Play Console one-time $25; no server costs while offline-only.

## First-10-customers plan (Algeria)
1. 2 sheep breeders (Sétif, Djelfa) via veterinary contacts — free pilot, weekly call.
2. 2 poultry farmers (Alger, Oran) via feed-store posters with QR to APK.
3. 1 cattle cooperative (Constantine) — group demo at souk day.
4. 2 goat/camel breeders (south: Ghardaïa, Adrar) via WhatsApp groups.
5. 1 vet clinic (Blida) as referrer — prints reminder cards.
6. 2 agri-school students (ITMA) as champions — feedback + word of mouth.
- Ask each: install APK, use 2 weeks, report bugs on WhatsApp; convert 5+ to
  testimonials before any Play release.
