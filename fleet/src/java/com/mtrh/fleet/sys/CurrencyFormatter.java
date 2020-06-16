/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtrh.fleet.sys;

/**
 *
 * @author owner
 */
public class CurrencyFormatter {
    
    private static java.text.NumberFormat decimalFormat = null;
    
    /** Creates a new instance of CurrencyFormatter */
    public CurrencyFormatter() {
        
    }
    public static java.lang.String getFormattedDouble(double double2format){
        
         
        decimalFormat = java.text.DecimalFormat.getInstance();
        
        
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setMinimumFractionDigits(2);
               
        
        
        return decimalFormat.format(double2format);
        
    }
    
    public static double parseFormattedString(java.lang.String formattedString){
        
        
        decimalFormat = java.text.DecimalFormat.getInstance();
        
        
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setMinimumFractionDigits(2);
                
        
        double parsedDouble = 0.00;
        
        try {
        
        java.lang.Number parsedNumber =  decimalFormat.parse(((formattedString == null || formattedString.equals("")) ? "0.00" : formattedString));
        
        parsedDouble = parsedNumber.doubleValue();
        
        } catch(java.text.ParseException parseEx){
            
            javax.swing.JOptionPane.showMessageDialog(new java.awt.Frame(), parseEx.getMessage(), "Number parse exception", javax.swing.JOptionPane.ERROR_MESSAGE);
            formattedString.equals( "0.00");
            parseEx.printStackTrace();
            
        }
        
        return parsedDouble;
        
    }
    
    //    }
    
}

