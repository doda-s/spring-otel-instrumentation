package me.phgs.application.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.phgs.application.dto.ItemDto;
import me.phgs.application.model.InventoryModel;
import me.phgs.application.model.common.Item;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private InventoryModel inventory = new InventoryModel();

    @PostMapping("/add")
    public Item addItem(
        @RequestBody ItemDto itemDto
    ) {
        inventory.addItem(itemDto);
        return itemDto; 
    }

    @GetMapping("/")
    public List<Item> getInventory(@RequestParam(value = "id") Optional<Integer> id) {
        if (id.isPresent()) {
            return Arrays.asList(inventory.items().get(id.get()-1));
        }

        return inventory.items();
    }

    @DeleteMapping("/remove")
    public Item removeItem(@RequestParam(value = "id") int id) {
        Item itemRemoved = inventory.popItem(id);
        if (itemRemoved != null) {
            return itemRemoved;
        }
        return null;
    }
}
