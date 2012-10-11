package com.codget.postvox;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;



public class FB extends Facebook{

	public static String appId = "285831614858736";
	public String[] permissions = new String[] {"publish_stream", "read_stream"};
	PostActivity contextLogin; 
    //construtor da classe
	public FB(){
		super(appId);
	}

	//pega a sessao, se nao esta logado, cria
	public void getSession(PostActivity context){
		contextLogin = context;
    	SharedPreferences sharedPreferences = context.getSharedPreferences("secretKey", Context.MODE_PRIVATE);
    	this.setAccessToken(sharedPreferences.getString(TOKEN, null));
    	this.setAccessExpires(sharedPreferences.getLong(EXPIRES, -1));
    	
    	if(!this.isSessionValid()){
    		
    		setSession(context);
    		
    	} 
    	
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    this.authorizeCallback(requestCode, resultCode, data);
	    contextLogin.onActivityResult(requestCode, resultCode, data);
	}
	
	//seta uma sessao para o usuario
    public void setSession(PostActivity context){
  
    	this.authorize(context, permissions, Facebook.FORCE_DIALOG_AUTH, new LoginDialogListener());
   
    }

	class LoginDialogListener implements DialogListener{
		
		public void onComplete(Bundle values) {
			
			saveCredentials(contextLogin);
			try {
				contextLogin.postWall();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		public void onFacebookError(FacebookError e) {
			AlertDialog dialog = contextLogin.createAlert("Facebook", "Ocorreu um erro ao se conectar ao Facebook, tente mais tarde!");
			dialog.show();
			
		}

		public void onError(DialogError e) {
			AlertDialog dialog = contextLogin.createAlert("Facebook", "Ocorreu um erro ao se conectar ao Facebook, tente mais tarde!");
			dialog.show();
			
		}

		public void onCancel() {
			// TODO Auto-generated method stub
			
			
		}

	}

    //salva as credenciais
	public boolean saveCredentials(PostActivity context){
    	Editor editor = context.getSharedPreferences("secretKey", Context.MODE_PRIVATE).edit();
    	editor.putString("access_token", this.getAccessToken());
    	editor.putLong("expires", this.getAccessExpires());

    	return editor.commit();
	}



    //bind no botao de post facebookPost
	public void facebookPost(String result) throws FileNotFoundException, MalformedURLException, IOException{

		Bundle parameters = new Bundle();
        parameters.putString("access_token", this.getAccessToken());
	    parameters.putString("message", result);
	    
		this.request("me/feed", parameters, "POST");
		AlertDialog dialog = contextLogin.createAlert("Facebook", "Texto postado com sucesso!");
		dialog.show();
	}
}