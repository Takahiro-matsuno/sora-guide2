package com.jalinfotec.kankoannai

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_taxi_reservation.*
import org.jetbrains.anko.startActivity
import android.app.DatePickerDialog
import android.content.Intent
import android.support.design.widget.TabLayout
import android.text.InputFilter
import android.widget.*
import java.util.Calendar

//import sun.util.locale.provider.LocaleProviderAdapter.getAdapter


class TaxiReservationActivity :
    AppCompatActivity(),
    TimePickerDialog.CallbackListener {
    companion object {
        const val fromTaxiKey = "FROM_TAXI"
        const val fromTaxi = true
    }

    private val minHour = 0
    private val maxHour = 23
    private var hour = 12
    private var minute = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taxi_reservation)

        // タクシー画面から遷移した際はアニメーションを無効化する
        if (intent.getBooleanExtra(fromTaxiKey, false)) {
            overridePendingTransition(0, 0)
        }

        // 入力内容確認画面から"戻る"ボタンで戻った場合の処理
        editDay.setText(intent.getStringExtra("DAY"))
        time_edit.setText(intent.getStringExtra("TIME"))
        editNumberAdlt.setText(intent.getStringExtra("ADLT"))
        editNumberChld.setText(intent.getStringExtra("CHLD"))
        editDispatchNumber.setText(intent.getStringExtra("DISP"))
        if (intent.getStringExtra("TAXI") != null) {
            setSelection(spinnerTaxi, intent.getStringExtra("TAXI"))
        }
        editDest.setText(intent.getStringExtra("DEST"))
        editName.setText(intent.getStringExtra("NAME"))
        editFurigana.setText(intent.getStringExtra("KANA"))
        editPhone.setText(intent.getStringExtra("PHONE"))
        editMail.setText(intent.getStringExtra("MAIL"))
        editMailCheck.setText(intent.getStringExtra("MAIL"))
        editComments.setText(intent.getStringExtra("COMMENT"))


        //
        /**
         * TODO 全画面別実装のため、TabではなくLinearLayoutとTextViewで実装している
         *   現状のままで問題ないか確認する
         */
        // 指定したタブを選択状態にする
        tabLayout.getTabAt(0)!!.select()
        // タブのタップイベント
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            // 選択中のタブがユーザーによって再度選択されたときに呼び出される
            override fun onTabReselected(p0: TabLayout.Tab?) {}

            // タブが選択された状態を終了したときに呼び出される
            override fun onTabSelected(p0: TabLayout.Tab?) {}

            // タブが選択状態になると呼び出される
            override fun onTabUnselected(p0: TabLayout.Tab?) {
                when (tabLayout.selectedTabPosition) {
                    0 -> {
                        startActivity<TaxiReservationActivity>(
                            Pair(TaxiReservationActivity.fromTaxiKey, TaxiReservationActivity.fromTaxi)
                        )
                    }
                    1 -> {
                        startActivity<MyTaxiActivity>(
                            Pair(TaxiReservationActivity.fromTaxiKey, TaxiReservationActivity.fromTaxi)
                        )
                    }
                }
                finish()
            }
        })

        //"入力確認"ボタン押下時の動作
        inputConfirmButton.setOnClickListener {
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
                editMail.text.isNotEmpty() &&
                editMailCheck.text.isNotEmpty()
            ) {
                //予約人数チェック
                if (editNumberAdlt.text.toString().toInt() + editNumberChld.text.toString().toInt() > 0) {
                    //"メールアドレス"と"メールアドレス(確認)"が同じか
                    if(editMail.text.toString().equals(editMailCheck.text.toString())) {
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
                    }
                    else {
                        Toast.makeText(this, "メールアドレスを確認してください", Toast.LENGTH_SHORT).show()
                    }
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


        //クリアボタンが押された場合の処理
        inputClearButton.setOnClickListener {
            //editDay.getText().clear()
            editDay.text.clear()
            time_edit.text.clear()
            editNumberAdlt.text.clear()
            editNumberChld.text.clear()
            editDispatchNumber.text.clear()
            spinnerTaxi.setSelection(0)
            editDest.text.clear()
            editName.text.clear()
            editFurigana.text.clear()
            editPhone.text.clear()
            editMail.text.clear()
            editMailCheck.text.clear()
            editComments.text.clear()
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

            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
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
