package com.vianda_app.base.controllers;

import com.vianda_app.base.entities.Area;
import com.vianda_app.base.services.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/area")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @GetMapping
    public List<Area> getAllAreas() {
        return areaService.getAll();
    }

}
