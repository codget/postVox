package com.codget.postvox;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import com.codget.postvox.R;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class PostActivity extends Activity{

	//vars
	public Speech speech = new Speech();
	public EditText speechResult;

	public FB facebook = new FB();



	//cria a tela
	@Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postactivity);

        speechResult = (EditText) findViewById(R.id.speechResult);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	//bind no botao do facebook
    public boolean facebookPostClick(View v) throws FileNotFoundException, MalformedURLException, IOException{
        facebook.getSession(this);
        
        if(speech.result == ""){
        	return false;
        }

    	return facebook.facebookPost(speech.result);
    }

    //bind no click botao de speak
    public void speechButtonClick(View v){
    	startActivityForResult(speech.intent, speech.requestCode);
    }

    //bind no update do multiline speech
    public void speechResultUpdate(String _result){
    	speechResult.setText(_result);
    }



    //function de callback de activities
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
    	super.onActivityResult(requestCode, resultCode, intent);

    	//callback do speechButton
    	String _result = speech.setResult(requestCode, resultCode, RESULT_OK,  intent);
    	speechResultUpdate(_result);
    }

}
