package com.github.hannotify;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/guitars")
public class GuitarResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getGuitars() {
        return "This will be replaced by a list of guitars... soon!";
    }
}
