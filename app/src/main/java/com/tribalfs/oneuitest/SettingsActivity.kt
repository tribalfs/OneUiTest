package com.tribalfs.oneuitest

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.util.SeslMisc
import androidx.preference.*
import com.tribalfs.oneuitest.databinding.ActivitySettingsBinding
import com.tribalfs.oneuitest.utils.DarkModeUtils
import com.tribalfs.oneuitest.utils.showToast
import dev.oneuiproject.oneui.preference.HorizontalRadioPreference
import dev.oneuiproject.oneui.preference.internal.PreferenceRelatedCard
import dev.oneuiproject.oneui.utils.PreferenceUtils


class SettingsActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(mBinding.settings.id, SettingsFragment())
                .commit()
        }
        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mBinding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        mBinding.ctl.title = "Settings"
        supportActionBar?.title = "Settings"

    }

    class SettingsFragment : PreferenceFragmentCompat(),
        Preference.OnPreferenceClickListener,
        Preference.OnPreferenceChangeListener  {

        private var mRelativeLinkCard: PreferenceRelatedCard? = null

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            initPreferences()
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
                getView()!!.setBackgroundColor(requireContext().getColor(R.color.oui_background_color))
            listView.seslSetLastRoundedCorner(false)
        }

        override fun onResume() {
            setRelativeLinkCard()
            super.onResume()
        }

        @SuppressLint("RestrictedApi")
        private fun initPreferences() {
            /*val tips = findPreference<TipsCardPreference>("tip")
            tips!!.addButton("Button") {
                requireContext().showToast("onClick")
            }*/

            val darkMode = DarkModeUtils.getDarkMode(requireContext())
            val darkModePref = findPreference<HorizontalRadioPreference>("dark_mode")
            darkModePref!!.onPreferenceChangeListener = this
            darkModePref.setDividerEnabled(false)
            darkModePref.setTouchEffectEnabled(false)
            darkModePref.isEnabled = darkMode != DarkModeUtils.DARK_MODE_AUTO
            darkModePref.value = if (SeslMisc.isLightTheme(requireContext())) "0" else "1"
            val autoDarkModePref = findPreference<SwitchPreferenceCompat>("dark_mode_auto")
            autoDarkModePref!!.onPreferenceChangeListener = this
            autoDarkModePref.isChecked = darkMode == DarkModeUtils.DARK_MODE_AUTO

            val keySync = findPreference<SeslSwitchPreferenceScreen>("key_sync")
            val enabled = keySync!!.isChecked
            keySync.summary = if (enabled) "tribalfs@gmail.com" else "Disabled"
            keySync.seslSetSummaryColor(getColoredSummaryColor(enabled))
            keySync.onPreferenceClickListener = this
            keySync.onPreferenceChangeListener = this


            val keyCurrency = findPreference<ListPreference>("key_currency")
            keyCurrency!!.seslSetSummaryColor(getColoredSummaryColor(true))

            val keyDf = findPreference<ListPreference>("key_df")
            keyDf!!.seslSetSummaryColor(getColoredSummaryColor(true))

            findPreference<Preference>("key_abt")?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
                //Todo()
                true
            }

        }

        private fun getColoredSummaryColor(enabled: Boolean): ColorStateList {
            return if (enabled) {
                val colorPrimaryDark = TypedValue()
                requireContext().theme.resolveAttribute(androidx.preference.R.attr.colorPrimaryDark, colorPrimaryDark, true)
                val states = arrayOf(
                    intArrayOf(android.R.attr.state_enabled),
                    intArrayOf(-android.R.attr.state_enabled)
                )
                val colors = intArrayOf(
                    Color.argb(
                        0xff,
                        Color.red(colorPrimaryDark.data),
                        Color.green(colorPrimaryDark.data),
                        Color.blue(colorPrimaryDark.data)
                    ),
                    Color.argb(
                        0x4d,
                        Color.red(colorPrimaryDark.data),
                        Color.green(colorPrimaryDark.data),
                        Color.blue(colorPrimaryDark.data)
                    )
                )
                ColorStateList(states, colors)
            } else {
                val outValue = TypedValue()
                requireContext().theme.resolveAttribute(
                    androidx.appcompat.R.attr.isLightTheme, outValue, true
                )
                requireContext().getColorStateList(if (outValue.data != 0) androidx.picker.R.color.sesl_secondary_text_light else androidx.picker.R.color.sesl_secondary_text_dark)
            }
        }


        override fun onPreferenceClick(preference: Preference): Boolean {

            when (preference.key) {
                "key_sync" -> {
                    requireContext().showToast("Todo")
                    return true
                }
                "key_abt" -> {
                    requireContext().showToast("Todo")
                    return true
                }
                "key_contact" -> {
                    requireContext().showToast("Todo")
                    return true
                }
            }
            return false
        }

        override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {
            val currentDarkMode = DarkModeUtils.getDarkMode(requireContext()).toString()
            val darkModePref = findPreference<Preference>("dark_mode") as HorizontalRadioPreference?
            when (preference.key) {

                "dark_mode" -> {
                    if (currentDarkMode !== newValue) {
                        DarkModeUtils.setDarkMode(
                            requireActivity() as AppCompatActivity,
                            if (newValue as String == "0") DarkModeUtils.DARK_MODE_DISABLED else DarkModeUtils.DARK_MODE_ENABLED
                        )
                    }
                    return true
                }

                "dark_mode_auto" -> {
                    if (newValue as Boolean) {
                        darkModePref!!.isEnabled = false
                        DarkModeUtils.setDarkMode(
                            requireActivity() as AppCompatActivity,
                            DarkModeUtils.DARK_MODE_AUTO
                        )
                    } else {
                        darkModePref!!.isEnabled = true
                    }
                    return true
                }

                "key_sync" -> {
                    val enabled = newValue as Boolean
                    preference.summary = if (enabled) "tribalfs@gmail.com" else "Disabled"
                    preference.seslSetSummaryColor(getColoredSummaryColor(enabled))
                    return true
                }

            }
            return false
        }

        private fun setRelativeLinkCard() {
            if (mRelativeLinkCard == null) {
                mRelativeLinkCard = PreferenceUtils.createRelatedCard(requireContext())!!
                mRelativeLinkCard!!.addButton("This", null)
                    .addButton("That", null)
                    .addButton("There", null)
                    .show(this)
            }
        }
    }
}