package com.luan.campoMinado.view;

import com.luan.campoMinado.model.CAMPO_EVENTO;
import com.luan.campoMinado.model.Campo;
import com.luan.campoMinado.model.CampoObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CampoButton extends JButton implements CampoObserver, MouseListener {

    private final Campo campo;
    private static final Color BG_PADRAO = new Color(184,184,184);
    private static final Color BG_MARCADO = new Color(8,179,247);
    private static final Color BG_EXPLOSAO = new Color(189,66,68);
    private static final Color TEXTO_VERDE = new Color(18, 199, 18);

    public CampoButton(Campo campo) {
       this.campo = campo;
       iniciar();
    }

    private void iniciar() {
        addMouseListener(this);
        restartButton();
        campo.registrarObservers(this);
    }

    private void restartButton() {
        setBackground(BG_PADRAO);
        setText("");
        setBorder(BorderFactory.createBevelBorder(0));
    }

    private void aplicarEstilo(CAMPO_EVENTO tipoEvento) {

        switch (tipoEvento) {

            case ABRIR:
                setBorder(BorderFactory.createLineBorder(Color.GRAY));
                atribuirCorCampo();
                break;

            case MARCAR:
                setBackground(BG_MARCADO);
                setForeground(Color.WHITE);
                setText("X");
                break;

            case EXPLODIR:
                setBackground(BG_EXPLOSAO);
                setForeground(Color.WHITE);
                setText("*");
                break;

            default:
                restartButton();
        }

    }

    private void atribuirCorCampo() {

        if(campo.isMinado()) {
            setBackground(BG_EXPLOSAO);
            return;
        }

        switch (campo.minasVizinhanca()) {

            case 1 :
                setForeground(TEXTO_VERDE);
                break;

            case 2 :
                setForeground(Color.BLUE);
                break;

            case 3 :
                setForeground(Color.YELLOW);
                break;

            case 4 :
                setForeground(Color.CYAN);
                break;

            case 5 :
                setForeground(Color.ORANGE);
                break;

            case 6 :
                setForeground(Color.RED);
                break;
            default:
                setForeground(Color.PINK);
        }

        setText(!campo.vizinhancaSegura() ? campo.minasVizinhanca() + "" : "");
    }

    public void reiniciarCampo() {
        setBackground(BG_PADRAO);
        setText("");
    }

    @Override
    public void campoEvento(Campo campo, CAMPO_EVENTO tipoEvento) {
        aplicarEstilo(tipoEvento);
    }

    // eventos do mouse
    @Override
    public void mousePressed(MouseEvent e) {

        if(e.getButton() == 1) {
            //btn esquerdo
            campo.abrir();
        } else {
            //btn direito
            campo.alternarMarcacao();
        }

    }
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
