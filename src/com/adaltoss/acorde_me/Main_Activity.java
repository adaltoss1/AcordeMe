package com.adaltoss.acorde_me;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;

import com.cyrilmottier.polaris.Annotation;
import com.cyrilmottier.polaris.MapCalloutView;
import com.cyrilmottier.polaris.PolarisMapView;
import com.cyrilmottier.polaris.PolarisMapView.OnAnnotationSelectionChangedListener;
import com.cyrilmottier.polaris.PolarisMapView.OnMapViewLongClickListener;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;

public class Main_Activity extends MapActivity {

	PolarisMapView mPolarisMapView; 
	boolean alarmeRodando = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);
        
        if(getIntent().getBooleanExtra("MostrarDialogo", false)){
        	stopService(new Intent(this, Alarme.class));
        	
        	Uri notificacao = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        	final Ringtone r = RingtoneManager.getRingtone(this, notificacao);
        	r.play();
        	AlertDialog.Builder pararAlarme = new AlertDialog.Builder(this);
        	pararAlarme.setTitle("Parar Alarme?");
        	pararAlarme.setMessage("Você chegou ao seu destino!");
        	pararAlarme.setPositiveButton("Parar Alarme", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					r.stop();
					alarmeRodando = false;
					dialog.dismiss();
					finish();
				}
			});
        	pararAlarme.show();
        }
        
        mPolarisMapView = (PolarisMapView) findViewById(R.id.polaris_map_view);
        mPolarisMapView.setUserTrackingButtonEnabled(true);
        mPolarisMapView.setOnMapViewLongClickListener(new OnMapViewLongClickListener() {
			
			@Override
			public void onLongClick(PolarisMapView mapView, final GeoPoint geoPoint) {
				// TODO Auto-generated method stub
				List<Annotation> anotations = new ArrayList<Annotation>();				
				anotations.add(new Annotation(geoPoint, "Acorde-Me Aqui"));
			
				mPolarisMapView.setAnnotations(anotations, R.drawable.marker);
				
				mPolarisMapView.setOnAnnotationSelectionChangedListener(new OnAnnotationSelectionChangedListener() {
					
					@Override
					public void onAnnotationSelected(PolarisMapView mapView,
							MapCalloutView calloutView, int position, Annotation annotation) {
						
					}
					
					@Override
					public void onAnnotationDeselected(PolarisMapView mapView,
							MapCalloutView calloutView, int position, Annotation annotation) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnnotationClicked(PolarisMapView mapView,
							MapCalloutView calloutView, int position, Annotation annotation) {
						
						if (!alarmeRodando) {
						
							// Chamar o serviço aqui!
							Intent i = new Intent(Main_Activity.this, Alarme.class);
							i.putExtra("Latitude", geoPoint.getLatitudeE6());
							i.putExtra("Longitude", geoPoint.getLongitudeE6());
							startService(i);
							
							alarmeRodando = true;
						}
					}
				});
			}
		});
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_, menu);
        return true;
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	@Override
	protected void onStart() {
	    super.onStart();
	    mPolarisMapView.onStart();
	}

	@Override
	protected void onStop() {
	    super.onStop();
	    mPolarisMapView.onStop();
	} 
	
	
	
}
