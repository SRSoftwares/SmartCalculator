package calculator;

import com.sun.awt.AWTUtilities;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by IntelliJ IDEA.
 * Author: Sumit Roy
 * Date: 8/1/11
 * Time: 5:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class TransparencyDialog extends JDialog {
    private JPanel mainPanel;
    private JFrame parentFrame;
    private int min;
    private int max;
    private int init;
    private Calculator calculator;
    private JSlider transSlider;
    private String titleText;

    TransparencyDialog(Calculator calc) {
        super(calc);
        titleText = "Make Transparent, SmartCalculator v"+Calculator.versionId+"     Transparency ";
        parentFrame = calc;
        calculator = calc;
        int parPosX = (int) calc.getLocation().getX();
        int parPosY = (int) calc.getLocation().getY();

        min = 0;
        max = 100;
        init = calc.getSliderVal();

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(border, "Choose Transparency Level");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        parentFrame.setEnabled(false);
        mainPanel.setBorder(titledBorder);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        this.setSize(435, 130);
        this.setLocation(parPosX - 20, parPosY + 75);
        this.createMainPanel();
        int v = transSlider.getValue();
        this.setTitle(titleText + " " + v + " %");
        mainPanel.requestFocus();
        this.add(mainPanel, BorderLayout.CENTER);
        this.setResizable(false);
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                parentFrame.setEnabled(true);
                calculator.setSliderVal(transSlider.getValue());

            }
        });

        Image image = null;
        try {
            image = ImageIO.read(this.getClass().getResource("transparent.png"));
        } catch (Exception e) {

        }
        this.setIconImage(image);

    }

    private void createMainPanel() {
        this.setBackground(parentFrame.getBackground());

        float val = (float) (100 - init) / 100;
        this.setBackground(Color.WHITE);
        mainPanel.setBackground(Color.WHITE);
        transSlider = new JSlider(JSlider.HORIZONTAL, min, max, init);
        AWTUtilities.setWindowOpacity(TransparencyDialog.this, val);
        mainPanel.updateUI();
        transSlider.setMajorTickSpacing(10);
        transSlider.setMinorTickSpacing(5);
        transSlider.setPaintLabels(true);
        transSlider.setPaintLabels(true);
        transSlider.setToolTipText("Transparency Level "+(transSlider.getValue())+" %");
        Font font = new Font("Arial", Font.BOLD, 10);
        transSlider.setFont(font);
        transSlider.setBackground(Color.WHITE);
        mainPanel.add(transSlider, BorderLayout.CENTER);
        transSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider tempSlider = (JSlider) e.getSource();
                float f = tempSlider.getValue();
                doTransparent(f);
                transSlider.setToolTipText("Transparency Level "+(transSlider.getValue())+" %");
            }
        });
        transSlider.addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                int rotation = e.getWheelRotation();
                int val = transSlider.getValue();
                if (rotation > 0 && val <= 100) {


                    transSlider.setValue(++val);

                    TransparencyDialog.this.mainPanel.updateUI();
                } else if (rotation < 0 && val >= 0) {
                    transSlider.setValue(--val);
                    TransparencyDialog.this.mainPanel.updateUI();

                }
            }
        });


    }

    /*  public static void main(String[] args) {
        TransparencyDialog obj = new TransparencyDialog(550, 430);
    }*/

    private void doTransparent(float f) {
        float val = (float) (100 - f) / 100;
        AWTUtilities.setWindowOpacity(TransparencyDialog.this, val);
        AWTUtilities.setWindowOpacity(parentFrame, val);
        TransparencyDialog.this.mainPanel.updateUI();
        int v = transSlider.getValue();
        this.setTitle(titleText + " " + v + " %");

        calculator.statusLevel2.setText("Transparent : " + v + " %");

    }


}
