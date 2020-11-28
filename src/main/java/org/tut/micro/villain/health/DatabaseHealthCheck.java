/**
 * 
 */
package org.tut.micro.villain.health;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;
import org.tut.micro.villain.VillainService;
import org.tut.micro.villain.data.Villain;

@Readiness
@ApplicationScoped
public class DatabaseHealthCheck implements HealthCheck {

    @Inject
    VillainService villainService;


    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse
                .named("Hero Datasource connection health check");

        try {
            List<Villain> heroes = villainService.findAllVillains();
            responseBuilder.withData("Number of villains in the database", heroes.size()).up();
        } catch (IllegalStateException e) {
            responseBuilder.down();
        }

        return responseBuilder.build();
    }
}