package co.id.cpn.bdmgafi.ui.distribution.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.id.cpn.bdmgafi.R
import co.id.cpn.bdmgafi.databinding.FragmentCustomerMapBinding
import co.id.cpn.entity.util.Constants.DISTRIBUTION
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.geojson.Feature
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponent
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.UiSettings
import com.mapbox.mapboxsdk.module.http.HttpRequestUtil
import kotlin.jvm.internal.Intrinsics

class CustomerMapFragment : Fragment(),
    OnMapReadyCallback, MapboxMap.OnMapClickListener, PermissionsListener {

    private val CPN_STYLE =
        "https://map.sadix.com/api/v1/styles/basic/style.json?key=MrgWC8yuolU6fHImhalIts5pzH6xNxiO"

    private var _binding: FragmentCustomerMapBinding? = null
    private val binding get() = _binding!!

    private var distribution: String? = null
    private var region: String? = null

    private val DIST = "pos"
    private val ICON_ID = "ICON_ID"
    private val LAYER_ID = "LAYER_ID"
    private val REGION = "type"
    private val REQUEST_CODE_AUTOCOMPLETE = 1
    private val SOURCE_ID = "SOURCE_ID"
    private val currentLatLang: LatLng? = null
    private val features: List<Feature> = ArrayList()
    private val locationComponent: LocationComponent? = null
    private val mapboxMap: MapboxMap? = null
    private val permissionsManager: PermissionsManager? = null

    companion object {
        @JvmStatic
        fun newInstance(distribution: String, region: String) =
            CustomerMapFragment().apply {
                arguments = Bundle().apply {
                    putString(DISTRIBUTION, distribution)
                    putString(REGION, region)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            distribution = it.getString(DISTRIBUTION)
            region = it.getString(REGION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Mapbox.getInstance(requireContext(), resources.getString(R.string.access_token));
        _binding = FragmentCustomerMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Intrinsics.checkNotNullParameter(view, "view")
        super.onViewCreated(view, savedInstanceState)
        binding.infoWindow.visibility = View.INVISIBLE

        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
        HttpRequestUtil.setLogEnabled(false)
//      Log.w("TAG", "asset_selected : " + getViewModel().getAssetSelected().getValue() + ' ')
        binding.closeButton.setOnClickListener(View.OnClickListener { view ->
            binding.infoWindow.visibility = View.INVISIBLE
        })
//        getBinding().currentLocationButton.setOnClickListener(View.OnClickListener { view ->
//            CustDistMapFragment.`m400onViewCreated$lambda1`(
//                this@CustDistMapFragment,
//                view
//            )
//        })
//        getBinding().showLegend.setOnClickListener(View.OnClickListener { view ->
//            CustDistMapFragment.`m401onViewCreated$lambda2`(
//                this@CustDistMapFragment,
//                view
//            )
//        })
//        getBinding().closeLegend.setOnClickListener(View.OnClickListener { view ->
//            CustDistMapFragment.`m402onViewCreated$lambda3`(
//                this@CustDistMapFragment,
//                view
//            )
//        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        val uiSettings: UiSettings = mapboxMap.uiSettings
        uiSettings.setCompassMargins(0, 60, 0, 0)
//        setupData(mapboxMap, "")
        /*getViewModel().getFilter().observe(this, object : Observer(mapboxMap2) {
            *//* synthetic *//* val `f$1`: MapboxMap? = null
            fun onChanged(obj: Any?) {
                CustDistMapFragment.`m397onMapReady$lambda4`(
                    this@CustDistMapFragment,
                    `f$1`,
                    obj as String?
                )
            }

            init {
                `f$1` = r2
            }
        })
        getViewModel().getFilterCustomer()
            .observe(viewLifecycleOwner, object : Observer(mapboxMap2) {
                *//* synthetic *//* val `f$1`: MapboxMap? = null
                fun onChanged(obj: Any?) {
                    CustDistMapFragment.`m398onMapReady$lambda5`(
                        this@CustDistMapFragment,
                        `f$1`,
                        obj as FilterCustomer?
                    )
                }

                init {
                    `f$1` = r2
                }
            })*/
    }

    override fun onMapClick(point: LatLng): Boolean {
        TODO("Not yet implemented")
    }

    override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {
        TODO("Not yet implemented")
    }

    override fun onPermissionResult(granted: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}