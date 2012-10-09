package br.edu.fasa.todo.dao;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Padrão de projeto DAO aplicado ao contexto
 * de uma aplicação móvel com SQLite 3
 * empregando generics do Java
 * 
 * @author Luis Guisso
 * @version 1.0 02 de junho de 2012
 */
public abstract class Dao <T> {

	/** Nome do banco de dados */
	private String DB_NAME = "todo";
	
	/** Versão do banco de dados */
	private int DB_VERSION = 1;
	
	/** Contexto da aplicação*/
	private Context context;
	
	/** Classe assistente para BD */
	private DbHelper dbHelper;
	
	/** Método responsável por selcionar todos os registros do BD */
	public abstract List<T> selectAll();
	
	/** Método responsável por recuperar um único registro do BD baseado em uma id */
	public abstract T select(int i);
	
	/** Método responsável pela inserção de um objeto no BD */
	public abstract void insert(T o);
	
	/** Método responsável pela exclusão de um único registro do BD baseado em uma id */
	public abstract void delete(int i);
	
	/** Método responsável pela atualização de um objeto no BD */
	public abstract void update(T o);
	
	/**
	 * Construtor da classe
	 * Obtém uma instância do dbHelper baseado no contexto da aplicação
	 * para um banco e versão específicas
	 * 
	 * @param context Contexto da aplicação
	 */
	protected Dao(Context context) {
		this.context = context;
		dbHelper = new DbHelper(context, DB_NAME, null, DB_VERSION);
	}
	
	/** Devolve o contexto recebido */
	protected Context getContext() {
		return context;
	}
	
	/** Retorna uma instância do BD instanciado pelo construtor */
	protected SQLiteDatabase getDB() {
		return dbHelper.getWritableDatabase();
	}
	
	
	// Inicio Metodos LIVRO -------------------------------------------------------------
	
	
	/** Método responsável por selcionar todos os registros do BD */
	public abstract List<T> selectAll_LIVROS();
	
	/** Método responsável por recuperar um único registro do BD baseado em uma id */
	public abstract T select_LIVRO(int i);
	
	/** Método responsável pela inserção de um objeto no BD */
	public abstract void insert_LIVRO(T o);
	
	/** Método responsável pela exclusão de um único registro do BD baseado em uma id */
	public abstract void delete_LIVRO(int i);
	
	/** Método responsável pela atualização de um objeto no BD */
	public abstract void update_LIVRO(T o);

	
}
