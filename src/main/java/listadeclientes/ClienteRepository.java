package listadeclientes;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ClienteRepository extends CrudRepository<Cliente, Long>{
   List<Cliente>findByNome(String nome);
   List<Cliente> findByNomeAndPassword(String nome, String password);
    @Transactional
    void deleteByNome(String nome);

}
