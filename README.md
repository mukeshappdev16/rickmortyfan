# Rick & Morty Fan App 🛸

A modern, adaptive Android application for Rick and Morty enthusiasts, built with Jetpack Compose, Material 3, and Clean Architecture.

## Features ✨

- **Adaptive Layouts:** Supports various screen sizes using `NavigationSuiteScaffold` (Bottom Bar for mobile, Navigation Rail for landscape/tablets).
- **Authentication:** Secure user login and signup flow.
- **Characters:** Explore the vast multiverse of Rick and Morty characters.
- **Locations:** Discover all the weird and wonderful places in the show.
- **Episodes:** Browse through all seasons and episodes.
- **Favorites:** Save your favorite characters, locations, and episodes for quick access.
- **Profile:** Manage your user profile and settings.

## Tech Stack 🛠️

- **UI:** Jetpack Compose with Material 3 Adaptive.
- **Architecture:** Clean Architecture with MVVM.
- **Dependency Injection:** Hilt.
- **Networking:** Retrofit / Ktor.
- **Local Database:** Room.
- **Image Loading:** Coil.
- **Navigation:** Jetpack Compose Navigation with Type-Safe Routes (Kotlin Serialization).
- **Asynchronous Work:** Kotlin Coroutines & Flow.

## Project Structure 📁

The project is modularized into three main modules:
- `:app`: The main application module containing the UI and domain logic for core features.
- `:auth`: A dedicated module for authentication logic and screens.
- `:common`: Shared utilities, constants, and data models used across modules.

## Getting Started 🚀

1. Clone the repository.
2. Open the project in Android Studio (Ladybug or newer recommended).
3. Sync Gradle and run the `:app` module.

## Screenshots 📸

*(Add screenshots here)*

---
Developed with ❤️ by Mukesh
