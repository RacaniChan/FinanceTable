# FinanceTable

Minimal Android (Kotlin + Jetpack Compose) scaffold implementing the PRD in `docs/prd.md`.

Structure:
- app/: Android application module
  - src/main/java/com/financetable/: Kotlin sources (Activity, DI, data, ui, vm)
  - src/main/res/: minimal resources

How to build (Android Studio recommended):

1. Open this folder in Android Studio.
2. Let Gradle sync and install required SDKs (compileSdk 34).
3. Run the app on an emulator or device.

Notes:
- Room stores amounts as cents (`Long`) to preserve precision. Use BigDecimal in business logic if needed.
- Location autocomplete and Maps integration are left as placeholders (requires API keys).
# FinanceTable
