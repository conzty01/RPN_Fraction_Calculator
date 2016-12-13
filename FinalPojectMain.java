// @author T. Conzett

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.WindowConstants;
import javax.swing.JTextArea;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.StringTokenizer;

public class FinalPojectMain extends javax.swing.JFrame {

    private HashMap<Integer, JButton> buttonMap = new HashMap<Integer, JButton>();
    private HashMap<String, Fraction> varMap = new HashMap<String, Fraction>();
    private MyStack<Fraction> stk = new MyStack();
    private JTextArea disArea = new JTextArea();
    private String changedExp = "";
    private boolean frac = false;
    private boolean dec = false;

    class MyKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int val = e.getKeyCode();

            if (e.isShiftDown()) {
                val += 256;
            }

            //System.out.println(val);
            if (val != 272) {
                buttonMap.get(val).doClick();
            }
        }
    }

    public void appendStr(javax.swing.JButton button) {

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String addStr = e.getActionCommand();

                switch (addStr) {
                    case "BKSPC":
                        if (changedExp.length() > 0) {
                            String subTmp = changedExp.substring(0, changedExp.length() - 1);
                            changedExp = subTmp;

                        } else if (!stk.isEmpty()) {
                            stk.pop();
                        }
                        break;
                    case "+/-":
                        changedExp += "~";
                        break;
                    case "STO":
                        changedExp += "S";
                        break;
                    case "REC":
                        changedExp += "R";
                        break;
                    case "DEC":
                        dec = true;
                        frac = false;
                        break;
                    case "FRAC":
                        frac = true;
                        dec = false;
                        break;
                    case "SWAP":
                        if (stk.stackLen() >= 2) {
                            stk.swap();
                        }
                        break;
                    default:
                        changedExp += addStr;
                        break;
                }
                refreshDisplay();
            }
        });
    }

    public FinalPojectMain() {
        //         --- Setup this window ---
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(550, 357);

        disArea.setFocusable(false);
        disArea.setEditable(false);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyListener());
        this.requestFocus();

        //         --- this Contents ---
        this.setLayout(new BorderLayout());

        JButton space = new JButton("Space");
        space.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changedExp += " ";
                refreshDisplay();
            }
        });
        buttonMap.put(32, space);

        ScrollPane viewer = new ScrollPane();
        viewer.add(disArea);
        this.add(viewer, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        this.add(buttonPanel, BorderLayout.SOUTH);

        //         --- buttonPanel Contents ---
        JPanel varPanel = new JPanel();
        varPanel.setLayout(new GridLayout(2, 13));

        buttonPanel.add(varPanel, BorderLayout.NORTH);

        //         --- varPanel Contents ---
        String vars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        // Below is used to assign the key press actions to the variable buttons
        int id = 65;

        for (int i = 0; i < vars.length(); i++) {
            Character tmpChar = vars.charAt(i);
            String strLetter = tmpChar.toString();
            JButton tmp = new JButton(strLetter);
            varPanel.add(tmp);

            buttonMap.put(id, tmp);
            buttonMap.put(id + 256, tmp);
            id += 1;

            appendStr(tmp);
            refreshDisplay();
        }

        //         --- buttonPanel Contents ---
        JPanel lowerButtonPanel = new JPanel();
        lowerButtonPanel.setLayout(new BorderLayout());
        buttonPanel.add(lowerButtonPanel, BorderLayout.CENTER);

        //         --- lowerButtonPanel Contents ---
        JPanel llPanel = new JPanel();
        llPanel.setLayout(new GridLayout(4, 2));
        lowerButtonPanel.add(llPanel, BorderLayout.WEST);

        JPanel lcPanel = new JPanel();
        lcPanel.setLayout(new BorderLayout());
        lowerButtonPanel.add(lcPanel, BorderLayout.CENTER);

        //         --- lcPanel Contents ---
        JPanel brGrid = new JPanel();
        brGrid.setLayout(new GridLayout(1, 5));
        lcPanel.add(brGrid, BorderLayout.CENTER);

        //         --- brGrid Contents ---
        JPanel eb = new JPanel();
        brGrid.add(eb);
        eb.setLayout(new GridLayout());

        JButton tmp = new JButton("ENTER");
        tmp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enterFunction();
            }
        });
        buttonMap.put(10, tmp);

        eb.add(tmp);                                // Column 1

        JPanel c2 = new JPanel();                   // Column 2
        c2.setLayout(new GridLayout(4, 1));

        int numId = 55;
        JButton[] tBA1 = {new JButton("7"),
            new JButton("4"),
            new JButton("1")
        };
        for (JButton button : tBA1) {
            appendStr(button);
            c2.add(button);

            buttonMap.put(numId, button);
            buttonMap.put(numId + 48, button);
            numId -= 3;
        }

        JButton a = new JButton("0");
        appendStr(a);
        c2.add(a);
        buttonMap.put(48, a);
        buttonMap.put(96, a);

        eb.add(c2);

        JPanel c3 = new JPanel();                   // Column 3
        c3.setLayout(new GridLayout(4, 1));

        numId = 56;
        JButton[] tBA2 = {new JButton("8"),
            new JButton("5"),
            new JButton("2"),};
        for (JButton button : tBA2) {
            appendStr(button);
            c3.add(button);

            buttonMap.put(numId, button);
            buttonMap.put(numId + 48, button);
            numId -= 3;
        }

        a = new JButton(".");
        appendStr(a);
        c3.add(a);
        buttonMap.put(46, a);
        buttonMap.put(110, a);

        eb.add(c3);

        JPanel c4 = new JPanel();                   // Column 4
        c4.setLayout(new GridLayout(4, 1));

        numId = 57;
        JButton[] tBA3 = {new JButton("9"),
            new JButton("6"),
            new JButton("3"),
            new JButton("+/-")
        };
        for (JButton button : tBA3) {
            appendStr(button);
            c4.add(button);

            if (button.getActionCommand().contains("+/-")) {
                buttonMap.put(192, button);
                buttonMap.put(448, button);
            } else {
                buttonMap.put(numId, button);
                buttonMap.put(numId + 48, button);
                numId -= 3;
            }
        }
        eb.add(c4);

        /* Column 5; do to the order of the buttons and their pseudo-random button
           id scheme, they are initialized outside of a loop */
        JPanel c5 = new JPanel();                   
        c5.setLayout(new GridLayout(4, 1));

        a = new JButton("/");
        appendStr(a);
        c5.add(a);
        buttonMap.put(111, a);
        buttonMap.put(47, a);

        a = new JButton("*");
        appendStr(a);
        c5.add(a);
        buttonMap.put(312, a);
        buttonMap.put(106, a);

        a = new JButton("-");
        appendStr(a);
        c5.add(a);
        buttonMap.put(109, a);
        buttonMap.put(45, a);

        a = new JButton("+");
        appendStr(a);
        c5.add(a);
        buttonMap.put(107, a);
        buttonMap.put(317, a);

        eb.add(c5);

        //         --- llPanel Contents ---
        String[] bNames = {"DEC", "FRAC", "(", ")", "SWAP", "BKSPC", "STO", "REC"};
        
        for (String buttName : bNames) {
            JButton spclButton = new JButton(buttName);
            appendStr(spclButton);
            llPanel.add(spclButton);
            
            if (buttName.contains("(")) {
                buttonMap.put(313, spclButton);
            } else if (buttName.contains(")")) {
                buttonMap.put(304, spclButton);
            } else if (buttName.contains("BKSPC")) {
                buttonMap.put(8, spclButton);
            }
        };

    }

    private void refreshDisplay() {
        int k;
        disArea.setText("");

        for (k = 0; k < 11 - stk.stackLen(); k++) {
            disArea.append("\n");
        }

        if (dec) {
            decToString();
        } else if (frac) {
            fracToString();
        }
        disArea.append(stk.toString());
        disArea.append(changedExp);
        this.requestFocus();
    }

    public void decToString() {
        for (Fraction f : stk) {
            f.setDec(true);
        }
    }

    public void fracToString() {
        for (Fraction f : stk) {
            f.setDec(false);
        }
    }

    public void enterFunction() {
        char fChar;
        double num;
        boolean isNum = false;
        boolean isNeg = false;
        int numBottom, numTop;
        String top, bottom, tmp;
        String textFieldVal = changedExp;

        StringTokenizer line = new StringTokenizer(textFieldVal, " ()+-*/~", true);

        while (line.hasMoreTokens()) {

            try {
                String token = line.nextToken();
                fChar = token.charAt(0);

                switch (fChar) {
                    case '(':

                        top = line.nextToken();
                        // '/' character dividing the fraction
                        tmp = line.nextToken();
                        bottom = line.nextToken();
                        // ')' character closing fraction
                        tmp = line.nextToken();

                        // Create Fraction object and push onto the stack
                        Fraction fTmp = new Fraction(new Integer(top), new Integer(bottom), false);
                        stk.push(fTmp);

                        break;
                    case '+':
                        Fraction f1 = stk.pop();
                        Fraction f2 = stk.pop();
                        Fraction resAdd = f1.add(f2);
                        stk.push(resAdd);
                        break;
                    case ' ':
                        break;
                    case '/':
                        Fraction f3 = stk.pop();
                        Fraction f4 = stk.pop();
                        Fraction resDiv = f4.divide(f3);
                        stk.push(resDiv);
                        break;
                    case '-':
                        Fraction f5 = stk.pop();
                        Fraction f6 = stk.pop();
                        Fraction resSub = f5.subtract(f6);
                        stk.push(resSub);
                        break;
                    case '*':
                        Fraction f7 = stk.pop();
                        Fraction f8 = stk.pop();
                        Fraction resMul = f7.multiply(f8);
                        stk.push(resMul);
                        break;
                    case 'S':
                        // The below line parses past the space between the Save character and the label
                        tmp = line.nextToken();
                        // The below line is the label for the saved fraction
                        token = line.nextToken();
                        Fraction sFrac = stk.pop();
                        varMap.put(token, sFrac);
                        stk.push(sFrac);
                        break;
                    case 'R':
                        // The below line parses past the space between the Save character and the label
                        tmp = line.nextToken();
                        // The below line is the label for the saved fraction
                        token = line.nextToken();
                        Fraction rFrac = varMap.get(token);
                        stk.push(rFrac);
                        break;
                    case '~':
                        Fraction tmpF1 = stk.pop();
                        int a1 = 0 - tmpF1.getNumer();
                        int a2 = tmpF1.getDenom();
                        boolean a3 = tmpF1.getIsDec();
                        stk.push(new Fraction(0 - tmpF1.getNumer(), tmpF1.getDenom(), tmpF1.getIsDec()));
                        break;
                    default:
                        isNum = true;
                        num = new Double(token);
                        numBottom = 1;

                        while (num - (int) num != 0) {
                            numBottom = numBottom * 10;
                            num = num * 10;
                        }
                        numTop = (int) num;

                        stk.push(new Fraction(numTop, numBottom, true));
                        break;
                }
                changedExp = "";

                // Below line cathces malformed expression exceptions
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Malformed Expression", "Error", JOptionPane.ERROR_MESSAGE);
            }
            refreshDisplay();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FinalPojectMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FinalPojectMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FinalPojectMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FinalPojectMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FinalPojectMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
