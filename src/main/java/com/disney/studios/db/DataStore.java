package com.disney.studios.db;

import com.disney.studios.model.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataStore {

    @Autowired
    DataSource dataSource;

    private Connection connection;

    @PostConstruct
    public void init() throws SQLException {
        connection = dataSource.getConnection();
    }

    @PreDestroy
    public void destroy() throws SQLException {
        connection.close();
    }

    public List<Dog> getDogs(String query) {
        List<Dog> dogs = new ArrayList<>();
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                dogs.add(new Dog(rs.getLong("id"), rs.getString("url"), rs.getString("breed"),
                        rs.getInt("vote")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dogs;
    }

    @Transactional
    public boolean addFavorite(Long dogId, Long userId) {
        return update(String.format("INSERT INTO FAVORITES (userId, dogId) VALUES (%d, %d)",
                userId, dogId)) &&
                update(String.format("UPDATE DOGS SET vote = vote + 1 WHERE id = %d", dogId));
    }

    @Transactional
    public boolean removeFavorite(Long dogId, Long userId) {
        return update(String.format("DELETE FROM FAVORITES WHERE userId = %d AND dogId = %d",
                userId, dogId)) &&
                update(String.format("UPDATE DOGS SET vote = vote - 1 WHERE id = %d", dogId));
    }

    public void createTables() throws SQLException {
        connection.prepareStatement(
                "CREATE TABLE DOGS (id int identity, url varchar(255), breed varchar(25), vote int)")
                .executeUpdate();
        connection.prepareStatement(
                "CREATE TABLE FAVORITES (userId int , dogId int, PRIMARY KEY (userId, dogId), " +
                        "FOREIGN KEY (dogId) REFERENCES DOGS(id))")
                .executeUpdate();
    }

    public void addDog(Dog dog) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "INSERT INTO DOGS (url, breed, vote) VALUES(?, ?, ?)");
        st.setString(1, dog.getUrl());
        st.setString(2, dog.getBreed());
        st.setInt(3, dog.getVote());
        st.executeUpdate();
    }

    private boolean update(String query) {
        try {
            return connection.prepareStatement(query).executeUpdate() > 0;
        } catch (SQLException e) {
            if (e.getMessage().contains("Unique index")) {
                return false;
            }
            throw new RuntimeException(e);
        }
    }
}
