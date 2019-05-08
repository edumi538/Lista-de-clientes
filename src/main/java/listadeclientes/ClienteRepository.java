package listadeclientes;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ClienteRepository extends CrudRepository<Cliente, Long>{
   List<Cliente>findByUsername(String username);
   List<Cliente> findByUsernameAndPassword(String username, String password);
    @Transactional
    void deleteByUsername(String username);

}
