package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class ItemStorageImpl implements ItemStorage {
    private final Map<Long, Item> items = new HashMap<>();
    private AtomicLong keyCounter = new AtomicLong(0);

    public Optional<ItemDto> addNewItem(Item item) {
        Long id = generateUniqueKey();
        items.put(id, item);
        log.info("Предмет добавлен в мапу");
        return Optional.ofNullable(ItemMapper.toItemDto(item, id));
    }

    public Optional<ItemDto> updateItem(Item item, Long id) {
        items.put(id, item);
        log.info("Предмет обновлен в мапе");
        return Optional.ofNullable(ItemMapper.toItemDto(item, id));
    }

    public Optional<ItemDto> getItemById(Long id) {
        log.info("Получен запрос в репозиторий на получение предмета по id");
        return Optional.ofNullable(ItemMapper.toItemDto(items.get(id), id));
    }

    public List<Item> getAllItems() {
        log.info("Получен запрос в репозиторий на получение всех предметов");
        return new ArrayList<>(items.values());
    }

    public List<Item> getAllOwnerItems(Long userId) {
        log.info("Получен запрос в репозиторий на получение всех предметов пользователя");
        return getAllItems().stream()
                .filter(item -> userId.equals(item.getOwnerId()))
                .collect(Collectors.toList());
    }

    public List<Item> getItemsBySearch(String text) {
        if (text == null || text.isBlank()) {
            log.info("Получен пустой текст");
            return List.of();
        }

        final String cleanKeyword = text.trim().toLowerCase();

        log.info("Получен запрос в репозиторий на получение предметов по поиску");
        return items.values().stream()
                .filter(entity -> containsIgnoreCase(entity.getName(), cleanKeyword)
                        || containsIgnoreCase(entity.getDescription(), cleanKeyword))
                .filter(entity -> entity.getAvailable().equals(true))
                .collect(Collectors.toList());
    }

    private boolean containsIgnoreCase(String source, String text) {
        if (source == null || text == null) {
            return false;
        }
        return source.toLowerCase().contains(text);
    }

    private Long generateUniqueKey() {
        return keyCounter.incrementAndGet();
    }
}
