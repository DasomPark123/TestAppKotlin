package com.example.testappkotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testappkotlin.adapters.DataListAdapter
import com.example.testappkotlin.databinding.ActivityMainBinding
import com.example.testappkotlin.dialogs.InputDataDialog
import com.example.testappkotlin.dialogs.RadioGroupDialog
import com.example.testappkotlin.entity.Fruits
import com.example.testappkotlin.utils.PreferenceUtils
import com.example.testappkotlin.viewmodel.FruitsViewModel

class MainActivity : AppCompatActivity() {

    private val TAG: String = javaClass.simpleName

    private lateinit var binding: ActivityMainBinding
    private val fruitsViewModel : FruitsViewModel by viewModels()

    //private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DataListAdapter
    private lateinit var prefUtils : PreferenceUtils

    private lateinit var name: String
    private lateinit var price: String

    private var type: Int = 0

    private var saveItems = emptyArray<String>()
    private var dataList: ArrayList<Fruits> = ArrayList()

    companion object {
        const val REQUEST_ADD: Int = 0x1001
        const val REQUEST_UPDATE: Int = 0x1002
        const val REQUEST_SAVE: Int = 0x1003

        const val INTERNAL_STORAGE: Int = 0
        const val EXTERNAL_STORAGE: Int = 1
        const val SHARED_PREFERENCE: Int = 2
        const val SQL_LITE: Int = 3
        const val ROOM: Int = 4
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private fun initView(): Void {
        setSupportActionBar(binding.toolbar)
        setTitle(getString(R.string.data_list))

        prefUtils = PreferenceUtils(applicationContext)

        adapter = DataListAdapter(dataList)
        binding.rvDataList.adapter = adapter
        binding.rvDataList.layoutManager = LinearLayoutManager(this)

        binding.btnDelete.setOnClickListener(onClickListener)

        saveItems = resources.getStringArray(R.array.save_type_array)

//        fruitsViewModel = ViewModelProvider(this,
//            ViewModelProvider.AndroidViewModelFactory(application)).get(FruitsViewModel.class)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_save -> {
                showSaveDataDialog()
            }
            R.id.action_add -> {
                val intent = Intent(this, InputDataDialog::class.java)
                startActivity(intent)
            }
            R.id.action_delete -> {
                binding.btnDelete.visibility = View.VISIBLE
                binding.rvDataList.adapter.checkboxVisibility = true
                binding.rvDataList.adapter?.notifyDataSetChanged()
            }
            R.id.action_select_all -> {
                for (fruit : Fruits in dataList)
                    fruit.check = true
                binding.rvDataList.adapter?.notifyDataSetChanged()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            REQUEST_ADD -> {
                if(resultCode == Activity.RESULT_OK) {
                    val fruits : Fruits = Fruits(data.getStringExtra(Utils.EXTRA_NAME_VALUE)
                        , data.getStringExtra(Utils.EXTRA_PRICE_VALUE))
                    fruitsViewModel.insertAll(fruits)
                }
                else {
                    showToast(applicationContext, getString(R.string.not_saved))
                }
            }

            REQUEST_UPDATE -> {
                if(resultCode == Activity.RESULT_OK) {
                    val fruits : Fruits = Fruits(data.getStringExtra(Utils.EXTRA_NAME_VALUE),
                        data.getStringExtra(Utils.EXTRA_PRICE_VALUE))
                    fruitsViewModel.update(fruits)
                }
            }

            REQUEST_SAVE -> {
                if(resultCode == Activity.RESULT_OK) {
                   var intent = Intent()
                    var result : Int = intent.getIntExtra(Utils.EXTRA_INT_VALUE, 0)
                    saveData(result)
                }
            }
        }
    }

    private fun saveData(saveType : Int) {
        when (saveType) {
            INTERNAL_STORAGE -> {

            }
            EXTERNAL_STORAGE -> {

            }
            SHARED_PREFERENCE -> {

            }
            SQL_LITE -> {

            }
            ROOM -> {

            }
        }
    }

    private fun saveViaRoom() {

    }

    private fun showSaveDataDialog(title : Int, itemList : Int, selectedItem : Int) {
        val intent : Intent = Intent(this, RadioGroupDialog::class.java)
        val item = resources.getStringArray(itemList)
        intent.putExtra(Utils.EXTRA_TITLE, getSring(title))
        intent.putExtra(Utils.EXTRA_STRING_ARRAY_VALUE, items)
        intent.putExtra(Utils.EXTRA_INT_VALUE, selectedItem)
        startActivityForResult(intent, REQUEST_SAVE)
    }

    private fun removeCheckedData() {
        for(fruits : Fruits in dataList) {
            if(fruits.check == true) {
                fruitsViewModel.deleteAll(fruits)
            }
        }
    }

    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btn_delete -> {
                removeCheckedData()
                binding.btnDelete.visibility = View.GONE
                binding.rvDataList.adapter?.apply {
                    setCheckboxVisibility = false
                    notifyDataSetChanged()
                }
            }
        }
    }
}