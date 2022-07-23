package com.itiad.iziland.services.Iservices;

public interface QRCodeService {
    byte[] generateQrCode(String qrCodeContent, int width, int height);
}
