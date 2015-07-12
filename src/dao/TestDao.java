package dao;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.Entrega;

import org.junit.Test;

public class TestDao {

	@Test
	public void test() {
		int idVenda = 3;
		int idEntregador =1;
		String end = "Rua Miguel Leite, 67, Brasília, Arapiraca-AL, 573131-110";
		String horario = "MANHA";
		String cliente = "CLIENTE"+idVenda;
		String produto = "TV"+idVenda;
		String cpf = "000.000.000-00";
		String telefone ="0000-0000";
		String dataEntrega = "2015-07-05";
		String problema = "problema";
		
		Entrega entrega = new Entrega();
		entrega.setEndereco(end);
		entrega.setId_venda(idVenda);
		entrega.setMelhorHorario(horario);
		entrega.setNome_cliente(cliente);
		entrega.setProduto(produto);
		entrega.setTelefone(telefone);
		entrega.setCpf(cpf);
		
//		entrega.imprimir();
		
		//INSERI NO BANCO
		EntregaDAO dao = new EntregaDAO();
//		dao.cadastrarEntrega(entrega);
//	
//		
//		dao.agendarEntrega(idVenda, dataEntrega);
//		dao.associarEntregaAoEntregador(idVenda, idEntregador);
//		dao.setOrdem(idVenda, 1);
//		dao.setObservacao(idVenda, problema);
//		dao.setEntregue(idVenda);
		
		
		Entrega e2 = dao.getEntrega(idVenda);
		assertEquals(cpf, e2.getCpf());
		assertEquals(horario, e2.getMelhorHorario());
		assertEquals(telefone, e2.getTelefone());
		assertEquals(end, e2.getEndereco());
		
		assertEquals(dataEntrega, e2.getDataEntrega());
		assertEquals(idEntregador, e2.getEntregador());
		
		assertEquals(1, e2.getOrdemEntrega());
		assertEquals(problema, e2.getObservacao());
		assertEquals("entregue", e2.getStatus());

//		dao.excluirEntrega(idVenda-1);
		
		ArrayList<Entrega> rota = dao.getRota(idEntregador, dataEntrega);
		ArrayList<Entrega> naoAgendadas = dao.getEntregasNaoAgendadas();
		ArrayList<Entrega> naoConcluidas = dao.getEntregasNaoConcluidas();
		
		//OK
		System.out.println(dao.agendarEntrega(-1, dataEntrega));
		System.out.println(dao.agendarEntrega(0, dataEntrega));
		System.out.println(dao.agendarEntrega(1000, dataEntrega));
		System.out.println(dao.agendarEntrega(1, dataEntrega));
		
		//não precisa estar certo, apenas para ver o funcionamento
		assertEquals(rota.size(), 3);
		assertEquals(naoAgendadas.size(), 3);
		assertEquals(naoConcluidas.size(), 4);
		
	}

}
