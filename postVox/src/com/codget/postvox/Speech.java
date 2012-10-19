package com.codget.postvox;

import java.util.ArrayList;

import android.content.Intent;
import android.speech.RecognizerIntent;

public class Speech{

	//vars
	public Intent intent;
	public int requestCode = 285831614;
	
	// Lista que recebe os resultados do RecognizerIntent.EXTRA_RESULTS
	ArrayList<String> results = new ArrayList<String>();
	
	// Array usado como parâmetro parao Dialog de sugestões
    String[] resultsDialog = null;


	public Speech(){
		intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);		
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "pt-BR");
		intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, new Long(2000));
	}

	public String[] setResult(int _requestCode, int resultCode, int resultOk,  Intent intent){
		
		if(_requestCode == requestCode && resultCode == resultOk){
            results = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            //Seta o array que será usado no Dialog
            resultsDialog = new String[results.size()];    
            for(int i = 0; i < results.size(); i++){     
            	resultsDialog[i] = results.get(i);    
            }
		}
		
		return resultsDialog;
	}
}
