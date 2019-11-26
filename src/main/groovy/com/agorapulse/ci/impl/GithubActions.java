package com.agorapulse.ci.impl;

import com.agorapulse.ci.CI;
import com.agorapulse.ci.Repository;

import java.util.Optional;

public enum GithubActions {

    INSTANCE;

    public Repository getRepository() {
        return Repository.parseSlug(System.getenv("GITHUB_REPOSITORY"));
    }

    public String getCommit() {
        return System.getenv("GITHUB_SHA");
    }

    public Optional<String> getBuildNumber() {
        return Optional.empty();
    }

    public Optional<String> getBranch() {
        return Optional.ofNullable(extractFromRef("refs/heads/"));
    }

    public Optional<String> getTag() {
        return Optional.ofNullable(extractFromRef("refs/tags/"));
    }

    private String extractFromRef(String prefix) {
        String ref = System.getenv("GITHUB_REF");
        if (ref.startsWith(prefix)) {
            return ref.substring(prefix.length());
        }
        return null;
    }

}
