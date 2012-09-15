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

	public static String accessToken = "access_token";
    public static String secretKey = "facebook-credentials";
    public static String expires = "expires_in";

	public String[] PERMISSIONS = new String[] {"publish_stream"};


    //construtor da classe
	public FB(){
		super(appId);
	}

	//pega a sessao, se nao esta logado, cria
	public void getSession(PostActivity context){
    	SharedPreferences sharedPreferences = context.getSharedPreferences(secretKey, Context.MODE_PRIVATE);
    	this.setAccessToken(sharedPreferences.getString(TOKEN, null));
    	this.setAccessExpires(sharedPreferences.getLong(EXPIRES, 0));

    	if(!this.isSessionValid()){
    		//se nao estiver, seta sessao
    		setSession(context);
    	}

	}

    //bind no botao de post facebookPost
	public void facebookPost(String result) throws FileNotFoundException, MalformedURLException, IOException{

		Bundle parameters = new Bundle();
        parameters.putString("access_token", this.getAccessToken());
	    parameters.putString("message", result);

		this.request("me/feed", parameters, "POST");
	}


	//seta uma sessao para o usuario
    public void setSession(PostActivity context){
    	this.authorize(context, PERMISSIONS, Facebook.FORCE_DIALOG_AUTH, new LoginDialogListener());
    	saveCredentials(context);
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
    	Editor editor = context.getSharedPreferences(secretKey, Context.MODE_PRIVATE).edit();
    	editor.putString(accessToken, this.getAccessToken());
    	editor.putLong(expires, this.getAccessExpires());

    	return editor.commit();
	}
}

















/*
package com.codget.postvox;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Logger;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class FB{

	public String appId = "285831614858736";
	public Facebook object = new Facebook(this.appId);

	private static final String[] PERMISSIONS = new String[] {"publish_stream"};

	private static final String TOKEN = "access_token";
    private static final String EXPIRES = "expires_in";
    private static final String KEY = "facebook-credentials";



	//function que seta o objeto, setando tambem o user
    public void setObject(PostActivity context){
    	SharedPreferences sharedPreferences = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);

    	this.object.setAccessToken(sharedPreferences.getString(TOKEN, null));
    	this.object.setAccessExpires(sharedPreferences.getLong(EXPIRES, 0));

    	if(!this.object.isSessionValid()){
    		setSession(context);
    	}
    }

    public void setSession(PostActivity context){
    	this.object.authorize(context, PERMISSIONS, Facebook.FORCE_DIALOG_AUTH, new LoginDialogListener());	
    	saveCredentials(context);
    }

	class LoginDialogListener implements DialogListener{

		@Override
		public void onComplete(Bundle values) {}

		@Override
		public void onFacebookError(FacebookError e) {}

		@Override
		public void onError(DialogError e) {}

		@Override
		public void onCancel() {}
	}
	
	public boolean saveCredentials(PostActivity context) {
    	Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();

    	editor.putString(TOKEN, this.object.getAccessToken());
    	editor.putLong(EXPIRES, this.object.getAccessExpires());

    	return editor.commit();
	}


    
	public void facebookPostClick(String result) throws FileNotFoundException, MalformedURLException, IOException{
	    Bundle parameters = new Bundle();
        parameters.putString("access_token", this.object.getAccessToken());
	    parameters.putString("message", result);
	    parameters.putString("message", result);

		String doido = this.object.request("me/feed", parameters, "POST");

		Logger log = Logger.getLogger("com.codget.postvox");
		log.warning(doido);
	}
}

*/