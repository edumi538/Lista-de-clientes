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

    @RequestMapping(value = "salvar", method = RequestMethod.POST)
    public String salvar(@RequestParam("nome") String nome, @RequestParam("email") String email,
            @RequestParam("telefone") String telefone, Model model) {

        Cliente novoCliente = new Cliente(nome, email, telefone);
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
