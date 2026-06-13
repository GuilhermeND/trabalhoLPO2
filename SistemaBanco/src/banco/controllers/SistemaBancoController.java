package banco.controllers;

import banco.models.ModelClientes;
import banco.models.ModelContas;

public class SistemaBancoController {
    private final ModelClientes modelClientes;
    private final ModelContas modelContas;
    private final TelaClientesController telaClientesController;
    private final TelaVincularContaController telaVincularContaController;
    private final TelaOperacoesController telaOperacoesController;

    public SistemaBancoController() {
        this.modelContas = new ModelContas();
        this.modelClientes = new ModelClientes(modelContas);
        this.telaClientesController = new TelaClientesController(modelClientes, modelContas);
        this.telaVincularContaController = new TelaVincularContaController(modelClientes, modelContas);
        this.telaOperacoesController = new TelaOperacoesController(modelClientes, modelContas);
    }

    public void inicializar() {
        modelContas.inicializarContasDeTeste(modelClientes);
    }

    public TelaClientesController getTelaClientesController() {
        return telaClientesController;
    }

    public TelaVincularContaController getTelaVincularContaController() {
        return telaVincularContaController;
    }

    public TelaOperacoesController getTelaOperacoesController() {
        return telaOperacoesController;
    }
}

