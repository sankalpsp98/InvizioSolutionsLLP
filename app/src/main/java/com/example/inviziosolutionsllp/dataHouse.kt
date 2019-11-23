package com.example.inviziosolutionsllp

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class dataHouse (@Id  var id: Long = 0,
                      var RFID_Tag : String? = null,
                      var SKU_Number:String?=null,
                      var Design_Number: String?=null,
                      var Image_Name:String?=null,
                      var Item_Status :String?=null,
                      var Item_Type:String?=null,
                      var Size:String?=null,
                      var Gross_Weight:String?=null,
                      var Net_Weight:String?=null,
                      var Collection_Line:String?=null,
                      var Metal_Type:String?=null,
                      var Metal_Purity:String?=null)