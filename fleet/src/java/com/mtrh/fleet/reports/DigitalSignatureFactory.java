/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtrh.fleet.reports;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import javax.imageio.ImageIO;

/**
 *
 * @author owner
 */
public class DigitalSignatureFactory {

    public static BufferedImage getSignature(String pfno, Connection connectDB) {
        BufferedImage img = null;

        try {

            java.sql.Statement st3 = connectDB.createStatement();
            java.sql.ResultSet rset3 = st3.executeQuery("select specimen_signature from fsmaster where employee_no  ilike '"+pfno+"';");
            while (rset3.next()) {

                InputStream is = rset3.getBinaryStream(1);
                 img = ImageIO.read(is);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return img;
    }

}
