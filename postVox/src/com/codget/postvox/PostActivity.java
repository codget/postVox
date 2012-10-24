package com.codget.postvox;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PostActivity extends Activity{

	//vars
	public Speech speech = new Speech();
	public EditText speechResult;
	public Button speechButton;

	public FB facebook;
	public Button postButton;
	
	private Context mContext;



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
        mContext = this;

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
    		speech = new Speech();
    	}
    }
    
    public void goToFace(View v){
    	try {
    		String uri = "facebook://facebook.com/wall";
        	Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
    		startActivity(intent);
    	} catch (ActivityNotFoundException e) {
    		Uri url = Uri.parse("http://m.facebook.com");
    		Intent intentl = new Intent(Intent.ACTION_VIEW, url);
    		startActivity(intentl);
    	}
    	
    }

    public void postWall() throws FileNotFoundException, MalformedURLException, IOException{
    	String textWall = speechResult.getText().toString();
    	AlertDialog dialog = createAlert("Texto vazio", "Favor inserir um texto.");
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
    public void speechResultUpdate(String[] resultsDialog){
    	//speechResult.setText(_result);
    	
		if (resultsDialog != null) {
			Dialog dialog = DialogSugestoes("Sugestões",resultsDialog);
			dialog.show();
		}
    }



    //function de callback de activities
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
    	super.onActivityResult(requestCode, resultCode, intent);

    	//callback do speechButton
    	String[] resultsDialog = speech.setResult(requestCode, resultCode, RESULT_OK,  intent);
    	speechResultUpdate(resultsDialog);
    	
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
    
    
    public Dialog DialogSugestoes(String title, final String[] resultsDialog) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setItems(resultsDialog, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int which) {
                	   String textResult = "";
                	   if(speechResult.getText().length() > 0){
                		   textResult = speechResult.getText().toString() + " " + resultsDialog[which].toString();
                	   } else {
                		   textResult = resultsDialog[which].toString();
                	   }
                	   
                   	   textResult = textResult.substring(0,1).toUpperCase() + textResult.substring(1);  
                   	   speechResult.setText(textResult);
                   	   
                	   speech.results.clear();
                	   speech.resultsDialog = null;
               }
        });
        return builder.create();
    }
  
}
