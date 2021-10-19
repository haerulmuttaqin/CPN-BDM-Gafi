package co.id.cpn.bdmgafi.util_view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.widget.AdapterView
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import co.id.cpn.bdmgafi.R
import co.id.cpn.bdmgafi.ui.adapter.CheckboxDropdownAdapter
import co.id.cpn.entity.Region


class RegionDropdown @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet?,
) : AppCompatAutoCompleteTextView(context, attributeSet) {
    private var instance: RegionDropdown? = null
    private val attributeSet: AttributeSet? = null

    private fun getInstance(): RegionDropdown? {
        if (instance == null) {
            instance = RegionDropdown(context, attributeSet)
        }
        return instance
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun setError(error: CharSequence?) {
        val icon = resources.getDrawable(R.drawable.ic_important)
        icon?.setBounds(
            0, 0,
            icon.intrinsicWidth,
            icon.intrinsicHeight
        )
        super.setError(error, icon)
    }

    fun setupData(regions: List<Region>) {
        setAdapter(
            CheckboxDropdownAdapter(
                context,
                R.layout.dropdown_checkbox,
                regions
            )
        )
        this.setOnClickListener { showDropDown() }
        this.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            Log.w("SELECTED ", selectedItem)
        }
        this.setOnDismissListener {
            val regionsSelected = arrayListOf<Region>()
            regions.forEach {
                if (it.isSelected) {
                    regionsSelected.add(it)
                }
            }
            this.setText("Selected ${regionsSelected.size} items")
            dismissDropDown()
        }
    }

    override fun showDropDown() {
        val displayFrame = Rect()
        getWindowVisibleDisplayFrame(displayFrame)
        val locationOnScreen = IntArray(2)
        getLocationOnScreen(locationOnScreen)
        val bottom = locationOnScreen[1] + height
        val availableHeightBelow: Int = displayFrame.bottom - bottom
        val r: Resources = resources
        val bannerHeight = Math.round(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                50f,
                r.getDisplayMetrics()
            )
        )
        val downHeight = availableHeightBelow - bannerHeight
        dropDownHeight = if (downHeight > 0) {
            downHeight
        } else {
            300
        }
        super.showDropDown()
    }
}