package Presentacio.Vistes;

import Presentacio.Controladors.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.JTableHeader;

public class VistaTeclat extends JFrame {

    private JLabel messageLabel1;
    private JButton crearTeclatButton;

    private JTable teclatTable;
    private JButton consultarButton;
    private JButton modificarButton;
    private JButton eliminarButton;
    private JButton enrereButton;
    private HashMap<String, Integer> idsTeclats = new HashMap<>();

    public VistaTeclat() {
        setTitle("GESTIÓ TECLATS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new BorderLayout());
        int marginLeft = 80;
        int marginRight = 80;
        int marginBottom = 80;
        centerPanel.setBorder(BorderFactory.createEmptyBorder(80, marginLeft, marginBottom, marginRight));


        JPanel messagePanel = new JPanel(new BorderLayout());
        messageLabel1 = new JLabel("Seleccionar Teclat:");
        crearTeclatButton = new JButton("Crear Teclat");
        messagePanel.add(messageLabel1, BorderLayout.WEST);
        messagePanel.add(crearTeclatButton, BorderLayout.EAST);

        centerPanel.add(messagePanel, BorderLayout.NORTH);

        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Teclats"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        teclatTable = new JTable(tableModel);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);


                if (isSelected) {
                    c.setBackground(Color.BLUE);
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(new Color(197, 230, 241));
                    c.setForeground(Color.BLACK);
                }

                ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
                return c;
            }
        };

        teclatTable.setDefaultRenderer(Object.class, renderer);

