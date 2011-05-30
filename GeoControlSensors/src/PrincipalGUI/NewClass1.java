/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PrincipalGUI;

import java.awt.Color;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JWindow;

/**
 *
 * @author kradac
 */
public class NewClass1 {

    public static void main(String[] args) {
        JFrame f = new JFrame("hello");
        Container fc = f.getContentPane();
        f.setSize(200, 300);
        f.setLocation(100, 100);
        fc.setBackground(Color.green);
        fc.setForeground(Color.pink);
        f.setVisible(true);

        JWindow w = new JWindow();
        Container wc = w.getContentPane();
        w.setSize(200, 300);
        w.setLocation(400, 100);
        wc.setBackground(Color.cyan);
        w.setVisible(true);
    }
}
