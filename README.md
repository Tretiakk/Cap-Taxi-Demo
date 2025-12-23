# Cap Taxi

**Geospatial & Routing Logic**:
Utilizes Android Fused Location Provider for precise user positioning. The routing system integrates Google Maps SDK and Google Cloud APIs (Directions & Places) to handle point-to-point path visualization and address autocomplete.

**Dynamic Fare Calculation**:
Implements a distance-based pricing algorithm that calculates costs based on the Great-circle distance between coordinates A and B. The logic includes a 15km threshold check and dynamic price adjustments that synchronize with the selected driver’s rate in real-time.

**Data Filtering & Driver Selection**:
Features a local data-driven system for managing a fleet of drivers. Includes a multi-parameter search functionality (car body type, model, driver identity) and a detailed profile view system implemented through specialized UI interactions.

**State Management & Resilience**
Built on MVVM architecture with Jetpack Compose, ensuring a reactive UI. The app includes a network monitoring layer that detects connectivity loss, providing automated error states and UI recovery once the connection is restored.

**Localization & Globalization**:
A centralized localization system supporting 6 languages (DE, EN, ES, FR, PL, UA). The architecture is designed to handle RTL/LTR layouts and local formatting for currency and distance.

<br>

Tech Stack: Kotlin, Jetpack Compose, MVVM, Google Maps, Retrofit, Google Cloud API (Directions API, Places API), Android Fused Location Provider, Compose Animations, JUnit, Gradle KTS.

<br>

<br>

## [Video demonstration](https://youtube.com/shorts/PiRExvUjGxU)

![](https://github.com/Tretiakk/Taxist-Demonstratoin/blob/main/Taxist%20Preview%203.png)

### Main menu
![](https://github.com/Tretiakk/Taxist-Demonstratoin/blob/main/Main.png)

### Drivers menu
![](https://github.com/Tretiakk/Taxist-Demonstratoin/blob/main/Drivers.png)

### Info menu
![](https://github.com/Tretiakk/Taxist-Demonstratoin/blob/main/Info.png)

### Network error
![](https://github.com/Tretiakk/Taxist-Demonstratoin/blob/main/Network.png)

