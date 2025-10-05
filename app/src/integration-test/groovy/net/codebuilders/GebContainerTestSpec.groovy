package net.codebuilders

import grails.plugin.geb.ContainerGebConfiguration
import grails.plugin.geb.ContainerGebSpec

/**
 * See https://docs.grails.org/latest/guide/testing.html#functionalTesting and https://www.gebish.org/manual/current/
 * for more instructions on how to write functional tests with Grails and Geb.
 */
@ContainerGebConfiguration(reporting = true)
class GebContainerTestSpec extends ContainerGebSpec {

    static TestFileServer server

    def setupSpec() {
        server = new TestFileServer()
        server.start(8080)
    }

    def "the home page title is correct"() {
        when: "go to localhost"
        go "/"

        then: "the page title should be correct"
        title == "Hello Geb"
    }

    def "the home page heading is correct"() {
        when: "go to localhost"
        go "/"

        then: "the welcome header should be displayed"
        $("h1").text() == "Welcome to the Geb/Spock Test"
    }

    def cleanup() {
        // give the vnc container time to copy the video
        sleep(1000)
    }

    def cleanupSpec() {
        server.stop(0)
    }

}
