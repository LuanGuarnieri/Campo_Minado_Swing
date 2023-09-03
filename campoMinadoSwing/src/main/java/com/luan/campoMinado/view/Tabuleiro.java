package com.luan.campoMinado.view;

import com.luan.campoMinado.model.CAMPO_EVENTO;
import com.luan.campoMinado.model.Campo;
import com.luan.campoMinado.model.CampoObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.luan.campoMinado.model.CAMPO_EVENTO.EXPLODIR;

public class Tabuleiro implements CampoObserver {
    private  int linhas;
    private int colunas;
    private int qtdMinas;
    private final List<Campo> campos = new ArrayList<>();
    public final List<Consumer<Boolean>> observers = new ArrayList<>();

    public Tabuleiro(int linhas, int colunas, int qtdMinas) {
        setarValoresiniciais(linhas,colunas,qtdMinas);
        gerarCampos();
        assossiarVizinhos();
        sortearMinas();

    }

    public int getLinhas() { return this.linhas; }

    public int getColunas() { return this.colunas; }

    private void setLinhas(int linhas) {
        if(linhas > 0) {
            this.linhas = linhas;

        } else {
            this.linhas = 5;
        }
    }

    private void setColunas(int colunas) {
        if(colunas > 0) {
            this.colunas = colunas;

        } else {
            this.colunas = 5;
        }
    }

    public void setQtdMinas(int qtdMinas) {
        if(qtdMinas > 0) {
            this.qtdMinas = qtdMinas;

        } else {
            this.qtdMinas = 5;
        }
    }

    public int getQtdMinas() {
        return this.qtdMinas;
    }

    private void setarValoresiniciais(int linhas, int colunas, int qtdMinas) {
        setLinhas(linhas);
        setColunas(colunas);
        setQtdMinas(qtdMinas);
    }

    private void gerarCampos() {
        for(int l = 1; l <= linhas; l ++ ) {
            for (int c = 1; c <= colunas; c ++ ) {
                Campo campo = new Campo(l, c);
                campo.registrarObservers(this);
                campos.add(campo);
            }
        }
    }

    private void assossiarVizinhos() {
        for(Campo c1 : campos) {
            for (Campo c2 : campos) {
                c1.adicionarVizinho(c2);
            }
        }
    }

    private void sortearMinas() {
        int minasAtivas = 0, randon;

        do {
            randon = (int)(Math.random() * campos.size());
            campos.get(randon).minar();
            minasAtivas = (int) campos.stream().filter(v -> v.isMinado()).count();

        } while (minasAtivas < qtdMinas);
    }

    public boolean objetivoAlcacado() {
        return campos.stream().allMatch( c -> c.objAlcacado());
    }

    public void reinicar() {
        campos.stream().forEach( c -> c.reiniciar());
        sortearMinas();
    }

    public void abrirCampo(int linha, int coluna) {
        campos.parallelStream()
                .filter( c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst()
                .ifPresent( c -> c.abrir());
    }

    public void alterarMarcacaoCampo(int linha, int coluna) {
        campos.parallelStream()
                .filter( c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst().ifPresent( c -> c.alternarMarcacao());
    }

    public void registraObservers(Consumer<Boolean> observer) {
        observers.add(observer);
    }

    private void notificaObservers(boolean result) {
        observers.stream().forEach( obs -> obs.accept(result));
    }

    private void mostrarMinas() {
        campos.stream()
                .filter(c-> c.isMinado())
                .filter(c -> !c.isMarcado())
                .forEach( c -> c.setAberto(true));
    }

    public void paraCada(Consumer<Campo> funcao) {
        campos.forEach(funcao);
    }
    private void verificarSeGanhou() {
        if(objetivoAlcacado()) {
            notificaObservers(true);
        }
    }

    @Override
    public void campoEvento(Campo campo, CAMPO_EVENTO tipoEvento) {
       if(tipoEvento.equals(EXPLODIR)) {
            mostrarMinas();
            notificaObservers(false);

        } else {
           verificarSeGanhou();
        }
    }
}
