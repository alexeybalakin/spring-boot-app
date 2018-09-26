package com.ardecs.springbootapp.client;

import java.util.List;
import javax.ws.rs.*;

import com.ardecs.springbootapp.entities.Document;

import org.fusesource.restygwt.client.Attribute;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

@Path("gwt/docs")
public interface RestDocService extends RestService {
    @GET
    void list(MethodCallback<List<Document>> callback);

    @DELETE
    @Path("/{docId}")
    void delete(@PathParam("docId") @Attribute("id") Document document, MethodCallback<Void> callback);

    @PUT
    void save(Document document, MethodCallback<Document> callback);
}
