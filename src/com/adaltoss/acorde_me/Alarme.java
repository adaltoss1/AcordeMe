package com.adaltoss.acorde_me;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class Alarme extends Service {
	
	double latitude;
	double longitude;
	static boolean rodando;
	LocationListener locationListener;
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		latitude = (intent.getIntExtra("Latitude", -1)) / 1E6;
		longitude = (intent.getIntExtra("Longitude", -1)) / 1E6;
		
		Log.d("Alarme", "Iniciando servio - latitude: " + latitude + "\nLongitude: " + longitude);
				
			final LocationManager localizacao = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			locationListener = new LocationListener() {
				
	    	    public void onLocationChanged(Location location) {
	    	    	double longitude_atual = location.getLongitude();
	    	    	double latitude_atual = location.getLatitude();
	    	    	double intervalo = 1/111.12;
	    	    	
	    	    	Log.d("Alarme", "Latitude atual: " + latitude_atual + "\nLongitude atual: " + longitude_atual);
	    	    	
	    	    	if(
	    	    			((longitude_atual >= longitude - intervalo) && (longitude_atual <= longitude + intervalo))
	    	    			&&
	    	    			((latitude_atual >= latitude - intervalo) && (latitude_atual <= latitude + intervalo)) 
	    	    			){
	    	    		Intent i = new Intent(Alarme.this, Main_Activity.class);
	    	    		i.putExtra("MostrarDialogo", true);
	    	    		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    	    		startActivity(i);
	    	    		localizacao.removeUpdates(locationListener);
	    	    		stopSelf();
	    	    	}
	    	    }

	    	    public void onStatusChanged(String provider, int status, Bundle extras) {}

	    	    public void onProviderEnabled(String provider) {}

	    	    public void onProviderDisabled(String provider) {}
	    	  };
			
	    	  localizacao.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
		return START_STICKY;
	}
	
	
}
	
	
	
	
	
	
	
	
	
	
	


