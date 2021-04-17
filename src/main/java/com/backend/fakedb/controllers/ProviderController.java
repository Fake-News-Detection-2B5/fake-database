package com.backend.fakedb.controllers;

import com.backend.fakedb.entities.ProviderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.backend.fakedb.services.ProviderService;

import java.util.ArrayList;
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
    @GetMapping(path = "/getProviders")
    public List<ProviderEntity> getProviders() {
        return providerService.getAll();
    }

    /**
     * Public method for requesting a specific provider
     * @param id ID to search for
     * @return if found, the ProviderEntity is returned. Else, return null.
     */
    @GetMapping(path = "/getProviders?id={id}")
    public ProviderEntity getById(@PathVariable Integer id) {
        return providerService.getById(id);
    }

    /**
     * Public method for requesting a specific provider
     * @param name name to search for
     * @return if found, the ProviderEntity is returned. Else, return null.
     */
    @GetMapping(path = "/getProviders?name={name}")
    public ProviderEntity getByName(@PathVariable String name) {
        return providerService.getByName(name);
    }

    /**
     * Public method for requesting a specific number of providers
     * @param s used for skipping a number of rows from the database
     * @param c how many rows are necessary
     * @return a List with the ProviderEntity requested
     */
    @GetMapping(path = "/getProviders?skip={s}&count={c}")
    public List<ProviderEntity> getWithCount(@PathVariable int s, @PathVariable int c) {
        return providerService.getWithCount(s, c);
    }

    /**
     * Public method which adds a new provider in the database
     * @param providerEntity the ProviderEntity received as a JSON
     */
    @PostMapping(path = "/addProvider")
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
    @PutMapping(path = "/updateProvider?id={idToUpdate}&name={newName}&credibility={newCredibility}&avatar={newAvatar}")
    public void updateProvider(@PathVariable Integer idToUpdate, @PathVariable String newName, @PathVariable Double newCredibility, @PathVariable String newAvatar) {
        providerService.updateProvider(idToUpdate, newName, newCredibility, newAvatar);
    }

    /**
     * Public method which deletes an existing provider
     * @param idToDelete the id of the provider to be deleted
     */
    @DeleteMapping(path = "/deleteProvider?id={idToDelete}")
    public void deleteProvider(@PathVariable Integer idToDelete) {
        providerService.deleteProvider(idToDelete);
    }

    /**
     * Dummy method for fake adding fake data to the database.
     */
    @PostMapping
    public void dummy() {
        for (int i = 1; i < 10; i++) {
            providerService.addProvider(new ProviderEntity(i, "a" + (10 - i), 10 + i, "b" + i));
        }
    }
}
