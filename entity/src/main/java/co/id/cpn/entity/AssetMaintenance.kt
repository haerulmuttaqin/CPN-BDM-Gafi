package co.id.cpn.entity

data class AssetMaintenance(
    val groupAsset: String,
    val idx: Int,
    val mandatory: Int,
    val name: String,
    val position: Int,
    val typeAsset: Int,
    val typeMnt: Int,
)