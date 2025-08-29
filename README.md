# PokedexApp Challenge

A native Android application developed in Kotlin that displays data from the [PokeAPI](https://pokeapi.co/). This project is built following industry best practices, with a focus on a clean, robust, and maintainable architecture, and a smooth, aesthetic user experience.

![Pokedex App](https://user-images.githubusercontent.com/2029139/189528761-e3135439-5147-4015-b778-184b2565452f.gif)

## Features

- **Infinite Pokémon List:** Displays a list of Pokémon and automatically loads more upon scrolling (Pagination).
- **Detail Screen:** A detailed view for each Pokémon, showcasing its stats, types, weight, and height.
- **Offline Support:** The application uses a local database (Room) to cache data. Once a Pokémon's details are viewed, they load instantly on subsequent visits, even without an internet connection.
- **Modern & Reactive UI:** The user interface is built 100% with Jetpack Compose, following Material 3 design guidelines.
- **State Handling:** The UI visually responds to loading, error, and success states, improving the user experience.
- **Native Splash Screen:** Implements the Android 12+ Splash Screen API for a professional app startup.
- **Aesthetic Design:**
    - A visually appealing grid layout on the main screen.
    - A themed detail screen, with colors that adapt to the Pokémon's primary type.
    - Subtle animations on the base stat bars.

## Architecture

The application follows the principles of **Clean Architecture** adapted for Android development, using the **MVVM (Model-View-ViewModel)** pattern. This architecture separates the code into independent layers, resulting in software that is easier to test, maintain, and scale.

### Application Layers

- **Presentation Layer (UI):** Built with Jetpack Compose. `ViewModels` expose state streams (`StateFlow`) that the UI observes to render itself. UI events are sent to the `ViewModels` to be processed.
- **Domain Layer:** Contains the core business logic of the application. `UseCases` encapsulate specific actions a user can perform, serving as a bridge between the Presentation and Data layers.
- **Data Layer:** Implements the Repository pattern as the Single Source of Truth. The `Repository` decides where to fetch data from (network or local cache) and exposes it to the Domain layer. It consists of:
    - **Remote Data Source:** Responsible for communicating with the PokeAPI using Retrofit.
    - **Local Data Source:** Manages the data cache in an SQLite database via Room.

### Data Flow Diagram

```
+-----------+      +----------------+      +-----------+      +-----------------------------+
|    UI     |----->|   ViewModel    |----->|  UseCase  |----->|         Repository          |
| (Compose) |<-----|  (StateFlow)   |<-----| (Domain)  |<-----| (Single Source of Truth)    |
+-----------+      +----------------+      +-----------+      +-----------------------------+
                                                                             |
                                                                             |
                                             +-------------------------------+-------------------------------+
                                             |                                                               |
                                             V                                                               V
                                    +--------------------+                                      +-------------------+
                                    | Remote Data Source |                                      | Local Data Source |
                                    | (Retrofit & Moshi) |                                      |  (Room Database)  |
                                    +--------------------+                                      +-------------------+
```

## Tech Stack & Libraries

- **Language:** 100% [Kotlin](https://kotlinlang.org/)
- **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose) for a declarative and modern UI.
- **Asynchronicity:** [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) and [Flow](https://kotlinlang.org/docs/flow.html) for efficient asynchronous data handling.
- **Architecture:**
    - [Android Jetpack ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
    - Clean Architecture Principles
- **Dependency Injection:** [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) to decouple classes and facilitate testing.
- **Networking:** [Retrofit](https://square.github.io/retrofit/) for API calls and [Moshi](https://github.com/square/moshi) for JSON parsing.
- **Database:** [Room](https://developer.android.com/training/data-storage/room) for data persistence and offline caching.
- **Pagination:** [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) to incrementally load and display data.
- **Image Loading:** [Coil](https://coil-kt.github.io/coil/) to efficiently load images from the network.
- **Navigation:** [Jetpack Navigation Component](https://developer.android.com/guide/navigation) to manage navigation between screens.

## How to Build & Run

To build and run the project, you will need:
- Android Studio Koala | 2024.1.1 or a more recent stable version.
- JDK 17.

Follow these steps:
1.  Clone the repository:
    ```bash
    git clone https://github.com/charBot21/test-pokedexapp.git
    ```
2.  Open the project in Android Studio.
3.  Let Gradle sync the project dependencies.
4.  Run the app on an emulator or a physical device with Android 7.0 (API 24) or higher.

## Potential Future Improvements

- **Data Pre-fetching with WorkManager:** Implement a background task to proactively download the first page of data, making the initial app load feel instantaneous.
- **Search Functionality:** Add a search bar to the main screen to filter Pokémon by name or number.
- **Testing:** Add Unit Tests for ViewModels and UseCases, and UI Tests with Compose Test.
- **Transition Animations:** Enhance navigation with screen transition animations between the list and detail screens.

