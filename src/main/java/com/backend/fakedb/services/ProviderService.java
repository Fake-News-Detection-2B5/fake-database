package com.backend.fakedb.services;

import com.backend.fakedb.entities.ProviderEntity;
import com.backend.fakedb.repositories.ProviderRepository;
import com.backend.fakedb.utilities.IntWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProviderService {

    private final ProviderRepository providerRepository;

    @Autowired
    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    /**
     * Public method that returns a List with all available providers.
     * @return a List containing all providers
     */
    public List<ProviderEntity> getAll() {
        return providerRepository.findAll();
    }

    /**
     * Public method that returns a List of ProviderEntity objects of size 'c' and skipping 's' rows.
     * @param s the amount of rows to skip (a number over or equal to zero)
     * @param c the number of elements in the list (a number over zero)
     * @return a List containing the specified values (if they exists). If the given skip and count numbers overpass the existing rows, null items are added in the list.
     */
    public List<ProviderEntity> getInterval(int s, int c) {
        // Cannot skip below zero rows and cannot get a list of negative or zero size.
        if (s < 0 || c < 1) {
            return null;
        }

        return providerRepository.findAll().stream()
                .skip(s)
                .limit(c)
                .collect(Collectors.toList());
    }

    /**
     * Public method for getting the amount of providers in the database.
     * @return the amount of providers in the database in a IntWrapper class
     */
    public IntWrapper getCount() {
        return new IntWrapper((int) providerRepository.count());
    }

    /**
     * Public method for getting the amount of providers in the database that contain the given string.
     * @param query string to be contained in the name of the provider
     * @return an IntWrapper containing the number of providers
     */
    public IntWrapper searchCount(String query) {
        int providerNr = (int) providerRepository.findAll().stream()
                .filter(p -> p.getName().contains(query))
                .count();
        return new IntWrapper(providerNr);
    }

    /**
     * Public method for getting the amount of providers in the database that contain the given string.
     * If the given skip and count parameters exceed the database limit, null objects will be added to the list.
     * @param query string that needs to be contained by the provider's name
     * @param s the amount of providers to be skipped (a number greater than or equal to zero)
     * @param c the amount of providers to be returned (a number greater that zero)
     * @return a list with the objects requested
     */
    public List<ProviderEntity> search(String query, int s, int c) {
        if (s < 0 || c < 1) {
            return null;
        }
        return providerRepository.findAll().stream()
                .filter(str -> str.getName().contains(query))
                .skip(s)
                .limit(c)
                .collect(Collectors.toList());
    }
}
