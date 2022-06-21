/* 
    Archivo: JuegoMoli.java
    fecha: 08/06/2022
    version: 1.0.0
    integrantes: 
        Jose Fabian Virguez Gomez
    descripcion: 
        El juego consiste en tener un escenario con algunos agujeros dispersos en la pista,
        3 vidas por jugador y en un costado de la pantalla el sistema nos debe indicar cual topo es el que debemos golpear, 
        el sistema nos va a mostrar en la pista topos aleatorios, debemos golpearlo antes que desaparezca también nos va a arrojar trampas 
        como topos con explosivos y topos con casco, estos últimos deben requerir dos toques para ser eliminado, 
        esto con el fin de equivocarnos y vayamos perdiendo vidas. La idea es hacer la mayor cantidad de puntos posibles.
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Moli extends JFrame {

    // Declaracion de variables
    private JPanel panel;
    private JLabel[] holes = new JLabel[16];
    private int[] board = new int[16];

    private int score = 0;
    private int timeLeft = 30;
    private int highscore = 0;

    private JLabel lblScore;
    private JLabel lblTimeLeft;
    private JLabel lblHighscore;

    private JButton btnStart;

    private Timer timer;

    // Funcion encargada de la carga de imagenes
    private ImageIcon loadImage(String path){
        Image image = new ImageIcon(this.getClass().getResource(path)).getImage();
        Image scaledImage = image.getScaledInstance(132, 132, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
    // Funcion encargada generar un randon para mostrar el moli en tablero
    private void genRandMole(){
        Random rnd = new Random(System.currentTimeMillis());
        int moleID = rnd.nextInt(16);
        board[moleID] = 1;
        holes[moleID].setIcon(loadImage("/TopoArriba.png"));
    }

    // Funcion encargada de generar un nuevo tablero
    private void clearBoard() {
        for (int i = 0; i < 16; i++) {
            holes[i].setIcon(loadImage("/TopoAbajo.png"));
            board[i] = 0;
        }
    }

    // Funcion encargada de agregar evento del click del mouse
    private void initEvents() {
        for (int i = 0; i < holes.length; i++){
            holes[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JLabel lbl = (JLabel)e.getSource();
                    int id = Integer.parseInt(lbl.getName());
                    pressedButton(id);
                }
            });
        }

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (timeLeft == 0){
                    lblTimeLeft.setText("" + timeLeft);
                    timer.stop();
                    gameOver();
                }
                lblTimeLeft.setText("" + timeLeft);
                timeLeft--;
            }
        });

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnStart.setEnabled(false);
                clearBoard();
                genRandMole();
                timer.start();
            }
        });
    }

    // Funcion encargada de calcular el puntaje
    private void pressedButton(int id) {
        int val = board[id];

        //if val is 1 = mole, if val is 0 = empty hole
        if (val==1){
            score++;
        }else{
            score--;
        }

        lblScore.setText("Score: " + score);

        clearBoard();

        genRandMole();
    }

    // Funcion encargada de validar si el juego termino
    private void gameOver() {
        btnStart.setEnabled(true);
        if(score > highscore){
            highscore = score;
            lblHighscore.setText("Highscore: " + highscore);
            JOptionPane.showMessageDialog(this, "Su puntaje final es: " + score, "Su puntaje más alto!", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(this, "Su puntaje final es: " + score, "Juego terminado!", JOptionPane.INFORMATION_MESSAGE);
        }
        score = 0;
        timeLeft = 30;
        lblScore.setText("Score: 0");
        lblTimeLeft.setText("30");

        clearBoard();
    }

    // Funcion encargada de configurar el juego para iniciar 
    private void initGUI() {
        panel = new JPanel();

        setTitle("Juego Moli");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 608, 720);

        JPanel contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 51, 0));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);

        setContentPane(contentPane);

    
        JLabel lblTitle = new JLabel("Juego Moli");
        lblTitle.setForeground(new Color(153, 204, 0));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Century Gothic", Font.BOLD, 20));
        lblTitle.setBounds(0, 0, 602, 47);

        contentPane.add(lblTitle);

        panel = new JPanel();
        panel.setBackground(new Color(0, 102, 0));
        panel.setBounds(32, 105, 535, 546);
        panel.setLayout(null);
        contentPane.add(panel);

        holes[0] = new JLabel("0");
        holes[0].setName("0");
        holes[0].setBounds(0, 396, 132, 132);
        panel.add(holes[0]);

        holes[1] = new JLabel("1");
        holes[1].setName("1");
        holes[1].setBounds(132, 396, 132, 132);
        panel.add(holes[1]);

        holes[2] = new JLabel("2");
        holes[2].setName("2");
        holes[2].setBounds(264, 396, 132, 132);
        panel.add(holes[2]);

        holes[3] = new JLabel("3");
        holes[3].setName("3");
        holes[3].setBounds(396, 396, 132, 132);
        panel.add(holes[3]);

        holes[4] = new JLabel("4");
        holes[4].setName("4");
        holes[4].setBounds(0, 264, 132, 132);
        panel.add(holes[4]);

        holes[5] = new JLabel("5");
        holes[5].setName("5");
        holes[5].setBounds(132, 264, 132, 132);
        panel.add(holes[5]);

        holes[6] = new JLabel("6");
        holes[6].setName("6");
        holes[6].setBounds(264, 264, 132, 132);
        panel.add(holes[6]);


        setContentPane(contentPane);

        lblScore = new JLabel("Score: 0");
        lblScore.setHorizontalAlignment(SwingConstants.TRAILING);
        lblScore.setFont(new Font("Cambria", Font.BOLD, 14));
        lblScore.setForeground(new Color(135, 206, 250));
        lblScore.setBounds(423, 54, 144, 33);
        contentPane.add(lblScore);

        lblTimeLeft = new JLabel("30");
        lblTimeLeft.setHorizontalAlignment(SwingConstants.CENTER);
        lblTimeLeft.setForeground(new Color(240, 128, 128));
        lblTimeLeft.setFont(new Font("Cambria Math", Font.BOLD, 24));
        lblTimeLeft.setBounds(232, 54, 144, 33);
        contentPane.add(lblTimeLeft);

        lblHighscore = new JLabel("Highscore: 0");
        lblHighscore.setHorizontalAlignment(SwingConstants.TRAILING);
        lblHighscore.setForeground(new Color(255, 255, 0));
        lblHighscore.setFont(new Font("Cambria", Font.BOLD, 14));
        lblHighscore.setBounds(433, 18, 134, 33);
        contentPane.add(lblHighscore);

        btnStart = new JButton("Start");
        btnStart.setBackground(Color.WHITE);
        btnStart.setBounds(32, 60, 110, 33);
        contentPane.add(btnStart);

        panel.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                loadImage("/moli.png").getImage(),
                new Point(0,0),"custom cursor1"));
    }


    public Moli() {
        initGUI();
        clearBoard();
        initEvents();
    }

    // metodo de inicialización
    public static void main(String[] args) {
        Game frame = new Moli();
        frame.setVisible(true);
    }
}