package br.edu.fasa.todo.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;

/**
 * Classe utilit�ria.
 * 
 * @author Luis Guisso
 * @version 1.0 02 de junho de 2012
 */
public class Utility {

	/**
	 * M�todo respons�vel pela gera��o e tratamento de di�logos.
	 * 
	 * @param context Contexto da aplica��o. 
	 * @param title T�tulo do di�logo a ser gerado.
	 * @param message Mensagem de texto para intera��o com o usu�rio.
	 * @param positiveLabel Texto para resposta positiva.
	 * @param positiveListener Tratamento da resposta positiva.
	 * @param negativeLabel Texto para resposta negativa.
	 * @param negativeListener Tratamento da resposta negativa.
	 * 
	 * @return Di�logo (AlertDialog) parametrizado.
	 */
	public static AlertDialog createDialog(Context context, int title,
			int message, int positiveLabel, OnClickListener positiveListener,
			int negativeLabel, OnClickListener negativeListener) {

		Log.d("Utility", title + " - " + message);
		Log.d("Utility", title + " - " + positiveLabel);
		Log.d("Utility", positiveListener == null ? "posL nulo" : "posL OK");
		Log.d("Utility", title + " - " + negativeLabel);
		Log.d("Utility", negativeListener == null ? "negL nulo" : "negL OK");

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(positiveLabel, positiveListener);
		builder.setNegativeButton(negativeLabel, negativeListener);

		return builder.create();
	}
}
