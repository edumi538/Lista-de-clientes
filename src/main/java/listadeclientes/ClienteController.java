package listadeclientes;

import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClienteController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }
    @Autowired
    private ClienteRepository repository;

    @RequestMapping("ListadeClientes")
    public String listaClientes(Model model) {

        Iterable<Usuario> clientes = repository.findAll();
        model.addAttribute("clientes", clientes);

        return "ListadeClientes";
    }
    @RequestMapping("relatorio")
    public String relatorio(Model model){
        return "relatorio";
    }
    @RequestMapping("remessas")
    public String remessas(Model model){
        return "remessas";
    }
    @RequestMapping("cadastro_Usuario")
    public String cadastro_usuarios_mapping(Model model){
        
        return "cadastro_Usuario";
        
    }
    @RequestMapping(value = "salvar", method = RequestMethod.POST)
    public String salvar(@RequestParam("nomecompleto")String nomecompleto,@RequestParam("username") String username, @RequestParam("email") String email,
            @RequestParam("password") String password, Model model) {

        Usuario novoUsuario = new Usuario(nomecompleto,username, email, password);
        repository.save(novoUsuario);

        return "redirect:/ListadeClientes";
    }
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String deleteByNome(@RequestParam String nome, Model model) {

        StringBuffer retBuf = new StringBuffer();

        repository.deleteByUsername(nome);

        Iterable<Usuario> usuarios = repository.findAll();
        model.addAttribute("usuarios", usuarios);
        
         return "redirect:/ListadeClientes";
    }
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String findByUsernameAndPassword(@RequestParam String nomecompleto, @RequestParam String password) {

        StringBuffer retBuf = new StringBuffer();

        List<Usuario> userAccountList = (List<Usuario>) repository
                .findByUsernameAndPassword(nomecompleto, password);

        if (userAccountList != null) {
            for (Usuario Cliente : userAccountList) {
                retBuf.append("nome = ");
                retBuf.append(Cliente.getUsername());
                retBuf.append(", password = ");
                retBuf.append(Cliente.getPassword());
                retBuf.append(", email = ");
                retBuf.append(Cliente.getEmail());
                retBuf.append("<br/>");
            }
        }

        if (retBuf.length() == 0) {
            retBuf.append("No record find.");
        }

        return retBuf.toString();
    }
    @RequestMapping(value = "update", method = RequestMethod.POST)
   public String updateUser(@RequestParam String username, @RequestParam String nomecompleto, @RequestParam String password, @RequestParam String email, Model model) {

        StringBuffer retBuf = new StringBuffer();

        List<Usuario> userAccountList = repository.findByUsername(nomecompleto);

        if (userAccountList != null) {
            for (Usuario Cliente : userAccountList) {
                Cliente.setUsername(username);
                Cliente.setPassword(password);
                Cliente.setEmail(email);
                repository.save(Cliente);
            }
        }
        Iterable<Usuario> usuarios = repository.findAll();
        model.addAttribute("usuarios", usuarios);

        return retBuf.toString();
        
    }
}
    

