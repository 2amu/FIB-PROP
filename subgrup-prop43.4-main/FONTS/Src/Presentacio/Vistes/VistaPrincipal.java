package Presentacio.Vistes;

import javax.swing.*;
import java.awt.*;

import Presentacio.Controladors.CtrlPresentacio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaPrincipal extends JFrame {

    private JButton usuariButton;
    private JButton alfabetButton;
    private JButton teclatButton;
    private JButton entradaButton;

    public VistaPrincipal() {
        setTitle("MENÚ PRINCIPAL");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);


        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.setBackground(new Color(197, 230, 241));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        panel.setPreferredSize(new Dimension(300, 250));

        int margin = 200;
        panel.setBorder(BorderFactory.createEmptyBorder(margin, margin, margin, margin));


        usuariButton = new JButton("GESTIÓ USUARI");
        alfabetButton = new JButton("GESTIÓ ALFABET");
        entradaButton = new JButton("GESTIÓ ENTRADA");
        teclatButton = new JButton("GESTIÓ TECLAT");

        usuariButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.VistaUsuari();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                setVisible(false);
            }
        });

        alfabetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.VistaAlfabet();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                setVisible(false);
            }
        });

        entradaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.VistaEntrada();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                setVisible(false);
            }
        });

        teclatButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.VistaTeclat();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                setVisible(false);
            }
        });


        Font novaFont = new Font("Arial", Font.PLAIN, 18);
        usuariButton.setFont(novaFont);
        alfabetButton.setFont(novaFont);
        entradaButton.setFont(novaFont);
        teclatButton.setFont(novaFont);

        panel.add(usuariButton);
        panel.add(alfabetButton);
        panel.add(entradaButton);
        panel.add(teclatButton);

        mainPanel.add(panel, BorderLayout.CENTER);

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(Box.createVerticalGlue());
        getContentPane().add(mainPanel);
        getContentPane().add(Box.createVerticalGlue());
    }
}