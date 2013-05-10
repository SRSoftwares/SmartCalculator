package calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by IntelliJ IDEA.
 * Author: Sumit Roy
 * Date: 7/16/11
 * Time: 3:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class HelpDialog extends JDialog {
    private JPanel mainPanel;

    HelpDialog(int parWidth, int parHeight) {

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setTitle("Help");
        this.setIconImage(new ImageIcon("help.png").getImage());
        this.setResizable(false);
        this.setSize(464, 235);

        this.setLocation(parWidth, parHeight);

        this.createHelpDialog();
        this.add(mainPanel, BorderLayout.CENTER);
        mainPanel.updateUI();
        this.setVisible(true);


        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int w = e.getWindow().getSize().width;
                int h = e.getWindow().getSize().height;
                System.out.println("Optimum " + w + "," + h);
            }
        });
    }

    public void createHelpDialog() {
        JLabel label = new JLabel();

        String msgText = "Thank you For using this Handy Calculator..";
        String msgText2 = "  If you found any bugs in this or you wanna suggest some new Cool Feature, then you are most WelCome";
        String msgText3 = " For any Suggestion/Query write to me at: sanku.sumit@gmail.com , or Visit my site :";
        String msgText4 = " https://www.sumitroy.co.nr";

        String msgText5 = "Designed By : Sumit Roy, Release Date : July 18th, 2011, Version " + Calculator.versionId;

        label.setText("<html>" + "<h3 style = \"text-align :center ; color : \t#8B0000 \">" + msgText + "<br>" + "<h4 style = \"text-align : center;color : \t#800080;\">" + msgText2 + "<h4 style = \"text-align : center;color : \t#800080;\">" + msgText3 + "<h4 style = \"text-align : center;color : #FF0000 ;\">" + "<a href=" + msgText4 + ">https://www.sumitroy.co.nr</a> " + "<h4 style = \"text-align : center;color : #006400 ;\">" + msgText5 + "</html>");
        label.setBackground(Color.WHITE);
        mainPanel.add(label, JLabel.CENTER);
    }

    public static void main(String[] args) {
        HelpDialog obj = new HelpDialog(500, 100);
    }
}
