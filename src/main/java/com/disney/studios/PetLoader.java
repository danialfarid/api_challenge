package com.disney.studios;

import com.disney.studios.db.DataStore;
import com.disney.studios.model.Dog;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Loads stored objects from the file system and builds up
 * the appropriate objects to add to the data source.
 * <p>
 * Created by fredjean on 9/21/15.
 */
@Component
public class PetLoader implements InitializingBean {
    // Resources to the different files we need to load.
    @Value("classpath:data/labrador.txt")
    private Resource labradors;

    @Value("classpath:data/pug.txt")
    private Resource pugs;

    @Value("classpath:data/retriever.txt")
    private Resource retrievers;

    @Value("classpath:data/yorkie.txt")
    private Resource yorkies;

    @Autowired
    DataSource dataSource;

    @Autowired
    DataStore dataStore;

    /**
     * Load the different breeds into the data source after
     * the application is ready.
     *
     * @throws Exception In case something goes wrong while we load the breeds.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Connection connection = dataSource.getConnection();
        try {
            dataStore.createTables();
            loadBreed("Labrador", labradors);
            loadBreed("Pug", pugs);
            loadBreed("Retriever", retrievers);
            loadBreed("Yorkie", yorkies);
        } finally {
            connection.close();
        }
    }

    /**
     * Reads the list of dogs in a category and (eventually) add
     * them to the data source.
     *
     * @param breed  The breed that we are loading.
     * @param source The file holding the breeds.
     * @throws IOException In case things go horribly, horribly wrong.
     */
    private void loadBreed(String breed, Resource source) throws IOException, SQLException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(source.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                dataStore.addDog(new Dog(null, line, breed, 0));
            }
        }
    }
}
