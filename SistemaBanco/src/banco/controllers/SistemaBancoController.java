package banco.controllers;

import banco.dao.DbInitializer;
import banco.models.ModelClientes;
import banco.models.ModelContas;

// Controlador principal que cria e distribui os controllers usados pelas telas do sistema.
public class SistemaBancoController {
    private final ModelClientes modelClientes;
    private final ModelContas modelContas;
    private final TelaClientesController telaClientesController;
    private final TelaVincularContaController telaVincularContaController;
    private final TelaOperacoesController telaOperacoesController;

    // Monta os models e injeta as mesmas instancias nos controllers das telas.
    public SistemaBancoController() {
        this.modelContas = new ModelContas();
        this.modelClientes = new ModelClientes(modelContas);
        this.telaClientesController = new TelaClientesController(modelClientes, modelContas);
        this.telaVincularContaController = new TelaVincularContaController(modelClientes, modelContas);
        this.telaOperacoesController = new TelaOperacoesController(modelClientes, modelContas);
    }

    // Inicializa os dados padrao e prepara o estado inicial do sistema.
    public void inicializar() {
        DbInitializer.inicializarDadosPadrao();
        modelContas.inicializarContasDeTeste(modelClientes);
    }

    // Retorna o controller responsavel pela tela de clientes.
    public TelaClientesController getTelaClientesController() {
        return telaClientesController;
    }

    // Retorna o controller responsavel pela tela de vincular conta.
    public TelaVincularContaController getTelaVincularContaController() {
        return telaVincularContaController;
    }

    // Retorna o controller responsavel pela tela de operacoes bancarias.
    public TelaOperacoesController getTelaOperacoesController() {
        return telaOperacoesController;
    }
}
