/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtrh.mtportal.sys;

import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
//import net.glxn.qrgen.image.ImageType;
//import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.javase.QRCode;
import net.glxn.qrgen.core.image.ImageType;

/**
 *
 * @author owner
 */
public class QRCodeGenerator {

    public QRCodeGenerator() {
    }

    public static void resize(String inputImagePath,
            String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException {
        // reads input image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);

        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        // extracts extension of output file
        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);

        // writes to output file
        ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }

    public static void resize(String inputImagePath,
            String outputImagePath, double percent) throws IOException {
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
        int scaledWidth = (int) (inputImage.getWidth() * percent);
        int scaledHeight = (int) (inputImage.getHeight() * percent);
        resize(inputImagePath, outputImagePath, scaledWidth, scaledHeight);
    }

    private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    public static String QR_URL(String someCode) {
        String fileURl = "";
        try {

            String data2Convert = someCode;
            ByteArrayOutputStream out = QRCode.from(data2Convert).to(ImageType.JPG).stream();

            java.io.File tempFile = java.io.File.createTempFile("REP_", ".jpg");
            tempFile.deleteOnExit();

            FileOutputStream fos = new FileOutputStream(tempFile);

            fos.write(out.toByteArray());
            fos.flush();

            BufferedImage image = ImageIO.read(tempFile);
            BufferedImage resized = resize(image, 100, 100);

            fileURl = tempFile.getAbsolutePath();

            // resize(fileURl, fileURl, 0.5);
        } catch (Exception ex) {
            Logger.getLogger(QRCodeGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileURl;
    }

    public static BufferedImage QR_BUFFERED_IMG(String someCode) {
        String fileURl = "";
        BufferedImage resized = null;
        try {

            String data2Convert = someCode;
            ByteArrayOutputStream out = QRCode.from(data2Convert).to(ImageType.JPG).stream();

            java.io.File tempFile = java.io.File.createTempFile("REP_", ".jpg");
            tempFile.deleteOnExit();

            FileOutputStream fos = new FileOutputStream(tempFile);

            fos.write(out.toByteArray());
            fos.flush();

            BufferedImage image = ImageIO.read(tempFile);
            //resized = resize(image, 100, 100);
            Image scaled = image.getScaledInstance(300, 255, Image.SCALE_DEFAULT);


            resized = getScaledImage(image, 100, 100);
            fileURl = tempFile.getAbsolutePath();

            // resize(fileURl, fileURl, 0.5);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resized;
    }

    public static BufferedImage getScaledImage(BufferedImage image, int width, int height) throws IOException {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        double scaleX = (double) width / imageWidth;
        double scaleY = (double) height / imageHeight;
        AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
        AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);

        return bilinearScaleOp.filter(
                image,
                new BufferedImage(width, height, image.getType()));
    }

    public static void main(String args[]) {
        String name = "I LOVE YOU PEPI";
        try {
            String url = QR_URL(name);

            File file = new File(url);

            BufferedImage image = ImageIO.read(file);
            BufferedImage resized = resize(image, 100, 100);

            // File output = new File(url);
            //ImageIO.write(resized, "png", output);
            System.err.println(url);

            Desktop.getDesktop().open(file);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
