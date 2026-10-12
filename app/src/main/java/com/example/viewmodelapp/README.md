# PasswordAnalyzer — ViewModel in Jetpack Compose

This project covers the concept of **ViewModel** in Jetpack Compose through a practical Password Strength Analyzer application.

## What is ViewModel?

`ViewModel` is a component from the Android Jetpack libraries that helps store and manage UI-related state and handle screen-level logic.

It separates state and logic from the UI layer, making the code easier to organize, maintain, and test.

Unlike ordinary local composable state, a ViewModel can survive configuration changes, such as screen rotation. When the Activity is recreated because of a configuration change, the new Activity can obtain the same ViewModel instance.

## Why do we need ViewModel when we have rememberSaveable?

We can use `rememberSaveable` to preserve supported UI state across configuration changes and restore it after certain system-initiated process recreations.

However, ViewModel and `rememberSaveable` serve different purposes.

- **rememberSaveable:** Useful for simple UI state, such as text-field values, selected tabs, and UI preferences that need to be restored.
- **ViewModel:** Useful for managing screen-level state, handling events, performing calculations, and separating UI logic from composables.
- **SavedStateHandle:** Can help restore selected ViewModel state after system-initiated process recreation.
- **Room or another persistent data source:** Useful when data needs to be stored beyond the lifetime of the current process.

A ViewModel survives configuration changes because Android retains it through the ViewModel lifecycle mechanism, which uses a `ViewModelStore`.

However, a ViewModel does not automatically survive permanent removal of its owner or process death. Its in-memory state is lost when the ViewModel is cleared or the process terminates. State that needs to be restored must be saved separately using an appropriate saved-state or persistent-storage mechanism.

Configuration changes include screen rotation and other changes that cause an Activity to be recreated, such as certain language or display-configuration changes.

## Project Implementation of ViewModel

I built a **Password Strength Analyzer** to practise ViewModel in Jetpack Compose.

The project mainly contains two Kotlin files:

### 1. MainActivity.kt — UI Layer

`MainActivity.kt` contains the composable screen and its presentation.

It displays:

- A password input field.
- A button to toggle password visibility.
- A password-strength label.
- A linear progress indicator.
- A checklist showing which password requirements have been met.

The UI reads the state exposed by `PasswordVM` and updates when the observable state changes. User actions, such as entering a password or toggling its visibility, are sent to the ViewModel.

### 2. PasswordVM.kt — ViewModel Layer

`PasswordVM.kt` manages the screen's state and password-strength calculation logic.

It contains:

- `password`: Stores the entered password.
- `isPasswordVisible`: Controls whether the password is displayed or hidden.
- `hasMinLength`: Checks whether the password contains at least eight characters.
- `hasUpper`: Checks for an uppercase letter.
- `hasLower`: Checks for a lowercase letter.
- `hasDigit`: Checks for a number.
- `hasSymbol`: Checks for a symbol.
- `strength`: Calculates a score based on the number of requirements met.
- `strengthLabel`: Determines whether the password is labelled Weak, Medium, Strong, or Very Strong.
- `onPasswordChange()`: Updates the password when the user enters text.
- `togglePasswordVisibility()`: Changes the password-visibility state.

The strength score is calculated by counting the number of requirements satisfied and dividing that number by five. The result ranges from `0.0` to `1.0`.

The UI uses this score to display progress and uses the strength label to communicate the result to the user.

## What I Learned

Through this project, I practised:

- Creating and obtaining a ViewModel in Jetpack Compose.
- Managing observable state with `mutableStateOf`.
- Using private setters to control how state is updated.
- Creating derived properties from existing state.
- Handling UI events through ViewModel functions.
- Separating screen logic from the UI layer.
- Understanding how Compose observes state and recomposes when that state changes.
- Understanding the difference between `rememberSaveable`, ViewModel, and saved-state restoration.

This project demonstrates how ViewModel can help keep a Compose UI focused on presentation while state management and screen-level logic remain in a separate class.