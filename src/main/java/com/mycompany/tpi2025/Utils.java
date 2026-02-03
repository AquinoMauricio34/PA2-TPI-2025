package com.mycompany.tpi2025;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import javax.imageio.ImageIO;

public class Utils {

    public static String generarQRBase64(String texto) throws WriterException, IOException {

        int width = 200;
        int height = 200;

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        // 🔹 Configuración para español (tildes, ñ, etc.)
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix bitMatrix = qrCodeWriter.encode(
                texto,
                BarcodeFormat.QR_CODE,
                width,
                height,
                hints
        );

        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(qrImage, "png", baos);

        byte[] bytes = baos.toByteArray();

        return Base64.getEncoder().encodeToString(bytes);
    }

    public static BufferedImage base64ToImage(String base64) throws IOException {
        byte[] bytes = Base64.getDecoder().decode(base64);
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        return ImageIO.read(bais);
    }

    public static boolean isLong(String str) {
        if (str == null) {
            return false;
        }
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isFechaValida(String fecha) {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("dd/MM/uuuu")
                .withResolverStyle(ResolverStyle.STRICT);

        try {
            LocalDate.parse(fecha, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isHoraValida(String hora) {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("HH:mm")
                .withResolverStyle(ResolverStyle.STRICT);

        try {
            LocalTime.parse(hora, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    public static boolean hayVacios(String... campos){
        return Stream.of(campos).anyMatch(String::isBlank);
    }

}
