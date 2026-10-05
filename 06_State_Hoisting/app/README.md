# State Management & State Hoisting

This project covers and demonstrates the concepts of **State Management** and **State Hoisting** in Jetpack Compose.

---

## 1. State Management

**State** controls how the UI updates when data changes in Jetpack Compose.

In this project, state is mainly managed using:

* `remember`
* `mutableStateOf`
* `rememberSaveable`

### `remember`

`remember` keeps a value across **recompositions**.

```kotlin
var amount by remember { mutableStateOf("") }
```

When `ExpenseScreen()` recomposes, the value of `amount` is remembered instead of being reset.

> **Important:** `remember` does not normally survive configuration changes such as screen rotation.

### `mutableStateOf`

`mutableStateOf` creates an **observable state value**.

When its value changes, Compose knows that the UI reading that state may need to be recomposed.

For example:

```kotlin
var amount by remember { mutableStateOf("") }
```

Here:

* `amount` holds the current value.
* Changing `amount` causes the relevant UI to update.

### `rememberSaveable`

`rememberSaveable` stores state that can be restored after **configuration changes**, such as screen rotation, when the value is saveable.

For example:

```kotlin
var amount by rememberSaveable { mutableStateOf("") }
```

Unlike `remember`, `rememberSaveable` can restore the value after a configuration change.

---

## 2. State Hoisting

**State Hoisting** means moving state from a child composable to its parent composable so that the parent can control and share that state.

A common pattern is:

```text
State
  ↓
Parent Composable
  ↓
Child Composable
```

The child does not own the state. Instead, the parent:

1. Owns the state.
2. Passes the current value to the child.
3. Passes a callback so the child can request a change.

This is commonly called **unidirectional data flow**:

```text
State flows down
Events flow up
```

### Why do we use State Hoisting?

State hoisting helps with:

* Sharing state between composables.
* Keeping state in one place.
* Making composables easier to reuse.
* Making composables easier to test.
* Separating state management from UI display.

---

# 3. State Hoisting in This Project

This project contains several composable functions:

* `ExpenseScreen()`
* `ExpenseInput()`
* `PeopleInput()`
* `CalculateButton()`
* `ExpenseResult()`

The `ExpenseScreen()` composable acts as the **parent** and owns the main state.

```kotlin
var amount by remember { mutableStateOf("") }
var people by remember { mutableStateOf("") }
var result by remember { mutableStateOf(0.0) }
```

These variables are stored in `ExpenseScreen()`.

The child composables do not create or own these pieces of state. Instead, `ExpenseScreen()` passes the required values and callback functions to them.

---

## 4. ExpenseInput()

`ExpenseInput()` receives two parameters:

```kotlin
fun ExpenseInput(
    amountStr: String,
    onAmountChange: (String) -> Unit
)
```

The parent calls it like this:

```kotlin
ExpenseInput(
    amountStr = amount,
    onAmountChange = { amount = it }
)
```

Here:

* `amount` is the state owned by `ExpenseScreen()`.
* `amountStr` receives the current value of that state.
* `onAmountChange` is a callback that allows `ExpenseInput()` to send the new value back to the parent.

So the flow is:

```text
ExpenseScreen
     │
     │ amount
     ↓
ExpenseInput
     │
     │ onAmountChange("new value")
     ↓
ExpenseScreen
```

The important point is that **`ExpenseInput()` does not own the `amount` state**.

---

## 5. PeopleInput()

The same idea is used for the number of people.

`ExpenseScreen()` owns:

```kotlin
var people by remember { mutableStateOf("") }
```

It passes the value and callback to `PeopleInput()`:

```kotlin
PeopleInput(
    peopleStr = people,
    onPeopleChange = { people = it }
)
```

The child receives:

```kotlin
fun PeopleInput(
    peopleStr: String,
    onPeopleChange: (String) -> Unit
)
```

So:

