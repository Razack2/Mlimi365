# Mlimi365 - Farm Season & Crop Guide

Mlimi365 is an Android application designed to empower farmers with accessible, offline-first agricultural knowledge. It provides a comprehensive farming calendar, crop-specific growing guides, and seasonal reminders to help farmers make better decisions and achieve healthier harvests.

## Tech Stack
- **Language:** Kotlin
- **UI Framework:** Jetpack Compose
- **Minimum SDK:** [Insert your min SDK, e.g., API 24]
- **Architecture:** MVVM (Model-View-ViewModel)

## 5. Functional Requirements
The application shall:
1. Display a January to December farming calendar showing important farming periods.
2. Identify the current farming period based on the current date.
3. Display crops suitable for the current farming period.
4. Allow farmers to select a crop and view its basic growing guide.
5. Provide basic crop-growing guidance on planting, spacing, fertilizer, weeding, and harvesting.
6. Provide reminders for important seasonal farming activities.
7. Allow farmers to search for crops and access their growing information.
8. Provide the main farming information offline, allowing farmers to use the application without continuous internet connectivity.

## 6. Non-Functional Requirements
The application shall meet the following non-functional requirements:

- **Usability:** The application shall provide a simple and clear interface that allows a farmer to access the farming calendar, crop information, and reminders with minimal navigation.
- **Performance:** The application shall display the requested farming information within 30 seconds after a user selects a farming period or crop, under normal device conditions.
- **Availability:** The application shall allow users to access the farming calendar and crop-growing information without an internet connection after the application has been installed.
- **Reliability:** The application shall provide consistent and validated farming information and shall correctly display the farming period and related crop information based on the stored data.
- **Compatibility:** The application shall run correctly on supported Android smartphones and shall maintain its core functionality across the supported Android versions and screen sizes.


##  UI Implementation Status
- [x] Splash Screen (Implemented using Jetpack Compose Canvas for custom vector graphics)
- [x] Farming Calendar Screen
- [x] Crop Details Screen
- [x] Search Screen
- [x] Reminders / Notifications

