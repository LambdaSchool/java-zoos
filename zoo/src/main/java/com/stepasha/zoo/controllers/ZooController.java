package com.stepasha.zoo.controllers;

import com.stepasha.zoo.models.Zoo;
import com.stepasha.zoo.services.ZooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "/zoos")
public class ZooController
{
    @Autowired
    private ZooService zooService;
   // http://localhost:2020/zoos/zoos
    @GetMapping(value = "/zoos", produces = {"application/json"})
    public ResponseEntity<?> listAllZoos()
    {
        return new ResponseEntity<>(zooService.findAllZoos(), HttpStatus.OK);
    }
    // http://localhost:2020/zoos/zoos/1
    @GetMapping(value = "/zoos/{id}", produces = {"application/json"})
    public ResponseEntity<?> findZooById(@PathVariable long id)
    {
        Zoo z = zooService.findZooById(id);
        return new ResponseEntity<>(z, HttpStatus.OK);
    }

    // http://localhost:2020/zoos/zoos/name
    @GetMapping(value = "/{name}", produces = {"application/json"})
    public ResponseEntity<?> findZooByName(@PathVariable String name)
    {
        Zoo z = zooService.findZooByName(name);
        return new ResponseEntity<>(z, HttpStatus.OK);
    }
    @PutMapping(value = "/zoos/{id}",
            produces = {"application/json"},
            consumes = {"application/json"})
    public ResponseEntity<?> updateZoo(
            @RequestBody
                    Zoo updateZoo,
            @PathVariable
                    long id)
    {
        zooService.updateZoo(updateZoo, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/zoos",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> addNewZoo(HttpServletRequest request, @Valid
    @RequestBody
            Zoo newZoo) throws URISyntaxException
    {
        newZoo = zooService.saveZoo(newZoo);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        // URI newRestaurantURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{zooid}").buildAndExpand(newZoo.getZooid()).toUri();
        URI newRestaurantURI = ServletUriComponentsBuilder.fromUriString(request.getServerName() + ":" + request.getLocalPort() + "/zoos/zoos/{zooid}").buildAndExpand(newZoo.getZooid()).toUri();
        responseHeaders.setLocation(newRestaurantURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }


    @DeleteMapping(value = "/zoos/{zooid}")
    public ResponseEntity<?> deleteZooById(
            @PathVariable
                    long zooid)
    {
        zooService.deleteZoo(zooid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

// @DeleteMapping(value = "/zoos/{zooid}/animals/{animalid}")
// public ResponseEntity<?> deleteZooAnimalCombo(
//         @PathVariable("zooid")
//                 long zooid,
//         @PathVariable("animalid")
//                 long animalid)
// {
//     zooService.deleteZooAnimalCombo(zooid, animalid);

//     return new ResponseEntity<>(HttpStatus.OK);
// }
}