```text
ExpenseScreen
     │
     │ people
     ↓
PeopleInput
     │
     │ onPeopleChange()
     ↓
ExpenseScreen
```

Again, the state is **owned by the parent**, while the child only receives the value and reports changes through the callback.

---

## 6. CalculateButton()

`CalculateButton()` receives a callback:

```kotlin
fun CalculateButton(onCalculate: () -> Unit)
```

The parent provides the calculation logic:

```kotlin
CalculateButton(
    onCalculate = {
        val amountValue = amount.toDoubleOrNull() ?: 0.0
        val peopleValue = people.toIntOrNull() ?: 1

        result =
            if (peopleValue > 0)
                amountValue / peopleValue
            else
                0.0
    }
)
```

The button itself does not calculate the expense.

It simply calls:

```kotlin
onClick = onCalculate
```

So the responsibility is separated:

```text
CalculateButton
      │
      │ User clicks button
      ↓
onCalculate()
      ↓
ExpenseScreen
      ↓
Calculates result
      ↓
Updates result state
```

This is another example of **state and logic being controlled by the parent**.

---

## 7. ExpenseResult()

`ExpenseResult()` receives the result as a parameter:

```kotlin
fun ExpenseResult(result: Double)
```

The parent passes the value:

```kotlin
ExpenseResult(result = result)
```

The child simply displays it:

```text
ExpenseScreen
     │
     │ result
     ↓
ExpenseResult
```

`ExpenseResult()` does not need to know where the result came from or how it was calculated.

It simply receives the value and displays it.

---

# 8. Complete State Flow

The complete flow of this project can be represented as:

```text
                         ExpenseScreen()
                              │
                ┌─────────────┼──────────────┐
                │             │              │
              amount        people         result
                │             │              │
                ↓             ↓              ↓
         ExpenseInput    PeopleInput    ExpenseResult
                │             │
                │             │
          user changes    user changes
                │             │
                └──────┬──────┘
                       ↓
                 ExpenseScreen
                       │
                       ↓
               CalculateButton
                       │
                  onCalculate()
                       │
                       ↓
               Calculate result
                       │
                       ↓
                  result state
                       │
                       ↓
                ExpenseResult
```

A simpler way to remember it is:

```text
              Parent
          ExpenseScreen()
                │
        ┌───────┼────────┐
        ↓       ↓        ↓
      Input   Button   Result
        │       │        ↑
        └───events───────┘

       State flows DOWN
       Events flow UP
```

---

# 9. What This Project Demonstrates

This project demonstrates **State Hoisting** because:

* `ExpenseScreen()` owns the state.
* `ExpenseInput()` receives the amount value.
* `PeopleInput()` receives the people value.
* `CalculateButton()` receives an action callback.
* `ExpenseResult()` receives the calculated result.
* Child composables do not directly own the shared state.
* Child composables communicate changes back to the parent through callbacks.

Therefore, the project follows the common Compose pattern:

> **State flows down, events flow up.**

---

# 10. Important Difference: State Management vs State Hoisting

These two concepts are related, but they are **not the same thing**.

### State Management

State management is about:

> **How the UI stores, observes, changes, and restores state.**

For example:

```kotlin
remember
mutableStateOf
rememberSaveable
```

### State Hoisting

State hoisting is about:

> **Where the state is stored and which composable owns and controls it.**

For example:

```text 
kotlin

ExpenseScreen()
      ↓
owns amount state
      ↓
ExpenseInput()
```

So:

```text
State Management
= Managing the state

State Hoisting
= Moving/keeping state in a higher-level composable
  so it can be shared and controlled
```

---

# 11. Key Concept to Remember

The easiest way to remember State Hoisting in Jetpack Compose is:

```text
Parent owns the state
        ↓
Parent passes state down
        ↓
Child displays the state
        ↓
Child sends events/callbacks up
        ↓
Parent changes the state
        ↓
Compose recomposes the affected UI
```

This is the main idea demonstrated by the Expense Splitter project.
