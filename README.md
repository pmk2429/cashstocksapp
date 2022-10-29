# Cash Stocks App

A simple app that shows a list of stocks; symbols, prices,last trade size and time of last trade for
a set of stocks parsed from the provided JSON endpoints.

## Architectural Approach

The app is built using latest jetpack libraries and architecture components. That being said, the
app follows a simple MVVM + UI State architecture to provide unidirectional data flow between from
backend API service to domain layer all the way till the UI component. The logic of what to present
to the UI stays in the ViewModel where as only the logic to drive Views behavior exists in
Fragment/Activity. The View component talks with only ViewModel and the language they prefer is
called `UiState`. UiState determines hows and whats for the Views. UiState is also the state holder
as well as the data holder. Apart from that, the app makes use of `UseCase`. UseCase as the name
suggests indicates each use case a user could possibly encounter during his/her interaction with a *
particular screen*. Each screen can have `n` number of UseCases. For us, it has only one; to fetch
the list of Stocks and display it on screen. The use cases makes use of the Repository to make the
backend call and fetch the actual data, parse it and send it back to the UseCase which inturn gets
propagated back to the ViewModel and in turn it's being
`listened` to or `collected` by the Views residing in the Fragment.

## Extra details

The app currently requires to call same end point with different scenarios and to mimic that
behavior I used a randomizer to basically get a random number until 4 (0..3) and in that way the
roulette would pick and chose which API backend service to call. The sole purpose of this detail is
to showcase the required 3 different scenarios and to give an almost real life like experience for
the tester/user. The randomizer gives the actual relevant API data fetch about 50% probability of
fetching the correct response every time where as 25% probability each for Malformed API call and
for EMPTY stocks list API call.

## Setup Requirements

- Android device or emulator
- Android Studio Arctic Fox or latest

## Getting Started -- How to run this App?

In order to get the app running yourself, you need to:

1. Clone this project ($ git clone https://github.com/pmk2429/cashstocksapp.git)
2. Import the project into Android Studio
3. Connect the android device with USB or just use your emulator
4. In Android Studio, click on the "Run" button.

## Libraries

Libraries used in the whole application are:

- [Kotlin](https://developer.android.com/kotlin) - Kotlin is a programming language that can run on
  JVM. Google has announced Kotlin as one of its officially supported programming languages in
  Android Studio; and the Android community is migrating at a pace from Java to Kotlin
- [Jetpack](https://developer.android.com/jetpack) - Jetpack is a suite of libraries to help
  developers follow best practices, reduce boilerplate code, and write code that works consistently
  across Android versions and devices so that developers can focus on the code they care about. Get
  started using Jetpack
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) -The ViewModel
  class is designed to store and manage UI-related data in a lifecycle conscious way
- [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow) - Flow APIs that
  enable flows to optimally emit state updates and emit values to multiple consumers.
- [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines) - A concurrency design
  pattern that you can use on Android to simplify code that executes asynchronously
- [Retrofit](https://square.github.io/retrofit) - Retrofit is a REST Client for Java and Android by
  Square inc under Apache 2.0 license. Its a simple network library that used for network
  transactions. By using this library we can seamlessly capture JSON response from web service/web
  API.
- [UseCases](https://developer.android.com/topic/architecture/domain-layer) - The domain layer is
  responsible for encapsulating complex business logic, or simple business logic that is reused by
  multiple ViewModels. This layer is optional because not all apps will have these requirements. You
  should only use it when needed-for example, to handle complexity or favor reusability.
- [DataBinding](https://developer.android.com/topic/libraries/data-binding) - A support library that
  allows you to bind UI components in your layouts to data sources in your app using a declarative
  format rather than programmatically
- [State Holders and UiState](https://developer.android.com/topic/architecture/ui-layer/stateholders)
