# Der3 Muslim (درع المسلم)
**Der3 Muslim** is a modern, modular Android application designed to be a comprehensive spiritual companion for Muslims. It provides a rich set of features including daily Azkar, prayer notifications, a digital Masbaha, and a customizable user experience, all built with the latest Android development practices.
---
## :iphone: Features
- **:book: Azkar & Supplications:**
    - Extensive collection of daily Azkar (Morning, Evening, Sleep, etc.).
    - Categorized for quick access with detailed views for each Zekr.
    - Search functionality to find specific supplications.
- **:bell: Smart Notifications:**
    - Timely reminders for daily Azkar.
    - Custom reminders for religious occasions and specific user-defined times.
    - Integration with Firebase Cloud Messaging (FCM) for dynamic updates.
- **:prayer_beads: Digital Masbaha (Tasbih):**
    - Interactive counter with haptic feedback.
    - History tracking to monitor your daily progress.
    - Customizable Azkar for the counter.
- **:wastebasket: Recycle Bin:**
    - Safety feature for deleted custom Azkar or notifications, allowing for recovery.
- **:star: Favorites:**
    - Quick access to your most-used supplications.
- **:art: Modern UI/UX:**
    - Fully built with Jetpack Compose.
    - Supports Dark/Light themes and dynamic styling.
---
## :building_construction: Architecture & Project Structure
The project follows **Clean Architecture** principles and uses a **Modular** approach to ensure scalability, testability, and separation of concerns.
### Project Modules:
- **`:app`**: The main entry point. Handles app initialization, theme configuration, and top-level navigation.
- **`:features`**: Contains feature-specific logic and UI.
    - `:features:home`: The core functional module containing the main dashboard, Azkar categories, and notifications.
    - `:features:on_boarding`: Manages the initial user setup and introduction.
- **`:core`**: Shared infrastructure and logic.
    - `:core:shared`: The domain and data layer. Contains models, repositories (Room/Ktor), and Use Cases.
    - `:core:mvi`: Base framework for the Model-View-Intent architecture.
    - `:core:ui`: Common UI components, themes, and design system.
    - `:core:ui-model`: Platform-agnostic UI models.
    - `:core:data_store`: Local settings management using Jetpack DataStore.
    - `:core:player`: Logic for playing audio recitations of Azkar.
    - `:core:utils`: General-purpose utility functions and extensions.
- **`:navigation`**: Centralized navigation logic for the entire app.
- **`:screens`**: High-level screen definitions and routing.
---
## :hammer_and_wrench: Tech Stack
- **Language:** [Kotlin](https://kotlinlang.org/)
- **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Dependency Injection:** [Hilt (Dagger)](https://dagger.dev/hilt/)
- **Asynchronous Flow:** [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html)
- **Architecture Pattern:** MVI (Model-View-Intent)
- **Local Database:** [Room](https://developer.android.com/training/data-storage/room)
- **Network:** [Ktor Client](https://ktor.io/docs/client.html)
- **Persistence:** [DataStore](https://developer.android.com/topic/libraries/architecture/datastore)
- **Backend/Analytics:** Firebase (Firestore, Messaging, Analytics)
- **Image Loading:** [Coil](https://coil-kt.github.io/coil/)
- **Build System:** Gradle (Kotlin DSL) with Version Catalogs.
---
## :rocket: Getting Started
### Prerequisites
- Android Studio Ladybug (2024.2.1) or newer.
- JDK 17+.
- Android SDK 35 (Compile SDK).
### Setup
1. **Clone the repo:**

Kotlin
Kotlin Programming Language
Kotlin is a concise and multiplatform programming language by JetBrains. Enjoy coding and build server-side, mobile, web, and desktop applications efficiently.
https://kotlinlang.org/


Android Developers
Jetpack Compose UI App Development Toolkit - Android Developers
Jetpack Compose is Android's recommended modern toolkit for building native UI. It simplifies and accelerates UI development on Android. Quickly bring your app to life with less code, powerful tools, and intuitive Kotlin APIs.
https://developer.android.com/jetpack/compose


dagger.dev
Hilt
Dagger is a fully static, compile-time dependency injection framework for both Java and Android.

Kotlin Help
Coroutines | Kotlin
https://kotlinlang.org/docs/coroutines-overview.html


Kotlin Help
Asynchronous Flow | Kotlin
https://kotlinlang.org/docs/flow.html

