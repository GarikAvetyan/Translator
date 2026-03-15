# Translator

## Setup
1. Install JDK 17 and Android SDK 35 + Android Studio or `sdkmanager` tools.
2. From the repo root run `./gradlew :app:assembleDebug` to download dependencies, build the modules, and populate the generated resources.
3. Install the APK on a device/emulator with `./gradlew :app:installDebug` or by launching via Android Studio.

## Architecture summary
MVVM + Jetpack Compose + Hilt + Navigation compose wired to a shared data layer (DataStore-backed repository adapters) for coins, learn progress, and translation state.

## What you implemented
- Translator screen with voice input, TTS output, clipboard actions, and a persistent coin counter shown in the top bar + bottom nav.
- Learn Spanish flow with locked module cards (Vocabulary/Grammar/Exam/Listen/Conversation), TTS audio controls, coin-based unlocks, persisted coin balance & unlock state via DataStore, and granular quizzes/listening sections.
- Phrasebook home + category lists with searchable resource-backed titles, audio playback controls, and the same coin balance in the top bar.
- Shared components (top bar, bottom nav, audio button) plus new domain/repo/use-case wiring for coins + learn progress.
- Data layer includes Retrofit + DataStore helpers, local stub content, and persistence for coins/unlocked modules.
- The translator uses the Lecto Translate API with the provided free key in `app/src/main/java/app/translator/data/remote/RetrofitProvider.kt`. Testing may hit rate/usage limits; swap in your own Lecto key there to continue translating after you hit the limits.

## Assumptions / tradeoffs
- Content is seeded via hardcoded local sources for now (no network backend for phrases or lessons).
- Coins are stored locally in DataStore and spent when a locked section is tapped; if a future backend is added, migrate `CoinRepository`/`LearnProgressRepository`.
- TTS relies on the platform engine; playback happens via TextToSpeech and the UI only toggles play/pause (no buffered audio).
