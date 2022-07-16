package com.itiad.iziland.presentation.restcontroller;

import com.itiad.iziland.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class DocumentRestController {

    @Autowired
    private DocumentRepository documentRepository;



}
