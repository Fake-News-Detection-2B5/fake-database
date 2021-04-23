package com.backend.fakedb.utilities;

import com.backend.fakedb.entities.AiEntity;
import com.backend.fakedb.entities.IngestionEntity;
import com.backend.fakedb.entities.PostEntity;
import com.backend.fakedb.entities.ProviderEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Class that handles the communication between the ingestion system and this app.
 */

// TEST LINK: https://fake-database-fe-support.herokuapp.com/provider/getInterval?skip=4&count=3
public class IngestionLinker {

    RestTemplate ingestion;

    public IngestionLinker() {
        ingestion = new RestTemplate();
    }

    /**
     * Test function until the ingestion system implements their own app.
     * Will be updated in the future with ProviderEntity -> IngestionEntity
     */
    public List<PostEntity> getResponseFromIngestion(int skip, int count) throws MalformedURLException {
        ResponseEntity<IngestionEntity[]> response =
                ingestion.getForEntity("https://fake-database-fe-support.herokuapp.com/provider/getInterval?skip=" + skip + "&count=" + count, IngestionEntity[].class);
        IngestionEntity[] responseArray = response.getBody();

        // If the list is null, don't continue.
        if (responseArray == null) {
            return null;
        }

        List<PostEntity> responseList = new ArrayList<>(responseArray.length);

        for (int i = 0; i < responseArray.length; i++) {
            // We need to sent an AiEntity to the AI module and get the associated result
            ScoreResult responseScore = ingestion.postForObject("url-to-ai-module", convertToAiEntity(responseArray[i]), ScoreResult.class);

            // We need to build a PostEntity with the associated score and information
            assert responseScore != null;
            responseList.set(i, convertToPostEntity(responseArray[i], responseScore.getScore()));
        }
        return responseList;
    }

    /**
     * Private method that converts a given ingestion entity to a AI entity.
     * @param ingestionEntity the entity to be converted
     * @return the AI entity
     */
    private AiEntity convertToAiEntity(IngestionEntity ingestionEntity) {
        return new AiEntity(ingestionEntity.getTitle(),
                            ingestionEntity.getContent());
    }

    /**
     * Private method that converts a given ingestion entity to a Post entity.
     * @param ingestionEntity the entity to be converted
     * @param score the associated score for this specific post
     * @return the Post entity
     */
    private PostEntity convertToPostEntity(IngestionEntity ingestionEntity, String score) {
        return new PostEntity(ingestionEntity.getId(),
                                ingestionEntity.getTitle(),
                                ingestionEntity.getThumbnail(),
                                ingestionEntity.getDescription(),
                                ingestionEntity.getPostDate(),
                                score,
                                ingestionEntity.getSourceUrl());
    }
}
