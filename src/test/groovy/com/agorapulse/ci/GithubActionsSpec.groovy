package com.agorapulse.ci

import com.agorapulse.ci.impl.GithubActions

class GithubActionsSpec extends CISpec {

    void 'master branch build'() {
        given:
            loadEnv 'branch.yml'
        expect:
            CI.getCurrent().present

        when:
            CI ci = CI.getCurrent().get()

        then:
            verifyAll {
                ci instanceof GithubActions

                ci.commit == '8eee24aac46ace5cd156d351062dfce68e57e49e'
                ci.repository == new Repository(url, 'agorapulse', 'ci')

                ci.branch.present
                ci.branch.get() == 'master'

                !ci.buildNumber.present
                !ci.tag.present
            }

        when:
            GithubActions actions = ci as GithubActions

        then:
            verifyAll {
                actions.action == 'run'
                actions.actor == 'musketyr'
                actions.eventName == 'push'
                actions.eventPath == '/home/runner/work/_temp/_github_workflow/event.json'
                actions.workflow == 'Java CI'
                actions.workspace == '/home/runner/work/ci/ci'
            }
    }

}
