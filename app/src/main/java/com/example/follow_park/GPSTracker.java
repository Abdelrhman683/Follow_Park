package com.example.follow_park;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.ActivityCompat;

/**
 * Ahmet Ertugrul OZCAN
 * Cihazin konum bilgisini goruntuler
 */
public class GPSTracker extends Service implements LocationListener
{
	private final Context mContext;
	
	// Cihazda gps acik mi?
	boolean isGPSEnabled = false;
	
	// Cihazda veri baglantisi aktif mi?
	boolean isNetworkEnabled = false;
	
	// Konum guncellemesi gerektirecek minimum degisim miktari
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // metre
	
	// Konum guncellemesi gerektirecek minimum sure miktari
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // dakika
	
	// LocationManager nesnesi
	protected LocationManager locationManager;
	
	//
	// Kurucu Metod - Constructor
	//
	public GPSTracker(Context context)
	{
		this.mContext = context;
		getLocation();
	}
	
	//
	// Konum bilgisini dondurur
	//
	public Location getLocation()
	{
		Location location = null;
		
		try
		{
			locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
			
			// GPS acik mi?
			isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			
			// Internet acik mi?
			isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			
			if (!isGPSEnabled && !isNetworkEnabled)
			{
				throw new Exception("Check GPS or internet connection!");
			}
			else
			{
				// Once internetten alinan konum bilgisi kayitlanir
				if (isNetworkEnabled)
				{
					if (ActivityCompat.checkSelfPermission((Activity)mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission((Activity)mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
					{
						throw new Exception("Permission needed for location!");
					}
					
					locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					Log.d("Network", "Network");
					if (locationManager != null)
					{
						location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					}
				}
				// GPS'ten alinan konum bilgisi;
				if (isGPSEnabled)
				{
					if (location == null)
					{
						locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						Log.d("GPS Enabled", "GPS Enabled");
						if (locationManager != null)
						{
							location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return location;
	}
	
	@Override
	public void onLocationChanged(Location location)
	{
	}
	
	@Override
	public void onProviderDisabled(String provider)
	{
	}
	
	@Override
	public void onProviderEnabled(String provider)
	{
	}
	
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{
	}
	
	@Override
	public IBinder onBind(Intent arg0)
	{
		return null;
	}
	
	// Konum bilgisi kapali ise kullaniciya ayarlar sayfasina baglanti iceren bir mesaj goruntulenir
	public void showSettingsAlert()
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
		
		// Mesaj basligi
		alertDialog.setTitle("GPS Kapal??");
		
		// Mesaj
		alertDialog.setMessage("Konum bilgisi al??nam??yor. Ayarlara giderek gps'i aktif hale getiriniz.");
		
		// Mesaj ikonu
		//alertDialog.setIcon(R.drawable.delete);
		
		// Ayarlar butonuna tiklandiginda
		alertDialog.setPositiveButton("Ayarlar", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog,int which)
			{
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				mContext.startActivity(intent);
			}
		});
		
		// Iptal butonuna tiklandiginda
		alertDialog.setNegativeButton("??ptal", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.cancel();
			}
		});
		
		// Mesaj kutusunu goster
		alertDialog.show();
	}
	
	// LocationManager'in gps isteklerini durdurur
	public void stopUsingGPS()
	{
		if (locationManager != null)
		{
			locationManager.removeUpdates(GPSTracker.this);
		}
	}
}