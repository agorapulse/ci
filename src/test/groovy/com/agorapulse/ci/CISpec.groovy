package com.agorapulse.ci

import com.agorapulse.testing.fixt.Fixt
import org.junit.Rule
import org.junit.contrib.java.lang.system.EnvironmentVariables
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

abstract class CISpec extends Specification {

    @Rule EnvironmentVariables env = new EnvironmentVariables()
    @Rule Fixt fixt = Fixt.create(getClass())

    void setup() {
        Set<String> variableNames = new LinkedHashSet<>(System.getenv().keySet())
        variableNames.each { env.set(it, null)}
    }

    protected void load(String filename) {
        Yaml yaml = new Yaml()

        InputStream stream = fixt.readStream(filename)

        assert stream

        yaml.load(stream).each {
            env.set(it.key, it.value?.toString())
        }
    }
}
