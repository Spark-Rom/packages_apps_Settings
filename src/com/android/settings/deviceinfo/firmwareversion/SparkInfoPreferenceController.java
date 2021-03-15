/*
 * Copyright (C) 2020 Wave-OS
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
import android.os.SystemProperties;
import android.widget.TextView;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.LayoutPreference;

public class SparkInfoPreferenceController extends AbstractPreferenceController {

    private static final String KEY_SPARK_INFO = "spark_info";

    private static final String PROP_SPARK_VERSION = "ro.spark.status";
    private static final String PROP_SPARK_VERSION_CODE = "ro.spark.branding.version";
    private static final String PROP_SPARK_RELEASETYPE = "ro.spark.build.type";
    private static final String PROP_SPARK_MAINTAINER = "ro.spark.maintainer";

    public SparkInfoPreferenceController(Context context) {
        super(context);
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        final LayoutPreference sparkInfoPreference = screen.findPreference(KEY_SPARK_INFO);
        final TextView version = (TextView) sparkInfoPreference.findViewById(R.id.version_message);
        final TextView versionCode = (TextView) sparkInfoPreference.findViewById(R.id.version_code_message);
        final TextView releaseType = (TextView) sparkInfoPreference.findViewById(R.id.release_type_message);
        final TextView maintainer = (TextView) sparkInfoPreference.findViewById(R.id.maintainer_message);
        final String sparkVersion = SystemProperties.get(PROP_SPARK_VERSION,
                this.mContext.getString(R.string.device_info_default));
        final String sparkVersionCode = SystemProperties.get(PROP_SPARK_VERSION_CODE,
                this.mContext.getString(R.string.device_info_default));
        final String sparkReleaseType = SystemProperties.get(PROP_SPARK_RELEASETYPE,
                this.mContext.getString(R.string.device_info_default));
        final String sparkMaintainer = SystemProperties.get(PROP_SPARK_MAINTAINER,
                this.mContext.getString(R.string.device_info_default));
        version.setText(sparkVersion);
        versionCode.setText(sparkVersionCode);
        releaseType.setText(sparkReleaseType);
        maintainer.setText(sparkMaintainer);
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
