package net.codebuilders

import grails.plugin.geb.ContainerGebConfiguration
import grails.plugin.geb.ContainerGebSpec
import spock.lang.Shared


/**
 * See https://docs.grails.org/latest/guide/testing.html#functionalTesting and https://www.gebish.org/manual/current/
 * for more instructions on how to write functional tests with Grails and Geb.
 */
@ContainerGebConfiguration(reporting = true)
class GebContainerTestSpec extends ContainerGebSpec {

    @Shared
    serverPort = 8080

    void 'should display the correct title on the home page'() {
        when: 'visiting the home page'
            go('/')

        then: 'the page title is correct'
            title == 'Welcome to Grails'
    }

    void 'should display the correct heading on the home page'() {
        when: 'visiting the home page'
        go('/')

        then: 'the heading is correct'
        $("h1").text() == 'Welcome to Grails'
    }

}
