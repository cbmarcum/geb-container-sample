package net.codebuilders

import grails.plugin.geb.ContainerGebConfiguration
import grails.plugin.geb.ContainerGebSpec
import org.testcontainers.Testcontainers
import spock.lang.Shared


/**
 * See https://docs.grails.org/latest/guide/testing.html#functionalTesting and https://www.gebish.org/manual/current/
 * for more instructions on how to write functional tests with Grails and Geb.
 */
// @org.testcontainers.spock.Testcontainers()
@ContainerGebConfiguration(reporting = true)
class GebContainerTestSpec extends ContainerGebSpec {

    @Shared
    serverPort = 8080

    void 'should display the correct title on the home page'() {
        when: 'visiting the home page'
            go('https://groovy.apache.org/geb/')

        then: 'the page title is correct'
            title == 'Geb - Very Groovy Browser Automation'
    }


    void 'should display the correct title on the docs page'() {
        when: 'visiting the home page'
        go('https://groovy.apache.org/geb/manual/snapshot/')

        then: 'the title is correct'
        title == 'The Book Of Geb'
    }

    void 'should display the correct title with a grails app'() {
        when: 'visiting the home page'
        go('/')

        then: 'the title is correct'
        title == 'Welcome to Grails'
    }

    def cleanup() {

    }

}
