package com.example.otlobna.Adapter;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class Resturant extends ExpandableGroup<Details> {
    public Resturant(String title, List<Details> items) {
        super(title, items);
    }
}