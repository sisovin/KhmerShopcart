# Modern ShopCart - Android Native Design Document

## 1. Project Overview

This document outlines the UI/UX design and technical specifications for the **Khmer ShopCart** Android application. This native app serves as the mobile counterpart to the "Modern ShopCart" Next.js web platform, delivering a high-performance, touch-optimized shopping experience using modern Android development practices.

## 2. Technology Stack

* **Language**: Kotlin (JVM 21+)
* **UI Toolkit**: Jetpack Compose (Declarative UI)
* **Navigation**: AndroidX Navigation Component (Compose Integration)
* **Architecture**: MVVM (Model-View-ViewModel) with Clean Architecture
* **Dependency Injection**: Hilt (recommended)
* **Network**: Retrofit + OkHttp
* **Image Loading**: Coil
* **Local Storage**: DataStore / Room

## 3. Design System & Color Scheme

The Android app mirrors the web platform's minimalist, high-contrast aesthetic.

### Color Palette (Light Mode Reference)

| Token | Web HSL | Hex Approximation | Usage |
| :--- | :--- | :--- | :--- |
| **Background** | `0 0% 100%` | `#FFFFFF` | Main screen backgrounds |
| **Foreground** | `0 0% 3.9%` | `#0A0A0A` | Primary text, headings |
| **Primary** | `0 0% 9%` | `#171717` | Primary buttons, active icons, brand elements |
| **Primary FG** | `0 0% 98%` | `#FAFAFA` | Text on primary buttons |
| **Secondary** | `0 0% 96.1%` | `#F5F5F5` | Secondary buttons, card backgrounds |
| **Muted** | `0 0% 96.1%` | `#F5F5F5` | Disabled states, placeholders |
| **Muted FG** | `0 0% 45.1%` | `#737373` | Secondary text, captions |
| **Destructive** | `0 84.2% 60.2%` | `#EF4444` | Error states, delete actions |
| **Border** | `0 0% 89.8%` | `#E5E5E5` | Dividers, input borders |

*Note: Dark mode values should be implemented using the corresponding dark theme tokens from the web project.*

## 4. UI/UX Specifications

### 4.1. Welcome Screen (Onboarding)

**Goal**: Introduce the user to the app and prompt them to enter the main flow.

* **Layout**: `Column` centered vertically and horizontally.
* **Components**:
  * **Logo**: Large brand logo centered (Modern ShopCart).
  * **Illustration**: Minimalist vector graphic representing shopping/e-commerce.
  * **Title**: "Welcome to Khmer ShopCart" (Typography: Headline Large).
  * **Subtitle**: "Your one-stop destination for quality products at amazing prices." (Typography: Body Medium, Muted Foreground).
  * **CTA Button**: "Let's Get Started"
    * *Style*: Primary Button (Solid `#171717` background, `#FAFAFA` text).
    * *Action*: Navigates to **Home Screen**.

### 4.2. Home Screen

**Goal**: Main dashboard for discovery and navigation.

* **Layout**: `Scaffold` with `TopAppBar` and `BottomNavigation` (optional), containing a scrollable `LazyColumn`.
* **Sections**:
    1. **Header**: App bar with Logo (Left) and Search/Cart Icons (Right).
    2. **Banner Carousel**:
        * *Component*: Horizontal Pager (Jetpack Compose).
        * *Content*: Promotional images with overlay text.
        * *Indicator*: Dots indicator at the bottom.
    3. **Categories**:
        * *Component*: `LazyRow`.
        * *Item*: Circular or rounded-square icons with labels (e.g., Electronics, Fashion, Home).
    4. **Featured Products**:
        * *Title*: "Featured Collections".
        * *Component*: `LazyRow` of Product Cards.
        * *Card Content*: Image, Title, Price, "Add" button.
    5. **Recommendations**:
        * *Title*: "Recommended for You".
        * *Component*: `LazyVerticalGrid` (nested or distinct section).
        * *Card Style*: Standard product card with "Heart" (Wishlist) icon.
    6. **Call to Action (CTA)**:
        * *Component*: Full-width banner or card.
        * *Text*: "Don't miss out on our daily deals!"
        * *Button*: "View Deals" (Secondary Style).

### 4.3. Product Detail Screen

**Goal**: Detailed view to convert interest into a purchase.

* **Navigation**: Accessed by tapping a product on Home/Shop screens.
* **Layout**: `Column` with scrollable content + fixed bottom bar.
* **Content**:
  * **Image Gallery**: Large swipeable image area at the top.
  * **Info Section**:
    * Product Title (Headline Medium).
    * Price (Title Large, Primary Color).
    * Rating/Reviews summary.
  * **Selectors**: Color/Size variants using `FlowRow` of `FilterChip`s.
  * **Description**: Expandable text block.
* **Bottom Bar (Fixed)**:
  * **Buy Now Button**:
    * *Style*: Primary Button (Full width or large weight).
    * *Text*: "Buy Now".
    * *Action*: Triggers checkout flow or adds to cart and navigates to Cart.
  * **Add to Cart**: Secondary/Outlined button (Icon only or smaller weight).

### 4.4. Cart Screen

**Goal**: Review selected items and proceed to payment.

* **Layout**: `Scaffold` with TopBar "My Cart".
* **Content**:
  * **Empty State**: If cart is empty, show icon + "Start Shopping" button.
  * **Cart List**: `LazyColumn` of Cart Items.
    * *Item Layout*: `Row` with Thumbnail, Details (Title, Price), and Quantity Controller (`- 1 +`).
    * *Swipe to Dismiss*: Ability to remove items by swiping.
  * **Order Summary**:
    * Subtotal, Tax, Shipping, Total (Bold).
* **Bottom Bar (Fixed)**:
  * **Checkout Button**:
    * *Style*: Primary Button (Solid).
    * *Text*: "Checkout • $Total".
    * *Action*: Navigates to Payment/Checkout flow (ABA PayWay integration).

## 5. Navigation Graph (Jetpack Navigation)

The app uses a single-activity architecture with a `NavHost` defining the following destinations:

```kotlin
sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Home : Screen("home")
    object ProductDetail : Screen("product/{productId}") {
        fun createRoute(productId: String) = "product/$productId"
    }
    object Cart : Screen("cart")
    object Checkout : Screen("checkout")
}
```

### Flow

1. **App Launch** -> `Welcome`
2. `Welcome` -> (Click "Let's Get Started") -> `Home`
3. `Home` -> (Click Product) -> `ProductDetail`
4. `Home` -> (Click Cart Icon) -> `Cart`
5. `ProductDetail` -> (Click "Buy Now") -> `Cart` (or direct Checkout)
6. `Cart` -> (Click "Checkout") -> `Checkout`

## 6. Development Guidelines

* **Theme**: Use `MaterialTheme` in Compose, overriding `ColorScheme` with the values defined in Section 3.
* **Typography**: Configure `Typography` to match the web's font stack (Inter or system default sans-serif).
* **Components**: Create reusable Composables for `ProductCard`, `PrimaryButton`, and `SectionHeader` to ensure consistency.
