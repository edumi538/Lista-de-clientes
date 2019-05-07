package listadeclientes;

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

        Iterable<Cliente> clientes = repository.findAll();
        model.addAttribute("clientes", clientes);

        return "ListadeClientes";
    }
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String deletar(@RequestParam("id") Long id,Model model){
        
      
        
       repository.deleteById(id);

           
        Iterable<Cliente> clientes = repository.findAll();
        model.addAttribute("clientes", clientes);
        
         return "ListadeClientes";
    }

}
