package org.wildfly.swarm.arquillian;

public interface ManualController {
    void start();

    void stop();

    void kill();
}
