# Der3 Muslim (درع المسلم)

**Der3 Muslim** is a modern, modular Android application designed to be a comprehensive spiritual companion for Muslims. It provides a rich set of features including daily Azkar, prayer notifications, a digital Masbaha, and a customizable user experience, all built with the latest Android development practices.

---

## 📱 Features

- **📖 Azkar & Supplications:**
    - Extensive collection of daily Azkar (Morning, Evening, Sleep, etc.).
    - Categorized for quick access with detailed views for each Zekr.
    - Search functionality to find specific supplications.
- **🔔 Smart Notifications:**
    - Timely reminders for daily Azkar.
    - Custom reminders for religious occasions and specific user-defined times.
    - Integration with Firebase Cloud Messaging (FCM) for dynamic updates.
- **📿 Digital Masbaha (Tasbih):**
    - Interactive counter with haptic feedback.
    - History tracking to monitor your daily progress.
    - Customizable Azkar for the counter.
- **🗑️ Recycle Bin:**
    - Safety feature for deleted custom Azkar or notifications, allowing for recovery.
- **⭐ Favorites:**
    - Quick access to your most-used supplications.
- **🎨 Modern UI/UX:**
    - Fully built with Jetpack Compose.
    - Supports Dark/Light themes and dynamic styling.

---

## 🏗️ Architecture & Project Structure

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

## 🛠️ Tech Stack

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

## 🚀 Getting Started

### Prerequisites
- Android Studio Ladybug (2024.2.1) or newer.
- JDK 17+.
- Android SDK 35 (Compile SDK).

### Setup
1. **Clone the repo:**
   ```bash
   git clone https://github.com/your-username/Der3-Muslim.git
   ```
2. **Open in Android Studio:**
   Wait for the Gradle sync to complete.
3. **Firebase Setup:**
   - Add your `google-services.json` to the `/app` directory.
4. **Build & Run:**
   Select the `app` module and run on your preferred device.

---

## 🤝 Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project.
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`).
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`).
4. Push to the Branch (`git push origin feature/AmazingFeature`).
5. Open a Pull Request.

---

## 📜 License
Distributed under the MIT License. See `LICENSE` for more information.
