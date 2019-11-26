package com.agorapulse.ci;

import java.util.Objects;

public final class GitReference {

    public enum Type {
        BRANCH, TAG
    }

    private final String sha;
    private final String ref;
    private final String name;
    private final Type type;

    public GitReference(String sha, String ref, String name, Type type) {
        this.sha = sha;
        this.ref = ref;
        this.name = name;
        this.type = type;
    }

    public String getSha() {
        return sha;
    }

    public String getRef() {
        return ref;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public boolean isTag() {
        return Type.TAG.equals(type);
    }

    public boolean isBranch() {
        return Type.BRANCH.equals(type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GitReference reference = (GitReference) o;
        return Objects.equals(sha, reference.sha) &&
            Objects.equals(ref, reference.ref) &&
            Objects.equals(name, reference.name) &&
            type == reference.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sha, ref, name, type);
    }
}
