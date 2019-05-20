/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import Controller.StateGame;
import controller.Computer;
import static controller.MyConst.STATE_HOA;
import static controller.MyConst.STATE_NONE;
import static controller.MyConst.STATE_THANG;
import static controller.MyConst.STATE_THUA;
import controller.Player;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FrameSingle extends JFrame implements ActionListener {

    private JButton btn[][] = new JButton[20][20];
    private JPanel pnlCtr, pnlChess;
    private JLabel lbLuotDi, lbInfor, lbLevel;
    private JButton btnStart, btnBack;
    private JCheckBox checkBox;
    private JLabel lbImage;
    private JTextField txtFieldPlayer1, txtFieldPlayer2;
    private JTextField tfTiSo;
    private JComboBox boxLevel;
    private DefaultComboBoxModel boxModellevel;

    public FrameSingle() {
        inti();
        btnStart.addActionListener(this);
        btnBack.addActionListener(this);
    }

    private void inti() {
        //khoi tao khung fame
        this.setTitle("Game Caro");
        this.setLayout(null);//giup mình tự thiết kế các nút bấm
        this.setSize(1020, 650);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// thoat han chuong trinh khi bam thoat
        this.setLocationRelativeTo(this);//khung hien thi giua man hinh

        //add component
        ImageIcon image = new ImageIcon(getClass().getResource("/Image/Cocaro.jpg"));
        lbImage = new JLabel(image);
        lbImage.setBounds(0, 0, 390, 250);
        this.add(lbImage);

        drawChess();

        //add pnlCtr
        pnlCtr = new JPanel(null);
        pnlCtr.setBounds(0, 0, 390, 600);

        lbInfor = new JLabel("---------SinglePlayer-----------");
        lbInfor.setBounds(100, 400, 200, 50);
        pnlCtr.add(lbInfor);

        lbLevel = new JLabel("Level");
        lbLevel.setBounds(10, 450, 100, 30);
        pnlCtr.add(lbLevel);

        boxModellevel=new DefaultComboBoxModel();
        boxLevel=new JComboBox(boxModellevel);
        //đưa dữ liệu vào box là đưa vào boxModel
        boxModellevel.addElement("Normal");
        boxModellevel.addElement("Hard ");
        boxLevel.setBounds(100, 450, 220, 30);
        pnlCtr.add(boxLevel);

        lbLuotDi = new JLabel("Chọn đi trước");
        lbLuotDi.setBounds(10, 500, 100, 30);
        pnlCtr.add(lbLuotDi);

        checkBox = new JCheckBox();
        checkBox.setBounds(150, 500, 50, 30);
        pnlCtr.add(checkBox);

        btnStart = new JButton("Start");
        btnStart.setBounds(220, 550, 150, 50);
        pnlCtr.add(btnStart);

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
                    //lay tao do nut bam
                    point.x = i;
                    point.y = j;
                    //
                    vsCom();
                }
            }
        }
        if (e.getSource() == btnStart) {
            initCheDoChoi();
        }
        if (e.getSource() == btnBack) {
            this.dispose();
            HomeGame homeGame = new HomeGame();
            homeGame.setVisible(true);
        }
    }

    private void initCheDoChoi() {
        /*
         B1: Nhập tên
         B2: Khởi tạo người chơi
         B3: Vẽ lại bàn cờ
a1         */
        // Bước 1
        String sTen = JOptionPane.showInputDialog(
                null,
                "Nhập tên của bạn",
                "Nhập thông tin",
                JOptionPane.QUESTION_MESSAGE
        );
        //Bước 2
        you = new Player(sTen);
        level = boxLevel.getSelectedIndex();
        computer = new Computer(level);

        txtFieldPlayer1.setText("You: " + you.name);
        txtFieldPlayer2.setText("Đối thủ: Computer");
        //Bước 3
        reDrawChess();
    }

    private void reDrawChess() {
        pnlChess.setVisible(false);                     //Tắt bàn cờ hiện tại
        drawChess();                                    //Vẽ lại bàn cờ 
        this.add(pnlChess);                              //Thêm vào panel chính
        this.revalidate();                               //Update
        this.repaint();                                  //Vẽ lại
        //nếu player đi sau thì khởi tạo luôn computer đánh ô (9,9 )
        if (!checkBox.isSelected()) {
            btn[9][9].setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/O.jpg")));
            data[9][9] = 2;
        }
    }

    private void vsCom() {
        /*
         B1: Người chơi đánh 
         B2: Kiểm tra trạng thái game
         B3: Computer đánh
         B4: Kiểm tra trạng thái game
         */
        //Kiểm tra xem người chơi có đánh vào ô đã đánh rồi không
        if (data[point.x][point.y] != 0) {
            return;
        }
        //---------------------------Bước 1-------------------------------------
        you.play(btn[point.x][point.y]);
        data[point.x][point.y] = 1;
        //---------------------------Bước 2-------------------------------------
        stateGame = check.stateGame(point, data);
        if (stateGame == STATE_THANG || stateGame == STATE_HOA) {
            endGame(stateGame, you.name);
            return;
        }
        //---------------------------Bước 3-------------------------------------
        switch (computer.level) {
            //computer level normal
            case 0: {
                point = computer.StepCom(data);          // computer tim nuoc di
                computer.ComputerGo(point, btn);
                data[point.x][point.y] = 2;
                break;
            }
            //computer level hard
            case 1: {
                break;
            }
        }
        //------------------------Bước 4----------------------------------------
        stateGame = check.stateGame(point, data);
        endGame(stateGame, "Computer");
    }

    private void endGame(int stateGame, String namePlayer) {
        /*
         B1: Nếu kết thúc game ==> thông báo trạng thái game + tính điểm cho người chơi
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

            case STATE_THUA:        //chỉ có chế độ chơi 2 mới có
                //Bước 1
                JOptionPane.showMessageDialog(null, "Bạn " + you.name + " LOST ", "Thông báo", 1);
                //Bước 2: Vẽ lại bàn cờ
                reDrawChess();
                break;
            case STATE_NONE:
                break;
        }
    }

    private Point point = new Point();          //lấy tạo độ nút bấm
    private char luotDi;
    private boolean flag = true;                       //cờ chuyển lượt chơi
    private Player you;
    private Computer computer;
    private StateGame check = new StateGame();
    private int stateGame = -1;
    private int level = -1;
    private char data[][] = new char[20][20];
}
