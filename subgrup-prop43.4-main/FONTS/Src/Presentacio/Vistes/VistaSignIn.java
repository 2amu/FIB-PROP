package Presentacio.Vistes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Presentacio.Controladors.CtrlPresentacio;

public class VistaSignIn extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField password2Field;
    private JButton logInButton;
    private JButton SignInButton;
    private JPanel mainPanel;
    private CtrlPresentacio ctrlPresentacio;

    public VistaSignIn() {
        ctrlPresentacio = CtrlPresentacio.obtenirInstancia();

        setTitle("REGISTRAR-SE");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);


        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 1));
        panel.setPreferredSize(new Dimension(500, 500));
        panel.setBackground(new Color(197, 230, 241));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 40));

        Font font = new Font("Arial", Font.PLAIN, 18);

        JLabel usernameLabel = new JLabel("NOM USUARI:");
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        usernameLabel.setFont(font);
        usernameField = new JTextField();
        usernameField.setMargin(new Insets(10, 20, 10, 20));
        usernameField.setFont(font);

        JLabel passwordLabel = new JLabel("CONTRASENYA:");
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passwordLabel.setFont(font);
        passwordField = new JPasswordField();
        passwordField.setMargin(new Insets(10, 20, 10, 20));
        passwordField.setFont(font);

        JLabel passwordLabel2 = new JLabel("CONFIRMA CONTRASENYA:");
        passwordLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        passwordLabel2.setFont(font);
        password2Field = new JPasswordField();
        password2Field.setMargin(new Insets(10, 20, 10, 20));
        password2Field.setFont(font);

        SignInButton = new JButton("Registrar-se");
        logInButton = new JButton("<html><u>Iniciar sessió</u></html>");
        SignInButton.setMargin(new Insets(10, 20, 10, 20));
        logInButton.setFont(font);
        SignInButton.setFont(font);

        logInButton.setContentAreaFilled(false);
        logInButton.setBorderPainted(false);
        logInButton.setFocusPainted(false);
        logInButton.setOpaque(false);
        logInButton.setForeground(Color.BLUE);
        logInButton.setCursor(new Cursor(Cursor.HAND_CURSOR));



        SignInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nomUsuari = usernameField.getText();
                String contrasenya = new String(passwordField.getPassword());
                String contrasenya2 = new String(password2Field.getPassword());

                if (nomUsuari.isEmpty() || contrasenya.isEmpty() || contrasenya2.isEmpty()) {
                    String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                            + "Tots els camps son obligatoris.</div></html>";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (nomUsuari.contains(" ") || contrasenya.contains(" ") || contrasenya2.contains(" ")) {
                    String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                            + "No es poden contenir espais en blanc.</div></html>";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (contrasenya.length() > 15) {
                    String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                            + "La contrasenya no pot tenir més de 15 caràcters.</div></html>";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!contrasenya.equals(contrasenya2)) {
                    String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                            + "Las contrasenyes no coincideixen..</div></html>";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                    return;

                }

                try {
                    ctrlPresentacio.crearUsuari(nomUsuari, contrasenya);
                    ctrlPresentacio.VistaPrincipal();
                    setVisible(false);
                } catch (Exception ex) {
                    String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                            + ex.getMessage() + ".</div></html>";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        logInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.iniPresentacio();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                setVisible(false);
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(passwordLabel2);
        panel.add(password2Field);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(SignInButton);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(logInButton);



        gbc.gridy = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        mainPanel.add(panel, gbc);

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(Box.createVerticalGlue());
        getContentPane().add(mainPanel);
        getContentPane().add(Box.createVerticalGlue());
    }
}