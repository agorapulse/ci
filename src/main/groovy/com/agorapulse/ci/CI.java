package com.agorapulse.ci;

import java.util.Optional;

public interface CI {

    Repository getRepository();
    GitReference getReference();
    Optional<PullRequest> getPullRequest();

    static Optional<CI> getCurrent() {
        return Factory.create();
    }

}
