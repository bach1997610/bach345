/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import Controller.StateGame;
import controller.Computer;
import controller.MyConst;
import static controller.MyConst.STATE_HOA;
import static controller.MyConst.STATE_NONE;
import static controller.MyConst.STATE_THANG;
import static controller.MyConst.STATE_THUA;
import controller.Player;
import controller.TCPClient;
import controller.TCPServer;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FrameMuti extends JFrame implements ActionListener {

    private JButton btn[][] = new JButton[20][20];
    private JPanel pnlCtr, pnlChess;
    private JButton btnBack, btnCreat, btnShow;
    private JLabel lbIP, lbInfor;
    private JTextField tfIP;
    private JLabel lbImage;
    private JTextField txtFieldPlayer1, txtFieldPlayer2;
    private JTextField tfCurrent;
    private JTextField tfTiSo;

    public FrameMuti() {
        inti();
        btnBack.addActionListener(this);
        btnCreat.addActionListener(this);
        btnShow.addActionListener(this);
    }

    private void inti() {
        //khoi tao khung fame
        this.setTitle("Game Caro");
        this.setLayout(null);
        this.setSize(1020, 650);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// thoat han chuong trinh khi bam thoat
        this.setLocationRelativeTo(this);

        //add component
        ImageIcon image = new ImageIcon(getClass().getResource("/Image/Cocaro.jpg"));
        lbImage = new JLabel(image);
        lbImage.setBounds(0, 0, 390, 250);
        this.add(lbImage);

        drawChess();

        //add pnlCtr      
        pnlCtr = new JPanel(null);
        pnlCtr.setBounds(0, 0, 390, 600);

        lbInfor = new JLabel("-----------------------------------Mutiplayer---------------------------");
        lbInfor.setBounds(10, 370, 390, 50);
        pnlCtr.add(lbInfor);

        lbIP = new JLabel("IP :");
        lbIP.setBounds(10, 420, 50, 30);
        pnlCtr.add(lbIP);

        tfIP = new JTextField();
        tfIP.setBounds(100, 420, 270, 30);
        pnlCtr.add(tfIP);

        btnCreat = new JButton("Tạo phòng");
        btnCreat.setBounds(10, 470, 150, 50);
        pnlCtr.add(btnCreat);

        btnShow = new JButton("Tìm phòng");
        btnShow.setBounds(220, 470, 150, 50);
        pnlCtr.add(btnShow);

        btnBack = new JButton("Back");
        btnBack.setBounds(10, 550, 150, 50);
        pnlCtr.add(btnBack);

        //add thong tin nguoi choi
        txtFieldPlayer1 = new JTextField();
        txtFieldPlayer1.setBounds(10, 350, 180, 30);
        txtFieldPlayer1.setBackground(Color.white);
        txtFieldPlayer1.setEditable(false);
        this.add(txtFieldPlayer1);

        txtFieldPlayer2 = new JTextField();
        txtFieldPlayer2.setBounds(190, 350, 180, 30);
        txtFieldPlayer2.setBackground(Color.white);
        txtFieldPlayer2.setEditable(false);
        this.add(txtFieldPlayer2);

        tfCurrent = new JTextField();
        tfCurrent.setBounds(50, 320, 240, 30);
        tfCurrent.setEditable(false);
        this.add(tfCurrent);

        this.add(pnlCtr);
    }

    private void drawChess() {
        //add pnlChess
        pnlChess = new JPanel(null);
        pnlChess.setBounds(400, 0, 600, 600);
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                btn[i][j] = new JButton();
                btn[i][j].setBounds(i * 30, j * 30, 30, 30);
                btn[i][j].setBackground(Color.white);
                pnlChess.add(btn[i][j]);

                data[i][j] = 0;
                btn[i][j].addActionListener(this);
            }
        }
        this.add(pnlChess);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (e.getSource() == btn[i][j]) {
                    //lay toa do nut bam
                    point.x = i;
                    point.y = j;
                    //
                    vsPlayer();
                }
            }
        }
        if (e.getSource() == btnBack) {
            this.dispose();           //thoat cua so hien tai
            HomeGame homeGame = new HomeGame();
            homeGame.setVisible(true);
        }
        if (e.getSource() == btnCreat) {
            btnCreatClick();
        }
        if (e.getSource() == btnShow) {
            btnShowClick();
        }
    }

    private void reDrawChess() {
        pnlChess.setVisible(false);                     //Tắt bàn cờ hiện tại
        drawChess();                                    //Vẽ lại bàn cờ 
        this.add(pnlChess);                              //Thêm vào panel chính
        this.revalidate();                               //Update
        this.repaint();                                  //Vẽ lại
    }

    private void vsPlayer() {
        if (data[point.x][point.y] != 0 || isPlay == false) {
            return;
        }
        if (flag) {
            you.play(btn[point.x][point.y]);
            data[point.x][point.y] = 1;
            flag = !flag;
            tfCurrent.setText("Doi doi thu danh");
            //kiem tra trang thai game
            stateGame = check.stateGame(point, data);
            endGame(stateGame, you.name);
            if (isServer) {
                server.sentData(point.x + " " + point.y);
            } else {
                client.sentData(point.x + " " + point.y);
            }
        }
    }

    private void endGame(int stateGame, String namePlayer) {
        /*
         B1: Nếu kết thúc game ==> thông báo trạng thái game
         B2: Vẽ lại bàn cờ
         */
        switch (stateGame) {
            case STATE_THANG:
                //Bước 1
                JOptionPane.showMessageDialog(null, "Chúc mừng " + namePlayer + " win", "Chúc mừng", 1);
                //Bươc 2
                reDrawChess();
                break;
            case STATE_HOA:
                //Bước 1
                JOptionPane.showMessageDialog(null, "hai bên HÒA", "Thông báo", 1);
                //Bước 2
                reDrawChess();
                break;

            case STATE_THUA:
                JOptionPane.showMessageDialog(null, "Ban thua", "Thông báo", 1);
                //Bước 2
                reDrawChess();
                break;
            case STATE_NONE:
                break;
        }
    }
