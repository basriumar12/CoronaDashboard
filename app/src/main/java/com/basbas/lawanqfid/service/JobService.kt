package id.co.iconpln.airsale.service

import android.app.PendingIntent
import android.app.PendingIntent.CanceledException
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.IBinder


class JobService : Service(){
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
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



        return START_STICKY

    }
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}
