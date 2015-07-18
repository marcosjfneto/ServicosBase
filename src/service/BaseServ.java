package service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.google.gson.Gson;

import model.Entrega;
import dao.EntregaDAO;

@WebService(endpointInterface = "service.IBaseService")
public class BaseServ implements IBaseService {

	private EntregaDAO dao;

	public BaseServ() {
		dao = new EntregaDAO();
	}

	

	// vendedor
//	@Override
//	public String cadastrarEntrega(String entregaJSOM) {
//		// TODO VER COMO FAZER / pode receber a entrega pronta
//		// buscar no outro webService os itens adquiridos
//		// pegar alguns os dados do cliente da nota
//		// pegar outros dados pelo formulario ou tudo pelo formulario
//		System.out.println(entregaJSOM);
//		
//		try {
//			Entrega entrega = new Gson().fromJson(entregaJSOM, Entrega.class);
//	
//			if (validaEntrega(entrega)) {
//				return dao.cadastrarEntrega(entrega);
//			}	
//		} catch (Exception e) {
//		}
//		
//		return "null";//"String";
//	}
	
	@Override
	public String cadastrarEntrega(Entrega entrega){
		entrega.imprimir();
			if (validaEntrega(entrega)) {
				return dao.cadastrarEntrega(entrega);
			}
		return "null";
	}

	
	
	// entregador
	@Override
	public String getRota(int idEntregador, String data) {// TODO feito
		if (validaData(data)){
			ArrayList<Entrega> rota = dao.getRota(idEntregador, data);
			return toStringListaDeEntrega(rota);
		} 
		return "null";
		
	}

	@Override
	public String setObservacao(int idVenda, String observacao) {
		return String.valueOf(dao.setObservacao(idVenda, observacao));
	}

	@Override
	public String setEntregaConcluida(int idVenda) {
		return String.valueOf(dao.setStatus(idVenda, "concluída"));
	}
	
	// cliente
	@Override
	public String getStatusEntrega(int idVenda) {
		return dao.getStatusEntrega(idVenda);
	}

	// gerente
	@Override
	public String agendarEntrega(int idVenda, String data) {
		if (validaDataAgendar(data)){
			return String.valueOf(dao.agendarEntrega(idVenda, data));
		}
		return "false";
	}

	@Override
	public String associarEntregaAoEntregador(int idEntrega, int idEntregador) {
		return String.valueOf(dao.associarEntregaAoEntregador(idEntrega,
				idEntregador));
	}

	@Override
	public String getEntregasNaoConcluidas() {// TODO feito
		ArrayList<Entrega> entregas = dao.getEntregasNaoConcluidas();
		return toStringListaDeEntrega(entregas);
	}

	@Override
	public String getEntregasNaoAgendadas() {// TODO feito
		ArrayList<Entrega> entregas = dao.getEntregasNaoAgendadas();
		return toStringListaDeEntrega(entregas);
	}

	@Override
	public String remarcarEntrega(int idVenda, String data) {
		if (validaDataAgendar(data)) {
			return String.valueOf(dao.remarcarEntrega(idVenda, data));
		}
		return "false";
	}

	// TODO novo
	private String toStringListaDeEntrega(ArrayList<Entrega> entregas) {
		String json = "{";
		for (Entrega e : dao.getEntregasNaoConcluidas()) {
			json += e.toJson();
		}
		return json+"}";
	}

	private static boolean validaDataAgendar(String sData) {
		if (!validaData(sData)){
			return false;
		}
		
		sData = transformaData(sData);
		
		try {
			Date hoje = Calendar.getInstance().getTime();
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date d = format.parse(sData);
			if (d.compareTo(hoje) < 0){
				return false;
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return true;
	}
//---------------------------------------
	private static boolean validaDataCadastrar(String sData) {
		if (!validaData(sData)){
			return false;
		}
		
		sData = transformaData(sData);
		
		try {
			Date calendar = Calendar.getInstance().getTime();
			
			Date hoje = new Date(calendar.getYear(), calendar.getMonth(), calendar.getDate());
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date d = format.parse(sData);
			if (d.compareTo(hoje) < 0){
				return false;
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return true;
	}

	private static boolean validaData(String sData){
		if (sData == null || sData.equals("") ){
			return false;
		}

		String regex = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})";
		if (!sData.matches(regex)){
			return false;
		}
		return true;
	}
	
	private static String transformaData(String sData){//TODO DENTRO DO METODO SETDATA
		String[] partes= sData.split("/");
		if (partes[2].length() == 2){
			return partes[0]+"/"+partes[1]+"/20"+partes[2];
		}
		return sData;
	}
	
	public static boolean calculaDiferencaEntreDatas (String data1, String data2){//TODO VERIFICAR COMO USAR ESSE MÉTODO
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date d1;
		try {
			d1 = format.parse(data1);
			Date d2 = format.parse(data2);
			
			long miliSegundosDia = 24*60*60*1000;
			return ( (d2.getTime() - d1.getTime())/(miliSegundosDia) <= 30 );
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private static boolean validaEntrega(Entrega entrega) {
		if (!validaCPF(entrega.getCpf() )){
			return false;
					
		} else if (	!validaDataCadastrar( entrega.getDataCadastro() ) ){
			return false;
			
		} else if (entrega.getEndereco() == null
				|| entrega.getEndereco().equals("")) {
			return false;
		} else if (entrega.getNomeCliente() == null
				|| entrega.getNomeCliente().equals("")) {
			return false;
		} else if (entrega.getProduto() == null
				|| entrega.getProduto().equals("")) {
			return false;
		} else if (entrega.getTelefone() == null
				|| entrega.getTelefone().equals("")) {
			return false;
		} else if (entrega.getId_venda() <= 0 ){
			return false;
		}
		return true;
	}
	
	private static boolean validaCPF(String cpf){
		if (cpf == null || cpf.equals("")) {
			return false;
		}
		
		String regex = "([0-9]{11})|([0-9]{3}[.][0-9]{3}[.][0-9]{3}-[0-9]{2})";
		if (!cpf.matches(regex)){
			System.out.println(cpf +" invalido");
			return false;
		}
		return true;
		
	}

	public static void main(String[] args) {// TODO para testar validaData
		validaData("32/05/15");
		
		Entrega e = new Entrega();
		e.setCpf("00000000000");
		e.setDataEntrega("111/08/15");
		e.setEndereco("rua");
		e.setEntregador(1);
		e.setId_venda(1);
		e.setMelhorHorario("Manha");
		e.setNomeCliente("cliente");
		e.setObservacao("observacao");
		e.setOrdemEntrega(1);
		e.setProduto("produto");
		e.setStatus("status");
		e.setTelefone("000000000");

		System.out.println(e.toJson());
		
		System.out.println(validaEntrega(e) );
		String str = "132/05/2006";
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		
		
		try {
			Date data = formatador.parse(str);
			System.out.println(data.toString());
		} catch (ParseException e1) {
			e1.printStackTrace();
		} 
	}



	@Override
	public String cancelarEntrega(int idVenda, String motivo) {
		return String.valueOf(dao.cancelarEntrega(idVenda, motivo));
	}

}
