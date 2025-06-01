package ru.practicum.shareit.itemAndComment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.itemAndComment.dto.CommentRequestDto;
import ru.practicum.shareit.itemAndComment.dto.ItemRequestDto;
import ru.practicum.shareit.itemAndComment.dto.ItemUpdateRequestDto;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> addNewItem(ItemRequestDto itemRequestDto, long userId) {
        return post("", userId, itemRequestDto);
    }

    public ResponseEntity<Object> updateItem(ItemUpdateRequestDto itemUpdateRequestDto, long userId, long itemId) {
        return patch("/" + itemId, userId, itemUpdateRequestDto);
    }

    public ResponseEntity<Object> getItemById(long itemId) {
        return get("/" + itemId);
    }

    public ResponseEntity<Object> getAllOwnerItems(long userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> getAllBySearch(String text) {
        return get("/search?text=" + text);
    }

    public ResponseEntity<Object> addNewComment(CommentRequestDto commentRequestDto, long userId, long itemId) {
        return post("/" + itemId + "/comment", userId, commentRequestDto);
    }
}
