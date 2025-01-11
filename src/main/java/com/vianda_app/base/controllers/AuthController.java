package com.vianda_app.base.controllers;

import com.vianda_app.base.dtos.RegistroRequest;
import com.vianda_app.base.entities.Rol;
import com.vianda_app.base.entities.Usuario;
import com.vianda_app.base.repositories.RolRepository;
import com.vianda_app.base.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

}
