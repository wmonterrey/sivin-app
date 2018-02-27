package ni.gob.minsa.sivin.wizard.model;

import java.util.ArrayList;



import android.support.v4.app.Fragment;
import android.text.TextUtils;
import ni.gob.minsa.sivin.wizard.ui.TextFragment;

public class TextPage extends Page {
	
	protected boolean mValPattern = false;
	protected String mPattern="";

	public TextPage(ModelCallbacks callbacks, String title, String hintText, String textColor, boolean isVisible) {
		super(callbacks, title, hintText, textColor, isVisible, true);
	}

	@Override
	public Fragment createFragment() {
		return TextFragment.create(getKey());
	}
	
	public Page setPatternValidation(boolean valPattern, String pattern) {
		mPattern = pattern;
		mValPattern = valPattern;
        return this;
    }

	@Override
	public void getReviewItems(ArrayList<ReviewItem> dest) {
		dest.add(new ReviewItem(getTitle(), mData.getString(SIMPLE_DATA_KEY),
				getKey()));

	}

	public boolean ismValPattern() {
		return mValPattern;
	}

	public void setmValPattern(boolean mValPattern) {
		this.mValPattern = mValPattern;
	}

	public String getmPattern() {
		return mPattern;
	}

	public void setmPattern(String mPattern) {
		this.mPattern = mPattern;
	}

	@Override
	public boolean isCompleted() {
		return !TextUtils.isEmpty(mData.getString(SIMPLE_DATA_KEY));
	}

	public TextPage setValue(String value) {
		mData.putString(SIMPLE_DATA_KEY, value);
		return this;
	}
}
