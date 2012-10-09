package br.edu.fasa.todo.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Assistente de criação do banco de dados inicial do sistema.
 * Opcionalmente criam-se uma população de dados inicial.
 * 
 * @author Luis Guisso
 * @version 1.0 02 de junho de 2012
 */
public class DbHelper extends SQLiteOpenHelper {

	/**
	 * Construtor da classe
	 * 
	 * @param context Contexto da aplicação
	 * @param name Nome do banco de dados a ser criado
	 * @param factory Interface que permite retornar novos cursores após consultas
	 * @param version Versão do banco de dados a ser criado
	 */
	public DbHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	/**
	 * Método invocado pelo Android a fim de executar
	 * a criação de um novo banco de dados
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
			Log.e("DbHelper", "Erro na criação da tabela", e);
		}
	}

	/**
	 * Método invocado pelo Android a fim de executar
	 * a atualização de versões do banco de dados
	 * 
	 * @param db Banco de dados a ser atualizado
	 * @param oldVersion Número da versão atual do banco de dados
	 * @param newVersion Número da versão para a qual o banco de dados
	 * será atualizado
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
			Log.e("DbHelper", "Erro na criação da tabela", e);
		}
	}

}
