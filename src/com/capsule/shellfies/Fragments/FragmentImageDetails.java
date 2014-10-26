package com.capsule.shellfies.Fragments;

import java.util.ArrayList;

import roboguice.inject.InjectView;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.capsule.shellfies.R;
import com.capsule.shellfies.Helpers.TextViewComment;
import com.capsule.shellfies.Helpers.UIHelper;
import com.capsule.shellfies.Objects.BeanComment;
import com.iapps.libs.views.ImageViewLoader;

public class FragmentImageDetails extends BaseFragmentShellfies {
	@InjectView(R.id.imagePopup)
	private ImageViewLoader			img;
	@InjectView(R.id.tvPopupName)
	private TextViewComment			tvName;
	@InjectView(R.id.llComments)
	private LinearLayout			llComments;

	private ArrayList<BeanComment>	alComments	= new ArrayList<BeanComment>();
	private String					url;

	public FragmentImageDetails(String url) {
		this.url = url;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.popup_image_content, container, false);
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		loadName();
		loadImage();
		loadComments();
	}

	public void loadName() {
		BeanComment name = new BeanComment(0, "Marcellinus Santoso", "Ini gambar untuk display");
		tvName.setComment(name);
		tvName.setListenerSpan(new ListenerNameClick(name));
	}

	public void loadImage() {
		int screenWidth = UIHelper.getScreenWidth(getActivity());
		int screenMargin = (int) UIHelper
				.convertDpToPixel(
						(int) getActivity().getResources().getDimension(
								R.dimen.activity_horizontal_margin),
						getActivity()); // Count with margin as well, because the dialog width
		// already having horizontal margin
		img.getLayoutParams().height = screenWidth - screenMargin;

		// Load image from url
		img.loadImage(url);
	}

	public void loadComments() {
		// Dummy comments
		// TODO : Think how to load all comments

		// Clear all the previous loaded comments
		alComments.clear();
		alComments.add(new BeanComment(0, "Elroy", "Hi Marcel!"));
		alComments.add(new BeanComment(1, "Budi", "Wie geht es Ihnen?"));
		alComments
				.add(new BeanComment(
						0,
						"Efindi",
						"Hey there, saya mau coba multiple line comment aja deh.. hahahahaha maafkan kalau spam yah teman-teman :D"));

		// Don't reload if comments already loaded
		if (llComments.getChildCount() <= 0) {
			for (int i = 0; i < alComments.size(); i++) {
				TextViewComment tvComment = new TextViewComment(getActivity(), alComments.get(i));
				tvComment.setListenerSpan(new ListenerNameClick(alComments.get(i)));

				// Add textview to the layout
				llComments.addView(tvComment);
			}
		}
	}

	class ListenerNameClick extends ClickableSpan {
		BeanComment	comment;

		public ListenerNameClick(BeanComment comment) {
			super();
			this.comment = comment;
		}

		public void onClick(View tv) {
			// Close dialog first
			getHome().dismissDialog();

			// Navigate to new page
			getHome().setFragment(new FragmentProfile(comment.getName()));
		}

		public void updateDrawState(TextPaint ds) {// override updateDrawState
			ds.setUnderlineText(false); // set to false to remove underline
		}
	}
}