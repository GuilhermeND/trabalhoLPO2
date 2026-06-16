package banco.dao;

import java.util.List;

// Interface generica com operacoes basicas de persistencia usadas pelos DAOs.
public interface Dao<T> {
    // Insere uma entidade no banco de dados.
    void inserir(T entidade);

    // Lista todas as entidades do tipo tratado pelo DAO.
    List<T> listarTodos();
}
