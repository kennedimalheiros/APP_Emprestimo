package br.edu.fasa.todo.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Assistente de cria��o do banco de dados inicial do sistema.
 * Opcionalmente criam-se uma popula��o de dados inicial.
 * 
 * @author Luis Guisso
 * @version 1.0 02 de junho de 2012
 */
public class DbHelper extends SQLiteOpenHelper {

	/**
	 * Construtor da classe
	 * 
	 * @param context Contexto da aplica��o
	 * @param name Nome do banco de dados a ser criado
	 * @param factory Interface que permite retornar novos cursores ap�s consultas
	 * @param version Vers�o do banco de dados a ser criado
	 */
	public DbHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	/**
	 * M�todo invocado pelo Android a fim de executar
	 * a cria��o de um novo banco de dados
	 * 
	 * @param db Nome do banco de dados fornecido pelo Android 
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			String sql = "CREATE TABLE " + ToDoDao.TABLE_NAME
					+ " (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
					+ "description TEXT NOT NULL);";
			db.execSQL(sql);
			
			sql = "INSERT INTO " + ToDoDao.TABLE_NAME
					+ " (description) VALUES ('Tarefa exemplo')";
			db.execSQL(sql);
		} catch (Exception e) {
			Log.e("DbHelper", "Erro na cria��o da tabela", e);
		}
	}

	/**
	 * M�todo invocado pelo Android a fim de executar
	 * a atualiza��o de vers�es do banco de dados
	 * 
	 * @param db Banco de dados a ser atualizado
	 * @param oldVersion N�mero da vers�o atual do banco de dados
	 * @param newVersion N�mero da vers�o para a qual o banco de dados
	 * ser� atualizado
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			String sql = "CREATE TABLE IF NOT EXISTS " + ToDoDao.TABLE_NAME
					+ " (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
					+ "description TEXT NOT NULL);";
			db.execSQL(sql);
			
			sql = "ALTER TABLE " + ToDoDao.TABLE_NAME
					+ " ADD COLUMN creation DATE;";
			db.execSQL(sql);
			
			sql = "INSERT INTO " + ToDoDao.TABLE_NAME
					+ " (description, creation) "
					+ "VALUES ('Tarefa exemplo', date('now'))";
			db.execSQL(sql);
		} catch (Exception e) {
			Log.e("DbHelper", "Erro na cria��o da tabela", e);
		}
	}

}
