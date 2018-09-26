package com.ardecs.springbootapp.server.ws;

import java.util.List;

import com.ardecs.springbootapp.entities.Document;
import com.ardecs.springbootapp.server.services.DocService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
public class DocRestfulController {

    @Autowired
    DocService docService;

    @GetMapping(path = "/gwtApp/gwt/docs")
    List<Document> list() {
        return docService.list();
    }

    @DeleteMapping("/gwtApp/gwt/docs/{docId}")
    void delete(@PathVariable("docId") Long docId) {
        docService.delete(docId);
    }

    @PutMapping("/gwtApp/gwt/docs")
    void save(@RequestBody Document document) {
        docService.save(document);
    }

}
