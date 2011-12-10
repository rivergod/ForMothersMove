package com.krcode.mothers;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import android.app.AlertDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.krcode.mothers.helpers.AptHelper;
import com.krcode.mothers.helpers.BusHelper;
import com.krcode.mothers.helpers.IdessAfUserHelper;
import com.krcode.mothers.helpers.SchoolHelper;
import com.krcode.mothers.vo.AccountVO;
import com.krcode.mothers.vo.AptsVO;
import com.krcode.mothers.vo.BusStationVO;
import com.krcode.mothers.vo.IPointVO;
import com.krcode.mothers.vo.IdessAfUserVO;
import com.krcode.mothers.vo.SchoolVO;

import de.android1.overlaymanager.ManagedOverlay;
import de.android1.overlaymanager.ManagedOverlayGestureDetector.OnOverlayGestureListener;
import de.android1.overlaymanager.ManagedOverlayItem;
import de.android1.overlaymanager.OverlayManager;
import de.android1.overlaymanager.ZoomEvent;
import de.android1.overlaymanager.lazyload.LazyLoadCallback;
import de.android1.overlaymanager.lazyload.LazyLoadException;
import de.android1.overlaymanager.lazyload.LazyLoadListener;

public class MainActivity extends MapActivity {
	public final static int RECOMMENDLOCATIONACTIVITY = 100;
	public final static int ATTENTIONLISTACTIVITY = 101;
	public final static int GOTOLOCATIONACTIVITY = 102;

	private MapView mapView;
	// private MapController mapController;

	private OverlayManager overlayManager;

	public static AccountVO accountVO = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Intent intent = getIntent();
		//
		// if (intent != null && intent.getExtras().containsKey("ACCOUNTVO")
		// && intent.getExtras().get("ACCOUNTVO") instanceof AccountVO) {
		// accountVO = (AccountVO) intent.getExtras().get("ACCOUNTVO");
		// }

		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		// mapController = mapView.getController();

