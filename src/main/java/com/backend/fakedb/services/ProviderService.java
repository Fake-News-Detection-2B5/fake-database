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
     * Public method that returns a ProviderEntity based on the given ID.
     * @param id the given ID to search for
     * @return if the provider exists, it is returned. Else, a null is returned.
     * @deprecated Method no longer needed as the ProviderController does not use it.
     */
    @Deprecated
    public ProviderEntity getById(Integer id) {
        Optional<ProviderEntity> returnEntity = providerRepository.findById(id);
        if (returnEntity.isEmpty()) return null;
        return returnEntity.get();
    }

    /**
     * Public method that returns a ProviderEntity based on the given name.
     * @param name the name of the provider to search for
     * @return if the providers exists, it is returned as a ProviderEntity, otherwise the function returns null.
     * @deprecated Method no longer needed as the ProviderController does not use it.
     */
    public ProviderEntity getByName(String name) {
        List<ProviderEntity> providerEntityList = getAll();
        for (ProviderEntity p : providerEntityList) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
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

//        List<ProviderEntity> listAll = providerRepository.findAll();
//
//        // The request skips all data
//        if (listAll.size() < s) {
//            return null;
//        }
//
//        List<ProviderEntity> listToReturn = new ArrayList<>(c);
//        for (int i = s; i < s + c; i++) {
//            // If no more rows are available, add null items to the list
//            if (i >= listAll.size()) {
//                listToReturn.add(null);
//            }
//            else {
//                listToReturn.add(listAll.get(i));
//            }
//        }
//        return listToReturn;

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
//        List<ProviderEntity> providerEntityList = providerRepository.findAll();
//        int providerNr = 0;
//        for (ProviderEntity p : providerEntityList) {
//            if (p.getName().contains(query)) {
//                providerNr++;
//            }
//        }
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

//        List<ProviderEntity> listAll = providerRepository.findAll();
//
//        // The request skips all data
//        if (listAll.size() < s) {
//            return null;
//        }
//
//        listAll = listAll.stream()
//                .filter(str -> str.getName().contains(query))
//                .collect(Collectors.toList());
//
//        List<ProviderEntity> listToReturn = new ArrayList<>(c);
//        for (int i = s; i < s + c; i++) {
//            if (i >= listAll.size()) {
//                listToReturn.add(null);
//            }
//            else {
//                listToReturn.add(listAll.get(i));
//            }
//        }
//        return listToReturn;

        return providerRepository.findAll().stream()
                .filter(str -> str.getName().contains(query))
                .skip(s)
                .limit(c)
                .collect(Collectors.toList());
    }

    /**
     * Public method for updating a provider in the database, given its ID.
     * @param id the id of the provider
     * @param name a string which will replace the current name (if it is empty, no replace will happen)
     * @param credibility a number which will replace the current credibility (if it is null, no replace will happen)
     * @param avatar a string which will replace the current avatar (if it is empty, no replace will happen)
     */
    @Transactional
    public void updateProvider(Integer id, String name, Double credibility, String avatar) {
        Optional<ProviderEntity> old = providerRepository.findById(id);
        if (old.isEmpty()) {
            return;
        }
        providerRepository.update(id, (name.equals("") ? old.get().getName() : name),
                (credibility == null ? old.get().getCredibility() : credibility),
                (avatar.equals("") ? old.get().getAvatar() : avatar));
    }

    /**
     * Public method for deleting a provider from the database
     * @param id the id of the provider to be deleted.
     */
    public void deleteProvider(Integer id) {
        providerRepository.deleteById(id);
    }

    /**
     * Public method that adds a ProviderEntity to the database.
     * @param providerEntity the entity to be added
     */
    public void addProvider(ProviderEntity providerEntity) {
        providerRepository.save(providerEntity);
    }
}
