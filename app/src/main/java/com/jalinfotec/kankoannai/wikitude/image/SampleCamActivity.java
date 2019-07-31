package com.jalinfotec.kankoannai.wikitude.image;

import com.jalinfotec.kankoannai.Constants;
import com.jalinfotec.kankoannai.R;

public class SampleCamActivity extends AbstractArchitectCamActivity {
	
	/**
	 * path to the World index.html. Relative paths: Relative to assets-root folder, Absolute paths: Web-Url (http://...) or file-path
	 */
	private static final String WORLD_PATH = "index.html";

	@Override
	public String getARchitectWorldPath() {
		return WORLD_PATH;
	}

	@Override
	public String getActivityTitle() {
		return getString(R.string.app_name);
	}

	@Override
	public int getContentViewId() {
		return R.layout.ar_campus;
	}

	@Override
	public int getArchitectViewId() {
		return R.id.archView;
	}
	
	@Override
	public String getWikitudeSDKLicenseKey() {
		return Constants.WIKITUDE_SDK_KEY;
	}

	@Override
	public float getInitialCullingDistanceMeters() {
		// you need to adjust this in case your POIs are more than 50km away from user here while loading or in JS code (compare 'AR.context.scene.cullingDistance')
		return ArchitectViewHolderInterface.CULLING_DISTANCE_DEFAULT_METERS;
	}

}