package calculator;

import javax.imageio.ImageIO;
import javax.swing.*;
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
 * Date: 8/18/11
 * Time: 6:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class HoverDuration extends JDialog {
    private Dimension frameDimension;
    private String title;
    private JPanel mainPanel;
    private JLabel mainLabel;
    private JSpinner hoverSpinner;
    private String[] hoverVal;
    private SpinnerListModel hoverModel;

    HoverDuration(final Calculator parent) {
        super(parent);
        parent.setEnabled(false);
        hoverVal = new String[]{"1 Sec ", "2 Sec ", "3 Sec ", "4 Sec ", "5 Sec "};
        title = "Change Hover Duration, SmartCalculator v" + Calculator.versionId;
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setTitle(title);
        frameDimension = new Dimension(270, 80);
        this.setSize(frameDimension);
        this.setBackground(Color.WHITE);
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainLabel = new JLabel("Choose Hover Duration");
        mainLabel.setBackground(Color.WHITE);
        mainLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        hoverModel = new SpinnerListModel(hoverVal);
        hoverSpinner = new JSpinner(hoverModel);

        int v = parent.getHoverDuration();
        hoverSpinner.setValue(v + " Sec ");
        hoverSpinner.setSize(new Dimension(100, 25));
        hoverSpinner.setMaximumSize(new Dimension(100, 25));
        hoverSpinner.setMinimumSize(new Dimension(100, 25));

        JFormattedTextField tf = ((JSpinner.DefaultEditor) hoverSpinner.getEditor()).getTextField();
        tf.setBackground(Color.WHITE);
        tf.setEditable(false);

        mainPanel.add(Box.createHorizontalStrut(10));
        mainPanel.add(mainLabel);
        mainPanel.add(Box.createHorizontalStrut(10));
        mainPanel.add(hoverSpinner);
        mainPanel.add(Box.createHorizontalStrut(10));
        mainPanel.updateUI();

        Image image = null;
        try {
            image = ImageIO.read(this.getClass().getResource("hover.png"));
        } catch (Exception e) {

        }
        this.setIconImage(image);


        this.setLayout(new BorderLayout());
        this.add(mainPanel, BorderLayout.CENTER);
        this.setLocationRelativeTo(parent);
        this.setResizable(false);
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int w = e.getWindow().getSize().width;
                int h = e.getWindow().getSize().height;
                //  System.out.println("Optimized View : Width = " + w + "\n\nHeight = " + h);
                int val = Integer.parseInt(String.valueOf(hoverSpinner.getValue().toString().charAt(0)));
                parent.setHoverDuration(val);

                parent.setEnabled(true);

            }
        });

        hoverSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                String val = hoverSpinner.getValue().toString().trim();
                parent.statusLevel3.setText("Hover Duration : "+val);
            }
        });

        hoverSpinner.addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                int rotation = e.getWheelRotation();
                int val = Integer.parseInt(String.valueOf(hoverSpinner.getValue().toString().charAt(0)));

                if (rotation > 0) {
                    if (val > 1) {
                        hoverSpinner.setValue(hoverSpinner.getPreviousValue());
                    }
                } else {
                    if (val < 5) {
                        hoverSpinner.setValue(hoverSpinner.getNextValue());
                    }
                }
            }
        });

    }

    /*public static void main(String[] args) {
        HoverDuration obj = new HoverDuration();
    }*/
}
