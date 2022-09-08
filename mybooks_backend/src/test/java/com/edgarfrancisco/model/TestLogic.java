package com.edgarfrancisco.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TestLogic {

    @Test
    public void canUseForLoop() { //NullPointerException

        List<String> list = null;

        for (String str : list) {
            System.out.println(str);
        }

        System.out.println("yes");
    }

    @Test
    public void mapObjectToJson() {

//      String callNumber, String title, String subtitle,
//      int year, int numberOfPages, String description, String[] authors,
//      String[] tags, String publisherName, String categoryName, String collectionName,
//      String userName

        ObjectMapper mapper = new ObjectMapper();

        Book book = new Book();
        book.setCallNumber("001");
        book.setTitle("Book 1");
        book.setSubtitle("Book One");
        book.setYear("1975");
        book.setNumberOfPages(340);
        book.setDescription("Add first book");

        Author author = new Author();
        author.setFirstName("Author 1 firstName");
        author.setLastName("Author 1 lastName");
        Author author2 = new Author();
        author2.setFirstName("Author 2 firstName");
        author2.setLastName("Author 2 lastName");
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        authors.add(author2);

        Tag tag = new Tag();
        tag.setTagName("Tag 1");
        Tag tag2 = new Tag();
        tag2.setTagName("Tag 2");
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        tags.add(tag2);

        Publisher publisher = new Publisher();
        publisher.setPublisherName("Publisher 1");

        Category category = new Category();
        category.setCategoryName("Category 1");

        Collection collection = new Collection();
        collection.setCollectionName("Collection 1");

        User user = new User();
        user.setFirstName("User 1");

        book.setAuthors(authors);
        book.setTags(tags);
        book.setPublisher(publisher);
        book.setCategory(category);
        book.setCollection(collection);
        book.setUser(user);

        try {
            String json = mapper.writeValueAsString(book);
            System.out.println(json);
        } catch (JsonProcessingException e) {

        }
    }

}
