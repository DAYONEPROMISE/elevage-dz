# OpenPencil pass — elevage-dz

## Design (Shared/OpenPencil/elevage-dz/)
- `design.html` (reminder card + herd summary, FR/AR, DZ tokens) → `design.fig`
- `lint --preset accessibility`: 0 errors, 0 warnings

## Code review (no rewrite needed)
Nav icons already described. No code change.

## APKs
`ci.yml` adds `assembleRelease` (unsigned) + `app-release` artifact + a versioned
`release-v*-b*` GitHub Release for the release APK (debug flow unchanged).
