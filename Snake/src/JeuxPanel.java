import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


public class JeuxPanel extends JPanel implements ActionListener{

    static final int SCREEN_LARGEUR = 600;
    static final int SCREEN_HAUTEUR = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_LARGEUR*SCREEN_HAUTEUR)/(UNIT_SIZE*UNIT_SIZE);
    static final int DELAY = 100;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int snakeBody = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    JeuxPanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_LARGEUR,SCREEN_HAUTEUR));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame() {
        nouvellePomme();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {

        if(running) {

            g.setColor(Color.yellow);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for(int i = 0; i< snakeBody;i++) {
                if(i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else {
                    g.setColor(new Color(45,180,0));
                    //g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.white);
            g.setFont( new Font("Ink Free",Font.BOLD, 40));
            g.drawString("Score: "+applesEaten, 5, 35);
        }
        else {
            FinduJeux(g);
        }

    }
    public void nouvellePomme(){
        appleX = random.nextInt((int)(SCREEN_LARGEUR/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HAUTEUR/UNIT_SIZE))*UNIT_SIZE;
    }
    public void deplacement(){
        for(int i = snakeBody;i>0;i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch(direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }
    public void checkApple() {
        if((x[0] == appleX) && (y[0] == appleY)) {
            snakeBody++;
            applesEaten++;
            nouvellePomme();
        }
    }
    public void checkCollisions() {
        //checks if head collides with body
        for(int i = snakeBody;i>0;i--) {
            if((x[0] == x[i])&& (y[0] == y[i])) {
                running = false;
            }
        }
        //check if head touches left border
        if(x[0] < 0) {
            running = false;
        }
        //check if head touches right border
        if(x[0] > SCREEN_LARGEUR) {
            running = false;
        }
        //check if head touches top border
        if(y[0] < 0) {
            running = false;
        }
        //check if head touches bottom border
        if(y[0] > SCREEN_HAUTEUR) {
            running = false;
        }

        if(!running) {
            timer.stop();
        }
    }
    public void FinduJeux(Graphics g) {
        //Score
        g.setColor(Color.red);
        g.setFont( new Font("Ink Free",Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten, (SCREEN_LARGEUR - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
        //Game Over text
        g.setColor(Color.red);
        g.setFont( new Font("Ink Free",Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Perdu !", (SCREEN_LARGEUR - metrics2.stringWidth("Perdu !"))/2, SCREEN_HAUTEUR/2);

        g.setColor(Color.white);
        g.setFont( new Font("arial",Font.BOLD, 25));
        g.drawString("Appuie sur espace pour Rejouer", 110,400);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if(running) {
            deplacement();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
