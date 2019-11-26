package com.agorapulse.ci.impl;

import com.agorapulse.ci.CI;
import com.agorapulse.ci.GitReference;
import com.agorapulse.ci.PullRequest;
import com.agorapulse.ci.Repository;

import java.util.Objects;
import java.util.Optional;

public class DefaultCI implements CI {

    private final Repository repository;
    private final GitReference reference;
    private final PullRequest pullRequest;

    public DefaultCI(Repository repository, GitReference reference, PullRequest pullRequest) {
        this.repository = repository;
        this.reference = reference;
        this.pullRequest = pullRequest;
    }

    @Override
    public Repository getRepository() {
        return repository;
    }

    @Override
    public GitReference getReference() {
        return reference;
    }

    @Override
    public Optional<PullRequest> getPullRequest() {
        return Optional.ofNullable(pullRequest);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultCI defaultCI = (DefaultCI) o;
        return Objects.equals(repository, defaultCI.repository) &&
            Objects.equals(reference, defaultCI.reference) &&
            Objects.equals(pullRequest, defaultCI.pullRequest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(repository, reference, pullRequest);
    }

}
