# Der3 Muslim (درع المسلم)

Der3 Muslim is a comprehensive Android application designed to serve as a spiritual companion for Muslims. It provides various features including Azkar, prayer notifications, and a digital Masbaha.## Features

- **Azkar & Supplications:** A wide collection of daily Azkar categorized for easy access (Morning, Evening, Sleep, etc.).
- **Notifications:** Timely reminders for Azkar and religious occasions.
- **Masbaha (Digital Tasbih):** An interactive digital counter for Tasbih with customizable Azkar.
- **MVI Architecture:** Built using the Model-View-Intent pattern for a predictable and robust UI state management.
- **Modern Tech Stack:** Developed with Kotlin, Jetpack Compose, Hilt, Room, and Ktor.

## Project Structure

The project follows a modular architecture to ensure scalability and maintainability:

- `:app` - The main entry point of the application.
- `:features` - Contains UI features:
    - `:features:home` - Main dashboard, Azkar categories, and notifications.
    - `:features:on_boarding` - Initial user experience and setup.
- `:core` - Shared logic and infrastructure:
    - `:core:shared` - Domain models, use cases, and repositories.
    - `:core:ui` - Shared Compose components and design system.
    - `:core:mvi` - Base classes for MVI architecture.
    - `:core:data_store` - Local settings storage using DataStore.
    - `:core:player` - Media playback logic for Azkar audio.
    - `:core:utils` - General utility functions and extensions.

## Tech Stack

- **Language:** Kotlin
- **UI Framework:** Jetpack Compose
- **Dependency Injection:** Hilt (Dagger)
- **Database:** Room
- **Networking:** Ktor
- **Asynchronous Programming:** Coroutines & Flow
- **Analytics & Backend:** Firebase (Firestore, Messaging, Analytics)

## Getting Started

1. Clone the repository.
2. Open the project in Android Studio (Ladybug or newer recommended).
3. Sync Gradle and build the project.
4. Run the `app` module on an emulator or physical device.

## Contributing

Contributions are welcome! Please feel free to submit Pull Requests or open issues for bugs and feature requests.