//--------------------------

    private void btnCreatClick() {
        server = new TCPServer();
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String s = addr.getHostAddress();
            tfIP.setText(s);
        } catch (UnknownHostException ex) {

        }

        threadRead();
    }

    private void btnShowClick() {
        flag = false;
        client = new TCPClient();
        isServer = false;
        client.connect();
        if (client.isConnect) {
            isPlay = true;
            threadRead();
        }
    }

    private void process(String str) {
        String s[] = str.split(" ");
        int x = Integer.parseInt(s[0]);
        int y = Integer.parseInt(s[1]);
        point = new Point(x, y);

        doiThu.play(btn[point.x][point.y]);
        data[point.x][point.y] = 2;
        flag = !flag;
        tfCurrent.setText("den luot ban");
        //kiem tra trang thai game
        stateGame = check.stateGame(point, data);
        //
        if (stateGame == STATE_THANG) {
            endGame(STATE_THUA, doiThu.name);
        } else if (stateGame == STATE_THUA) {
            endGame(STATE_THANG, doiThu.name);
        } else {
            endGame(stateGame, doiThu.name);
        }

    }

    private void threadRead() {
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                if (isServer) {
                    tfCurrent.setText("Dang cho nguoi choi khac");
                    server.connect();
                    tfCurrent.setText("Doi thu da vao phong");
                    isPlay = true;
                    while (true) {
                        try {
                            server.readData();
                            process(server.data);
                        } catch (IOException ex) {

                        }
                    }
                } else {
                    while (true) {
                        try {
                            client.readData();
                            process(client.data);
                        } catch (IOException ex) {

                        }
                    }
                }
            }
        });
        t.start();
    }
//--------------------------
    private Point point = new Point();          //lấy tạo độ nút bấm
    private boolean flag = true;                       //cờ chuyển lượt chơi
    private Player you = new Player("Player1", new ImageIcon(getClass().getResource("/Image/X.jpg")));
    private Player doiThu = new Player("Player2", new ImageIcon(getClass().getResource("/Image/O.jpg")));
    private StateGame check = new StateGame();
    private int stateGame = -1;
    private char data[][] = new char[20][20];
    TCPClient client;
    TCPServer server;
    boolean isServer = true;
    boolean isPlay = false;

}
