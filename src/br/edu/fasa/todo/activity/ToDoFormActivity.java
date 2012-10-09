package br.edu.fasa.todo.activity;

import br.edu.fasa.todo.R;
import br.edu.fasa.todo.dao.ToDoDao;
import br.edu.fasa.todo.entity.ToDo;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Activity para gest�o da tela de inclus�o/altera��o de tarefa.
 * 
 * @author Luis Guisso
 * @version 1.0 02 de junho de 2012
 */
public class ToDoFormActivity extends Activity {

	// Identifica��o �nica da tarefa sobre a qual deseja-se operar.
	private int todoId;

	// Entidade tarefa a ser alterada.
	private ToDo todo;

	/**
	 * Processamento de incializa��o da activity.
	 * 
	 * @param savedInstanceState Conjunto de dados para reinicializa��o.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Define-se o leiaute da tela.
		setContentView(R.layout.todoform);

		// Recupera��o do conte�do de dados da Intent.
		Bundle bundle = getIntent().getExtras();

		// Caso haja dados...
		if (bundle != null) {
			
			// ... recupera-se a id passada e
			todoId = bundle.getInt(ToDoActivity.TODO_ID);
			
			// ... recupera-se a tarefa correspondente � id passada.
			todo = ToDoDao.getToDoDao(getApplicationContext()).select(todoId);
			
			// Se, efetivamente, houver uma tarefa a ser editada
			if (todo != null) {
				
				// ... preenche-se o formul�rio para altera��o.
				EditText edDescription = (EditText) findViewById(R.id.description);
				edDescription.setText(todo.getDescription());
			}
		}
	}

	/**
	 * M�todo para cancelamento da opera��o.
	 * 
	 * @param v View clicada.
	 */
	public void cancel(View v) {
		
		// Define-se a resposta como sendo de cancelamento.
		setResult(ToDoActivity.RESPONSE_CANCEL);
		
		// Encerra-se a activity.
		finish();
	}

	/**
	 * M�todo para confirma��o da execu��o da tarefa de inclus�o/edi��o.
	 * 
	 * @param v View clicada.
	 */
	public void save(View v) {
		
		// Recupera-se o dado digitado.
		EditText edDescription = (EditText) findViewById(R.id.description);
		String description = edDescription.getText().toString();

		// Caso n�o haja uma tarefa instanciada (nova tarefa)
		if (todo == null) {
			
			// ... cria-se uma entidade.
			todo = new ToDo();
		}
		
		// Define-se a descri��o da tarefa.
		todo.setDescription(description);

		// Caso a identifica��o seja 0
		if (todo.getId() == 0) {
			
			// ... insere-se uma nova tarefa
			ToDoDao.getToDoDao(getApplicationContext()).insert(todo);
		} else {
			
			// ... caso contr�rio, atualiza-se a tarefa.
			ToDoDao.getToDoDao(getApplicationContext()).update(todo);
		}

		// Define-se a resposta como sucesso.
		setResult(ToDoActivity.RESPONSE_SUCCESS);
		
		// Encerra-se a activity.
		finish();
	}

}
