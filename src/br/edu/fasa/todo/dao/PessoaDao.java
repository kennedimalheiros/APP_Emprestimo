package br.edu.fasa.todo.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.edu.fasa.todo.entity.Pessoa;

public class PessoaDao extends Dao<Pessoa> {

	/** Contexto da aplica��o. */
	private static Context context;

	/**
	 * Inst�ncia est�tica da pr�pria classe para implementa��o do padr�o de
	 * projeto Singleton.
	 */
	private static PessoaDao pessoa;

	/** Nome da tabela sobre a qual esta classe atua */
	public static String TABLE_NAME = "pessoas";

	/**
	 * Classe est�tica com a finalidade de definir constantes mais acess�veis
	 * para nomes de colunas da tabela sobre a qual esta classe atual.
	 */
	public static final class Column {
		public static String ID = "_id";
		public static String NOME = "nome";
		public static String EMAIL = "email";
		public static String TELEFONE_FIXO = "telefone_fixo";
		public static String TELEFONE_CELULAR = "telefone_celular";
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
	private PessoaDao(Context ctx) {
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
	public static PessoaDao getPessoaDao(Context ctx) {
		context = ctx;
		if (pessoa == null) {
			// Uma nova inst�ncia da classe s� � criada
			// caso o contexto da aplica��o ainda n�o
			// tenha sido definido
		}
		return pessoa;
	}

	/**
	 * M�todo respons�vel pela gera��o de uma lista com todos os resgistros
	 * armazenados no banco de dados.
	 * 
	 * @return Lista com todos os ToDo armazenados em banco de dados.
	 */
	@Override
	public List<Pessoa> selectAll() {
		// getDB() � uma implementa��o da classe base que
		// usa o DbHelper para conseguir uma inst�ncia
		// acess�vel do banco de dados.
		SQLiteDatabase db = getDB();

		// Cursor para itera��o sobre o resultado gerado
		Cursor c = null;

		try {
			// Nome das colunas que dever�o ser devolvidas
			// como resultadoda da consulta
			String columns[] = new String[] { Column.ID, Column.NOME };

			// Execu��o da consulta.
			// O resultado � um cursor para itera��o sobre o resultado.
			c = db.query(TABLE_NAME, columns, null, null, null, null,
					Column.NOME);

			// Vari�vel para armazenamento dos
			// resultados gerados pela consulta.
			List<Pessoa> allTodos = new ArrayList<Pessoa>();

			// Se existe um primeiro registro...
			if (c.moveToFirst()) {
				do {
					// ... cria-se uma classe que ser� populada pelos
					// dados retornados pela consulta
					Pessoa pessoa = new Pessoa();
					pessoa.setId(c.getInt(c.getColumnIndex(Column.ID)));
					pessoa.setNome(c.getString(c
							.getColumnIndex(Column.NOME)));

					// Adiciona-se a nova inst�ncia � lista geral.
					allTodos.add(pessoa);

					// Itera enquanto houver um pr�ximo registro.
				} while (c.moveToNext());
			}

			// Devolve a lista com todos os resgistros encontrados.
			// Pode ser nulo, caso n�o haja resgistros armazenados.
			return allTodos;

		} catch (Exception e) {
			Log.e(this.getClass().getName(), "Falha na leitura dos dados.", e);
		} finally {
			// Libera recursos para o sistema.
			// startManagingCursor(Cursor) s� funciona
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
	 * M�todo respons�vel pela consulta de uma tarefa espec�fica com base em sua
	 * id.
	 * 
	 * @param i
	 *            Identifica��o �nica da tarefa no banco de dados.
	 * @return Retorna a tarefa cuja id for localizada.
	 */
	@Override
	public Pessoa select(int i) {
		// Processo semelhante ao m�todo anterior
		SQLiteDatabase db = getDB();
		Cursor c = null;

		try {
			String columns[] = new String[] { Column.ID, Column.NOME };

			// Column.ID + " = ?" corresponde ao crit�rio de consulta.
			// new String[] { String.valueOf(i) } corresponde ao(s)
			// valor(es) a ser(em) substitu�do(s) no crit�rio de consulta.
			c = db.query(TABLE_NAME, columns, Column.ID + " = ?",
					new String[] { String.valueOf(i) }, null, null, null);

			Pessoa pessoa = new Pessoa();

			if (c.moveToFirst()) {
				pessoa.setId(c.getInt(c.getColumnIndex(Column.ID)));
				pessoa.setNome(c.getString(c
						.getColumnIndex(Column.NOME)));
				return pessoa;
			}

		} catch (Exception e) {
			Log.e(this.getClass().getName(), "Falha na leitura dos dados.", e);
		} finally {
			if (c != null) {
				c.close();
			}
			db.close();
		}

		return null;
	}

	/**
	 * M�todo respons�vel pela inser��o de uma nova tarefa no banco de dados.
	 * 
	 * @param todo
	 *            Objeto correspondente � nova tarefa a ser armazenada.
	 */
	@Override
	public void insert(Pessoa pessoa) {
		// Processo semelhante ao m�todo anterior
		SQLiteDatabase db = getDB();

		// Como n�o existe valor de retorno, n�o � necess�rio um Cursor

		try {
			// Vari�vel que conter� os valores a serem armazenados.
			ContentValues values = new ContentValues();

			// Prepara��o do par coluna/valor para inser��o.
			// _id � autoincrement�vel, bastando que seja inserida
			// a descri��o da nova tarefa.
			values.put(Column.NOME, pessoa.getNome());

			// Inser��o do(s) valor(es) na tabela espec�fica.
			db.insert(TABLE_NAME, null, values);
		} catch (Exception e) {
			Log.e("PessoaDao",
					TABLE_NAME + ": falha ao inserir registro "
							+ pessoa.getNome(), e);
		} finally {
			db.close();
		}
	}

	/**
	 * M�todo respons�vel pela atualiza��o de uma tarefa pr�-existente.
	 * 
	 * @param todo
	 *            Objeto correspondente � nova tarefa a ser atualizada.
	 */
	@Override
	public void update(Pessoa pessoa) {
		// Processo semelhante ao m�todo anterior
		SQLiteDatabase db = getDB();

		try {
			// Vari�vel que conter� os valores a serem armazenados.
			ContentValues values = new ContentValues();

			// Prepara��o do par coluna/valor para inser��o.
			values.put(Column.NOME, pessoa.getNome());

			// "_id = ?" corresponde ao crit�rio da atualiza��o.
			// new String[] { String.valueOf(todo.getId()) } corresponde ao(s)
			// valor(es) a ser(em) substitu�do(s) no crit�rio de atualiza��o.
			db.update(TABLE_NAME, values, "_id = ?",
					new String[] { String.valueOf(pessoa.getId()) });
		} catch (Exception e) {
			Log.e("PessoaDao", TABLE_NAME + ": falha ao atualizar registro "
					+ pessoa.getId(), e);
		} finally {
			db.close();
		}
	}

	/**
	 * M�todo respons�vel pela exclus�o de uma tarefa pr�-existente.
	 * 
	 * @param i
	 *            Identifica��o �nica da tarefa a ser exclu�da.
	 */
	@Override
	public void delete(int i) {
		SQLiteDatabase db = getDB();

		try {
			// "_id = ?" corresponde ao crit�rio da exclus�o.
			// new String[] { String.valueOf(i) } corresponde ao(s)
			// valor(es) a ser(em) substitu�do(s) no crit�rio de exclus�o.
			db.delete(TABLE_NAME, "_id = ?", new String[] { String.valueOf(i) });
		} catch (Exception e) {
			Log.e("PessoaDao", TABLE_NAME + ": falha ao excluir registro " + i, e);
		} finally {
			db.close();
		}
	}
	
}
