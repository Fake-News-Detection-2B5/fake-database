package com.backend.fakedb.controllers;

import com.backend.fakedb.entities.ProviderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.backend.fakedb.services.ProviderService;

import java.util.List;

@RestController
@RequestMapping(path = "provider")
public class ProviderController {

    private final ProviderService providerService;

    @Autowired
    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }


    @GetMapping(path = "/getProviders")
    public List<ProviderEntity> getProviders() {
        return providerService.getAll();
    }

    /*@GetMapping(path = "/getProviders?skip={s}&count={c}")
    public List<ProviderEntity> getWithCount(@PathVariable int s, @PathVariable int c) {
        return providerService.getWithCount(s, c);
    }*/

    @PostMapping
    public void dummy() {
        for (int i = 1; i < 10; i++) {
            providerService.addProviderEntity(new ProviderEntity(i, "a" + (10 - i), 10 + i, "b" + i));
        }
    }

    @PostMapping(path = "/addProvider")
    public void addProvider(@RequestBody ProviderEntity providerEntity) {
        providerService.addProviderEntity(providerEntity);
    }
}