		overlayManager = new OverlayManager(getApplication(), mapView);
		createOverlays();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		for (int i = 0; i < mapView.getOverlays().size(); i++) {
			if (overlayManager.getOverlay(i) instanceof ManagedOverlay) {
				((ManagedOverlay) overlayManager.getOverlay(i))
						.invokeLazyLoad(100);
			}
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		if (menu.size() >= 4) {
			if (MainActivity.accountVO != null
					&& MainActivity.accountVO.isAvalidable()) {
				if (menu.getItem(3).getItemId() == R.id.quick_menu_loginout) {
					menu.getItem(3).setTitle("로그아웃");
				}
			} else {
				if (menu.getItem(3).getItemId() == R.id.quick_menu_loginout) {
					menu.getItem(3).setTitle("로그인");
				}
			}
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.quick_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
		case R.id.quick_menu_goto:
			intent = new Intent(MainActivity.this,
					GotoLocationActivity.class);

			startActivityForResult(intent, MainActivity.GOTOLOCATIONACTIVITY);
			return true;
		case R.id.quick_menu_recommend:
			if (MainActivity.accountVO != null
					&& MainActivity.accountVO.isAvalidable() && mapView != null) {
				intent = new Intent(MainActivity.this,
						RecommendLocationActivity.class);

				startActivityForResult(intent,
						MainActivity.RECOMMENDLOCATIONACTIVITY);
			} else {
				Toast.makeText(getApplicationContext(), "로그인 후 사용하실 수 있습니다.",
						Toast.LENGTH_SHORT).show();
			}
			return true;
		case R.id.quick_menu_attention:
			if (MainActivity.accountVO != null
					&& MainActivity.accountVO.isAvalidable()) {
				intent = new Intent(MainActivity.this,
						AttentionListActivity.class);

				GeoPoint point = mapView.getMapCenter();

				intent.putExtra("LATITUDE",
						String.valueOf(point.getLatitudeE6()));
				intent.putExtra("LONGITUDE",
						String.valueOf(point.getLongitudeE6()));

				startActivityForResult(intent,
						MainActivity.ATTENTIONLISTACTIVITY);
			} else {
				Toast.makeText(getApplicationContext(), "로그인 후 사용하실 수 있습니다.",
						Toast.LENGTH_SHORT).show();
			}
			return true;
		case R.id.quick_menu_loginout:
			if (MainActivity.accountVO != null
					&& MainActivity.accountVO.isAvalidable()) {
				MainActivity.accountVO = null;
			} else {
				startActivity(new Intent(MainActivity.this, LoginActivity.class));
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		switch (requestCode) {
		case MainActivity.GOTOLOCATIONACTIVITY:
			if (resultCode == RESULT_OK) {
				String address = data.getExtras().getString("ADDRESS");

				Locale.setDefault(Locale.KOREA); // = ko_KO, 디폴트로 되어 있으면 말고
				Geocoder geocoder = new Geocoder(getApplicationContext(),
						Locale.getDefault());

				List<Address> addresses = null;

				try {
					addresses = geocoder.getFromLocationName(address, 1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (addresses != null) {
					animateTo(new GeoPoint((int) (addresses.get(0)
							.getLatitude() * 1E6), (int) (addresses.get(0)
							.getLongitude() * 1E6)));
					mapView.getController().setZoom(18);
				}

			}
			break;
		case MainActivity.RECOMMENDLOCATIONACTIVITY:
			if (resultCode == RESULT_OK) {
				String address = data.getExtras().getString("ADDRESS");

				Locale.setDefault(Locale.KOREA); // = ko_KO, 디폴트로 되어 있으면 말고
				Geocoder geocoder = new Geocoder(getApplicationContext(),
						Locale.getDefault());

				List<Address> addresses = null;

				try {
					addresses = geocoder.getFromLocationName(address, 1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (addresses != null) {
					animateTo(new GeoPoint((int) (addresses.get(0)
							.getLatitude() * 1E6), (int) (addresses.get(0)
							.getLongitude() * 1E6)));
					mapView.getController().setZoom(18);
				}

			}
			break;

		case MainActivity.ATTENTIONLISTACTIVITY:
			if (resultCode == RESULT_OK) {
				// String address = data.getExtras().getString("ADDRESS");

				int lat = Integer.parseInt(data.getExtras().getString(
						"LATITUDE"));
				int lng = Integer.parseInt(data.getExtras().getString(
						"LONGITUDE"));

				animateTo(new GeoPoint(lat, lng));
				mapView.getController().setZoom(18);
			}
			break;
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	private void animateTo(GeoPoint point) {
		if (this.mapView != null) {
			mapView.getController().animateTo(point);

			for (int i = 0; i < mapView.getOverlays().size(); i++) {
				if (overlayManager.getOverlay(i) instanceof ManagedOverlay) {
					((ManagedOverlay) overlayManager.getOverlay(i))
							.invokeLazyLoad(100);
				}
			}
		}
	}

	private void createOverlays() {
		createAptsManagedOverlay();
		createIdeffAfUserManagedOverlay();
		createBusStationManagedOverlay();
		createSchoolManagedOverlay();

		overlayManager.populate();
	}

	private void createSchoolManagedOverlay() {
		// TODO Auto-generated method stub
		ManagedOverlay schoolManagedOverlay = overlayManager.createOverlay(
				"SchoolManagedOverlay",
				getResources().getDrawable(R.drawable.map_marker_04));

		// schoolManagedOverlay.enableLazyLoadAnimation(loaderanim);

		schoolManagedOverlay.setLazyLoadCallback(new LazyLoadCallback() {

			@Override
			public List<? extends ManagedOverlayItem> lazyload(
					GeoPoint topLeft, GeoPoint bottomRight,
					ManagedOverlay overlay) throws LazyLoadException {
				List<PointManagedOverlayItem> items = new LinkedList<PointManagedOverlayItem>();

				List<? extends IPointVO> points = SchoolHelper.findMarker(
						topLeft, bottomRight, overlay.getZoomlevel());

				Iterator<? extends IPointVO> iter = points.iterator();

				int i = 0;

				while (iter.hasNext()) {
					IPointVO vo = iter.next();

					PointManagedOverlayItem item = new PointManagedOverlayItem(
							new GeoPoint(vo.getLatitude1E6(), vo
									.getLongitude1E6()), "Item" + i, "", vo);

					items.add(item);

					i++;
				}

				Log.d("MOTHERS", items.size() + " school items append!");

				return items;
			}
		});

		// A LazyLoadListener is optional!
		schoolManagedOverlay.setLazyLoadListener(new LazyLoadListener() {
			// long debug_lazyload_runtime;

			@Override
			public void onBegin(ManagedOverlay overlay) {
				// debug_lazyload_runtime = System.currentTimeMillis();
			}

			@Override
			public void onSuccess(ManagedOverlay overlay) {
				// Toast.makeText(
				// getApplicationContext(),
				// "school Runtime: "
				// + (System.currentTimeMillis() - debug_lazyload_runtime)
				// + " ms", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onError(LazyLoadException exception,
					ManagedOverlay overlay) {

			}
		});

		schoolManagedOverlay
				.setOnOverlayGestureListener(new OnOverlayGestureListener() {

					@Override
					public boolean onZoom(ZoomEvent arg0, ManagedOverlay arg1) {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public boolean onSingleTap(MotionEvent arg0,
							ManagedOverlay arg1, GeoPoint arg2,
							ManagedOverlayItem item) {
						if (item != null
								&& item instanceof PointManagedOverlayItem) {

							PointManagedOverlayItem pItem = (PointManagedOverlayItem) item;

							SchoolVO vo = (SchoolVO) pItem.getVo();

							AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
									MainActivity.this);
							dialogBuilder.setTitle(vo.getName());
							dialogBuilder.setMessage("홈페이지: "
									+ vo.getHomepage() + "\n" + "전화번호: "
									+ vo.getTelephone() + "\n" + "주소: "
									+ vo.getAddress() + "\n");

							dialogBuilder.show();

						}
						return false;
					}

					@Override
					public boolean onScrolled(MotionEvent arg0,
							MotionEvent arg1, float arg2, float arg3,
							ManagedOverlay arg4) {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public void onLongPressFinished(MotionEvent arg0,
							ManagedOverlay arg1, GeoPoint arg2,
							ManagedOverlayItem arg3) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onLongPress(MotionEvent arg0,
							ManagedOverlay arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public boolean onDoubleTap(MotionEvent arg0,
							ManagedOverlay arg1, GeoPoint arg2,
							ManagedOverlayItem arg3) {
						// TODO Auto-generated method stub
						return false;
					}
				});

	}

	private void createBusStationManagedOverlay() {
		// TODO Auto-generated method stub
		ManagedOverlay busStationManagedOverlay = overlayManager.createOverlay(
				"BusStationOverlay",
				getResources().getDrawable(R.drawable.map_marker_03));

		// busStationManagedOverlay.enableLazyLoadAnimation(loaderanim);

		busStationManagedOverlay.setLazyLoadCallback(new LazyLoadCallback() {

			@Override
			public List<? extends ManagedOverlayItem> lazyload(
					GeoPoint topLeft, GeoPoint bottomRight,
					ManagedOverlay overlay) throws LazyLoadException {
				List<PointManagedOverlayItem> items = new LinkedList<PointManagedOverlayItem>();

				List<? extends IPointVO> points = BusHelper.findMarker(topLeft,
						bottomRight, overlay.getZoomlevel());

				Iterator<? extends IPointVO> iter = points.iterator();

				int i = 0;

				while (iter.hasNext()) {
					IPointVO vo = iter.next();

					PointManagedOverlayItem item = new PointManagedOverlayItem(
							new GeoPoint(vo.getLatitude1E6(), vo
									.getLongitude1E6()), "Item" + i, "", vo);

					items.add(item);

					i++;
				}

				Log.d("MOTHERS", items.size() + " bus station items append!");

				return items;
			}
		});

		// A LazyLoadListener is optional!
		busStationManagedOverlay.setLazyLoadListener(new LazyLoadListener() {
			// long debug_lazyload_runtime;

			@Override
			public void onBegin(ManagedOverlay overlay) {
				// debug_lazyload_runtime = System.currentTimeMillis();
			}

			@Override
			public void onSuccess(ManagedOverlay overlay) {
				// Toast.makeText(
				// getApplicationContext(),
				// "Bus Runtime: "
				// + (System.currentTimeMillis() - debug_lazyload_runtime)
				// + " ms", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onError(LazyLoadException exception,
					ManagedOverlay overlay) {

			}
		});

		busStationManagedOverlay
				.setOnOverlayGestureListener(new OnOverlayGestureListener() {

					@Override
					public boolean onZoom(ZoomEvent arg0, ManagedOverlay arg1) {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public boolean onSingleTap(MotionEvent arg0,
							ManagedOverlay arg1, GeoPoint arg2,
							ManagedOverlayItem item) {
						if (item != null
								&& item instanceof PointManagedOverlayItem) {

							PointManagedOverlayItem pItem = (PointManagedOverlayItem) item;

							BusStationVO vo = (BusStationVO) pItem.getVo();

							AlertDialog.Builder idessDialogBuilder = new AlertDialog.Builder(
									MainActivity.this);
							idessDialogBuilder.setTitle(vo.getStationNameKor());
							idessDialogBuilder.setMessage(vo.getStationId());

							idessDialogBuilder.show();
						}
						return false;
					}

					@Override
					public boolean onScrolled(MotionEvent arg0,
							MotionEvent arg1, float arg2, float arg3,
							ManagedOverlay arg4) {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public void onLongPressFinished(MotionEvent arg0,
							ManagedOverlay arg1, GeoPoint arg2,
							ManagedOverlayItem arg3) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onLongPress(MotionEvent arg0,
							ManagedOverlay arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public boolean onDoubleTap(MotionEvent arg0,
							ManagedOverlay arg1, GeoPoint arg2,
							ManagedOverlayItem arg3) {
						// TODO Auto-generated method stub
						return false;
					}
				});

		// overlayManager.populate();

	}

	private void createIdeffAfUserManagedOverlay() {
		// TODO Auto-generated method stub
		ManagedOverlay idessAfUserManagedOverlay = overlayManager
				.createOverlay("IdessAfUserOverlay", getResources()
						.getDrawable(R.drawable.map_marker_02));

		// idessAfUserManagedOverlay.enableLazyLoadAnimation(loaderanim);

		idessAfUserManagedOverlay.setLazyLoadCallback(new LazyLoadCallback() {

			@Override
			public List<? extends ManagedOverlayItem> lazyload(
					GeoPoint topLeft, GeoPoint bottomRight,
					ManagedOverlay overlay) throws LazyLoadException {
				List<PointManagedOverlayItem> items = new LinkedList<PointManagedOverlayItem>();

				List<? extends IPointVO> points = IdessAfUserHelper.findMarker(
						topLeft, bottomRight, overlay.getZoomlevel());

				Iterator<? extends IPointVO> iter = points.iterator();

				int i = 0;

				while (iter.hasNext()) {
					IPointVO vo = iter.next();

					PointManagedOverlayItem item = new PointManagedOverlayItem(
							new GeoPoint(vo.getLatitude1E6(), vo
									.getLongitude1E6()), "Item" + i, "", vo);

					items.add(item);

					i++;
				}

				Log.d("MOTHERS", items.size() + " idess items append!");

				return items;
			}
		});

		// A LazyLoadListener is optional!
		idessAfUserManagedOverlay.setLazyLoadListener(new LazyLoadListener() {
			// long debug_lazyload_runtime;

			@Override
			public void onBegin(ManagedOverlay overlay) {
				// debug_lazyload_runtime = System.currentTimeMillis();
			}

			@Override
			public void onSuccess(ManagedOverlay overlay) {
				// Toast.makeText(
				// getApplicationContext(),
				// "LazyLoadRuntime: "
				// + (System.currentTimeMillis() - debug_lazyload_runtime)
				// + " ms", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onError(LazyLoadException exception,
					ManagedOverlay overlay) {

			}
		});

		idessAfUserManagedOverlay
				.setOnOverlayGestureListener(new OnOverlayGestureListener() {

					@Override
					public boolean onZoom(ZoomEvent arg0, ManagedOverlay arg1) {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public boolean onSingleTap(MotionEvent arg0,
							ManagedOverlay arg1, GeoPoint arg2,
							ManagedOverlayItem item) {
						if (item != null
								&& item instanceof PointManagedOverlayItem) {

							PointManagedOverlayItem pItem = (PointManagedOverlayItem) item;

							IdessAfUserVO vo = (IdessAfUserVO) pItem.getVo();

							AlertDialog.Builder idessDialogBuilder = new AlertDialog.Builder(
									MainActivity.this);
							idessDialogBuilder.setTitle(vo.getNm());
							idessDialogBuilder.setMessage(vo.getAd());

							idessDialogBuilder.show();
						}
						return false;
					}

					@Override
					public boolean onScrolled(MotionEvent arg0,
							MotionEvent arg1, float arg2, float arg3,
							ManagedOverlay arg4) {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public void onLongPressFinished(MotionEvent arg0,
							ManagedOverlay arg1, GeoPoint arg2,
							ManagedOverlayItem arg3) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onLongPress(MotionEvent arg0,
							ManagedOverlay arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public boolean onDoubleTap(MotionEvent arg0,
							ManagedOverlay arg1, GeoPoint arg2,
							ManagedOverlayItem arg3) {
						// TODO Auto-generated method stub
						return false;
					}
				});

		// overlayManager.populate();

	}

	private void createAptsManagedOverlay() {
		// TODO Auto-generated method stub
		// animation will be rendered to this ImageView
		ImageView loaderanim = (ImageView) findViewById(R.id.loader);

		ManagedOverlay aptsManagedOverlay = overlayManager.createOverlay(
				"AptsOverlay",
				getResources().getDrawable(R.drawable.map_marker_01));

		// default built-in animation
		aptsManagedOverlay.enableLazyLoadAnimation(loaderanim);

		aptsManagedOverlay.setLazyLoadCallback(new LazyLoadCallback() {
			@Override
			public List<? extends ManagedOverlayItem> lazyload(
					GeoPoint topLeft, GeoPoint bottomRight,
					ManagedOverlay overlay) throws LazyLoadException {
				List<PointManagedOverlayItem> items = new LinkedList<PointManagedOverlayItem>();

				try {
					List<? extends IPointVO> points = AptHelper.findMarker(
							topLeft, bottomRight, overlay.getZoomlevel());

					Iterator<? extends IPointVO> iter = points.iterator();

					int i = 0;

					while (iter.hasNext()) {
						IPointVO vo = iter.next();

						PointManagedOverlayItem item = new PointManagedOverlayItem(
								new GeoPoint(vo.getLatitude1E6(), vo
										.getLongitude1E6()), "Item" + i, "", vo);

						items.add(item);

						i++;
					}
					// lets simulate a latency
					TimeUnit.SECONDS.sleep(1);
				} catch (Exception e) {
					throw new LazyLoadException(e.getMessage());
				}

				Log.d("MOTHERS", items.size() + " apt station items append!");

				return items;
			}
		});

		// A LazyLoadListener is optional!
		aptsManagedOverlay.setLazyLoadListener(new LazyLoadListener() {
			// long debug_lazyload_runtime;

			@Override
			public void onBegin(ManagedOverlay overlay) {
				// debug_lazyload_runtime = System.currentTimeMillis();
			}

			@Override
			public void onSuccess(ManagedOverlay overlay) {
				// Toast.makeText(
				// getApplicationContext(),
				// "LazyLoadRuntime: "
				// + (System.currentTimeMillis() - debug_lazyload_runtime)
				// + " ms", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onError(LazyLoadException exception,
					ManagedOverlay overlay) {

			}
		});

		aptsManagedOverlay
				.setOnOverlayGestureListener(new OnOverlayGestureListener() {

					@Override
					public boolean onZoom(ZoomEvent arg0, ManagedOverlay arg1) {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public boolean onSingleTap(MotionEvent arg0,
							ManagedOverlay arg1, GeoPoint arg2,
							ManagedOverlayItem item) {
						if (item != null
								&& item instanceof PointManagedOverlayItem) {

							PointManagedOverlayItem pItem = (PointManagedOverlayItem) item;

							AptsVO vo = (AptsVO) pItem.getVo();

//							AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
//									MainActivity.this);
//							dialogBuilder.setTitle(vo.getAptName());
//							dialogBuilder.setMessage("주소: " + vo.getAddress()
//									+ "\n");
//
//							dialogBuilder.show();
							
							Intent intent = new Intent(MainActivity.this, AptsActivity.class);
							intent.putExtra("APTSVO", vo);
							startActivity(intent);
						}
						return false;
					}

					@Override
					public boolean onScrolled(MotionEvent arg0,
							MotionEvent arg1, float arg2, float arg3,
							ManagedOverlay arg4) {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public void onLongPressFinished(MotionEvent arg0,
							ManagedOverlay arg1, GeoPoint arg2,
							ManagedOverlayItem arg3) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onLongPress(MotionEvent arg0,
							ManagedOverlay arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public boolean onDoubleTap(MotionEvent arg0,
							ManagedOverlay arg1, GeoPoint arg2,
							ManagedOverlayItem arg3) {
						// TODO Auto-generated method stub
						return false;
					}
				});

		// overlayManager.populate();

	}

}
