package org.wildfly.swarm.arquillian.adapter.resources;

import org.jboss.arquillian.container.spi.ContainerRegistry;
import org.jboss.arquillian.container.spi.client.deployment.DeploymentScenario;
import org.jboss.arquillian.container.test.api.Deployer;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.test.spi.enricher.resource.ResourceProvider;
import org.wildfly.swarm.arquillian.ManualController;
import org.wildfly.swarm.arquillian.adapter.ManualControllerImpl;

import java.lang.annotation.Annotation;

public class ManualControllerProvider implements ResourceProvider {
    @Inject
    private Instance<Deployer> deployer;

    @Inject
    private Instance<ContainerRegistry> containerRegistry;

    @Inject
    private Instance<DeploymentScenario> deploymentScenario;

    @Override
    public boolean canProvide(Class<?> type) {
        return ManualController.class.isAssignableFrom(type);
    }

    @Override
    public Object lookup(ArquillianResource resource, Annotation... qualifiers) {
        return new ManualControllerImpl(deployer.get(), deploymentScenario.get(), containerRegistry.get());
    }
}
