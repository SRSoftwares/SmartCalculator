package calculator;


import com.sun.awt.AWTUtilities;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DecimalFormat;

/**
 * Created by IntelliJ IDEA.
 * Author: Sumit Roy
 * Date: 7/8/11
 * Time: 12:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class Calculator extends JFrame implements ActionListener, MouseListener {

    static int posX;
    static int posY;
    static int frameCountX;
    static int frameCountY;
    static Boolean firstOne;
    static double versionId = 1.7;

    private JTextField displayText;
    private JLabel statusLevel1;
    public JLabel statusLevel2;
    public JLabel statusLevel3;
    private JPanel statusPanel;

    private JButton backSpaceButton;
    private JButton ceButton;
    private JButton cButton;
    private JButton Button7;
    private JButton Button8;
    private JButton Button9;
    private JButton Button6;
    private JButton Button5;
    private JButton Button4;
    private JButton Button3;
    private JButton Button2;
    private JButton Button1;
    private JButton Button0;
    private JButton mulButton;
    private JButton addButton;
    private JButton subButton;
    private JButton divButton;
    private JButton sqrtButton;
    private JButton perButton;
    private JButton plmnButton;
    private JButton dotButton;
    private JButton invButton;
    private JButton eqlButton;
    private JButton factButton;
    private JButton x2yButton;
    private JButton nPrButton;
    private JButton nCrButton;

    private Double firstOperand;
    private Double secondOperand;
    private static File pdfFile;

    private int height;
    private int width;
    private int btnHGap;
    private int btnVGap;
    private int signBtnCount;
    private int maxFrameX;
    private int maxFrameY;
    private int screenWidth;
    private int screenHeight;
    private int sliderVal;
    private int fractDigit;
    private int hoverDuration;

    private static Boolean pdfClosed;
    private Boolean resetText;
    private Boolean errorFix;
    private Boolean gotSecondArgs;
    private Boolean exit = false;
    private Boolean hoverEffect;
    private volatile boolean isInside;

    private String numLockStatus;
    private String hoverStatus;
    private String transparentStatus;
    private String filePath;
    private String x2y;
    private String npr;
    private String ncr;
    private String output;
    private String defaultTxt;
    private String headerText;
    private String tootTip;
    private String sign;


    private JMenuBar menubar;
    private JMenu helpMenu;
    private JMenu converterMenu;
    private JMenu settingsMenu;
    private JMenu changeTheme;
    private JMenu changeHover;

    private JMenuItem helpItem;
    private JMenuItem aboutItem;
    private JMenuItem newCalItem;
    private JMenuItem converterItem;
    private JMenuItem hoverDurationItem;
    private JMenuItem changeTransparentItem;
    private JMenuItem exitItem;

    private Color col;
    private JCheckBoxMenuItem[] menuItems;
    private JCheckBoxMenuItem[] hoverItems;
    private Toolkit tool;
    private String numLock;
    private String statusIcon;
    private JPanel statusImgPanel;
    private Image statusImage;


    Calculator() {

        tool = Toolkit.getDefaultToolkit();
        hoverDuration = 2000;   // in milli seconds
        fractDigit = 15;
        sliderVal = 1;
        hoverEffect = false;
        pdfClosed = true;
        isInside = false;
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = d.width;
        screenHeight = d.height;
        resetText = false;
        width = 384;
        height = 280;
        maxFrameX = screenWidth / (width + 5);
        maxFrameY = screenHeight / (height + 5);

        boolean b = tool.getLockingKeyState(KeyEvent.VK_NUM_LOCK);

        if (b) {
            numLock = "On";
            statusIcon = "green.png";
        } else {
            numLock = "Off";
            statusIcon = "red.png";
        }
        numLockStatus = "Num Lock : " + numLock;
        transparentStatus = "Transparent : " + sliderVal + " %";
        hoverStatus = "Hover Duration : " + hoverDuration / 1000 + " Sec";

        statusLevel1 = new JLabel(numLockStatus, JLabel.CENTER);
        statusLevel1.setBackground(Color.WHITE);
        statusLevel1.setFont(new Font("Times New Roman", Font.BOLD, 12));
        statusLevel1.setMaximumSize(new Dimension(82, 14));
        statusLevel1.setPreferredSize(new Dimension(82, 14));
        statusLevel1.setMinimumSize(new Dimension(82, 14));

        statusLevel2 = new JLabel(transparentStatus, JLabel.CENTER);
        statusLevel2.setBackground(Color.WHITE);
        statusLevel2.setFont(new Font("Times New Roman", Font.BOLD, 12));
        statusLevel2.setMaximumSize(new Dimension(107, 14));
        statusLevel2.setPreferredSize(new Dimension(107, 14));
        statusLevel2.setMinimumSize(new Dimension(107, 14));

        statusLevel3 = new JLabel(hoverStatus, JLabel.CENTER);
        statusLevel3.setBackground(Color.WHITE);
        statusLevel3.setFont(new Font("Times New Roman", Font.BOLD, 12));
        statusLevel3.setMaximumSize(new Dimension(120, 14));
        statusLevel3.setPreferredSize(new Dimension(120, 14));
        statusLevel3.setMinimumSize(new Dimension(120, 14));

        statusImgPanel = new JPanel();

        statusImage = null;
        try {
            statusImage = ImageIO.read(AboutDialog.class.getResource(statusIcon));

            statusImgPanel = new ImageBackgroundPanel(statusImage, 0, 2);
            statusImgPanel.setBackground(Color.WHITE);


        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        statusPanel = new JPanel();
        statusPanel.setBackground(Color.WHITE);
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        statusPanel.add(Box.createHorizontalStrut(5));
        statusPanel.add(statusLevel1);
        statusPanel.add(Box.createHorizontalStrut(8));
        statusPanel.add(statusImgPanel);
        statusPanel.add(Box.createHorizontalStrut(25));
        statusPanel.add(statusLevel2);
        statusPanel.add(Box.createHorizontalStrut(15));
        statusPanel.add(statusLevel3);
        statusPanel.add(Box.createHorizontalStrut(30));

        Border levelBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED);
        statusPanel.setBorder(levelBorder);

        this.setBackground(Color.WHITE);
        AWTUtilities.setWindowOpacity(this, (float) (100 - sliderVal) / 100);


        if (firstOne) {
            posX = -width;
            posY = 3;
            firstOne = false;
            frameCountX = 0;
            frameCountY = 0;
            this.setLocation(screenWidth / 2, screenHeight / 2);
        } else {

            if (frameCountX == maxFrameX) {

                posX = -width;
                posY = posY + height + 3;
                frameCountX = 0;
                frameCountY++;
            }
            if (frameCountY == maxFrameY) {

                JOptionPane.showMessageDialog(null, "No More Calculator Can Be Created..Exiting", "Calculator Creation Error", JOptionPane.ERROR_MESSAGE);

                this.dispose();
                posY = posY - height - 3;
                frameCountY--;
                this.exit = true;

            }

            frameCountX++;

            posX = posX + width + 10;
            this.setLocation(posX, posY);

        }
        if (!this.exit) {
            signBtnCount = 0;
            errorFix = true;
            firstOperand = 0.0;
            secondOperand = 0.0;

            output = "";
            gotSecondArgs = false;
            sign = "";

            btnHGap = 3;
            btnVGap = 3;
            defaultTxt = "0";
            headerText = "SmartCalculator v" + versionId;


            helpMenu = new JMenu("Help");
            converterMenu = new JMenu("Converter");
            settingsMenu = new JMenu("Settings");
            menubar = new JMenuBar();

            helpItem = new JMenuItem("Help Manual & Release Notes");
            aboutItem = new JMenuItem("About");
            newCalItem = new JMenuItem("New SmartCalculator");
            converterItem = new JMenuItem("Unit Converter");
            hoverDurationItem = new JMenuItem("Change Hover Duration");
            exitItem = new JMenuItem("Exit");
            changeTheme = new JMenu("Change Theme");
            changeHover = new JMenu("Hover Effect");
            changeTransparentItem = new JMenuItem("Make Transparent");

            this.setTitle(headerText);

            this.setLayout(new BorderLayout());
            Image image;
            try {
                image = ImageIO.read(this.getClass().getResource("calIcon.png"));
            } catch (Exception e) {
                image = this.getIconImage();
            }
            this.setIconImage(image);

            this.setResizable(false);
            this.add(createTextFieldPanel(), BorderLayout.NORTH);
            this.addMenuBarItem();


            JPanel lowerPanel = new JPanel();
            lowerPanel.setLayout(new BorderLayout());
            lowerPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            lowerPanel.setBackground(Color.WHITE);
            lowerPanel.add(createButtonFieldPanel(), BorderLayout.CENTER);

            this.add(lowerPanel, BorderLayout.CENTER);
            this.add(statusPanel, BorderLayout.SOUTH);
            this.setJMenuBar(menubar);
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    if (!pdfClosed) {
                        JOptionPane.showMessageDialog(null, "You need to close the Help Manual before closing Calculator", "Calculator Closing Error", JOptionPane.ERROR_MESSAGE);
                    }

                    if (pdfFile != null) {
                        if (pdfFile.exists()) {
                            pdfFile.delete();
                        }

                    }


                }
            });

            this.setSize(new Dimension(width, height));
            setVisible(true);

        }

        this.requestFocus();
        this.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                char ch = e.getKeyChar();
                handleKeyAction(ch);

            }

            public void keyPressed(KeyEvent e) {

                int c = e.getKeyCode();      // c=144 for Num Lock
                if (c == 144) {
                    boolean b = tool.getLockingKeyState(KeyEvent.VK_NUM_LOCK);
                    if (b) {
                        numLock = "On";
                        statusIcon = "green.png";
                    } else {
                        numLock = "Off";
                        statusIcon = "red.png";
                    }

                    numLockStatus = "Num Lock : " + numLock;
                    statusLevel1.setText(numLockStatus);
                    try {
                        statusImage = ImageIO.read(AboutDialog.class.getResource(statusIcon));

                        statusImgPanel = new ImageBackgroundPanel(statusImage, 0, 2);
                        statusImgPanel.setBackground(Color.WHITE);
                        statusPanel.removeAll();
                        statusPanel.setBackground(Color.WHITE);
                        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
                        statusPanel.add(Box.createHorizontalStrut(5));
                        statusPanel.add(statusLevel1);
                        statusPanel.add(Box.createHorizontalStrut(8));
                        statusPanel.add(statusImgPanel);
                        statusPanel.add(Box.createHorizontalStrut(25));
                        statusPanel.add(statusLevel2);
                        statusPanel.add(Box.createHorizontalStrut(15));
                        statusPanel.add(statusLevel3);
                        statusPanel.add(Box.createHorizontalStrut(30));

                        Border levelBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED);
                        statusPanel.setBorder(levelBorder);

                    } catch (IOException et) {
                        et.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }

            public void keyReleased(KeyEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });


    }

    private void exitingCalc() {
        if (!firstOne) {
            JOptionPane.showMessageDialog(null, "Thank You For Using this SmartCalculator, Hope you Liked it ! ", "Visit : www.sumitroy.co.nr", JOptionPane.INFORMATION_MESSAGE);
            if (frameCountX == 1) {
                frameCountX--;
                posX = 0;
                posY = 0;

            } else if (frameCountX > 1) {
                frameCountX--;
                posX = posX - (width + 10);
            }
        }
        if (pdfFile != null) {
            if (pdfFile.exists()) {
                pdfFile.delete();
            }

        }
    }

    private void addMenuBarItem() {
        helpMenu.add(helpItem);
        helpMenu.add(aboutItem);
        converterMenu.add(converterItem);
        converterMenu.add(newCalItem);
        converterMenu.add(exitItem);
        setLookNFeelMenuItems();
        createHoverMenu();


        settingsMenu.add(changeHover);
        settingsMenu.add(changeTheme);
        settingsMenu.add(changeTransparentItem);

        settingsMenu.add(hoverDurationItem);
        menubar.add(converterMenu);
        menubar.add(settingsMenu);
        menubar.add(helpMenu);

        helpItem.addActionListener(this);
        aboutItem.addActionListener(this);
        newCalItem.addActionListener(this);
        exitItem.addActionListener(this);
        changeTransparentItem.addActionListener(this);
        converterItem.addActionListener(this);
        hoverDurationItem.addActionListener(this);


        converterMenu.setMnemonic('C');
        newCalItem.setMnemonic('N');
        converterItem.setMnemonic('R');
        exitItem.setMnemonic('X');

        settingsMenu.setMnemonic('S');
        changeHover.setMnemonic('G');
        changeTheme.setMnemonic('M');
        changeTransparentItem.setMnemonic('T');
        hoverDurationItem.setMnemonic('V');

        helpMenu.setMnemonic('H');
        helpItem.setMnemonic('P');
        aboutItem.setMnemonic('A');


        newCalItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.Event.CTRL_MASK));
        converterItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.Event.CTRL_MASK));
        exitItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.Event.CTRL_MASK));


        changeTransparentItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.Event.CTRL_MASK));
        hoverDurationItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.Event.CTRL_MASK));

        helpItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.Event.CTRL_MASK));
        aboutItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.Event.CTRL_MASK));


    }

    private void setLookNFeelMenuItems() {
        UIManager.LookAndFeelInfo[] lookAndFeelInfos = UIManager.getInstalledLookAndFeels();

        menuItems = new JCheckBoxMenuItem[lookAndFeelInfos.length];
        ButtonGroup grp = new ButtonGroup();
        for (int i = 0; i < lookAndFeelInfos.length; i++) {
            UIManager.LookAndFeelInfo lookAndFeelInfo = lookAndFeelInfos[i];
            String lafClassName = lookAndFeelInfo.getClassName();

            menuItems[i] = new JCheckBoxMenuItem(lookAndFeelInfo.getName());
            grp.add(menuItems[i]);

            menuItems[i].setActionCommand(lafClassName);
            menuItems[i].addActionListener(this);
            changeTheme.add(menuItems[i]);
            if (lafClassName.equalsIgnoreCase(UIManager.getSystemLookAndFeelClassName())) {
                menuItems[i].setState(true);

            }
        }


    }

    public void setSliderVal(int slide) {

        sliderVal = slide;
    }

    public int getSliderVal() {
        return sliderVal;
    }

    private void createHoverMenu() {
        String hoverItemNames[] = {"On", "Off"};
        hoverItems = new JCheckBoxMenuItem[hoverItemNames.length];
        ButtonGroup grp = new ButtonGroup();
        for (int i = 0; i < hoverItemNames.length; i++) {
            hoverItems[i] = new JCheckBoxMenuItem(hoverItemNames[i]);
            grp.add(hoverItems[i]);
            hoverItems[i].addActionListener(this);
            changeHover.add(hoverItems[i]);

        }
        hoverItems[1].setState(true);
    }

    private JPanel createTextFieldPanel() {
        JPanel upperPanel = new JPanel();
        upperPanel.setSize(new Dimension(width, height / 2));
        BoxLayout uPanel = new BoxLayout(upperPanel, BoxLayout.Y_AXIS);
        upperPanel.setLayout(uPanel);
        displayText = new JTextField(15);
        displayText.setForeground(Color.DARK_GRAY);
        displayText.setBackground(Color.WHITE);
        Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(blackBorder, "Result");

        displayText.setBorder(titledBorder);
        displayText.setText(defaultTxt);
        displayText.setFont(new Font("Times New Roman", Font.BOLD, 15));
        displayText.setForeground(Color.MAGENTA);
        displayText.setEditable(false);
        displayText.setHorizontalAlignment(JTextField.RIGHT);
        displayText.setToolTipText("Displays The Output");
        upperPanel.add(displayText);
        upperPanel.add(Box.createVerticalStrut(4));
        JPanel semiButtonPanel = new JPanel();
        semiButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        backSpaceButton = new JButton("Backspace");
        backSpaceButton.setToolTipText("Backspace");
        ceButton = new JButton("CE");
        ceButton.setToolTipText("CE");
        cButton = new JButton("C");
        cButton.setToolTipText("C");
        backSpaceButton.addActionListener(this);
        cButton.addActionListener(this);
        ceButton.addActionListener(this);
        semiButtonPanel.add(backSpaceButton);
        semiButtonPanel.add(ceButton);
        semiButtonPanel.add(cButton);
        upperPanel.add(semiButtonPanel);

        return upperPanel;
    }

    private JPanel createButtonFieldPanel() {

        initializeButtons();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        GridLayout gridLayout = new GridLayout(4, 5);
        gridLayout.setHgap(btnHGap);
        gridLayout.setVgap(btnVGap);

        buttonPanel.setLayout(gridLayout);

        buttonPanel.add(Button7);
        buttonPanel.add(Button8);
        buttonPanel.add(Button9);
        buttonPanel.add(divButton);
        buttonPanel.add(factButton);
        buttonPanel.add(sqrtButton);

        buttonPanel.add(Button4);
        buttonPanel.add(Button5);
        buttonPanel.add(Button6);
        buttonPanel.add(mulButton);
        buttonPanel.add(x2yButton);
        buttonPanel.add(perButton);

        buttonPanel.add(Button1);
        buttonPanel.add(Button2);
        buttonPanel.add(Button3);
        buttonPanel.add(subButton);
        buttonPanel.add(nPrButton);
        buttonPanel.add(invButton);

        buttonPanel.add(Button0);
        buttonPanel.add(plmnButton);
        buttonPanel.add(dotButton);
        buttonPanel.add(addButton);
        buttonPanel.add(nCrButton);
        buttonPanel.add(eqlButton);


        addListenerToButtons();
        return buttonPanel;
    }

    private void initializeButtons() {
        // AS 1/X is of 3 characters so every button text is used in a manner so that size of every button looks same
        Button7 = new JButton(" 7 ");
        Button7.setToolTipText("7");
        Button8 = new JButton(" 8 ");
        Button8.setToolTipText("8");
        Button9 = new JButton(" 9 ");
        Button9.setToolTipText("9");
        Button6 = new JButton(" 6 ");
        Button6.setToolTipText("6");
        Button5 = new JButton(" 5 ");
        Button5.setToolTipText("5");
        Button4 = new JButton(" 4 ");
        Button4.setToolTipText("4");
        Button3 = new JButton(" 3 ");
        Button3.setToolTipText("3");
        Button2 = new JButton(" 2 ");
        Button2.setToolTipText("2");
        Button1 = new JButton(" 1 ");
        Button1.setToolTipText("1");
        Button0 = new JButton(" 0 ");
        Button0.setToolTipText("0");

        mulButton = new JButton(" X ");
        mulButton.setToolTipText("Multiplication");

        addButton = new JButton(" + ");
        addButton.setToolTipText("Plus");

        subButton = new JButton(" - ");
        subButton.setToolTipText("Minus");

        divButton = new JButton(" " + String.valueOf('\u00F7') + " ");
        divButton.setToolTipText("Division");

        sqrtButton = new JButton(" " + String.valueOf('\u221A') + " ");
        sqrtButton.setToolTipText("Square root");

        perButton = new JButton(" % ");
        perButton.setToolTipText("Percentage");

        plmnButton = new JButton(" " + String.valueOf('\u00B1') + " ");
        plmnButton.setToolTipText("Plus Minus");

        dotButton = new JButton(" . ");
        dotButton.setToolTipText(".");

        invButton = new JButton("1/x");
        invButton.setToolTipText("Inverse");

        eqlButton = new JButton(" = ");
        eqlButton.setToolTipText("=");

        factButton = new JButton(" X!");
        factButton.setToolTipText("Factorial");


        x2y = "<html>x<sup>Y</sup></html>";
        x2yButton = new JButton(x2y);
        x2yButton.setToolTipText("X to the Power Y");

        npr = "<html><sup>n</sup>P<sub>r</sub></html>";
        nPrButton = new JButton(npr);
        nPrButton.setToolTipText("Permutation");

        ncr = "<html><sup>n</sup>C<sub>r</sub></html>";
        nCrButton = new JButton(ncr);
        nCrButton.setToolTipText("Combination");


        Font digitBtnFont = new Font("Courier New", Font.BOLD, 13);
        Font symbolBtnFont = new Font("Times New Roman", Font.BOLD, 12);

        Button1.setForeground(Color.BLUE);
        Button1.setFont(digitBtnFont);

        Button2.setForeground(Color.BLUE);
        Button2.setFont(digitBtnFont);

        Button3.setForeground(Color.BLUE);
        Button3.setFont(digitBtnFont);

        Button4.setForeground(Color.BLUE);
        Button4.setFont(digitBtnFont);

        Button5.setForeground(Color.BLUE);
        Button5.setFont(digitBtnFont);

        Button6.setForeground(Color.BLUE);
        Button6.setFont(digitBtnFont);

        Button7.setForeground(Color.BLUE);
        Button7.setFont(digitBtnFont);

        Button8.setFont(digitBtnFont);
        Button8.setForeground(Color.BLUE);

        Button9.setForeground(Color.BLUE);
        Button9.setFont(digitBtnFont);

        Button0.setForeground(Color.BLUE);
        Button0.setFont(digitBtnFont);

        mulButton.setForeground(Color.BLACK);
        mulButton.setFont(new Font("Arial", Font.BOLD, 12));

        addButton.setForeground(Color.BLACK);
        addButton.setFont(new Font("Times New Roman", Font.BOLD, 20));

        subButton.setForeground(Color.BLACK);
        subButton.setFont(new Font("Times New Roman", Font.BOLD, 20));

        divButton.setForeground(Color.BLACK);
        divButton.setFont(new Font("Times New Roman", Font.BOLD, 18));

        sqrtButton.setForeground(Color.BLACK);
        sqrtButton.setFont(new Font("Times New Roman", Font.BOLD, 20));

        perButton.setForeground(Color.BLACK);
        perButton.setFont(symbolBtnFont);

        plmnButton.setForeground(Color.BLACK);
        plmnButton.setFont(symbolBtnFont);

        dotButton.setForeground(Color.BLACK);
        dotButton.setFont(symbolBtnFont);

        invButton.setForeground(Color.BLACK);
        invButton.setFont(symbolBtnFont);

        factButton.setForeground(Color.BLACK);
        factButton.setFont(new Font("Lucida Console", Font.BOLD, 12));

        nPrButton.setForeground(Color.BLACK);
        nPrButton.setFont(symbolBtnFont);

        nCrButton.setForeground(Color.BLACK);
        nCrButton.setFont(symbolBtnFont);

        x2yButton.setForeground(Color.BLACK);
        x2yButton.setFont(symbolBtnFont);

        eqlButton.setForeground(Color.RED);
        eqlButton.setFont(new Font("Times New Roman", Font.BOLD, 20));

    }

    private void addListenerToButtons() {
        Button1.addActionListener(this);
        Button2.addActionListener(this);
        Button3.addActionListener(this);
        Button4.addActionListener(this);
        Button5.addActionListener(this);
        Button6.addActionListener(this);
        Button7.addActionListener(this);
        Button8.addActionListener(this);
        Button9.addActionListener(this);
        Button0.addActionListener(this);

        Button1.addMouseListener(this);
        Button2.addMouseListener(this);
        Button3.addMouseListener(this);
        Button4.addMouseListener(this);
        Button5.addMouseListener(this);
        Button6.addMouseListener(this);
        Button7.addMouseListener(this);
        Button8.addMouseListener(this);
        Button9.addMouseListener(this);
        Button0.addMouseListener(this);

        mulButton.addActionListener(this);
        addButton.addActionListener(this);
        subButton.addActionListener(this);
        divButton.addActionListener(this);
        sqrtButton.addActionListener(this);
        perButton.addActionListener(this);
        plmnButton.addActionListener(this);
        dotButton.addActionListener(this);
        invButton.addActionListener(this);
        eqlButton.addActionListener(this);
        factButton.addActionListener(this);
        x2yButton.addActionListener(this);
        nPrButton.addActionListener(this);
        nCrButton.addActionListener(this);

        mulButton.addMouseListener(this);
        addButton.addMouseListener(this);
        subButton.addMouseListener(this);
        divButton.addMouseListener(this);
        sqrtButton.addMouseListener(this);
        perButton.addMouseListener(this);
        plmnButton.addMouseListener(this);
        dotButton.addMouseListener(this);
        invButton.addMouseListener(this);
        eqlButton.addMouseListener(this);
        factButton.addMouseListener(this);
        x2yButton.addMouseListener(this);
        nPrButton.addMouseListener(this);
        nCrButton.addMouseListener(this);

        backSpaceButton.addMouseListener(this);
        ceButton.addMouseListener(this);
        cButton.addMouseListener(this);


    }

    public static void main(String[] args) {
        firstOne = true;
        posX = 0;
        posY = 0;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }


        Calculator myCalc = new Calculator();


    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            handleButtonEvent(e);

        }
        if (e.getSource() instanceof JMenuItem) {
            try {
                handleMenuBarEvent(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }

    public void handleButtonEvent(ActionEvent e) {

        if (!errorFix) {
            this.resetSystem();
        }
        Object prsdBtn = e.getSource();
        JButton btn = (JButton) prsdBtn;

        if (prsdBtn == backSpaceButton) {
            String text = displayText.getText();

            if ((!text.equals(defaultTxt) && (text.length() > 1))) {
                String text2 = text.substring(0, text.length() - 1);
                text = text2;

                displayText.setText(text2);

            } else if (text.length() == 1) {
                displayText.setText(defaultTxt);
                resetSystem();

            }
        }
        if (prsdBtn == ceButton) {
            resetSystem();
        }
        if (prsdBtn == cButton) {
            resetSystem();
        }
        if (prsdBtn == Button0 || prsdBtn == Button1 || prsdBtn == Button2 || prsdBtn == Button3 || prsdBtn == Button4 || prsdBtn == Button5 || prsdBtn == Button6 || prsdBtn == Button7 || prsdBtn == Button8 || prsdBtn == Button9 || prsdBtn == dotButton) {


            if (displayText.getForeground() == Color.RED) {
                displayText.setText(defaultTxt);
                displayText.setForeground(Color.MAGENTA);
                firstOperand = 0.0;
            }
            digitBtnPrsd((JButton) prsdBtn);
        }
        if (prsdBtn == addButton || prsdBtn == subButton || prsdBtn == mulButton || prsdBtn == divButton || prsdBtn == nPrButton || prsdBtn == nCrButton || prsdBtn == x2yButton) {


            if (gotSecondArgs)
                signBtnCount++;

            if (signBtnCount == 2) {
                eqlBtnPrsd((JButton) prsdBtn, ((JButton) prsdBtn).getText().trim());

            } else if (signBtnCount == 1 && !gotSecondArgs) {
                sign = ((JButton) prsdBtn).getText().trim();   //update sign button if already a sign button is pressed
            } else {
                signBtnPrsd((JButton) prsdBtn);
            }


        }
        if (prsdBtn == eqlButton) {
            eqlBtnPrsd((JButton) prsdBtn, "");
        }

        if (prsdBtn == plmnButton) {
            plmnBtnPrsd((JButton) prsdBtn);
        }
        if (prsdBtn == invButton) {
            invBtnPrsd((JButton) prsdBtn);
        }
        if (prsdBtn == sqrtButton) {
            sqrtBtnPrsd((JButton) prsdBtn);
        }
        if (prsdBtn == perButton) {
            percentBtnPrsd((JButton) prsdBtn);
        }

        if (prsdBtn == factButton) {
            factBtnPrsd((JButton) prsdBtn);
        }
    }

    private void factBtnPrsd(JButton prsdBtn) {
        Double val = Double.valueOf(displayText.getText());
        double fact=0.0;
        if (val < 0) {
            displayText.setForeground(Color.RED);
            displayText.setText("Input Must Be Positive!");
            errorFix = false;
            return;
        } else if (displayText.getText().contains(".")) {
            displayText.setForeground(Color.RED);
            displayText.setText("Input Must Be Integer Type !");
            errorFix = false;
            return;
        } else {
            int a = Integer.parseInt(displayText.getText());

            fact = doFactorial(a);
            DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
            format.setDecimalSeparatorAlwaysShown(false);
            format.setMaximumFractionDigits(fractDigit);
            displayText.setText(format.format(fact).replaceAll(",", ""));
        }

        resetText=true;
        System.out.println("================================================");
        System.out.println("Input Value : " + val);
        System.out.println("Output Value : " + fact);
        System.out.println("Operation Performed : Factorial");
        System.out.println("================================================");
    }

    private double doFactorial(int a) {
        double fact = 1;
        while (a > 0) {
            fact = fact * a;
            a--;

        }

        return fact;
    }

    public void handleMenuBarEvent(ActionEvent ae) throws IOException {
        JMenuItem item = (JMenuItem) ae.getSource();
        String selectedItem = item.getText();


        if (selectedItem.equals("Help Manual & Release Notes")) {
            InputStream inputStream = this.getClass().getResourceAsStream("Help.pdf");
            String home = System.getProperty("user.home");
            filePath = home + "\\help.pdf";
            pdfFile = new File(filePath);
            OutputStream out = null;

            if (!pdfFile.exists()) {
                out = new FileOutputStream(pdfFile);
                byte buf[] = new byte[1024];
                int len;
                while ((len = inputStream.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.flush();
                out.close();
                inputStream.close();

                Desktop desktop = null;
                if (Desktop.isDesktopSupported()) {
                    desktop = Desktop.getDesktop();
                }
                if (desktop != null) {
                    if (desktop.isSupported(Desktop.Action.OPEN)) {
                        try {


                            desktop.open(pdfFile);
                            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                            pdfClosed = false;


                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                            }


                            Runnable runnable = new Runnable() {
                                public void run() {
                                    while (!pdfFile.delete()) {
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                                        }
                                    }
                                    pdfClosed = true;
                                    Calculator.this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                }
                            };
                            new Thread(runnable).start();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "PDF Reader is Missing.Please install any PDF reader", "Missing PDF Reader", JOptionPane.ERROR_MESSAGE);
                            pdfFile.delete();
                        }
                    }
                }


            } else {
                JOptionPane.showMessageDialog(null, "PDF Reader is already opened", "PDF Reader is Opened", JOptionPane.WARNING_MESSAGE);
            }

        } else if (selectedItem.equals("On")) {
            hoverEffect = true;

        } else if (selectedItem.equals("Off")) {
            hoverEffect = false;
        } else if (selectedItem.equals("Make Transparent")) {
            TransparencyDialog tranDialog = new TransparencyDialog(this);

        } else if (selectedItem.equals("Unit Converter")) {
            UnitConverter converter = new UnitConverter(this);

        } else if (selectedItem.equals("Change Hover Duration")) {
            HoverDuration hovDur = new HoverDuration(this);

        } else if (selectedItem.equals("About")) {
            Point p = this.getLocationOnScreen();

            this.setEnabled(false);

            AboutDialog obj = new AboutDialog(this);

        } else if (selectedItem.equals("New SmartCalculator")) {
            Calculator display = new Calculator();

        } else if (selectedItem.equals("Exit")) {
            this.exitingCalc();
            this.dispose();
        } else {
            String lafClassName = item.getActionCommand();
            try {
                UIManager.setLookAndFeel(lafClassName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }

            SwingUtilities.updateComponentTreeUI(this);
            this.validate();

        }

    }

    private void resetSystem() {
        displayText.setText(defaultTxt);
        displayText.setBackground(Color.WHITE);
        displayText.setForeground(Color.MAGENTA);
        firstOperand = 0.0;
        secondOperand = 0.0;

        sign = "";
        output = "";
        signBtnCount = 0;
        resetText = false;
        gotSecondArgs = false;
        errorFix = true;

    }

    private Boolean isDouble(Double d) {
        double s = d;
        Boolean result = false;

        int p = (int) s;
        double k = p;
        if (k == s)
            result = false;
        else
            result = true;

        return result;
    }

    private void eqlBtnPrsd(JButton prsdBtn, String btnSign) {

        String signText= "";
        if(sign.trim().equals("+")){
            signText="Plus";
        }else if(sign.trim().equals("-")){
            signText="Subtraction";
        }else if(sign.trim().equals("X")){
            signText="Multiplication";
        }else if(sign.equals(String.valueOf('\u00F7'))){
            signText="Division";
        }else if(sign.equals(npr)){
            signText="Permutation";
        }else if(sign.equals(ncr)){
            signText="Combination";
        }else if(sign.equals(x2y)){
            signText="X to the power Y";
        }


        secondOperand = Double.valueOf(displayText.getText());

        System.out.println("================================================");
        System.out.println("First Operand : " + firstOperand);
        System.out.println("Second Operand : " + secondOperand);
        System.out.println("Operation Performed = " + signText);

        double outVal = 0.0;
        if (!sign.equals("")) {

            if (sign.equals(npr) || sign.equals(ncr)) {
                if (firstOperand < 0 || secondOperand < 0) {
                    displayText.setForeground(Color.RED);
                    displayText.setText("Input Must Be Postive");
                    secondOperand = 0.0;
                    errorFix = false;

                    sign = "";
                    signBtnCount = 0;
                    return;
                } else if (isDouble(firstOperand) || isDouble(secondOperand)) {
                    displayText.setForeground(Color.RED);
                    displayText.setText("Input Must Be Integer Type");
                    secondOperand = 0.0;
                    errorFix = false;
                    sign = "";
                    signBtnCount = 0;
                    return;
                } else if (firstOperand < secondOperand) {
                    displayText.setForeground(Color.RED);
                    displayText.setText("n must be greater than r");
                    secondOperand = 0.0;
                    errorFix = false;
                    sign = "";
                    signBtnCount = 0;
                    return;
                } else {
                    int n = (int) ((double) firstOperand);
                    int r = (int) ((double) secondOperand);
                    double result = 0;
                    if (sign.equals(npr)) {
                        result = doFactorial(n) / doFactorial(n - r);
                    } else {
                        result = doFactorial(n) / (doFactorial(r) * doFactorial(n - r));
                    }
                    outVal = result;
                    System.out.println("outVal = " + outVal);
                    sign = "";
                }
            }

            if (sign.equals(x2y)) {
                outVal = Math.pow(firstOperand, secondOperand);
                sign = "";
            }

            if (sign.equals("+")) {
                outVal = firstOperand + secondOperand;
                sign = "";

            } else if (sign.equals("-")) {
                outVal = firstOperand - secondOperand;
                sign = "";

            } else if (sign.equals("X")) {
                outVal = firstOperand * secondOperand;
                sign = "";

            } else if (sign.equals(String.valueOf('\u00F7'))) {
                sign = "";

                if (secondOperand != 0 || secondOperand != 0.0) {
                    outVal = firstOperand / secondOperand;
                } else {
                    displayText.setForeground(Color.RED);
                    displayText.setText("Divide By Zero Error !");
                    secondOperand = 0.0;
                    errorFix = false;

                    sign = "";
                    signBtnCount = 0;
                    return;
                }

            }
            displayText.setForeground(Color.BLUE);
            output = String.valueOf(outVal);
            if (output.endsWith(".0")) {
                displayText.setText(output.substring(0, output.length() - 2));
            } else {

                DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
                format.setDecimalSeparatorAlwaysShown(false);
                format.setMaximumFractionDigits(fractDigit);
                displayText.setText(format.format(outVal).replaceAll(",", ""));
            }


        }
        firstOperand = Double.valueOf(displayText.getText());
        gotSecondArgs = false;
        secondOperand = 0.0;

        if (!btnSign.equals("")) {

            signBtnCount = 1;


        } else {
            signBtnCount = 0;
        }
        sign = btnSign;
        resetText = true;
        System.out.println("Output :" + displayText.getText());
        System.out.println("================================================");


    }

    public void digitBtnPrsd(JButton btn) {

        String txt = btn.getText().trim();


        if (signBtnCount == 1 && resetText) {

            firstOperand = Double.valueOf(displayText.getText());
            displayText.setText(defaultTxt);
            gotSecondArgs = true;
            resetText = false;

        }
        if (resetText) {
            displayText.setText(defaultTxt);
            resetText = false;
        }
        String val = displayText.getText();
        if (txt.equals(".") && val.equals(defaultTxt)) {

            displayText.setText(defaultTxt + txt);
        }
        if (txt.equals(".") && (!val.equals(defaultTxt))) {
            if (displayText.getText().contains(".")) {

                // do nothing as a dot already exist
            } else {

                displayText.setText(val + ".");

            }

        }
        if ((!txt.equals(".")) && val.equals(defaultTxt)) {

            displayText.setText(txt);

        }
        if ((!txt.equals(".")) && (!val.equals(defaultTxt))) {

            displayText.setText(val + txt);

        }


    }

    public void plmnBtnPrsd(JButton btn) {
        String txt = btn.getText().trim();
        double val=0.0;
        if (txt.equals(String.valueOf('\u00B1'))) {  // Plus Minus Button
            if (!(displayText.getText().equals(defaultTxt) || displayText.getText().equals("0"))) {
                val = Double.parseDouble(displayText.getText());

                if (val > 0) {
                    displayText.setText("-" + displayText.getText());
                } else {
                    displayText.setText(displayText.getText().substring(1, displayText.getText().length()));
                }
                firstOperand = Double.valueOf(displayText.getText());


            }
        }

        System.out.println("================================================");
        System.out.println("Input Value : " + val);
        System.out.println("Output Value : " + displayText.getText());
        System.out.println("Operation Performed : Plus Minus Button");
        System.out.println("================================================");

    }

    public void signBtnPrsd(JButton btn) {
        signBtnCount++;

        sign = btn.getText().trim();
        firstOperand = Double.valueOf(displayText.getText());
        gotSecondArgs = false;
        resetText = true;
    }

    public void sqrtBtnPrsd(JButton btn) {
        double val = Double.valueOf(displayText.getText());
        double d = 0.0;
        if (val < 0) {
            displayText.setForeground(Color.RED);
            displayText.setText("Input Must Be Positive!");
            errorFix = false;
            return;
        } else {
            d = Math.sqrt(val);
            String res = String.valueOf(d);

            if (res.endsWith(".0")) {
                displayText.setText(res.substring(0, res.length() - 2));
            } else {
                DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
                format.setDecimalSeparatorAlwaysShown(false);
                format.setMaximumFractionDigits(fractDigit);
                displayText.setText(format.format(d).replaceAll(",", ""));

            }
        }
        System.out.println("================================================");
        System.out.println("Input Value : " + val);
        System.out.println("Output Value : " + d);
        System.out.println("Operation Performed : SQUARE ROOT");
        System.out.println("================================================");
        resetText = true;
    }

    public void invBtnPrsd(JButton btn) {
        double val = Double.valueOf(displayText.getText());
        double v = 0.0;
        if (val != 0 || val != 0.0) {
            v = 1 / val;
            String res = String.valueOf(v);
            if (res.endsWith(".0")) {
                displayText.setText(res.substring(0, res.length() - 2));
            } else {
                DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
                format.setDecimalSeparatorAlwaysShown(false);
                format.setMaximumFractionDigits(fractDigit);
                displayText.setText(format.format(v).replaceAll(",", ""));

            }
        } else {
            displayText.setForeground(Color.RED);
            displayText.setText("Divide By Zero Error !");
            errorFix = false;
            return;
        }
        resetText = true;
        System.out.println("================================================");
        System.out.println("Input Value : " + val);
        System.out.println("Output Value : " + v);
        System.out.println("Operation Performed : Inverse (Reciprocal)");
        System.out.println("================================================");
    }

    public void percentBtnPrsd(JButton btn) {
        Double val = Double.valueOf(displayText.getText());
        double d = 0.0;

        String res = "";
        if (val < 0) {
            displayText.setForeground(Color.RED);
            displayText.setText("Input Must Be Positive!");
            return;
        } else {

            if (firstOperand != null && !(firstOperand.equals(0.0))) {
                res = String.valueOf((firstOperand * val) / 100);
            } else {

                d = val / 100;
                res = String.valueOf(d);
            }

            if (res.endsWith(".0")) {
                displayText.setText(res.substring(0, res.length() - 2));
            } else {
                DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
                format.setDecimalSeparatorAlwaysShown(false);
                format.setMaximumFractionDigits(fractDigit);
                displayText.setText(format.format(d).replaceAll(",", ""));

            }
        }
        resetText = true;

        System.out.println("================================================");
        System.out.println("Input Value : " + val);
        System.out.println("Output Value : " + d);
        System.out.println("Operation Performed : Percentage %");
        System.out.println("================================================");
    }

    public void handleKeyAction(char ch) {


        if ((ch >= '0' && ch <= '9') || ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '.' || ch == 10 || ch == 8) {
            if (ch == '0') {
                Button0.doClick();
            }
            if (ch == '1') {
                Button1.doClick();
            }
            if (ch == '2') {
                Button2.doClick();
            }
            if (ch == '3') {
                Button3.doClick();
            }
            if (ch == '4') {
                Button4.doClick();
            }
            if (ch == '5') {
                Button5.doClick();
            }
            if (ch == '6') {
                Button6.doClick();
            }
            if (ch == '7') {
                Button7.doClick();
            }
            if (ch == '8') {
                Button8.doClick();
            }

            if (ch == '9') {
                Button9.doClick();
            }
            if (ch == '+') {
                addButton.doClick();
            }
            if (ch == '-') {
                subButton.doClick();
            }
            if (ch == '*') {
                mulButton.doClick();
            }
            if (ch == '/') {
                divButton.doClick();
            }
            if (ch == '.') {
                dotButton.doClick();
            }
            if (ch == 10) {
                eqlButton.doClick();
            }
            if (ch == 8) {
                backSpaceButton.doClick();
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mousePressed(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseReleased(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseEntered(MouseEvent e) {
        this.requestFocus();
        Color color;
        final JButton btn = (JButton) e.getSource();
        col = btn.getForeground();
        tootTip = btn.getToolTipText(e);
        String btnText = btn.getText().trim();
        if (btnText.equals("1") || btnText.equals("2") || btnText.equals("3") || btnText.equals("4") || btnText.equals("5") || btnText.equals("6") || btnText.equals("7") || btnText.equals("8") || btnText.equals("9") || btnText.equals("0")) {
            color = new Color(255, 13, 13);
        } else {
            color = new Color(17, 111, 66);
        }


        btn.setForeground(color);
        if (hoverEffect) {
            isInside = true;
            int v = hoverDuration / 1000;
            btn.setToolTipText("Hold Your Mouse for " + v + " Sec to Press Button Automatically");
            Runnable runnable = new Runnable() {
                public void run() {
                    try {

                        for (int i = 0; i < hoverDuration; i++) {

                            if (i >= hoverDuration - 500 && i <= hoverDuration) {
                                btn.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                            }
                            Thread.sleep(1);
                            if (!isInside) {
                                isInside = false;
                                btn.setForeground(col);
                                btn.setToolTipText(tootTip);
                                btn.setCursor(Cursor.getDefaultCursor());
                                break;
                            }
                        }
                        if (isInside) {
                            btn.setToolTipText(tootTip);
                            btn.setForeground(col);
                            btn.setCursor(Cursor.getDefaultCursor());
                            isInside = false;
                            btn.doClick();

                        }


                    } catch (InterruptedException e1) {
                        e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }


                }
            };
            new Thread(runnable).start();
        }


    }

    public void mouseExited(MouseEvent e) {
        this.requestFocus();
        isInside = false;
        JButton btn = (JButton) e.getSource();
        btn.setForeground(col);
        btn.setCursor(Cursor.getDefaultCursor());
        btn.setToolTipText(tootTip);
    }

    public int getHoverDuration() {
        return hoverDuration / 1000;
    }

    public void setHoverDuration(int val) {
        hoverDuration = val * 1000;
    }

}