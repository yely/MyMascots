package org.MyMascots.namespace;

import java.io.IOException;
import java.util.Calendar;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

public class MainMisMascotas extends Activity {
    /** Called when the activity is first created. */
	private Button bMyMascots, bCitas, bSolicitar, bSalir;
	private SoundPool soundPool;
	private MediaPlayer media ;
	private int idperro ;
	private MagatzemDades db = new MagatzemDadesSql(this, "",null,1);
	private boolean inicio = true;
	Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = this;
        InicializarBotons();
    }
	private void ComprobarCitasHoy(){
		Calendar cal = Calendar.getInstance();
		int dia = cal.get(Calendar.DATE);
		int mes = cal.get(Calendar.MONTH);
		int any = cal.get(Calendar.YEAR);
		int fechalong =  any*10000 +mes*100 + dia;
		
		Vector<EntitatCita> cites = db.llistatCites(0, fechalong);

		if(cites.size() > 0){

		    media.start();
			Intent i = new Intent(context, Mensaje.class );
			i.putExtra("num", cites.size());
			startActivity(i);
		}
	}    
	private void InicializarBotons() { 
		try{
			media = MediaPlayer.create(context, R.raw.perro);
			media.prepare();
		}catch(IOException IOex){
			Log.e("MyMascots", IOex.getMessage(), IOex);
		}catch(IllegalStateException Illex){
			Log.e("MyMascots", Illex.getMessage(), Illex);
		}
		bMyMascots = (Button) findViewById(R.id.btMyMascots);
		bMyMascots.startAnimation(AnimationUtils.loadAnimation(this, R.anim.aparecer));
		
		bCitas = (Button) findViewById(R.id.btCitas);
		bCitas.startAnimation(AnimationUtils.loadAnimation(this, R.anim.aparecer));
		
		bSolicitar = (Button) findViewById(R.id.btSolicitar);
		bSolicitar.startAnimation(AnimationUtils.loadAnimation(this, R.anim.aparecer));
		
		bSalir = (Button) findViewById(R.id.btSalir);
		bSalir.startAnimation(AnimationUtils.loadAnimation(this, R.anim.aparecer));
		
		bMyMascots.setOnClickListener( new  OnClickListener(){
			public void onClick(View view){
				executeActiviy(Mascotas.class);
			} 
		});
		bCitas.setOnClickListener( new  OnClickListener(){
			public void onClick(View view){
				executeActiviy(Citas.class);
			}
		});
		bSolicitar.setOnClickListener( new  OnClickListener(){
			public void onClick(View view){
				executeActiviy(Solicitar.class);
				//call();
			}
		});
		bSalir.setOnClickListener( new  OnClickListener(){
			public void onClick(View view){
				finish();       	
			}
		}); 
	}
	public void executeActiviy(Class clase){

		Intent i = new Intent(this, clase );
		startActivity(i);
	}
	@Override
	protected void onStart() {
	   super.onStart();
	   if (inicio){
		   ComprobarCitasHoy();
		   inicio = false;
	   }
	}
	 
}