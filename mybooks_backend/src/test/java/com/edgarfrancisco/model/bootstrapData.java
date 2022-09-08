package com.edgarfrancisco.model;

import com.edgarfrancisco.service.BookService;
import com.edgarfrancisco.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class bootstrapData {

    @Autowired
    UserService userService;
    @Autowired
    BookService bookService;


    @Test
    public void bootstrapData() {

        User user1 = new User("Edgar", "Perez", "edgar", "edgar@test.com");
        User user2 = new User("Francisco", "Perez", "franperz", "franperz@test.com");
        User user3 = new User("User", "testUser", "user", "user@test.com");

        Book book1 = new Book();
        book1.setCallNumber("001");
        book1.setTitle("Java");
        book1.setSubtitle("This is book one");
        book1.setYear("1000");
        book1.setNumberOfPages(100);
        book1.setNumberOfCopies(1);
        book1.setDescription("Java development");

        Author author1 = new Author("Jorge", "", "Beltrán");
        book1.addAuthor(author1);

        Tag tag1 = new Tag("back_end");
        book1.addTag(tag1);

        Publisher publisher = new Publisher("random house");
        book1.setPublisher(publisher);

        Category category = new Category("Programming languages");

        Book book2 = new Book();
        book2.setCallNumber("002");
        book2.setTitle("MySQL");
        book2.setSubtitle("This is book two");
        book2.setYear("1000");
        book2.setNumberOfPages(101);
        book2.setNumberOfCopies(1);
        book2.setDescription("Learn MySQL");

        Author author2 = new Author("Jorge", "", "Beltrán");
        book2.addAuthor(author2);

        Tag tag2 = new Tag("back_end");
        book2.addTag(tag1);

        Publisher publisher2 = new Publisher("random house");
        book2.setPublisher(publisher2);

        Category category2 = new Category("Programming languages");


        Book book3 = new Book();
        book3.setCallNumber("003");
        book3.setTitle("CSS");
        book3.setSubtitle("This is book three");
        book3.setYear("1001");
        book3.setNumberOfPages(102);
        book3.setNumberOfCopies(1);
        book3.setDescription("Learn CSS");

        Author author3 = new Author("Nestor", "J", "Gould");
        book3.addAuthor(author3);

        Tag tag3 = new Tag("front_end");
        book3.addTag(tag1);

        Publisher publisher3 = new Publisher("Horizon");
        book3.setPublisher(publisher3);

        Category category3 = new Category("Programming languages");

        Book book4 = new Book();
        book4.setCallNumber("004");
        book4.setTitle("Bootstrap");
        book4.setSubtitle("This is book four");
        book4.setYear("1002");
        book4.setNumberOfPages(103);
        book4.setNumberOfCopies(1);
        book4.setDescription("Learn Bootstrap");

        Author author4 = new Author("Juan", "", "Flores");
        book4.addAuthor(author4);

        Tag tag4 = new Tag("front_end");
        book4.addTag(tag4);

        Publisher publisher4 = new Publisher("SkyBooks");
        book4.setPublisher(publisher4);

        Category category4 = new Category("Programming languages");


        Book book5 = new Book();
        book5.setCallNumber("005");
        book5.setTitle("Microservices");
        book5.setSubtitle("This is book fove");
        book5.setYear("1002");
        book5.setNumberOfPages(103);
        book5.setNumberOfCopies(1);
        book5.setDescription("Learn Microservices");

        Author author5 = new Author("Enrique", "", "García");
        book5.addAuthor(author5);

        Tag tag5 = new Tag("Advanced Concepts");
        book5.addTag(tag5);

        Publisher publisher5 = new Publisher("Indiana Publishers");
        book5.setPublisher(publisher5);

        Category category5 = new Category("Computer Science");


        Book book6 = new Book();
        book6.setCallNumber("006");
        book6.setTitle("Full Stack");
        book6.setSubtitle("This is book six");
        book6.setYear("1003");
        book6.setNumberOfPages(104);
        book6.setNumberOfCopies(1);
        book6.setDescription("Learn Full Stack");

        Author author6 = new Author("Enrique", "", "García");
        book6.addAuthor(author5);

        Tag tag6 = new Tag("Advanced Concepts");
        book6.addTag(tag6);

        Publisher publisher6 = new Publisher("Indiana Publishers");
        book6.setPublisher(publisher6);

        Category category6 = new Category("Computer Science");

        ObjectMapper mapper = new ObjectMapper();

        try {
            String json1 = mapper.writeValueAsString(book1);
            System.out.println(json1);
            String json2 = mapper.writeValueAsString(book2);
            System.out.println(json2);
            String json3 = mapper.writeValueAsString(book3);
            System.out.println(json3);
            String json4 = mapper.writeValueAsString(book4);
            System.out.println(json4);
            String json5 = mapper.writeValueAsString(book5);
            System.out.println(json5);
            String json6 = mapper.writeValueAsString(book6);
            System.out.println(json6);
        } catch (JsonProcessingException e) {

        }
    }
}
