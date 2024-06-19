package com.github.hannotify;

import io.quarkus.resteasy.reactive.links.InjectRestLinks;
import io.quarkus.resteasy.reactive.links.RestLink;
import io.quarkus.resteasy.reactive.links.RestLinkType;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.common.util.RestMediaType;

import java.net.URI;
import java.util.List;

@Path("/guitars")
public class GuitarResource {
    @GET
    @RestLink(rel = "list")
    @InjectRestLinks
    @Produces({ MediaType.APPLICATION_JSON, RestMediaType.APPLICATION_HAL_JSON })
    public List<Guitar> list() {
        return Guitar.listAll();
    }

    @GET
    @Path("/{id}")
    @RestLink(rel = "self")
    @InjectRestLinks
    @Produces({ MediaType.APPLICATION_JSON, RestMediaType.APPLICATION_HAL_JSON })
    public Guitar get(@PathParam("id") Long id) {
        return Guitar.findById(id);
    }

    @POST
    @Transactional
    public Response create(Guitar guitar) {
        guitar.persist();
        return Response.created(URI.create("/guitars/" + guitar.id)).build();
    }

    @PUT
    @Transactional
    @RestLink(rel = "update")
    @InjectRestLinks(RestLinkType.INSTANCE)
    @Produces({ MediaType.APPLICATION_JSON, RestMediaType.APPLICATION_HAL_JSON })
    @Path("/{id}")
    public Guitar update(@PathParam("id") Long id, Guitar updatedGuitar) {
        Guitar guitar = Guitar.findById(id);

        if (guitar == null) {
            throw new NotFoundException();
        }

        guitar.make = updatedGuitar.make;
        guitar.model = updatedGuitar.model;

        return guitar;
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        var guitar = Guitar.findById(id);

        if (guitar == null) {
            throw new NotFoundException();
        }
        guitar.delete();
    }
}
