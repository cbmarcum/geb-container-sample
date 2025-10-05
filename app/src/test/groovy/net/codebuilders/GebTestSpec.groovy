package net.codebuilders

import geb.spock.GebSpec
import spock.lang.Shared

/**
 * See https://docs.grails.org/latest/guide/testing.html#functionalTesting and https://www.gebish.org/manual/current/
 * for more instructions on how to write functional tests with Grails and Geb.
 */

class GebTestSpec extends GebSpec {

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

}
