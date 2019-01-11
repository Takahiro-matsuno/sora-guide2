package com.jalinfotec.kankoannai

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputFilter
import android.widget.Spinner
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_my_taxi_change.*
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import java.util.*

//TODO 予約画面のアクティビティをそのまま貼り付け。要見直し。
@SuppressLint("Registered")
class MyTaxiChangeActivity :
    AppCompatActivity(),
    TimePickerDialog.CallbackListener {

    private val minHour = 0
    private val maxHour = 23
    private var hour = 12
    private var minute = 0

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_taxi_change)

        // アニメーションを無効化する（？）
        overridePendingTransition(0, 0)

        val bookingId = intent.getStringExtra("ID")!!

        //予約番号をキーに予約情報を検索
        val bookingDetailInfo = TaxiStub().getTaxiBookingInfo(bookingId)

        //予約情報を得られた場合、画面の各項目に値を設定
        if (bookingDetailInfo != null) {
            val df = SimpleDateFormat("yyyy/MM/dd")
            val tf = SimpleDateFormat("hh:mm")
            editDay.setText(df.format(bookingDetailInfo.rideOnDate))
            time_edit.setText(tf.format(bookingDetailInfo.rideOnDate))
            editNumberAdlt.setText(bookingDetailInfo.adultPassengerNumber.toString())
            editNumberChld.setText(bookingDetailInfo.childPassengerNumber.toString())
            editDispatchNumber.setText(bookingDetailInfo.taxiNumber.toString())
            setSelection(spinnerTaxi, bookingDetailInfo.companyId)
            editDest.setText(bookingDetailInfo.destination)
            editName.setText(bookingDetailInfo.name)
            editFurigana.setText(bookingDetailInfo.phonetic)
            editPhone.setText(bookingDetailInfo.userPhoneNumber)
            editMail.setText(bookingDetailInfo.mailAddress)
            editComments.setText(bookingDetailInfo.userComment)
        } else {
            //TODO 予約情報の取得に失敗した場合の処理
        }

        //"入力確認"ボタン押下時の動作
        changeCompleteButton.setOnClickListener {
            //入力必須項目が入力済み、大人＋子供が1人以上の場合のみ次画面に遷移
            if (editDay.text.isNotEmpty() &&
                time_edit.text.isNotEmpty() &&
                editNumberAdlt.text.isNotEmpty() &&
                editNumberChld.text.isNotEmpty() &&
                editDispatchNumber.text.isNotEmpty() &&
                editDest.text.isNotEmpty() &&
                editName.text.isNotEmpty() &&
                editFurigana.text.isNotEmpty() &&
                editPhone.text.isNotEmpty() &&
                editMail.text.isNotEmpty()
            ) {
                if (editNumberAdlt.text.toString().toInt() + editNumberChld.text.toString().toInt() > 0) {
                    startActivity<MyTaxiChangeCompActivity>(
                        Pair("RESULT_TEXT","変更が完了しました！\n通信未実装だけど。。。")
                    )
                    /*
                    //予約内容確認画面へ遷移
                    startActivity<TaxiReservationCheckActivity>(
                        Pair("DAY", editDay.text.toString()),
                        Pair("TIME", time_edit.text.toString()),
                        Pair("ADLT", editNumberAdlt.text.toString()),
                        Pair("CHLD", editNumberChld.text.toString()),
                        Pair("DISP", editDispatchNumber.text.toString()),
                        Pair("TAXI", spinnerTaxi.selectedItem.toString()),
                        Pair("DEST", editDest.text.toString()),
                        Pair("NAME", editName.text.toString()),
                        Pair("KANA", editFurigana.text.toString()),
                        Pair("PHONE", editPhone.text.toString()),
                        Pair("MAIL", editMail.text.toString()),
                        Pair("COMMENT", editComments.text.toString())
                    )
                    this.finish()
                    */
                } else {
                    Toast.makeText(this, "予約人数を1名以上にしてください", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "未入力の項目があります", Toast.LENGTH_SHORT).show()
            }
        }

        time_edit.setOnClickListener {
            // 乗車時間選択ダイアログを表示
            showTimePickerDialog(minHour, maxHour, hour, minute)
        }
        editDay.setOnClickListener {
            //乗車日選択ダイアログを表示
            datePick()
        }

        //戻るボタンが押された場合の処理
        backButton.setOnClickListener {
            startActivity<MyTaxiDetailActivity>(
                Pair("ID", bookingId)
            )
            this.finish()
        }


        val taxiNumFilter = InputFilter { source, start, end, dest, dstart, dend ->
            if (source.toString().matches("^[1-9]$".toRegex())) {
                source
            } else {
                ""
            }
        }
        val numFilter = InputFilter { source, start, end, dest, dstart, dend ->
            if (source.toString().matches("^[0-9]$".toRegex())) {
                source
            } else {
                ""
            }
        }
        editNumberAdlt.filters = arrayOf(numFilter, InputFilter.LengthFilter(1))
        editNumberChld.filters = arrayOf(numFilter, InputFilter.LengthFilter(1))
        editDispatchNumber.filters = arrayOf(taxiNumFilter, InputFilter.LengthFilter(1))
    }

    /**
     * Time PickerDialog
     */
    private fun showTimePickerDialog(minHour: Int, maxHour: Int, hour: Int, minute: Int) {
        val dialog = TimePickerDialog().newInstance(this)
        dialog.setParameter(minHour, maxHour, hour, minute)
        dialog.show(supportFragmentManager, null)
    }

    override fun setTime(hour: Int, minute: Int) {
        this.hour = hour
        this.minute = minute
        val hourStr = if (hour < 10) "0$hour" else "$hour"
        val minuteStr = if (minute < 10) "0$minute" else "$minute"
        setTimeText("$hourStr:$minuteStr")
    }

    private fun setTimeText(str: String) = time_edit.setText(str)

    /**
     *  Date PickerDialog
     */
    private fun datePick() {
        // 現在の日付を取得
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // 日付選択ダイアログの生成
        val datePicker = DatePickerDialog(
            this,

            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                // 「設定」ボタンクリック時の処理
                val showMonth = monthOfYear.toInt() + 1
                editDay.setText(year.toString() + "/" + showMonth + "/" + dayOfMonth.toString())
            },
            year, month, day
        )
        // 過去日を指定不可に設定
        datePicker.datePicker.minDate = System.currentTimeMillis() - 1000
        //一年以上先の日付は指定不可に設定
        datePicker.datePicker.maxDate = System.currentTimeMillis() + 31557600000
        // 表示
        datePicker.show()
    }

    //spinnerと文字列を入れて、その文字をspinnerに設定する
    private fun setSelection(spinner: Spinner, item: String) {
        val adapter = spinner.adapter
        var index = 0
        for (i in 0 until adapter.count) {
            if (adapter.getItem(i) == item) {
                index = i
                break
            }
        }
        spinner.setSelection(index)
    }
}