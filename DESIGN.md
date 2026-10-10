# ElevageDZ — UI/UX 2026 pass

No shared Penpot direction was found in `Shared/` at implementation time, so this
pass applies the Material3 2026 baseline directly in Compose (zero new deps):

- Dynamic color (Android 12+), pasture-green seed fallback below API 31 (`Theme.kt`)
- Dark mode: Système / Clair / Sombre toggle in Settings (`ThemeMode`)
- Edge-to-edge (`enableEdgeToEdge()` + Scaffold insets)
- TopAppBar per tab + NavigationBar with icons and content descriptions
- Adaptive content: max 640dp centered column on tablets/foldables
- FR/AR RTL via `LocalLayoutDirection`; theme strings in both tables
- Accessibility: icon content descriptions, semantic label on offline note

Penpot: screens were not mocked in Penpot (no shared workspace credentials);
layout tokens (seed colors, 640dp breakpoint, nav structure) are documented here
so the design lead can mirror them into Penpot libraries later.
