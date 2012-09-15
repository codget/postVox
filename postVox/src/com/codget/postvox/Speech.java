package com.codget.postvox;

import java.util.ArrayList;

import android.content.Intent;
import android.speech.RecognizerIntent;

public class Speech{

	//vars
	public Intent intent;
	public int requestCode = 1234;
	public String result;


	public Speech(){
		intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);		
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "pt-BR");
		intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
	}

	public String setResult(int _requestCode, int resultCode, int resultOk,  Intent intent){
		if(_requestCode == requestCode && resultCode == resultOk){
            ArrayList<String> results = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            result = results.get(0);
		}

		intent.putExtra("name", "Speech");
		
		return result;
	}
}
