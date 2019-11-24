package com.example.inviziosolutionsllp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import com.nbsp.materialfilepicker.MaterialFilePicker
import com.nbsp.materialfilepicker.ui.FilePickerActivity
import io.objectbox.Box
import io.objectbox.BoxStoreBuilder
import io.objectbox.kotlin.boxFor
import kotlinx.android.synthetic.main.activity_main.*
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileInputStream
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {
    private var purityC: Int = 11
    private var metalC: Int = 10

    private var collectionC: Int = 9
    private var netC: Int = 8
    private var grossC: Int = 7
    private var sizeC: Int = 6
    private var typeC: Int = 5

    private var statusC: Int = 4
    private var imageC: Int = 3
    private var designC: Int = 2
    private var skuC: Int = 1
    private var rfidC: Int = 0


    private var purityS:    String ?="Metal Purity"
    private var metalS:     String?="Metal Type"
    private var categoryS:  String?="Item Category"
    private var collectionS:String?="Collection Line"
    private var netS:       String?="Net Weight"
    private var grossS:     String?="Gross Weight"
    private var sizeS:      String?="Size"
    private var typeS:      String?="Item Type"
    private var salesS:     String?="SalesManName"
    private var statusS:    String?="Item Status"
    private var imageS:     String?="Image Name"
    private var designS:    String?="Design Number"
    private var skuS:       String?="SKU Number"
    private var rfidS:      String?="RFID Tag"
     val myRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://invizio-solutions-llp.firebaseio.com/"+BoxStoreBuilder.DEFAULT_NAME)



    lateinit var button:Button
    lateinit var  userBox: Box<dataHouse>

    lateinit var viewModel:DataViewModel

    var  listData :List<dataHouse> = ArrayList<dataHouse>()

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        System.setProperty(
            "org.apache.poi.javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl")
        System.setProperty(
            "org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl")
        System.setProperty(
            "org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl")


         ObjectBox.init(this)

         userBox = ObjectBox.boxStore.boxFor()

        viewModel  = ViewModelProviders.of(this).get(DataViewModel::class.java)


        if (userBox!=null) {
            viewModel.getBoxLiveData(userBox).observe(this,
                Observer<List<dataHouse?>> { dataXList->

                    if (!dataXList.isEmpty()) {
                        listData =userBox.all
                        for (i in 0..(listData.size)-1) {

                            var dataListX = dataHouse()
                            dataListX= listData.get(i)

                            var root = myRef.child(dataListX.id.toString() + "")

                            var rootID = root.child(dataListX.RFID_Tag+"")

                            rootID.child(skuS.toString()).setValue(dataListX.SKU_Number)
                                .addOnSuccessListener {

                                    rootID.child(designS.toString())
                                        .setValue(dataListX.Design_Number)
                                        .addOnSuccessListener {

                                            rootID.child(imageS.toString())
                                                .setValue(dataListX.Image_Name)
                                                .addOnSuccessListener {

                                                    rootID.child(statusS.toString())
                                                        .setValue(dataListX.Item_Status)
                                                        .addOnSuccessListener {

                                                            rootID.child(typeS.toString())
                                                                .setValue(dataListX.Item_Type)
                                                                .addOnSuccessListener {

                                                                    rootID.child(sizeS.toString())
                                                                        .setValue(dataListX.Size)
                                                                        .addOnSuccessListener {

                                                                            rootID.child(
                                                                                grossS.toString()
                                                                            )
                                                                                .setValue(
                                                                                    dataListX.Gross_Weight
                                                                                )
                                                                                .addOnSuccessListener {

                                                                                    rootID.child(
                                                                                        netS.toString()
                                                                                    )
                                                                                        .setValue(
                                                                                            dataListX.Net_Weight
                                                                                        )
                                                                                        .addOnSuccessListener {


                                                                                            rootID.child(
                                                                                                collectionS.toString()
                                                                                            )
                                                                                                .setValue(
                                                                                                    dataListX.Collection_Line
                                                                                                )
                                                                                                .addOnSuccessListener {

                                                                                                    rootID.child(
                                                                                                        metalS.toString()
                                                                                                    )
                                                                                                        .setValue(
                                                                                                            dataListX.Metal_Type
                                                                                                        )
                                                                                                        .addOnSuccessListener {

                                                                                                            rootID.child(
                                                                                                                purityS.toString()
                                                                                                            )
                                                                                                                .setValue(
                                                                                                                    dataListX.Metal_Purity
                                                                                                                )
                                                                                                                .addOnSuccessListener {

                                                                                                                    Log.e(
                                                                                                                        "Log-----", "" + listData.size+
                                                                                                                                "" +
                                                                                                                                dataListX.Metal_Type + " " + dataListX.SKU_Number + " " + dataListX.Gross_Weight)
                                                                                                                    Toast.makeText(
                                                                                                                        this,
                                                                                                                        "Data has been Synced",
                                                                                                                        Toast.LENGTH_SHORT
                                                                                                                    )
                                                                                                                        .show()
                                                                                                                }
                                                                                                        }
                                                                                                }
                                                                                        }
                                                                                }
                                                                        }
                                                                }
                                                        }
                                                }
                                        }
                                }


                        }

                    }








                })
        }
        button =findViewById(R.id.button)

        button.setOnClickListener(View.OnClickListener {
            if (checkPermission()) {
                MaterialFilePicker()
                    .withActivity(this)
                    .withRequestCode(1)
                    .withFilter(Pattern.compile(".*\\.xlsx$")) // Filtering files and directories by file name using regexp
                    .withFilterDirectories(true) // Set directories filterable (false by default)
                    .withHiddenFiles(true) // Show hidden files and folders
                    .start()
            }
        })
        fab.setOnClickListener { view ->
            Snackbar.make(view, "number of data points :" +userBox.all.size, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            listData =userBox.all
            for (i in 0..(listData.size)-1) {

                var dataListX = dataHouse()
                dataListX = listData.get(i)
                Log.e(
                    "Log-----", "" + listData.size+
                            "" +
                            dataListX.Metal_Type + " " + dataListX.SKU_Number + " " + dataListX.Gross_Weight+" m> "+dataListX.Metal_Purity)


            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {


            val filePath = data!!.getStringExtra(FilePickerActivity.RESULT_FILE_PATH)
            // Do anything with file
            print(">>> $filePath\n")

            val excelFile = FileInputStream(File(filePath))
            val workbook = XSSFWorkbook(excelFile)

            val sheet = workbook.getSheetAt(0)
            val rows = sheet.iterator()
            val count :Int=0
            var rowsX = sheet.getRow(count)
            userBox.removeAll()
            myRef.removeValue()
            /*for(i in 0..11) {
                when (rowsX.getCell(i).toString()) {
                    rfidS -> {
                        rfidC = i
                    }
                    skuS -> {
                        skuC = i
                    }
                    designS -> {
                        designC = i
                    }
                    imageS -> {
                        imageC = i
                    }
                    statusS -> {
                        statusC = i
                    }

                    typeS -> {
                        typeC = i
                    }
                    sizeS -> {
                        sizeC = i
                    }
                    grossS -> {
                        grossC = i
                    }
                    netS -> {
                        netC = i
                    }
                    collectionS -> {
                        collectionC = i
                    }

                    metalS -> {
                        metalC = i
                    }
                    purityS -> {
                        purityC = i
                    }
                }
            }*/

            for (row:Row in sheet) {

                if (row.rowNum >0) {

                    var dataX =dataHouse()
                    for ((indexC,cell: Cell) in row.withIndex()) {
                        // Do something here
                      if (rfidC===indexC)
                      {
                          dataX.RFID_Tag=cell.toString()

                          Log.e("file ",  indexC.toString()+" " + cell)
                      }else if (skuC===indexC)
                      {
                          dataX.SKU_Number=cell.toString()

                          Log.e("file ",  indexC.toString()+" " + cell)
                      }else if(designC===indexC)
                      {
                            dataX.Design_Number =cell.toString()

                          Log.e("file ",  indexC.toString()+" " + cell)
                      }else if(imageC===indexC)
                      {
                          dataX.Image_Name  =  cell.toString()

                          Log.e("file ",  indexC.toString()+" " + cell)
                      }else if (statusC=== indexC)
                      {
                          dataX.Item_Status = cell.toString()

                          Log.e("file ",  indexC.toString()+" " + cell)

                      }else if(typeC === indexC)
                      {
                          dataX.Item_Type =cell.toString()

                          Log.e("file ",  indexC.toString()+" " + cell)
                      }else if (sizeC === indexC)
                      {
                          dataX.Size = cell.toString()

                          Log.e("file ",  indexC.toString()+" " + cell)
                      }else if (grossC=== indexC)
                      {
                          dataX.Gross_Weight =cell.toString()

                          Log.e("file ",  indexC.toString()+" " + cell)
                      }else if (netC === indexC)
                      {
                          dataX.Net_Weight = cell.toString()

                          Log.e("file ",  indexC.toString()+" " + cell)
                      }else if (collectionC === indexC)
                      {
                          dataX.Collection_Line = cell.toString()

                          Log.e("file ",  indexC.toString()+" " + cell)
                      }
                      else if(metalC === indexC)
                      {
                          dataX.Metal_Type =cell.toString()

                          Log.e("file ",  indexC.toString()+" " + cell)

                      }
                      else if (purityC === indexC)
                      {
                          dataX.Metal_Purity = cell.toString()

                          Log.e("file ",  indexC.toString()+" " + cell)
                      }


                        println("--------")

                    }

                    println("-------------------->>>>>>>>>>>>>>>>>>")

                    userBox.put(dataX)


                }

            }





/*
            while (rows.hasNext()) {

                getIndexFromXLSX(rows,count)
            }*/

        }
    }

    private fun checkPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) { //Can add more as per requirement
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                123
            )
        } else {
            return true
        }
        return false
    }


   /**
    *  just made to check data
   *
   private fun getIndexFromXLSX(rows: MutableIterator<Row>, count: Int) {
        val currentRow = rows.next()

        val cellsInRow = currentRow.iterator()

        while (cellsInRow.hasNext()) {
            val currentCell = cellsInRow.next()
            if (currentCell.getCellTypeEnum() === CellType.STRING) {
                print(currentCell.getStringCellValue() + " | ")
                Toast.makeText(this,""+currentCell.getStringCellValue() ,Toast.LENGTH_SHORT)
            } else if (currentCell.getCellTypeEnum() === CellType.NUMERIC) {
                print(currentCell.getNumericCellValue().toString() + "(numeric)")
            }
        }

        println()

    }
*/
}

