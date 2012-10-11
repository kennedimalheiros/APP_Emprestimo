package br.edu.fasa.todo.activity;

import br.edu.fasa.todo.R;
import br.edu.fasa.todo.dao.PessoaDao;
import br.edu.fasa.todo.entity.Pessoa;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Pessoas extends Activity {

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
				p.setNome(nome.getText().toString());
				p.setEmail(email.getText().toString());
				p.setTelefone(telefone.getText().toString());
				p.setCelular(celular.getText().toString());

				PessoaDao.getPessoaDao(getApplicationContext()).insert(p);

			}
		});

		cancelar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				p.setNome(null);
				p.setEmail(null);
				p.setTelefone(null);
				p.setCelular(null);

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