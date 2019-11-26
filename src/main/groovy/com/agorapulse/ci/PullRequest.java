package com.agorapulse.ci;

import java.util.Objects;
import java.util.Optional;

public final class PullRequest {

    private final String url;
    private final Repository source;
    private final Repository destination;
    private final String commitRange;

    public PullRequest(String url, Repository source, Repository destination, String commitRange) {
        this.url = url;
        this.source = source;
        this.destination = destination;
        this.commitRange = commitRange;
    }

    public String getUrl() {
        return url;
    }

    public Repository getSource() {
        return source;
    }

    public Repository getDestination() {
        return destination;
    }

    public Optional<String> getCommitRange() {
        return Optional.ofNullable(commitRange);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PullRequest that = (PullRequest) o;
        return Objects.equals(url, that.url) &&
            Objects.equals(source, that.source) &&
            Objects.equals(destination, that.destination) &&
            Objects.equals(commitRange, that.commitRange);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, source, destination, commitRange);
    }
}
