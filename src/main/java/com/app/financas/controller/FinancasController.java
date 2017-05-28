package com.app.financas.controller;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.financas.exceptions.LancamentoNotFoundException;
import com.app.financas.modelo.Categoria;
import com.app.financas.modelo.Conta;
import com.app.financas.modelo.Lancamento;
import com.app.financas.modelo.Response;
import com.app.financas.modelo.TipoConta;
import com.app.financas.repositorio.LancamentoMongoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/lancamentos")
public class FinancasController {

	@Autowired
	LancamentoMongoRepository lancamentoMongoRepository;

	ObjectMapper objectMapper;
	@RequestMapping(method = RequestMethod.POST)
	public Lancamento create(@RequestBody Lancamento lancamento) {
		return lancamentoMongoRepository.save(lancamento);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Lancamento find(@PathVariable String id) {
        Lancamento detail = lancamentoMongoRepository.findOne(id);
        if (detail == null) {
            throw new LancamentoNotFoundException();
        } else {
            return detail;
        }
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Lancamento update(@PathVariable String id, HttpServletRequest request){

		Lancamento existing =  find(id);
		Lancamento updated = objectMapper.readerForUpdating(existing).readValue(request.getReader());
		
		 MutablePropertyValues propertyValues = new MutablePropertyValues();
		 
		 propertyValues.add("id", updated.getId());
	        propertyValues.add("conta", updated.getContaId());
	        propertyValues.add("valor", updated.getShortDescription());
	        propertyValues.add("data", updated.getLongDescription());
	        
	        DataBinder binder = new DataBinder(updated);
	        binder.addValidators(validator);
	        binder.bind(propertyValues);
	        binder.validate();
	        if (binder.getBindingResult().hasErrors()) {
	            return new ResponseEntity<>(binder.getBindingResult().getAllErrors(), HttpStatus.BAD_REQUEST);
	        } else {
	            return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
	        }
	        
		Lancamento lancamento = lancamentoMongoRepository.findOne(id);
		
		Conta conta = contaMongoRepository.findOne(idConta);

		lancamento.setData(getDataFromString(data));
		lancamento.setConta(conta);
		lancamento.setValor(valor);

		lancamento = lancamentoMongoRepository.save(lancamento);

		Response<Lancamento> response = new Response<>();
		response.setItens(lancamento);
		response.setStatusCode(200);
		response.setMsg("Sucesso");
		return response;
	}

	private LocalDate getDataFromString(String data) {
		String[] aux = data.split("-");
		int year, month, day;
		year = Integer.valueOf(aux[0]);
		month = Integer.valueOf(aux[1]);
		day = Integer.valueOf(aux[2]);
		LocalDate dt = LocalDate.of(year, month, day);
		return dt;
	}

	@RequestMapping(value = "/lancamentos", method = RequestMethod.GET)
	public Response<List<Lancamento>> buscarLancamentos() {
		List<Lancamento> lancamento = lancamentoMongoRepository.findAll();
		Response<List<Lancamento>> response = new Response<>();
		response.setItens(lancamento);
		response.setStatusCode(200);
		response.setMsg("Sucesso");
		return response;
	}

	@RequestMapping(value = "/lancamentos", method = RequestMethod.DELETE)
	public Response<String> excluirLancamentos(@RequestParam("idLancamento") String idLancamento) {
		lancamentoMongoRepository.delete(lancamentoMongoRepository.findOne(idLancamento));
		Response<String> response = new Response<>();

		response.setItens("Item removido com sucesso!");
		response.setStatusCode(200);
		response.setMsg("Sucesso");
		return response;
	}

	@RequestMapping(value = "/categorias", method = RequestMethod.POST)
	public Response<Categoria> novoCategorias(@RequestParam(name = "nome") String nome) {
		Response<Categoria> response = new Response<>();
		response.setItens(categoriaMongoRepository.save(new Categoria(nome)));
		response.setMsg("Sucesso");
		response.setStatusCode(200);
		return response;
	}

	@RequestMapping(value = "/categorias", method = RequestMethod.PUT)
	public Response<Categoria> editarCategorias(@RequestParam("id") String id, @RequestParam("nome") String nome) {
		Categoria categoria = categoriaMongoRepository.findOne(id);
		categoria.setNome(nome);

		categoria = categoriaMongoRepository.save(categoria);

		Response<Categoria> response = new Response<>();
		response.setItens(categoria);
		response.setStatusCode(200);
		response.setMsg("Sucesso");
		return response;
	}

	@RequestMapping(value = "/categorias", method = RequestMethod.GET)
	public Response<List<Categoria>> buscarCategorias() {
		List<Categoria> categoria = categoriaMongoRepository.findAll();
		Response<List<Categoria>> response = new Response<>();
		response.setItens(categoria);
		response.setStatusCode(200);
		response.setMsg("Sucesso");
		return response;
	}

	@RequestMapping(value = "/categorias", method = RequestMethod.DELETE)
	public Response<String> excluirCategorias(@RequestParam("id") String id) {
		categoriaMongoRepository.delete(categoriaMongoRepository.findOne(id));
		Response<String> response = new Response<>();

		response.setItens("Item removido com sucesso!");
		response.setStatusCode(200);
		response.setMsg("Sucesso");
		return response;
	}

	@RequestMapping(value = "/contas", method = RequestMethod.POST)
	public Response<Conta> novoContas(@RequestParam("nome") String nome, @RequestParam("tipoConta") int tipoConta,
			@RequestParam("idCategoria") String idCategoria) {

		Response<Conta> response = new Response<>();

		Categoria categoria = categoriaMongoRepository.findOne(idCategoria);

		response.setItens(contaMongoRepository.save(new Conta(nome, TipoConta.get(tipoConta), categoria)));
		response.setMsg("Sucesso");
		response.setStatusCode(200);

		return response;
	}

	@RequestMapping(value = "/contas", method = RequestMethod.PUT)
	public Response<Conta> editarContas(@RequestParam("id") String id, @RequestParam("idCategoria") String idCategoria,
			@RequestParam("nome") String nome, @RequestParam("idTipoConta") int idTipoConta) {
		Conta conta = contaMongoRepository.findOne(id);
		Categoria categoria = categoriaMongoRepository.findOne(idCategoria);

		conta.setCategoria(categoria);
		conta.setNome(nome);
		conta.setTipoConta(TipoConta.get(idTipoConta));

		conta = contaMongoRepository.save(conta);

		Response<Conta> response = new Response<>();
		response.setItens(conta);
		response.setStatusCode(200);
		response.setMsg("Sucesso");
		return response;
	}

	@RequestMapping(value = "/contas", method = RequestMethod.GET)
	public Response<List<Conta>> buscarContas() {
		List<Conta> conta = contaMongoRepository.findAll();
		Response<List<Conta>> response = new Response<>();
		response.setItens(conta);
		response.setStatusCode(200);
		response.setMsg("Sucesso");
		return response;
	}

	@RequestMapping(value = "/contas", method = RequestMethod.DELETE)
	public Response<String> excluirContas(@RequestParam("id") String id) {
		contaMongoRepository.delete(contaMongoRepository.findOne(id));
		Response<String> response = new Response<>();

		response.setItens("Item removido com sucesso!");
		response.setStatusCode(200);
		response.setMsg("Sucesso");
		return response;
	}

}
