package br.edu.fasa.todo.activity;

import br.edu.fasa.todo.R;
import br.edu.fasa.todo.dao.LivroDao;
import br.edu.fasa.todo.entity.Livro;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Livro_Activity extends Activity {
	
	private EditText titulo;
	private EditText autor;
	private EditText edicao;
	private Button cadastrar;
	private Button cancelar;
	private Button sair;
	private Livro lv;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_cadastra_livros);

		titulo = (EditText) findViewById(R.id.edtTituloLivro);
		autor = (EditText) findViewById(R.id.edtAutorLivro);
		edicao = (EditText) findViewById(R.id.edtEdicaoLivro);
		cadastrar = (Button) findViewById(R.id.salvarLivro);
		cancelar = (Button) findViewById(R.id.cancelarLivro);
		sair = (Button) findViewById(R.id.sairLivro);
		

		cadastrar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				lv.setTitulo(titulo.getText().toString());
				lv.setAutor(autor.getText().toString());
				lv.setEdicao(edicao.getText().toString());				

				LivroDao.getLivroDao(getApplicationContext()).insert(lv);

			}
		});

		cancelar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				lv.setTitulo(null);
				lv.setAutor(null);
				lv.setEdicao(null);	

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



