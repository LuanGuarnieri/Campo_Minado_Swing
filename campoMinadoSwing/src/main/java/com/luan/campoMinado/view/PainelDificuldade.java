package com.luan.campoMinado.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PainelDificuldade extends JFrame {

    private final AppView painelPai;
    private final JButton easyModel = new JButton("FACIL");
    private final JButton mediumModel = new JButton("MEDIO");
    private final JButton hardModel = new JButton("DIFICIL");
    private static final Color EASY = new Color(33, 190, 33);
    private static final Color MEDIUM = new Color(222, 182, 21);
    private static final Color HARD = new Color(220, 21, 21);
    private static final Color PADRAO = new Color(155, 155, 155);
    public PainelDificuldade(AppView panelPai) {
        this.painelPai = panelPai;
        configPainel(this.painelPai);
    }

    private void addComponentes() {

        setLayout(new GridLayout(3, 1));

        configButton(easyModel, EASY);
        configButton(mediumModel, MEDIUM);
        configButton(hardModel, HARD);
    }

    private void setTab(String opcao) {
        Tabuleiro tab;
        dificuldadeAtual(opcao);

        switch (opcao) {

            case "FACIL":
                tab = new Tabuleiro(10, 10, 15);
                break;

            case "MEDIO":
                tab = new Tabuleiro(30, 30, 150);
                break;

            case "DIFICIL":
                tab = new Tabuleiro(40, 40, 500);
                break;

            default:
                tab = new Tabuleiro(10, 10, 10);
        }

        painelPai.setTab(tab);
        painelPai.setDificuldade(String.format("Dificuldade: %s - total de minas: %s", opcao.toLowerCase(), tab.getQtdMinas()));
    }

    private void dificuldadeAtual(String atual) {
        if(atual.equals("FACIL")) {
            easyModel.setBackground(EASY);
            mediumModel.setBackground(PADRAO);
            hardModel.setBackground(PADRAO);

        } else if (atual.equals("MEDIO")) {
            easyModel.setBackground(PADRAO);
            mediumModel.setBackground(MEDIUM);
            hardModel.setBackground(PADRAO);

        } else {
            easyModel.setBackground(PADRAO);
            mediumModel.setBackground(PADRAO);
            hardModel.setBackground(HARD);
        }
    }

    // configuracao frame
    private void configPainel(JFrame panel) {
        setSize(new Dimension(250, 250));
        setLocationRelativeTo(panel);
        setResizable(false);
        setUndecorated(true);
        addComponentes();
    }

    private void configButton(JButton btn, Color corHover) {
        btn.setFocusable(false);
        btn.setForeground(Color.WHITE);
        btn.setBackground(PADRAO);
        btn.setBorder(null);
        add(btn);

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(corHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(!painelPai.getDificulade().toUpperCase().contains(btn.getText().toUpperCase())) {
                    btn.setBackground(PADRAO);
                }

            }
        });

        btn.addActionListener(e -> {
            setTab(btn.getText().toUpperCase());
            dispose();
            painelPai.criarPainelTabuleiro();
        });
    }
}
