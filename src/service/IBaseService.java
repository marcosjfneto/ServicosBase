package service;

import java.util.Date;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import model.Entrega;

@WebService
@SOAPBinding(style = Style.RPC)
public interface IBaseService {

		//  cliente
		@WebMethod String getStatusEntrega(int idEntrega);

		//	entregador 
		@WebMethod String getRota(int idEntregador, String data);//data?
		@WebMethod String setObservacao(int idVenda, String observacao);
		@WebMethod String setEntregaConcluida(int idVenda);

		//	Vendedor
//		@WebMethod String cadastrarEntrega(String entregaJSOM);
		@WebMethod String cadastrarEntrega(Entrega entrega);
//		@WebMethod String cadastrarEntrega(int idEntrega, String informVenda);

		//	Gerente (gerencia as entregas)
		@WebMethod String agendarEntrega(int idVenda, String data);
		@WebMethod String associarEntregaAoEntregador(int idVenda, int idEntregador);
		@WebMethod String getEntregasNaoConcluidas();
		@WebMethod String getEntregasNaoAgendadas();
		@WebMethod String remarcarEntrega(int idVenda, String data);
		@WebMethod String cancelarEntrega(int idVenda, String motivo);
//		

}
