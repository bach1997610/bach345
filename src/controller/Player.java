/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Player {

    public String name;
    private ImageIcon image1 = new ImageIcon(getClass().getResource("/Image/X.jpg"));

    public Player(String name) {
        this.name = name;
    }

    public Player(String name, ImageIcon image1) {
        this.name = name;
        this.image1 = image1;
    }

    public void play(JButton btn) {
        btn.setIcon(image1);
    }
}
