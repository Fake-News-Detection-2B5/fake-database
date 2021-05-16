package com.backend.fakedb.controllers;

import com.backend.fakedb.entities.ProviderEntity;
import com.backend.fakedb.services.ProviderService;
import com.backend.fakedb.utilities.IntWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "provider")
public class ProviderController {

    private final ProviderService providerService;

    @Autowired
    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    /**
     * Public method for requesting all providers from the database
     * @return a List with every ProviderEntity from the database
     */
    @GetMapping(path = "/getAll")
    public List<ProviderEntity> getProviders() {
        return providerService.getAll();
    }

    /**
     * Public method for requesting a specific number of providers from the database
     * @param s used for skipping a number of rows from the database
     * @param c how many rows are necessary
     * @return a List with the ProviderEntity requested
     */
    @GetMapping(path = "/getInterval")
    public List<ProviderEntity> getInterval(@RequestParam(name = "skip", required = true) int s, @RequestParam(name = "count", required = true) int c) {
        return providerService.getInterval(s, c);
    }

    /**
     * Public method for requesting the total number of providers enlisted in the database
     * @return the number of providers as a IntWrapper class
     */
    @GetMapping(path = "/getCount")
    public IntWrapper getCount() {
        return providerService.getCount();
    }

    /**
     * Public method for requesting the number of providers containing the provided string
     * @param query used for providing the string to be searched within the names of the providers
     * @return an IntWrapper containing the number of providers
     */
    @GetMapping(path = "/searchCount")
    public IntWrapper searchCount(@RequestParam(name = "query", required = true) String query) {
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
}
