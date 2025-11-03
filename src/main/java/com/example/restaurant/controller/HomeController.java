package com.example.restaurant.controller;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//public abstract class Controller<T> implements IController<T, U>{
//    protected final U items;
//
//    @Override
//    public List<T> all(){
//        return items.getAll();
//    }
//
//    @Override
//    public T ById(@PathVariable String id) {
//        return items.getById(id);
//    }

//
//    public String add(@RequestBody T t);
//    public String delete(@PathVariable String id);
//    public String clearAll();

//}

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addText("message Salut din Thymeleaf!");
        return "index"; // va căuta index.html în templates
    }
}
