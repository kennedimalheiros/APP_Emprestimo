package br.edu.fasa.todo.activity;

import java.util.List;

import br.edu.fasa.todo.R;
import br.edu.fasa.todo.dao.ToDoDao;
import br.edu.fasa.todo.entity.ToDo;
import br.edu.fasa.todo.util.Utility;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Activity para gest�o da tela principal da aplica��o.
 * 
 * @author Luis Guisso
 * @version 1.0 02 de junho de 2012
 */
public class ToDoActivity
	// Activity para cria��o de listas.
	extends ListActivity
 	// Interface para tratar eventos de clique em menus de contexto.
	implements OnCreateContextMenuListener {

	// Adaptador para manuten��o de tarefas.
	private ArrayAdapter<ToDo> adapter;

	// C�digo para inclus�o e extra��o da id da tarefa em Intents.
	public static final String TODO_ID = "todoId";

	// C�digo para identifica��o de clique em Menu/Novo.
	public static final int MENUITEM_NEW = 100;
	
	// C�digo para identifica��o de clique em Menu/Edit.
	public static final int CONTEXTMENUITEM_EDIT = 101;
	
	// C�digo para identifica��o de clique em Menu/Delete.
	public static final int CONTEXTMENUITEM_DELETE = 102;

	// C�digo para identifica��o de solicita��o de opera��o
	// inclus�o de um nova tarefa no banco de dados.
	public static final int REQUEST_NEW = 100;
	
	// C�digo para identifica��o de solicita��o de opera��o
	// atualiza��o de tarefas no banco de dados.
	public static final int REQUEST_UPDATE = 101;

	// C�digo de resposta a ser utilizado para quando houver
	// sucesso no processamento solicitado. 
	public static final int RESPONSE_SUCCESS = 200;
	
	// C�digo de resposta a ser utilizado para quando houver
	// cancelamento no processamento solicitado.
	public static final int RESPONSE_CANCEL = 300;

	/**
	 * Processamento de incializa��o da activity.
	 * 
	 * @param savedInstanceState Conjunto de dados para reinicializa��o.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Popula��o do adaptador que quarda as tarefas a serem exibidas.
		adapter = new ArrayAdapter<ToDo>(getApplicationContext(),
				android.R.layout.simple_list_item_1);
		
		// Defini��o do adaptador desta ListActivity.
		setListAdapter(adapter);

		// Carga dos dados do banco de dados para o adaptador e
		// consequente resposta na ListActivity.
		loadToDos();

		// Registro de ouvinte de menu de contexto para a ListActivity.
		registerForContextMenu(getListView());
	}

	/**
	 * M�todo respons�vel pela carga dos dados do banco de dados.<br/>
	 * Limpam-se os dados do adaptador atual, pois pode haver altera��es
	 * que devam ser refletidas nele, e, posteriormente, ocorre a carga
	 * dos dados atrav�s do m�todo selectAll() do ToDoDao.
	 */
	private void loadToDos() {
		adapter.clear();
		List<ToDo> allToDos = ToDoDao.getToDoDao(getApplicationContext())
				.selectAll();
		if (allToDos != null) {
			for (ToDo item : allToDos) {
				adapter.add(item);
			}
		}
	}

	/**
	 * M�todo respons�vel pela cria��o do menu de adi��o de nova tarefa.
	 * 
	 * @param menu Objeto representativo do menu da aplica��o
	 * passado pelo Android.
	 * @return Sucesso ou falha da opera��o de cria��o do menu.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		MenuItem menuItem = menu.add(0, MENUITEM_NEW, 0, R.string.menuitem_new);
		menuItem.setIcon(android.R.drawable.ic_menu_add);

		return true;
	}

	/**
	 * M�todo respons�vel pelo tratamento da sele��o do item de menu.
	 * 
	 * @param item Item de menu clicado passado pelo Android.
	 * @return Sucesso ou falha da opera��o de tratamento do evento.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		
		// Pode haver v�rios itens de menus, assim, identifica-se
		// qual item menu foi clicado. Sendo um pedido de grava��o
		//  de uma nova tarefa... 
		if (item.getItemId() == MENUITEM_NEW) {
			
			// ... cria-se uma Intent para iniciar a activity que
			// conte�m o formul�rio de inser��o e...
			Intent intent = new Intent(getApplicationContext(),
					ToDoFormActivity.class);
			
			// ... inicia-se a nova activity aguardando resposta.
			startActivityForResult(intent, REQUEST_NEW);
		}

		return true;
	}

	/**
	 * M�todo respons�vel pelo tratamento da resposta dada pelo formul�rio
	 * de cadastro de uma nova tarefa no banco de dados.<br/>
	 * 
	 * @param requestCode C�digo correspondente � chamada efetuada.
	 * @param resultCode C�digo de resposta da outra activity.
	 * @param data Dados devolvidos pela outra activity.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// Tarefa inserida com sucesso
		if (requestCode == REQUEST_NEW && resultCode == RESPONSE_SUCCESS) {
			
			// Se houve uma inser��o � necess�rio recarregar(reexibir) dados
			loadToDos();
			Toast.makeText(getApplicationContext(), R.string.iteminsert_sucess,
					Toast.LENGTH_SHORT).show();
		
		// Tarefa atualizada com sucesso
		} else if (requestCode == REQUEST_UPDATE && resultCode == RESPONSE_SUCCESS) {
			// Se houve uma altera��o � necess�rio recarregar(reexibir) dados
			loadToDos();
			Toast.makeText(getApplicationContext(), R.string.itemedit_sucess,
					Toast.LENGTH_SHORT).show();
		
		// Opera��o cancelada
		} else if ((requestCode == REQUEST_NEW || requestCode == REQUEST_UPDATE)
				&& resultCode == RESPONSE_CANCEL) {

			// N�o � necess�rio carregar dados, somente informar
			// o usu�rio do sucesso da opera��o.
			Toast.makeText(getApplicationContext(), R.string.cancel,
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * M�todo n�o utilizado, por�m, seria poss�vel que, ao clicar em
	 * uma tarefa, esta seja passada a um formul�rio para visualiza��o
	 * de detalhes ou mesmo altera��o.
	 * 
	 * @param l Esta ListView.
	 * @param v A View clicada.
	 * @param position A posi��o View na ListView.
	 * @param id A id da view clicada.
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		/*
		 * **
		 * ** Tamb�m � poss�vel tratar um clique simples em um item
		 * **
		 *  
		 * ToDo todo = (ToDo) l.getItemAtPosition(position);
		 * 
		 * Intent intent = new Intent(getApplicationContext(),
		 * ToDoFormActivity.class);
		 * intent.putExtra(TODO_ID, todo.getId());
		 * 
		 * startActivityForResult(intent, REQUEST_UPDATE);
		 */
	}

	/**
	 * M�todo respons�vel pela cria��o do menu de contexto de edi��o
	 * e exclus�o de uma tarefa.
	 * 
	 * @param menu O pr�prio menu de contexto.
	 * @param v A View constru�da no menu de contexto.
	 * @param menuInfo Informa��es adicionais sobre o item de menu clicado.
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		// Constr�i o menu de contexto...
		if (v.getId() == getListView().getId()) {
			
			// ... no grupo 0
			// ... com o c�digo CONTEXTMENUITEM_EDIT
			// ... na ordem 1
			// ... e com o texto R.string.context_edit.
			menu.add(0, CONTEXTMENUITEM_EDIT, 1, R.string.context_edit);
			
			// Idem � anterior.
			menu.add(0, CONTEXTMENUITEM_DELETE, 2, R.string.context_delete);
		}
	}

	/**
	 * M�todo respons�vel pelo tratamento da sele��o
	 * do item de menu de contexto.
	 * 
	 * @param item O item de menu de contexto selecionado.
	 * @return Sucesso ou falha na opera��o.
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		super.onContextItemSelected(item);

		// Recupera-se informa��o adicional sobre o item de menu clicado.
		AdapterView.AdapterContextMenuInfo info
				= (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		
		// "final" para que seja poss�vel seu uso na classe interna
		// an�nima de tratamento de eventos.
		// Aqui ocorre a recupera��o da tarefa (ToDo) clicada nesta
		// ListActivity atrav�s de seu adaptador.
		final ToDo todo = adapter.getItem(info.position);

		// Registra-se em log a tarefa selecionada. O m�todo toString()
		// da classe ToDo foi sobrescrito para apresentar somente
		// a descri��o da tarefa.
		Log.d(this.getClass().getName(), "ToDo selecionado: " + todo);

		// Caso o item de menu de contexto selecionado seja edi��o...
		if (item.getItemId() == CONTEXTMENUITEM_EDIT) {
			
			// ... cria-se uma Intent para invocar o formul�rio de edi��o
			Intent intent = new Intent(getApplicationContext(),
					ToDoFormActivity.class);
			
			// ... insere-se na Intent a id da tarefa a ser alterada
			intent.putExtra(TODO_ID, todo.getId());

			// ... e invoca-se a activity com o c�digo de requisi��o
			// de altera��o de dados da tarefa.
			startActivityForResult(intent, REQUEST_UPDATE);
		
		// Caso o item de menu de contexto selecionado seja exclus�o...
		} else if (item.getItemId() == CONTEXTMENUITEM_DELETE) {

			// Ouvinte de clique para CONFIRMA��O de exclus�o.
			// ** ATEN��O! OnClickListener de DialogInterface **
			OnClickListener positiveListener = new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					// Efetiva��o da exclus�o.
					ToDoDao.getToDoDao(getApplicationContext())
							.delete(todo.getId());
					
					// Recarga dos dados efetivos no banco de dados.
					loadToDos();
					
					// Fechamento da caixa de di�logo aberta.
					dialog.dismiss();
					
					// Informa��o de sucesso da opera��o.
					Toast.makeText(getApplicationContext(),
							R.string.itemdelete_sucess,
							Toast.LENGTH_SHORT).show();
				}
			};

			// Ouvinte de clique para CANCELAMENTO de exclus�o
			// ** ATEN��O! OnClickListener de DialogInterface **
			OnClickListener negativeListener = new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					// Fechamento da caixa de di�logo aberta.
					dialog.dismiss();
					
					// Informa��o de sucesso da opera��o.
					Toast.makeText(getApplicationContext(),
							R.string.cancel,
							Toast.LENGTH_SHORT).show();
				}
			};
			
			// this --> getContextApplication() n�o funciona!
			// O di�logo deve estar vinculado a esta (this) activity.
			AlertDialog dialog = Utility.createDialog(this,
					R.string.dialog_title, R.string.dialog_message,
					R.string.dialog_positve, positiveListener,
					R.string.dialog_negative, negativeListener);
		
			// Exibi��o do di�logo criado pela classe Utility.
			dialog.show();
			
		}

		return true;
	}

}