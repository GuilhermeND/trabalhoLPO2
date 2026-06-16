package banco;

import banco.views.TelaPrincipal;

import javax.swing.SwingUtilities;

// Classe de entrada da aplicacao.
// Responsavel por iniciar a interface grafica principal usando a thread correta do Swing.
public class Main {
    // Metodo principal executado quando o programa e iniciado.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }
}
