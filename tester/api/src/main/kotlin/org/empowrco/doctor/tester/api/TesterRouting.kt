package org.empowrco.doctor.tester.api

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.empowrco.doctor.tester.presenters.ExecuteTestRequest
import org.empowrco.doctor.tester.presenters.TesterPresenter
import org.koin.ktor.ext.inject

fun Application.testerRouting() {
    val presenter: TesterPresenter by inject()
    routing {
        get("test") {
            val result = presenter.test(
                ExecuteTestRequest(
                    language = "swift",
                    tests = """import XCTest
@testable import trials

final class trialsTests: XCTestCase {
    func testExample() throws {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct
        // results.
        XCTAssertEqual(trials().text, "Hello, Worlfd!")
    }
    
    func testMe() throws {
        XCTAssertEqual(tester(), 0)
    }
}
""",
                    code = """public struct trials {
    public private(set) var text = "Hello, World!"

    public init() {
    }
}


func tester() -> Int {
    return 0
}
"""
                )
            )
            call.respond(result)
        }
    }
}
