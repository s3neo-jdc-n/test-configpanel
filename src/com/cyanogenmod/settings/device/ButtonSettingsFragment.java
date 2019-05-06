/*
 * Copyright (C) 2016 The CyanogenMod Project
 *           (C) 2017 The LineageOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cyanogenmod.settings.device;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.support.v14.preference.PreferenceFragment;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.support.v7.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceScreen;
import android.text.TextUtils;
import android.view.MenuItem;

import java.io.File;

import com.cyanogenmod.settings.device.utils.FileUtils;

public class ButtonSettingsFragment extends PreferenceFragment
        implements OnPreferenceChangeListener {

    private Preference mKcalPref;
    private VibratorStrengthPreference mVibratorStrength;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.button_panel);
        final ActionBar actionBar = getActivity().getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mVibratorStrength = (VibratorStrengthPreference) findPreference("vibrator_key");
        if (mVibratorStrength != null) {
            mVibratorStrength.setEnabled(VibratorStrengthPreference.isSupported());
        }

        mKcalPref = findPreference("kcal");
        mKcalPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
             @Override
             public boolean onPreferenceClick(Preference preference) {
                 Intent intent = new Intent(getActivity(), DisplayCalibration.class);
                 startActivity(intent);
                 return true;
             }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        updatePreferencesBasedOnDependencies();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       if (item.getItemId() == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public void onDisplayPreferenceDialog(Preference preference) {
        if (preference instanceof VibratorStrengthPreference){
           ((VibratorStrengthPreference)preference).onDisplayPreferenceDialog(preference);
        } else {
            super.onDisplayPreferenceDialog(preference);
        }
    }
}
