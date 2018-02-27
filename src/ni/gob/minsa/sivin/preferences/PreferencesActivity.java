/*
 * Copyright (C) 2012
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package ni.gob.minsa.sivin.preferences;


import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.MenuItem;
import android.widget.Toast;
import ni.gob.minsa.sivin.R;
import ni.gob.minsa.sivin.activities.ListaSegmentosActivity;
import ni.gob.minsa.sivin.utils.UrlUtils;

/**
 * @author william aviles
 */
@SuppressWarnings("deprecation")
public class PreferencesActivity extends PreferenceActivity implements
        OnSharedPreferenceChangeListener {
	

    public static String KEY_SERVER_URL = "server_url";
    public static String KEY_USERNAME = "username";
    public static String KEY_BARCODE = "barcode";
    public static String KEY_SEGMENTO = "segmento";
    public static String KEY_CODE_SEGMENTO = "segmentoSeleccionado";


    private EditTextPreference mServerUrlPreference;
    private EditTextPreference mUsernamePreference;
    private PreferenceScreen mSegmentoPreference;
    
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        	ActionBar actionBar = getActionBar();
        	actionBar.setDisplayHomeAsUpEnabled(true);
        }
        
        
        setTitle(getString(R.string.app_name) + " > " + getString(R.string.preferences));
        buscarSegmento();
        updateServerUrl();
        updateUsername();
        updateSegmento();
    }


    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(
            this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        updateServerUrl();
        updateUsername();
        updateSegmento();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    	if (key.equals(KEY_SERVER_URL)) {
            updateServerUrl();
        } else if (key.equals(KEY_USERNAME)) {
            updateUsername();
        } else if (key.equals(KEY_SEGMENTO)) {
        	updateSegmento();
        }
    }

    private void validateUrl(EditTextPreference preference) {
        if (preference != null) {
            String url = preference.getText();
            if (UrlUtils.isValidUrl(url)) {
                preference.setText(url);
                preference.setSummary(url);
            } else {
                // preference.setText((String) preference.getSummary());
                Toast.makeText(getApplicationContext(), getString(R.string.url_error),
                    Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateServerUrl() {
        mServerUrlPreference = (EditTextPreference) findPreference(KEY_SERVER_URL);

        // remove all trailing "/"s
        while (mServerUrlPreference.getText().endsWith("/")) {
            mServerUrlPreference.setText(mServerUrlPreference.getText().substring(0,
                mServerUrlPreference.getText().length() - 1));
        }
        validateUrl(mServerUrlPreference);
        mServerUrlPreference.setSummary(mServerUrlPreference.getText());

        mServerUrlPreference.getEditText().setFilters(new InputFilter[] {
            getReturnFilter()
        });
    }


    private void updateUsername() {
        mUsernamePreference = (EditTextPreference) findPreference(KEY_USERNAME);
        mUsernamePreference.setSummary(mUsernamePreference.getText());

        mUsernamePreference.getEditText().setFilters(new InputFilter[] {
            getWhitespaceFilter()
        });

    }
    
    private void updateSegmento() {
    	mSegmentoPreference = (PreferenceScreen) findPreference(KEY_SEGMENTO);
    	mSegmentoPreference.setSummary(mSegmentoPreference.getSharedPreferences().getString(KEY_SEGMENTO, null));
    }
    
    private void buscarSegmento() {
    	mSegmentoPreference = (PreferenceScreen) findPreference(KEY_SEGMENTO);
    	mSegmentoPreference.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference preference) {
				// Presenta Departamento
				Intent myIntent = new Intent(PreferencesActivity.this, ListaSegmentosActivity.class);
				PreferencesActivity.this.startActivity(myIntent);
			    return true;
			}
    		
    	}); 	
    }


    private InputFilter getWhitespaceFilter() {
        InputFilter whitespaceFilter = new InputFilter() {
            @Override
			public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                    int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };
        return whitespaceFilter;
    }


    private InputFilter getReturnFilter() {
        InputFilter returnFilter = new InputFilter() {
            @Override
			public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                    int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (Character.getType((source.charAt(i))) == Character.CONTROL) {
                        return "";
                    }
                }
                return null;
            }
        };
        return returnFilter;
    }
    
    /**
     * Let's the user tap the activity icon to go 'home'.
     * Requires setHomeButtonEnabled() in onCreate().
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
    	switch (menuItem.getItemId()) {
        case android.R.id.home:
          finish();
          return true;
    	}
      return (super.onOptionsItemSelected(menuItem));
    }
}
