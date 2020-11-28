package org.tut.micro.villain.health;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.tut.micro.villain.VillainResource;

@Liveness
@ApplicationScoped
public class PingVillainResourceHealthCheck implements HealthCheck {
	
	@Inject
	VillainResource villainResource;
	
	@Override
    public HealthCheckResponse call() {
        villainResource.hello();
        return HealthCheckResponse.named("Ping Villain REST Endpoint").up().build();
    }
	

}
