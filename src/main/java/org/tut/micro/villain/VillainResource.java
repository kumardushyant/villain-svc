package org.tut.micro.villain;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.jboss.logging.Logger;
import org.tut.micro.villain.data.Villain;

@Path("/villain")
@Produces(MediaType.APPLICATION_JSON)
public class VillainResource {

	private static final Logger LOGGER = Logger.getLogger(VillainResource.class);

    @Inject
    VillainService service;

    @Counted(name = "countGetRandomVillain", description = "Counts how many times the getRandomVillain method has been invoked")
    @Timed(name = "timeGetRandomVillain", description = "Times how long it takes to invoke the getRandomVillain method", unit = MetricUnits.MILLISECONDS)
    @GET
    @Path("/random")
    public Response getRandomVillain() {
        Villain villain = service.findRandomVillain();
        LOGGER.info("Found random villain " + villain);
        return Response.ok(villain).build();
    }

    @Counted(name = "countGetAllVillains", description = "Counts how many times the getAllVillains method has been invoked")
    @Timed(name = "timeGetAllVillains", description = "Times how long it takes to invoke the getAllVillains method", unit = MetricUnits.MILLISECONDS)
    @GET
    public Response getAllVillains() {
        List<Villain> villains = service.findAllVillains();
        LOGGER.debug("Total number of villains " + villains);
        return Response.ok(villains).build();
    }

    @Counted(name = "countGetVillain", description = "Counts how many times the getVillain method has been invoked")
    @Timed(name = "timeGetVillain", description = "Times how long it takes to invoke the getVillain method", unit = MetricUnits.MILLISECONDS)
    @GET
    @Path("/{id}")
    public Response getVillain(@PathParam("id") Long id) {
        Villain villain = service.findVillainById(id);
        if (villain != null) {
            LOGGER.debug("Found villain " + villain);
            return Response.ok(villain).build();
        } else {
            LOGGER.debug("No villain found with id " + id);
            return Response.noContent().build();
        }
    }

    @Counted(name = "countCreateVillain", description = "Counts how many times the createVillain method has been invoked")
    @Timed(name = "timeCreateVillain", description = "Times how long it takes to invoke the createVillain method", unit = MetricUnits.MILLISECONDS)
    @POST
    public Response createVillain(@Valid Villain villain, @Context UriInfo uriInfo) {
        villain = service.persistVillain(villain);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Long.toString(villain.id));
        LOGGER.debug("New villain created with URI " + builder.build().toString());
        return Response.created(builder.build()).build();
    }

    @Counted(name = "countUpdateVillain", description = "Counts how many times the updateVillain method has been invoked")
    @Timed(name = "timeUpdateVillain", description = "Times how long it takes to invoke the updateVillain method", unit = MetricUnits.MILLISECONDS)
    @PUT
    public Response updateVillain(@Valid Villain villain) {
        villain = service.updateVillain(villain);
        LOGGER.debug("Villain updated with new valued " + villain);
        return Response.ok(villain).build();
    }

    @Counted(name = "countDeleteVillain", description = "Counts how many times the deleteVillain method has been invoked")
    @Timed(name = "timeDeleteVillain", description = "Times how long it takes to invoke the deleteVillain method", unit = MetricUnits.MILLISECONDS)
    @DELETE
    @Path("/{id}")
    public Response deleteVillain(@PathParam("id") Long id) {
        service.deleteVillain(id);
        LOGGER.debug("Villain deleted with " + id);
        return Response.noContent().build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/hello")
    public String hello() {
        return "hello";
    }
    
}