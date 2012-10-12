package br.edu.fasa.todo.activity;

import br.edu.fasa.todo.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnCreateContextMenuListener;

/**
 * Activity tela principal da aplicação.
 * 
 * @author Kennedi Paulo
 * @version 1.0 09 de outubro de 2012
 */

public class Tela_inicial extends Activity
// Interface para tratar eventos de clique em menus de contexto.
		implements OnCreateContextMenuListener {

	// Código para identificação de clique em Menu.
	public static final int MENUITEM_CAD_PESSOA = 100;
	public static final int MENUITEM_CAD_LIVRO = 200;
	public static final int MENUITEM_CAD_EMPRESTIMO = 300;
	
	// Código para identificação de clique em Menu/Edit.
	public static final int CONTEXTMENUITEM_EDIT = 101;
	
	// Código para identificação de clique em Menu/Delete.
	public static final int CONTEXTMENUITEM_DELETE = 102;

	// Código para identificação de solicitação de operação
	// inclusão de um nova tarefa no banco de dados.
	public static final int REQUEST_NEW = 100;
	
	// Código para identificação de solicitação de operação
	// atualização de tarefas no banco de dados.
	public static final int REQUEST_UPDATE = 101;

	// Código de resposta a ser utilizado para quando houver
	// sucesso no processamento solicitado. 
	public static final int RESPONSE_SUCCESS = 200;
	
	// Código de resposta a ser utilizado para quando houver
	// cancelamento no processamento solicitado.
	public static final int RESPONSE_CANCEL = 300;
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_inicial);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.tela_inicial, menu);
		super.onCreateOptionsMenu(menu);

		MenuItem menu_cad_pessoa = menu.add(0, MENUITEM_CAD_PESSOA, 0, R.string.menuitem_cad_pessoa);
		menu_cad_pessoa.setIcon(android.R.drawable.ic_menu_add);
		
		MenuItem menu_cad_livro = menu.add(0, MENUITEM_CAD_LIVRO, 0, R.string.menuitem_cad_livro);
		menu_cad_livro.setIcon(android.R.drawable.ic_menu_gallery);
		
		MenuItem menu_cad_emprestimo = menu.add(0, MENUITEM_CAD_EMPRESTIMO, 0, R.string.menuitem_cad_emprestimo);
		menu_cad_emprestimo.setIcon(android.R.drawable.ic_menu_agenda);
		
		return true;
	}
	
	
	/**
	 * Método responsável pelo tratamento da seleção do item de menu.
	 * 
	 * @param item Item de menu clicado passado pelo Android.
	 * @return Sucesso ou falha da operação de tratamento do evento.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		
		// Pode haver vários itens de menus, assim, identifica-se
		// qual item menu foi clicado. Sendo um pedido de gravação
		//  de uma nova tarefa... 
		if (item.getItemId() == MENUITEM_CAD_PESSOA) {
			
			// ... cria-se uma Intent para iniciar a activity que
			// conteém o formulário de inserção e...
			Intent intent = new Intent(getApplicationContext(),
					Pessoas.class);
			
			// ... inicia-se a nova activity aguardando resposta.
			startActivityForResult(intent, REQUEST_NEW);
		}
		else if (item.getItemId() == MENUITEM_CAD_LIVRO) {
			
			// ... cria-se uma Intent para iniciar a activity que
			// conteém o formulário de inserção e...
			Intent intent = new Intent(getApplicationContext(),
					Livro_Activity.class);
			
			// ... inicia-se a nova activity aguardando resposta.
			startActivityForResult(intent, REQUEST_NEW);
		}

		return true;
	}

	
}
