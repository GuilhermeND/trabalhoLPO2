package banco.dao;

import java.util.List;

public interface Dao<T> {
    void inserir(T entidade);

    List<T> listarTodos();
}

