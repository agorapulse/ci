package com.agorapulse.ci;

import java.util.Objects;

public final class Repository {

    public static Repository parseSlug(String slug) {
        String[] parts = slug.split("/");
        if (parts.length == 2) {
            return new Repository(null, parts[0], parts[1]);
        }
        return new Repository(null, null, slug);
    }

    private final String url;
    private final String owner;
    private final String name;

    public Repository(String url, String owner, String name) {
        this.url = url;
        this.owner = owner;
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Repository that = (Repository) o;
        return Objects.equals(url, that.url) &&
            Objects.equals(owner, that.owner) &&
            Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, owner, name);
    }
}
