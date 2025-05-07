package Presentacio.Vistes;

import Presentacio.Controladors.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.JTableHeader;

public class VistaAlfabet extends JFrame {

    private JLabel messageLabel1;
    private JButton crearAlfabetButton;

    private JTable alfabetTable;
    private JButton consultarButton;
    private JButton modificarButton;
    private JButton eliminarButton;
    private JButton enrereButton;
    private HashMap<String, Integer> idsAlfabets = new HashMap<>();

    public VistaAlfabet() {
        setTitle("GESTIÓ ALFABETS");
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
        messageLabel1 = new JLabel("Seleccionar Alfabet:");
        crearAlfabetButton = new JButton("Crear Alfabet");
        messagePanel.add(messageLabel1, BorderLayout.WEST);
        messagePanel.add(crearAlfabetButton, BorderLayout.EAST);
        centerPanel.add(messagePanel, BorderLayout.NORTH);

        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Alfabets"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        alfabetTable = new JTable(tableModel);

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

        alfabetTable.setDefaultRenderer(Object.class, renderer);

        JScrollPane scrollPane = new JScrollPane(alfabetTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(60, 0, 0, 0),
                scrollPane.getBorder()));

        centerPanel.add(scrollPane, BorderLayout.CENTER);


        alfabetTable.setPreferredScrollableViewportSize(new Dimension(400, 200));
        alfabetTable.setFillsViewportHeight(false);
        alfabetTable.setRowHeight(50);


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
        crearAlfabetButton.setFont(novaFont);
        consultarButton.setFont(novaFont);
        modificarButton.setFont(novaFont);
        eliminarButton.setFont(novaFont);
        enrereButton.setFont(novaFont);
        alfabetTable.setFont(novaFont);

        JTableHeader header = alfabetTable.getTableHeader();
        Font headerFont = new Font("Arial", Font.BOLD, 18);
        header.setFont(headerFont);

        crearAlfabetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarPanelCrear();
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
                int filaSeleccionada = alfabetTable.getSelectedRow();
                if (filaSeleccionada != -1) {
                    int idEntrada = idsAlfabets.get(alfabetTable.getValueAt(filaSeleccionada, 0));
                    try {
                        String[] s = CtrlPresentacio.consultarAlfabet(idEntrada);


                        JTextArea textAreaEntrada = new JTextArea();
                        textAreaEntrada.setEditable(false);
                        textAreaEntrada.setFont(new Font("Arial", Font.BOLD, 14));
                        textAreaEntrada.setMargin(new Insets(10, 10, 10, 10));
                        textAreaEntrada.setRows(6);


                        textAreaEntrada.append("\nNom: " + s[1] + " \n\nAlfabet: " + s[0] + "\n\n");


                        JOptionPane.showMessageDialog(null, new JScrollPane(textAreaEntrada),
                                "Consulta d'Alfabet", JOptionPane.PLAIN_MESSAGE);

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                    carregarDades();
                }
            }
        });

        modificarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = alfabetTable.getSelectedRow();
                if (filaSeleccionada != -1) {
                    mostrarPanelModificar();
                }
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = alfabetTable.getSelectedRow();
                if (filaSeleccionada != -1) {
                    String[] opciones = {"Si", "No"};
                    String pregunta = "<html><div style='font-family: Arial; font-size: 12px; color: black; text-align: center;'>¿Desitja continuar?</div></html>";
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
                            int idAlfabet = idsAlfabets.get(alfabetTable.getValueAt(filaSeleccionada, 0));
                            CtrlPresentacio.eliminarAlfabet(idAlfabet);

                            String missatge = "<html><div style='font-family: Arial; font-size: 12px; color: blue; text-align: center; margin: 20px 30px 20px 10px;'>"
                                    + "Alfabet eliminat amb èxit.</div></html>";
                            JOptionPane.showMessageDialog(null, missatge, "Operació realitzada", JOptionPane.INFORMATION_MESSAGE);

                        } catch (Exception ex) {
                            String missatge = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                                    + ex.getMessage() + ".</div></html>";
                            JOptionPane.showMessageDialog(null, missatge, "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    carregarDades();
                }
            }
        });
    }

    private void mostrarPanelModificar() {

        Font font = new Font("Arial", Font.BOLD, 14);

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Introdueix el nou contingut:   ");
        JTextField textField = new JTextField(15);
        textField.setMargin(new Insets(5, 10, 5, 20));

        label.setFont(font);
        textField.setFont(font);

        panel.add(label);
        panel.add(textField);

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        int resposta = JOptionPane.showConfirmDialog(null, panel, "Modificar Contingut",  JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);

        if (resposta == JOptionPane.OK_OPTION) {
            String nouContingut = textField.getText();
            if (nouContingut == null) {
                String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                        + "Tots els camps son obligatoris.</div></html>";
                JOptionPane.showMessageDialog(null, errorMessage,"Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (nouContingut.contains(" ") ) {
                String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                        + "No es poden contenir espais en blanc.</div></html>";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(nouContingut.length() > 25) {
                String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                        + "L'alfabet pot tenir un maxim de 25 caracters.</div></html>";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int filaSeleccionada = alfabetTable.getSelectedRow();
            if (filaSeleccionada != -1) {
                int idAlfabet = idsAlfabets.get(alfabetTable.getValueAt(filaSeleccionada, 0));
                try {
                    CtrlPresentacio.modificarAlfabet(idAlfabet, nouContingut);
                    String infoMessage = "<html><div style='font-family: Arial; font-size: 12px; color: blue; text-align: center; margin: 20px 30px 20px 10px;'>"
                            + "Alfabet modificat amb èxit.</div></html>";
                    JOptionPane.showMessageDialog(null, infoMessage, "Operació realitzada", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                            + ex.getMessage() + ".</div></html>";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                }
                carregarDades();
            }
        }
    }

    private void mostrarPanelCrear() {

        Font font = new Font("Arial", Font.BOLD, 14);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        JLabel label = new JLabel("Introdueix el contingut:   ");
        JTextField textField = new JTextField(15);
        textField.setMargin(new Insets(5, 10, 5, 20));
        JLabel label2 = new JLabel("Introdueix el nom:   ");
        JTextField textField2 = new JTextField(15);
        textField2.setMargin(new Insets(5, 10, 5, 20));

        label.setFont(font);
        textField.setFont(font);
        label2.setFont(font);
        textField2.setFont(font);

        panel.add(label);
        panel.add(textField);

        panel.add(new JPanel());
        panel.add(new JPanel());

        panel.add(label2);
        panel.add(textField2);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        int resposta = JOptionPane.showConfirmDialog(null, panel, "Crear Alfabet",  JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);

        if (resposta == JOptionPane.OK_OPTION) {
            String contingut = textField.getText();
            String nom = textField2.getText();
            if (contingut.isEmpty() || nom.isEmpty()) {
                String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                        + "Tots els camps son obligatoris.</div></html>";
                JOptionPane.showMessageDialog(null, errorMessage,"Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (contingut.contains(" ") || nom.contains(" ")) {
                String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                        + "No es poden contenir espais en blanc.</div></html>";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (nom.length() > 15) {
                String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                        + "El nom no pot tenir mes de 15 caracters.</div></html>";
                JOptionPane.showMessageDialog(null, errorMessage,"Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(contingut.length() > 25) {
                String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                        + "L'alfabet pot tenir un maxim de 25 caracters.</div></html>";
                JOptionPane.showMessageDialog(null, errorMessage,"Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                CtrlPresentacio.crearAlfabet(nom, contingut);
                String infoMessage = "<html><div style='font-family: Arial; font-size: 12px; color: blue; text-align: center; margin: 20px 30px 20px 10px;'>"
                        + "Alfabet creat amb èxit.</div></html>";
                JOptionPane.showMessageDialog(null, infoMessage, "Operació realitzada", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                        + e.getMessage() + ".</div></html>";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void carregarDades() {
        try {
            ArrayList<String> llistaAlfabets = CtrlPresentacio.consultarAlfabets();
            String[] alfabets = new String[llistaAlfabets.size()];
            for (int i = 0; i < llistaAlfabets.size(); i++) {
                String[] parts = llistaAlfabets.get(i).split(",");
                alfabets[i] = parts[1];
                idsAlfabets.put(parts[1],Integer.parseInt(parts[0]));
            }
            actualitzarTable(alfabets);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void actualitzarTable(String[] alfabets) {
        DefaultTableModel tableModel = (DefaultTableModel) alfabetTable.getModel();
        tableModel.setRowCount(0);

        for (String s : alfabets) {
            tableModel.addRow(new Object[]{s});
        }
    }
}