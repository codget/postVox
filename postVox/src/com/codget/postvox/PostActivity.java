package com.codget.postvox;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PostActivity extends Activity{

	//vars
	public Speech speech = new Speech();
	public EditText speechResult;
	public Button speechButton;

	public FB facebook;
	public Button postButton;

	//cria a tela
	@Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        facebook = new FB();
        setContentView(R.layout.postactivity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        speechResult = (EditText) findViewById(R.id.speechResult);
        speechButton = (Button) findViewById(R.id.speechButton);
        postButton = (Button) findViewById(R.id.postButton);

        if(!isNetworkAvailable()){
        	speechButton.setVisibility(View.INVISIBLE);
            postButton.setVisibility(View.INVISIBLE);
        }
	}

	//verifica se existe conex�o com a internet
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if(cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()) {
        	return true;
        }else{
        	return false;
        }
    }

	//bind no botao do facebook
    public void facebookPostClick(View v) {
    	
        facebook.getSession(this);
        
    }
    
  //bind no botao do clear text
    public void clearText(View v) {
    	String textWall = speechResult.getText().toString();
    	 if(textWall.length() > 0){
    		 speechResult.setText("");
    	 }
        
    }
    
    public void postWall() throws FileNotFoundException, MalformedURLException, IOException{
    	String textWall = speechResult.getText().toString();
    	AlertDialog dialog =  createAlert("Texto vazio", "Favor inserir um texto.");
    	 if(textWall.length() == 0){ 
    		 dialog.show();
         } else {
        	 facebook.facebookPost(textWall);
        	 speechResult.setText("");
         }
         
     	
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
    
    public AlertDialog createAlert(String title, String message){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);

    	// 2. Chain together various setter methods to set the dialog characteristics
    	builder.setMessage(message)
    	       .setTitle(title);
    	builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
    	// 3. Get the AlertDialog from create()
    	AlertDialog dialog = builder.create();
    	return dialog;
    }

}
