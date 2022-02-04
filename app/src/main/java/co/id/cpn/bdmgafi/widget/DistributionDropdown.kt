package co.id.cpn.bdmgafi.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import co.id.cpn.bdmgafi.R
import co.id.cpn.entity.Distribution
import java.util.*
import kotlin.jvm.internal.Intrinsics


class DistributionDropdown(context: Context?, attributeSet2: AttributeSet?) :
    AppCompatAutoCompleteTextView(
        context!!, attributeSet2
    ) {
    private val attributeSet: AttributeSet? = null
    private var dataSelectedId: String? = null
    private var dataSelectedName: String? = null
    private var instance: DistributionDropdown? = null
        private get() {
            if (field == null) {
                val context = context
                Intrinsics.checkNotNullExpressionValue(context, "context")
                field = DistributionDropdown(context, attributeSet)
            }
            return field
        }

    override fun setError(error: CharSequence) {
        val icon = resources.getDrawable(R.drawable.ic_important)
        icon?.setBounds(0, 0, icon.intrinsicWidth, icon.intrinsicHeight)
        super.setError(error, icon)
    }

    override fun getFreezesText(): Boolean {
        return false
    }

    fun setupData(distributions: List<Distribution>) {
        val label: MutableList<String> = ArrayList<String>()
        for (data in distributions) {
            label.add(data.distributionName)
        }
        setAdapter<ArrayAdapter<String>>(
            ArrayAdapter(
                context,
                android.R.layout.simple_spinner_dropdown_item,
                label
            )
        )
        setOnClickListener { showDropDown() }
        setOnItemClickListener { parent, _, position, _ ->
            this.dataSelectedName = parent.getItemAtPosition(position).toString()
            this.dataSelectedId = distributions[position].distributionSID
        }
    }
}