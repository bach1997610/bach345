/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class HomeGame extends JFrame implements ActionListener {

    private JButton btnSingle;
    private JButton btnMuti;
    private JButton btnGui;
    private JButton btnAbout;
    private JButton btnExit;
    private JLabel lbImage;

    public HomeGame() {
        init();
    }

    private void init() {
        //khoi tao khung frame
        this.setTitle("Game Caro");
        this.setLayout(null);
        this.setSize(360, 550);
        this.setLocationRelativeTo(this);// khung hien thi giua man hinh
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//thoat chuong trinh 

        //add component
        ImageIcon image = new ImageIcon(getClass().getResource("/Image/Cocaro.jpg"));
        lbImage = new JLabel(image);
        lbImage.setBounds(0, 0, 360, 165);
        this.add(lbImage);

        btnSingle = new JButton("SinglePlayer");
        btnSingle.setBounds(80, 190, 200, 40);
        this.add(btnSingle);

        btnMuti = new JButton("MutiPlayer");
        btnMuti.setBounds(80, 250, 200, 40);
        this.add(btnMuti);

        btnGui = new JButton("Hướng dẫn");
        btnGui.setBounds(80, 310, 200, 40);
        this.add(btnGui);

        btnAbout = new JButton("About");
        btnAbout.setBounds(80, 370, 200, 40);
        this.add(btnAbout);

        btnExit = new JButton("Exit");
        btnExit.setBounds(80, 430, 200, 40);
        this.add(btnExit);
//        this.repaint();
//        this.revalidate();

        //add ActionLister
        btnSingle.addActionListener(this);//add bo nghe cho cac nut
        btnMuti.addActionListener(this);
        btnGui.addActionListener(this);
        btnAbout.addActionListener(this);
        btnExit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSingle) {
            this.dispose();                         //thoat cua so hien tai
            FrameSingle chess = new FrameSingle();//khi an bam nut tạo ra 1 bang moi
            chess.setVisible(true);
        }
        if (e.getSource() == btnMuti) {
            this.dispose();                         //thoat cua so hien tai
            FrameMuti chess = new FrameMuti();
            chess.setVisible(true);
        }

        if (e.getSource() == btnGui) {
            JOptionPane.showMessageDialog(null, "Người thắng là người đầu tiên có chuỗi liên tục 5 quân trên cùng một hàng ngang, hàng dọc hoặc hàng chéo \nChú ý: Chặn hai đầu không thắng", "Hướng dẫn", 1);
        }
        if (e.getSource() == btnAbout) {
            JOptionPane.showMessageDialog(null, "GameCaro Nhóm 6\n" + "Môn Lập trình nâng cao", "Thông tin", 1);
        }
        if (e.getSource() == btnExit) {
            System.exit(0);
        }
    }

}
