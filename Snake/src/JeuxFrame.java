import javax.swing.*;

public class JeuxFrame extends JFrame {

    JeuxFrame(){
        /* GamePanel panel = new GamePanel();
         this.add(panel)
         */
        this.add(new JeuxPanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }
}
