package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.google.gson.Gson;

public class Entrega {

	private int id_entrega;
	private int id_venda;

	private String nome_cliente;
	private String telefone;
	private String cpf;
	private String status;
	private String melhorHorario;
	private String endereco;
	private String dataCadastro;
	private String dataEntrega;
	private String produto;
	private int entregador;
	private int ordemEntrega;
	private String observacao;

	public Entrega(){
		SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
		this.dataCadastro = formatador.format(
				Calendar.getInstance().getTime() );
		this.ordemEntrega = 0;
		this.nome_cliente = ""; 
		this.telefone = ""; 
		this.cpf = "";
		this.status = ""; 
		this.melhorHorario = ""; 
		this.endereco = ""; 
		this.dataEntrega = ""; 
		this.produto = ""; 
		this.observacao = "";

	}
	
	public Entrega(String json){
		Entrega entrega = new Gson().fromJson(json, Entrega.class);
	
		this.ordemEntrega = entrega.getOrdemEntrega();
		this.nome_cliente = entrega.getNomeCliente(); 
		this.telefone = entrega.getTelefone(); 
		this.cpf = entrega.getCpf();
		this.status = entrega.getStatus(); 
		this.melhorHorario = entrega.getMelhorHorario(); 
		this.endereco = entrega.getEndereco(); 
		this.dataEntrega = entrega.getDataEntrega(); 
		this.produto = entrega.getProduto(); 
		this.observacao = entrega.getObservacao();

	}

	public int getId_entrega() {
		return id_entrega;
	}

	public void setId_entrega(int id_entrega) {
		this.id_entrega = id_entrega;
	}

	public int getId_venda() {
		return id_venda;
	}

	public void setId_venda(int id_venda) {
		this.id_venda = id_venda;
	}

	public String getNomeCliente() {
		return nome_cliente;
	}

	public void setNome_cliente(String nome_cliente) {
		this.nome_cliente = nome_cliente;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMelhorHorario() {
		return melhorHorario;
	}

	public void setMelhorHorario(String melhorHorario) {
		this.melhorHorario = melhorHorario;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(String dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public int getEntregador() {
		return entregador;
	}

	public void setEntregador(int idEntregador) {
		this.entregador = idEntregador;
	}

	public int getOrdemEntrega() {
		return ordemEntrega;
	}

	public void setOrdemEntrega(int ordemEntrega) {
		this.ordemEntrega = ordemEntrega;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public void imprimir(){
		System.out.println(
				"nome_cliente: " + nome_cliente +
				"telefone: " + telefone +
				"cpf: " + cpf +
				"status: " + status +
				"melhorHorario: " + melhorHorario +
				"endereco: " + endereco + 
				"dataCadastro: " + dataCadastro +
				"dataEntrega: " + dataEntrega +
				"produto: " + produto +
				"entregador: " + entregador +
				"ordemEntrega: " + ordemEntrega 
				);
	}
	
	public String toJson(){
		return new Gson().toJson(this);
//		return (
//		"{nome_cliente: " + nome_cliente +
//		", telefone: " + telefone +
//		", cpf: " + cpf +
//		", status: " + status +
//		", melhorHorario: " + melhorHorario +
//		", endereco: " + endereco + 
//		", dataCadastro: " + dataCadastro +
//		", dataEntrega: " + dataEntrega +
//		", produto: " + produto +
//		", entregador: " + entregador +
//		", ordemEntrega: " + ordemEntrega +"}"
//		);
	}

}
