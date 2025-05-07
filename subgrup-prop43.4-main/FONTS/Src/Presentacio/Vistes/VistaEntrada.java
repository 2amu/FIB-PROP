package Presentacio.Vistes;

import Presentacio.Controladors.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.table.JTableHeader;

public class VistaEntrada extends JFrame {

    private JLabel messageLabel1;
    private JButton crearEntradaButton;

    private JTable entradaTable;
    private JButton consultarButton;
    private JButton modificarButton;
    private JButton eliminarButton;
    private JButton enrereButton;
    private HashMap<String, Integer> idsEntrades = new HashMap<>();

    public VistaEntrada() {
        setTitle("GESTIÓ ENTRADES");
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
        messageLabel1 = new JLabel("Seleccionar Entrada:");
        crearEntradaButton = new JButton("Crear Entrada");
        messagePanel.add(messageLabel1, BorderLayout.WEST);
        messagePanel.add(crearEntradaButton, BorderLayout.EAST);
        centerPanel.add(messagePanel, BorderLayout.NORTH);

        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Entrades"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        entradaTable = new JTable(tableModel);

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

        entradaTable.setDefaultRenderer(Object.class, renderer);

        JScrollPane scrollPane = new JScrollPane(entradaTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(60, 0, 0, 0),
                scrollPane.getBorder()));

        centerPanel.add(scrollPane, BorderLayout.CENTER);

        entradaTable.setPreferredScrollableViewportSize(new Dimension(400, 200));
        entradaTable.setFillsViewportHeight(false);
        entradaTable.setRowHeight(50);


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
        crearEntradaButton.setFont(novaFont);
        consultarButton.setFont(novaFont);
        modificarButton.setFont(novaFont);
        eliminarButton.setFont(novaFont);
        enrereButton.setFont(novaFont);
        entradaTable.setFont(novaFont);

        JTableHeader header = entradaTable.getTableHeader();
        Font headerFont = new Font("Arial", Font.BOLD, 18);
        header.setFont(headerFont);

        crearEntradaButton.addActionListener(new ActionListener() {
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
                int filaSeleccionada = entradaTable.getSelectedRow();
                if (filaSeleccionada != -1) {
                    int idEntrada = idsEntrades.get(entradaTable.getValueAt(filaSeleccionada, 0));
                    try {
                        String[] s = CtrlPresentacio.consultarEntrada(idEntrada);


                        JTextArea textAreaEntrada = new JTextArea();
                        textAreaEntrada.setEditable(false);
                        textAreaEntrada.setFont(new Font("Arial", Font.BOLD, 14));
                        textAreaEntrada.setMargin(new Insets(10, 10, 10, 10));
                        textAreaEntrada.setRows(6);


                        textAreaEntrada.append("\nNom: " + s[1] + " \n\nEntrada: " + s[0] + "\n\n");


                        JOptionPane.showMessageDialog(null, new JScrollPane(textAreaEntrada),
                                "Consulta d'Entrada", JOptionPane.PLAIN_MESSAGE);

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                    carregarDades();
                }
            }
        });


        modificarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = entradaTable.getSelectedRow();
                if (filaSeleccionada != -1) {
                    mostrarPanelModificar();
                }
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = entradaTable.getSelectedRow();
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
                            int idEntrada = idsEntrades.get(entradaTable.getValueAt(filaSeleccionada, 0));
                            CtrlPresentacio.eliminarEntrada(idEntrada);
                            String infoMessage = "<html><div style='font-family: Arial; font-size: 12px; color: blue; text-align: center; margin: 20px 30px 20px 10px;'>"
                                    + "Entrada eliminada amb èxit.</div></html>";
                            JOptionPane.showMessageDialog(null, infoMessage, "Operació realitzada", JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
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

        int resposta = JOptionPane.showConfirmDialog(null, panel, "Modificar Contingut", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);

        if (resposta == JOptionPane.OK_OPTION) {

            String nouContingut = textField.getText();
            if (nouContingut == null) {
                String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                        + "Tots els camps son obligatoris.</div></html>";
                JOptionPane.showMessageDialog(null, errorMessage,"Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(nouContingut.length() > 500) {
                String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                        + "L'entrada pot tenir un maxim de 500 caracters.</div></html>";
                JOptionPane.showMessageDialog(null, errorMessage,"Error", JOptionPane.ERROR_MESSAGE);
                return;

            }

            int filaSeleccionada = entradaTable.getSelectedRow();
            if (filaSeleccionada != -1) {
                int idEntrada = idsEntrades.get(entradaTable.getValueAt(filaSeleccionada, 0));
                try {
                    CtrlPresentacio.modificarEntrada(idEntrada, nouContingut);
                    String infoMessage = "<html><div style='font-family: Arial; font-size: 12px; color: blue; text-align: center; margin: 20px 30px 20px 10px;'>"
                            + "Entrada modificada amb èxit.</div></html>";
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

        JPanel panel = new JPanel(new GridLayout(6, 2));

        JRadioButton radioButtonText = new JRadioButton("Text");
        JRadioButton radioButtonList = new JRadioButton("Llista Paraules");

        radioButtonList.setFont(font);
        radioButtonText.setFont(font);

        ButtonGroup group = new ButtonGroup();
        group.add(radioButtonText);
        group.add(radioButtonList);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        radioButtonList.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String infoMessage = "<html><div style='font-family: Arial; font-size: 12px; color: blue; text-align: center; margin: 20px 30px 20px 10px;'>"
                            + "Format correcte: paraula1:int,paraula2:int, ...</div></html>";
                    JOptionPane.showMessageDialog(null, infoMessage, "Operació realitzada", JOptionPane.WARNING_MESSAGE);
                }
            }
        });



        JLabel contenidoLabel2 = new JLabel("Introdueix el nom:");
        JTextField contenidoTextField2 = new JTextField();
        contenidoTextField2.setMargin(new Insets(5, 10, 5, 20));

        JLabel contenidoLabel = new JLabel("Introdueix el contingut:   ");

        JLabel tipusLabel = new JLabel("Tipus de contingut:   ");
        JTextField contenidoTextField = new JTextField();
        contenidoTextField.setMargin(new Insets(5, 10, 5, 20));

        contenidoLabel.setFont(font);
        contenidoTextField.setFont(font);
        contenidoLabel2.setFont(font);
        contenidoTextField2.setFont(font);
        tipusLabel.setFont(font);


        panel.add(contenidoLabel);
        panel.add(contenidoTextField);
        panel.add(new JPanel());
        panel.add(new JPanel());
        panel.add(contenidoLabel2);
        panel.add(contenidoTextField2);
        panel.add(new JPanel());
        panel.add(new JPanel());
        panel.add(tipusLabel);
        panel.add(radioButtonText);
        panel.add(new JLabel(""));
        panel.add(radioButtonList);

        int resposta = JOptionPane.showConfirmDialog(null, panel, "Crear Entrada", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);

        if (resposta == JOptionPane.OK_OPTION) {
            String nouContingut = contenidoTextField.getText();
            String nom = contenidoTextField2.getText();
            String tipusContingut = radioButtonText.isSelected() ? "Text" : "Llista Paraules";

            if (nouContingut.isEmpty() || nom.isEmpty() || (!radioButtonText.isSelected() && !radioButtonList.isSelected())) {
                String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                        + "Tots els camps son obligatoris.</div></html>";
                JOptionPane.showMessageDialog(null, errorMessage,"Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (nom.contains(" ")) {
                String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                        + "No es poden contenir espais en blanc.</div></html>";
                JOptionPane.showMessageDialog(null, errorMessage,"Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (nom.length() > 15) {
                String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                        + "El nom no pot tenir mes de 15 caracters.</div></html>";
                JOptionPane.showMessageDialog(null, errorMessage,"Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(nouContingut.length() > 500) {
                String errorMessage = "<html><div style='font-family: Arial; font-size: 12px; color: red; text-align: center; margin: 20px 30px 20px 10px;'>"
                        + "L'entrada pot tenir un maxim de 500 caracters.</div></html>";
                JOptionPane.showMessageDialog(null, errorMessage,"Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                if(tipusContingut.equals("Text"))
                    tipusContingut = "text";
                CtrlPresentacio.crearEntrada(nom, tipusContingut, nouContingut);
                String infoMessage = "<html><div style='font-family: Arial; font-size: 12px; color: blue; text-align: center; margin: 20px 30px 20px 10px;'>"
                        + "Entrada creada amb èxit.</div></html>";
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
            ArrayList<String> llistaEntrades = CtrlPresentacio.consultarEntrades();
            String[] entrades = new String[llistaEntrades.size()];
            for (int i = 0; i < llistaEntrades.size(); i++) {
                String[] parts = llistaEntrades.get(i).split(",");
                entrades[i] = parts[1];
                idsEntrades.put(parts[1],Integer.parseInt(parts[0]));
            }
            actualitzarTable(entrades);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void actualitzarTable (String[] entrades) {
        DefaultTableModel tableModel = (DefaultTableModel) entradaTable.getModel();
        tableModel.setRowCount(0);

        for (String s : entrades) {
            tableModel.addRow(new Object[]{s});
        }
    }
}