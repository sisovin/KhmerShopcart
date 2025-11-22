# Khmer ShopCart - Android Application

## Overview

**Khmer ShopCart** is a modern, native Android e-commerce application built with **Jetpack Compose**. It serves as the mobile counterpart to the "Modern ShopCart" Next.js web platform, delivering a high-performance, touch-optimized shopping experience.

The app features a minimalist, high-contrast design with a focus on usability and speed. It follows modern Android development practices, including MVVM architecture and declarative UI.

## Features

* **Welcome Screen**: Engaging onboarding experience.
* **Home Dashboard**:
  * Promotional Banner Carousel.
  * Category browsing.
  * Featured Collections.
  * Personalized Recommendations.
* **Product Details**:
  * High-quality image gallery.
  * Product variants (Color/Size selection).
  * Detailed descriptions and ratings.
* **Shopping Cart**:
  * Manage cart items (add/remove/update quantity).
  * Real-time price calculation (Subtotal, Tax, Total).
* **Checkout Flow**: Seamless transition to payment (UI placeholder).

## Technology Stack

* **Language**: Kotlin
* **UI Toolkit**: Jetpack Compose (Material3)
* **Navigation**: Jetpack Navigation Compose
* **Image Loading**: Coil
* **Architecture**: MVVM (Model-View-ViewModel)
* **Build System**: Gradle (Kotlin DSL)

## Project Structure

```
app/src/main/java/com/peanech/khmershopcart/
├── MainActivity.kt          # Entry point
├── ui/
│   ├── components/          # Reusable UI components (ProductCard, SectionHeader)
│   ├── navigation/          # Navigation graph and screen definitions
│   ├── screens/             # Screen implementations (Home, Product, Cart, etc.)
│   └── theme/               # Material3 Theme, Colors, and Typography
```

## Setup & Installation

1. **Prerequisites**:
    * Android Studio Ladybug or newer (recommended).
    * JDK 17 or higher.
    * Android SDK API 35.

2. **Clone the Repository**:

    ```bash
    git clone https://github.com/sisovin/KhmerShopcart.git
    cd khmer-shopcart-android
    ```

3. **Open in Android Studio**:
    * Open Android Studio and select "Open".
    * Navigate to the project directory and select it.

4. **Build and Run**:
    * Wait for Gradle sync to complete.
    * Select an emulator or connected device.
    * Click the "Run" button (green play icon).

## Design System

The app uses a custom design system defined in `ui/theme/`:

* **Colors**: High-contrast black and white palette with red accents for errors.
* **Typography**: Clean sans-serif typography (default system font).

## Future Improvements

* **Backend Integration**: Connect to a real API (e.g., the Next.js backend).
* **Local Storage**: Implement Room or DataStore for persisting cart items.
* **Payment Integration**: Integrate ABA PayWay or Stripe.
* **Authentication**: User login and profile management.

## License

This project is licensed under the MIT License.
