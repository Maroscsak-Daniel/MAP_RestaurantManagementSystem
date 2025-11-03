package com.example.restaurant.controller;

import com.example.restaurant.model.Chef;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IController<T, U> {
    public List<T> all();
    public T getById(@PathVariable String id);
    public String add(@RequestBody T t);
    public String delete(@PathVariable String id);
    public String clearAll();
}
