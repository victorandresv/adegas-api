package adegas.fago.helpers;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.aztec.AztecWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class QRCodesHelper {
    public static String GenerateAztec(String data){
        AztecWriter qrCodeWriter = new AztecWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.AZTEC, 800, 800);
            BufferedImage fimage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(fimage, "png", baos);
            byte[] bytes = baos.toByteArray();
            byte[] encoded = Base64.getEncoder().encode(bytes);

            return new String(encoded);
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }

        return "";
    }
}
