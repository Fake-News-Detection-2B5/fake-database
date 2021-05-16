package com.backend.fakedb.services;

import com.backend.fakedb.entities.PostEntity;
import com.backend.fakedb.utilities.IngestionLinker;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private IngestionLinker ingestionLinker;

    public PostService() {
        ingestionLinker = new IngestionLinker();
    }

    // Only used in unit tests in order to inject a mock into the PostService class
    // TODO: In order for this method to not be exposed, simulating a friend class
    //       that would instead set the IngestionLinker reference would be better
    public void setLinker(IngestionLinker injectionLinker) {
        this.ingestionLinker = injectionLinker;
    }

    /**
     * Public method for getting a list of posts from the ingestion system.
     * If the given parameters exceed the database number of existing rows, null objects will be added to the list.
     * @param s the amount of rows to skip (must be a number equal or greater than zero)
     * @param c the number of posts to return (must be a number greater than zero)
     * @return a list of size 'c' of PostEntity objects
     */
    public List<PostEntity> getInterval(int s, int c) {
        return ingestionLinker.getInterval(s, c);
    }

    /**
     * Public method which returns a list of post from a specific provider.
     * If the given parameters exceed the database number of existing rows, null objects will be added to the list.
     * @param provider_id the provider from which to get the posts
     * @param s the amount of rows to skip (must be a number equal or greater than zero)
     * @param c the number of posts to return (must be a number greater than zero)
     * @return a list containing the requested elements.
     */
    public List<PostEntity> getIntervalByProvider(int provider_id, int s, int c) {
        return ingestionLinker.getIntervalByProvider(provider_id, s, c);
    }

}
