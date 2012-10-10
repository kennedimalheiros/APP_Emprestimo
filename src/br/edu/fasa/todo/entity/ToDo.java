package br.edu.fasa.todo.entity;

/**
 * Classe representativa da entidade Tarefa a ser empregada na aplicação. Também
 * conhecida como Plain Old Java Object (POJO) e, às vezes, Bean.
 * 
 * @author Luis Guisso
 * @version 1.0 02 de junho de 2012
 */
public class ToDo {

	private int id;
	private String description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return this.description;
	}
}