        JScrollPane scrollPane = new JScrollPane(teclatTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(60, 0, 0, 0),
                scrollPane.getBorder()));

        centerPanel.add(scrollPane, BorderLayout.CENTER);


        teclatTable.setPreferredScrollableViewportSize(new Dimension(400, 200));
        teclatTable.setFillsViewportHeight(false);
        teclatTable.setRowHeight(50);

        teclatTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = teclatTable.getSelectedRow();
            }
        });

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 80, 20));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        consultarButton = new JButton("Consultar");
        modificarButton = new JButton("Modificar");
        eliminarButton = new JButton("Eliminar");

        buttonPanel.add(consultarButton);
        buttonPanel.add(modificarButton);
        buttonPanel.add(eliminarButton);

        bottomPanel.add(buttonPanel, BorderLayout.NORTH);

        JPanel controlPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(controlPanel, BoxLayout.X_AXIS);
        controlPanel.setLayout(boxLayout);

        enrereButton = new JButton("Enrere");
        enrereButton.setAlignmentX(Component.LEFT_ALIGNMENT);


        controlPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        controlPanel.add(enrereButton);

        bottomPanel.add(controlPanel, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mainPanel);

        carregarDades();

        Font novaFont = new Font("Arial", Font.PLAIN, 18);
        messageLabel1.setFont(novaFont);
        crearTeclatButton.setFont(novaFont);
        consultarButton.setFont(novaFont);
        modificarButton.setFont(novaFont);
        eliminarButton.setFont(novaFont);
        enrereButton.setFont(novaFont);
        teclatTable.setFont(novaFont);

        JTableHeader header = teclatTable.getTableHeader();
        Font headerFont = new Font("Arial", Font.BOLD, 18);
        header.setFont(headerFont);
        crearTeclatButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    mostrarPanelCrearTeclat();
                } catch (Exception ex) {
                    String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                            + ex.getMessage() + ".</div></html>";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                }
                carregarDades();
            }
        });

        enrereButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.VistaPrincipal();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                setVisible(false);
            }
        });

        consultarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = teclatTable.getSelectedRow();
                if (filaSeleccionada != -1) {
                    int idTeclat = idsTeclats.get(teclatTable.getValueAt(filaSeleccionada, 0));
                    try {
                        String[] s = CtrlPresentacio.consultarTeclat(idTeclat);
                        String[] filesTeclat = s[0].split("\\.");

                        if (s[0].equals(",")) {
                            JOptionPane.showMessageDialog(null, "Teclat buit");
                            return;
                        }

                        Character[][] matriuTeclat = new Character[filesTeclat.length][];
                        for (int i = 0; i < filesTeclat.length; ++i) {
                            matriuTeclat[i] = new Character[filesTeclat[i].length()];
                            for (int j = 0; j < filesTeclat[i].length(); ++j)
                                matriuTeclat[i][j] = filesTeclat[i].charAt(j);
                        }

                        JButton[][] matriuTeclatButtons = new JButton[filesTeclat.length][];
                        JPanel buttonPanel = new JPanel(new GridLayout(filesTeclat.length, matriuTeclat[0].length));

                        for (int i = 0; i < filesTeclat.length; ++i) {
                            matriuTeclatButtons[i] = new JButton[filesTeclat[i].length()];
                            for (int j = 0; j < filesTeclat[i].length(); ++j) {
                                char currentChar = matriuTeclat[i][j] != null ? matriuTeclat[i][j] : ' ';
                                matriuTeclatButtons[i][j] = new JButton(Character.toString(currentChar));
                                buttonPanel.add(matriuTeclatButtons[i][j]);
                            }
                        }


                        JPanel mainPanel = new JPanel(new BorderLayout());
                        mainPanel.add(buttonPanel, BorderLayout.CENTER);


                        StringBuilder additionalInfo = new StringBuilder();
                        additionalInfo.append("<html><b>Nom:</b> ").append(s[1]).append("<br><br>");
                        additionalInfo.append("<b>Alfabet:</b> ").append(s[2]).append("<br><br>");
                        additionalInfo.append("<b>Entrada:</b> ").append(s[3]).append("<br><br></html>");

                        JLabel infoLabel = new JLabel(additionalInfo.toString());
                        mainPanel.add(infoLabel, BorderLayout.NORTH);
                        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 40));


                        JOptionPane.showMessageDialog(null, mainPanel,
                                "Consulta teclat", JOptionPane.INFORMATION_MESSAGE);

                    } catch (Exception ex) {
                        String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                                + ex.getMessage() + ".</div></html>";
                        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });




        modificarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = teclatTable.getSelectedRow();
                if (filaSeleccionada != -1) {
                    try {
                        mostrarPanelModificarTeclat();
                    } catch (Exception ex) {
                        String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                                + ex.getMessage() + ".</div></html>";
                        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = teclatTable.getSelectedRow();
                if (filaSeleccionada != -1) {

                    String[] opciones = {"Si", "No"};
                    String pregunta = "<html><div style='font-family: Arial; font-size: 12px; color: black; text-align: center;  margin: 20px 30px 20px 10px;'>¿Desitja continuar?</div></html>";
                    int respuesta = JOptionPane.showOptionDialog(
                            null,
                            pregunta,
                            "Eliminar",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            opciones,
                            opciones[0]
                    );
                    if (JOptionPane.OK_OPTION == respuesta) {
                        try {
                            int idTeclat = idsTeclats.get(teclatTable.getValueAt(filaSeleccionada, 0));
                            CtrlPresentacio.eliminarTeclat(idTeclat);
                            String infoMessage = "<html><div style='font-family: Arial; font-size: 12px; color: blue; text-align: center;  margin: 20px 30px 20px 10px;"
                                    + "Teclat eliminat amb èxit.</div></html>";
                            JOptionPane.showMessageDialog(null, infoMessage, "Operació realitzada", JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception ex) {
                            String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                                    + ex.getMessage() + ".</div></html>";
                            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    carregarDades();
                }
            }
        });
    }
    private void mostrarPanelCrearTeclat() throws Exception {
        JPanel panel = new JPanel(new GridLayout(8, 2));

        Font font = new Font("Arial", Font.BOLD, 14);

        JLabel nomLabel = new JLabel("Introdueix el nom del teclat:   ");
        JTextField nomTextField = new JTextField();
        nomTextField.setMargin(new Insets(5, 10, 5, 20));
        nomLabel.setFont(font);
        nomTextField.setFont(font);
        panel.add(nomLabel);
        panel.add(nomTextField);
        panel.add(new JPanel());
        panel.add(new JPanel());

        JLabel escullAlfabetLabel = new JLabel("Escull alfabet:");

        DefaultComboBoxModel<String> alfabetsModel = new DefaultComboBoxModel<>();
        JComboBox<String> alfabetsComboBox = new JComboBox<String>(alfabetsModel);

        HashMap<String, Integer> idsAlfabets = new HashMap<>();
        ArrayList<String> llistaAlfabets = CtrlPresentacio.consultarAlfabets();
        for (String alfabet : llistaAlfabets) {
            String[] parts = alfabet.split(",");
            alfabetsComboBox.addItem(parts[1]);
            idsAlfabets.put(parts[1], Integer.parseInt(parts[0]));
        }

        escullAlfabetLabel.setFont(font);
        alfabetsComboBox.setFont(font);
        panel.add(escullAlfabetLabel);
        panel.add(alfabetsComboBox);

        panel.add(new JPanel());
        panel.add(new JPanel());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel escullEntradaLabel = new JLabel("Escull entrada:");

        DefaultComboBoxModel<String> entradesModel = new DefaultComboBoxModel<>();
        JComboBox<String> entradesComboBox = new JComboBox<String>(entradesModel);

        HashMap<String, Integer> idsEntrades = new HashMap<>();
        ArrayList<String> llistaEntrades = CtrlPresentacio.consultarEntrades();
        for (String entrada : llistaEntrades) {
            String[] parts = entrada.split(",");
            entradesComboBox.addItem(parts[1]);
            idsEntrades.put(parts[1], Integer.parseInt(parts[0]));
        }

        escullEntradaLabel.setFont(font);
        entradesComboBox.setFont(font);
        panel.add(escullEntradaLabel);
        panel.add(entradesComboBox);
        panel.add(new JPanel());
        panel.add(new JPanel());


        JRadioButton radioButton1 = new JRadioButton("QAP");
        JRadioButton radioButton2 = new JRadioButton("Simulated Annealing");

        radioButton2.setFont(font);
        radioButton1.setFont(font);

        ButtonGroup group = new ButtonGroup();
        group.add(radioButton1);
        group.add(radioButton2);

        JLabel algoritmeLabel = new JLabel("Escull algoritme:");
        algoritmeLabel.setFont(font);
        panel.add(algoritmeLabel);
        panel.add(radioButton1);
        panel.add(new JLabel(""));
        panel.add(radioButton2);

        int resposta = JOptionPane.showConfirmDialog(null, panel, "Crear Teclat", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);

        if (resposta == JOptionPane.OK_OPTION) {

            String nomTeclat = nomTextField.getText();
            Integer alfabetSeleccionat = idsAlfabets.get(alfabetsComboBox.getSelectedItem());
            Integer entradaSeleccionada = idsEntrades.get(entradesComboBox.getSelectedItem());
            String algoritme = radioButton1.isSelected() ? "QAP" : "Simulated Annealing";
            if (nomTeclat.isEmpty() || (!radioButton1.isSelected() && !radioButton2.isSelected())) {
                String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                        + "Tots els camps son obligatoris.</div></html>";
                JOptionPane.showMessageDialog(null, errorMessage,"Error", JOptionPane.ERROR_MESSAGE);
            }
            else if ( nomTeclat.contains(" ")) {
                String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                        + "No es poden contenir espais en blanc.</div></html>";
                JOptionPane.showMessageDialog(null, errorMessage,"Error", JOptionPane.ERROR_MESSAGE);
            }

            else {
                if (algoritme.equals("QAP")) {
                    mostrarModEntrada();
                    CtrlPresentacio.crearTeclat(nomTeclat,alfabetSeleccionat,entradaSeleccionada,0);
                    String infoMessage = "<html><div style='font-family: Arial; font-size: 12px; color: blue; text-align: center; margin: 20px 30px 20px 10px;'>"
                            + "Teclat creat amb èxit.</div></html>";
                    JOptionPane.showMessageDialog(null, infoMessage, "Operació realitzada", JOptionPane.INFORMATION_MESSAGE);

                }
                else {
                    mostrarModEntrada();
                    CtrlPresentacio.crearTeclat(nomTeclat,alfabetSeleccionat,entradaSeleccionada,0);
                    String infoMessage = "<html><div style='font-family: Arial; font-size: 12px; color: blue; text-align: center; margin: 20px 30px 20px 10px;'>"
                            + "Teclat creat amb èxit.</div></html>";
                    JOptionPane.showMessageDialog(null, infoMessage, "Operació realitzada", JOptionPane.INFORMATION_MESSAGE);

                }
            }

        }
    }

    private void mostrarPanelModificarTeclat() throws Exception {

        Font font = new Font("Arial", Font.BOLD, 14);


        JPanel panel = new JPanel(new GridLayout(8, 2));

        JRadioButton reassignarEntradaRadioButton = new JRadioButton("Reassignar entrada");
        JRadioButton reassignarAlfabetoRadioButton = new JRadioButton("Reassignar alfabet");

        ButtonGroup radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(reassignarEntradaRadioButton);
        radioButtonGroup.add(reassignarAlfabetoRadioButton);

        reassignarEntradaRadioButton.setFont(font);
        reassignarAlfabetoRadioButton.setFont(font);

        panel.add(reassignarEntradaRadioButton);
        panel.add(reassignarAlfabetoRadioButton);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(new JPanel());
        panel.add(new JPanel());

        JLabel escullAlfabetLabel = new JLabel("Reassignar alfabet:");

        DefaultComboBoxModel<String> alfabetsModel = new DefaultComboBoxModel<>();
        JComboBox<String> alfabetsComboBox = new JComboBox<>(alfabetsModel);

        HashMap<String, Integer> idsAlfabets = new HashMap<>();
        ArrayList<String> llistaAlfabets = CtrlPresentacio.consultarAlfabets();
        for (String alfabet : llistaAlfabets) {
            String[] parts = alfabet.split(",");
            alfabetsComboBox.addItem(parts[1]);
            idsAlfabets.put(parts[1],Integer.parseInt(parts[0]));
        }

        escullAlfabetLabel.setFont(font);
        alfabetsComboBox.setFont(font);

        panel.add(escullAlfabetLabel);
        panel.add(alfabetsComboBox);
        panel.add(new JPanel());
        panel.add(new JPanel());

        JLabel escullEntradaLabel = new JLabel("Reassignar entrada:");

        DefaultComboBoxModel<String> entradesModel = new DefaultComboBoxModel<>();
        JComboBox<String> entradesComboBox = new JComboBox<>(entradesModel);

        HashMap<String, Integer> idsEntrades = new HashMap<>();
        ArrayList<String> llistaEntrades = CtrlPresentacio.consultarEntrades();
        for (String entrada : llistaEntrades) {
            String[] parts = entrada.split(",");
            entradesComboBox.addItem(parts[1]);
            idsEntrades.put(parts[1], Integer.parseInt(parts[0]));
        }

        escullEntradaLabel.setFont(font);
        entradesComboBox.setFont(font);

        panel.add(escullEntradaLabel);
        panel.add(entradesComboBox);
        panel.add(new JPanel());
        panel.add(new JPanel());

        JLabel escullAlgoritmeLabel = new JLabel("Reassignar algoritme:");

        JRadioButton radioButton1 = new JRadioButton("QAP");
        JRadioButton radioButton2 = new JRadioButton("Simulated Annealing");

        ButtonGroup group2 = new ButtonGroup();
        group2.add(radioButton1);
        group2.add(radioButton2);

        escullAlgoritmeLabel.setFont(font);
        radioButton1.setFont(font);
        radioButton2.setFont(font);

        panel.add(escullAlgoritmeLabel);
        panel.add(radioButton1);
        panel.add(new JLabel(""));
        panel.add(radioButton2);

        ActionListener radioButtonListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean reasignarEntradaSelected = reassignarEntradaRadioButton.isSelected();
                escullAlfabetLabel.setEnabled(!reasignarEntradaSelected);
                alfabetsComboBox.setEnabled(!reasignarEntradaSelected);

                escullEntradaLabel.setEnabled(reasignarEntradaSelected);
                entradesComboBox.setEnabled(reasignarEntradaSelected);
            }
        };

        reassignarEntradaRadioButton.addActionListener(radioButtonListener);
        reassignarAlfabetoRadioButton.addActionListener(radioButtonListener);


        int resposta = JOptionPane.showConfirmDialog(null, panel, "Modificar Teclat", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resposta == JOptionPane.OK_OPTION) {
            int filaSeleccionada = teclatTable.getSelectedRow();
            if (filaSeleccionada != -1) {
                int idTeclat = idsTeclats.get(teclatTable.getValueAt(filaSeleccionada, 0));


                if (reassignarEntradaRadioButton.isSelected()) {
                    Integer entradaSeleccionada = idsEntrades.get(entradesComboBox.getSelectedItem());
                    mostrarModEntrada();
                    CtrlPresentacio.modificarTeclat(idTeclat, false, entradaSeleccionada, 1);
                    String infoMessage = "<html><div style='font-family: Arial; font-size: 12px; color: blue; text-align: center; margin: 20px 30px 20px 10px;'>"
                            + "Teclat modificat amb èxit.</div></html>";
                    JOptionPane.showMessageDialog(null, infoMessage, "Operació realitzada", JOptionPane.INFORMATION_MESSAGE);

                } else if (reassignarAlfabetoRadioButton.isSelected()) {
                    Integer alfabetSeleccionat = idsAlfabets.get(alfabetsComboBox.getSelectedItem());
                    mostrarModEntrada();
                    CtrlPresentacio.modificarTeclat(idTeclat, true, alfabetSeleccionat, 1);
                    String infoMessage = "<html><div style='font-family: Arial; font-size: 12px; color: blue; text-align: center; margin: 20px 30px 20px 10px;'>"
                            + "Teclat modificat amb èxit.</div></html>";
                    JOptionPane.showMessageDialog(null, infoMessage, "Operació realitzada", JOptionPane.INFORMATION_MESSAGE);

                } else if(!reassignarEntradaRadioButton.isSelected() && !reassignarEntradaRadioButton.isSelected()) {

                    String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                            + "Tots els camps son obligatoris.</div></html>";
                    JOptionPane.showMessageDialog(null, errorMessage,"Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else {
                    int algoritmeSeleccionatInt;
                    String algoritmeSeleccionat = radioButton1.isSelected() ? "QAP" : "Simulated Annealing";

                    if (algoritmeSeleccionat.equals("QAP")) {
                        algoritmeSeleccionatInt = 0;
                    } else {
                        algoritmeSeleccionatInt = 1;
                    }
                    if (reassignarEntradaRadioButton.isSelected()) {

                        Integer entradaSeleccionada = idsEntrades.get(entradesComboBox.getSelectedItem());
                        mostrarModEntrada();
                        CtrlPresentacio.modificarTeclat(idTeclat, false, entradaSeleccionada, algoritmeSeleccionatInt);
                        String infoMessage = "<html><div style='font-family: Arial; font-size: 12px; color: blue; text-align: center; margin: 20px 30px 20px 10px;'>"
                                + "Teclat modificat amb èxit.</div></html>";
                        JOptionPane.showMessageDialog(null, infoMessage, "Operació realitzada", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if (reassignarAlfabetoRadioButton.isSelected()) {

                        Integer alfabetSeleccionat = idsAlfabets.get(alfabetsComboBox.getSelectedItem());
                        mostrarModEntrada();
                        CtrlPresentacio.modificarTeclat(idTeclat, true, alfabetSeleccionat, algoritmeSeleccionatInt);
                        String infoMessage = "<html><div style='font-family: Arial; font-size: 12px; color: blue; text-align: center; margin: 20px 30px 20px 10px;'>"
                                + "Teclat modificat amb èxit.</div></html>";
                        JOptionPane.showMessageDialog(null, infoMessage, "Operació realitzada", JOptionPane.INFORMATION_MESSAGE);
                    }

                }
            }
        }
    }
    private void mostrarModEntrada() {
        String infoMessage = "<html><div style='font-family: Arial; font-size: 12px; color: blue; text-align: center; margin: 20px 30px 20px 10px;'>"
                + "Alguns caracters de l'entrada poden ser modificats.</div></html>";
        JOptionPane.showMessageDialog(null, infoMessage, "Operació realitzada",JOptionPane.WARNING_MESSAGE);
    }

    private void carregarDades() {

        try {

            ArrayList<String> llistaTeclats = CtrlPresentacio.consultarTeclats();
            String[] teclats = new String[llistaTeclats.size()];
            for (int i = 0; i < llistaTeclats.size(); i++) {
                String[] parts = llistaTeclats.get(i).split(",");
                teclats[i] = parts[1];
                idsTeclats.put(parts[1],Integer.parseInt(parts[0]));
            }
            actualitzarTable(teclats);

        } catch (Exception e) {
            String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                    + e.getMessage() + ".</div></html>";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualitzarTable (String[] entrades) {
        DefaultTableModel tableModel = (DefaultTableModel) teclatTable.getModel();
        tableModel.setRowCount(0);

        for (String s : entrades) {
            tableModel.addRow(new Object[]{s});
        }
    }
}