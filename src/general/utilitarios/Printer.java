package general.utilitarios;

import general.Dado;
import general.Regra;
import general.arvore.Branch;
import general.arvore.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Printer {
    public Map<String, List<Regra>> regrasDeClasses;

    public Printer() {
        this.regrasDeClasses = new HashMap<>();
    }

    public void printaRegras(Node raiz) {

        // popula o regrasDeClasses
        montaRegrasRecursivo(raiz, new Regra());

        // imprime as regras
        for (String classe : regrasDeClasses.keySet()) {
            System.out.print("IF ");
            for (Regra regra : regrasDeClasses.get(classe)) {
                System.out.print("(" + regra + ") || ");
            }
            System.out.print("THEN [" + classe + "]");
            System.out.println();
        }

    }


    private void montaRegrasRecursivo(Node raiz, Regra regraAtual) {
        Node atual = raiz;
        if (atual.ehFolha) {
            regrasDeClasses.computeIfAbsent(atual.nomeAtributo, k -> new ArrayList<>());
            List<Regra> listaNova = new ArrayList<>(regrasDeClasses.get(atual.nomeAtributo));
            listaNova.add(regraAtual);
            regrasDeClasses.put(atual.nomeAtributo, listaNova);
            return;
        }


        for (Branch aresta : atual.arestas) {
            Regra regraNova = regraAtual.copy();
            regraNova.add(atual.nomeAtributo, aresta.valorCondicao);
            montaRegrasRecursivo(aresta.filho, regraNova);
        }

    }

}
