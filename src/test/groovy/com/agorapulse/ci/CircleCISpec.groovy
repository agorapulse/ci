package com.agorapulse.ci

class CircleCISpec extends CISpec {

    void 'master branch build'() {
        given:
            loadEnv 'branch.yml'
        expect:
            CI.getCurrent().present

        when:
            CI ci = CI.getCurrent().get()

        then:
            verifyAll {
                ci.repository
                ci.repository.url == 'https://github.com/musketyr/ci'
                ci.repository.owner == 'musketyr'
                ci.repository.name == 'ci'

                ci.reference
                ci.reference.name == 'master'
                ci.reference.ref == 'refs/heads/master'
                ci.reference.sha == 'fa4d680a53ea9a6a74c3a9bbd7e441aeff5467a4'
                ci.reference.type == GitReference.Type.BRANCH
                ci.reference.branch

                !ci.pullRequest.present
            }
    }

    void 'tag build'() {
        given:
            loadEnv 'tag.yml'
        expect:
            CI.getCurrent().present

        when:
            CI ci = CI.getCurrent().get()

        then:
            verifyAll {
                ci.repository
                ci.repository.url == 'https://github.com/musketyr/ci'
                ci.repository.owner == 'musketyr'
                ci.repository.name == 'ci'

                ci.reference
                ci.reference.name == 'test-09'
                ci.reference.ref == 'refs/tags/test-09'
                ci.reference.sha == '95024956047394b290ff813d575a37514baf90da'
                ci.reference.type == GitReference.Type.TAG
                ci.reference.tag

                !ci.pullRequest.present
            }
    }

    void 'pr build'() {
        given:
            loadEnv 'pr.yml'
        expect:
            CI.getCurrent().present

        when:
            CI ci = CI.getCurrent().get()

        then:
            verifyAll {
                ci.repository
                ci.repository.url == 'https://github.com/musketyr/ci'
                ci.repository.owner == 'musketyr'
                ci.repository.name == 'ci'

                ci.reference
                ci.reference.name == 'fast-pr'
                ci.reference.ref == 'refs/heads/fast-pr'
                ci.reference.sha == '8c8c79ea40eab229e8dbf980e3eaf0aa575b7c96'
                ci.reference.type == GitReference.Type.BRANCH
                ci.reference.branch

                ci.pullRequest.present
            }

        when:
            PullRequest pr = ci.pullRequest.get()
        then:
            pr.source == ci.repository
            pr.destination == ci.repository
            pr.url == 'https://github.com/musketyr/ci/pull/8'
            pr.commitRange.present
            pr.commitRange.get() == '313d8f838ce12732f604008ea8ce26afe567b5ef...8c8c79ea40eab229e8dbf980e3eaf0aa575b7c96'
    }

    void 'pr fork build'() {
        given:
            loadEnv 'fork.yml'
        expect:
            CI.getCurrent().present

        when:
            CI ci = CI.getCurrent().get()

        then:
            verifyAll {
                ci.repository
                ci.repository.url == 'https://github.com/musketyr/ci'
                ci.repository.owner == 'musketyr'
                ci.repository.name == 'ci'

                ci.reference
                ci.reference.name == 'pull/4'
                ci.reference.ref == 'refs/heads/pull/4'
                ci.reference.sha == '2791c9b15ed9f51a82a829ff0fea62e48b1a0c5f'
                ci.reference.type == GitReference.Type.BRANCH
                ci.reference.branch

                ci.pullRequest.present
            }

        when:
            PullRequest pr = ci.pullRequest.get()
        then:
            pr.source
            pr.source.url == 'https://github.com/dsl-builders/ci'
            pr.source.owner == 'dsl-builders'
            pr.source.name == 'ci'
            pr.destination == ci.repository
            pr.url == 'https://github.com/musketyr/ci/pull/4'
            pr.commitRange.present
            pr.commitRange.get() == '95024956047394b290ff813d575a37514baf90da...2791c9b15ed9f51a82a829ff0fea62e48b1a0c5f'
    }

}
