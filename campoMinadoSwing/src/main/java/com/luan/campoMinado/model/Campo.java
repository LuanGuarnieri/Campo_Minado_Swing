package com.luan.campoMinado.model;

import java.util.ArrayList;
import java.util.List;

public class Campo {

    private final int linha;
    private final int coluna;
    private boolean minado;
    private boolean aberto;
    private boolean isMarcado;
    private List<Campo> vizinhos = new ArrayList<>();
    private List<CampoObserver> observers = new ArrayList<>();

    public Campo(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public boolean isMarcado() {
        return this.isMarcado;
    }

    public boolean isAberto() {
        return this.aberto;
    }

    public void setAberto(boolean opcao) {
        this.aberto = opcao;

        if(aberto) {
            notificarObservers(CAMPO_EVENTO.ABRIR);
        }
    }

    public boolean isMinado() { return this.minado; }

    public int getLinha() { return this.linha; }

    public int getColuna() { return this.coluna; }

    public boolean objAlcacado() {
        return (this.aberto && !this.minado) || (this.minado && this.isMarcado);
    }

    public boolean adicionarVizinho(Campo vizinho) {
        boolean vizCruz = validarVizinho(vizinho, 1);
        boolean isDiagonal = Math.abs(this.coluna - vizinho.coluna) == 1 && validarVizinho(vizinho, 2);
        boolean isVizinho = vizCruz || isDiagonal;

        if(isVizinho) {
            vizinhos.add(vizinho);
        }

        return isVizinho;
    }

    public void alternarMarcacao() {
        if(!aberto) {
            this.isMarcado = !this.isMarcado;

            if(isMarcado) {
                notificarObservers(CAMPO_EVENTO.MARCAR);
            } else {
                notificarObservers(CAMPO_EVENTO.DESMARCAR);
            }
        }
    }

    public void minar() {
        if(!aberto) {
            this.minado = true;
        }
    }

    public boolean abrir() {
        if(!aberto && !isMarcado) {
            this.aberto = true;

            if(minado) {
                notificarObservers(CAMPO_EVENTO.EXPLODIR);
                return true;
            }

            setAberto(true);

            if(vizinhancaSegura()) {
                vizinhos.forEach(Campo::abrir);
            }
            return true;
        }
        return  false;
    }

    public boolean vizinhancaSegura() {
        return vizinhos.stream().noneMatch( v -> v.minado);
    }

    private boolean validarVizinho(Campo vizinho, int resultado) {
        return ((Math.abs(this.linha - vizinho.linha)) + (Math.abs(this.coluna - vizinho.coluna))) == resultado;
    }

    public int minasVizinhanca() {
        return (int) vizinhos.stream().filter(v -> v.minado).count();
    }

    public void reiniciar() {
        this.aberto = false;
        this.minado = false;
        this.isMarcado = false;
        notificarObservers(CAMPO_EVENTO.REINICIAR);
    }

    public void registrarObservers(CampoObserver co) {
        observers.add(co);
    }

    private void notificarObservers(CAMPO_EVENTO EVENT) {
        observers.stream().forEach( obs -> obs.campoEvento(this, EVENT));
    }
}
