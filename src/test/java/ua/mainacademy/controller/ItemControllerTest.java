package ua.mainacademy.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import ua.mainacademy.model.Item;
import ua.mainacademy.service.ItemService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ua.mainacademy.prototype.ItemPrototype.aNewItem;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ItemControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private ItemService service;

    @Test
    void createSuccessTest() throws URISyntaxException {
        Item testItem = aNewItem();

        when(service.create(any(Item.class))).thenReturn(testItem);

        RequestEntity<Item> requestEntity = new RequestEntity<>(testItem, HttpMethod.PUT, new URI("/item"));

        ResponseEntity<Item> responseEntity = testRestTemplate.exchange(requestEntity, Item.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void createFailedTest() throws URISyntaxException {
        Item testItem = aNewItem();

        when(service.create(any(Item.class))).thenThrow(new RuntimeException());

        RequestEntity<Item> requestEntity = new RequestEntity<>(testItem, HttpMethod.PUT, new URI("/item"));

        ResponseEntity<Item> responseEntity = testRestTemplate.exchange(requestEntity, Item.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void createFailedTestForNull() throws URISyntaxException {
        Item testItem = new Item();

        RequestEntity<Item> requestEntity = new RequestEntity<>(testItem, HttpMethod.PUT, new URI("/item"));

        ResponseEntity<Item> responseEntity = testRestTemplate.exchange(requestEntity, Item.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void updateSuccessTest() throws URISyntaxException {
        Item testItem = aNewItem();

        when(service.update(any(Item.class))).thenReturn(testItem);

        RequestEntity<Item> requestEntity = new RequestEntity<>(testItem, HttpMethod.POST, new URI("/item"));

        ResponseEntity<Item> responseEntity = testRestTemplate.exchange(requestEntity, Item.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void updateFailedTest() throws URISyntaxException {
        Item testItem = aNewItem();

        when(service.update(any(Item.class))).thenThrow(new RuntimeException());

        RequestEntity<Item> requestEntity = new RequestEntity<>(testItem, HttpMethod.POST, new URI("/item"));

        ResponseEntity<Item> responseEntity = testRestTemplate.exchange(requestEntity, Item.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void updateFailedTestForNull() throws URISyntaxException {
        Item testItem = new Item();

        RequestEntity<Item> requestEntity = new RequestEntity<>(testItem, HttpMethod.POST, new URI("/item"));

        ResponseEntity<Item> responseEntity = testRestTemplate.exchange(requestEntity, Item.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void findByIdSuccessTest() throws URISyntaxException {
        Item testItem = aNewItem();
        long id = 1L;

        when(service.findOneById(id)).thenReturn(testItem);

        RequestEntity<Item> requestEntity = new RequestEntity<>(HttpMethod.GET, new URI("/item/" + id));

        ResponseEntity<Item> responseEntity = testRestTemplate.exchange(requestEntity, Item.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void findByIdFailedTest() throws URISyntaxException {
        long id = 1L;

        when(service.findOneById(id)).thenThrow(new RuntimeException());

        RequestEntity<Item> requestEntity = new RequestEntity<>(HttpMethod.GET, new URI("/item/" + id));

        ResponseEntity<Item> responseEntity = testRestTemplate.exchange(requestEntity, Item.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void findByIdFailedTestForNull() throws URISyntaxException {
        Long id = null;

        RequestEntity<Item> requestEntity = new RequestEntity<>(HttpMethod.GET, new URI("/item/" + id));

        ResponseEntity<Item> responseEntity = testRestTemplate.exchange(requestEntity, Item.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void findAllSuccessTest() throws URISyntaxException {
        Item testItem = aNewItem();

        when(service.findAll()).thenReturn(List.of(testItem));

        RequestEntity<List<Item>> requestEntity = new RequestEntity<>(HttpMethod.GET, new URI("/item"));

        ResponseEntity<List<Item>> responseEntity = testRestTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<Item>>() {
        });
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        List<Item> items = responseEntity.getBody();

        assertThat(List.of(aNewItem())).isEqualTo(items);
    }

    @Test
    void findAllSuccessForEmptyTest() throws URISyntaxException {
        when(service.findAll()).thenReturn(emptyList());

        RequestEntity<List<Item>> requestEntity = new RequestEntity<>(HttpMethod.GET, new URI("/item"));

        ResponseEntity<List<Item>> responseEntity = testRestTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<Item>>() {
        });
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        List<Item> items = responseEntity.getBody();

        assertThat(emptyList()).isEqualTo(items);
    }

    @Test
    void deleteSuccessTest() throws URISyntaxException {
        long id = 1L;

        RequestEntity<Void> requestEntity = new RequestEntity<>(HttpMethod.DELETE, new URI("/item/" + id));

        ResponseEntity<Void> responseEntity = testRestTemplate.exchange(requestEntity, Void.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void deleteFailedTestForNull() throws URISyntaxException {
        Long id = null;

        RequestEntity<Void> requestEntity = new RequestEntity<>(HttpMethod.DELETE, new URI("/item/" + id));

        ResponseEntity<Void> responseEntity = testRestTemplate.exchange(requestEntity, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}