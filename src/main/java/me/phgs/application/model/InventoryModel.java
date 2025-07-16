package me.phgs.application.model;

import java.util.ArrayList;
import java.util.List;

import me.phgs.application.model.common.Item;

public class InventoryModel {
    private List<Item> items;

    public InventoryModel() {
        items = new ArrayList<>();
    }

    public void addItem(Item itemToAdd) {
        for (Item item : items) {
            if (item.getName().equals(itemToAdd.getName())) {
                item.addItemCount(itemToAdd.getItemCount());
                return;
            }
        }

        Item itemModel = new ItemModel(itemToAdd.getName(), itemToAdd.getItemCount());
        items.add(itemModel);
    }

    public Item popItem(long id) {
        for(Item item : items) {
            if (item.getId() == id) {
                items.remove(item);
                return item;
            }
        }
        return null;
    }

    public List<Item> items() {
        return items;
    }
}
