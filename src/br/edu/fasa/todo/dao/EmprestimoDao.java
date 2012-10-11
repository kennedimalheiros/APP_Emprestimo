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

	/** Contexto da aplica��o. */
	private static Context context;

	/**
	 * Inst�ncia est�tica da pr�pria classe para implementa��o do padr�o de
	 * projeto Singleton.
	 */
	private static EmprestimoDao emprestimo;

	/** Nome da tabela sobre a qual esta classe atua */
	public static String TABLE_NAME = "emprestimo";

	/**
	 * Classe est�tica com a finalidade de definir constantes mais acess�veis
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
	 * Somente � poss�vel ter acesso a uma inst�ncia da classe atrav�s de seu
	 * m�todo est�tico getToDoDao(Context). Assim, � poss�vel gerenciar quantas
	 * inst�ncias �nicas da classe s�o criadas.
	 * 
	 * @param ctx
	 *            Contexto da aplica��o.
	 */

	private EmprestimoDao(Context ctx) {
		super(context);
		context = ctx;
	}

	/**
	 * M�todo de acesso � inst�ncia �nica da pr�pria classe.
	 * 
	 * @param ctx
	 *            Contexto da aplica��o.
	 * @return Inst�ncia �nica da pr�pria classe.
	 */
	public static EmprestimoDao getEmprestimoDao(Context ctx) {
		context = ctx;
		if (emprestimo == null) {
			// Uma nova inst�ncia da classe s� � criada
			// caso o contexto da aplica��o ainda n�o
			// tenha sido definido
			emprestimo = new EmprestimoDao(context);
		}
		return emprestimo;
	}

	@Override
	public List<Emprestimo> selectAll() {
		// getDB() � uma implementa��o da classe base que
		// usa o DbHelper para conseguir uma inst�ncia
		// acess�vel do banco de dados.
		SQLiteDatabase db = getDB();

		// Cursor para itera��o sobre o resultado gerado
		Cursor c = null;

		try {
			// Nome das colunas que dever�o ser devolvidas
			// como resultadoda da consulta
			String columns[] = new String[] { Column.ID,
					Column.DATA_EMPRESTIMO, Column.DATA_DEVOLUCAO,
					Column.STATUS, Column.PESSOA_ID };

			// Execu��o da consulta.
			// O resultado � um cursor para itera��o sobre o resultado.
			c = db.query(TABLE_NAME, columns, null, null, null, null,
					Column.STATUS);

			// Vari�vel para armazenamento dos
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

					// Adicionar a nova inst�ncia � lista.
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
			// Variavel que conte� os valores a serem armazenados
			ContentValues values = new ContentValues();

			values.put(Column.DATA_EMPRESTIMO, emprestimo.getData_devolucao());
			values.put(Column.DATA_DEVOLUCAO, emprestimo.getData_devolucao());
			values.put(Column.STATUS, emprestimo.getData_devolucao());
			values.put(Column.PESSOA_ID, emprestimo.getPessoa_id());

			// Inser��o dos valores na tabela
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
			// Vari�vel que conter� os valores a serem armazenados.
			ContentValues values = new ContentValues();
			// Prepara��o do par coluna/valor para inser��o.
			values.put(Column.DATA_EMPRESTIMO, emprestimo.getData_devolucao());
			values.put(Column.DATA_DEVOLUCAO, emprestimo.getData_devolucao());
			values.put(Column.STATUS, emprestimo.getData_devolucao());
			values.put(Column.PESSOA_ID, emprestimo.getPessoa_id());

			// "_id = ?" corresponde ao crit�rio da atualiza��o.
			// new String[] { String.valueOf(todo.getId()) } corresponde ao(s)
			// valor(es) a ser(em) substitu�do(s) no crit�rio de atualiza��o.
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
