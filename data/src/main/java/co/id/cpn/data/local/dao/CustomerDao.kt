package co.id.cpn.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.id.cpn.entity.Customer
import co.id.cpn.entity.CustomerItem

@Dao
interface CustomerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(customer: Customer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(customer: List<Customer>)

    @Query("select * from customer")
    fun getAll(): LiveData<List<Customer>>

    @Query("select " +
            "customer.customer_sid," +
            "customer.customer_id," +
            "customer.customer_vipcode," +
            "customer.customer_name," +
            "customer.customer_address," +
            "customer.customer_type_sid," +
            "customer.customer_order," +
            "customer.customer_inspection," +
            "case when(" +
            "select GROUP_CONCAT(asset_id, ', ') from asset where asset.customer_sid = customer.customer_sid group by customer_sid " +
            ") is null then '-' else (" +
            "select GROUP_CONCAT(asset_id, ', ') from asset where asset.customer_sid = customer.customer_sid group by customer_sid " +
            ") end " +
            "asset_id, " +
            "'' asset_type " +
            "from customer left join asset on customer.customer_sid = asset.customer_sid")
    fun getCustomersItems(): LiveData<List<CustomerItem>>

    @Query("select " +
            "customer.customer_sid," +
            "customer.customer_id," +
            "customer.customer_vipcode," +
            "customer.customer_name," +
            "customer.customer_address," +
            "customer.customer_type_sid," +
            "customer.customer_order," +
            "customer.customer_inspection," +
            "case when asset.asset_id is null then '-' else asset.asset_id end asset_id, " +
            "'' asset_type " +
            "from customer left join asset on customer.customer_sid = asset.customer_sid " +
            "where customer.customer_sid = :customerSID ")
    fun getCustomersItemsBy(customerSID: String): LiveData<CustomerItem>

    @Query("DELETE FROM customer")
    fun deleteAll()
}