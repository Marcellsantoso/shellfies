package com.capsule.shellfies.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.capsule.shellfies.Interfaces.IntContainer;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockFragmentActivity;
import com.iapps.libs.helpers.BaseUIHelper;

public class BaseActivityShellfies extends RoboSherlockFragmentActivity
		implements IntContainer {
	private int containerId;

	/******************************************
	 * FRAGMENT FUNCTIONS
	 ****************************************/
	/**
	 * Add fragment on top of the current one
	 * 
	 * @param frag
	 */
	public void addFragment(Fragment frag) {
		if (this.containerId > 0) {
			getSupportFragmentManager().beginTransaction()
					.add(this.containerId, frag).addToBackStack(null).commit();

			getSupportActionBar().setDisplayHomeAsUpEnabled(false);

			// Hide keyboard by default
			BaseUIHelper.hideKeyboard(this);
		}
	}

	/**
	 * Change to new fragment
	 * 
	 * @param frag
	 */
	public void setFragment(Fragment frag) {
		if (this.containerId > 0) {

			getSupportFragmentManager().beginTransaction()
					.replace(this.containerId, frag).addToBackStack(null)
					.commit();

			getSupportActionBar().setDisplayHomeAsUpEnabled(false);
			setSupportProgressBarIndeterminateVisibility(false);

			// Hide keyboard by default when changing fragment
			BaseUIHelper.hideKeyboard(this);
		}
	}

	/**
	 * Clear all fragments
	 */
	public void clearFragment() {
		getSupportFragmentManager().popBackStack(null,
				FragmentManager.POP_BACK_STACK_INCLUSIVE);
	}

	/**
	 * Remove top of fragments
	 */
	public void popBackstack() {
		getSupportFragmentManager().popBackStack();
	}

	/**
	 * MUST set if want to use fragment methods
	 */
	@Override
	public void setContainerId(int containerId) {
		this.containerId = containerId;
	}
}