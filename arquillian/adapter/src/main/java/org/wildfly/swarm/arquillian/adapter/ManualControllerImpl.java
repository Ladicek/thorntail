package org.wildfly.swarm.arquillian.adapter;

import org.jboss.arquillian.container.spi.Container;
import org.jboss.arquillian.container.spi.ContainerRegistry;
import org.jboss.arquillian.container.spi.client.container.DeployableContainer;
import org.jboss.arquillian.container.spi.client.deployment.Deployment;
import org.jboss.arquillian.container.spi.client.deployment.DeploymentScenario;
import org.jboss.arquillian.container.test.api.Deployer;
import org.wildfly.swarm.arquillian.ManualController;

import java.util.List;

public final class ManualControllerImpl implements ManualController {
    private final Deployer deployer;
    private final Deployment deployment;
    private final WildFlySwarmContainer container;

    public ManualControllerImpl(Deployer deployer, DeploymentScenario deploymentScenario, ContainerRegistry containerRegistry) {
        this.deployer = deployer;

        List<Deployment> deployments = deploymentScenario.deployments();
        if (deployments.size() != 1) {
            throw new IllegalStateException("No deployment or more than one deployment present");
        }
        this.deployment = deployments.get(0);

        WildFlySwarmContainer wildFlySwarmContainer = null;
        for (Container container : containerRegistry.getContainers()) {
            DeployableContainer<?> deployableContainer = container.getDeployableContainer();
            if (deployableContainer instanceof WildFlySwarmContainer) {
                if (wildFlySwarmContainer == null) {
                    wildFlySwarmContainer = (WildFlySwarmContainer) deployableContainer;
                } else {
                    throw new IllegalStateException("More than one WildFlySwarmContainer present");
                }
            }
        }
        if (wildFlySwarmContainer == null) {
            throw new IllegalStateException("No WildFlySwarmContainer present");
        }
        this.container = wildFlySwarmContainer;
    }

    @Override
    public void start() {
        deployer.deploy(deployment.getDescription().getName());
    }

    @Override
    public void stop() {
        deployer.undeploy(deployment.getDescription().getName());
    }

    @Override
    public void kill() {
        // TODO
    }
}
