package com.jeanbarcellos.project106.resources;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.jeanbarcellos.project106.dtos.PersonRequest;
import com.jeanbarcellos.project106.services.PersonService;

@Path("/people")
@Tag(name = "Manutenção de Pessoa")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {

    @Inject
    protected PersonService service;

    @GET
    @Path("/")
    @Operation(summary = "Listar pessoas físicas")
    public Response getAll() {
        return Response.ok(this.service.getAll()).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obter pessoa pelo ID")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(this.service.getById(id)).build();
    }

    @POST
    @Path("/")
    @Operation(summary = "Inserir uma pessoa")
    public Response insert(@RequestBody PersonRequest request) {
        return Response.status(Status.CREATED)
                .entity(this.service.insert(request)).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Alterar uma pessoa")
    public Response update(@PathParam("id") Long id, @RequestBody PersonRequest request) {
        return Response.ok(this.service.update(request.setId(id))).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Apagar uma pessoa")
    public Response delete(@PathParam("id") Long id) {
        this.service.delete(id);
        return Response.noContent().build();
    }

}