package com.luan.campoMinado.view;

import javax.swing.*;
import java.awt.*;

public class AppView extends JFrame {

    private final PainelDificuldade DIFICULDADE = new PainelDificuldade(this);
    private Tabuleiro tab;
    private JPanel jp;
    private PainelTabuleiro jpTab;
    private Label dificuldade;

    public AppView() {
        configTela();
        mostrarPainelDificuldade();
    }

    protected void setDificuldade(String texto) {
        this.dificuldade.setText(texto);
        this.dificuldade.setSize(300, 20);
    }

    protected void setTab(Tabuleiro tab) {
        this.tab = tab;
    }

    public String getDificulade() {
        return this.dificuldade.getText();
    }
    protected void criarPainelTabuleiro() {
        if(jpTab == null) {
            jpTab = new PainelTabuleiro(tab);
            add(jpTab, BorderLayout.CENTER);

        } else {
            jpTab.removeAll();
            jpTab.setTab(tab);
            jpTab.setTotalColunas(tab.getColunas());
            jpTab.setTotalLinhas(tab.getLinhas());
            jpTab.addComponentes();
        }

        validarTamanhoTelaDificuldade();
        setVisible(true);
    }

    private void validarTamanhoTelaDificuldade() {
        String nivel;
        if (dificuldade.getText() != null) {
             nivel = dificuldade.getText();

             if(nivel.toUpperCase().contains("FACIL")) {
                 setSize(new Dimension(500, 500));

             } else if(nivel.toUpperCase().contains("MEDIO")) {
                 setSize(new Dimension(600, 600));

             } else {
                 setSize(new Dimension(750, 750));
             }
        }
        setLocationRelativeTo(null);
    }

    private void configTela() {
        JButton btn = configBtn("DIFICULDADE", new Color(154, 145, 145));
        FlowLayout layoutPanel = new FlowLayout(5);
        BorderLayout layoutFrame = new BorderLayout();
        jp = new JPanel();
        dificuldade = new Label();

        setTitle("Campo Minado");
        setMinimumSize(new Dimension(500, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(layoutFrame);

        jp.setLayout(layoutPanel);
        jp.add(btn);
        jp.add(dificuldade);
        add(jp, BorderLayout.NORTH);

        btn.addActionListener( e -> {
            System.out.println("Entrouu");
            DIFICULDADE.setVisible(true);
        });
    }

    private void mostrarPainelDificuldade() {
        DIFICULDADE.setVisible(true);
    }

    private JButton configBtn(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFocusable(false);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);

        return btn;
    }
}
