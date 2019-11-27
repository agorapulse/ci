package com.agorapulse.ci.impl;

import com.agorapulse.ci.*;

import java.util.Map;

public class CircleCI implements Factory {

    @Override
    public CI create(Map<String, String> env) {
        if (!("true".equals(env.get("CIRCLECI")))) {
            return null;
        }

        // git@github.com:owner/name.git
        String circleRepositoryUrl = env.get("CIRCLE_REPOSITORY_URL");
        String[] repoParts = circleRepositoryUrl.split(":");
        String site = repoParts[0].split("@")[1];
        String siteUrl = "https://" + site;
        String orgAndNamePart = repoParts[1];
        orgAndNamePart = orgAndNamePart.substring(0, orgAndNamePart.length() - 4);
        String[] orgAndName = orgAndNamePart.split("/");
        String owner = orgAndName[0];
        String name = orgAndName[1];

        Repository repository = new Repository(siteUrl + "/" + owner + "/" + name, owner, name);

        String tag = env.get("CIRCLE_TAG");
        String branch = env.get("CIRCLE_BRANCH");
        String sha = env.get("CIRCLE_SHA1");

        GitReference.Type type = GitReference.Type.BRANCH;
        String ref = "refs/heads/" + branch;
        String refName = branch;

        if (tag != null && tag.length() > 0) {
            type = GitReference.Type.TAG;
            ref = "refs/tags/" + tag;
            refName = tag;
        }

        GitReference reference = new GitReference(sha, ref, refName, type);

        // https://github.com/musketyr/ci/pull/4
        String pullRequestUrl = env.get("CIRCLE_PULL_REQUEST");

        if (pullRequestUrl == null || pullRequestUrl.length() == 0) {
            return new DefaultCI(repository, reference, null);
        }

        String prOwner = env.get("CIRCLE_PR_USERNAME");
        String prRepo = env.get("CIRCLE_PR_REPONAME");

        Repository source = repository;
        if (prOwner != null && prOwner.length() > 0 ) {
            source = new Repository(siteUrl + "/" + prOwner + "/" + prRepo, prOwner, prRepo);
        }

        // https://github.com/musketyr/ci/compare/95024956047394b290ff813d575a37514baf90da...2791c9b15ed9f51a82a829ff0fea62e48b1a0c5f
        String compareUrl = env.get("CIRCLE_COMPARE_URL");
        String commitRange = compareUrl.substring(compareUrl.lastIndexOf("/") + 1);

        PullRequest pullRequest = new PullRequest(
            pullRequestUrl,
            source,
            repository,
            commitRange
        );

        return new DefaultCI(repository, reference, pullRequest);
    }

}
