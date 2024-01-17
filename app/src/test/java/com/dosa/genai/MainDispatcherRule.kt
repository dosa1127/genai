package com.dosa.genai

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 *
 *     @get:Rule
 *     val mainDispatcherRule = MainDispatcherRule()
 *
 *     To make sure that there’s only one scheduler in your test,
 *     create the MainDispatcherRule property first.
 *     Then reuse its dispatcher
 *     (or its scheduler, if you need a TestDispatcher of a different type)
 *     in the initializers of other class-level properties as needed.
 */
class MainDispatcherRule @OptIn(ExperimentalCoroutinesApi::class) constructor(
    val dispatcher: TestDispatcher = StandardTestDispatcher(),
) : TestWatcher() {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun starting(description: Description) {
        Dispatchers.setMain(dispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}