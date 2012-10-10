package br.edu.fasa.todo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnCreateContextMenuListener;
import br.edu.fasa.todo.R;

/**
 * Activity tela principal da aplicação.
 * 
 * @author Kennedi Paulo
 * @version 1.0 09 de outubro de 2012
 */

public class Tela_inicial extends Activity
// Interface para tratar eventos de clique em menus de contexto.
		implements OnCreateContextMenuListener {

	// Código para identificação de clique em Menu/Novo.
	public static final int CAD_PESSOA = 100;
	public static final int CAD_LIVRO = 200;
	public static final int CAD_EMPRESTIMO = 300;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_inicial);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.tela_inicial, menu);
		super.onCreateOptionsMenu(menu);

		MenuItem menu_cad_pessoa = menu.add(0, CAD_PESSOA, 0, R.string.menuitem_cad_pessoa);
		menu_cad_pessoa.setIcon(android.R.drawable.ic_menu_add);
		
		MenuItem menu_cad_livro = menu.add(0, CAD_LIVRO, 0, R.string.menuitem_cad_livro);
		menu_cad_livro.setIcon(android.R.drawable.ic_menu_gallery);
		
		MenuItem menu_cad_emprestimo = menu.add(0, CAD_EMPRESTIMO, 0, R.string.menuitem_cad_emprestimo);
		menu_cad_emprestimo.setIcon(android.R.drawable.ic_menu_agenda);
		
		return true;
	}
}
