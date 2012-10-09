package br.edu.fasa.todo.activity;

import br.edu.fasa.todo.R;
import br.edu.fasa.todo.dao.ToDoDao;
import br.edu.fasa.todo.entity.ToDo;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Activity para gestão da tela de inclusão/alteração de tarefa.
 * 
 * @author Luis Guisso
 * @version 1.0 02 de junho de 2012
 */
public class ToDoFormActivity extends Activity {

	// Identificação única da tarefa sobre a qual deseja-se operar.
	private int todoId;

	// Entidade tarefa a ser alterada.
	private ToDo todo;

	/**
	 * Processamento de incialização da activity.
	 * 
	 * @param savedInstanceState Conjunto de dados para reinicialização.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Define-se o leiaute da tela.
		setContentView(R.layout.todoform);

		// Recuperação do conteúdo de dados da Intent.
		Bundle bundle = getIntent().getExtras();

		// Caso haja dados...
		if (bundle != null) {
			
			// ... recupera-se a id passada e
			todoId = bundle.getInt(ToDoActivity.TODO_ID);
			
			// ... recupera-se a tarefa correspondente á id passada.
			todo = ToDoDao.getToDoDao(getApplicationContext()).select(todoId);
			
			// Se, efetivamente, houver uma tarefa a ser editada
			if (todo != null) {
				
				// ... preenche-se o formulário para alteração.
				EditText edDescription = (EditText) findViewById(R.id.description);
				edDescription.setText(todo.getDescription());
			}
		}
	}

	/**
	 * Método para cancelamento da operação.
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
	 * Método para confirmação da execução da tarefa de inclusão/edição.
	 * 
	 * @param v View clicada.
	 */
	public void save(View v) {
		
		// Recupera-se o dado digitado.
		EditText edDescription = (EditText) findViewById(R.id.description);
		String description = edDescription.getText().toString();

		// Caso não haja uma tarefa instanciada (nova tarefa)
		if (todo == null) {
			
			// ... cria-se uma entidade.
			todo = new ToDo();
		}
		
		// Define-se a descrição da tarefa.
		todo.setDescription(description);

		// Caso a identificação seja 0
		if (todo.getId() == 0) {
			
			// ... insere-se uma nova tarefa
			ToDoDao.getToDoDao(getApplicationContext()).insert(todo);
		} else {
			
			// ... caso contrário, atualiza-se a tarefa.
			ToDoDao.getToDoDao(getApplicationContext()).update(todo);
		}

		// Define-se a resposta como sucesso.
		setResult(ToDoActivity.RESPONSE_SUCCESS);
		
		// Encerra-se a activity.
		finish();
	}

}
