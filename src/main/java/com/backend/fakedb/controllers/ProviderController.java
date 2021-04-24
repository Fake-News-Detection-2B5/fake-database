package com.backend.fakedb.controllers;

import com.backend.fakedb.entities.ProviderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.backend.fakedb.services.ProviderService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = "provider")
public class ProviderController {

    private final ProviderService providerService;

    @Autowired
    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    /**
     * Public method for requesting all providers from the database.
     * @return a List with every ProviderEntity from the database
     */
    @GetMapping(path = "/getAll")
    public List<ProviderEntity> getProviders() {
        return providerService.getAll();
    }

    /**
     * Public method for requesting a specific provider
     * @param id ID to search for
     * @return if found, the ProviderEntity is returned. Else, return null.
     */
    @GetMapping(path = "/getById")
    public ProviderEntity getById(@RequestParam(name = "id", required = true) Integer id) {
        return providerService.getById(id);
    }


    /**
     * Public method for requesting a specific provider
     * @param name name to search for
     * @return if found, the ProviderEntity is returned. Else, return null.
     */
    @GetMapping(path = "/getByName")
    public ProviderEntity getByName(@RequestParam(name = "name", required = true) String name) {
        return providerService.getByName(name);
    }

    /**
     * Public method for requesting a specific number of providers
     * @param s used for skipping a number of rows from the database
     * @param c how many rows are necessary
     * @return a List with the ProviderEntity requested
     */
    @GetMapping(path = "/getInterval")
    public List<ProviderEntity> getInterval(@RequestParam(name = "skip", required = true) int s, @RequestParam(name = "count", required = true) int c) {
        return providerService.getInterval(s, c);
    }

    @GetMapping(path = "/getCount")
    public int getCount() {
        return providerService.getCount();
    }

    /**
     * Public method for requesting the number of providers containing the provided string
     * @param query used for providing the string to be searched within the names of the providers
     * @return an integer
     */
    @GetMapping(path = "/searchCount")
    public int searchCount(@RequestParam(name = "query", required = true) String query) {
        return providerService.searchCount(query);
    }

    /**
     * Public method for requesting a list of providers containing the provided string
     * @param query used for providing the string to be searched within the names of the providers
     * @param s used for skipping a number of rows from the database
     * @param c how many rows are necessary
     * @return a List with the ProviderEntity requested
     */
    @GetMapping(path = "/search")
    public List<ProviderEntity> search(@RequestParam(name = "query", required = true) String query, @RequestParam(name = "skip", required = true) int s, @RequestParam(name = "count", required = true) int c) {
        return providerService.search(query, s, c);
    }

    /**
     * Public method which adds a new provider in the database
     * @param providerEntity the ProviderEntity received as a JSON
     */
    @PostMapping(path = "/add")
    public void addProvider(@RequestBody ProviderEntity providerEntity) {
        providerService.addProvider(providerEntity);
    }

    /**
     * Public method which updates an already existing provider. If it does not exist, the function does not update anything.
     * @param idToUpdate the provider's ID which will be overwritten
     * @param newName the new name of the provider (if it is empty it will remain the same)
     * @param newCredibility the new credibility of the provider (if it is empty it will remain the same)
     * @param newAvatar the new avatar URL of the provider (if it is empty it will remain the same)
     */
    @PutMapping(path = "/update")
    public void updateProvider(@RequestParam(name = "id", required = true) Integer idToUpdate,
                               @RequestParam(name = "name", required = false, defaultValue = "") String newName,
                               @RequestParam(name = "credibility", required = false, defaultValue = "") Double newCredibility,
                               @RequestParam(name = "avatar", required = false, defaultValue = "") String newAvatar) {
        providerService.updateProvider(idToUpdate, newName, newCredibility, newAvatar);
    }

    /**
     * Public method which deletes an existing provider
     * @param idToDelete the id of the provider to be deleted
     */
    @DeleteMapping(path = "/delete")
    public void deleteProvider(@RequestParam(name = "id", required = true) Integer idToDelete) {
        providerService.deleteProvider(idToDelete);
    }

    /**
     * Dummy method for fake adding fake data to the database.
     */
    @PostMapping("/dummy")
    public void dummy() {
        for (int i = 1; i < 10; i++) {
            providerService.addProvider(new ProviderEntity(i, "a" + (10 - i), 10 + i, "b" + i));
        }
    }
}
