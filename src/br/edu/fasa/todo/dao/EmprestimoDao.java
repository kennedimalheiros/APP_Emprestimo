package br.edu.fasa.todo.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.edu.fasa.todo.entity.Emprestimo;

public class EmprestimoDao extends Dao<Emprestimo> {

	/** Contexto da aplicação. */
	private static Context context;

	/**
	 * Instância estática da própria classe para implementação do padrão de
	 * projeto Singleton.
	 */
	private static EmprestimoDao emprestimo;

	/** Nome da tabela sobre a qual esta classe atua */
	public static String TABLE_NAME = "emprestimo";

	/**
	 * Classe estática com a finalidade de definir constantes mais acessíveis
	 * para nomes de colunas da tabela sobre a qual esta classe atual.
	 */
	public static final class Column {
		public static String ID = "_id";
		public static String DATA_EMPRESTIMO = "data_emprestimo";
		public static String DATA_DEVOLUCAO = "data_devolucao";
		public static String STATUS = "status";
		public static String PESSOA_ID = "pessoa_id";
	}

	/**
	 * Construtor da classe.<br/>
	 * Somente é possível ter acesso a uma instância da classe através de seu
	 * método estático getToDoDao(Context). Assim, é possível gerenciar quantas
	 * instâncias únicas da classe são criadas.
	 * 
	 * @param ctx
	 *            Contexto da aplicação.
	 */

	private EmprestimoDao(Context ctx) {
		super(context);
		context = ctx;
	}

	/**
	 * Método de acesso à instância única da própria classe.
	 * 
	 * @param ctx
	 *            Contexto da aplicação.
	 * @return Instância única da própria classe.
	 */
	public static EmprestimoDao getEmprestimoDao(Context ctx) {
		context = ctx;
		if (emprestimo == null) {
			// Uma nova instância da classe só é criada
			// caso o contexto da aplicação ainda não
			// tenha sido definido
			emprestimo = new EmprestimoDao(context);
		}
		return emprestimo;
	}

	@Override
	public List<Emprestimo> selectAll() {
		// getDB() é uma implementação da classe base que
		// usa o DbHelper para conseguir uma instância
		// acessível do banco de dados.
		SQLiteDatabase db = getDB();

		// Cursor para iteração sobre o resultado gerado
		Cursor c = null;

		try {
			// Nome das colunas que deverão ser devolvidas
			// como resultadoda da consulta
			String columns[] = new String[] { Column.ID,
					Column.DATA_EMPRESTIMO, Column.DATA_DEVOLUCAO,
					Column.STATUS, Column.PESSOA_ID };

			// Execução da consulta.
			// O resultado é um cursor para iteração sobre o resultado.
			c = db.query(TABLE_NAME, columns, null, null, null, null,
					Column.STATUS);

			// Variável para armazenamento dos
			// resultados gerados pela consulta.
			List<Emprestimo> allTodos = new ArrayList<Emprestimo>();

			// Se existir um registro
			if (c.moveToFirst()) {
				do {
					// Popular dados
					Emprestimo emprestimo = new Emprestimo();
					emprestimo.setId(c.getInt(c.getColumnIndex(Column.ID)));
					emprestimo.setData_emprestimo(c.getString(c
							.getColumnIndex(Column.DATA_EMPRESTIMO)));
					emprestimo.setData_devolucao(c.getString(c
							.getColumnIndex(Column.DATA_DEVOLUCAO)));
					emprestimo.setStatus(c.getString(c
							.getColumnIndex(Column.STATUS)));
					emprestimo.setPessoa_id(c.getString(c
							.getColumnIndex(Column.PESSOA_ID)));

					// Adicionar a nova instância à lista.
					allTodos.add(emprestimo);

					// Itera enquando tiver registros
				} while (c.moveToNext());
			}

			return allTodos;

		} catch (Exception e) {
			Log.e(this.getClass().getName(), "Falha na leitura dos dados.", e);
		} finally {
			if (c != null) {
				c.close();
			}
			db.close();
		}

		// Garante que haja um valor de retorno
		return null;
	}

