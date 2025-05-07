package Presentacio.Vistes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Presentacio.Controladors.CtrlPresentacio;

public class VistaLogin extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton logInButton;
    private JButton SignInButton;
    private JPanel mainPanel;
    private CtrlPresentacio ctrlPresentacio;

    public VistaLogin() {
        ctrlPresentacio = CtrlPresentacio.obtenirInstancia();
        setTitle("INICIAR SESSIÓ");
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
        panel.setLayout(new GridLayout(8, 1));
        panel.setPreferredSize(new Dimension(500, 500));
        panel.setBackground(new Color(197, 230, 241));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 40));



        Font font = new Font("Arial", Font.PLAIN, 18);

        JLabel usernameLabel = new JLabel("NOM USUARI:");
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        usernameLabel.setFont(font);
        usernameField = new JTextField();
        usernameField.setMargin(new Insets(5, 20, 5, 20));
        usernameField.setFont(font);

        JLabel passwordLabel = new JLabel("CONTRASENYA:");
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passwordLabel.setFont(font);
        passwordField = new JPasswordField();
        passwordField.setMargin(new Insets(5, 20, 5, 20));
        passwordField.setFont(font);

        logInButton = new JButton("Iniciar sessió");
        SignInButton = new JButton("<html><u>Registrar-se</u></html>");
        logInButton.setMargin(new Insets(10, 20, 5, 20));
        logInButton.setFont(font);
        SignInButton.setFont(font);

        SignInButton.setContentAreaFilled(false);
        SignInButton.setBorderPainted(false);
        SignInButton.setFocusPainted(false);
        SignInButton.setOpaque(false);
        SignInButton.setForeground(Color.BLUE);
        SignInButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        logInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nomUsuari = usernameField.getText();
                String contrasenya = new String(passwordField.getPassword());

                if (nomUsuari.isEmpty() || contrasenya.isEmpty()) {
                    String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                            + "Tots els camps son obligatoris.</div></html>";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (nomUsuari.contains(" ") || contrasenya.contains(" ")) {
                    String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                            + "No es poden contenir espais en blanc.</div></html>";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                    return;

                }


                try {
                    ctrlPresentacio.iniciarSessio(nomUsuari, contrasenya);
                    ctrlPresentacio.VistaPrincipal();
                    setVisible(false);
                } catch (Exception ex) {
                    String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                            + ex.getMessage() + ".</div></html>";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        SignInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ctrlPresentacio.VistaSignin();
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
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(logInButton);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(SignInButton);

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