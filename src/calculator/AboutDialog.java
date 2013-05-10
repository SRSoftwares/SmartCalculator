package calculator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * Author: Sumit Roy
 * Date: 7/16/11
 * Time: 4:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class AboutDialog extends JDialog {
    private JPanel mainPanel;
    private JPanel imgPanel;


    AboutDialog(Calculator parent) {
        super(parent);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setTitle("About the SmartCalculator");
        Image image = null;
        try {
            image = ImageIO.read(this.getClass().getResource("about.png"));
        } catch (Exception e) {

        }
        this.setIconImage(image);

        this.setSize(376, 380);
        this.setLocationRelativeTo(parent);
        this.setLayout(new BorderLayout());
        this.createAboutDialog();
        this.setResizable(false);


        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int w = e.getWindow().getSize().width;
                int h = e.getWindow().getSize().height;
             // System.out.println("Optimized View : Width = " + w + "\n\nHeight = " + h);
                AboutDialog.this.getParent().setEnabled(true);

            }
        });

        this.setVisible(true);
    }

    public void createAboutDialog() {
        Image image = null;
        try {
            image = ImageIO.read(AboutDialog.class.getResource("logo.png"));

            imgPanel = new ImageBackgroundPanel(image,65,10);
            imgPanel.setBackground(Color.WHITE);
            this.add(imgPanel, BorderLayout.CENTER);

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        JLabel label = new JLabel();

        String msgText = "About The SmartCalculator Version " + Calculator.versionId;
        String msgText2 = "Coded Using Java Swing, JDK : v6.26";
        String msgText3 = "Developed By : Sumit Roy <br> <h4 style = \"text-align : center;color : \t#800080;\"> Visit us at - https://www.srsoftwares.co.nr ";
        String copyRt = String.valueOf('\u00A9');
        String trademark = String.valueOf('\u2122');
        String msgText4 = "  Copy Rights " + copyRt + " 2011, SR Softwares"+trademark+", All Rights Reserved ";

        label.setText("<html>" + "<h3 style = \"text-align :center ; color : \t#8B0000 \">" + msgText + "<br>" + "<h4 style = \"text-align : center;color : \t#800080;\">" + msgText2 + "<h4 style = \"text-align : center;color : \t#800080;\">" + msgText3 + "<h4 style = \"text-align : center;color : #FF0000 ;\">" + msgText4 + "</html>");

        mainPanel.add(label, JLabel.CENTER);

        this.add(mainPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        // AboutDialog obj = new AboutDialog(571, 376);
    }

}
