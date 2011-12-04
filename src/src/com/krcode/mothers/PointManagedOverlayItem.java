package com.krcode.mothers;

import com.google.android.maps.GeoPoint;
import com.krcode.mothers.vo.IPointVO;

import de.android1.overlaymanager.ManagedOverlayItem;

public class PointManagedOverlayItem extends ManagedOverlayItem {
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
