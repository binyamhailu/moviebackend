package com.example.moviebappbackend.user;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @GetMapping
//    @PreAuthorize("hasAuthority('admin:read')")
    public String get() {
        return "admin controller authentication is working ";
    }

}
