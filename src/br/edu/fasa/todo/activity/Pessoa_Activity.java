package br.edu.fasa.todo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import br.edu.fasa.todo.R;
import br.edu.fasa.todo.dao.PessoaDao;
import br.edu.fasa.todo.dao.ToDoDao;
import br.edu.fasa.todo.entity.Pessoa;

public class Pessoa_Activity extends Activity {
 
	private EditText nome;
	private EditText email;
	private EditText telefone;
	private EditText celular;
	private Button cadastrar;
	private Button cancelar;
	private Button sair;
	private Pessoa p;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_cadastra_pessoas);

		nome = (EditText) findViewById(R.id.edtNome);
		email = (EditText) findViewById(R.id.edtEmail);
		telefone = (EditText) findViewById(R.id.edtTelefoneFixo);
		celular = (EditText) findViewById(R.id.edtTelefoneCelular);
		cadastrar = (Button) findViewById(R.id.salvar);
		cancelar = (Button) findViewById(R.id.cancelar);
		sair = (Button) findViewById(R.id.sair);

		cadastrar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("pessoal Activity", "entrou 11");
				// Caso não haja uma tarefa instanciada (nova tarefa)
				if (p == null) {
					Log.d("pessoal Activity", "entrou 111");
					// ... cria-se uma entidade.
					p = new Pessoa();
				}
				Log.d("pessoal Activity", "entrou 111111");
				p.setNome(nome.getText().toString());
				p.setEmail(email.getText().toString());
				p.setTelefone_fixo(telefone.getText().toString());
				p.setTelefone_celular(celular.getText().toString());
				Log.d("pessoal Activity", "entrou 1");
				PessoaDao.getPessoaDao(getApplicationContext()).insert(p);
				Log.d("pessoal Activity", "entrou 2");

			}
		});

		cancelar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				// Caso não haja uma tarefa instanciada (nova tarefa)
				if (p == null) {
					
					// ... cria-se uma entidade.
					p = new Pessoa();
				}
				
				p.setNome(null);
				p.setEmail(null);
				p.setTelefone_fixo(null);
				p.setTelefone_celular(null);

			}
		});

		sair.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_pessoas, menu);
		return true;
	}
}
