package Presentacio.Vistes;

import Presentacio.Controladors.CtrlPresentacio;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VistaUsuari extends JFrame {

    private JButton EliminarButton;
    private JButton ModificarButton;
    private JButton TancarButton;
    private JButton ConsultarButton;

    private JButton enrereButton;

    public VistaUsuari() {
        setTitle("GESTIÓ USUARI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setExtendedState(JFrame.MAXIMIZED_BOTH);


        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(197, 230, 241));
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));
        panel.setPreferredSize(new Dimension(300, 250));

        int margin = 200;
        panel.setBorder(BorderFactory.createEmptyBorder(margin, margin, margin, margin));

        EliminarButton = new JButton("ELIMINAR USUARI");
        ModificarButton = new JButton("MODIFICAR USUARI");
        TancarButton = new JButton("TANCAR SESSIÓ");
        ConsultarButton = new JButton("CONSULTAR USUARI");
        enrereButton = new JButton("MENÚ PRINCIPAL");


        EliminarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] opciones = {"Si", "No"};
                int resposta = JOptionPane.showOptionDialog(
                        null,
                        "¿Desitja continuar?",
                        "Eliminar",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opciones,
                        opciones[0]
                );
                if (JOptionPane.OK_OPTION == resposta) {
                    try {
                        CtrlPresentacio.eliminarUsuari();
                        CtrlPresentacio.iniPresentacio();
                        String infoMessage = "<html><div style='font-family: Arial; font-size: 14px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                                + "Usuari eliminat amb èxit.</div></html>";
                        JOptionPane.showMessageDialog(null, infoMessage, "Operació realitzada", JOptionPane.INFORMATION_MESSAGE);
                        setVisible(false);
                    } catch (Exception ex) {
                        String errorMessage = "<html><div style='font-family: Arial; font-size: 14px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                                + ex.getMessage() + ".</div></html>";
                        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });

        ModificarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarPanelModificar();
            }
        });

        TancarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    CtrlPresentacio.tancarSessio();
                    CtrlPresentacio.iniPresentacio();
                    setVisible(false);
                } catch (Exception ex) {
                    String errorMessage = "<html><div style='font-family: Arial; font-size: 14px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                            + ex.getMessage() + ".</div></html>";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });



        ConsultarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<String> llistaEntrades = CtrlPresentacio.consultarEntrades();
                    ArrayList<String> llistaTeclats = CtrlPresentacio.consultarTeclats();
                    ArrayList<String> llistaAlfabets = CtrlPresentacio.consultarAlfabets();


                    JPanel panel = new JPanel(new GridBagLayout());
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.insets = new Insets(5, 5, 5, 5);

                    Font font = new Font("Arial", Font.BOLD, 14);

                    JTextArea textAreaEntrades = new JTextArea();
                    textAreaEntrades.setEditable(false);
                    textAreaEntrades.append("Entrades:\n\n");
                    textAreaEntrades.setAlignmentX(SwingConstants.CENTER);
                    textAreaEntrades.setColumns(60);
                    textAreaEntrades.setMargin(new Insets(10, 10, 10, 10));
                    textAreaEntrades.setRows(10);
                    textAreaEntrades.setFont(font);

                    for (String entrada : llistaEntrades) {
                        String[] parts = entrada.split(",");
                        textAreaEntrades.append(parts[1] + "\n");
                    }

                    JScrollPane scrollPaneEntrades = new JScrollPane(textAreaEntrades);
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.gridwidth = 10;
                    gbc.fill = GridBagConstraints.BOTH;
                    gbc.weightx = 400.0;
                    gbc.insets = new Insets(10, 10, 10, 10);
                    panel.add(scrollPaneEntrades, gbc);

                    JTextArea textAreaTeclats = new JTextArea();
                    textAreaTeclats.setEditable(false);
                    textAreaTeclats.append("Teclats:\n\n");
                    textAreaTeclats.setColumns(60);
                    textAreaTeclats.setMargin(new Insets(10, 10, 10, 10));
                    textAreaTeclats.setRows(10);
                    textAreaTeclats.setFont(font);


                    for (String teclat : llistaTeclats) {
                        String[] parts = teclat.split(",");
                        textAreaTeclats.append(parts[1] + "\n");
                    }


                    JScrollPane scrollPaneTeclats = new JScrollPane(textAreaTeclats);
                    gbc.gridx = 0;
                    gbc.gridy = 1;
                    gbc.gridwidth = 10;
                    gbc.fill = GridBagConstraints.BOTH;
                    gbc.weightx = 400.0;
                    gbc.insets = new Insets(10, 10, 10, 10);
                    panel.add(scrollPaneTeclats, gbc);


                    JTextArea textAreaAlfabets = new JTextArea();
                    textAreaAlfabets.setEditable(false);
                    textAreaAlfabets.append("Alfabets:\n\n");
                    textAreaAlfabets.setColumns(60);
                    textAreaAlfabets.setMargin(new Insets(10, 10, 10, 10));
                    textAreaAlfabets.setRows(10);
                    textAreaAlfabets.setFont(font);


                    for (String alfabet : llistaAlfabets) {
                        String[] parts = alfabet.split(",");
                        textAreaAlfabets.append(parts[1] + "\n");
                    }

                    JScrollPane scrollPaneAlfabets = new JScrollPane(textAreaAlfabets);
                    gbc.gridx = 0;
                    gbc.gridy = 2;
                    gbc.gridwidth = 10;
                    gbc.fill = GridBagConstraints.BOTH;
                    gbc.weightx = 400.0;
                    gbc.insets = new Insets(10, 10, 10, 10);
                    panel.add(scrollPaneAlfabets, gbc);


                    JOptionPane.showMessageDialog(null, panel, "Consulta d'Usuari", JOptionPane.PLAIN_MESSAGE);
                } catch (Exception ex) {
                    String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                            + ex.getMessage() + ".</div></html>";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        enrereButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    CtrlPresentacio.VistaPrincipal();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    setVisible(false);
                } catch (Exception ex) {
                    String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                            + ex.getMessage() + ".</div></html>";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        Font novaFont = new Font("Arial", Font.PLAIN, 18);
        ConsultarButton.setFont(novaFont);
        ModificarButton.setFont(novaFont);
        enrereButton.setFont(novaFont);
        EliminarButton.setFont(novaFont);
        TancarButton.setFont(novaFont);

        panel.add(ConsultarButton);
        panel.add(ModificarButton);
        panel.add(enrereButton);
        panel.add(EliminarButton);
        panel.add(TancarButton);


        mainPanel.add(panel, BorderLayout.CENTER);

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(Box.createVerticalGlue());
        getContentPane().add(mainPanel);
        getContentPane().add(Box.createVerticalGlue());
    }

    private void mostrarPanelModificar() {

        Font font = new Font("Arial", Font.BOLD, 14);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        JLabel label = new JLabel("Introdueix la nova contrasenya:   ");
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setMargin(new Insets(5, 10, 5, 20));
        label.setFont(font);
        passwordField.setFont(font);
        panel.add(label);
        panel.add(passwordField);

        panel.add(new JPanel());
        panel.add(new JPanel());



        JLabel label2 = new JLabel("Confirma la contrasenya:   ");
        JPasswordField passwordField2 = new JPasswordField(15);
        passwordField2.setMargin(new Insets(5, 10, 5, 20));
        label2.setFont(font);
        passwordField2.setFont(font);
        panel.add(label2);
        panel.add(passwordField2);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        int resposta = JOptionPane.showConfirmDialog(null, panel, "Modificar Contrasenya",  JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);

        if (resposta == JOptionPane.OK_OPTION) {
            String novaContrasenya = String.valueOf(passwordField.getPassword());
            String novaContrasenya2 = String.valueOf(passwordField2.getPassword());
            if (novaContrasenya.isEmpty() || novaContrasenya2.isEmpty()) {
                String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                        + "Tots els camps son obligatoris.</div></html>";
                JOptionPane.showMessageDialog(null, errorMessage,"Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (novaContrasenya.contains(" ") || novaContrasenya2.contains(" ")) {
                String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                        + "No es poden contenir espais en blanc.</div></html>";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // mirar q siguin iguals
            if (novaContrasenya.equals(novaContrasenya2)) {
                try {
                    CtrlPresentacio.modificarContrasenyaUsuari(novaContrasenya);
                    String infoMessage = "<html><div style='font-family: Arial; font-size: 12px; color: blue; text-align: center; margin: 20px 30px 20px 10px;'>"
                            + "Usuari modificat amb èxit.</div></html>";
                    JOptionPane.showMessageDialog(null, infoMessage, "Operació realitzada", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception e) {
                    String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                            + e.getMessage() + ".</div></html>";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                        + "Les contrasenyes no són iguals.</div></html>";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}