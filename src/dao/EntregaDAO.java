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
		System.out.println("id_venda: "+id_venda+ " data: "+data);
		String sql = "UPDATE entrega SET data_entrega=? WHERE id_venda = ?";
//		String sql = "UPDATE entrega SET data_entrega='"+ data +"' WHERE id_venda ='"+id_venda+"'";

		int resultado = 0;
		try {
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setString(1, data );
			st.setInt(2, id_venda);
//			ResultSet rs =
			resultado =	st.executeUpdate();

//			if (!rs.next()) {
//				return false;
//			}
//			return true;

		} catch (SQLException e) { 
			e.printStackTrace(); 
		}
		return (resultado>0) ? true : false;
//		return false;
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
					.prepareStatement("select * from entrega where status !='entregue'");
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

//			Entrega entrega; 
//			ArrayList<Entrega> lista = new ArrayList<Entrega>();
			if (!rs.next()) {
				return null;
			} 
			return getListaEntregas(rs);
//			return lista;
		} catch (SQLException e) { e.printStackTrace();
		}
		return null;
	}
	
	

	public boolean setObservacao(int id_venda, String observacao)  {//TODO MELHORAR A MENSAGEM
		try {
			String sql = "UPDATE entrega SET observacao=? WHERE id_venda = ?";
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

	public boolean setEntregue(int id_venda) {
		String sql = "UPDATE entrega SET status='entregue' WHERE id_venda = ?";
		PreparedStatement st;
		try {
			st = conexao.prepareStatement(sql);
			st.setInt(1, id_venda);
			return st.execute();
//			if (!rs.next()) {
//				return false;
//			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void excluirEntrega(int idVenda){//TODO MUDAR STATUS DA ENTREGA PARA EXCLUIDA
		
		try {
			PreparedStatement sst = conexao.prepareStatement("delete from entrega where id_venda = ?");
			sst.setInt(1, idVenda);
			sst.execute();            
		} catch (SQLException ex) { 
			ex.printStackTrace();
		}

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
//				System.out.println("getListaEnt: "+entrega.getId_entrega());
				lista.add(entrega);
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
	
	private String validaData(String data){
		//^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})
		data.r
	}
}

