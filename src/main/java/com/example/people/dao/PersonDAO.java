package com.example.people.dao;

import com.example.people.models.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Component
public class PersonDAO {
//    private static int PEOPLE_COUNT;

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    private static final Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Person> index() throws SQLException {
        List<Person> people = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM person");

        while (resultSet.next()) {
            Person person = new Person();
            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setEmail(resultSet.getString("email"));
            person.setAge(resultSet.getInt("age"));

            people.add(person);
        }
        return people;
    }

    public Person show(int id) throws SQLException {
//        return people
//                .stream()
//                .filter(person -> person.getId() == id)
//                .findAny()
//                .orElse(null);

        Person person = new Person();

        PreparedStatement preparedStatement = connection.prepareStatement("select * from person where id=?");
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        person.setId(resultSet.getInt("id"));
        person.setName(resultSet.getString("name"));
        person.setAge(resultSet.getInt("age"));
        person.setEmail(resultSet.getString("email"));

        return person;
    }

    public void save(Person person) throws SQLException {
//        person.setId(++PEOPLE_COUNT);
//        people.add(person);

        PreparedStatement statement = connection.prepareStatement("insert into person values(1, ?, ?, ?)");

        statement.setString(1, person.getName());
        statement.setInt(2, person.getAge());
        statement.setString(3, person.getEmail());

        statement.executeUpdate();
    }

    public void update(int id, Person updatedPerson) throws SQLException {
//        Person personToBeUpdated = show(id);
//        personToBeUpdated.setName(updatedPerson.getName());
//        personToBeUpdated.setAge(updatedPerson.getAge());
//        personToBeUpdated.setEmail(updatedPerson.getEmail());

        PreparedStatement preparedStatement = connection.prepareStatement("update person set name=?, age=?, email=? where id=?");

        preparedStatement.setString(1, updatedPerson.getName());
        preparedStatement.setInt(2, updatedPerson.getAge());
        preparedStatement.setString(3, updatedPerson.getEmail());
        preparedStatement.setInt(4, id);

        preparedStatement.executeUpdate();
    }

    public void delete(int id) throws SQLException {
//        people.removeIf(person -> person.getId() == id);

        PreparedStatement preparedStatement = connection.prepareStatement("delete from person where id=?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }
}
