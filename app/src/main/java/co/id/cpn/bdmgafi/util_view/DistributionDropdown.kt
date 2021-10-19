package co.id.cpn.bdmgafi.util_view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import co.id.cpn.bdmgafi.R
import co.id.cpn.entity.Distribution
import java.util.*

class DistributionDropdown @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet?,
) : AppCompatAutoCompleteTextView(context, attributeSet) {
    private var dataSelectedName: String? = null
    private var dataSelectedId: String? = null
    private var instance: DistributionDropdown? = null
    private val attributeSet: AttributeSet? = null

    private fun getInstance(): DistributionDropdown? {
        if (instance == null) {
            instance = DistributionDropdown(context, attributeSet)
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

    fun setupData(distributions: List<Distribution>) {
        val label: MutableList<String> = ArrayList()
        for (data in distributions) {
            label.add(data.distributionName)
        }
        setAdapter(
            ArrayAdapter(
                context,
                android.R.layout.simple_spinner_dropdown_item,
                label
            )
        )
        setOnClickListener { showDropDown() }
        this.onItemClickListener = AdapterView.OnItemClickListener{
                parent, _, position, _ ->
            dataSelectedName = parent.getItemAtPosition(position).toString()
            dataSelectedId = distributions[position].distributionSID
        }
    }
    
    fun getSelectedID() : String = dataSelectedId.toString()
}