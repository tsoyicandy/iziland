package com.itiad.iziland.util;

import com.itiad.iziland.services.Iservices.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class QrCodeGenerator {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;

    @Autowired
    private QRCodeService qrCodeService;

    public byte[] generateQrCode(String code) {
        return qrCodeService.generateQrCode(code, WIDTH, HEIGHT);
    }
}
