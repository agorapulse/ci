package com.agorapulse.ci;

import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;

public interface Factory {

    CI create(Map<String, String> env);

    static Optional<CI> create() {
        ServiceLoader<Factory> loader = ServiceLoader.load(Factory.class);

        for (Factory f : loader) {
            Map<String, String> envs = System.getenv();
            CI ci = f.create(envs);
            if (ci != null) {
                return Optional.of(ci);
            }
        }

        return Optional.empty();
    }

}
