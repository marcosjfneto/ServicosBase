package service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.google.gson.Gson;

import model.Entrega;
import dao.EntregaDAO;

@WebService(endpointInterface = "service.IBaseService")
public class BaseServ implements IBaseService{

	private EntregaDAO dao;
	
	public BaseServ(){
		dao = new EntregaDAO();	
	}
	
	
	@Override
	public float soma(float num1, float num2) { // TODO APAGAR
		return num1 + num2;
	}

	//vendedor
		@Override
		public String cadastrarEntrega(String entregaJSOM) {;//TODO VER COMO FAZER / pode receber a entrega pronta
		//buscar no outro webService os itens adquiridos
		//pegar alguns os dados do cliente da nota
		//pegar outros dados pelo formulario ou tudo pelo formulario
		Entrega entrega = new Gson().fromJson(entregaJSOM, Entrega.class);//new Entrega();
		if (validaEntrega(entrega)){
			return dao.cadastrarEntrega(entrega);
		} 
		return null;
//		entrega.setNome_cliente (); 
//		entrega.setTelefone (); 
//		entrega.setCpf ();
//		entrega.setStatus (); 
//		entrega.setMelhorHorario (); 
//		entrega.setEndereco (); 
//		entrega.setDataEntrega (); 
//		entrega.setProduto (); 
//		entrega.setObservacao ();
		}
		
		
		private boolean validaEntrega(Entrega entrega){
			if (entrega.getCpf() == null || entrega.getCpf().equals("")){
				return false;
			} else if (entrega.getDataCadastro() == null || entrega.getDataCadastro().equals("")) {
				return false;
			} else if (entrega.getEndereco() == null || entrega.getEndereco().equals("")) {
				return false;
			}else if (entrega.getNomeCliente() == null || entrega.getNomeCliente().equals("")) {
				return false;
			}else if (entrega.getProduto() == null || entrega.getProduto().equals("")) {
				return false;
			}else if (entrega.getTelefone() == null || entrega.getTelefone().equals("")) {
				return false;
			}
			
			return true;
		}
	

	

	//entregador
	@Override
	public String getRota(int idEntregador, String data) {//TODO feito 
		ArrayList<Entrega> rota = dao.getRota(idEntregador, data);
		return toStringListaDeEntrega(rota); 
	}

	@Override
	public String setObservacao(int idVenda, String observacao) {
			return String.valueOf( dao.setObservacao(idVenda, observacao) );
	}

	@Override
	public String setEntregaConcluida(int idVenda) {
			return String.valueOf( dao.setEntregue(idVenda) );
		
	}
	

	//cliente
	@Override
	public String getStatusEntrega(int idVenda) {
			return dao.getStatusEntrega(idVenda);
	}
	
	

	//gerente
	@Override
	public String agendarEntrega(int idVenda, String data) {
		return String.valueOf(dao.agendarEntrega(idVenda, data));
	}

	@Override
	public String associarEntregaAoEntregador(int idEntrega, int idEntregador) {
		return String.valueOf( dao.associarEntregaAoEntregador(idEntrega, idEntregador) );
	}

	@Override
	public String getEntregasNaoConcluidas() {//TODO feito
		ArrayList<Entrega> entregas = dao.getEntregasNaoConcluidas();			
		return toStringListaDeEntrega(entregas);
	}

	@Override
	public String getEntregasNaoAgendadas() {//TODO feito
		ArrayList<Entrega> entregas = dao.getEntregasNaoAgendadas();
		return toStringListaDeEntrega(entregas);
	}

	@Override
	public String remarcarEntrega(int idVenda, String data) {
		return String.valueOf( dao.remarcarEntrega(idVenda, data) );
	}
	//TODO novo
	private String toStringListaDeEntrega(ArrayList<Entrega> entregas){
		String json = "{";
		for (Entrega e : dao.getEntregasNaoConcluidas() ){
			json += e.toJson();
		}
		System.out.println("Entre nao conc "+json);	
		return json;
	}

}
