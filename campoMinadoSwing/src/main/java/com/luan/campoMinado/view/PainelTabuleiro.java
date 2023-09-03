package com.luan.campoMinado.view;

import javax.swing.*;
import java.awt.*;

public class PainelTabuleiro extends JPanel {

    private int totalLinhas;
    private int totalColunas;
    private Tabuleiro tab;

    public PainelTabuleiro(Tabuleiro tab) {
        totalLinhas = tab.getLinhas();
        totalColunas = tab.getColunas();
        this.tab = tab;
        addComponentes();
    }

    public void setTab(Tabuleiro tab) {
        this.tab = tab;
    }

    public void setTotalLinhas(int linhas) {
        this.totalLinhas = linhas;
    }

    public void setTotalColunas(int colunas) {
        this.totalColunas = colunas;
    }
    protected void addComponentes() {
        setLayout(new GridLayout(totalLinhas, totalColunas));
        tab.paraCada(c -> add(new CampoButton(c)));
        tab.registraObservers(e -> {

            SwingUtilities.invokeLater(() -> {
                String msg = " OH NÃO, VOCÊ PERDEU! :(";
                String title = "LOSE";

                if (e) {
                    msg = "PARABENS VOCE GANHOU! :)";
                    title = "TEMOS UM CAMPEAO!";
                }

                JOptionPane.showMessageDialog(this, msg, title, JOptionPane.INFORMATION_MESSAGE);
                tab.reinicar();
            });
        });
    }
}
