package com.backend.fakedb.services;

import com.backend.fakedb.entities.ProviderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import com.backend.fakedb.repositories.ProviderRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
     */
    public ProviderEntity getById(Integer id) {
        Optional<ProviderEntity> returnEntity = providerRepository.findById(id);
        if (returnEntity.isEmpty()) return null;
        return returnEntity.get();
    }

    /**
     * Public method that returns a ProviderEntity based on the given name.
     * @param name the name of the provider to search for
     * @return if the providers exists, it is returned as a ProviderEntity, otherwise the function returns null.
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

    public List<ProviderEntity> getInterval(int s, int c) {
        List<ProviderEntity> listAll = getAll();

        // The request skips all data
        if (listAll.size() < s) {
            return null;
        }

        List<ProviderEntity> listToReturn = new ArrayList<>(c);
        for (int i = s; i < s + c; i++) {
            listToReturn.add(listAll.get(i));
        }
        return listToReturn;
    }

    @Transactional
    public void updateProvider(Integer id, String name, Double credibility, String avatar) {
        ProviderEntity old = getById(id);
        if (old == null) {
            return;
        }
        providerRepository.update(id, (name.equals("") ? old.getName() : name), (credibility == null ? old.getCredibility() : credibility), (avatar.equals("") ? old.getAvatar() : avatar));
    }

    public void deleteProvider(Integer id) {
        providerRepository.deleteById(id);
    }

    /**
     * Public method that adds a ProviderEntity to the database.
     * @param providerEntity the entity to be added
     * @throws IllegalArgumentException if the parameter given is null
     */
    public void addProvider(ProviderEntity providerEntity) {
        providerRepository.save(providerEntity);
    }

    public int getCount() {
        return (int) providerRepository.count();
    }
}
