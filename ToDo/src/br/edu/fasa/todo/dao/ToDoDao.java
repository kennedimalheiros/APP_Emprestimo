package br.edu.fasa.todo.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.edu.fasa.todo.entity.ToDo;

/**
 * Implementação do DAO específico para gerenciar ToDo.
 * 
 * @author Luis Guisso
 * @version 1.0 02 de junho de 2012
 */
public class ToDoDao extends Dao<ToDo> {

	/** Contexto da aplicação. */
	private static Context context;
	
	/**
	 * Instância estática da própria classe para
	 * implementação do padrão de projeto Singleton.
	 */
	private static ToDoDao todo;

	/** Nome da tabela sobre a qual esta classe atua */
	public static String TABLE_NAME = "todos";

	/**
	 * Classe estática com a finalidade de definir
	 * constantes mais acessíveis para nomes de colunas
	 * da tabela sobre a qual esta classe atual.
	 */
	public static final class Column {
		public static String ID = "_id";
		public static String DESCRIPTION = "description";
	}

	/**
	 * Construtor da classe.<br/>
	 * Somente é possível ter acesso a uma instância da
	 * classe através de seu método estático getToDoDao(Context).
	 * Assim, é possível gerenciar quantas instâncias únicas
	 * da classe são criadas.
	 * 
	 * @param ctx Contexto da aplicação.
	 */
	private ToDoDao(Context ctx) {
		super(context);
		context = ctx;
	}

	/**
	 * Método de acesso à instância única da própria classe.
	 * 
	 * @param ctx Contexto da aplicação.
	 * @return Instância única da própria classe.
	 */
	public static ToDoDao getToDoDao(Context ctx) {
		context = ctx;
		if (todo == null) {
			// Uma nova instância da classe só é criada
			// caso o contexto da aplicação ainda não
			// tenha sido definido
			todo = new ToDoDao(context);
		}
		return todo;
	}

	/**
	 * Método responsável pela geração de uma lista com todos
	 * os resgistros armazenados no banco de dados.
	 * 
	 * @return Lista com todos os ToDo armazenados em banco de dados.
	 */
	@Override
	public List<ToDo> selectAll() {
		// getDB() é uma implementação da classe base que
		// usa o DbHelper para conseguir uma instância
		// acessível do banco de dados.
		SQLiteDatabase db = getDB();
		
		// Cursor para iteração sobre o resultado gerado
		Cursor c = null;

		try {
			// Nome das colunas que deverão ser devolvidas
			// como resultadoda da consulta
			String columns[] = new String[] { Column.ID, Column.DESCRIPTION };

			// Execução da consulta.
			// O resultado é um cursor para iteração sobre o resultado.
			c = db.query(TABLE_NAME, columns, null, null, null, null,
					Column.DESCRIPTION);

			// Variável para armazenamento dos
			// resultados gerados pela consulta.
			List<ToDo> allTodos = new ArrayList<ToDo>();

			// Se existe um primeiro registro...
			if (c.moveToFirst()) {
				do {
					// ... cria-se uma classe que será populada pelos
					// dados retornados pela consulta
					ToDo todo = new ToDo();
					todo.setId(c.getInt(c.getColumnIndex(Column.ID)));
					todo.setDescription(c.getString(c
							.getColumnIndex(Column.DESCRIPTION)));

					// Adiciona-se a nova instância à lista geral.
					allTodos.add(todo);

				// Itera enquanto houver um próximo registro.
				} while (c.moveToNext());
			}

			// Devolve a lista com todos os resgistros encontrados.
			// Pode ser nulo, caso não haja resgistros armazenados.
			return allTodos;

		} catch (Exception e) {
			Log.e(this.getClass().getName(),
					"Falha na leitura dos dados.", e);
		} finally {
			// Libera recursos para o sistema.
			// startManagingCursor(Cursor) só funciona
			// para o ciclo de vida de uma Activity!
			if (c != null) {
				c.close();
			}
			db.close();
		}

		// Garante que haja um valor de retorno
		return null;
	}

