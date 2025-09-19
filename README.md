Clean Architecture User Management App

📱 App Description
A modern Android application built with Clean Architecture principles that allows users to manage personal information efficiently. The app demonstrates best practices in Android development using the latest technologies and architectural patterns.
✨ Key Features

User Input Management: Add new users with personal details (name, age, job title, gender)
Real-time Data Display: View all saved users in an organized list
Data Persistence: Local database storage using Room
Modern UI: Built with Jetpack Compose for a responsive user experience
Data Validation: Comprehensive input validation with user-friendly error messages
CRUD Operations: Create, read, and delete user records
Reactive Programming: Real-time updates using Flow and coroutines

🛠️ Technical Stack
Core Technologies

Language: Kotlin
Architecture: Clean Architecture + MVVM
Dependency Injection: Dagger Hilt
Database: Room
Async Programming: Coroutines + Flow
UI Framework: Jetpack Compose
Navigation: Navigation Compose

🎯 App Features
Screen 1: User Input

Purpose: Collect user information
Fields: Name, Age, Job Title, Gender
Features:

Real-time input validation
Dropdown gender selection
Loading states during save operations
Error handling with user feedback
Form reset after successful submission

Screen 2: User Display

Purpose: Show all saved users
Features:

Real-time data updates
Delete individual users
Empty state handling

🧪 Testing
The project includes comprehensive unit tests covering all layers:
Test Categories

Mapper Tests: Data transformation validation
Repository Tests: Database operations and error handling
Use Case Tests: Business logic verification
ViewModel Tests: UI state management and user interactions

🎨 Design Patterns Used
Clean Architecture

Dependency Rule: Inner layers don't depend on outer layers
Use Cases: Single responsibility business operations
Repository Pattern: Abstract data source management

MVVM (Model-View-ViewModel)

Separation of Concerns: UI logic separated from business logic
Data Binding: Reactive UI updates using StateFlow
Lifecycle Awareness: ViewModels survive configuration changes

Dependency Injection

Dagger Hilt: Compile-time dependency injection
Module Organization: Clear separation of concerns
Testability: Easy mocking for unit tests