	@Override
	public Emprestimo select(int i) {
		SQLiteDatabase db = getDB();
		Cursor c = null;

		try {

			String columns[] = new String[] { Column.ID,
					Column.DATA_EMPRESTIMO, Column.DATA_DEVOLUCAO,
					Column.STATUS, Column.PESSOA_ID };
			c = db.query(TABLE_NAME, columns, Column.ID + " = ? ",
					new String[] { String.valueOf(i) }, null, null, null);

			Emprestimo emprestimo = new Emprestimo();

			if (c.moveToFirst()) {
				emprestimo.setId(c.getInt(c.getColumnIndex(Column.ID)));
				emprestimo.setData_emprestimo(c.getString(c
						.getColumnIndex(Column.DATA_EMPRESTIMO)));
				emprestimo.setData_devolucao(c.getString(c
						.getColumnIndex(Column.DATA_DEVOLUCAO)));
				emprestimo.setStatus(c.getString(c
						.getColumnIndex(Column.STATUS)));
				emprestimo.setPessoa_id(c.getString(c
						.getColumnIndex(Column.PESSOA_ID)));
				return emprestimo;
			}

		} catch (Exception e) {
			Log.e(this.getClass().getName(), "Falha na leitura dos dados.", e);
		} finally {
			if (c != null) {
				c.close();
			}
			db.close();
		}
		// Garante que haja um valor de retorno
		return null;
	}

	@Override
	public void insert(Emprestimo emprestimo) {
		SQLiteDatabase db = getDB();

		try {
			// Variavel que conteá os valores a serem armazenados
			ContentValues values = new ContentValues();

			values.put(Column.DATA_EMPRESTIMO, emprestimo.getData_devolucao());
			values.put(Column.DATA_DEVOLUCAO, emprestimo.getData_devolucao());
			values.put(Column.STATUS, emprestimo.getData_devolucao());
			values.put(Column.PESSOA_ID, emprestimo.getPessoa_id());

			// Inserção dos valores na tabela
			db.insert(TABLE_NAME, null, values);

		} catch (Exception e) {
			Log.e("LivroDao", TABLE_NAME
					+ ": falha ao inserir registro para Pessoa com id =  "
					+ emprestimo.getPessoa_id(), e);
		} finally {
			db.close();
		}

	}

	@Override
	public void delete(int i) {
		SQLiteDatabase db = getDB();

		try {
			db.delete(TABLE_NAME, "_id = ?", new String[] { String.valueOf(i) });
		} catch (Exception e) {
			Log.e("EmprestimoDao", TABLE_NAME + ": falha ao excluir registro "
					+ i, e);
		} finally {
			db.close();
		}

	}

	@Override
	public void update(Emprestimo emprestimo) {
		SQLiteDatabase db = getDB();

		try {
			// Variável que conterá os valores a serem armazenados.
			ContentValues values = new ContentValues();
			// Preparação do par coluna/valor para inserção.
			values.put(Column.DATA_EMPRESTIMO, emprestimo.getData_devolucao());
			values.put(Column.DATA_DEVOLUCAO, emprestimo.getData_devolucao());
			values.put(Column.STATUS, emprestimo.getData_devolucao());
			values.put(Column.PESSOA_ID, emprestimo.getPessoa_id());

			// "_id = ?" corresponde ao critério da atualização.
			// new String[] { String.valueOf(todo.getId()) } corresponde ao(s)
			// valor(es) a ser(em) substituído(s) no critério de atualização.
			db.update(TABLE_NAME, values, "_id = ? ",
					new String[] { String.valueOf(emprestimo.getId()) });

		} catch (Exception e) {
			Log.e("EmprestimoDao", TABLE_NAME
					+ ": falha ao atualizar registro " + emprestimo.getId(), e);
		} finally {
			db.close();
		}

	}

}
