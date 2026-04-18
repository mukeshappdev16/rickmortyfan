# Rick & Morty Fan App 🛸

A modern, adaptive Android application for Rick and Morty enthusiasts, built with Jetpack Compose, Material 3, and Clean Architecture.

## Features ✨

- **Adaptive Layouts:** Supports various screen sizes using `NavigationSuiteScaffold` (Bottom Bar for mobile, Navigation Rail for landscape/tablets).
- **Authentication:** Secure user login and signup flow.
- **Characters:** Explore the vast multiverse of Rick and Morty characters with custom infinite scroll.
- **Locations:** Discover all the weird and wonderful places in the show with infinite scroll.
- **Episodes:** Browse through all seasons and episodes with infinite scroll.
- **Favorites:** Save your favorite characters, locations, and episodes for quick access.
- **Profile:** Manage your user profile and settings with a polished Material 3 UI.

## Architecture 🏗️

The project is built following **Clean Architecture** principles and the **MVVM (Model-View-ViewModel)** pattern to ensure scalability, maintainability, and testability.

### Layers:
- **Domain Layer:** The heart of the application. Contains **Entities**, **Repository Interfaces**, and **Use Cases**. It is purely Kotlin/Java and has no dependencies on the Android framework.
- **Data Layer:** Responsible for data retrieval and persistence. Implements the Domain's Repository interfaces using **Retrofit** for network calls and **Room** for local storage.
- **Presentation Layer:** Handles the UI and user interaction. Built with **Jetpack Compose**, it follows the MVVM pattern where ViewModels interact with Use Cases and expose state via Kotlin **Flow** and **Compose State**.

## Multi-Module Architecture 📁

The project is modularized to improve build performance and enforce a clear separation of concerns:
- **`:app`**: The main entry point. Contains the implementation of core features (Characters, Locations, Episodes, Favorites, Profile).
- **`:auth`**: A feature module dedicated to authentication logic, including login and signup screens.
- **`:common`**: A core module providing shared resources, utilities, and base classes used across the entire project.

## Tech Stack 🛠️

- **Language:** Kotlin
- **UI:** Jetpack Compose with Material 3 Adaptive Navigation.
- **Dependency Injection:** Hilt (Dagger).
- **Networking:** Retrofit with Gson.
- **Local Database:** Room.
- **Image Loading:** Coil.
- **Navigation:** Type-safe Compose Navigation using Kotlin Serialization.
- **Concurrency:** Kotlin Coroutines & Flow.

## Screenshots 📸

<div align="center">
  <img src="screenshots/Screenshot_20260418_142709.png" width="30%" />
  <img src="screenshots/Screenshot_20260418_142742.png" width="30%" />
  <img src="screenshots/Screenshot_20260418_142817.png" width="30%" />
  <br />
  <img src="screenshots/Screenshot_20260418_142824.png" width="30%" />
  <img src="screenshots/Screenshot_20260418_142829.png" width="30%" />
  <img src="screenshots/Screenshot_20260418_142858.png" width="30%" />
  <br />
  <img src="screenshots/Screenshot_20260418_142904.png" width="30%" />
  <img src="screenshots/Screenshot_20260418_142916.png" width="30%" />
  <img src="screenshots/Screenshot_20260418_142936.png" width="30%" />
</div>

---
Developed with ❤️ by Mukesh
