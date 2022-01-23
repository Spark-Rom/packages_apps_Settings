/*
 * Copyright (C) 2020 Wave-OS
 * Copyright (C) 2022 Project Arcana
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

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.LayoutPreference;

public class SparkInfoPreferenceController extends AbstractPreferenceController {

    private static final String KEY_SPARK_INFO = "spark_info";

    private static final String PROP_SPARK_VERSION = "ro.spark.build.version";
    private static final String PROP_SPARK_RELEASETYPE = "ro.spark.buildtype";
    private static final String PROP_SPARK_MAINTAINER = "ro.spark.maintainer";
    private static final String PROP_SPARK_DEVICE = "ro.spark.device";

    public SparkInfoPreferenceController(Context context) {
        super(context);
    }

    private String getDeviceName() {
        String device = SystemProperties.get(PROP_SPARK_DEVICE, "");
        if (device.equals("")) {
            device = Build.MANUFACTURER + " " + Build.MODEL;
        }
        return device;
    }

    private String getSparkVersion() {
        final String version = SystemProperties.get(PROP_SPARK_VERSION,
                this.mContext.getString(R.string.device_info_default));

        return version + " ";
    }

    private String getSparkReleaseType() {
        final String releaseType = SystemProperties.get(PROP_SPARK_RELEASETYPE,
                this.mContext.getString(R.string.device_info_default));

        return releaseType.substring(0, 1).toUpperCase() +
                 releaseType.substring(1).toLowerCase();
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        final LayoutPreference SparkInfoPreference = screen.findPreference(KEY_SPARK_INFO);
        final TextView version = (TextView) SparkInfoPreference.findViewById(R.id.version_message);
        final TextView device = (TextView) SparkInfoPreference.findViewById(R.id.device_message);
        final TextView releaseType = (TextView) SparkInfoPreference.findViewById(R.id.release_type_message);
        final TextView maintainer = (TextView) SparkInfoPreference.findViewById(R.id.maintainer_message);
        final String SparkVersion = getSparkVersion();
        final String SparkDevice = getDeviceName();
        final String SparkReleaseType = getSparkReleaseType();
        final String SparkMaintainer = SystemProperties.get(PROP_SPARK_MAINTAINER,
                this.mContext.getString(R.string.device_info_default));
        version.setText(SparkVersion);
        device.setText(SparkDevice);
        releaseType.setText(SparkReleaseType);
        maintainer.setText(SparkMaintainer);
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
