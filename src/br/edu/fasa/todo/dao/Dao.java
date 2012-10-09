package br.edu.fasa.todo.dao;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Padr�o de projeto DAO aplicado ao contexto
 * de uma aplica��o m�vel com SQLite 3
 * empregando generics do Java
 * 
 * @author Luis Guisso
 * @version 1.0 02 de junho de 2012
 */
public abstract class Dao <T> {

	/** Nome do banco de dados */
	private String DB_NAME = "todo";
	
	/** Vers�o do banco de dados */
	private int DB_VERSION = 1;
	
	/** Contexto da aplica��o*/
	private Context context;
	
	/** Classe assistente para BD */
	private DbHelper dbHelper;
	
	/** M�todo respons�vel por selcionar todos os registros do BD */
	public abstract List<T> selectAll();
	
	/** M�todo respons�vel por recuperar um �nico registro do BD baseado em uma id */
	public abstract T select(int i);
	
	/** M�todo respons�vel pela inser��o de um objeto no BD */
	public abstract void insert(T o);
	
	/** M�todo respons�vel pela exclus�o de um �nico registro do BD baseado em uma id */
	public abstract void delete(int i);
	
	/** M�todo respons�vel pela atualiza��o de um objeto no BD */
	public abstract void update(T o);
	
	/**
	 * Construtor da classe
	 * Obt�m uma inst�ncia do dbHelper baseado no contexto da aplica��o
	 * para um banco e vers�o espec�ficas
	 * 
	 * @param context Contexto da aplica��o
	 */
	protected Dao(Context context) {
		this.context = context;
		dbHelper = new DbHelper(context, DB_NAME, null, DB_VERSION);
	}
	
	/** Devolve o contexto recebido */
	protected Context getContext() {
		return context;
	}
	
	/** Retorna uma inst�ncia do BD instanciado pelo construtor */
	protected SQLiteDatabase getDB() {
		return dbHelper.getWritableDatabase();
	}
	
	
	// Inicio Metodos LIVRO -------------------------------------------------------------
	
	
	/** M�todo respons�vel por selcionar todos os registros do BD */
	public abstract List<T> selectAll_LIVROS();
	
	/** M�todo respons�vel por recuperar um �nico registro do BD baseado em uma id */
	public abstract T select_LIVRO(int i);
	
	/** M�todo respons�vel pela inser��o de um objeto no BD */
	public abstract void insert_LIVRO(T o);
	
	/** M�todo respons�vel pela exclus�o de um �nico registro do BD baseado em uma id */
	public abstract void delete_LIVRO(int i);
	
	/** M�todo respons�vel pela atualiza��o de um objeto no BD */
	public abstract void update_LIVRO(T o);

	
}
