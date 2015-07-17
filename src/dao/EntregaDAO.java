package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Entrega;

public class EntregaDAO extends Conexao {
	public EntregaDAO() {
		super();
	}

	//vendedor
	public String cadastrarEntrega(Entrega entrega) {//TODO COLOCAR STRING
		try {
			String sql = "INSERT INTO entrega ("
					+"id_venda, nome_cliente, cpf, "
					+"telefone, melhor_horario, endereco, "  
					+"data_cadastro, produto, id_entregador, "
					+"data_entrega, status, observacao, "
					+"ordem_entrega) "
					+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement st;
			st = conexao.prepareStatement(sql);
			
			st.setInt(1, entrega.getId_venda());
			st.setString(2, entrega.getNomeCliente());
			st.setString(3, entrega.getCpf());
			
			st.setString(4, entrega.getTelefone());
			st.setString(5, entrega.getMelhorHorario());
			st.setString(6, entrega.getEndereco());
			
			st.setString(7, entrega.getDataCadastro());
			st.setString(8, entrega.getProduto());
			st.setInt(9, entrega.getEntregador());
			
			st.setString(10, entrega.getDataEntrega());
			st.setString(11, entrega.getStatus());
			st.setString(12, entrega.getObservacao());
			
			st.setInt(13, entrega.getOrdemEntrega());
			
			boolean result = st.execute();
			System.out.println("dao cadastrou");
			
			st.close();
			return String.valueOf(result);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}

	// cliente
	public String getStatusEntrega(int idVenda) {
		return getEntrega(idVenda).getStatus();
	}

	//gerente
	public boolean remarcarEntrega(int id_venda, String data){
		return agendarEntrega(id_venda, data);
	}

	//	Gerente (gerencia as entregas)
	public boolean agendarEntrega(int id_venda, String data){
		String sql = "UPDATE entrega SET data_entrega=?, status=? WHERE id_venda = ?";

		int resultado = 0;
		try {
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setString(1, data );
			st.setString(2, "agendada");
			st.setInt(3, id_venda);
			resultado =	st.executeUpdate();
//			agendarEntrega(id_venda, data);

		} catch (SQLException e) { 
			e.printStackTrace(); 
		}
		return (resultado>0) ? true : false;
	}

	public boolean associarEntregaAoEntregador(int idVenda, int idEntregador){

		String sql = "UPDATE entrega SET id_entregador=? WHERE id_venda = ?";
		try {
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setInt(1, idEntregador );
			st.setInt(2, idVenda);
			return st.execute();

		} catch (SQLException e) { e.printStackTrace(); }

		return false;
	}


	public ArrayList<Entrega> getEntregasNaoConcluidas() {//TODO Lista de entregas

		PreparedStatement st;
		try {
			st = conexao
					.prepareStatement("select * from entrega where status !='concluída'");
			ResultSet rs = st.executeQuery();
			
			if (!rs.next()) {
				return null;
			}
			return getListaEntregas(rs);

		} catch (SQLException e) { 
			e.printStackTrace(); 
		}

		return null;
	}

	public ArrayList<Entrega> getEntregasNaoAgendadas() {//TODO VER DATA, Lista de entregas

		PreparedStatement st;
		try {
			st = conexao
					.prepareStatement("select * from entrega where data_entrega =''");
			ResultSet rs = st.executeQuery();
			
			if (!rs.next()) {
				return null;
			}
			return getListaEntregas(rs);
//			return retornarEntrega(rs);
		} catch (SQLException e) { e.printStackTrace(); }

		return null;
	}


	// entregador
	public ArrayList<Entrega> getRota(int id_entregador, String data) {//TODO Lista de entregas
		//		SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");

		String sql = "SELECT * FROM entrega WHERE id_entregador = ? AND data_entrega = ? ORDER BY ordem_entrega ASC";
		PreparedStatement st;
		try {
			st = conexao.prepareStatement(sql);

			st.setInt(1, id_entregador);
			st.setString(2, data );
			ResultSet rs = st.executeQuery();
			
//			ArrayList<Entrega> lista = new ArrayList<Entrega>();
			if (!rs.next()) {
				return null;
			} 
			return getListaEntregas(rs);
		} catch (SQLException e) { e.printStackTrace();
		}
		return null;
	}
	
	

	public boolean setObservacao(int id_venda, String observacao)  {//TODO MELHORAR A MENSAGEM
		try {
			String sql = "UPDATE entrega SET observacao=?, status='não concluída' WHERE id_venda = ?";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setString(1, observacao);
			st.setInt(2, id_venda);
			return st.execute();
			
		} catch (SQLException e) { e.printStackTrace(); }
		return true;
	}

	public boolean setOrdem(int id_venda, int ordem)  {
		String sql = "UPDATE entrega SET ordem_entrega=? WHERE id_venda = ?";
		PreparedStatement st;
		try {
			st = conexao.prepareStatement(sql);
			st.setInt(1, ordem);
			st.setInt(2, id_venda);
			return st.execute();
			
		} catch (SQLException e) { 
			e.printStackTrace(); 
		}

		return false;
	}

	public boolean setConcluida(int id_venda) {
		String sql = "UPDATE entrega SET status='concluída' WHERE id_venda = ?";
		PreparedStatement st;
		try {
			st = conexao.prepareStatement(sql);
			st.setInt(1, id_venda);
			return st.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private void setEmExecucao(int idVenda){
		this.setStatus(idVenda, "aguardando entrega");
	}
	
	
	public boolean setStatus(int id_venda, String status) {
		String sql = "UPDATE entrega SET status='"+status+"' WHERE id_venda = ?";
		PreparedStatement st;
		try {
			st = conexao.prepareStatement(sql);
			st.setInt(1, id_venda);
			return st.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean cancelarEntrega(int idVenda, String motivo){//TODO 
		
		try {
			String sql = "UPDATE entrega SET observacao=?, status='cancelada' WHERE id_venda = ?";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setString(1, motivo);
			st.setInt(2, idVenda);
			return st.execute();
		} catch (SQLException ex) { 
			ex.printStackTrace();
		}
		return false;

	}


	//AUXILIARES
	public Entrega getEntrega(int id_venda) {

		PreparedStatement st;
		try {
			st = conexao
					.prepareStatement("select * from entrega where id_venda = ?");

			st.setInt(1, id_venda);
			ResultSet rs = st.executeQuery();
			if (!rs.next()) {
				return null;
			}
			return retornarEntrega(rs);

		} catch (Exception e) { e.printStackTrace(); }

		return null;
	}

	public ArrayList<Entrega> getListaEntregas(ResultSet rs){
		Entrega entrega; 
		ArrayList<Entrega> lista = new ArrayList<Entrega>();
		
			try {
				
				do{ 
				entrega = retornarEntrega(rs);
				lista.add(entrega);
				setEmExecucao(entrega.getId_venda());
				} while (rs.next());
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return lista;
	}
	
	private Entrega retornarEntrega(ResultSet rs) throws NullPointerException, SQLException{
		Entrega entrega = new Entrega();            

		entrega.setId_entrega(rs.getInt("id_entrega"));
		entrega.setId_venda(rs.getInt("id_venda"));

		entrega.setNome_cliente(rs.getString("nome_cliente"));
		entrega.setCpf(rs.getString("cpf"));
		entrega.setTelefone(rs.getString("telefone"));

		entrega.setMelhorHorario(rs.getString("melhor_horario"));
		entrega.setEndereco(rs.getString("endereco"));
		entrega.setDataCadastro(rs.getString("data_cadastro"));

		entrega.setProduto(rs.getString("produto"));
		entrega.setEntregador(rs.getInt("id_entregador"));
		entrega.setDataEntrega(rs.getString("data_entrega"));

		entrega.setStatus(rs.getString("status"));
		entrega.setObservacao(rs.getString("observacao"));
		entrega.setOrdemEntrega(rs.getInt("ordem_entrega") );
		return entrega;        
	}
	
	
}

