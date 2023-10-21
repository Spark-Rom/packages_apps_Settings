/*
 * Copyright (C) 2020 Wave-OS
 * Copyright (C) 2023 the RisingOS Android Project
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

package com.android.settings.deviceinfo.firmwareversion;

import android.content.Context;
import android.os.Build;
import android.os.SystemProperties;
import android.widget.TextView;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.LayoutPreference;

public class sparkInfoPreferenceController extends AbstractPreferenceController {

    private static final String KEY_SPARK_INFO = "spark_info";
    private static final String KEY_SPARK_DEVICE = "spark_device";
    private static final String KEY_SPARK_VERSION = "spark_version";
    private static final String KEY_BUILD_STATUS = "rom_build_status";
    private static final String KEY_BUILD_VERSION = "spark_build_version";
    
    private static final String PROP_SPARK_VERSION = "ro.spark.version";
    private static final String PROP_SPARK_RELEASETYPE = "ro.spark.buildtype";
    private static final String PROP_SPARK_MAINTAINER = "ro.spark.maintainer";
    private static final String PROP_SPARK_DEVICE = "ro.spark.device";
    private static final String PROP_SPARK_BUILD_VERSION = "ro.spark.build.version";


    public sparkInfoPreferenceController(Context context) {
        super(context);
    }

    private String getPropertyOrDefault(String propName) {
        return SystemProperties.get(propName, this.mContext.getString(R.string.device_info_default));
    }

    private String getDeviceName() {
        String device = SystemProperties.get(PROP_SPARK_DEVICE, "");
        if (device.equals("")) {
            device = Build.MANUFACTURER + " " + Build.MODEL;
        }
        return device;
    }

    private String getSparkBuildVersion() {
        final String buildVer = SystemProperties.get(PROP_SPARK_BUILD_VERSION,
                this.mContext.getString(R.string.device_info_default));;

        return buildVer;
    }
    
    private String getSparkVersion() {
        final String version = SystemProperties.get(PROP_SPARK_VERSION,
                this.mContext.getString(R.string.device_info_default));

        return version;
    }

    private String getSparkReleaseType() {
        final String releaseType = SystemProperties.get(PROP_SPARK_RELEASETYPE,
                this.mContext.getString(R.string.device_info_default));
	
        return releaseType.substring(0, 1).toUpperCase() +
               releaseType.substring(1).toLowerCase();
    }
    
    private String getSparkbuildStatus() {
	final String buildType = SystemProperties.get(PROP_SPARK_RELEASETYPE,
                this.mContext.getString(R.string.device_info_default));
        final String isOfficial = this.mContext.getString(R.string.build_is_official_title);
	final String isCommunity = this.mContext.getString(R.string.build_is_community_title);
	
	if (buildType.toLowerCase().equals("official")) {
		return isOfficial;
	} else {
		return isCommunity;
	}
    }

    private String getSparkMaintainer() {
	final String SparkMaintainer = SystemProperties.get(PROP_SPARK_MAINTAINER,
                this.mContext.getString(R.string.device_info_default));
	final String buildType = SystemProperties.get(PROP_SPARK_RELEASETYPE,
                this.mContext.getString(R.string.device_info_default));
        final String isOffFine = this.mContext.getString(R.string.build_is_official_summary, SparkMaintainer);
	final String isOffMiss = this.mContext.getString(R.string.build_is_official_summary_oopsie);
	final String isCommMiss = this.mContext.getString(R.string.build_is_community_summary_oopsie);
	final String isCommFine = this.mContext.getString(R.string.build_is_community_summary, SparkMaintainer);
	
	if (buildType.toLowerCase().equals("official") && !SparkMaintainer.equalsIgnoreCase("Unknown")) {
	    return isOffFine;
	} else if (buildType.toLowerCase().equals("official") && SparkMaintainer.equalsIgnoreCase("Unknown")) {
	     return isOffMiss;
	} else if (buildType.equalsIgnoreCase("Community") && SparkMaintainer.equalsIgnoreCase("Unknown")) {
	     return isCommMiss;
	} else {
	    return isCommFine;
	}
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);

        final String SparkMaintainer = getSparkMaintainer();
        final String isOfficial = getPropertyOrDefault(PROP_SPARK_RELEASETYPE).toLowerCase();

        screen.findPreference(KEY_BUILD_STATUS).setTitle(getSparkbuildStatus());
        screen.findPreference(KEY_BUILD_STATUS).setSummary(SparkMaintainer);
        screen.findPreference(KEY_BUILD_VERSION).setSummary(getSparkBuildVersion());
        screen.findPreference(KEY_SPARK_VERSION).setSummary(getSparkVersion());
        screen.findPreference(KEY_SPARK_DEVICE).setSummary(getDeviceName());
        screen.findPreference(KEY_BUILD_STATUS).setIcon(isOfficial.equals("official") ? R.drawable.verified : R.drawable.unverified);
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getPreferenceKey() {
        return KEY_SPARK_INFO;
    }
}
