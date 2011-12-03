package com.krcode.mothers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.krcode.mothers.helpers.AptHelper;
import com.krcode.mothers.helpers.BusHelper;
import com.krcode.mothers.helpers.IdessAfUserHelper;
import com.krcode.mothers.helpers.SchoolHelper;
import com.krcode.mothers.vo.BusStationVO;
import com.krcode.mothers.vo.IPointVO;
import com.krcode.mothers.vo.IdessAfUserVO;

import de.android1.overlaymanager.ManagedOverlay;
import de.android1.overlaymanager.ManagedOverlayGestureDetector.OnOverlayGestureListener;
import de.android1.overlaymanager.ManagedOverlayItem;
import de.android1.overlaymanager.OverlayManager;
import de.android1.overlaymanager.ZoomEvent;
import de.android1.overlaymanager.lazyload.LazyLoadCallback;
import de.android1.overlaymanager.lazyload.LazyLoadException;
import de.android1.overlaymanager.lazyload.LazyLoadListener;

public class MainActivity extends MapActivity {
	private MapView mapView;
	// private MapController mapController;

	OverlayManager overlayManager;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		// mapController = mapView.getController();

		overlayManager = new OverlayManager(getApplication(), mapView);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		createOverlays();

		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.quick_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.quick_menu_goto:
			startActivity(new Intent(MainActivity.this,
					GotoLocationActivity.class));
			return true;
		case R.id.quick_menu_recommend:
			startActivity(new Intent(MainActivity.this,
					RecommendLocationActivity.class));
			return true;
		case R.id.quick_menu_attention:
			startActivity(new Intent(MainActivity.this,
					AttentionListActivity.class));
			return true;
		case R.id.quick_menu_loginout:
			startActivity(new Intent(MainActivity.this, LoginActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void createOverlays() {
		// animation will be rendered to this ImageView
		ImageView loaderanim = (ImageView) findViewById(R.id.loader);

		ManagedOverlay aptsManagedOverlay = overlayManager.createOverlay(
				"AptsOverlay",
				getResources().getDrawable(R.drawable.map_marker_01));

		// default built-in animation
		aptsManagedOverlay.enableLazyLoadAnimation(loaderanim);

		aptsManagedOverlay.setLazyLoadCallback(new LazyLoadCallback() {
			@Override
			public List<ManagedOverlayItem> lazyload(GeoPoint topLeft,
					GeoPoint bottomRight, ManagedOverlay overlay)
					throws LazyLoadException {
				List<ManagedOverlayItem> items = new LinkedList<ManagedOverlayItem>();
				try {
					List<GeoPoint> marker = AptHelper.findMarker(topLeft,
							bottomRight, overlay.getZoomlevel());
					for (int i = 0; i < marker.size(); i++) {
						GeoPoint point = marker.get(i);
						ManagedOverlayItem item = new ManagedOverlayItem(point,
								"Item" + i, "");
						items.add(item);
					}
					// lets simulate a latency
					TimeUnit.SECONDS.sleep(1);
				} catch (Exception e) {
					throw new LazyLoadException(e.getMessage());
				}

				Log.d("MOTHERS", items.size() + " apt items append!");

				return items;
			}
		});

		// A LazyLoadListener is optional!
		aptsManagedOverlay.setLazyLoadListener(new LazyLoadListener() {
			long debug_lazyload_runtime;

			@Override
			public void onBegin(ManagedOverlay overlay) {
				debug_lazyload_runtime = System.currentTimeMillis();
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
		overlayManager.populate();

		ManagedOverlay idessAfUserManagedOverlay = overlayManager
				.createOverlay("IdessAfUserOverlay", getResources()
						.getDrawable(R.drawable.map_marker_02));

		idessAfUserManagedOverlay.enableLazyLoadAnimation(loaderanim);

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
			long debug_lazyload_runtime;

			@Override
			public void onBegin(ManagedOverlay overlay) {
				debug_lazyload_runtime = System.currentTimeMillis();
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

		overlayManager.populate();

		ManagedOverlay busStationManagedOverlay = overlayManager.createOverlay(
				"BusStationOverlay",
				getResources().getDrawable(R.drawable.map_marker_03));

		busStationManagedOverlay.enableLazyLoadAnimation(loaderanim);

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
			long debug_lazyload_runtime;

			@Override
			public void onBegin(ManagedOverlay overlay) {
				debug_lazyload_runtime = System.currentTimeMillis();
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

		overlayManager.populate();

		ManagedOverlay schoolManagedOverlay = overlayManager.createOverlay(
				"SchoolManagedOverlay",
				getResources().getDrawable(R.drawable.map_marker_04));

		schoolManagedOverlay.enableLazyLoadAnimation(loaderanim);

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
			long debug_lazyload_runtime;

			@Override
			public void onBegin(ManagedOverlay overlay) {
				debug_lazyload_runtime = System.currentTimeMillis();
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

		overlayManager.populate();

	}

	private class PointManagedOverlayItem extends ManagedOverlayItem {

		private IPointVO vo = null;

		public PointManagedOverlayItem(GeoPoint point, String title,
				String snippet, IPointVO vo) {
			super(point, title, snippet);
			this.vo = vo;
		}

		public IPointVO getVo() {
			return vo;
		}
	}

}
