package com.example.qrapplication

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.qrapplication.databinding.ActivityMainBinding
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView

class MainActivity : AppCompatActivity(), ZBarScannerView.ResultHandler {


    private lateinit var binding: ActivityMainBinding


    private lateinit var mView: View

    lateinit var scannerView: ZBarScannerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initializeQRCamera()
        onClicks()


    }

    private fun initializeQRCamera() {
        scannerView = ZBarScannerView(this)
        scannerView.setResultHandler(this)
        scannerView.setBackgroundColor(ContextCompat.getColor(this!!, R.color.colorTranslucent))
        scannerView.setBorderColor(ContextCompat.getColor(this!!, R.color.colorPrimaryDark))
        scannerView.setLaserColor(ContextCompat.getColor(this!!, R.color.colorPrimaryDark))
        scannerView.setBorderStrokeWidth(10)
        scannerView.setSquareViewFinder(true)
        scannerView.setupScanner()
        scannerView.setAutoFocus(true)
        startQRCamera()
        binding.containerScanner.addView(scannerView)
    }


    private fun startQRCamera() {
        scannerView.startCamera()
    }



    private fun onClicks() {
        binding.flashToggle.setOnClickListener {
            if (binding.flashToggle.isSelected) {
                offFlashLight()
            } else {
                onFlashLight()
            }
        }
    }

    private fun onFlashLight() {
        binding.flashToggle.isSelected = true
        scannerView.flash = true
    }

    private fun offFlashLight() {
        binding.flashToggle.isSelected = false
        scannerView.flash = false
    }

    override fun onResume() {
        super.onResume()
        scannerView.setResultHandler(this)
        scannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        scannerView.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        scannerView.stopCamera()
    }

    override fun handleResult(rawResult: Result?)
    {

        Toast.makeText(this!!,rawResult?.contents, Toast.LENGTH_LONG).show()
        scannerView.resumeCameraPreview(this)

    }


}