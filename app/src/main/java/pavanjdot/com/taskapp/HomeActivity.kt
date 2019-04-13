package pavanjdot.com.taskapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_home.*
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.util.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btn_uploadImage.setOnClickListener {

            uploadImage()
        }


        //Opening the Gallery Intent
        btn_openGallary.setOnClickListener(View.OnClickListener {
            Toast.makeText(
                this, "The photo gallery button is clicked",
                Toast.LENGTH_SHORT
            ).show()
            val galleryIntent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(galleryIntent, 2)

        })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {

                val contentURI = data?.getData()
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                imgTaken.setImageBitmap(bitmap)
            }
        }


    }

    fun uploadImage() {

        val uploadURL = "http://sfs.dev.ritesh.happlabs.in/dev/upload"

        var requestQ = Volley.newRequestQueue(this@HomeActivity)

        var stringRequest = StringRequest(Request.Method.POST, uploadURL, Response.Listener
        {
                response ->

            var jsonObj = JSONObject(response)

            var output = jsonObj.getString("response")
            Toast.makeText(this@HomeActivity, response, Toast.LENGTH_SHORT).show()
            imgTaken.setImageResource(0)
            imgTaken.setVisibility(View.GONE)
            edt_filename.setText("")
            edt_partnumber.setText("")



        }, Response.ErrorListener
        { error ->

            Toast.makeText(this@HomeActivity, error.message, Toast.LENGTH_SHORT).show()

        })

        @RequiresApi(Build.VERSION_CODES.O)
        fun getParams():Map<String, String> {
            val params = HashMap<String, String>()
            //This does not appear in the log
            Log.d("TAG", "Does it assign params?")
            params.put("file_name", edt_filename.text.toString())
            params.put("part_number", edt_partnumber.text.toString())
           // params.put("image", BitMapToString(bitmap))



            return params
        }




    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun BitMapToString(bitmap: Bitmap): String {
        val base = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, base)
        val b = base.toByteArray()
        return Base64.getEncoder().encodeToString(b)
    }
}
