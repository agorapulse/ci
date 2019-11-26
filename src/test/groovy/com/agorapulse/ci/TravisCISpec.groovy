package com.agorapulse.ci

import spock.lang.Ignore

class TravisCISpec extends CISpec {

    @Ignore
    void 'master branch build'() {
        given:
            loadEnv 'branch.yml'
        expect:
            CI.getCurrent().present

        when:
            CI ci = CI.getCurrent().get()

        then:
            verifyAll {

                ci.commit == '807d24b230d3f33832a7c472b84150a0299e6365'
                ci.repository == new Repository(url, 'agorapulse', 'ci')

                ci.branch.present
                ci.branch.get() == 'master'

                ci.buildNumber.present
                ci.buildNumber.get() == '1'

                !ci.tag.present
            }

    }

}
