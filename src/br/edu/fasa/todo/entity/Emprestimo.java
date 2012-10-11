package br.edu.fasa.todo.entity;

public class Emprestimo {
	private int id;
	private String data_emprestimo;
	private String data_devolucao;
	private String status;
	private String pessoa_id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getData_emprestimo() {
		return data_emprestimo;
	}

	public void setData_emprestimo(String data_emprestimo) {
		this.data_emprestimo = data_emprestimo;
	}

	public String getData_devolucao() {
		return data_devolucao;
	}

	public void setData_devolucao(String data_devolucao) {
		this.data_devolucao = data_devolucao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPessoa_id() {
		return pessoa_id;
	}

	public void setPessoa_id(String pessoa_id) {
		this.pessoa_id = pessoa_id;
	}
	

}
