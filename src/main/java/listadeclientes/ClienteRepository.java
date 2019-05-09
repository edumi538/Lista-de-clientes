package listadeclientes;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ClienteRepository extends CrudRepository<Usuario, Long>{
   List<Usuario>findByUsername(String username);
   List<Usuario> findByUsernameAndPassword(String username, String password);
    @Transactional
    void deleteByUsername(String username);

}
