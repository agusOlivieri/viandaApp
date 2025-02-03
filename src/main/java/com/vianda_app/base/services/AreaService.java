package com.vianda_app.base.services;

import com.vianda_app.base.entities.Area;
import com.vianda_app.base.repositories.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaService {

    @Autowired
    private AreaRepository areaRepository;

    public List<Area> getAll() {
        return areaRepository.findAll();
    }

}
