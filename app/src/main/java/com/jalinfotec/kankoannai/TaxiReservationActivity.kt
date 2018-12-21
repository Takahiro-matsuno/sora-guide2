package com.jalinfotec.kankoannai

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_taxi_reservation.*
import org.jetbrains.anko.startActivity
import android.widget.DatePicker
import android.app.DatePickerDialog
import kotlinx.android.synthetic.main.activity_my_taxi.*
import java.util.Calendar


class TaxiReservationActivity :
    AppCompatActivity(),
    TimePickerDialog.CallbackListener
{
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

        /**
         * TODO 全画面別実装のため、TabではなくLinearLayoutとTextViewで実装している
         *   現状のままで問題ないか確認する
         */
        check_tab.setOnClickListener {
            // Myタクシー確認画面へ遷移
            startActivity<MyTaxiActivity>()
            this.finish()
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
            editDay.getText().clear()
            time_edit.getText().clear()
            editNumberAdlt.getText().clear()
            editNumberChld.getText().clear()
            editDispatchNumber.getText().clear()
            spinnerTaxi.setSelection(0)
            editDest.getText().clear()
            editName.getText().clear()
            editFurigana.getText().clear()
            editPhone.getText().clear()
            editMail.getText().clear()
            editComments.getText().clear()
            
        }


        /*
        // TODO テスト用に一時作成 画面遷移が完了したら消す
        toComp.setOnClickListener { startActivity<TaxiReservationCompActivity>(
            Pair(fromTaxiKey, fromTaxi),
            Pair(TaxiReservationCompActivity.resultKey, """このたびは、ホテルじゃらん　をご予約いただき誠にありがとうございます。
ご予約いただいた内容をお知らせします。

予約受付日時：2011年04月08日22:22

予約番号：01234567
宿泊代表者氏名：○○○○ 様
宿名：ホテルじゃらん　
電話番号：03-XXXX-XXXX
所在地：〒XXX-XXXX 東京都港区○○○○１－１－１
チェックイン日時：2011年04月09日14:00
宿泊日数：1泊
部屋タイプ：スーペリアルームＢ（32平米）
部屋数：1室

プラン名：ツィンルーム　朝食無料プラン
チェックイン可能時間：14:00～18:00
チェックアウト時間：11:00
プラン内容：お部屋は○○湾を一望できるオーシャンビューのハリウッドツイン。海を見な
がらのんびりゆったり癒しの一時をお楽しみ下さい。お部屋にはお子様用パジャマ・スリッ
パをご用意。絵本やおもちゃの貸し出しサービスもあるので、退屈しません。お子様の御夕
食は洋風のお子様ランチメニューとさせて頂きます。
食事：朝なし
夕なし

キャンセル規定：
1日前：宿泊料金の20%
当日：宿泊料金の80%
無連絡キャンセル：宿泊料金の100%
※12/17～25 X'MASフ゜ランにて宿泊のお客様は、料金特記参照。

料金明細

（1泊目）
（1部屋目）
8,000円（大人）× 2名
小計：16,000円

合計：16,000円（税込・サービス料別）
利用ポイント：0ポイント
ポイント利用後：16,000円（税込）
支払料金：16,000円（税込・サービス料別）
※別途サービス料5％(800円)が加算されます

★チェックインの時間の変更や予約金・料金確認など詳細のお問合せにつきましては、
下記宿泊施設まで直接ご連絡下さい★
宿名：ホテルじゃらん　
電話番号：03-XXXX-XXXX

予約を変更・キャンセルされる際はこちらの画面からお願いします。
ネットからの変更・キャンセル期限を過ぎている場合は直接宿泊施設へご連絡ください。
https://www.jalan.net/jalan/doc/howto/03yoyaku.html

■ホテルじゃらん　の詳細情報はこちら■
https://www.jalan.net/uw/uwp3000/uww3001.do?yadNo=123456

■ホテルじゃらん　周辺の観光スポット・イベント情報はこちら■
https://www.jalan.net/ou/oup1300/ouw1301.do?lrgCd=000000

ホテルじゃらん　

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

・・○●じゃらんｎｅｔからのお願い●○・・

▼予約確認メールについて
ご宿泊時には本メールをプリントアウトし、ご持参いただくことをお勧めいたします。

▼宿泊時に発生したトラブルについて
掲載内容と違う、ポイントを利用できなかった等、宿泊時に発生したトラブルに
つきましては、早期に解決するためにも、まずは宿泊施設へ
お申し出くださいますようお願いします。

・・●じゃらんnetで、旅の準備ができます●・・

☆★旅の同行者に予約内容をお知らせしませんか？★☆
同行される方に、楽しいイラスト入りのメッセージカードで、今回予約した内容をお知らせすることができます。
▼同行者へのメッセージカードはこちらから
https://message.jalan.net/pc/?a=companion_mail&rsv_no=XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

☆★旅先で訪ねる観光スポットはもうお決まりですか？★☆
じゃらん観光ガイドでは、気になる観光スポットの詳細情報はもちろん、
周辺のオススメスポットまでの距離や、イベント、宿泊施設の情報も一緒に
確認できます。旅行先で計画を立てる時に効率よく活用しよう！
▼観光ガイドはこちらから
https://www.jalan.net/ou/oup1300/ouw1301.do?lrgCd=000000

・・●じゃらんnetで、交通手段を予約できます●・・

☆★レンタカーをラクラク予約！★☆
ネット予約でオトクな割引も♪24時間いつでもOK！
▼じゃらんnet レンタカー予約はこちらから
http://c.p-advg.com/adpCnt/r?mid=819458&lid=1

☆★高速バスでお得に旅行！★☆
往復で予約すれば、更にうれしい割引が！
▼じゃらんnet 高速バスの予約はこちらから
http://c.p-advg.com/adpCnt/r?mid=839381&lid=1

☆★航空券の予約もじゃらんnetで★☆
JAL・ANAの航空券が予約・購入できます！
▼じゃらんnet 航空券の予約はこちらから
http://c.p-advg.com/adpCnt/r?mid=841068&lid=2

☆★毎日抽選！宿代全額キャッシュバックキャンペーン実施中★☆
なんと毎日一名様に、夏の宿予約代金を全額プレゼント！
更に温泉宿を予約した方には、１０万円相当温泉宿宿泊券が当たるかも？
キャンペーン期間：2010/6/1から2010/9/30まで
http://c.p-advg.com/adpCnt/r?mid=874063&lid=13

☆★ハワイ・ソウル・上海旅行が当たる！キャンペーン実施中★☆
なんと合計15組30名様にハワイ・ソウル・上海旅行をプレゼント！
にゃらんがハワイに！？新CMも公開中！
キャンペーン期間：2010/7/10から2010/8/30まで
https://www.jalan.net/jalan/doc/nyalan/summer2010/index.html

☆★帰ってきたら、ぜひあなたの感想・おすすめ情報を教えて下さい★☆
クチコミ投稿はこちらから↓
https://www.jalan.net/ji/pc/jit6001Login.do?TEMP4=LEVEL_K&TEMP5=https://www.jalan.net/uw/uwp0300/uww0301init.do
毎月50名様にJCBギフト券5000円分が当たります！
https://www.jalan.net/jalan/doc/howto/kuchikomi_toukou.html

株式会社リクルート
じゃらんnet　宿予約サービス
""".trimIndent()))
            this.finish()
        }
        */
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
                val showMonth = monthOfYear.toInt()+1
                editDay.setText(year.toString()+"/"+showMonth+"/"+dayOfMonth.toString())
            },
            year, month, day
        )

        // 表示
        datePicker.show()
    }
}
