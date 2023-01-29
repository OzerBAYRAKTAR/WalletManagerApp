package com.example.walletmanagerapplication.ui.Analytics

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.example.walletmanagerapplication.R
import com.example.walletmanagerapplication.data.RoomDb.Transaction
import com.example.walletmanagerapplication.databinding.FragmentAnalyticsBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AnalyticsFragment @Inject constructor() : Fragment(R.layout.fragment_analytics) {


    lateinit var arrayList:ArrayList<PieEntry>
    lateinit var ourPieChart:PieChart
    private val viewModel:AnalyticsViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding=FragmentAnalyticsBinding.bind(view)

        ourPieChart=binding.pieChart

        retrieveRecordsAndPopulateCharts()
        /*
        lifecycle.coroutineScope.launch {
            viewModel.getTransaction().collect {
                arrayList=ArrayList<PieEntry>()
                val arrayListx= ArrayList<String>()
                for (items in it.indices) {
                    arrayList.add(PieEntry(items.toFloat(),it[items].amount))
                    //arrayListx.add(it[items].category.toString())
                    val pieDataSet=PieDataSet(arrayList,"")
                    pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS,225)
                    pieDataSet.valueTextSize=15f
                    pieDataSet.valueTextColor= Color.BLACK
                    val pieData=PieData(pieDataSet)
                    binding.apply {
                        pieChart.data=pieData
                        pieChart.invalidate()
                        pieChart.description.text="Pie Chart"
                        pieChart.centerText="Expense List"
                        pieChart.animateY(2000)
                    }
                }
            }
          }
  */
    }
    private fun populatePieChart(values:Array<Int>,labels:Array<String>) {
        val ourPieEntry = ArrayList<PieEntry>()
        var i=0
        for (entry in values) {
            var value=values[i].toFloat()
            var label=labels[i]

            //adding each value to the pieEntry array
            ourPieEntry.add(PieEntry(value,label))
            i++
        }
        //assigning color to each slices
        val pieShades:ArrayList<Int> = ArrayList()
        pieShades.add(Color.parseColor("#0E2DEC"))
        pieShades.add(Color.parseColor("#B7520E"))
        pieShades.add(Color.parseColor("#5E6D4E"))
        pieShades.add(Color.parseColor("#DA1F12"))
        pieShades.add(Color.parseColor("#f1c40f"))
        pieShades.add(Color.parseColor("#8e44ad"))
        pieShades.add(Color.parseColor("#3498db"))
        pieShades.add(Color.parseColor("#d35400"))
        pieShades.add(Color.parseColor("#c0392b"))

        //add values to the pie dataset and passing them to the constructor
        val ourSet=PieDataSet(ourPieEntry,"")
        val data=PieData(ourSet)

        //setting the slices divider width
        ourSet.sliceSpace = 5f

        //populating the colors and data
        ourSet.colors = pieShades

        ourPieChart.data=data
        //setting color and size of text
        data.setValueTextColor(Color.WHITE)
        data.setValueTextSize(17f)


        ourPieChart.animateY(2300, Easing.EaseInCirc)

        ourPieChart.isDrawHoleEnabled = false

        ourPieChart.description.isEnabled = true
        ourPieChart.description.text="Total expenses incurred"
        ourPieChart.description.textSize
        ourPieChart.setEntryLabelTextSize(25f)
        //legend enabled and its various appearance settings
        ourPieChart.legend.isEnabled = true
        ourPieChart.legend.orientation = Legend.LegendOrientation.HORIZONTAL
        ourPieChart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        ourPieChart.legend.isWordWrapEnabled = true

        ourPieChart.centerText = "Amounts"
        ourPieChart.isDrawHoleEnabled = true

        ourPieChart.setTransparentCircleColor(Color.WHITE)
        ourPieChart.setTransparentCircleAlpha(350)
        ourPieChart.setCenterTextSize(15f)
        ourPieChart.setEntryLabelTextSize(26f)




        //dont show the text values on slices e.g Antelope, impala etc
        ourPieChart.setDrawEntryLabels(false)
        //refreshing the chart
        ourPieChart.invalidate()

    }
     fun retrieveRecordsAndPopulateCharts() {

        //calling the retreiveAnimals method of DatabaseHandler class to read the records
        val transaction: List<Transaction> = viewModel.getTrans()

        //create arrays for storing the values gotten

        val transactionId = Array<Int>(transaction.count()) { 0 }
        val nameArray = Array<String>(transaction.count()) { "natgeo" }
        val amountArray = Array<Int>(transaction.count()) { 0 }

        //add the records till done
        var index = 0
        for (abb in transaction) {
            transactionId[index]=abb.id
            nameArray[index]=abb.category
            amountArray[index]=abb.amount.toInt()
            index++
        }
        //call the methods for populating the charts
        populatePieChart(amountArray, nameArray)

    }

}
/* <androidx.cardview.widget.CardView
        android:id="@+id/analyticsTodayCard"
        android:layout_width="150dp"
        android:layout_height="150dp"
        android:layout_gravity="center"
        android:layout_rowWeight="1"
        android:backgroundTint="#03A9F4"
        android:layout_marginTop="60dp"
        app:cardCornerRadius="20dp"
        app:cardElevation="15dp">
        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:gravity="center"
            android:layout_gravity="center_vertical|center_horizontal">

            <ImageView
                android:id="@+id/analyticsTodayImage"
                android:layout_width="60dp"
                android:layout_height="60dp"
                android:src="@drawable/calendar"/>
            <TextView
                android:id="@+id/analyticsTodayText"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Today Analytics"
                android:textStyle="bold"
                android:textColor="#000"
                android:textAlignment="center"/>

        </LinearLayout>
    </androidx.cardview.widget.CardView>

 */