package org.empowrco.doctor.run.presenters

import kotlinx.coroutines.runBlocking
import org.empowrco.doctor.models.Error
import org.empowrco.doctor.models.NoValidExecutor
import org.empowrco.doctor.models.Success
import org.empowrco.doctor.run.backend.fakes.FakeRunRepo
import org.empowrco.doctor.utils.UnsupportedLanguage
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AssignmentPresenterTest {
    private val repo = FakeRunRepo()
    private val presenter = RealRunPresenter(repo)

    @AfterTest
    fun teardown() {
        repo.executorResponses.clear()
    }

    @Test
    fun runSuccess(): Unit = runBlocking {
        val output = "Hello, World"
        repo.executorResponses.add(Success(output, false))
        val response = presenter.run(
            RunRequest(
                code = "a",
                language = "",
            )
        )
        assertEquals(
            response, RunResponse(
                output = "Hello, World",
                success = true,
            )
        )
    }

    @Test
    fun runUnsupportedLanguage(): Unit = runBlocking {
        repo.executorResponses.add(NoValidExecutor("lang"))
        val exception = assertFailsWith<UnsupportedLanguage> {
            presenter.run(
                RunRequest(
                    code = "a",
                    language = "",
                )
            )
        }
        assertEquals(exception.message, "The server does not support lang")
    }

    @Test
    fun runError(): Unit = runBlocking {
        repo.executorResponses.add(Error("error"))
        val exception = assertFailsWith<Error> {
            presenter.run(
                RunRequest(
                    code = "a",
                    language = "",
                )
            )
        }
        assertEquals(exception.message, "error")
    }

}
