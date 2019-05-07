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

        Iterable<Cliente> clientes = repository.findAll();
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
    public String salvar(@RequestParam("nome") String nome, @RequestParam("email") String email,
            @RequestParam("password") String password, Model model) {

        Cliente novoCliente = new Cliente(nome, email, password);
        repository.save(novoCliente);

        return "redirect:/ListadeClientes";
    }
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String deleteByNome(@RequestParam String nome, Model model) {

        StringBuffer retBuf = new StringBuffer();

        repository.deleteByNome(nome);

        Iterable<Cliente> clientes = repository.findAll();
        model.addAttribute("clientes", clientes);
        
         return "ListadeClientes";
    }
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String findByNomeAndPassword(@RequestParam String nome, @RequestParam String password) {

        StringBuffer retBuf = new StringBuffer();

        List<Cliente> userAccountList = (List<Cliente>) repository
                .findByNomeAndPassword(nome, password);

        if (userAccountList != null) {
            for (Cliente Cliente : userAccountList) {
                retBuf.append("nome = ");
                retBuf.append(Cliente.getNome());
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
   public String updateUser(@RequestParam String nome, @RequestParam String password, @RequestParam String email, Model model) {

        StringBuffer retBuf = new StringBuffer();

        List<Cliente> userAccountList = repository.findByNome(nome);

        if (userAccountList != null) {
            for (Cliente Cliente : userAccountList) {
                Cliente.setNome(nome);
                Cliente.setPassword(password);
                Cliente.setEmail(email);
                repository.save(Cliente);
            }
        }
        Iterable<Cliente> clientes = repository.findAll();
        model.addAttribute("clientes", clientes);

        return retBuf.toString();
        
    }
}
    

