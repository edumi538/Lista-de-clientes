/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listadeclientes;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


public interface userAccountRepository extends CrudRepository<userAccount, Long> {

    /*
     * Get user list by user name. Please note the format should be
     * findBy<column_name>.
     */
    List<userAccount> findByUsername(String username);

    /*
     * Get user list by user name and password. Please note the format should be
     * findBy<column_name_1>And<column_name_2>.
     */
    List<userAccount> findByUsernameAndPassword(String username, String password);

    @Transactional
    void deleteByUsernameAndPassword(String username, String password);

    @Transactional
    void deleteByUsername(String username);

}