	/**
	 * Método responsável pela consulta de uma tarefa específica
	 * com base em sua id.
	 * 
	 * @param i Identificação única da tarefa no banco de dados.
	 * @return Retorna a tarefa cuja id for localizada.
	 */
	@Override
	public ToDo select(int i) {
		// Processo semelhante ao método anterior
		SQLiteDatabase db = getDB();
		Cursor c = null;

		try {
			String columns[] = new String[] { Column.ID, Column.DESCRIPTION };

			// Column.ID + " = ?" corresponde ao critério de consulta.
			// new String[] { String.valueOf(i) } corresponde ao(s)
			// valor(es) a ser(em) substituído(s) no critério de consulta. 
			c = db.query(TABLE_NAME, columns, Column.ID + " = ?",
					new String[] { String.valueOf(i) }, null, null, null);

			ToDo todo = new ToDo();

			if (c.moveToFirst()) {
				todo.setId(c.getInt(c.getColumnIndex(Column.ID)));
				todo.setDescription(c.getString(c
						.getColumnIndex(Column.DESCRIPTION)));
				return todo;
			}

		} catch (Exception e) {
			Log.e(this.getClass().getName(),
					"Falha na leitura dos dados.", e);
		} finally {
			if (c != null) {
				c.close();
			}
			db.close();
		}

		return null;
	}

	/**
	 * Método responsável pela inserção de uma nova tarefa no banco de dados.
	 * 
	 * @param todo Objeto correspondente à nova tarefa a ser armazenada.
	 */
	@Override
	public void insert(ToDo todo) {
		// Processo semelhante ao método anterior
		SQLiteDatabase db = getDB();
		
		// Como não existe valor de retorno, não é necessário um Cursor

		try {
			// Variável que conterá os valores a serem armazenados.
			ContentValues values = new ContentValues();
			
			// Preparação do par coluna/valor para inserção.
			// _id é autoincrementável, bastando que seja inserida
			// a descrição da nova tarefa.
			values.put(Column.DESCRIPTION, todo.getDescription());
			
			// Inserção do(s) valor(es) na tabela específica.
			db.insert(TABLE_NAME, null, values);
		} catch (Exception e) {
			Log.e("ToDoDao",
					TABLE_NAME + ": falha ao inserir registro "
							+ todo.getDescription(), e);
		} finally {
			db.close();
		}
	}

	/**
	 * Método responsável pela atualização de uma tarefa pré-existente.
	 * 
	 * @param todo Objeto correspondente à nova tarefa a ser atualizada.
	 */
	@Override
	public void update(ToDo todo) {
		// Processo semelhante ao método anterior
		SQLiteDatabase db = getDB();

		try {
			// Variável que conterá os valores a serem armazenados.
			ContentValues values = new ContentValues();
			
			// Preparação do par coluna/valor para inserção.
			values.put(Column.DESCRIPTION, todo.getDescription());
			
			// "_id = ?" corresponde ao critério da atualização.
			// new String[] { String.valueOf(todo.getId()) } corresponde ao(s)
			// valor(es) a ser(em) substituído(s) no critério de atualização.
			db.update(TABLE_NAME, values, "_id = ?",
					new String[] { String.valueOf(todo.getId()) });
		} catch (Exception e) {
			Log.e("ToDoDao", TABLE_NAME + ": falha ao atualizar registro "
					+ todo.getId(), e);
		} finally {
			db.close();
		}
	}

	/**
	 * Método responsável pela exclusão de uma tarefa pré-existente.
	 * 
	 * @param i Identificação única da tarefa a ser excluída.
	 */
	@Override
	public void delete(int i) {
		SQLiteDatabase db = getDB();

		try {
			// "_id = ?" corresponde ao critério da exclusão.
			// new String[] { String.valueOf(i) } corresponde ao(s)
			// valor(es) a ser(em) substituído(s) no critério de exclusão.
			db.delete(TABLE_NAME, "_id = ?", new String[] { String.valueOf(i) });
		} catch (Exception e) {
			Log.e("ToDoDao", TABLE_NAME + ": falha ao excluir registro " + i, e);
		} finally {
			db.close();
		}
	}

}
