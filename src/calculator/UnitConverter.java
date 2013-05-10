package calculator;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

/**
 * Created by IntelliJ IDEA.
 * Author: Sumit Roy
 * Date: 7/30/11
 * Time: 2:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class UnitConverter extends JDialog implements CaretListener {
    private String title;
    private JComboBox convertBox;
    private Dimension frameDimension;
    private Dimension comboBoxDimension;
    private Dimension textFieldDimension;
    private JPanel parentPanel;
    private JComboBox inputBox;
    private JComboBox outputBox;
    private JTextField outputField;
    private JTextField inputField;
    private String[] convertItems;
    private String[] lengthItem;
    private String[] speedItem;
    private String[] wightItem;
    private String[] baseItem;
    private JPanel semiCentralPanel;
    private JPanel fromPanel;
    private JPanel toPanel;
    private JLabel statusLevel;
    private String status;
    private String inputHexVal;
    private double inputValue;
    private JPanel centralPanel;

    UnitConverter(final Calculator parent) {



        super(parent);
        parent.setEnabled(false);

        title = "Unit Converter, SmartCalculator v" + Calculator.versionId;
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setTitle(title);
        frameDimension = new Dimension(370, 200);
        comboBoxDimension = new Dimension(100, 25);
        textFieldDimension = new Dimension(160, 25);
        this.setSize(frameDimension);
        status = "Status : ";
        statusLevel = new JLabel(status, JLabel.RIGHT);
        statusLevel.setFont(new Font("Times New Roman", Font.BOLD, 12));

        convertItems = new String[]{"Length", "Speed", "Weight", "Base"};
        lengthItem = new String[]{"Miles", "Kilometer", "Meter", "Centimeter", "Millimeter", "Inch", "Feet", "Yard"};
        speedItem = new String[]{"Km/hr", "Km/Sec", "Meter/Sec", "Meter/Min", "Miles/hr", "Miles/Sec", "Feet/Sec", "Feet/Min"};
        wightItem = new String[]{"Pound", "Ton", "Kilogram", "Gram", "Milligram", "Ounce"};
        baseItem = new String[]{"Decimal", "Binary", "Octal", "Hexadecimal"};

        parentPanel = new JPanel();
        parentPanel.setLayout(new BorderLayout());
        parentPanel.setBackground(Color.WHITE);
        this.setResizable(false);

        Image image = null;
        try {
            image = ImageIO.read(this.getClass().getResource("converter.png"));
        } catch (Exception e) {

        }
        this.setIconImage(image);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int w = e.getWindow().getSize().width;
                int h = e.getWindow().getSize().height;
                //   System.out.println("WIDTH = " + w + "\n\n HEIGHT = " + h);
                parent.setEnabled(true);

            }
        });


        parentPanel.add(createTopPanel(), BorderLayout.NORTH);
        parentPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        this.add(parentPanel);
        this.setBackground(Color.WHITE);
        this.setLocationRelativeTo((Component) parent);

        this.setVisible(true);


    }

    public static void main(String[] args) {
        UnitConverter con = new UnitConverter(new Calculator());


    }

    private JPanel createTopPanel() {


        JPanel upPanel = new JPanel();
        upPanel.setBackground(Color.WHITE);
        BoxLayout layout = new BoxLayout(upPanel, BoxLayout.X_AXIS);
        upPanel.setLayout(layout);


        JLabel label = new JLabel("Choose Conversion Type", JLabel.CENTER);
        label.setFont(new Font("Times New Roman", Font.BOLD, 16));

        upPanel.add(label);
        upPanel.add(Box.createHorizontalStrut(10));


        convertBox = new JComboBox(convertItems);
        convertBox.setBackground(Color.WHITE);
        convertBox.setSelectedIndex(-1);


        Dimension dim = new Dimension(110, 20);
        convertBox.setPreferredSize(dim);
        convertBox.setMaximumSize(dim);
        convertBox.setMinimumSize(dim);
        convertBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (convertBox.getSelectedIndex() >= 0)
                    UnitConverter.this.handleComboBox();
                else {

                    if (parentPanel.getComponentCount() > 1) {

                        parentPanel.remove(centralPanel);
                        parentPanel.updateUI();
                        frameDimension = new Dimension(370, 200);
                        UnitConverter.this.setSize(frameDimension);
                        parentPanel.updateUI();
                    }


                }
            }
        });
        upPanel.add(convertBox);
        upPanel.add(Box.createHorizontalStrut(5));

        convertBox.addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                int index = convertBox.getSelectedIndex();
                int type = e.getWheelRotation();

                if (type > 0) {
                    if (index < 3) {
                        convertBox.setSelectedIndex(++index);
                        statusLevel.setText(status);
                        statusLevel.setForeground(Color.black);
                    }
                } else {
                    if (index >= 0) {
                        convertBox.setSelectedIndex(--index);
                    }
                }


            }
        });

        JPanel mainPanel = new JPanel();
        BoxLayout mainLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(mainLayout);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(upPanel);

        return mainPanel;
    }

    protected void handleComboBox() {
        String selectedItem = convertBox.getSelectedItem().toString();
        int index = convertBox.getSelectedIndex();
        String items[] = new String[]{};

        switch (index) {
            case 0:
                items = lengthItem;
                break;
            case 1:
                items = speedItem;
                break;
            case 2:
                items = wightItem;
                break;
            case 3:
                items = baseItem;
                break;
        }


        frameDimension = new Dimension(370, 257);
        this.setSize(frameDimension);
        parentPanel.updateUI();
        selectedItem = selectedItem + " Conversion";
        centralPanel = new JPanel(new BorderLayout());
        semiCentralPanel = new JPanel();
        semiCentralPanel.setLayout(new BoxLayout(semiCentralPanel, BoxLayout.Y_AXIS));
        semiCentralPanel.setBackground(Color.WHITE);

        fromPanel = new JPanel();
        fromPanel.setBackground(Color.WHITE);
        fromPanel.setLayout(new BoxLayout(fromPanel, BoxLayout.X_AXIS));

        toPanel = new JPanel();
        toPanel.setBackground(Color.WHITE);
        toPanel.setLayout(new BoxLayout(toPanel, BoxLayout.X_AXIS));


        int i = 1;
        while (i < parentPanel.getComponentCount()) {

            parentPanel.remove(i);
            i++;

            parentPanel.updateUI();
        }


        Border border = BorderFactory.createLineBorder(Color.BLACK);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(border, selectedItem);
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        centralPanel.setBorder(titledBorder);

        JLabel fromLabel = new JLabel("From");
        JLabel toLabel = new JLabel("To");

        fromLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        toLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        fromLabel.setForeground(Color.RED);
        toLabel.setForeground(Color.BLUE);


        inputBox = new JComboBox(items);
        outputBox = new JComboBox(items);

        inputBox.setMaximumSize(comboBoxDimension);
        inputBox.setMinimumSize(comboBoxDimension);
        inputBox.setPreferredSize(comboBoxDimension);

        outputBox.setMaximumSize(comboBoxDimension);
        outputBox.setMinimumSize(comboBoxDimension);
        outputBox.setPreferredSize(comboBoxDimension);

        inputBox.setSelectedIndex(-1);
        outputBox.setSelectedIndex(-1);

        inputField = new JTextField();
        inputField.setEnabled(false);
        inputField.setHorizontalAlignment(JTextField.CENTER);
        inputField.setFont(new Font("Times New Roman", Font.BOLD, 14));
        inputField.setBackground(Color.WHITE);

        outputField = new JTextField();
        outputField.setEditable(false);
        outputField.setHorizontalAlignment(JTextField.CENTER);
        outputField.setFont(new Font("Times New Roman", Font.BOLD, 14));
        outputField.setForeground(Color.BLUE);

        inputField.setMaximumSize(textFieldDimension);
        inputField.setMinimumSize(textFieldDimension);
        inputField.setPreferredSize(textFieldDimension);

        outputField.setMaximumSize(textFieldDimension);
        outputField.setMinimumSize(textFieldDimension);
        outputField.setPreferredSize(textFieldDimension);
        prepareControls();


        fromPanel.add(Box.createHorizontalStrut(5));
        fromPanel.add(fromLabel);
        fromPanel.add(Box.createHorizontalStrut(5));
        fromPanel.add(inputBox);
        fromPanel.add(Box.createHorizontalStrut(10));
        fromPanel.add(inputField);
        fromPanel.add(Box.createHorizontalStrut(5));

        FontMetrics fromLabelMetrics = fromLabel.getFontMetrics(fromLabel.getFont());
        int fromLabelWidth = fromLabelMetrics.stringWidth(fromLabel.getText());

        FontMetrics toLabelMetrics = toLabel.getFontMetrics(toLabel.getFont());
        int toLabelWidth = toLabelMetrics.stringWidth(toLabel.getText());

        toPanel.add(Box.createHorizontalStrut((fromLabelWidth-toLabelWidth)+5));
        toPanel.add(toLabel);
        toPanel.add(Box.createHorizontalStrut(5));
        toPanel.add(outputBox);
        toPanel.add(Box.createHorizontalStrut(10));
        toPanel.add(outputField);
        toPanel.add(Box.createHorizontalStrut(5));

        semiCentralPanel.add(Box.createVerticalStrut(5));
        semiCentralPanel.add(fromPanel);
        semiCentralPanel.add(Box.createVerticalStrut(10));
        semiCentralPanel.add(toPanel);
        semiCentralPanel.add(Box.createVerticalStrut(5));


       Border levelBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED);
        statusLevel.setBorder(levelBorder);


        centralPanel.add(semiCentralPanel, BorderLayout.CENTER);

        centralPanel.add(statusLevel, BorderLayout.SOUTH);

        semiCentralPanel.setBackground(parentPanel.getBackground());
        centralPanel.setBackground(parentPanel.getBackground());
        parentPanel.add(centralPanel, BorderLayout.CENTER);
        parentPanel.updateUI();


    }

    private void prepareControls() {

        outputBox.setEnabled(false);

        inputBox.addMouseWheelListener(new MouseWheelListener() {
            int size = inputBox.getItemCount();
            int selectedIndex = inputBox.getSelectedIndex();

            public void mouseWheelMoved(MouseWheelEvent e) {
                int rotation = e.getWheelRotation();
                if (rotation > 0) {
                    if (selectedIndex < (size - 1)) {
                        inputBox.setSelectedIndex(++selectedIndex);

                    }

                } else {
                    if (selectedIndex >= 0) {
                        inputBox.setSelectedIndex(--selectedIndex);
                    }

                }

            }
        });

        outputBox.addMouseWheelListener(new MouseWheelListener() {


            int size = outputBox.getItemCount();
            int selectedIndex = outputBox.getSelectedIndex();

            public void mouseWheelMoved(MouseWheelEvent e) {
                if (outputBox.isEnabled()) {
                    int rotation = e.getWheelRotation();
                    if (rotation > 0) {
                        if (selectedIndex < (size - 1)) {
                            outputBox.setSelectedIndex(++selectedIndex);
                        }
                    } else {
                        statusLevel.setText(status);
                        if (selectedIndex >= 0) {
                            outputBox.setSelectedIndex(--selectedIndex);
                        }

                    }
                }
            }
        });

        inputBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (inputBox.getSelectedIndex() >= 0) {
                    inputField.setEnabled(true);
                } else {
                    inputField.setText("");
                    inputField.setEnabled(false);
                    outputBox.setEnabled(false);
                }

                boolean valid = true;

                if (convertBox.getSelectedIndex() == 3 && inputBox.getSelectedIndex() >= 0) {
                    String val = inputField.getText();
                    String errorCode = "";

                    if (val.length() > 0) {
                        if (inputBox.getSelectedIndex() == 0) {       // DECIMAL VALIDATION
                            if (!isValidDecFormat(val)) {
                                errorCode = " Invalid Decimal Format ";
                                valid = false;
                            }
                        } else if (inputBox.getSelectedIndex() == 1) {
                            if (!isValidBinaryFormat(val)) {
                                errorCode = " Invalid Binary Format ";
                                valid = false;
                            }

                        } else if (inputBox.getSelectedIndex() == 2) {
                            if (!isValidOctalFormat(val)) {
                                errorCode = " Invalid Octal Format ";
                                valid = false;
                            }

                        } else if (inputBox.getSelectedIndex() == 3) {
                            if (!isValidHexFormat(val)) {
                                errorCode = " Invalid Hexadecimal Format ";
                                valid = false;
                            }
                        }
                        if (!valid) {
                            inputField.setForeground(Color.RED);
                            outputField.setText("");
                            outputBox.setEnabled(false);
                            statusLevel.setText(status + " Wrong/Invalid Input." + errorCode + " Please Correct ! **");
                            statusLevel.setForeground(Color.RED);
                        } else {
                            inputField.setForeground(Color.BLACK);
                            outputField.setText("");
                            outputBox.setEnabled(true);
                            statusLevel.setText(status);
                            statusLevel.setForeground(Color.BLACK);

                        }

                    }


                }


                if (outputBox.getSelectedIndex() >= 0 && (inputField.getText().length() > 0) && valid) {
                    doConversion();
                }
            }
        });
        outputBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (outputBox.getSelectedIndex() >= 0) {
                    doConversion();
                } else {
                    outputField.setText("");
                    statusLevel.setText(status);
                }
            }
        });

        inputField.addCaretListener(this);

        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String str = inputField.getText() + e.getKeyChar();
                if (str.length() >= 10) {
                    e.setKeyChar('\0');
                }
            }
        });

    }


    public void caretUpdate(CaretEvent e) {

        int dot = e.getDot();
        String errorCode = "";
        if (dot >= 0) {
            String input = inputField.getText();
            try {
                if (convertBox.getSelectedIndex() == 3 && inputBox.getSelectedIndex() == 0) {           // DECIMAL VALIDATION
                    if (!isValidDecFormat(input)) {
                        errorCode = " Invalid Decimal Format ";
                        throw (new Exception());
                    }
                } else if (convertBox.getSelectedIndex() == 3 && inputBox.getSelectedIndex() == 1) {     // BINARY VALIDATION
                    if (!isValidBinaryFormat(input)) {
                        errorCode = " Invalid Binary Format ";
                        throw (new Exception());
                    }
                } else if (convertBox.getSelectedIndex() == 3 && inputBox.getSelectedIndex() == 2) {    // OCTAL VALIDATION
                    if (!isValidOctalFormat(input)) {
                        errorCode = " Invalid Octal Format ";
                        throw (new Exception());
                    }
                } else if (convertBox.getSelectedIndex() == 3 && inputBox.getSelectedIndex() == 3) {    // Hexadecimal VALIDATION
                    if (!isValidHexFormat(input)) {
                        errorCode = " Invalid Hexadecimal Format ";

                        throw (new Exception());
                    }

                } else {
                    inputValue = Double.parseDouble(input);
                }

                outputBox.setEnabled(true);
                inputField.setForeground(Color.BLUE);
                statusLevel.setText(status);
                statusLevel.setForeground(Color.BLACK);
                if (outputBox.getSelectedIndex() >= 0) {
                    doConversion();
                }
            } catch (Exception e1) {

                inputField.setForeground(Color.RED);
                outputField.setText("");
                outputBox.setEnabled(false);
                if (input.length() > 0) {
                    statusLevel.setText(status + " Wrong/Invalid Input." + errorCode + " Please Correct !");
                    statusLevel.setForeground(Color.RED);


                }

            }


        }
        if (inputField.getText().length() == 0) {
            statusLevel.setText(status);
            statusLevel.setForeground(Color.BLACK);
            outputField.setText("");
            outputBox.setSelectedIndex(-1);
        }
    }

    private void doConversion() {

        String str = "";
        double val = 0.0;
        if (convertBox.getSelectedIndex() != 3) {
            String stat = "Conversion from " + inputField.getText() + " " + inputBox.getSelectedItem().toString() + " " + "to " + outputBox.getSelectedItem().toString();
            statusLevel.setText(status + " " + stat);
            statusLevel.setForeground(Color.BLACK);

            if (convertBox.getSelectedIndex() == 0) {
                val = lengthConversion();
                str = String.valueOf(val);

            } else if (convertBox.getSelectedIndex() == 1) {
                val = speedConversion();
                str = String.valueOf(val);

            } else if (convertBox.getSelectedIndex() == 2) {
                val = weightConversion();
                str = String.valueOf(val);
            }
            if (str.endsWith(".0")) {
                int v = (int) val;
                outputField.setText(String.valueOf(v));
            } else {
                DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
                format.setDecimalSeparatorAlwaysShown(false);

                format.setMaximumFractionDigits(6);
                outputField.setText("(approx.) " + format.format(val).replaceAll(",", ""));
            }

        } else {             // BASE CONVERSION


            outputBox.setEnabled(true);
            inputField.setForeground(Color.BLUE);
            String output = baseConversion();

            outputField.setText(output.toUpperCase());
            String stat = "Conversion from " + inputBox.getSelectedItem().toString() + " " + inputField.getText() + " " + "to " + outputBox.getSelectedItem().toString() + "  ";
            statusLevel.setText(status + " " + stat);
            statusLevel.setForeground(Color.BLACK);
        }

    }


    private double lengthConversion() {

        double convertedVal = 0.0;
        double factorValue = 0.0;
        int index1 = inputBox.getSelectedIndex();
        int index2 = outputBox.getSelectedIndex();

        // MILES TO OTHERS

        if (index1 == 0 && index2 == 0) {         // Mile to Mile
            factorValue = 1.0;
        } else if (index1 == 0 && index2 == 1) {  // Miles to Kilometer
            factorValue = 1.6;
        } else if (index1 == 0 && index2 == 2) {  // Miles to Meter
            factorValue = 1.6 * 1000;
        } else if (index1 == 0 && index2 == 3) {  // Miles to Centimeter
            factorValue = 1.6 * 1000 * 100;
        } else if (index1 == 0 && index2 == 4) {  // Miles to Millimeter
            factorValue = 1.6 * 1000 * 1000;
        } else if (index1 == 0 && index2 == 5) {  // Miles to Inch
            factorValue = 63360;
        } else if (index1 == 0 && index2 == 6) {  // Miles to Feet
            factorValue = 5280;
        } else if (index1 == 0 && index2 == 7) {  // Miles to Yard
            factorValue = 1760;
        }


        //KM to OTHERS

        else if (index1 == 1 && index2 == 0) {   // KM to Mile
            factorValue = 0.621371192;
        } else if (index1 == 1 && index2 == 1) {  // KM to Kilometer
            factorValue = 1.0;
        } else if (index1 == 1 && index2 == 2) {  // KM to Meter
            factorValue = 1000;
        } else if (index1 == 1 && index2 == 3) {  // KM to Centimeter
            factorValue = 1000 * 100;
        } else if (index1 == 1 && index2 == 4) {  // KM to Millimeter
            factorValue = 1000 * 1000;
        } else if (index1 == 1 && index2 == 5) {  // KM to Inch
            factorValue = 39370.0787;
        } else if (index1 == 1 && index2 == 6) {  // KM to Feet
            factorValue = 3280.8399;
        } else if (index1 == 1 && index2 == 7) {  // KM to Yard
            factorValue = 1093.6133;
        }


        //Meter to OTHERS

        else if (index1 == 2 && index2 == 0) {    // Meter to Mile
            factorValue = 0.000621371192;
        } else if (index1 == 2 && index2 == 1) {  // Meter to Kilometer
            factorValue = .001;
        } else if (index1 == 2 && index2 == 2) {  // Meter to Meter
            factorValue = 1.0;
        } else if (index1 == 2 && index2 == 3) {  // Meter to Centimeter
            factorValue = 100;
        } else if (index1 == 2 && index2 == 4) {  // Meter to Millimeter
            factorValue = 1000;
        } else if (index1 == 2 && index2 == 5) {  // Meter to Inch
            factorValue = 39.3700787;
        } else if (index1 == 2 && index2 == 6) {  // Meter to Feet
            factorValue = 3.2808399;
        } else if (index1 == 2 && index2 == 7) {  // Meter to Yard
            factorValue = 1.0936133;
        }

        //Centimeter to OTHERS

        else if (index1 == 3 && index2 == 0) {    // Centimeter to Mile
            factorValue = 6.21371192 * Math.pow(10, -6);
        } else if (index1 == 3 && index2 == 1) {  // Centimeter to Kilometer
            factorValue = Math.pow(10, -5);
        } else if (index1 == 3 && index2 == 2) {  // Centimeter to Meter
            factorValue = Math.pow(10, -2);
        } else if (index1 == 3 && index2 == 3) {  // Centimeter to Centimeter
            factorValue = 1.0;
        } else if (index1 == 3 && index2 == 4) {  // Centimeter to Millimeter
            factorValue = 10;
        } else if (index1 == 3 && index2 == 5) {  // Centimeter to Inch
            factorValue = 0.393700787;
        } else if (index1 == 3 && index2 == 6) {  // Centimeter to Feet
            factorValue = 0.032808399;
        } else if (index1 == 3 && index2 == 7) {  // Centimeter to Yard
            factorValue = 0.010936133;
        }

        //Millimeter to OTHERS

        else if (index1 == 4 && index2 == 0) {    // Millimeter to Mile
            factorValue = 6.21371192 * Math.pow(10, -7);
        } else if (index1 == 4 && index2 == 1) {  // Millimeter to Kilometer
            factorValue = Math.pow(10, -6);
        } else if (index1 == 4 && index2 == 2) {  // Millimeter to Meter
            factorValue = Math.pow(10, -3);
        } else if (index1 == 4 && index2 == 3) {  // Millimeter to Centimeter
            factorValue = 0.1;
        } else if (index1 == 4 && index2 == 4) {  // Millimeter to Millimeter
            factorValue = 1.0;
        } else if (index1 == 4 && index2 == 5) {  // Millimeter to Inch
            factorValue = 0.0393700787;
        } else if (index1 == 4 && index2 == 6) {  // Millimeter to Feet
            factorValue = 0.0032808399;
        } else if (index1 == 4 && index2 == 7) {  // Millimeter to Yard
            factorValue = 0.0010936133;
        }

        //Inch to OTHERS

        else if (index1 == 5 && index2 == 0) {    // Inch to Mile
            factorValue = 1.57828283 * Math.pow(10, -5);
        } else if (index1 == 5 && index2 == 1) {  // Inch to Kilometer
            factorValue = 2.54 * Math.pow(10, -5);
        } else if (index1 == 5 && index2 == 2) {  // Inch to Meter
            factorValue = 0.0254;
        } else if (index1 == 5 && index2 == 3) {  // Inch to Centimeter
            factorValue = 2.54;
        } else if (index1 == 5 && index2 == 4) {  // Inch to Millimeter
            factorValue = 25.4;
        } else if (index1 == 5 && index2 == 5) {  // Inch to Inch
            factorValue = 1.0;
        } else if (index1 == 5 && index2 == 6) {  // Inch to Feet
            factorValue = 0.0833333333;
        } else if (index1 == 5 && index2 == 7) {  // Inch to Yard
            factorValue = 0.0277777778;
        }

        //Feet to OTHERS

        else if (index1 == 6 && index2 == 0) {    // Feet to Mile
            factorValue = 0.000189393939;
        } else if (index1 == 6 && index2 == 1) {  // Feet to Kilometer
            factorValue = 0.0003048;
        } else if (index1 == 6 && index2 == 2) {  // Feet to Meter
            factorValue = 0.3048;
        } else if (index1 == 6 && index2 == 3) {  // Feet to Centimeter
            factorValue = 30.48;
        } else if (index1 == 6 && index2 == 4) {  // Feet to Millimeter
            factorValue = 304.8;
        } else if (index1 == 6 && index2 == 5) {  // Feet to Inch
            factorValue = 12;
        } else if (index1 == 6 && index2 == 6) {  // Feet to Feet
            factorValue = 1.0;
        } else if (index1 == 6 && index2 == 7) {  // Feet to Yard
            factorValue = 0.333333333;
        }

        //Yard to OTHERS

        else if (index1 == 7 && index2 == 0) {    // Yard to Mile
            factorValue = 0.000568181818;
        } else if (index1 == 7 && index2 == 1) {  // Yard to Kilometer
            factorValue = 0.0009144;
        } else if (index1 == 7 && index2 == 2) {  // Yard to Meter
            factorValue = 0.9144;
        } else if (index1 == 7 && index2 == 3) {  // Yard to Centimeter
            factorValue = 91.44;
        } else if (index1 == 7 && index2 == 4) {  // Yard to Millimeter
            factorValue = 914.4;
        } else if (index1 == 7 && index2 == 5) {  // Yard to Inch
            factorValue = 36;
        } else if (index1 == 7 && index2 == 6) {  // Yard to Feet
            factorValue = 3;
        } else if (index1 == 7 && index2 == 7) {  // Yard to Yard
            factorValue = 1.0;
        }


        convertedVal = inputValue * factorValue;
        return convertedVal;

    }

    private double speedConversion() {
        double convertedVal = 0.0;
        double factorValue = 0.0;
        int index1 = inputBox.getSelectedIndex();
        int index2 = outputBox.getSelectedIndex();


        // Km/hr TO OTHERS

        if (index1 == 0 && index2 == 0) {          // KM/HR to KM/HR
            factorValue = 1.0;
        } else if (index1 == 0 && index2 == 1) {  // KM/HR to Km/Sec
            factorValue = 1.0 / 3600.0;

        } else if (index1 == 0 && index2 == 2) {  // KM/HR to Meter/Sec
            factorValue = 0.277778;
        } else if (index1 == 0 && index2 == 3) {  // KM/HR to Meter/Min
            factorValue = 1000.0 / 60.0;
        } else if (index1 == 0 && index2 == 4) {  // KM/HR to Miles/hr
            factorValue = 1.6;
        } else if (index1 == 0 && index2 == 5) {  // KM/HR to Miles/Sec
            factorValue = 1.6 / 3600.0;
        } else if (index1 == 0 && index2 == 6) {  // KM/HR to Feet/Sec
            factorValue = 3280.84 / 3600.0;
        } else if (index1 == 0 && index2 == 7) {  // KM/HR to Feet/Min
            factorValue = 3280.84 / 60.0;
        }


        // KM//Sec to OTHERS

        if (index1 == 1 && index2 == 0) {          // Km/Sec to KM/HR
            factorValue = 3600.0;
        } else if (index1 == 1 && index2 == 1) {  // Km/Sec to Km/Sec to
            factorValue = 1.0;
        } else if (index1 == 1 && index2 == 2) {  // Km/Sec to Meter/Sec
            factorValue = 1000.0;
        } else if (index1 == 1 && index2 == 3) {  // Km/Sec to Meter/Min
            factorValue = 60000.0;
        } else if (index1 == 1 && index2 == 4) {  // Km/Sec to Miles/hr
            factorValue = 1.6 * 3600.0;
        } else if (index1 == 1 && index2 == 5) {  // Km/Sec to Miles/Sec
            factorValue = 1.6;
        } else if (index1 == 1 && index2 == 6) {  // Km/Sec to Feet/Sec
            factorValue = 3280.84;
        } else if (index1 == 1 && index2 == 7) {  // Km/Sec to Feet/Min
            factorValue = 3280.84 * 60;
        }


        // Meter/Sec  to OTHERS

        if (index1 == 2 && index2 == 0) {          // Meter/Sec to KM/HR
            factorValue = 3.6;
        } else if (index1 == 2 && index2 == 1) {  // Meter/Sec to KM/Sec
            factorValue = 1.0 / 1000.0;
        } else if (index1 == 2 && index2 == 2) {  // Meter/Sec to Meter/Sec
            factorValue = 1.0;
        } else if (index1 == 2 && index2 == 3) {  // Meter/Sec to Meter/Min
            factorValue = 60;
        } else if (index1 == 2 && index2 == 4) {  // Meter/Sec to Miles/hr
            factorValue = 2.236936;
        } else if (index1 == 2 && index2 == 5) {  // Meter/Sec to Miles/Sec
            factorValue = 2.236936 / 3600.0;
        } else if (index1 == 2 && index2 == 6) {  // Meter/Sec to Feet/Sec
            factorValue = 3.28084;
        } else if (index1 == 2 && index2 == 7) {  // Meter/Sec to Feet/Min
            factorValue = 3.28084 * 60;
        }

        // Meter/Min to OTHERS

        if (index1 == 3 && index2 == 0) {          // Meter/Min to KM/HR
            factorValue = 0.06;
        } else if (index1 == 3 && index2 == 1) {  // Meter/Min to KM/Sec
            factorValue = 3.6;
        } else if (index1 == 3 && index2 == 2) {  // Meter/Min to Meter/Sec
            factorValue = 60;
        } else if (index1 == 3 && index2 == 3) {  // Meter/Min to Meter/Min
            factorValue = 1;
        } else if (index1 == 3 && index2 == 4) {  // Meter/Min to Miles/hr
            factorValue = 0.037282272;
        } else if (index1 == 3 && index2 == 5) {  // Meter/Min to Miles/Sec
            factorValue = 0.037282272 / 3600.0;
        } else if (index1 == 3 && index2 == 6) {  // Meter/Min to Feet/Sec
            factorValue = 0.05468067;
        } else if (index1 == 3 && index2 == 7) {  // Meter/Min to Feet/Min
            factorValue = 0.05468067 * 60;
        }

        // Miles/hr to OTHERS

        if (index1 == 4 && index2 == 0) {          // Miles/hr to KM/HR
            factorValue = 1.6;
        } else if (index1 == 4 && index2 == 1) {  // Miles/hr to KM/Sec
            factorValue = 1.6 / 36000.0;
        } else if (index1 == 4 && index2 == 2) {  // Miles/hr to Meter/Sec
            factorValue = 16.0 / 36.0;
        } else if (index1 == 4 && index2 == 3) {  // Miles/hr to Meter/Min
            factorValue = 160.0 / 6.0;
        } else if (index1 == 4 && index2 == 4) {  // Miles/hr to Miles/hr
            factorValue = 1;
        } else if (index1 == 4 && index2 == 5) {  // Miles/hr to Miles/Sec
            factorValue = 1.0 / 3600.0;
        } else if (index1 == 4 && index2 == 6) {  // Miles/hr to Feet/Sec
            factorValue = 1.4666667;
        } else if (index1 == 4 && index2 == 7) {  // Miles/hr to Feet/Min
            factorValue = 1.4666667 * 60.0;
        }

        // Miles/Sec to OTHERS

        if (index1 == 5 && index2 == 0) {          // Miles/Sec to KM/HR
            factorValue = 1.6 * 3600;
        } else if (index1 == 5 && index2 == 1) {  // Miles/Sec to KM/Sec
            factorValue = 1.6;
        } else if (index1 == 5 && index2 == 2) {  // Miles/Sec to Meter/Sec
            factorValue = 1600;
        } else if (index1 == 5 && index2 == 3) {  // Miles/Sec to Meter/Min
            factorValue = 1600 * 60;
        } else if (index1 == 5 && index2 == 4) {  // Miles/Sec to Miles/hr
            factorValue = 3600;
        } else if (index1 == 5 && index2 == 5) {  // Miles/Sec to Miles/Sec
            factorValue = 1;
        } else if (index1 == 5 && index2 == 6) {  // Miles/Sec to Feet/Sec
            factorValue = 5280;
        } else if (index1 == 5 && index2 == 7) {  // Miles/Sec to Feet/Min
            factorValue = 5280 * 60;
        }

        // Feet/Sec to OTHERS

        if (index1 == 6 && index2 == 0) {          // Feet/Sec to KM/HR
            factorValue = 1.09728;
        } else if (index1 == 6 && index2 == 1) {  // Feet/Sec to KM/Sec
            factorValue = 1.09728 / 3600.0;
        } else if (index1 == 6 && index2 == 2) {  // Feet/Sec to Meter/Sec
            factorValue = 1097.28 / 3600.0;
        } else if (index1 == 6 && index2 == 3) {  // Feet/Sec to Meter/Min
            factorValue = 1097.28 / 60.0;
        } else if (index1 == 6 && index2 == 4) {  // Feet/Sec to Miles/hr
            factorValue = 0.681818;
        } else if (index1 == 6 && index2 == 5) {  // Feet/Sec to Miles/Sec
            factorValue = 0.00018939;
        } else if (index1 == 6 && index2 == 6) {  // Feet/Sec to Feet/Sec
            factorValue = 1.0;
        } else if (index1 == 6 && index2 == 7) {  // Feet/Sec to Feet/Min
            factorValue = 60.0;
        }

        // Feet/Min to OTHERS

        if (index1 == 7 && index2 == 0) {          // Feet/Min to KM/HR
            factorValue = 0.0183;
        } else if (index1 == 7 && index2 == 1) {  // Feet/Min to KM/Sec
            factorValue = 0.0183 / 3600.0;
        } else if (index1 == 7 && index2 == 2) {  // Feet/Min to Meter/Sec
            factorValue = 0.183 / 36.0;
        } else if (index1 == 7 && index2 == 3) {  // Feet/Min to Meter/Min
            factorValue = 0.3048;
        } else if (index1 == 7 && index2 == 4) {  // Feet/Min to Miles/hr
            factorValue = 0.0114;
        } else if (index1 == 7 && index2 == 5) {  // Feet/Min to Miles/Sec
            factorValue = 0.0114 / 3600.0;
        } else if (index1 == 7 && index2 == 6) {  // Feet/Min to Feet/Sec
            factorValue = 1.0 / 60.0;
        } else if (index1 == 7 && index2 == 7) {  // Feet/Min to Feet/Min
            factorValue = 1;
        }
        convertedVal = inputValue * factorValue;

        return convertedVal;
    }

    private double weightConversion() {
        double convertedVal = 0.0;
        double factorValue = 0.0;
        int index1 = inputBox.getSelectedIndex();
        int index2 = outputBox.getSelectedIndex();

        // Pound TO OTHERS


        if (index1 == 0 && index2 == 0) {         // Pound To Pound
            factorValue = 1.0;
        } else if (index1 == 0 && index2 == 1) {  // Pound To Ton
            factorValue = 0.00045359237;
        } else if (index1 == 0 && index2 == 2) {  // Pound To Kilogram
            factorValue = 0.45359237;
        } else if (index1 == 0 && index2 == 3) {  // Pound To Gram
            factorValue = 453.59237;
        } else if (index1 == 0 && index2 == 4) {  // Pound To Milligram
            factorValue = 453592.37;
        } else if (index1 == 0 && index2 == 5) {  // Pound To Ounce
            factorValue = 16;
        }


        // Ton to Others

        if (index1 == 1 && index2 == 0) {         // Ton To Pound
            factorValue = 2204.62262;
        } else if (index1 == 1 && index2 == 1) {  // Ton To Ton
            factorValue = 1.0;
        } else if (index1 == 1 && index2 == 2) {  // Ton To Kilogram
            factorValue = 1000.0;
        } else if (index1 == 1 && index2 == 3) {  // Ton To Gram
            factorValue = 1000000.0;
        } else if (index1 == 1 && index2 == 4) {  // Ton To Milligram
            factorValue = 1000000000.0;
        } else if (index1 == 1 && index2 == 5) {  // Ton To Ounce
            factorValue = 35273.96195;
        }

        // Kilogram to others


        if (index1 == 2 && index2 == 0) {         // Kilogram To Pound
            factorValue = 2.2046226;
        } else if (index1 == 2 && index2 == 1) {  // Kilogram To Ton
            factorValue = 0.001;
        } else if (index1 == 2 && index2 == 2) {  // Kilogram To Kilogram
            factorValue = 1.0;
        } else if (index1 == 2 && index2 == 3) {  // Kilogram To Gram
            factorValue = 1000;
        } else if (index1 == 2 && index2 == 4) {  // Kilogram To Milligram
            factorValue = 1000000.0;
        } else if (index1 == 2 && index2 == 5) {  // Kilogram To Ounce
            factorValue = 35.27396195;
        }


        // Gram to Kilogram


        if (index1 == 3 && index2 == 0) {         // Gram To Pound
            factorValue = 0.0022046;
        } else if (index1 == 3 && index2 == 1) {  // Gram To Ton
            factorValue = 0.000001;
        } else if (index1 == 3 && index2 == 2) {  // Gram To Kilogram
            factorValue = 0.001;
        } else if (index1 == 3 && index2 == 3) {  // Gram To Gram
            factorValue = 1.0;
        } else if (index1 == 3 && index2 == 4) {  // Gram To Milligram
            factorValue = 1000.0;
        } else if (index1 == 3 && index2 == 5) {  // Gram To Ounce
            factorValue = 0.03527400;
        }


        // Milligram to others

        if (index1 == 4 && index2 == 0) {         // Milligram To Pound
            factorValue = 0.0000022046;
        } else if (index1 == 4 && index2 == 1) {  // Milligram To Ton
            factorValue = 1.0 * Math.pow(10, -9);
        } else if (index1 == 4 && index2 == 2) {  // Milligram To Kilogram
            factorValue = 0.000001;
        } else if (index1 == 4 && index2 == 3) {  // Milligram To Gram
            factorValue = 0.001;
        } else if (index1 == 4 && index2 == 4) {  // Milligram To Milligram
            factorValue = 1.0;
        } else if (index1 == 4 && index2 == 5) {  // Milligram To Ounce
            factorValue = 0.00003527397;
        }

        // Ounce to others


        if (index1 == 5 && index2 == 0) {         // Ounce To Pound
            factorValue = 0.0625;
        } else if (index1 == 5 && index2 == 1) {  // Ounce To Ton
            factorValue = 0.00002834952;
        } else if (index1 == 5 && index2 == 2) {  // Ounce To Kilogram
            factorValue = 0.028349523;
        } else if (index1 == 5 && index2 == 3) {  // Ounce To Gram
            factorValue = 28.349523;
        } else if (index1 == 5 && index2 == 4) {  // Ounce To Milligram
            factorValue = 28349.523;
        } else if (index1 == 5 && index2 == 5) {  // Ounce To Ounce
            factorValue = 1.0;
        }


        convertedVal = inputValue * factorValue;
        return convertedVal;
    }

    private String baseConversion() {

        int index1 = inputBox.getSelectedIndex();
        int index2 = outputBox.getSelectedIndex();
        String outputVal = "";

        boolean inputIsNegative = false;

        int input = 0;
        if (inputBox.getSelectedIndex() != 3)
            input = Integer.parseInt(inputField.getText());

        if (inputField.getText().contains("-")) {
            inputIsNegative = true;
        }


        // Decimal to others

        if (index1 == 0) {
            if (index2 == 0) {         // Decimal To Decimal
                outputVal = String.valueOf(input);
            } else if (index2 == 1) {  // Decimal To Binary

                if (inputIsNegative) {
                    outputVal = "1 " + Integer.toBinaryString(-input);
                } else {
                    outputVal = Integer.toBinaryString(input);
                }


            } else if (index2 == 2) {  // Decimal To Octal
                if (inputIsNegative) {
                    outputVal = " -" + Integer.toOctalString(-input);
                } else {
                    outputVal = Integer.toOctalString(input);
                }

            } else if (index2 == 3) {  // Decimal To Hexadecimal
                if (inputIsNegative) {
                    outputVal = " -" + Integer.toHexString(-input);
                } else {
                    outputVal = Integer.toHexString(input);
                }

            }

            return outputVal;
        }

        if (index1 == 1) {    // Binary To Others
            if (index2 == 0) {   // Binary to Decimal
                outputVal = String.valueOf(Integer.parseInt(String.valueOf(input), 2));
            }
            if (index2 == 1) {   // Binary to Binary
                outputVal = String.valueOf(input);

            }
            if (index2 == 2) {   // Binary to Octal
                String sign = "";
                int p = Integer.parseInt(String.valueOf(input), 2); // Bin -> Dec
                if (inputIsNegative) {
                    p = -p;
                    sign = " -";
                }
                outputVal = sign + Integer.toOctalString(p);  // Dec -> Octal


            }
            if (index2 == 3) {   // Binary to Hexadecimal
                String sign = "";
                int p = Integer.parseInt(String.valueOf(input), 2); // Bin -> Dec
                if (inputIsNegative) {
                    p = -p;
                    sign = " -";
                }
                outputVal = sign + Integer.toHexString(p);  // Dec -> Hex

            }
        }

        if (index1 == 2) {    // Octal To Others

            if (index2 == 0) {   // Octal to Decimal
                outputVal = String.valueOf(Integer.parseInt(String.valueOf(input), 8));  // Oct-> Dec

            }
            if (index2 == 1) {   // Octal to Binary
                int p = Integer.parseInt(String.valueOf(input), 8);  // oct-> bin;
                String sign = "";
                if (inputIsNegative) {
                    p = -p;
                    sign = "1 ";
                }

                outputVal = sign + Integer.toBinaryString(p);

            }
            if (index2 == 2) {   // Octal to Octal
                outputVal = String.valueOf(input);
            }
            if (index2 == 3) {   // Octal to Hexadecimal
                int p = Integer.parseInt(String.valueOf(input), 8); // Oct -> Dec
                String sign = "";
                if (inputIsNegative) {
                    p = -p;
                    sign = " -";
                }

                outputVal = sign + Integer.toHexString(p);  // Dec -> Hex

            }
        }

        if (index1 == 3) {    // Hexadecimal To Others
            inputHexVal = inputField.getText().trim();

            if (index2 == 0) {   // Hex to Decimal

                outputVal = String.valueOf(Integer.parseInt(inputHexVal, 16));  // HEX -> Dec

            }
            if (index2 == 1) {   // Hex to Binary
                int p = Integer.parseInt(inputHexVal, 16);  // hex -> dec;
                String sign = "";

                if (inputIsNegative) {
                    p = -p;

                    sign = "1 ";
                }

                outputVal = sign + Integer.toBinaryString(p); // dec -> bin

            }
            if (index2 == 2) {   // Hex to Octal
                int p = Integer.parseInt(inputHexVal, 16);  // hex -> dec;
                String sign = "";
                if (inputIsNegative) {
                    p = -p;
                    sign = " -";
                }
                outputVal = sign + Integer.toOctalString(p);    // dec -> Octal
            }
            if (index2 == 3) {   // Hex to Hexadecimal
                outputVal = inputHexVal;


            }

            outputVal = outputVal.toUpperCase();
        }


        return outputVal;
    }


    private boolean isValidBinaryFormat(String num) {
        boolean res = true;
        try {
            int a = Integer.parseInt(num, 2);

            res = true;
        } catch (Exception e) {
            res = false;
        }

        return res;
    }

    private boolean isValidOctalFormat(String num) {
        boolean res = true;
        try {
            int a = Integer.parseInt(num, 8);

            res = true;
        } catch (Exception e) {
            res = false;
        }

        return res;
    }

    private boolean isValidHexFormat(String s) {

        boolean res = true;
        try {
            int a = Integer.parseInt(s, 16);

            res = true;
        } catch (Exception e) {
            res = false;
        }

        return res;
    }

    private boolean isValidDecFormat(String s) {
        boolean res = true;
        try {
            int a = Integer.parseInt(s);

            res = true;
        } catch (Exception e) {
            res = false;
        }

        return res;
    }


}
