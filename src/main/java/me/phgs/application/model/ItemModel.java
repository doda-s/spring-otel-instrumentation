package me.phgs.application.model;

import me.phgs.application.model.common.Item;
import me.phgs.application.util.Generator;

public class ItemModel extends Item {

    private long id;

    public ItemModel(String name, int count) {
        super(name, count);
        id = Generator.generateId();
    }
    
    public long getId() {
        return id;
    }
}
