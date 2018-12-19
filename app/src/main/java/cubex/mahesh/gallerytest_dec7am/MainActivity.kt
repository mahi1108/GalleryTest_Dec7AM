package cubex.mahesh.gallerytest_dec7am

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.MediaController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.indiview.view.*
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var status = ContextCompat.checkSelfPermission(
            this@MainActivity,
            Manifest.permission.READ_EXTERNAL_STORAGE)

        if(status == PackageManager.PERMISSION_GRANTED){
            read()
        }else{
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                11)
        }
    } // onCreate( )

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            read()
        }else{
            System.exit(0)
        }

    }

    fun  read( ){

        var path = "/storage/sdcard0/WhatsApp/Media/WhatsApp Video/"
        var file = File(path)
        if(!file.exists()){
            path = "/storage/emulated/0/WhatsApp/Media/WhatsApp Video/"
            file = File(path)
        }

        var files = file.listFiles()

        var myadapter = object:BaseAdapter(){
            override fun getView(pos: Int, p1: View?, p2: ViewGroup?): View {

                var inflater = LayoutInflater.from(this@MainActivity)
                var v = inflater.inflate(R.layout.indiview,null)

                var new_file = files[pos]
                v.vview.setVideoPath(new_file.path)
                v.vview.setMediaController(MediaController(this@MainActivity))
                v.cb1.text = new_file.name
                v.cb1.setOnCheckedChangeListener { compoundButton, b ->
                    if(b)
                        v.vview.start()
                    else
                        v.vview.pause()
                }


                return  v
            }

            override fun getItem(p0: Int): Any {
                return  0
            }

            override fun getItemId(p0: Int): Long {
                return  0
            }

            override fun getCount(): Int {
                return  files.size
            }


        }

        gal.adapter = myadapter

    } // read( )
}
