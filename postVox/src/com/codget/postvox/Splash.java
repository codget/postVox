package com.codget.postvox;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends Activity implements Runnable {

	final Splash splashActivity = this;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		if(!isNetworkAvailable()){
        	AlertDialog dialog = createAlertFinish("Conexão", "Para o uso da aplicação, é necessário estar conectado à internet.");
			dialog.show();
        } else {
        	Handler handler = new Handler();
    		handler.postDelayed(this, 4000);
        }
		
	}

	public void run(){
		startActivity(new Intent(this, PostActivity.class));
		finish();
	}
	
	public AlertDialog createAlertFinish(String title, String message){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);

    	// 2. Chain together various setter methods to set the dialog characteristics
    	builder.setMessage(message)
    	       .setTitle(title);
    	builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	splashActivity.finish();
            }
        });
    	// 3. Get the AlertDialog from create()
    	AlertDialog dialog = builder.create();
    	return dialog;
    }
	
	//verifica se existe conexï¿½o com a internet
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if(cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()) {
        	return true;
        }else{
        	return false;
        }
    }
}