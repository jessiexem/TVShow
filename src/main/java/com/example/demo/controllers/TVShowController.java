package com.example.demo.controllers;

import com.example.demo.model.TvShow;
import com.example.demo.service.TVShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class TVShowController {

    @Autowired
    TVShowService tvShowSvc;

    @GetMapping
    public String showHome() {
        return "home";
    }

    @GetMapping("/shows")
    public String getShows(@RequestParam (name="showName") String showName, Model model) {
        System.out.println(">>>>> in getShows() controller");
        Optional<List<TvShow>> opt = tvShowSvc.searchShows(showName);

        if(opt.isEmpty()) {
            return "404";
        }

        List<TvShow> showList = opt.get();

        for(TvShow s: showList) {
            System.out.println(s.getShowName()+s.getLanguage()+s.getImage()+s.getAverageRating());
        }


        model.addAttribute("showList",showList);


        return "displayShow";
    }



}
