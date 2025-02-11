package com.example.composepractice

import androidx.compose.runtime.compositionLocalOf

/*
    So why do this?
    -   tldr:
        - Eliminates unnecessary prop-drilling
        - Centralizes ViewModel management
        - Improves code maintainability
        - Supports Jetpack Compose’s scoped hierarchy
        - Makes multi-ViewModel injection easier

    1.  centralized viewmodel access
        -   by defining LocalViewModel as a CompositionLocal provider, you ensure that any Composable
            within the hierarchy can access the ViewModel without *prop-drilling.
        -   prop-drilling: also known as "threading" (seems confusing), happens when data is passed
            down through multiple levels of a component hierarchy, even if some intermediate
            components don't directly use it. When using Jetpack Compose, data might be passed as
            parameters through several nested composable functions before reaching the composable
            that actually needs it. This is generally considered a bad practice.

        -   without CompositionLocal, the ViewModel would need to be manually passed through the
            function parameters, making the code harder to maintain and reducing readability:

            @Composable
            fun Screen(viewModel: MyViewModel) {
                Content(viewModel)
            }
            @Composable
            fun Content(viewModel: MyViewModel) {
                Button(onClick = { viewModel.sendEvent("Hello!") }) {
                    Text("Click me")
                }
            }

        -   with CompositionLocal as used in this class, any Composable can retrieve the ViewModel
            via LocalViewModel.current.

    2.  aligns with Jetpack Compose Scoped Hierarchy
        -   Jetpack Compose works by building a Composable tree, meaning child Composables inherit
            values from their ancestors. By wrapping the ViewModel in a CompositionLocalProvider,
            all child Composables inside of AppContent() can retrieve LocalViewModel.current
            without explicitly passing it:

            @Composable
            fun MainScreen() {
                val viewModel: MyViewModel = viewModel()

                CompositionLocalProvider(LocalViewModel provides viewModel) {
                    AppContent()
                }
            }

    3.  enables multi-viewmodel support
        -   if an app has multiple ViewModels, by using CompositionLocalProvider multiple
            ViewModels can be injected efficiently:

            CompositionLocalProvider(
                LocalUserViewModel provides userViewModel,
                LocalSettingsViewmodel provides settingsViewModel
            ) {
                AppContent()
            }

    **Extra Credit**
    -   using LocalViewModel isn't necessary. If the app has a single ViewModel and it isn't
        a burden to pass it explicitly, then that is a viable option. However, it becomes very
        useful when your app has deeply nested UI components that need access to ViewModel state.
        You may also have multiple ViewModels and want a cleaner way to manage dependencies.
    -   this approach also provides flexibility when making a future switch to Hilt or another DI
        approach. This is done by abstracting the ViewModel provision mechanism, meaning the
        ViewModel creation logic can easily be switched out without modifying every Composable that
        depends on it.
 */

val LocalViewModel = compositionLocalOf<MyViewModel> {
    error("No ViewModel provided")
}