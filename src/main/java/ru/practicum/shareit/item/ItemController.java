package ru.practicum.shareit.item;

import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
public class ItemController {

    @PostMapping
    public ResponseEntity<Item> addNewItem(@RequestBody Item item) {

        return ResponseEntity.ok(item);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Item> updateItem(@RequestBody Item item,
                                           @PathVariable @Positive Long id) {
//сделай проверку на владельца
        return ResponseEntity.ok(new Item());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable @Positive Long id) {

        return ResponseEntity.ok(new Item());
    }

    @GetMapping
    public ResponseEntity<List<Item>> getAllOwnerItems() {

        return ResponseEntity.ok(new ArrayList<>());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Item>> getAllBySearch(@RequestParam String text) {
//сделай проверку на доступность вещей
        return ResponseEntity.ok(new ArrayList<>());
    }
}
