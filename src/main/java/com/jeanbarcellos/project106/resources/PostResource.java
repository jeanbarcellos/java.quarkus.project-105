package com.jeanbarcellos.project106.resources;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.jeanbarcellos.project106.dtos.CommentRequest;
import com.jeanbarcellos.project106.dtos.PostRequest;
import com.jeanbarcellos.project106.services.PostService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/posts")
@Tag(name = "Post handler")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PostResource {

    @Inject
    protected PostService service;

    @GET
    @Path("/")
    @Operation(summary = "Listar posts")
    public Response getAll() {
        return Response.ok(this.service.getAll()).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obter post pelo ID")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(this.service.getById(id)).build();
    }

    @POST
    @Path("/")
    @Operation(summary = "Inserir uma post")
    public Response insert(@RequestBody PostRequest request) {
        return Response.status(Status.CREATED)
                .entity(this.service.insert(request)).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Alterar uma post")
    public Response update(@PathParam("id") Long id, @RequestBody PostRequest request) {
        return Response.ok(this.service.update(request.setId(id))).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Apagar uma post")
    public Response delete(@PathParam("id") Long id) {
        this.service.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/comments")
    @Operation(summary = "Listar Comentários do post")
    public Response getAllComments(@PathParam("id") Long postId) {
        return Response.ok(this.service.getAllComments(postId)).build();
    }

    @POST
    @Path("/{id}/comments")
    @Operation(summary = "Criar Comentários no post")
    public Response insertComment(@PathParam("id") Long postId, @RequestBody CommentRequest request) {
        return Response.ok(this.service.insertComment(request.setPostId(postId))).build();
    }

    @GET
    @Path("/{id}/comments/{commentId}")
    @Operation(summary = "Obter um comentário do post")
    public Response getCommentById(@PathParam("id") Long postId, @PathParam("commentId") Long commentId) {
        return Response.ok(this.service.getCommentById(postId, commentId)).build();
    }

    @PUT
    @Path("/{id}/comments/{commentId}")
    @Operation(summary = "Alterar um comentários do post")
    public Response updateComment(@PathParam("id") Long postId, @PathParam("commentId") Long commentId,
            @RequestBody CommentRequest request) {
        return Response.ok(this.service.updateComment(request.setId(commentId).setPostId(postId))).build();
    }

    @DELETE
    @Path("/{id}/comments")
    @Operation(summary = "Apagar todos comentários do post")
    public Response delteAllComment(@PathParam("id") Long id) {
        this.service.deleteAllComments(id);
        return Response.noContent().build();
    }

}