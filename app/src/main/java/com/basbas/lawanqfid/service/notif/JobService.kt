//package id.co.iconpln.airsale.service
//
//import android.app.PendingIntent
//import android.app.PendingIntent.CanceledException
//import android.app.Service
//import android.bluetooth.BluetoothAdapter
//import android.content.Intent
//import android.os.IBinder
//import android.util.Log
//import iconpln.airsale.gayo.model.response.TransactionDto
//import iconpln.airsale.gayo.model.response.TransactionReportDto
//import id.co.iconpln.airsale.data.model.PrinterDto
//import id.co.iconpln.airsale.ui.setting.SettingActivity
//import id.co.iconpln.airsale.util.PrintUtilV2
//
//
//class JobService : Service(){
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        Log.e("TAG","running")
//        val resultIntent = Intent(this, SettingActivity::class.java)
//        resultIntent.putExtra("url", "rl")
//
//        val transactionDto: TransactionDto = TransactionDto()
//        val printer = PrinterDto()
//         val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
//        printer.let {
//            var isPrinterExist = false
//            bluetoothAdapter.bondedDevices.forEach { bt ->
//                if (bt.address == it.mac) isPrinterExist = true
//            }
//            if (isPrinterExist)
//                PrintUtilV2.printStrukAddress(this,
//                        BluetoothAdapter.getDefaultAdapter().getRemoteDevice(it.mac),
//                        transactionDto, false, "printerType")
//
//        }
//
//        PrintUtilV2.printtestService(this)
//        val pendingIntent = PendingIntent.getActivity(
//                this, 0, resultIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT
//
//        )
//        try { // Perform the operation associated with our pendingIntent
//            pendingIntent.send()
//        } catch (e: CanceledException) {
//            e.printStackTrace()
//        }
//
//        return super.onStartCommand(intent, flags, startId)
//    }
//    override fun onBind(p0: Intent?): IBinder? {
//        return null
//    }
//}
