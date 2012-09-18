package com.codget.postvox;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;



public class FB extends Facebook{

	public static String appId = "285831614858736";
	public String[] permissions = new String[] {"publish_stream", "read_stream"};


    //construtor da classe
	public FB(){
		super(appId);
	}



	//pega a sessao, se nao esta logado, cria
	public boolean getSession(PostActivity context){
    	SharedPreferences sharedPreferences = context.getSharedPreferences("credentials", Context.MODE_PRIVATE);
    	this.setAccessToken(sharedPreferences.getString(TOKEN, null));
    	this.setAccessExpires(sharedPreferences.getLong(EXPIRES, 0));

    	if(!this.isSessionValid()){
    		//se nao estiver, seta sessao
    		return setSession(context);

    	}

    	return true;
	}


	//seta uma sessao para o usuario
    public boolean setSession(PostActivity context){
    	this.authorize(context, permissions, Facebook.FORCE_DIALOG_AUTH, new LoginDialogListener());
    	
    	if(saveCredentials(context)){
        	return true;    		
    	}

    	return false;
    }

	class LoginDialogListener implements DialogListener{

		public void onComplete(Bundle values) {
			// TODO Auto-generated method stub
			
		}

		public void onFacebookError(FacebookError e) {
			// TODO Auto-generated method stub
			
		}

		public void onError(DialogError e) {
			// TODO Auto-generated method stub
			
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
	public boolean facebookPost(String result) throws FileNotFoundException, MalformedURLException, IOException{

		Bundle parameters = new Bundle();
        parameters.putString("access_token", this.getAccessToken());
	    parameters.putString("message", result);

		this.request("me/feed", parameters, "POST");

		return true;
	}
}